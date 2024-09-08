package com.tafakkur.blogweb.pages.post

import androidx.compose.runtime.*
import com.tafakkur.blogweb.components.CategoryNavigationItems
import com.tafakkur.blogweb.components.ErrorView
import com.tafakkur.blogweb.components.LoadingIndicator
import com.tafakkur.blogweb.components.OverflowSidePanel
import com.tafakkur.blogweb.core.sealed.ApiResponse
import com.tafakkur.blogweb.dto.PostResponse
import com.tafakkur.blogweb.models.Constants.POST_ID_PARAM
import com.tafakkur.blogweb.models.Constants.SHOW_SECTIONS_PARAM
import com.tafakkur.blogweb.repository.PostRepository
import com.tafakkur.blogweb.sections.FooterSection
import com.tafakkur.blogweb.sections.HeaderSection
import com.tafakkur.blogweb.util.*
import com.tafakkur.blogweb.util.Constants.FONT_FAMILY
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.ObjectFit
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.TextOverflow
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import kotlinx.browser.document
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.koin.core.Koin
import org.koin.core.context.GlobalContext.get
import org.w3c.dom.HTMLDivElement

@Page
@Composable
fun PostDetailPage(){
    val scope = rememberCoroutineScope()

    val context = rememberPageContext()
    val breakpoint = rememberBreakpoint()
    var overflowOpened by remember { mutableStateOf(false) }
    var showSections by remember { mutableStateOf(true) }
    var postResponse by remember { mutableStateOf(PostResponse()) }
    var isSuccess by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }
    val hasPostIdParam = remember(key1 = context.route){
        context.route.params.containsKey(POST_ID_PARAM)
    }

    val inject: Koin = get()
    val repository = inject.get<PostRepository>()

    LaunchedEffect(key1 = context.route){
        showSections = if (context.route.params.containsKey(SHOW_SECTIONS_PARAM)){
            context.route.params.getValue(SHOW_SECTIONS_PARAM).toBoolean()
        }else true
        if (hasPostIdParam){
            val postId = context.route.params.getValue(POST_ID_PARAM).toLong()
            scope.launch {
                when(val result = repository.getFrontPostDetail(id = postId)){
                    is ApiResponse.Success -> {
                        postResponse = result.data.data
                        isSuccess = true
                        isLoading = false
                    }
                    is ApiResponse.Error -> {
                        isError = true
                        isLoading = false
                    }
                    is ApiResponse.Loading -> {
                        isLoading = true
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (overflowOpened){
            OverflowSidePanel(
                onMenuClose = { overflowOpened = false},
                content = { CategoryNavigationItems(vertical = true) }
            )
        }
        if (showSections){
            HeaderSection(
                breakpoint = breakpoint,
                logo = Res.Image.logo,
                onMenuOpen = { overflowOpened = false}
            )
        }

        if (isSuccess){
            PostContent(
                post = postResponse,
                breakpoint = breakpoint
            )

            scope.launch {
                delay(50)
                try {
                    js("hljs.highlightAll()") as Unit

                }catch (e: Exception){
                    println(e.message)
                }
            }
        }

        if (isLoading){
            LoadingIndicator()
        }

        if (isError){
            ErrorView(message = "Something wrong")
        }

        if (showSections){
            FooterSection()
        }
    }
}

@Composable
fun PostContent(
    post: PostResponse,
    breakpoint: Breakpoint
){
    LaunchedEffect(post){
        (document.getElementById(Id.postContent) as HTMLDivElement).innerHTML = post.content
    }
    Column(
        modifier = Modifier
            .margin(top = 50.px, bottom = 200.px)
            .padding(leftRight = 24.px)
            .fillMaxWidth()
            .maxWidth(800.px),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SpanText(
            modifier = Modifier
                .fillMaxWidth()
                .color(JsTheme.HalfBlack.rgb)
                .fontFamily(FONT_FAMILY)
                .fontSize(14.px),
            text = post.createdAt.toFormattedDateTime()
        )
        SpanText(
            modifier = Modifier
                .fillMaxWidth()
                .margin(bottom = 20.px)
                .color(Colors.Black)
                .fontFamily(FONT_FAMILY)
                .fontSize(40.px)
                .fontWeight(FontWeight.Bold)
                .overflow(Overflow.Hidden)
                .textOverflow(TextOverflow.Ellipsis)
                .styleModifier {
                    property("display", "-webkit-box")
                    property("-webkit-line-clamp", "2")
                    property("line-clamp", "2")
                    property("-webkit-box-orient", "vertical")
                },
            text = post.title
        )
        Image(
            modifier = Modifier
                .margin(bottom = 40.px)
                .fillMaxWidth()
                .objectFit(ObjectFit.Cover)
                .height(
                    if (breakpoint <= Breakpoint.SM) 250.px
                    else if (breakpoint <= Breakpoint.MD) 400.px
                    else 400.px
                ),
            src = checkUrlThumbnailImage(post.thumbnailImageUrl)
        )
        Div(
            attrs = Modifier
                .id(Id.postContent)
                .fontFamily(FONT_FAMILY)
                .fillMaxWidth()
                .toAttrs()
        )
    }
}