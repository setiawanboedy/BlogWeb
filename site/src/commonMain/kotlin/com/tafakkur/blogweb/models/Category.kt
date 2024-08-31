@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.tafakkur.blogweb.models

expect enum class Category: CategoryColor {
    Technology,
    Programming,
    Design
}

interface CategoryColor {
    val color: String
}