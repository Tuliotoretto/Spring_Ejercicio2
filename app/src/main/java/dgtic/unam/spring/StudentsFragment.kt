package dgtic.unam.spring

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import dgtic.unam.spring.adapters.StudentAdapter
import dgtic.unam.spring.databinding.FragmentStudentsBinding
import dgtic.unam.spring.models.Student
import org.json.JSONArray

class StudentsFragment : Fragment() {

    private var _binding: FragmentStudentsBinding? = null
    private val binding get() = _binding!!

    private lateinit var volleyAPI: VolleyAPI
    private lateinit var studentsAdapter: StudentAdapter
    private var students: ArrayList<Student> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentsBinding.inflate(inflater, container, false)

        volleyAPI = VolleyAPI(requireContext())

        val layoutManager = LinearLayoutManager(context)
        binding.rvStudents.layoutManager = layoutManager
        studentsAdapter = StudentAdapter(students)
        binding.rvStudents.adapter = studentsAdapter

        studentsJSON()
        return binding.root
    }

    private fun studentsJSON() {
        val urlJSON = "http://"+VolleyAPI.localIp+"/estudiantesJSON"

        val jsonRequest = object : JsonArrayRequest(
            urlJSON,
            Response.Listener<JSONArray> { response ->
                (0 until response.length()).forEach {
                    val student = Gson().fromJson(response.getJSONObject(it).toString(), Student::class.java)
                    students.add(student)
                }
                studentsAdapter.notifyDataSetChanged()
            },
            Response.ErrorListener {
                Toast.makeText(requireContext(),"Error al cargar estudiantes", Toast.LENGTH_LONG).show()
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