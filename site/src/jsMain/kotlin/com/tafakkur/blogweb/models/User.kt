package com.tafakkur.blogweb.models

import kotlinx.serialization.Serializable

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
@Serializable
actual data class User(
    actual val id: String = "",
    actual val username: String = "",
    actual val password: String = ""
)