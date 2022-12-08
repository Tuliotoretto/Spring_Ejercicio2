package dgtic.unam.spring.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dgtic.unam.spring.R
import dgtic.unam.spring.databinding.ListElementStudentBinding
import dgtic.unam.spring.models.Student

class StudentAdapter(private val studentsList: List<Student>): RecyclerView.Adapter<StudentAdapter.ViewHolder>() {

    private var students: List<Student> = listOf()

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvNombre = itemView.findViewById<TextView>(R.id.tvNombre)
        val tvCuenta = itemView.findViewById<TextView>(R.id.tvCuenta)
        val tvEdad = itemView.findViewById<TextView>(R.id.tvEdad)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_element_student, parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentStudent = studentsList[position]

        holder.tvNombre.text = currentStudent.nombre
        holder.tvCuenta.text = currentStudent.cuenta
        holder.tvEdad.text = currentStudent.edad
    }

    override fun getItemCount(): Int {
        Log.d("DEBBUGING", studentsList.count().toString())
        return studentsList.size
    }


}