package com.tafakkur.blogweb.sections

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.tafakkur.blogweb.components.PostPreview
import com.tafakkur.blogweb.dto.PostResponse
import com.tafakkur.blogweb.models.PostWithoutDetails
import com.tafakkur.blogweb.util.Constants.PAGE_WIDTH
import com.tafakkur.blogweb.util.JsTheme
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

@Composable
fun MainSection(
    breakpoint: Breakpoint,
    posts: List<PostResponse>,
    onClick: (Long) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .backgroundColor(JsTheme.Secondary.rgb),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .maxWidth(PAGE_WIDTH.px),
            contentAlignment = Alignment.Center
        ) {
            MainPosts(
                breakpoint = breakpoint,
                posts = posts,
                onClick = onClick
            )
        }
    }
}

@Composable
fun MainPosts(
    breakpoint: Breakpoint,
    posts: List<PostResponse>,
    onClick: (Long) -> Unit
) {
    LaunchedEffect(Unit) {
        println("Logging posts: ${posts.first()}")
    }
    Row(
        modifier = Modifier
            .fillMaxWidth(
                if (breakpoint > Breakpoint.MD) 80.percent
                else 90.percent
            )
            .margin(topBottom = 50.px)
    ) {
        if (posts.isNotEmpty()) {

            if (breakpoint == Breakpoint.XL) {
                PostPreview(
                    post = posts.first(),
                    darkTheme = true,
                    thumbnailHeight = 640.px,
                    onClick = { }
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth(80.percent)
                        .margin(left = 20.px)
                ) {
                    posts.drop(1).forEach { postWithoutDetails ->
                        PostPreview(
                            modifier = Modifier.margin(bottom = 20.px),
                            post = postWithoutDetails,
                            darkTheme = true,
                            vertical = false,
                            thumbnailHeight = 200.px,
                            titleMaxLines = 1,
                            onClick = {
                                onClick(postWithoutDetails.id)
                            }
                        )
                    }
                }
            } else if (breakpoint >= Breakpoint.LG) {
                Box(
                    modifier = Modifier
                        .margin(right = 10.px)
                ) {
                    PostPreview(
                        post = posts.first(),
                        darkTheme = true,
                        onClick = { onClick(posts.first().id) }
                    )
                }
                Box(
                    modifier = Modifier
                        .margin(left = 10.px)
                ) {
                    PostPreview(
                        post = posts[1],
                        darkTheme = true,
                        onClick = { onClick(posts[1].id) }
                    )
                }


            } else {
                PostPreview(
                    post = posts.first(),
                    darkTheme = true,
                    thumbnailHeight = 640.px,
                    onClick = { onClick(posts.first().id) }
                )
            }
        }
    }
}