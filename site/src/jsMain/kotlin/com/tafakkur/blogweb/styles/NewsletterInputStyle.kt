package com.tafakkur.blogweb.styles

import com.tafakkur.blogweb.util.JsTheme
import com.varabyte.kobweb.compose.css.CSSTransition
import com.varabyte.kobweb.compose.css.TransitionProperty
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.outline
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.focus
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.px

val NewsletterInputStyle = CssStyle {
    base {
        Modifier
            .outline(
                width = 1.px,
                style = LineStyle.Solid,
                color = Colors.Transparent
            )
            .border(
                width = 1.px,
                style = LineStyle.Solid,
                color = Colors.Transparent
            )
            .transition(CSSTransition(property = TransitionProperty.All, duration = 300.ms))
    }

    focus {
        Modifier
            .outline(
                width = 1.px,
                style = LineStyle.Solid,
                color = JsTheme.Primary.rgb
            )
            .border(
                width = 1.px,
                style = LineStyle.Solid,
                color = JsTheme.Primary.rgb
            )
    }
}