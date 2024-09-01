package com.tafakkur.blogweb.components

import androidx.compose.runtime.Composable
import com.tafakkur.blogweb.models.EditorControl
import com.tafakkur.blogweb.styles.InputStyle
import com.tafakkur.blogweb.util.Constants.FONT_FAMILY
import com.tafakkur.blogweb.util.Id
import com.tafakkur.blogweb.util.JsTheme
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.toModifier
import kotlinx.browser.document
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Input
import org.w3c.dom.HTMLInputElement

@Composable
fun MessagePopup(
    message: String,
    onDialogDismiss: () -> Unit
){
    Box(
        modifier = Modifier
            .width(100.vw)
            .height(100.vh)
            .position(Position.Fixed)
            .zIndex(19),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .backgroundColor(JsTheme.HalfBlack.rgb)
                .onClick { onDialogDismiss() }
        )
        Box(
            modifier = Modifier
                .padding(all = 24.px)
                .backgroundColor(Colors.White)
                .borderRadius(r = 4.px)
        ) {
            SpanText(
                modifier = Modifier
                    .fillMaxWidth()
                    .textAlign(TextAlign.Center)
                    .fontFamily(FONT_FAMILY)
                    .fontSize(16.px),
                text = message
            )
        }
    }
}

@Composable
fun ControlPopup(
    editorControl: EditorControl,
    onDialogDismiss: () -> Unit,
    onAddClick: (String, String) -> Unit,
){
    Box(
        modifier = Modifier
            .width(100.vw)
            .height(100.vh)
            .position(Position.Fixed)
            .zIndex(19),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .backgroundColor(JsTheme.HalfBlack.rgb)
                .onClick { onDialogDismiss() }
        )
        Column(
            modifier = Modifier
                .width(500.px)
                .padding(all = 24.px)
                .backgroundColor(Colors.White)
                .borderRadius(r = 4.px)
        ){
            Input(
                type = InputType.Text,
                attrs = InputStyle.toModifier()
                    .id(Id.linkHrefInput)
                    .fillMaxWidth()
                    .height(54.px)
                    .padding(left = 20.px)
                    .margin(bottom = 12.px)
                    .fontFamily(FONT_FAMILY)
                    .fontSize(14.px)
                    .borderRadius(r = 4.px)
                    .backgroundColor(JsTheme.LightGray.rgb)
                    .toAttrs {
                        attr("placeholder", if (editorControl == EditorControl.Link) "Href" else "Image URL")
                    }
            )

            Input(
                type = InputType.Text,
                attrs = InputStyle.toModifier()
                    .id(Id.linkTitleInput)
                    .fillMaxWidth()
                    .height(54.px)
                    .padding(left = 20.px)
                    .margin(bottom = 20.px)
                    .fontFamily(FONT_FAMILY)
                    .fontSize(14.px)
                    .borderRadius(r = 4.px)
                    .backgroundColor(JsTheme.LightGray.rgb)
                    .toAttrs {
                        attr("placeholder", if (editorControl == EditorControl.Link) "Title" else "Description")
                    }
            )

            Button(
                attrs = Modifier
                    .onClick {
                        val href = (document.getElementById(Id.linkHrefInput) as HTMLInputElement).value
                        val title = (document.getElementById(Id.linkTitleInput) as HTMLInputElement).value
                        onAddClick(href, title)
                        onDialogDismiss()
                    }
                    .fillMaxWidth()
                    .height(54.px)
                    .borderRadius(r = 4.px)
                    .backgroundColor(JsTheme.Primary.rgb)
                    .color(Colors.White)
                    .border(0.px)
                    .fontFamily(FONT_FAMILY)
                    .fontSize(14.px)
                    .toAttrs()
            ) {
                SpanText(text = "Add")
            }
        }
    }
}