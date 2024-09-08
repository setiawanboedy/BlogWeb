package com.tafakkur.blogweb.pages.admin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.tafakkur.blogweb.models.Constants.UPDATED_PARAM
import com.tafakkur.blogweb.navigation.Screen
import com.tafakkur.blogweb.util.Constants.FONT_FAMILY
import com.tafakkur.blogweb.util.JsTheme
import com.tafakkur.blogweb.util.Res
import com.tafakkur.blogweb.util.isUserLoggedIn
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.text.SpanText
import kotlinx.coroutines.delay
import org.jetbrains.compose.web.css.px

@Page
@Composable
fun SuccessPage(){
    isUserLoggedIn{
        SuccessScreen()
    }
}

@Composable
fun SuccessScreen(){
    val context = rememberPageContext()
    val postUpdate = context.route.params.containsKey(UPDATED_PARAM)

    LaunchedEffect(Unit){
        delay(500)
        context.router.navigateTo(Screen.AdminCreate.route)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.margin(bottom = 24.px),
            src = Res.Icon.checkmark,
            alt = "Checkmark Icon"
        )
        SpanText(
            modifier = Modifier
                .fontFamily(FONT_FAMILY)
                .fontSize(24.px),
            text = if (postUpdate) "Post Successfully Update!" else "Post Successfully Created!"
        )
        SpanText(
            modifier = Modifier
                .color(JsTheme.HalfBlack.rgb)
                .fontFamily(FONT_FAMILY)
                .fontSize(18.px),
            text = "Redirecting you back..."
        )
    }
}