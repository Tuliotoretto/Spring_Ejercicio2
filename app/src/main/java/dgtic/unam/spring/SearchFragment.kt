package dgtic.unam.spring

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import dgtic.unam.spring.R
import dgtic.unam.spring.databinding.FragmentSearchBinding
import dgtic.unam.spring.databinding.FragmentStudentsBinding
import org.json.JSONObject

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var volleyAPI: VolleyAPI

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(layoutInflater, container, false)

        volleyAPI = VolleyAPI(requireContext())

        binding.btnSearch.setOnClickListener {
            if (checkInput()) {
                studentsID()
            } else {
                Toast.makeText(requireContext(),"Llena el id del estudiante", Toast.LENGTH_LONG)
                    .show()
            }
        }

        return binding.root
    }

    private fun checkInput(): Boolean = binding.searchText.text!!.isNotEmpty()

    private fun studentsID(){
        val urlJSON = "http://"+VolleyAPI.localIp+"/id/"+binding.searchText.text.toString()
        println(urlJSON)
        var cadena = ""
        val jsonRequest = object : JsonObjectRequest(
            Method.GET,
            urlJSON,
            null,
            Response.Listener<JSONObject> { response ->
                binding.outText.text = response.get("cuenta")
                    .toString() + "----" + response.get("nombre").toString() + "\n"
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
        }
        volleyAPI.add(jsonRequest)
    }
}