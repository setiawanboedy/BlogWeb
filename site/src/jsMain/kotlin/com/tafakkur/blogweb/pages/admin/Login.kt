package com.tafakkur.blogweb.pages.admin

import androidx.compose.runtime.*
import com.tafakkur.blogweb.styles.LoginInputStyle
import com.tafakkur.blogweb.util.Constants.FONT_FAMILY
import com.tafakkur.blogweb.util.Id
import com.tafakkur.blogweb.util.JsTheme
import com.tafakkur.blogweb.util.Res
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Input

@Page
@Composable
fun LoginScreen() {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(leftRight = 50.px, top = 80.px, bottom = 24.px)
                .backgroundColor(JsTheme.Gray.rgb),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .margin(bottom = 50.px)
                    .width(100.px),
                src = Res.Image.logo,
                alt = "Logo Image"
            )

            Input(
                type = InputType.Text,
                attrs = LoginInputStyle.toModifier()
                    .id(Id.usernameInput)
                    .margin(bottom = 12.px)
                    .width(350.px)
                    .height(54.px)
                    .padding(leftRight = 20.px)
                    .backgroundColor(Colors.White)
                    .fontFamily(FONT_FAMILY)
                    .fontSize(14.px)
                    .outline(
                        width = 0.px,
                        style = LineStyle.None,
                        color = Colors.Transparent
                    )
                    .toAttrs {
                        attr("placeholder","Username")
                    }
            )
            Input(
                type = InputType.Password,
                attrs = LoginInputStyle.toModifier()
                    .id(Id.passwordInput)
                    .margin(bottom = 12.px)
                    .width(350.px)
                    .height(54.px)
                    .padding(leftRight = 20.px)
                    .backgroundColor(Colors.White)
                    .fontFamily(FONT_FAMILY)
                    .fontSize(14.px)
                    .outline(
                        width = 0.px,
                        style = LineStyle.None,
                        color = Colors.Transparent
                    )
                    .toAttrs {
                        attr("placeholder","Password")
                    }
            )
        }
    }
}