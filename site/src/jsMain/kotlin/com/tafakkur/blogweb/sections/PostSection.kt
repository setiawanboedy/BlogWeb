package com.tafakkur.blogweb.sections

import androidx.compose.runtime.Composable
import com.tafakkur.blogweb.components.PostView
import com.tafakkur.blogweb.models.PostWithoutDetails
import com.tafakkur.blogweb.util.Constants.PAGE_WIDTH
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import org.jetbrains.compose.web.css.px

@Composable
fun PostSection(
    breakpoint: Breakpoint,
    posts: List<PostWithoutDetails>,
    title: String? = null,
    showMoreVisibility: Boolean,
    onShowMore: ()->Unit,
    onClick: (Long)->Unit,
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .margin(topBottom = 50.px)
            .maxWidth(PAGE_WIDTH.px),
        contentAlignment = Alignment.TopCenter
    ) {
        PostView(
            breakpoint = breakpoint,
            posts = posts,
            title = title,
            showMoreVisibility = showMoreVisibility,
            onShowMore = onShowMore,
            onClick = onClick
        )
    }
}