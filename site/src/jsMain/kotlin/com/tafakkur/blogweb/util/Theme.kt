package com.tafakkur.blogweb.util

import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Color.Companion.rgb
import com.varabyte.kobweb.compose.ui.graphics.Color.Companion.rgba

enum class JsTheme(
    val hex: String,
    val rgb: Color.Rgb
) {
    Primary(
        hex = "#00A2FF",
        rgb = rgb(r = 0, g = 162, b = 255)
    ),

    Secondary(
        hex = "#001019",
        rgb = rgb(r = 0, g = 16, b = 25)
    ),
    Tertiary(
        hex = "#001925",
        rgb = rgb(r = 0, g = 25, b = 37)
    ),
    LightGray(
        hex = "#FAFAFA",
        rgb = rgb(r = 250, g = 250, b = 250)
    ),
    Gray(
        hex = "#E9E9E9",
        rgb = rgb(r = 233, g = 233, b = 233)
    ),
    DarkGray(
        hex = "#646464",
        rgb = rgb(r = 100, g = 100, b = 100)
    ),
    HalfWhite(
        hex = "#FFFFFF",
        rgb = rgba(r = 255, g = 255, b = 255, a = 0.5f)
    ),
    HalfBlack(
        hex = "#000000",
        rgb = rgba(r = 0, g = 0, b = 0, a = 0.5f)
    ),
    White(
        hex = "#FFFFFF",
        rgb = rgb(r = 255, g = 255, b = 255)
    ),
    Green(
        hex = "#00FF94",
        rgb = rgb(r = 0, g = 255, b = 148)
    ),
    Yellow(
        hex = "#FFEC45",
        rgb = rgb(r = 255, g = 236, b = 69)
    ),
    Red(
        hex = "#FF6359",
        rgb = rgb(r = 255, g = 99, b = 89)
    ),
    Purple(
        hex = "#8B6DFF",
        rgb = rgb(r = 139, g = 109, b = 255)
    ),
    Sponsored(
        hex = "#3300FF",
        rgb = rgb(r = 51, g = 0, b = 255)
    ),

    Card(
        hex = "#F2F7FF",
        rgb = rgb(r = 242, g = 247, b = 255)
    ),

    Tech(
        hex = "#57CAEB",
        rgb = rgb(r = 87, g = 202, b = 235)
    ),

    Design(
        hex = "#9694FF",
        rgb = rgb(r = 150, g = 148, b = 255)
    ),

    Program(
        hex = "#5DDAB4",
        rgb = rgb(r = 93, g = 218, b = 180)
    ),
}