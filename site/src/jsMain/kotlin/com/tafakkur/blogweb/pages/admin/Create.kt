package com.tafakkur.blogweb.pages.admin

import androidx.compose.runtime.*
import com.tafakkur.blogweb.components.AdminPageLayout
import com.tafakkur.blogweb.components.ControlPopup
import com.tafakkur.blogweb.components.Dropdown
import com.tafakkur.blogweb.components.MessagePopup
import com.tafakkur.blogweb.core.sealed.ApiResponse
import com.tafakkur.blogweb.dto.PostRequest
import com.tafakkur.blogweb.models.Category
import com.tafakkur.blogweb.models.Constants.POST_ID_PARAM
import com.tafakkur.blogweb.models.EditorControl
import com.tafakkur.blogweb.models.Status
import com.tafakkur.blogweb.navigation.Screen
import com.tafakkur.blogweb.pages.admin.components.Editor
import com.tafakkur.blogweb.pages.admin.components.EditorControls
import com.tafakkur.blogweb.pages.admin.components.ThumbnailUploader
import com.tafakkur.blogweb.repository.PostRepository
import com.tafakkur.blogweb.styles.FormInputStyle
import com.tafakkur.blogweb.util.*
import com.tafakkur.blogweb.util.Constants.FONT_FAMILY
import com.tafakkur.blogweb.util.Constants.SIDE_PANEL_WIDTH
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.foundation.layout.*
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.forms.Switch
import com.varabyte.kobweb.silk.components.forms.SwitchSize
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import kotlinx.browser.document
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.*
import org.koin.core.Koin
import org.koin.core.context.GlobalContext.get
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLTextAreaElement

data class CreatePageUiState(
    var id: Long = 0,
    var title: String = "",
    var subtitle: String = "",
    var thumbnail: String = "",
    var thumbnailUrl: String = "",
    var thumbnailName: String = "",
    var thumbnailInputDisable: Boolean = true,
    var content: String = "",
    var category: Category = Category.Programming,
    var tags: MutableList<String> = mutableListOf(),
    var status: Status = Status.DRAFT,
    var buttonText: String = "Create",
    var popular: Boolean = false,
    var main: Boolean = false,
    var sponsored: Boolean = false,
    var editorVisibility: Boolean = true,
    var messagePopup: Boolean = false,
    var linkPopup: Boolean = false,
    var imagePopup: Boolean = false
) {
    fun reset() = this.copy(
        id = 0,
        title = "",
        subtitle = "",
        thumbnail = "",
        thumbnailName = "",
        content = "",
        category = Category.Programming,
        tags = mutableListOf(),
        status = Status.DRAFT,
        buttonText = "Create",
        main = false,
        popular = false,
        sponsored = false,
        editorVisibility = true,
        messagePopup = false,
        linkPopup = false,
        imagePopup = false
    )
}

@Page
@Composable
fun CreatePage() {
    isUserLoggedIn {
        CreateScreen()
    }

}

