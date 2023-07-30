package ru.dimagor555.academica

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import ru.dimagor555.academica.login.AuthRepo
import ru.dimagor555.academica.navigation.GlobalNavState
import ru.dimagor555.academica.navigation.MainConfig
import ru.dimagor555.academica.ui.theme.AcademicaTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val authRepo = AuthRepo(LocalContext.current)
            if (authRepo.isAuthorized) {
                GlobalNavState.clearAndPush(MainConfig)
            }
            AcademicaTheme(useDarkTheme = true) {
                val stack by GlobalNavState.stack.collectAsState()
                val currConfig = stack.last()
                currConfig.ScreenUI()
            }
        }
    }

    override fun onBackPressed() {
        if (!GlobalNavState.pop()) {
            super.onBackPressed()
        }
    }
}