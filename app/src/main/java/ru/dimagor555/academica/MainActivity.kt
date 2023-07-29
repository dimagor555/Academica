package ru.dimagor555.academica

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.view.WindowCompat
import ru.dimagor555.academica.navigation.GlobalNavState
import ru.dimagor555.academica.ui.theme.AcademicaTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            AcademicaTheme(useDarkTheme = true) {
                val stack by GlobalNavState.stack.collectAsState()
                val currConfig = stack.last()
                currConfig.ScreenUI()
            }
        }
    }
}