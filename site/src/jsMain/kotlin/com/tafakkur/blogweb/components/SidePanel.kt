package com.tafakkur.blogweb.components

import androidx.compose.runtime.*
import com.tafakkur.blogweb.navigation.Screen
import com.tafakkur.blogweb.util.Constants.COLLAPSED_PANEL_HEIGHT
import com.tafakkur.blogweb.util.Constants.SIDE_PANEL_WIDTH
import com.tafakkur.blogweb.util.JsTheme
import com.tafakkur.blogweb.util.Res
import com.varabyte.kobweb.compose.css.CSSTransition
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.ScrollBehavior
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.icons.fa.FaBars
import com.varabyte.kobweb.silk.components.icons.fa.FaXmark
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.*

@Composable
fun SidePanel(onMenuClick: () -> Unit){
    val breakpoint = rememberBreakpoint()
    if (breakpoint > Breakpoint.MD){
        SidePanelInternal()
    }else{
        CollapsedSidePanel(onMenuClick)
    }
}

@Composable
private fun SidePanelInternal(){
    Column(
        modifier = Modifier
            .padding(leftRight = 40.px, topBottom = 50.px)
            .width(SIDE_PANEL_WIDTH.px)
            .height(100.vh)
            .position(Position.Fixed)
            .backgroundColor(JsTheme.Secondary.rgb)
            .zIndex(9)
    ){
        Image(
            modifier = Modifier.margin(bottom = 60.px),
            src = Res.Image.logo,
            alt = "Logo Image"
        )
    }
}

@Composable
private fun CollapsedSidePanel(onMenuClick: ()->Unit){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(COLLAPSED_PANEL_HEIGHT.px)
            .padding(leftRight = 24.px)
            .backgroundColor(JsTheme.Secondary.rgb),
        verticalAlignment = Alignment.CenterVertically

    ) {
        FaBars(
            modifier = Modifier
                .margin(right = 24.px)
                .color(Colors.White)
                .cursor(Cursor.Pointer)
                .onClick { onMenuClick() },
            size = IconSize.XL
        )

        Image(
            modifier = Modifier.width(80.px),
            src = Res.Image.logo,
            alt = "Logo Image"
        )
    }
}

@Composable
fun OverflowSidePanel(
    onMenuClose: () -> Unit,
    content: @Composable () -> Unit
){
    val context = rememberPageContext()
    val breakpoint = rememberBreakpoint()
    val scope = rememberCoroutineScope()

    var translateX by remember{ mutableStateOf((-100).percent) }
    var opacity by remember { mutableStateOf(0.percent) }

    LaunchedEffect(key1 = breakpoint){
        translateX = 0.percent
        opacity = 100.percent
        if (breakpoint > Breakpoint.MD){
            scope.launch {
                translateX = (-100).percent
                opacity = 0.percent
                delay(500)
                onMenuClose()
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.vh)
            .position(Position.Fixed)
            .zIndex(9)
            .opacity(opacity)
            .transition(CSSTransition(property = "opacity", duration = 300.ms))
            .backgroundColor(JsTheme.HalfBlack.rgb)
    ) {
        Column(
            modifier = Modifier
                .padding(all = 24.px)
                .fillMaxHeight()
                .width(if (breakpoint < Breakpoint.MD) 50.percent else 25.percent)
                .transition(CSSTransition(property = "translate", duration = 300.ms))
                .overflow(Overflow.Auto)
                .scrollBehavior(ScrollBehavior.Smooth)
                .backgroundColor(JsTheme.Secondary.rgb)
        ) {
            Row(
                modifier = Modifier.margin(
                    bottom = 60.px,
                    top = 24.px
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FaXmark(
                    modifier = Modifier
                        .margin(right = 24.px)
                        .color(Colors.White)
                        .cursor(Cursor.Pointer)
                        .onClick {
                            scope.launch {
                                translateX = (-100).percent
                                opacity = 0.percent
                                delay(500)
                                onMenuClose()
                            }
                        },
                    size = IconSize.LG
                )
                Image(
                    modifier = Modifier
                        .width(80.px)
                        .onClick {
                            context.router.navigateTo(Screen.AdminLogin.route)
                        }
                        .cursor(Cursor.Pointer),
                    src = Res.Image.logo,
                    alt = "Logo Image"
                )
            }
            content()
        }
    }
}