@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.tafakkur.blogweb.models

expect class User{
    val id: String
    val username: String
    val password: String
}