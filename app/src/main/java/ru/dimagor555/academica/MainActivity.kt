package ru.dimagor555.academica

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.dimagor555.academica.ui.theme.AcademicaTheme

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AcademicaTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Color.Black,
                ) { padding ->
                    Surface(
                        modifier = Modifier
                            .padding(top = 100.dp)
                            .fillMaxSize(),
                        shape = RoundedCornerShape(32.dp),
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(top = 64.dp)
                                .fillMaxWidth()
                                .padding(vertical = 16.dp, horizontal = 32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                        ) {
                            var email by remember { mutableStateOf("") }
                            OutlinedTextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = email,
                                onValueChange = { email = it },
                                placeholder = { Text(text = "Email") }
                            )
                            var password by remember { mutableStateOf("") }
                            OutlinedTextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = password,
                                onValueChange = { password = it },
                                placeholder = { Text(text = "Password") }
                            )
                            Button(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = { /*TODO*/ },
                            ) {
                                Text(
                                    modifier = Modifier.padding(vertical = 8.dp),
                                    text = "Sing up",
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}