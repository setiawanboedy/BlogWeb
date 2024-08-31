package com.tafakkur.blogweb.pages.admin.components

import androidx.compose.runtime.Composable
import com.tafakkur.blogweb.models.EditorControl
import com.tafakkur.blogweb.styles.EditorKeyStyle
import com.tafakkur.blogweb.util.*
import com.tafakkur.blogweb.util.Constants.FONT_FAMILY
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Visibility
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.thenIf
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import kotlinx.browser.document
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button

@Composable
fun EditorControls(
    breakpoint: Breakpoint,
    editorVisibility: Boolean,
    onLinkClick: () -> Unit,
    onImageClick: () -> Unit,
    onEditorVisibilityChange: () -> Unit
){
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        SimpleGrid(
            modifier = Modifier.fillMaxWidth(),
            numColumns = numColumns(base = 1, sm = 2)
        ){
            Row(
                modifier = Modifier
                    .backgroundColor(JsTheme.LightGray.rgb)
                    .borderRadius(r = 4.px)
                    .height(54.px)
            ) {
                EditorControl.entries.forEach {
                    EditorControlView(
                        control = it,
                        onClick = {
                            applyControlStyle(
                                editorControl = it,
                                onLinkClick = onLinkClick,
                                onImageClick = onImageClick
                            )
                        }
                    )
                }
            }

            Box(contentAlignment = Alignment.CenterEnd) {
                Button(
                    attrs = Modifier
                        .height(54.px)
                        .thenIf(
                            condition = breakpoint < Breakpoint.SM,
                            other = Modifier.fillMaxWidth()
                        )
                        .margin(topBottom = if (breakpoint < Breakpoint.SM) 12.px else 0.px)
                        .padding(leftRight = 24.px)
                        .borderRadius(r = 4.px)
                        .backgroundColor(
                            if (editorVisibility) JsTheme.LightGray.rgb
                            else JsTheme.Primary.rgb
                        )
                        .color(
                            if (editorVisibility) JsTheme.DarkGray.rgb
                            else Colors.White
                        )
                        .border(width = 0.px)
                        .onClick {
                            onEditorVisibilityChange()
                            document.getElementById(Id.editorPreview)?.innerHTML = getEditor().value
                            js("hljs.highlightAll()") as Unit
                        }
                        .toAttrs()
                ) {
                    SpanText(
                        modifier = Modifier
                            .fontFamily(FONT_FAMILY)
                            .fontWeight(FontWeight.Medium)
                            .fontSize(14.px),
                        text = "Preview"
                    )
                }
            }
        }
    }
}

@Composable
fun EditorControlView(
    control: EditorControl,
    onClick: () -> Unit
){
    Box(
        modifier = EditorKeyStyle.toModifier()
            .fillMaxHeight()
            .padding(leftRight = 12.px)
            .borderRadius(r = 4.px)
            .cursor(Cursor.Pointer)
            .onClick { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            src = control.icon,
            alt = "${control.name} Icon"
        )
    }
}