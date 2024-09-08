package com.tafakkur.blogweb.components

import androidx.compose.runtime.*
import com.tafakkur.blogweb.dto.PostResponse
import com.tafakkur.blogweb.models.Category
import com.tafakkur.blogweb.styles.MainPostPreviewStyle
import com.tafakkur.blogweb.styles.PostPreviewStyle
import com.tafakkur.blogweb.util.Constants.FONT_FAMILY
import com.tafakkur.blogweb.util.JsTheme
import com.tafakkur.blogweb.util.checkUrlThumbnailImage
import com.tafakkur.blogweb.util.toFormattedDateTime
import com.varabyte.kobweb.compose.css.*
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.*
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.CheckboxInput

@Composable
fun PostPreview(
    modifier: Modifier = Modifier,
    post: PostResponse,
    selectableMode: Boolean = false,
    darkTheme: Boolean = false,
    vertical: Boolean = true,
    thumbnailHeight: CSSSizeValue<CSSUnit.px> = 320.px,
    titleMaxLines: Int = 2,
    titleColor: CSSColorValue = Colors.Black,
    onSelect: (Long) -> Unit = {},
    onDeselect: (Long) -> Unit = {},
    onClick: (Long) -> Unit
) {
    var checked by remember(selectableMode) { mutableStateOf(false) }
    if (vertical) {
        Column(
            modifier = Modifier
                .thenIf(
                    condition = post.main,
                    other = MainPostPreviewStyle.toModifier()
                )
                .thenIf(
                    condition = !post.main,
                    other = PostPreviewStyle.toModifier()
                )
                .then(modifier)
                .fillMaxWidth(
                    if (darkTheme) 100.percent
                    else if (titleColor == JsTheme.Sponsored.rgb) 100.percent
                    else 95.percent
                )
                .margin(bottom = 24.px)
                .padding(all =
                if (selectableMode) 10.px else 0.px)
                .borderRadius(r = 4.px)
                .border(
                    width = if (selectableMode) 4.px else 0.px,
                    style = if (selectableMode) LineStyle.Solid else LineStyle.None,
                    color = if (checked) JsTheme.Primary.rgb else JsTheme.Gray.rgb
                )
                .onClick {
                    if (selectableMode){
                        checked = !checked
                        if (checked){
                            onSelect(post.id)
                        }else{
                            onDeselect(post.id)
                        }
                    }else{
                        onClick(post.id)
                    }
                }
                .transition(CSSTransition(property = TransitionProperty.All, duration = 200.ms))
                .cursor(Cursor.Pointer)
        ) {
            PostContent(
                post = post,
                selectableMode = selectableMode,
                darkTheme = darkTheme,
                vertical = vertical,
                thumbnailHeight = thumbnailHeight,
                titleMaxLines = titleMaxLines,
                titleColor = titleColor,
                checked = checked,
            )
        }
    } else {
        Row(
            modifier = Modifier
                .thenIf(
                    condition = post.main,
                    other = MainPostPreviewStyle.toModifier()
                )
                .thenIf(
                    condition = !post.main,
                    other = PostPreviewStyle.toModifier()
                )
                .then(modifier)
                .height(thumbnailHeight)
                .onClick { onClick(post.id) }
                .cursor(Cursor.Pointer)
        ) {
        PostContent(
            post = post,
            selectableMode = selectableMode,
            darkTheme = darkTheme,
            vertical = vertical,
            thumbnailHeight = thumbnailHeight,
            titleMaxLines = titleMaxLines,
            titleColor = titleColor,
            checked = checked
        )
        }
    }
}

@Composable
fun PostContent(
    post: PostResponse,
    selectableMode: Boolean,
    darkTheme: Boolean,
    vertical: Boolean,
    thumbnailHeight: CSSSizeValue<CSSUnit.px>,
    titleMaxLines: Int,
    titleColor: CSSColorValue,
    checked: Boolean
) {
    Image(
        modifier = Modifier
            .margin(bottom = if (darkTheme) 20.px else 16.px)
            .height(size = thumbnailHeight)
            .fillMaxWidth()
            .objectFit(ObjectFit.Cover),
        src = checkUrlThumbnailImage(post.thumbnailImageUrl),
        alt = "Post Thumbnail Image"
    )

    Column(
        modifier = Modifier
            .thenIf(
                condition = !vertical,
                other = Modifier.margin(left = 20.px)
            )
            .padding(all = 12.px)
            .fillMaxWidth()
    ) {
        SpanText(
            modifier = Modifier
                .fontFamily(FONT_FAMILY)
                .fontSize(12.px)
                .color(
                    if (darkTheme) JsTheme.HalfWhite.rgb else JsTheme.HalfBlack.rgb
                ),
            text = post.createdAt.toFormattedDateTime()
        )
        SpanText(
            modifier = Modifier
                .margin(bottom = 12.px)
                .fontFamily(FONT_FAMILY)
                .fontSize(20.px)
                .fontWeight(FontWeight.Bold)
                .color(
                    if (darkTheme) Colors.White else titleColor
                )
                .textOverflow(TextOverflow.Ellipsis)
                .overflow(Overflow.Hidden)
                .styleModifier {
                    property("display", "-webkit-box")
                    property("-webkit-line-clamp", "$titleMaxLines")
                    property("line-clamp", "$titleMaxLines")
                    property("-webkit-box-orient", "vertical")
                },
            text = post.title
        )
        SpanText(
            modifier = Modifier
                .margin(bottom = 8.px)
                .fontFamily(FONT_FAMILY)
                .fontSize(16.px)
                .color(if (darkTheme) Colors.White else Colors.Black)
                .textOverflow(TextOverflow.Ellipsis)
                .overflow(Overflow.Hidden)
                .styleModifier {
                    property("display", "-webkit-box")
                    property("-webkit-line-clamp", "3")
                    property("line-clamp", "3")
                    property("-webkit-box-orient", "vertical")
                },
            text = post.subtitle
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CategoryChip(
                category = Category.valueOf(post.category), darkTheme = darkTheme
            )
            if (selectableMode) {
                CheckboxInput(
                    checked = checked,
                    attrs = Modifier.size(20.px)
                        .toAttrs()
                )
            }
        }
    }
}