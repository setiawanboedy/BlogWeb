package com.tafakkur.blogweb.styles

import com.tafakkur.blogweb.util.Constants.FONT_FAMILY
import com.tafakkur.blogweb.util.JsTheme
import com.varabyte.kobweb.compose.css.CSSTransition
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.focus
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.px

val FormInputStyle = CssStyle {
    base {
        Modifier
            .fillMaxSize()
            .height(54.px)
            .padding(leftRight = 20.px)
            .backgroundColor(JsTheme.LightGray.rgb)
            .borderRadius(r = 4.px)
            .fontFamily(FONT_FAMILY)
            .fontSize(16.px)
            .border(
                width = 2.px,
                style = LineStyle.Solid,
                color = Colors.Transparent
            )
            .transition(CSSTransition(property = "border", 300.ms))
    }
    focus {
        Modifier.border(
            width = 2.px,
            style = LineStyle.Solid,
            color = JsTheme.Primary.rgb
        )
    }
}