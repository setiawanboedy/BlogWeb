package com.tafakkur.blogweb.pages.admin

import androidx.compose.runtime.Composable
import com.tafakkur.blogweb.components.AdminPageLayout
import com.tafakkur.blogweb.util.Constants.FONT_FAMILY
import com.tafakkur.blogweb.util.Constants.SIDE_PANEL_WIDTH
import com.tafakkur.blogweb.util.JsTheme
import com.tafakkur.blogweb.util.isUserLoggedIn
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.icons.fa.FaCode
import com.varabyte.kobweb.silk.components.icons.fa.FaLaptop
import com.varabyte.kobweb.silk.components.icons.fa.FaPaintbrush
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import org.jetbrains.compose.web.css.px

@Page
@Composable
fun HomePage() {
    isUserLoggedIn {
        HomeScreen()
    }
}

@Composable
fun HomeScreen() {
    val breakpoint = rememberBreakpoint()
    AdminPageLayout {
        Box(
            modifier = Modifier.fillMaxSize()
                .margin(topBottom = 50.px)
                .padding(left = if (breakpoint > Breakpoint.MD) SIDE_PANEL_WIDTH.px else 0.px),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SimpleGrid(
                    numColumns = numColumns(base = 1, sm = 3)
                ) {
                    Box(
                        modifier = Modifier.margin(leftRight = 24.px, bottom = 12.px)
                    ) {
                        CardStats(
                            title = "100",
                            subtitle = "Technology"
                        ) {
                            FaLaptop(
                                modifier = Modifier
                                    .margin(right = 16.px)
                                    .padding(18.px)
                                    .borderRadius(8.px)
                                    .color(Colors.White)
                                    .backgroundColor(JsTheme.Tech.rgb),
                                size = IconSize.X2
                            )
                        }
                    }
                    Box(
                        modifier = Modifier.margin(leftRight = 24.px, bottom = 12.px)
                    ) {
                        CardStats(
                            title = "54",
                            subtitle = "Programming"
                        ) {
                            FaCode(
                                modifier = Modifier
                                    .margin(right = 16.px)
                                    .padding(18.px)
                                    .borderRadius(8.px)
                                    .color(Colors.White)
                                    .backgroundColor(JsTheme.Program.rgb),
                                size = IconSize.X2
                            )
                        }
                    }
                    Box(
                        modifier = Modifier.margin(leftRight = 24.px, bottom = 12.px)
                    ) {
                        CardStats(
                            title = "32",
                            subtitle = "Design"
                        ) {
                            FaPaintbrush(
                                modifier = Modifier
                                    .margin(right = 16.px)
                                    .padding(18.px)
                                    .borderRadius(8.px)
                                    .color(Colors.White)
                                    .backgroundColor(JsTheme.Design.rgb),
                                size = IconSize.X2
                            )
                        }
                    }
                }
//                Text("THIS PAGE INTENTIONALL")
            }
        }

    }
}

@Composable
fun CardStats(
    title: String,
    subtitle: String,
    icon: @Composable () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.px)
            .borderRadius(8.px)
            .backgroundColor(JsTheme.Card.rgb),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon()
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            SpanText(
                modifier = Modifier
                    .fontFamily(FONT_FAMILY)
                    .fontWeight(FontWeight.Bold)
                    .fontSize(24.px),
                text = title
            )
            SpanText(
                modifier = Modifier
                    .fontFamily(FONT_FAMILY)
                    .color(JsTheme.HalfBlack.rgb),
                text = subtitle
            )
        }
    }
}