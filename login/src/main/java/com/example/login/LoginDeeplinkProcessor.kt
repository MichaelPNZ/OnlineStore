package com.example.login

import android.content.Context
import android.content.Intent
import com.example.core.navigation.DeeplinkProcessor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginDeeplinkProcessor @Inject constructor(
    private val context: Context
) : DeeplinkProcessor {
    override fun matches(deeplink: String): Boolean {
        return deeplink.contains("/login")
    }

    override fun execute(deeplink: String) {
        val intent = Intent(context, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}