package ru.dimagor555.academica.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.dimagor555.academica.navigation.GlobalNavState
import ru.dimagor555.academica.navigation.OtpConfig

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen() {
    LoginContainer {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .padding(vertical = 32.dp, horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            var phone by remember { mutableStateOf(TextFieldValue("")) }
            var isPhoneError by remember { mutableStateOf(false) }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {

                Text(
                    text = "Вход и регистрация",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "Нужен только номер телефона",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Normal,
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = phone,
                    onValueChange = {
                        isPhoneError = false
                        if (it.text.any { it !in "0123456789+" }
                            || it.text.drop(1).any { it !in "0123456789" }) {
                            return@OutlinedTextField
                        }
                        if (phone.text.length > it.text.length && it.text.length > 12) {
                            return@OutlinedTextField
                        }
                        if (phone.text.isEmpty()) {
                            when (it.text) {
                                "8", "+", "+7" -> {
                                    phone = TextFieldValue(text = "+7", selection = TextRange(2))
                                    return@OutlinedTextField
                                }
                                "9" -> {
                                    phone = TextFieldValue(
                                        text = "+79",
                                        selection = TextRange(3)
                                    )
                                    return@OutlinedTextField
                                }
                            }
                        }
                        phone = it
                    },
                    placeholder = { Text(text = "Введите номер") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone, imeAction = ImeAction.Go),
                    keyboardActions = KeyboardActions(onGo = {
                        if (!isNumberValid(phone.text)) {
                            isPhoneError = true
                        } else {
                            tryGoNext(phone.text)
                        }
                    }),
                    isError = isPhoneError,
                    //todo add visual transformation for number
                )
                AnimatedVisibility(
                    visible = isPhoneError,
                ) {
                    Text(
                        text = "Номер введён неверно",
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    if (!isNumberValid(phone.text)) {
                        isPhoneError = true
                    } else {
                        tryGoNext(phone.text)
                    }
                },
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

private fun isNumberValid(phone: String) =
    phone.length == 12 && "\\+7\\d{10}".toRegex().matches(phone)

private fun tryGoNext(phone: String) {
    GlobalNavState.push(OtpConfig(phone = phone))
}