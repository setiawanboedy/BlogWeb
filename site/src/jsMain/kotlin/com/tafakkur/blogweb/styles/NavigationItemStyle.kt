package com.tafakkur.blogweb.styles

import com.tafakkur.blogweb.util.Id
import com.tafakkur.blogweb.util.JsTheme
import com.varabyte.kobweb.compose.css.CSSTransition
import com.varabyte.kobweb.compose.css.TransitionProperty
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.style.CssStyle
import org.jetbrains.compose.web.css.ms

val NavigationItemStyle = CssStyle {
    cssRule(" > #${Id.svgParent} > #${Id.vectorIcon}"){
        Modifier.transition(CSSTransition(property = TransitionProperty.All, duration = 300.ms))
            .styleModifier {
                property("stroke", JsTheme.White.hex)
            }
    }

    cssRule(":hover > #${Id.svgParent} > #${Id.vectorIcon}"){
        Modifier
            .styleModifier {
                property("stroke", JsTheme.Primary.hex)
            }
    }

    cssRule(" > #${Id.navigationText}"){
        Modifier.transition(CSSTransition(property = TransitionProperty.All, duration = 300.ms))
            .color(JsTheme.White.rgb)
    }

    cssRule(":hover > #${Id.navigationText}"){
        Modifier.color(JsTheme.Primary.rgb)
    }
}