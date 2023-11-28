package com.example.awakeclient

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.awakeclient.ui.theme.AwakeClientTheme
import com.example.upstats.IAwakeInterface


class MainActivity : ComponentActivity() {
    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance.
            val time = IAwakeInterface.Stub.asInterface(service).getUptime()
            uptime.value = 1234
            Log.d("MainActivity", "Service connected: $time")
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
           Log.d("MainActivity", "Service disconnected")
        }
    }
    private var uptime: MutableState<Long> = mutableStateOf(0)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AwakeClientTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(uptime.value.toString())
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val intent = Intent()
        intent.component = ComponentName("com.example.upstats", "com.example.upstats.AwakenService")

        val status = bindService(intent, connection, BIND_AUTO_CREATE)
        Log.d("MainActivity", "Service bound $status")
        uptime.value = 4321
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AwakeClientTheme {
        Greeting("Android")
    }
}