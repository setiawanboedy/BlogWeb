package com.tafakkur.blogweb.pages.admin.components

import androidx.compose.runtime.Composable
import com.tafakkur.blogweb.styles.InputStyle
import com.tafakkur.blogweb.util.*
import com.tafakkur.blogweb.util.Constants.FONT_FAMILY
import com.varabyte.kobweb.compose.css.*
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.TextArea

@Composable
fun Editor(editorVisibility: Boolean) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        TextArea(
            attrs = InputStyle.toModifier()
                .id(Id.editor)
                .fillMaxWidth()
                .height(300.px)
                .maxHeight(300.px)
                .resize(Resize.None)
                .margin(top = 8.px)
                .padding(all = 20.px)
                .backgroundColor(JsTheme.LightGray.rgb)
                .outline(
                    width = 0.px,
                    style = LineStyle.None,
                    color = Colors.Transparent
                )
                .visibility(
                    if (editorVisibility) Visibility.Visible
                    else Visibility.Hidden
                )
                .onKeyDown {
                    if (it.code == "Enter" && it.shiftKey) {
                        applyStyle(
                            controlStyle = ControlStyle.Break(
                                selectedText = getSelectedText()
                            )
                        )
                    }
                }
                .fontFamily(FONT_FAMILY)
                .fontSize(16.px)
                .toAttrs {
                    attr("placeholder", "Type here...")
                }
        )
        Div(
            attrs = Modifier
                .id(Id.editorPreview)
                .fillMaxWidth()
                .height(300.px)
                .maxHeight(300.px)
                .margin(top = 8.px)
                .padding(all = 20.px)
                .backgroundColor(JsTheme.LightGray.rgb)
                .borderRadius(r = 4.px)
                .visibility(if (editorVisibility) Visibility.Hidden
                else Visibility.Visible)
                .overflow(Overflow.Auto)
                .scrollBehavior(ScrollBehavior.Smooth)
                .border(width = 0.px)
                .toAttrs()
        )
    }
}