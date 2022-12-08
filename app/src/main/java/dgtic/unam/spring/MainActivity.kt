package dgtic.unam.spring

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import dgtic.unam.spring.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        // initial setup
        replaceFragment(StudentsFragment())
        mainBinding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.students -> {
                    replaceFragment(StudentsFragment())
                }
                R.id.add -> {
                    replaceFragment(AddFragment())
                }
                R.id.search -> {
                    replaceFragment(SearchFragment())
                }
                R.id.delete -> {
                    replaceFragment(DeleteFragment())
                }
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(mainBinding.frameLayout.id, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}