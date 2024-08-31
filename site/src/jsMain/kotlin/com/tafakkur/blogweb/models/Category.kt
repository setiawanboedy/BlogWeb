@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.tafakkur.blogweb.models

import com.tafakkur.blogweb.util.JsTheme
import kotlinx.serialization.Serializable

@Serializable
actual enum class Category(override val color: String): CategoryColor {
    Technology(color = JsTheme.Green.hex),
    Programming(color = JsTheme.Yellow.hex),
    Design(color = JsTheme.Purple.hex)
}