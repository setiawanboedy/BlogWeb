package com.tafakkur.blogweb.pages

import androidx.compose.runtime.*
import com.tafakkur.blogweb.components.CategoryNavigationItems
import com.tafakkur.blogweb.components.OverflowSidePanel
import com.tafakkur.blogweb.core.sealed.ApiResponse
import com.tafakkur.blogweb.dto.PostResponse
import com.tafakkur.blogweb.models.Constants.POSTS_PER_PAGE
import com.tafakkur.blogweb.navigation.Screen
import com.tafakkur.blogweb.repository.PostRepository
import com.tafakkur.blogweb.sections.*
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import kotlinx.coroutines.launch
import org.koin.core.Koin
import org.koin.core.context.GlobalContext.get

@Page
@Composable
fun HomePage() {
    val context = rememberPageContext()
    val scope = rememberCoroutineScope()

    val breakpoint = rememberBreakpoint()
    var overflowOpened by remember { mutableStateOf(false) }
    val mainPosts = remember { mutableStateListOf<PostResponse>() }
    val latestPosts = remember { mutableStateListOf<PostResponse>() }
    val sponsoredPosts = remember { mutableStateListOf<PostResponse>() }
    val popularPosts = remember { mutableStateListOf<PostResponse>() }
    var latestPostsToSkip by remember { mutableStateOf(0) }
    var popularPostsToSkip by remember { mutableStateOf(0) }
    var showMoreLatest by remember { mutableStateOf(false) }
    var showMorePopular by remember { mutableStateOf(false) }

    val inject: Koin = get()
    val repository = inject.get<PostRepository>()

    LaunchedEffect(context.route) {
        scope.launch {
            //  main
            when(val result = repository.getAllPosts(mutableMapOf("main" to true))){
                is ApiResponse.Success -> {
                    if (result.data.data.isNotEmpty()){
                        mainPosts.clear()
                        mainPosts.addAll(result.data.data)
                    }
                }
                is ApiResponse.Error -> {
                    println(result.message)
                }
                is ApiResponse.Loading -> {}
            }

            //  latestPost
            when(val result = repository.getAllPosts(mutableMapOf("page" to 0, "size" to POSTS_PER_PAGE))){
                is ApiResponse.Success -> {
                    if (result.data.data.isNotEmpty()){
                        latestPosts.clear()
                        latestPosts.addAll(result.data.data)
                    }
                }
                is ApiResponse.Error -> {
                    println(result.message)
                }
                is ApiResponse.Loading -> {}
            }

            //  popular
            when(val result = repository.getAllPosts(mutableMapOf("popular" to true))){
                is ApiResponse.Success -> {
                    if (result.data.data.isNotEmpty()){
                        popularPosts.clear()
                        popularPosts.addAll(result.data.data)
                    }
                }
                is ApiResponse.Error -> {
                    println(result.message)
                }
                is ApiResponse.Loading -> {}
            }

            //  sponsored
            when(val result = repository.getAllPosts(mutableMapOf("sponsored" to true))){
                is ApiResponse.Success -> {
                    if (result.data.data.isNotEmpty()){
                        sponsoredPosts.clear()
                        sponsoredPosts.addAll(result.data.data)
                    }
                }
                is ApiResponse.Error -> {
                    println(result.message)
                }
                is ApiResponse.Loading -> {}
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (overflowOpened) {
            OverflowSidePanel(
                onMenuClose = { overflowOpened = false },
                content = { CategoryNavigationItems(vertical = true) }
            )
        }
        HeaderSection(
            breakpoint = breakpoint,
            onMenuOpen = { overflowOpened = true }
        )

        if (mainPosts.isNotEmpty()){
            MainSection(
                breakpoint = breakpoint,
                posts = mainPosts,
                onClick = { context.router.navigateTo(Screen.PostPage.getPosts(id = it)) }
            )
        }

        PostSection(
            breakpoint = breakpoint,
            posts = latestPosts,
            title = "Latest Posts",
            showMoreVisibility = showMoreLatest,
            onShowMore = {},
            onClick = { context.router.navigateTo(Screen.PostPage.getPosts(id = it)) }
        )

        SponsoredPostsSection(
            breakpoint = breakpoint,
            posts = sponsoredPosts,
            onClick = { context.router.navigateTo(Screen.PostPage.getPosts(id = it)) }
        )

        PostSection(
            breakpoint = breakpoint,
            posts = popularPosts,
            title = "Popular Posts",
            showMoreVisibility = showMorePopular,
            onShowMore = {},
            onClick = { context.router.navigateTo(Screen.PostPage.getPosts(id = it)) }
        )

        NewsletterSection(
            breakpoint = breakpoint
        )
        FooterSection()
    }
}