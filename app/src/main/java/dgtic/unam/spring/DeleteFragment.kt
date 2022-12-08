package dgtic.unam.spring

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import dgtic.unam.spring.R
import dgtic.unam.spring.databinding.FragmentDeleteBinding
import org.json.JSONArray

class DeleteFragment : Fragment() {

    private var _binding: FragmentDeleteBinding? = null
    private val binding get() = _binding!!

    private lateinit var volleyAPI: VolleyAPI

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDeleteBinding.inflate(layoutInflater, container, false)

        volleyAPI = VolleyAPI(requireContext())

        binding.btnDel.setOnClickListener {
            if(checkInput()) {
                studentsDelete()
            } else {
                Toast.makeText(requireContext(), "Ingresa el id del estudiante", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        return binding.root
    }

    private fun checkInput(): Boolean = binding.searchText.text!!.isNotEmpty()

    private fun studentsDelete() {
        val urlJSON = "http://"+VolleyAPI.localIp+"/borrarestudiante/"+binding.searchText.text.toString()
        var cadena = ""
        val jsonRequest = object : JsonArrayRequest(
            urlJSON,
            Response.Listener<JSONArray> { response ->
                Toast.makeText(requireContext(), "Eliminado", Toast.LENGTH_LONG)
                    .show()
            },
            Response.ErrorListener {
                Toast.makeText(requireContext(), "No se encontró al estudiante", Toast.LENGTH_LONG)
                    .show()
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0 (Windows NT 6.1)"
                return headers
            }
            override fun getMethod(): Int {
                return Method.DELETE
            }
        }
        volleyAPI.add(jsonRequest)
    }
}