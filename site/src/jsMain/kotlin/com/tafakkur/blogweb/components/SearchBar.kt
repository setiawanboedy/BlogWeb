package com.tafakkur.blogweb.components

import androidx.compose.runtime.*
import com.tafakkur.blogweb.styles.InputStyle
import com.tafakkur.blogweb.util.Id
import com.tafakkur.blogweb.util.JsTheme
import com.varabyte.kobweb.compose.css.CSSTransition
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.thenIf
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.icons.fa.FaMagnifyingGlass
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Col
import org.jetbrains.compose.web.dom.Input

@Composable
fun SearchBar(
    breakpoint: Breakpoint,
    modifier: Modifier = Modifier,
    fullWidth: Boolean = true,
    darkTheme: Boolean = false,
    onEnterClick: () -> Unit,
    onSearchIconClick: (Boolean) -> Unit
){
    var focused by remember { mutableStateOf(false) }

    LaunchedEffect(breakpoint){
        if (breakpoint >= Breakpoint.SM) onSearchIconClick(false)
    }

    if (breakpoint >= Breakpoint.SM || fullWidth){
        Row(
            modifier = modifier.thenIf(
                condition = fullWidth,
                other = Modifier.fillMaxWidth()
            )
                .padding(left = 20.px)
                .height(54.px)
                .backgroundColor(
                    if (darkTheme) JsTheme.Tertiary.rgb else JsTheme.LightGray.rgb
                )
                .borderRadius(r = 100.px)
                .border(
                    width = 2.px,
                    style = LineStyle.Solid,
                    color = if (focused && !darkTheme) JsTheme.Primary.rgb
                    else if (focused && darkTheme) JsTheme.Primary.rgb
                    else if (!focused && !darkTheme) JsTheme.LightGray.rgb
                    else if (!focused && darkTheme) JsTheme.Secondary.rgb
                    else JsTheme.LightGray.rgb
                )
                .transition(CSSTransition(property = "border", duration = 200.ms)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FaMagnifyingGlass(
                modifier = Modifier
                    .margin(right = 14.px)
                    .color(
                        if (focused) JsTheme.Primary.rgb else JsTheme.DarkGray.rgb
                    )
                    .transition(CSSTransition(property = "color", duration = 200.ms)),
                size = IconSize.SM
            )
            Input(
                type = InputType.Text,
                attrs = Modifier
                    .id(Id.adminSearchBar)
                    .fillMaxSize()
                    .color(
                        if (darkTheme) Colors.White else Colors.Black
                    )
                    .backgroundColor(Colors.Transparent)
                    .border(0.px)
                    .outline(
                        width = 0.px,
                        style = LineStyle.None,
                        color = Colors.Transparent
                    )
                    .onFocusIn { focused = true }
                    .onFocusOut { focused = false }
                    .onKeyDown {
                        if (it.key == "Enter"){
                            onEnterClick()
                        }
                    }
                    .toAttrs {
                        attr("placeholder", "Search...")
                    }
            )
        }
    }else{
        FaMagnifyingGlass(
            modifier = Modifier
                .margin(right = 14.px)
                .color(JsTheme.Primary.rgb)
                .cursor(Cursor.Pointer)
                .onClick { onSearchIconClick(true) },
            size = IconSize.SM
        )
    }
}