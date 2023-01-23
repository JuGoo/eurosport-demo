package com.eurosport.demo.ui.navigation

sealed class NavRoute(val path: String) {

    object Home : NavRoute("home")

    object Detail : NavRoute("detail") {
        const val item = "item"
    }

    object Player : NavRoute("player") {
        const val videoUrl = "videoUrl"
    }

    // build navigation path (for screen navigation)
    fun withArgs(vararg args: String): String {
        return buildString {
            append(path)
            args.forEach{ arg ->
                append("/$arg")
            }
        }
    }

    // build and setup route format (in navigation graph)
    fun withArgsFormat(vararg args: String) : String {
        return buildString {
            append(path)
            args.forEach{ arg ->
                append("/{$arg}")
            }
        }
    }
}