package com.example.cloud_messaging_example

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.cloud_messaging_example.ui.theme.Cloud_messaging_exampleTheme
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Cloud_messaging_exampleTheme {
                Surface(modifier = Modifier.fillMaxSize(),color = MaterialTheme.colorScheme.background) {
                    Home(intent)
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(intent: Intent) {
    val context = LocalContext.current
    val gckTokenKey = stringPreferencesKey("gcm_token")
    
    val fcmToken = flow<String> { 
        context.dataStore.data.map { 
            it[gckTokenKey]
        }.collect(collector = {
            if(it != null){
                this.emit(it)
            }
        })
    }.collectAsState(initial = "")

    var notificationTitle = remember {
        mutableStateOf(
            if (intent.hasExtra("title")) {
                intent.getStringExtra("title")
            } else {
                ""
            }
        )
    }


    var notificationBody = remember {
        mutableStateOf(
            if (intent.hasExtra("body")) {
                intent.getStringExtra("body")
            } else {
                ""
            }
        )
    }

    
    Scaffold(topBar = { SmallTopAppBar(title = { Text(text = "Push Notification") })}) {
        Column(modifier = Modifier.padding(it)) {
            //Text(text = "FCM Token")
            //Text(text = fcmToken.value)

            Text(text = "Notification Title: ${notificationTitle.value}")
            Text(text = "Notification Body: ${notificationBody.value}")


        }
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
    Cloud_messaging_exampleTheme {
        Greeting("Android")
    }
}