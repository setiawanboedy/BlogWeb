package com.tafakkur.blogweb.models

import com.tafakkur.blogweb.util.Res

enum class EditorControl(
    val icon: String
) {
    Bold(icon = Res.Icon.bold),
    Italic(icon = Res.Icon.italic),
    Link(icon = Res.Icon.link),
    Title(icon = Res.Icon.title),
    Subtitle(icon = Res.Icon.subtitle),
    Quote(icon = Res.Icon.quote),
    Code(icon = Res.Icon.code),
    Image(icon = Res.Icon.image)
}