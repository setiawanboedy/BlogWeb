package com.tafakkur.blogweb

import androidx.compose.runtime.*
import com.tafakkur.blogweb.di.appModule
import com.tafakkur.blogweb.navigation.Screen
import com.tafakkur.blogweb.util.isUserLoggedIn
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.App
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.SilkApp
import com.varabyte.kobweb.silk.components.layout.Surface
import com.varabyte.kobweb.silk.style.common.SmoothColorStyle
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.*
import org.koin.core.context.startKoin


@App
@Composable
fun MyApp(content: @Composable () -> Unit) {
    startKoin {
        modules(appModule)
    }

    SilkApp {
        Surface(SmoothColorStyle.toModifier().minHeight(100.vh)) {

            content()
        }
    }
}

