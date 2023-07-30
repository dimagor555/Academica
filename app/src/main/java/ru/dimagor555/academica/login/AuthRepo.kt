package ru.dimagor555.academica.login

import android.content.Context
import androidx.core.content.edit

class AuthRepo(
    context: Context,
) {

    private val prefs = context.getSharedPreferences("main", Context.MODE_PRIVATE)

    var isAuthorized: Boolean
        get() {
            return prefs.getBoolean("isAuth", false)
        }
        set(value) {
            prefs.edit {
                putBoolean("isAuth", value)
            }
        }
}