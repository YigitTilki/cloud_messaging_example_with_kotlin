package com.example.cloud_messaging_example

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.cloud_messaging_example.R
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.cloud_messaging_example.ui.theme.Cloud_messaging_exampleTheme
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.time.format.TextStyle

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Cloud_messaging_exampleTheme {
                Surface(modifier = Modifier.fillMaxSize(),color = Color.White) {
                    HomePage(intent = intent)
                }

            }
        }
    }


    @Composable
    fun HomePage(intent: Intent){
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
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            //Text(text = "FCM Token")
            //Text(text = fcmToken.value)
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "Cloud Messaging Project",
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            )
            Spacer(modifier = Modifier.height(15.dp))
            if(notificationTitle.value!!.isEmpty() && notificationBody.value!!.isEmpty()){
                Text(text = "Notification Box Empty", modifier = Modifier.padding(15.dp),fontSize = 17.sp)
            }
            else {
                Text(
                    text = "Notification Title",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Text(
                    text = notificationTitle.value!!,
                    fontSize = 17.sp
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Notification Body",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Text(
                    text = notificationBody.value!!,
                    fontSize = 17.sp
                )
            }
            Spacer(modifier = Modifier.height(300.dp))
            Card(
                modifier = Modifier.size(100.dp),
            ) {
                Image(
                    painterResource(R.drawable.ic_message),
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    
    }
}








