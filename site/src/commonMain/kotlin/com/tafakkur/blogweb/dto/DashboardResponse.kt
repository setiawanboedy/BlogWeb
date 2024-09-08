package com.tafakkur.blogweb.dto

import kotlinx.serialization.Serializable


@Serializable
data class DashboardResponse(
    var technology: Int = 0,
    var programming: Int = 0,
    var design: Int = 0,
)
