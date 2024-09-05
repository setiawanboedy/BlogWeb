package com.tafakkur.blogweb.components

import androidx.compose.runtime.*
import com.tafakkur.blogweb.util.Constants.PAGE_WIDTH
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import org.jetbrains.compose.web.css.px

@Composable
fun AdminPageLayout(content: @Composable () -> Unit){
    var overflowOpened by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .maxWidth(PAGE_WIDTH.px)
        ) {
            SidePanel(onMenuClick = {
                overflowOpened = true
            })
            if (overflowOpened){
                OverflowSidePanel(
                    onMenuClose = {
                        overflowOpened = false
                    },
                    content = {
                        NavigationItems()
                    }
                )
            }
            content()
        }
    }
}