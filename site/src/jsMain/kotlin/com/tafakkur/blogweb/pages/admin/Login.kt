package com.tafakkur.blogweb.pages.admin

import androidx.compose.runtime.*
import com.tafakkur.blogweb.dto.LoginRequest
import com.tafakkur.blogweb.navigation.Screen
import com.tafakkur.blogweb.repository.AuthRepository
import com.tafakkur.blogweb.styles.InputStyle
import com.tafakkur.blogweb.util.Constants.FONT_FAMILY
import com.tafakkur.blogweb.util.Id
import com.tafakkur.blogweb.util.JsTheme
import com.tafakkur.blogweb.util.Res
import com.tafakkur.blogweb.util.isUserLoggedIn
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.toModifier
import kotlinx.browser.document
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Input
import org.koin.core.Koin
import org.koin.core.context.GlobalContext.get
import org.w3c.dom.HTMLInputElement


@Page
@Composable
fun LoginPage(){
    val context = rememberPageContext()
    isUserLoggedIn{
        context.router.navigateTo(Screen.AdminHome.route)
    }
    LoginScreen()
}

@Composable
fun LoginScreen() {
    val context = rememberPageContext()
    val scope = rememberCoroutineScope()
    var errorText by remember { mutableStateOf("") }
    val inject: Koin = get()
    val repository = inject.get<AuthRepository>()


    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(leftRight = 50.px, top = 80.px, bottom = 24.px)
                .backgroundColor(JsTheme.LightGray.rgb),
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
                attrs = InputStyle.toModifier()
                    .id(Id.usernameInput)
                    .margin(bottom = 12.px)
                    .width(350.px)
                    .height(54.px)
                    .padding(leftRight = 20.px)
                    .backgroundColor(Colors.White)
                    .fontFamily(FONT_FAMILY)
                    .fontSize(14.px)
                    .toAttrs {
                        attr("placeholder","Username")
                    }
            )
            Input(
                type = InputType.Password,
                attrs = InputStyle.toModifier()
                    .id(Id.passwordInput)
                    .margin(bottom = 12.px)
                    .width(350.px)
                    .height(54.px)
                    .padding(leftRight = 20.px)
                    .backgroundColor(Colors.White)
                    .fontFamily(FONT_FAMILY)
                    .fontSize(14.px)
                    .toAttrs {
                        attr("placeholder","Password")
                    }
            )

            Button(
                attrs = Modifier
                    .margin(bottom = 24.px)
                    .width(350.px)
                    .height(54.px)
                    .backgroundColor(JsTheme.Primary.rgb)
                    .color(Colors.White)
                    .borderRadius(r = 4.px)
                    .fontFamily(FONT_FAMILY)
                    .fontWeight(FontWeight.Medium)
                    .fontSize(14.px)
                    .border(0.px)
                    .cursor(Cursor.Pointer)
                    .onClick {
                        scope.launch {
                            val username =
                                (document.getElementById(Id.usernameInput) as HTMLInputElement).value
                            val password =
                                (document.getElementById(Id.passwordInput) as HTMLInputElement).value
                            if (username.isNotEmpty() && password.isNotEmpty()) {
                                val user = repository.login(LoginRequest(username,password))
                                if (user.data != null){
                                    context.router.navigateTo(Screen.AdminHome.route)
                                }else {
                                    errorText = "The user doesn't exist."
                                    delay(3000)
                                    errorText = " "
                                }
                            } else {
                                errorText = "Input fields are empty."
                                delay(3000)
                                errorText = " "
                            }
                        }
                    }
                    .toAttrs()
            ) {
                SpanText(text = "Sign in")
            }

            SpanText(
                modifier = Modifier
                    .width(350.px)
                    .color(Colors.Red)
                    .textAlign(TextAlign.Center)
                    .fontFamily(FONT_FAMILY),
                text = errorText
            )
        }
    }
}