@Composable
fun CreateScreen() {
    var uiState by remember { mutableStateOf(CreatePageUiState()) }
    val breakpoint = rememberBreakpoint()
    val scope = rememberCoroutineScope()
    val context = rememberPageContext()

    val hasPostIdParam = remember(key1 = context.route) {
        context.route.params.containsKey(POST_ID_PARAM)
    }

    val inject: Koin = get()
    val repository = inject.get<PostRepository>()

    LaunchedEffect(hasPostIdParam) {
        if (hasPostIdParam) {
            val postId = context.route.params[POST_ID_PARAM] ?: ""
            when (val result = repository.getPostDetail(id = postId.toLong())) {
                is ApiResponse.Success -> {
                    val response = result.data.data
                    (document.getElementById(Id.editor) as HTMLTextAreaElement).value = response.content
                    uiState = uiState.copy(
                        id = response.id,
                        title = response.title,
                        main = response.main,
                        popular = response.popular,
                        sponsored = response.sponsored,
                        subtitle = response.subtitle,
                        content = response.content,
                        category = Category.valueOf(response.category),
                        buttonText = "Update",
                    )
                }

                is ApiResponse.Error -> {
                    (document.getElementById(Id.editor) as HTMLTextAreaElement).value = ""
                    uiState = uiState.reset()
                }

                is ApiResponse.Loading -> {

                }
            }
        }
    }

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
                            checked = uiState.popular,
                            onCheckedChange = {
                                uiState = uiState.copy(popular = it)
                            },
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
                            checked = uiState.main,
                            onCheckedChange = {
                                uiState = uiState.copy(main = it)
                            },
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
                            checked = uiState.sponsored,
                            onCheckedChange = {
                                uiState = uiState.copy(sponsored = it)
                            },
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
                            attr("value", uiState.title)
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
                            attr("value", uiState.subtitle)
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
                            uiState = uiState.copy(thumbnailInputDisable = !it)
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
                        uiState = uiState.copy(thumbnail = file, thumbnailName = filename)
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
                        uiState =
                            uiState.copy(title = (document.getElementById(Id.titleInput) as HTMLInputElement).value)
                        uiState =
                            uiState.copy(subtitle = (document.getElementById(Id.subtitleInput) as HTMLInputElement).value)
                        uiState =
                            uiState.copy(content = (document.getElementById(Id.editor) as HTMLTextAreaElement).value)
                        uiState = uiState.copy(tags = mutableListOf(uiState.category.name))
                        uiState = uiState.copy(status = Status.DRAFT)
                        if (!uiState.thumbnailInputDisable) {
                            uiState =
                                uiState.copy(thumbnailUrl = (document.getElementById(Id.thumbnailInput) as HTMLInputElement).value)
                        }
                        if (
                            uiState.title.isNotEmpty() &&
                            uiState.subtitle.isNotEmpty() &&
                            uiState.content.isNotEmpty()
                        ) {
                            scope.launch {
                                if (hasPostIdParam) {
                                    val result = repository.updatePost(
                                        id = uiState.id,
                                        PostRequest(
                                            title = uiState.title,
                                            subtitle = uiState.subtitle,
                                            content = uiState.content,
                                            category = uiState.category.name,
                                            tags = uiState.tags,
                                            thumbnailName = uiState.thumbnailName,
                                            thumbnailLinkUrl = uiState.thumbnailUrl,
                                            main = uiState.main,
                                            popular = uiState.popular,
                                            sponsored = uiState.sponsored,
                                            status = uiState.status.name
                                        ),
                                        if (uiState.thumbnail.isNotEmpty()) {
                                            base64ToByteArray(uiState.thumbnail)
                                        } else {
                                            null
                                        }
                                    )
                                    when (result) {
                                        is ApiResponse.Success -> {
                                            context.router.navigateTo(Screen.AdminSuccess.postUpdated())
                                        }

                                        is ApiResponse.Error -> {
                                            println("Error: ${result.message}")
                                        }

                                        is ApiResponse.Loading -> {
                                            println("Loading...")
                                        }
                                    }
                                } else {
                                    val result = repository.createPost(
                                        PostRequest(
                                            title = uiState.title,
                                            subtitle = uiState.subtitle,
                                            content = uiState.content,
                                            category = uiState.category.name,
                                            tags = uiState.tags,
                                            thumbnailName = uiState.thumbnailName,
                                            thumbnailLinkUrl = uiState.thumbnailUrl,
                                            main = uiState.main,
                                            popular = uiState.popular,
                                            sponsored = uiState.sponsored,
                                            status = uiState.status.name
                                        ),
                                        if (uiState.thumbnail.isNotEmpty()) {
                                            base64ToByteArray(uiState.thumbnail)
                                        } else {
                                            null
                                        }
                                    )

                                    when (result) {
                                        is ApiResponse.Success -> {
                                            context.router.navigateTo(Screen.AdminSuccess.route)
                                        }

                                        is ApiResponse.Error -> {
                                            println("Error: ${result.message}")
                                        }

                                        is ApiResponse.Loading -> {
                                            println("Loading...")
                                        }
                                    }
                                }
                            }
                        } else {
                            scope.launch {
                                uiState = uiState.copy(messagePopup = true)
                                delay(2000)
                                uiState = uiState.copy(messagePopup = false)
                            }
                        }
                    }
                )
            }
        }

    }

    if (uiState.messagePopup) {
        MessagePopup(
            message = "Please fill out all fields.",
            onDialogDismiss = {
                uiState = uiState.copy(messagePopup = false)
            }
        )
    }

    if (uiState.linkPopup) {
        ControlPopup(
            editorControl = EditorControl.Link,
            onDialogDismiss = {
                uiState = uiState.copy(linkPopup = false)
            },
            onAddClick = { href, title ->
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

    if (uiState.imagePopup) {
        ControlPopup(
            editorControl = EditorControl.Image,
            onDialogDismiss = {
                uiState = uiState.copy(imagePopup = false)
            },
            onAddClick = { imageUrl, description ->
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
            .cursor(Cursor.Pointer)
            .toAttrs()
    ) {
        SpanText(text = text)
    }
}