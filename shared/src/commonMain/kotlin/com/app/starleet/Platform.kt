package com.app.starleet

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform