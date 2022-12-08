package dgtic.unam.spring.models

data class Student (
    val cuenta: String,
    val nombre: String,
    val edad: String,
    val materias: List<Materia>
)

data class Materia (
    val id: Long,
    val nombre: String,
    val creditos: Long
)

