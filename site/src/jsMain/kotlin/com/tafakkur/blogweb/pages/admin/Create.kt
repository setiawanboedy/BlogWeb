package com.tafakkur.blogweb.pages.admin

import androidx.compose.runtime.*
import com.tafakkur.blogweb.components.AdminPageLayout
import com.tafakkur.blogweb.components.ControlPopup
import com.tafakkur.blogweb.components.Dropdown
import com.tafakkur.blogweb.components.MessagePopup
import com.tafakkur.blogweb.models.Category
import com.tafakkur.blogweb.models.EditorControl
import com.tafakkur.blogweb.pages.admin.components.CategoryDropdown
import com.tafakkur.blogweb.pages.admin.components.Editor
import com.tafakkur.blogweb.pages.admin.components.EditorControls
import com.tafakkur.blogweb.pages.admin.components.ThumbnailUploader
import com.tafakkur.blogweb.styles.FormInputStyle
import com.tafakkur.blogweb.util.*
import com.tafakkur.blogweb.util.Constants.FONT_FAMILY
import com.tafakkur.blogweb.util.Constants.SIDE_PANEL_WIDTH
import com.varabyte.kobweb.compose.css.VerticalAlign
import com.varabyte.kobweb.compose.foundation.layout.*
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.forms.Switch
import com.varabyte.kobweb.silk.components.forms.SwitchSize
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.*
import org.w3c.dom.HTMLInputElement
import kotlinx.browser.document

data class CreatePageUiState(
    var id: String = "",
    var title: String = "",
    var subtitle: String = "",
    var thumbnail: String = "",
    var thumbnailInputDisable: Boolean = true,
    var content: String = "",
    var category: Category = Category.Programming,
    var buttonText: String = "Create",
    var editorVisibility: Boolean = true,
    var messagePopup: Boolean = false,
    var linkPopup: Boolean = false,
    var imagePopup: Boolean = false
) {
    fun reset() = this.copy(
        id = "",
        title = "",
        subtitle = "",
        thumbnail = "",
        content = "",
        category = Category.Programming,
        buttonText = "Create",
        editorVisibility = true,
        messagePopup = false,
        linkPopup = false,
        imagePopup = false
    )
}

