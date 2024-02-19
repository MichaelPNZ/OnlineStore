package com.example.core.navigation

interface DeeplinkHandler {
    fun process(deeplink: String): Boolean
}