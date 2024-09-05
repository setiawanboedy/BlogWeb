package com.tafakkur.blogweb.components

import androidx.compose.runtime.*
import com.tafakkur.blogweb.models.Category
import com.tafakkur.blogweb.util.JsTheme
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.icons.fa.FaAngleDown
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text

@Composable
fun Dropdown(
    selectedCategory: Category,
    onCategorySelect: (Category)->Unit,
){
    var isDropdownOpen by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .backgroundColor(JsTheme.LightGray.rgb)
            .padding(12.px)
            .cursor(Cursor.Pointer)
            .position(Position.Relative)
            .onClick {
                isDropdownOpen = !isDropdownOpen
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SpanText(text = selectedCategory.name)
            FaAngleDown(
                modifier = Modifier
                    .color(JsTheme.HalfBlack.rgb)
            )
        }
    }

    if (isDropdownOpen){
        Div(
            attrs = Modifier
                .fillMaxWidth()
                .backgroundColor(Colors.White)
                .border(1.px, LineStyle.Solid, Color.black)
                .padding(8.px)
                .toAttrs()
        ) {
            Category.entries.forEach { category: Category ->

                DropdonwItem(text = category.name, onClick = {
                    onCategorySelect(category)
                    isDropdownOpen = !isDropdownOpen
                })
            }
        }
    }
}

@Composable
fun DropdonwItem(
    text: String,
    onClick: () -> Unit
){
    Div(
        attrs = DropdownStyle.toModifier()
            .fillMaxWidth()
            .padding(8.px)
            .cursor(Cursor.Pointer)
            .onClick { onClick() }
            .toAttrs()
    ) {
        Text(text)
    }
}

val DropdownStyle = CssStyle {
    hover {
        Modifier.
        backgroundColor(Color.lightgray)
    }
}