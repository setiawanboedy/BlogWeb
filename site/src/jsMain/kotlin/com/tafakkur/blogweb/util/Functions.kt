package com.tafakkur.blogweb.util

import com.tafakkur.blogweb.core.utils.Constants.IMAGE_BASE_URL
import com.tafakkur.blogweb.models.EditorControl
import kotlinx.browser.document
import org.w3c.dom.HTMLTextAreaElement
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.js.Date


fun applyControlStyle(
    editorControl: EditorControl,
    onLinkClick: () -> Unit,
    onImageClick: () -> Unit
){
    when (editorControl){
        EditorControl.Bold -> {
            applyStyle(
                ControlStyle.Bold(selectedText = getSelectedText())
            )
        }
        EditorControl.Italic -> {
            applyStyle(
                ControlStyle.Italic(
                    selectedText = getSelectedText()
                )
            )
        }

        EditorControl.Link -> {
            onLinkClick()
        }

        EditorControl.Title -> {
            applyStyle(
                ControlStyle.Title(
                    selectedText = getSelectedText()
                )
            )
        }

        EditorControl.Subtitle -> {
            applyStyle(
                ControlStyle.Subtitle(
                    selectedText = getSelectedText()
                )
            )
        }

        EditorControl.Quote -> {
            applyStyle(
                ControlStyle.Quote(
                    selectedText = getSelectedText()
                )
            )
        }

        EditorControl.Code -> {
            applyStyle(
                ControlStyle.Code(
                    selectedText = getSelectedText()
                )
            )
        }
        EditorControl.Image -> {
            onImageClick()
        }
    }
}

fun getEditor() = document.getElementById(Id.editor) as HTMLTextAreaElement

fun getSelectedIntRange(): IntRange? {
    val editor = getEditor()
    val start = editor.selectionStart
    val end = editor.selectionEnd
    return if (start != null && end != null){
        IntRange(start, (end - 1))
    }else null
}

fun getSelectedText() : String? {
    val range = getSelectedIntRange()
    return if (range != null){
        getEditor().value.substring(range)
    }else null
}

fun applyStyle(controlStyle: ControlStyle){
    val selectedText = getSelectedText()
    val selectedIntRange = getSelectedIntRange()
    if (selectedIntRange != null && selectedText != null){
        getEditor().value = getEditor().value.replaceRange(
            range = selectedIntRange,
            replacement = controlStyle.style
        )
        document.getElementById(Id.editorPreview)?.innerHTML = getEditor().value
    }
}

fun parseSwitchText(posts: List<Long>): String {
    return if (posts.size == 1) "1 Posts Selected" else "${posts.size} Posts Selected"
}

fun validateEmail(email: String): Boolean {
    val regex = "^[A-Za-z](.*)(@)(.+)(\\.)(.+)"
    return regex.toRegex().matches(email)
}

fun isTokenExpired(expiresIn: String?): Boolean {

    if (expiresIn == null){
        return true
    }
    val expirationTime = Date(expiresIn).getTime()
    val currentTime = Date.now()

    return currentTime > expirationTime
}

@OptIn(ExperimentalEncodingApi::class)
fun base64ToByteArray(dataUrl: String): ByteArray {
    val base64Data = dataUrl.substringAfter("base64,")

    return Base64.decode(base64Data)
}

fun checkUrlThumbnailImage(url: String): String {
    val isHaveHttp = url.contains("http")
    var urlImage = url
    if (!isHaveHttp){
        urlImage = "$IMAGE_BASE_URL$url"
    }
    return urlImage
}