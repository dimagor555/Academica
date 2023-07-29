package ru.dimagor555.academica.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.dimagor555.academica.ui.theme.AcademicaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen() {
    LoginContainer(
        modifier = Modifier.verticalScroll(rememberScrollState()),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .padding(vertical = 32.dp, horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Заполните паспортные данные",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
            Text(
                text = "Это необходимо для получения предложений от банков",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(16.dp))
            val fields = listOf(
                Field(name = "Фамилия"),
                Field(name = "Имя"),
                Field(name = "Отчество"),
                Field(name = "Дата рождения"),
                Field(name = "Серия и номер паспорта"),
                Field(name = "Кем выдан"),
                Field(name = "Даты выдачи"),
                Field(name = "Код подразделения"),
                Field(name = "Email", isRequired = false),
            )
            fields.forEach {
                var text by remember { mutableStateOf("") }
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = text,
                    onValueChange = { text = it },
                    placeholder = { Text(text = it.formattedName) },
                    singleLine = true,
                    maxLines = 1,
                    label = { Text(text = it.formattedName) }
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {/*TODO*/},
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = "Далее",
                    style = MaterialTheme.typography.labelLarge,
                    fontSize = 16.sp,
                )
            }
        }
    }
}

private data class Field(
    private val name: String,
    val isRequired: Boolean = true,
) {
    val formattedName get() = if (isRequired) "$name*" else name
}

@Preview
@Composable
private fun RegistrationScreenPreview() {
    AcademicaTheme {
        RegistrationScreen()
    }
}