package com.tafakkur.blogweb.components

import androidx.compose.runtime.Composable
import com.tafakkur.blogweb.dto.PostResponse
import com.tafakkur.blogweb.models.PostWithoutDetails
import com.tafakkur.blogweb.util.Constants.FONT_FAMILY
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.Visibility
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

@Composable
fun PostView(
    breakpoint: Breakpoint,
    posts: List<PostResponse>,
    title: String? = null,
    selectableMode: Boolean = false,
    onSelect: (Long) -> Unit = {},
    onDeselect: (Long) -> Unit = {},
    showMoreVisibility: Boolean,
    onShowMore: () -> Unit,
    onClick: (Long) -> Unit
){
    Column(
        modifier = Modifier.fillMaxWidth(
            if (breakpoint > Breakpoint.MD) 80.percent
            else 90.percent
        ),
        verticalArrangement = Arrangement.Center
    ) {
        if (title != null){
            SpanText(
                modifier = Modifier
                    .margin(bottom = 24.px)
                    .fontFamily(FONT_FAMILY)
                    .fontSize(18.px)
                    .fontWeight(FontWeight.Medium),
                text = title
            )
        }

        SimpleGrid(
            modifier = Modifier.fillMaxWidth(),
            numColumns = numColumns(base = 1, sm = 2, md = 3, lg = 4)
        ){
            posts.forEach {
                PostPreview(
                    post = it,
                    selectableMode = selectableMode,
                    onSelect = onSelect,
                    onDeselect = onDeselect,
                    onClick = onClick
                )
            }
        }
        SpanText(
            modifier = Modifier
                .fillMaxWidth()
                .margin(topBottom = 50.px)
                .textAlign(TextAlign.Center)
                .fontFamily(FONT_FAMILY)
                .fontSize(16.px)
                .fontWeight(FontWeight.Medium)
                .cursor(Cursor.Pointer)
                .visibility(
                    if (showMoreVisibility) Visibility.Visible else Visibility.Hidden
                )
                .onClick { onShowMore() },
            text = "Show more"
        )
    }
}