package ru.dimagor555.academica.navigation

import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import ru.dimagor555.academica.login.LoginScreen
import ru.dimagor555.academica.login.OtpCodeScreen
import ru.dimagor555.academica.login.RegistrationScreen

object GlobalNavState {

    val stack = MutableStateFlow(listOf<Config>(LoginConfig))

    fun push(config: Config) {
        stack.update { it + config }
    }
}

sealed interface Config {

    @Composable
    fun ScreenUI()
}

object LoginConfig : Config {

    @Composable
    override fun ScreenUI() {
        LoginScreen()
    }
}

data class OtpConfig(
    val phone: String,
) : Config {

    @Composable
    override fun ScreenUI() {
        OtpCodeScreen(phone = phone)
    }
}

data class RegistrationConfig(
    val phone: String,
) : Config {

    @Composable
    override fun ScreenUI() {
        RegistrationScreen()
    }
}