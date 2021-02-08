package raum.muchbeer.foodktx.utility

import android.util.Log
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket

object DoesNetworkHaveInternet {
    // Make sure to execute this on a background thread.
    fun execute(): Boolean {
        return try{
            Log.d("DoesNetInternet", "PINGING google.")
            val socket = Socket()
            socket.connect(InetSocketAddress("8.8.8.8", 53), 1500)
            socket.close()
            Log.d("DoesNetInternet", "PING success.")
            true
        }catch (e: IOException){
            Log.e("DoesNetInternet", "No internet connection. ${e}")
            false
        }
    }

}