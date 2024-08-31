package com.tafakkur.blogweb.pages.admin.components

import androidx.compose.runtime.Composable
import com.tafakkur.blogweb.styles.FormInputStyle
import com.tafakkur.blogweb.util.Constants.FONT_FAMILY
import com.tafakkur.blogweb.util.Id
import com.tafakkur.blogweb.util.JsTheme
import com.varabyte.kobweb.browser.file.loadDataUrlFromDisk
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.thenIf
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.toModifier
import kotlinx.browser.document
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Input

@Composable
fun ThumbnailUploader(
    thumbnail: String,
    thumbnailInputDisabled: Boolean,
    onThumbnailSelect: (String, String) -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(54.px)
    ) {
        Input(
            type = InputType.Text,
            attrs = FormInputStyle.toModifier()
                .id(Id.thumbnailInput)
                .margin(right = 12.px)
                .thenIf(
                    condition = thumbnailInputDisabled,
                    other = Modifier.disabled()
                )
                .toAttrs {
                    attr("placeholder", "Thumbnail")
                }
        )
        Button(
            attrs = Modifier
                .onClick {
                    document.loadDataUrlFromDisk(
                        accept = "image/png, image/jpeg",
                        onLoad = {
                            onThumbnailSelect(filename, it)
                        }
                    )
                }
                .fillMaxHeight()
                .padding(leftRight = 24.px)
                .backgroundColor(if (!thumbnailInputDisabled) JsTheme.Gray.rgb else JsTheme.Primary.rgb)
                .color(if (!thumbnailInputDisabled) JsTheme.DarkGray.rgb else Colors.White)
                .borderRadius(r = 4.px)
                .fontFamily(FONT_FAMILY)
                .fontWeight(FontWeight.Medium)
                .fontSize(14.px)
                .border(
                    width = 0.px,
                )
                .thenIf(
                    condition = !thumbnailInputDisabled,
                    other = Modifier.disabled()
                )
                .toAttrs()
        ) {
            SpanText(text = "Upload")
        }
    }
}