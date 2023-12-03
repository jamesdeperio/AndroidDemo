package com.example.common.router.route

object AccountOpeningRoute {
    object LoginScreen:Route() {
        override fun getClassPath(): String
                = "com.example.login.ui.login.LoginActivity"
    }


}