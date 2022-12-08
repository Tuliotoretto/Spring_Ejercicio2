package dgtic.unam.spring

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class VolleyAPI constructor(context: Context) {
    companion object{
        val localIp = "192.168.0.3:8080"

        @Volatile
        private var INSTANCE: VolleyAPI?=null
        fun getInstance(context: Context)= INSTANCE?: synchronized(this) {
            INSTANCE?:VolleyAPI(context).also { INSTANCE=it }
        }
    }
    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }
    fun<T> add(req: Request<T>){
        requestQueue.add(req)
    }
}