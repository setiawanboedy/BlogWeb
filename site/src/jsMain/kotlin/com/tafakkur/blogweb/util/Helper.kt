package com.tafakkur.blogweb.util

import androidx.compose.runtime.*
import com.tafakkur.blogweb.core.storage.LocalStorageManager
import com.tafakkur.blogweb.core.utils.Constants.EXPIRES_AT
import com.tafakkur.blogweb.navigation.Screen
import com.varabyte.kobweb.core.rememberPageContext
import org.koin.core.Koin
import org.koin.core.context.GlobalContext.get

@Composable
fun isUserLoggedIn(content: @Composable () -> Unit) {
    val inject: Koin = get()
    val storage = inject.get<LocalStorageManager>()

    val context = rememberPageContext()
    val expired = remember { storage.getItem(EXPIRES_AT) }

    var isExpired by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        if (!expired.isNullOrEmpty()) {
            isExpired = isTokenExpired(expired.toLong())
            println(isExpired)
        } else {
            context.router.navigateTo(Screen.AdminLogin.route)
        }

    }
    if (isExpired) {
        context.router.navigateTo(Screen.AdminLogin.route)
    } else {
        content()
    }
}
