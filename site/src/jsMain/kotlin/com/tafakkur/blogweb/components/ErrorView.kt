package com.tafakkur.blogweb.components

import androidx.compose.runtime.Composable
import com.tafakkur.blogweb.util.Constants.FONT_FAMILY
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.css.px

@Composable
fun ErrorView(message: String){
    Box {
        SpanText(
            modifier = Modifier
                .fontFamily(FONT_FAMILY)
                .fontSize(18.px)
                .fontWeight(FontWeight.Medium),
            text = message
        )
    }
}