package com.tafakkur.blogweb.components

import androidx.compose.runtime.Composable
import com.tafakkur.blogweb.models.Category
import com.tafakkur.blogweb.util.Constants.FONT_FAMILY
import com.tafakkur.blogweb.util.JsTheme
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.css.px

@Composable
fun CategoryChip(
    category: Category,
    darkTheme: Boolean = false
){
    Box {
        SpanText(
            modifier = Modifier
                .fontFamily(FONT_FAMILY)
                .fontSize(12.px)
                .color(
                    if (darkTheme) JsTheme.entries.find {
                        it.hex == category.color
                    }?.rgb ?: JsTheme.HalfBlack.rgb
                    else JsTheme.HalfBlack.rgb
                ),
            text = category.name
        )
    }
}