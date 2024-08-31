package com.tafakkur.blogweb.styles

import com.tafakkur.blogweb.util.JsTheme
import com.varabyte.kobweb.compose.css.CSSTransition
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.hover
import org.jetbrains.compose.web.css.ms

val EditorKeyStyle = CssStyle {
    base {
        Modifier
            .backgroundColor(Colors.Transparent)
            .transition(CSSTransition(property = "background", 300.ms))
    }
    hover {
        Modifier.backgroundColor(JsTheme.Primary.rgb)
    }
}