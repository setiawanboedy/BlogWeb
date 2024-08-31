package com.tafakkur.blogweb.pages.admin

import androidx.compose.runtime.*
import com.tafakkur.blogweb.components.AdminPageLayout
import com.tafakkur.blogweb.models.Category
import com.tafakkur.blogweb.styles.FormInputStyle
import com.tafakkur.blogweb.util.Constants.FONT_FAMILY
import com.tafakkur.blogweb.util.Constants.SIDE_PANEL_WIDTH
import com.tafakkur.blogweb.util.Id
import com.tafakkur.blogweb.util.JsTheme
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.foundation.layout.*
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.attrsModifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
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

data class CreatePageUiState(
    var id: String = "",
    var title: String = "",
    var subtitle: String = "",
    var thumbnail: String = "",
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
        thumbnail="",
        content="",
        category=Category.Programming,
        buttonText="Create",
        editorVisibility = true,
        messagePopup = false,
        linkPopup = false,
        imagePopup = false
    )
}

@Page
@Composable
fun CreateScreen() {
    var uiState by remember{ mutableStateOf(CreatePageUiState()) }
    val breakpoint = rememberBreakpoint()
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
                    numColumns = numColumns(base = 1, sm = 3)) {
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

                CategoryDropdown(
                    selectedCategory = uiState.category,
                    onCategorySelect = {
                        uiState = uiState.copy(category = it)
                    }
                )
            }
        }

    }
}

@Composable
fun CategoryDropdown(
    selectedCategory: Category,
    onCategorySelect: (Category) -> Unit
) {

    Box(
        modifier = Modifier
            .margin(topBottom = 12.px)
            .classNames("dropdown")
            .fillMaxWidth()
            .height(54.px)
            .backgroundColor(JsTheme.LightGray.rgb)
            .cursor(Cursor.Pointer)
            .attrsModifier {
                attr("data-bs-toggle", "dropdown")
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(leftRight = 20.px),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SpanText(
                modifier = Modifier
                    .fillMaxWidth()
                    .fontSize(16.px)
                    .fontFamily(FONT_FAMILY),
                text = selectedCategory.name
            )
            Box(modifier = Modifier.classNames("dropdown-toggle"))
        }

        Ul(
            attrs = Modifier
                .fillMaxWidth()
                .classNames("dropdown-menu")
                .toAttrs()
        ) {
            Category.entries.forEach { category: Category ->
                Li {
                    A(
                        attrs = Modifier
                            .classNames("dropdown-item")
                            .color(Colors.Black)
                            .fontFamily(FONT_FAMILY)
                            .fontSize(16.px)
                            .onClick { onCategorySelect(category) }
                            .toAttrs()
                    ) {
                        Text(value = category.name)
                    }
                }
            }
        }
    }
}