@Page
@Composable
fun CreateScreen() {
    var uiState by remember { mutableStateOf(CreatePageUiState()) }
    val breakpoint = rememberBreakpoint()
    var isChecked by remember { mutableStateOf(false) }
    AdminPageLayout {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .margin(topBottom = 50.px)
                .padding(left = if (breakpoint > Breakpoint.MD) SIDE_PANEL_WIDTH.px else 0.px),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .maxWidth(700.px),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SimpleGrid(
                    modifier = Modifier
                        .margin(topBottom = 24.px),
                    numColumns = numColumns(base = 1, sm = 3)
                ) {
                    Row(
                        modifier = Modifier
                            .margin(
                                right = 24.px,
                                bottom = if (breakpoint < Breakpoint.SM) 12.px else 0.px
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Switch(
                            modifier = Modifier.margin(right = 8.px),
                            checked = true,
                            onCheckedChange = {},
                            size = SwitchSize.LG
                        )
                        SpanText(
                            modifier = Modifier
                                .fontSize(14.px)
                                .fontFamily(FONT_FAMILY)
                                .color(JsTheme.HalfBlack.rgb),
                            text = "Popular"
                        )
                    }
                    Row(
                        modifier = Modifier
                            .margin(
                                right = 24.px,
                                bottom = if (breakpoint < Breakpoint.SM) 12.px else 0.px
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Switch(
                            modifier = Modifier.margin(right = 8.px),
                            checked = true,
                            onCheckedChange = {},
                            size = SwitchSize.LG
                        )
                        SpanText(
                            modifier = Modifier
                                .fontSize(14.px)
                                .fontFamily(FONT_FAMILY)
                                .color(JsTheme.HalfBlack.rgb),
                            text = "Main"
                        )
                    }
                    Row(
                        modifier = Modifier
                            .margin(bottom = if (breakpoint < Breakpoint.SM) 12.px else 0.px),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Switch(
                            modifier = Modifier.margin(right = 8.px),
                            checked = false,
                            onCheckedChange = {},
                            size = SwitchSize.LG
                        )
                        SpanText(
                            modifier = Modifier
                                .fontSize(14.px)
                                .fontFamily(FONT_FAMILY)
                                .color(JsTheme.HalfBlack.rgb),
                            text = "Sponsored"
                        )
                    }
                }
                Input(
                    type = InputType.Text,
                    attrs = FormInputStyle.toModifier()
                        .id(Id.titleInput)
                        .margin(topBottom = 12.px)
                        .outline(
                            width = 0.px,
                            style = LineStyle.None,
                            color = Colors.Transparent
                        )
                        .toAttrs {
                            attr("placeholder", "Title")
                        }
                )
                Input(
                    type = InputType.Text,
                    attrs = FormInputStyle.toModifier()
                        .id(Id.subtitleInput)
                        .margin(topBottom = 12.px)
                        .outline(
                            width = 0.px,
                            style = LineStyle.None,
                            color = Colors.Transparent
                        )
                        .toAttrs {
                            attr("placeholder", "Subtitle")
                        }
                )
                Dropdown(
                    selectedCategory = uiState.category,
                    onCategorySelect = {
                        uiState = uiState.copy(category = it)
                    }
                )
                Box(modifier = Modifier.margin(topBottom = 12.px))
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .margin(topBottom = 12.px),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Switch(
                        modifier = Modifier.margin(right = 8.px),
                        checked = !uiState.thumbnailInputDisable,
                        onCheckedChange = {
                            uiState = uiState.copy(thumbnailInputDisable = it)
                        },
                        size = SwitchSize.MD
                    )
                    SpanText(
                        modifier = Modifier
                            .fontSize(14.px)
                            .fontFamily(FONT_FAMILY)
                            .color(JsTheme.HalfBlack.rgb),
                        text = "Paste an Image URL instead"
                    )
                }
                Box(modifier = Modifier.margin(topBottom = 12.px))
                ThumbnailUploader(
                    thumbnail = uiState.thumbnail,
                    thumbnailInputDisabled = uiState.thumbnailInputDisable,
                    onThumbnailSelect = { filename, file ->
                        (document.getElementById(Id.thumbnailInput) as HTMLInputElement).value = filename
                        uiState = uiState.copy(thumbnail = file)
                    }
                )
                Box(modifier = Modifier.margin(topBottom = 12.px))
                EditorControls(
                    breakpoint = breakpoint,
                    editorVisibility = uiState.editorVisibility,
                    onEditorVisibilityChange = {
                        uiState = uiState.copy(
                            editorVisibility = !uiState.editorVisibility
                        )
                    },
                    onLinkClick = {
                        uiState = uiState.copy(linkPopup = true)
                    },
                    onImageClick = {
                        uiState = uiState.copy(imagePopup = true)
                    }
                )
                Editor(
                    editorVisibility = uiState.editorVisibility
                )
                CreateButton(
                    text = uiState.buttonText,
                    onClick = {


                    }
                )
            }
        }

    }

    if (uiState.messagePopup){
        MessagePopup(
            message = "Please fill out all fields.",
            onDialogDismiss = {
                uiState = uiState.copy(messagePopup = false)
            }
        )
    }

    if (uiState.linkPopup){
        ControlPopup(
            editorControl = EditorControl.Link,
            onDialogDismiss = {
                uiState = uiState.copy(linkPopup = false)
            },
            onAddClick = {href, title ->
                applyStyle(
                    ControlStyle.Link(
                        selectedText = getSelectedText(),
                        href = href,
                        title = title
                    )
                )
            }
        )
    }

    if (uiState.imagePopup){
        ControlPopup(
            editorControl = EditorControl.Image,
            onDialogDismiss = {
                uiState = uiState.copy(imagePopup = false)
            },
            onAddClick = {imageUrl, description ->
                applyStyle(
                    ControlStyle.Image(
                        selectedText = getSelectedText(),
                        imageUrl = imageUrl,
                        alt = description
                    )
                )
            }
        )
    }
}

@Composable
fun CreateButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        attrs = Modifier
            .onClick { onClick() }
            .fillMaxWidth()
            .height(54.px)
            .margin(top = 24.px)
            .backgroundColor(JsTheme.Primary.rgb)
            .color(Colors.White)
            .borderRadius(r = 4.px)
            .fontFamily(FONT_FAMILY)
            .fontSize(16.px)
            .border(width = 0.px)
            .toAttrs()
    ) {
        SpanText(text = text)
    }
}