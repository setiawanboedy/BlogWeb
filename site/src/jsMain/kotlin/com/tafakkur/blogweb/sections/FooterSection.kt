package com.tafakkur.blogweb.sections

import androidx.compose.runtime.Composable
import com.tafakkur.blogweb.util.Constants.FONT_FAMILY
import com.tafakkur.blogweb.util.JsTheme
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.css.px

@Composable
fun FooterSection(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(topBottom = 50.px)
            .backgroundColor(JsTheme.Secondary.rgb),
        contentAlignment = Alignment.Center
    ) {
        Row {
            SpanText(
                modifier = Modifier
                    .fontFamily(FONT_FAMILY)
                    .fontSize(14.px)
                    .color(Colors.White),
                text = "Copyright @ 2024 • "
            )

            SpanText(
                modifier = Modifier
                    .fontFamily(FONT_FAMILY)
                    .fontSize(14.px)
                    .color(JsTheme.Primary.rgb),
                text = "Budi Setiawan"
            )
        }
    }
}