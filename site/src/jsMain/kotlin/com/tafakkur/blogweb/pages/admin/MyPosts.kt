package com.tafakkur.blogweb.pages.admin

import androidx.compose.runtime.*
import com.tafakkur.blogweb.components.AdminPageLayout
import com.tafakkur.blogweb.components.PostView
import com.tafakkur.blogweb.components.SearchBar
import com.tafakkur.blogweb.core.sealed.ApiResponse
import com.tafakkur.blogweb.dto.PostResponse
import com.tafakkur.blogweb.models.PostWithoutDetails
import com.tafakkur.blogweb.navigation.Screen
import com.tafakkur.blogweb.repository.PostRepository
import com.tafakkur.blogweb.util.Constants.FONT_FAMILY
import com.tafakkur.blogweb.util.Constants.SIDE_PANEL_WIDTH
import com.tafakkur.blogweb.util.Id
import com.tafakkur.blogweb.util.JsTheme
import com.tafakkur.blogweb.util.isUserLoggedIn
import com.tafakkur.blogweb.util.parseSwitchText
import com.varabyte.kobweb.compose.css.CSSTransition
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TransitionProperty
import com.varabyte.kobweb.compose.css.Visibility
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.forms.Switch
import com.varabyte.kobweb.silk.components.forms.SwitchSize
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import kotlinx.browser.document
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button
import org.koin.core.Koin
import org.koin.core.context.GlobalContext.get
import org.w3c.dom.HTMLInputElement

@Page
@Composable
fun MyPostsPage(){
    isUserLoggedIn{
        MyPostsScreen()
    }
}

@Composable
fun MyPostsScreen() {
    val context = rememberPageContext()
    val breakpoint = rememberBreakpoint()
    val scope = rememberCoroutineScope()
    val myPosts = remember { mutableStateListOf<PostResponse>() }
    var selectableMode by remember { mutableStateOf(false) }
    var switchText by remember { mutableStateOf("Select") }
    var showMoreVisibility by remember { mutableStateOf(false) }
    val selectedPosts = remember { mutableStateListOf<Long>() }

    val inject: Koin = get()
    val repository = inject.get<PostRepository>()

    LaunchedEffect(context.route){
        val filter: MutableMap<String, Any> = mutableMapOf("page" to 0, "size" to 1)
        scope.launch {
           val result = repository.getAllPosts(filter)

            when(result){
                is ApiResponse.Success -> {
                    myPosts.clear()
                    myPosts.addAll(result.data.data)
                }
                is ApiResponse.Error -> {

                }
                is ApiResponse.Loading -> {

                }
            }
        }
    }

    AdminPageLayout {
        Column(
            modifier = Modifier
                .margin(topBottom = 50.px)
                .fillMaxSize()
                .padding(left = if (breakpoint > Breakpoint.MD) SIDE_PANEL_WIDTH.px else 0.px),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(
                    if (breakpoint > Breakpoint.MD) 30.percent
                    else 50.percent
                )
                    .margin(bottom = 24.px),
                contentAlignment = Alignment.Center
            ) {
                SearchBar(
                    breakpoint = breakpoint,
                    modifier = Modifier.visibility(
                        if (selectableMode) Visibility.Hidden else Visibility.Visible
                    )
                        .transition(CSSTransition(property = TransitionProperty.All, duration = 200.ms)),
                    onSearchIconClick = {
                        val searchInput = (document.getElementById(Id.adminSearchBar) as HTMLInputElement).value
                        if (searchInput.isNotEmpty()) {
                            context.router.navigateTo(Screen.AdminMyPosts.searchByTitle(query = searchInput))
                        } else {
                            context.router.navigateTo(Screen.AdminMyPosts.route)
                        }
                    },
                    onEnterClick = {}
                )

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(
                        if (breakpoint > Breakpoint.MD) 80.percent
                        else 90.percent
                    )
                    .margin(bottom = 24.px),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Switch(
                        modifier = Modifier
                            .margin(right = 8.px),
                        size = SwitchSize.LG,
                        checked = selectableMode,
                        onCheckedChange = {
                            selectableMode = it
                            if (!selectableMode) {
                                switchText = "Select"
                                selectedPosts.clear()
                            } else {
                                switchText = "0 Posts Selected"
                            }
                        }
                    )
                    SpanText(
                        modifier = Modifier
                            .color(
                                if (selectableMode) Colors.Black else JsTheme.HalfBlack.rgb
                            ),
                        text = switchText
                    )
                }

                Button(
                    attrs = Modifier
                        .margin(right = 20.px)
                        .height(54.px)
                        .padding(leftRight = 24.px)
                        .backgroundColor(JsTheme.Red.rgb)
                        .color(Colors.White)
                        .border(0.px)
                        .borderRadius(r = 4.px)
                        .fontFamily(FONT_FAMILY)
                        .fontSize(14.px)
                        .fontWeight(FontWeight.Medium)
                        .visibility(
                            if (selectedPosts.isNotEmpty()) Visibility.Visible else Visibility.Hidden
                        )
                        .onClick {  }
                        .toAttrs()
                ){
                    SpanText(text = "Delete")
                }
            }
            PostView(
                breakpoint = breakpoint,
                posts =  myPosts,
                selectableMode = selectableMode,
                onSelect = {
                    selectedPosts.add(it)
                    switchText = parseSwitchText(selectedPosts.toList())
                },
                onDeselect = {
                    selectedPosts.remove(it)
                    switchText = parseSwitchText(selectedPosts.toList())
                },
                showMoreVisibility = showMoreVisibility,
                onShowMore = {

                },
                onClick = {}
            )
        }
    }
}