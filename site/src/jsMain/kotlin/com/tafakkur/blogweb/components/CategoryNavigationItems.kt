package com.tafakkur.blogweb.components

import androidx.compose.runtime.Composable
import com.tafakkur.blogweb.models.Category
import com.tafakkur.blogweb.navigation.Screen
import com.tafakkur.blogweb.styles.CategoryItemStyle
import com.tafakkur.blogweb.util.Constants.FONT_FAMILY
import com.tafakkur.blogweb.util.JsTheme
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextDecorationLine
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.thenIf
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.px

@Composable
fun CategoryNavigationItems(
    selectCategory: Category? = null,
    vertical: Boolean = false
){
    val context = rememberPageContext()
    Category.entries.forEach { category: Category ->
        Link(
            modifier = CategoryItemStyle.toModifier()
                .thenIf(
                    condition = vertical,
                    other = Modifier.margin(bottom = 24.px)
                )
                .thenIf(
                    condition = !vertical,
                    other = Modifier.margin(right = 24.px)
                )
                .thenIf(
                    condition = selectCategory == category,
                    other = Modifier.color(JsTheme.Primary.rgb)
                )
                .fontFamily(FONT_FAMILY)
                .fontSize(16.px)
                .fontWeight(FontWeight.Medium)
                .textDecorationLine(TextDecorationLine.None)
                .onClick {
                    context.router.navigateTo(Screen.SearchPage.searchByCategory(category))
                },
            path = "",
            text = category.name
        )
    }
}