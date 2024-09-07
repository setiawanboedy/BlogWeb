package com.tafakkur.blogweb.pages

import androidx.compose.runtime.*
import com.tafakkur.blogweb.components.CategoryNavigationItems
import com.tafakkur.blogweb.components.OverflowSidePanel
import com.tafakkur.blogweb.dto.PostResponse
import com.tafakkur.blogweb.models.Category
import com.tafakkur.blogweb.models.PostWithoutDetails
import com.tafakkur.blogweb.navigation.Screen
import com.tafakkur.blogweb.sections.*
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint

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

    LaunchedEffect(Unit) {
        mainPosts.addAll(
            listOf(
                PostResponse(
                    id = 1,
                    author = "Budi",
                    title = "Kotlin backend",
                    slug = "kotlin-backend",
                    subtitle = "Kotlin",
                    thumbnailImageUrl = "https://i.pinimg.com/736x/3b/fd/25/3bfd258e53765d880b01881eccb2c932.jpg",
                    status = "PUBLISHED",
                    category = Category.Programming.name,
                    tags = mutableListOf(),
                    popular = true,
                    main = false,
                    sponsored = false,
                    createdAt = "2024-09-05"
                )
            )
        )
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

        MainSection(
            breakpoint = breakpoint,
            posts = mainPosts,
            onClick = { context.router.navigateTo(Screen.PostPage.getPosts(id = it)) }
        )

        PostSection(
            breakpoint = breakpoint,
            posts = mainPosts,
            title = "Latest Posts",
            showMoreVisibility = showMoreLatest,
            onShowMore = {},
            onClick = { context.router.navigateTo(Screen.PostPage.getPosts(id = it)) }
        )

        SponsoredPostsSection(
            breakpoint = breakpoint,
            posts = mainPosts,
            onClick = { context.router.navigateTo(Screen.PostPage.getPosts(id = it)) }
        )

        PostSection(
            breakpoint = breakpoint,
            posts = mainPosts,
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