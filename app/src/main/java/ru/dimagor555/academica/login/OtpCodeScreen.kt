package ru.dimagor555.academica.login

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import ru.dimagor555.academica.navigation.GlobalNavState
import ru.dimagor555.academica.navigation.RegistrationConfig
import ru.dimagor555.academica.navigation.UniversityOverviewConfig
import ru.dimagor555.academica.net.client
import ru.dimagor555.academica.ui.theme.AcademicaTheme
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.slots.PredefinedSlots

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpCodeScreen(phone: String) {
    LoginContainer {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .padding(vertical = 32.dp, horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            var code by remember { mutableStateOf("") }
            var isCodeError by remember { mutableStateOf(false) }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = "Введите код из смс",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                )
                val formattedPhone = remember {
                    val mask = MaskImpl(PredefinedSlots.RUS_PHONE_NUMBER, true)
                    mask.insertFront(phone)
                    mask.toString()
                }
                Text(
                    text = "Отправили на номер $formattedPhone",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Normal,
                )
                Spacer(modifier = Modifier.height(16.dp))
                val context = LocalContext.current
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = code,
                    onValueChange = {
                        isCodeError = false
                        if (it.length >= 4 && isCodeCorrect(it).not()) {
                            isCodeError = true
                        }
                        if (it.length > 4) {
                            return@OutlinedTextField
                        }
                        code = it
                        if (isCodeCorrect(code)) {
                            tryGoNext(phone = phone, context = context)
                        }
                    },
                    placeholder = { Text(text = "Введите код из смс") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Go),
                    keyboardActions = KeyboardActions(onGo = {}),
                    isError = isCodeError,
                )
                AnimatedVisibility(
                    visible = isCodeError,
                ) {
                    Text(
                        text = "Неверный код",
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            }
            var timeLeftSecs by remember { mutableStateOf(59) }
            LaunchedEffect(Unit) {
                while (true) {
                    delay(1000)
                    timeLeftSecs--
                }
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = timeLeftSecs <= 0,
                onClick = { timeLeftSecs = 59 },
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = when {
                        timeLeftSecs <= 0 -> "Отправить ещё раз"
                        timeLeftSecs >= 10 -> "00:$timeLeftSecs"
                        else -> "00:0$timeLeftSecs"
                    },
                    style = MaterialTheme.typography.labelLarge,
                    fontSize = 16.sp,
                )
            }
        }
    }
}

private fun isCodeCorrect(code: String): Boolean =
    code == "5555"

@OptIn(DelicateCoroutinesApi::class)
private fun tryGoNext(phone: String, context: Context) {
    GlobalScope.launch {
        val userDto = runCatching {
            client.get("users/admin/telephone") {
                contentType(ContentType.Application.Json)
                setBody(PhoneDto(phone.replace("+7", "8")))
            }.body<UserDto>()
        }.getOrNull()
        val authRepo = AuthRepo(context)
        authRepo.isAuthorized = userDto != null
        if (userDto == null) {
            GlobalNavState.push(RegistrationConfig(phone = phone))
        } else {
            GlobalNavState.clearAndPush(UniversityOverviewConfig)
        }
    }
}

@Serializable
private data class PhoneDto(
    val telephone: String,
)

@Serializable
private data class UserDto(
    val fullName: String,
    val email: String,
    val telephone: String,
    val passport: String,
)

@Preview
@Composable
private fun OtpCodeScreen() {
    AcademicaTheme {
        OtpCodeScreen(phone = "+79648979230")
    }
}