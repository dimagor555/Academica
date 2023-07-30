package ru.dimagor555.academica.navigation

import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import ru.dimagor555.academica.credit.CreditOfferOverviewScreen
import ru.dimagor555.academica.login.LoginScreen
import ru.dimagor555.academica.login.OtpCodeScreen
import ru.dimagor555.academica.login.RegistrationScreen
import ru.dimagor555.academica.university.University
import ru.dimagor555.academica.university.UniversityDetailsScreen
import ru.dimagor555.academica.university.UniversityOverviewScreen

object GlobalNavState {

    val stack = MutableStateFlow(listOf<Config>(LoginConfig))

    fun push(config: Config) {
        stack.update { it + config }
    }

    fun clearAndPush(config: Config) {
        stack.value = listOf(config)
    }

    fun pop(): Boolean {
        if (stack.value.size <= 1) {
            return false
        }
        stack.update {
            if (it.size > 1) it.dropLast(1) else it
        }
        return true
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

object CreditOfferOverviewConfig : Config {

    @Composable
    override fun ScreenUI() {
        CreditOfferOverviewScreen()
    }
}

object UniversityOverviewConfig : Config {

    @Composable
    override fun ScreenUI() {
        UniversityOverviewScreen()
    }
}

data class UniversityDetailsConfig(
    val university: University,
) : Config {

    @Composable
    override fun ScreenUI() {
        UniversityDetailsScreen(university = university)
    }
}