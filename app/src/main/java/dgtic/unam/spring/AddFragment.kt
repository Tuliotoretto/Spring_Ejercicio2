package dgtic.unam.spring

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import dgtic.unam.spring.databinding.FragmentAddBinding
import org.json.JSONArray
import org.json.JSONObject

class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private lateinit var volleyAPI: VolleyAPI

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(layoutInflater, container, false)

        volleyAPI = VolleyAPI(requireContext())


        binding.btnAdd.setOnClickListener {
            activity?.window?.setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

            if (checkInput()) {
                studentsAdd()
            } else {
                Toast.makeText(requireContext(), "Llena los datos necesarios", Toast.LENGTH_LONG)
                    .show()
            }
        }

        return binding.root
    }

    private fun checkInput(): Boolean = binding.etCuenta.text!!.isNotEmpty() and
            binding.etNombre.text!!.isNotEmpty() and
            binding.etEdad.text!!.isNotEmpty()


    private fun studentsAdd() {
        val urlJSON = "http://" + VolleyAPI.localIp + "/agregarestudiante"
        var cadena = ""
        val jsonRequest = object : JsonArrayRequest(
            urlJSON,
            Response.Listener<JSONArray> { response ->
                (0 until response.length()).forEach {
                    val estudiante = response.getJSONObject(it)
                    val materia = estudiante.getJSONArray("materias")
                    cadena += estudiante.get("cuenta").toString() + "<-"
                    (0 until materia.length()).forEach {
                        val datos = materia.getJSONObject(it)
                        cadena += datos.get("nombre").toString() + "----" + datos.get("creditos")
                            .toString() + "--"
                    }
                    cadena += "> \n"
                }
                //binding.outText.text = cadena
            },
            Response.ErrorListener {
                //binding.outText.text = getString(R.string.error)
                println(it.toString())
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                headers["Accept"] = "application/json"
                return headers
            }

            override fun getBody(): ByteArray {
                val estudiante = JSONObject()
                estudiante.put("cuenta", binding.etCuenta.text)
                estudiante.put("nombre", binding.etNombre.text)
                estudiante.put("edad", binding.etEdad.text)
                val materias = JSONArray()
                val itemMaterias = JSONObject()
                itemMaterias.put("id", "1")
                itemMaterias.put("nombre", "Nueva Materia")
                itemMaterias.put("creditos", "10")
                materias.put(itemMaterias)
                estudiante.put("materias", materias)
                println(estudiante.toString())
                return estudiante.toString().toByteArray(charset = Charsets.UTF_8)
            }

            override fun getMethod(): Int {
                return Method.POST
            }
        }
        volleyAPI.add(jsonRequest)
    }
}