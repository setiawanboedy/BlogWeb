package com.tafakkur.blogweb.sections

import androidx.compose.runtime.Composable
import com.tafakkur.blogweb.components.PostPreview
import com.tafakkur.blogweb.models.PostWithoutDetails
import com.tafakkur.blogweb.util.Constants.PAGE_WIDTH
import com.tafakkur.blogweb.util.JsTheme
import com.varabyte.kobweb.compose.foundation.layout.Box
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
//    posts:
    onClick: (Long) -> Unit
){
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
                onClick = onClick
            )
        }
    }
}

@Composable
fun MainPosts(
    breakpoint: Breakpoint,
    onClick: (Long) -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth(
                if (breakpoint > Breakpoint.MD) 80.percent
                else 90.percent
            )
            .margin(topBottom = 50.px)
    ) {
        if (breakpoint == Breakpoint.XL){
            PostPreview(
                post = mutableListOf<PostWithoutDetails>().first(),
                darkTheme = true,
                thumbnailHeight = 640.px,
                onClick = {onClick(1)}
            )

        }
    }
}