package com.tafakkur.blogweb.sections

import androidx.compose.runtime.Composable
import com.tafakkur.blogweb.components.PostPreview
import com.tafakkur.blogweb.dto.PostResponse
import com.tafakkur.blogweb.models.PostWithoutDetails
import com.tafakkur.blogweb.util.Constants.FONT_FAMILY
import com.tafakkur.blogweb.util.Constants.PAGE_WIDTH
import com.tafakkur.blogweb.util.JsTheme
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.icons.fa.FaTag
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

@Composable
fun SponsoredPostsSection(
    breakpoint: Breakpoint,
    posts: List<PostResponse>,
    onClick: (Long)->Unit
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .margin(bottom = 100.px)
            .backgroundColor(JsTheme.LightGray.rgb),
        contentAlignment = Alignment.Center

    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .maxWidth(PAGE_WIDTH.px)
                .margin(topBottom = 50.px),
            contentAlignment = Alignment.TopCenter
        ) {
            SponsoredPosts(
                breakpoint = breakpoint,
                posts = posts,
                onClick = onClick
            )
        }
    }
}

@Composable
fun SponsoredPosts(
    breakpoint: Breakpoint,
    posts: List<PostResponse>,
    onClick: (Long) -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxWidth(
                if (breakpoint > Breakpoint.MD) 80.percent
                else 90.percent
            ),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .margin(bottom = 30.px),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FaTag(
                modifier = Modifier
                    .margin(right = 10.px)
                    .color(JsTheme.Sponsored.rgb),
                size = IconSize.XL
            )

            SpanText(
                modifier = Modifier
                    .fontFamily(FONT_FAMILY)
                    .fontSize(18.px)
                    .fontWeight(FontWeight.Medium)
                    .color(JsTheme.Sponsored.rgb),
                text = "Sponsored Posts"
            )
        }

        SimpleGrid(
            modifier = Modifier
                .fillMaxWidth(),
            numColumns = numColumns(base = 1, xl = 2)
        ){
            posts.forEach { post ->
                PostPreview(
                    modifier = Modifier
                        .margin(right = 50.px),
                    post = post,
                    vertical = breakpoint < Breakpoint.MD,
                    titleMaxLines = 1,
                    titleColor = JsTheme.Sponsored.rgb,
                    thumbnailHeight = if (breakpoint >= Breakpoint.MD) 200.px else 300.px,
                    onClick = onClick
                )
            }
        }
    }
}