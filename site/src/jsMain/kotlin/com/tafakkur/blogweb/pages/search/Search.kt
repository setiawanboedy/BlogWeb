package com.tafakkur.blogweb.pages.search

import androidx.compose.runtime.*
import com.tafakkur.blogweb.components.CategoryNavigationItems
import com.tafakkur.blogweb.components.LoadingIndicator
import com.tafakkur.blogweb.components.OverflowSidePanel
import com.tafakkur.blogweb.core.sealed.ApiResponse
import com.tafakkur.blogweb.dto.PostResponse
import com.tafakkur.blogweb.models.Category
import com.tafakkur.blogweb.models.Constants.CATEGORY_PARAM
import com.tafakkur.blogweb.models.Constants.PAGE
import com.tafakkur.blogweb.models.Constants.POSTS_PER_PAGE
import com.tafakkur.blogweb.navigation.Screen
import com.tafakkur.blogweb.repository.PostRepository
import com.tafakkur.blogweb.sections.FooterSection
import com.tafakkur.blogweb.sections.HeaderSection
import com.tafakkur.blogweb.sections.PostSection
import com.tafakkur.blogweb.util.Constants.FONT_FAMILY
import com.tafakkur.blogweb.util.Id
import com.tafakkur.blogweb.util.Res
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import kotlinx.browser.document
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh
import org.koin.core.Koin
import org.koin.core.context.GlobalContext.get
import org.w3c.dom.HTMLInputElement

@Page(routeOverride = "query")
@Composable
fun SearchPostPage(){
    val breakpoint = rememberBreakpoint()
    val context = rememberPageContext()
    val scope = rememberCoroutineScope()

    var overflowOpened by remember { mutableStateOf(false) }
    val searchPosts = remember { mutableStateListOf<PostResponse>() }

    var page by remember { mutableStateOf(0) }
    var showMoreVisibility by remember { mutableStateOf(false) }
    var isSuccess by remember { mutableStateOf(false) }

    val hasCategoryParam = remember(key1 = context.route) {
        context.route.params.containsKey(CATEGORY_PARAM)
    }

    val hasQueryParam = remember(key1 = context.route) {
        context.route.params.containsKey(CATEGORY_PARAM)
    }

    val value = remember(key1 = context.route) {
        if (hasCategoryParam){
            context.route.params.getValue(CATEGORY_PARAM)
        }else if (hasQueryParam){
            context.route.params.getValue(CATEGORY_PARAM)
        }else{
            ""
        }
    }

    val inject: Koin = get()
    val repository = inject.get<PostRepository>()

    LaunchedEffect(key1 = context.route){
        (document.getElementById(Id.adminSearchBar) as HTMLInputElement).value = ""
        showMoreVisibility = false
        page = 0
        if (hasCategoryParam){
            scope.launch {
                val category = kotlin.runCatching { Category.valueOf(value) }.getOrElse { Category.Programming }
                val filter: MutableMap<String, Any> = mutableMapOf("category" to category)
                when (val result = repository.getAllFrontPosts(filter)) {
                    is ApiResponse.Success -> {
                        isSuccess = true
                        searchPosts.clear()
                        searchPosts.addAll(result.data.data)
                        page += PAGE
                        showMoreVisibility = result.data.data.size >= POSTS_PER_PAGE
                    }
                    is ApiResponse.Error -> {}
                    is ApiResponse.Loading -> {}
                }
            }
        }else if (hasQueryParam){
            (document.getElementById(Id.adminSearchBar) as HTMLInputElement).value = value
            val filter: MutableMap<String, Any> = mutableMapOf("title" to value)
            when (val result = repository.getAllFrontPosts(filter)) {
                is ApiResponse.Success -> {
                    isSuccess = true
                    searchPosts.clear()
                    searchPosts.addAll(result.data.data)
                    page += PAGE
                    showMoreVisibility = result.data.data.size >= POSTS_PER_PAGE
                }
                is ApiResponse.Error -> {}
                is ApiResponse.Loading -> {}
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (overflowOpened){
            OverflowSidePanel(
                onMenuClose = { overflowOpened = false},
                content = {
                    CategoryNavigationItems(
                        selectCategory = if (hasCategoryParam) kotlin.runCatching {
                            Category.valueOf(value)
                        }.getOrElse { Category.Programming } else null,
                        vertical = true
                    )
                }
            )
        }

        HeaderSection(
            breakpoint = breakpoint,
            selectedCategory = if (hasCategoryParam) kotlin.runCatching {
                Category.valueOf(value)
            }.getOrElse { Category.Programming } else null,
            logo = Res.Image.logo,
            onMenuOpen = { overflowOpened = true }
         )

        if (isSuccess){
            if (hasCategoryParam){
                SpanText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .textAlign(TextAlign.Center)
                        .margin(top = 100.px, bottom = 40.px)
                        .fontFamily(FONT_FAMILY)
                        .fontSize(36.px),
                    text = value.ifEmpty { Category.Programming.name }
                )
            }
            if (searchPosts.isNotEmpty()){
                PostSection(
                    breakpoint = breakpoint,
                    posts = searchPosts,
                    showMoreVisibility = showMoreVisibility,
                    onShowMore = {
                        scope.launch {
                            if (hasCategoryParam){
                                val category = kotlin.runCatching { Category.valueOf(value) }.getOrElse { Category.Programming }
                                val filter: MutableMap<String, Any> = mutableMapOf("category" to category)
                                when (val result = repository.getAllFrontPosts(filter)) {
                                    is ApiResponse.Success -> {
                                        if (result.data.data.isNotEmpty()){

                                            searchPosts.addAll(result.data.data)
                                            page += PAGE
                                            showMoreVisibility = result.data.data.size < POSTS_PER_PAGE
                                        }else{
                                            showMoreVisibility = false
                                        }
                                    }
                                    is ApiResponse.Error -> {}
                                    is ApiResponse.Loading -> {}
                                }
                            }else if (hasQueryParam){
                                val filter: MutableMap<String, Any> = mutableMapOf("title" to value)
                                when (val result = repository.getAllFrontPosts(filter)) {
                                    is ApiResponse.Success -> {
                                        if (result.data.data.isNotEmpty()){
                                            searchPosts.addAll(result.data.data)
                                            page += PAGE
                                        }else{
                                            showMoreVisibility = result.data.data.size < POSTS_PER_PAGE
                                        }
                                    }
                                    is ApiResponse.Error -> {}
                                    is ApiResponse.Loading -> {}
                                }
                            }
                        }
                    },
                    onClick = {
                        context.router.navigateTo(Screen.PostPage.getPosts(id = it))
                    }
                )
            }else{
                Box(
                    modifier = Modifier
                        .height(100.vh),
                    contentAlignment = Alignment.Center
                ) {
                    SpanText(
                        modifier = Modifier
                            .fontFamily(FONT_FAMILY)
                            .fontSize(16.px)
                            .fontWeight(FontWeight.Medium),
                        text = "Post not found"
                    )
                }
            }
        }else{
            LoadingIndicator()
        }
        FooterSection()
    }

}