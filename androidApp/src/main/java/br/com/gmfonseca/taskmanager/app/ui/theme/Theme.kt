package br.com.gmfonseca.taskmanager.app.ui.theme

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color as AndroidColor

fun ComponentActivity.setThemedContent(content: @Composable () -> Unit) {
    setContent {
        Themed(content)
    }
}

@Composable
fun Themed(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = GrayAndYellow,
        content = content
    )
}

private val GrayAndYellow
    get() = Colors(
        primary = Color.Gray3,
        primaryVariant = Color.Gray4,

        secondary = Color.Yellow2,
        secondaryVariant = Color.Yellow3,

        background = Color.Gray1,
        surface = Color.Gray1,
        error = Color.DarkRed,

        onPrimary = Color.TextGray1,
        onSecondary = Color.TextGray2,
        onBackground = Color.TextGray2,
        onSurface = Color.TextGray2,
        onError = Color.TextGray1,
        isLight = true
    )

@Suppress("UNUSED")
private val RandomColors
    get() = Colors(
        primary = AndroidColor(0xFFFF4F4F),
        primaryVariant = AndroidColor(0xFFFF1A1A),

        secondary = AndroidColor(0xFF4FFF4F),
        secondaryVariant = AndroidColor(0xFF1AFF1A),

        background = AndroidColor(0xFF4F4FFF),
        surface = AndroidColor(0xFF1A1AFF),
        error = AndroidColor(0xFF625D4A),

        onPrimary = AndroidColor(0xFFFFFF00),
        onSecondary = AndroidColor(0xFFFF1AFF),
        onBackground = AndroidColor(0xFFFA1A6F),
        onSurface = AndroidColor(0xFFFAa444),
        onError = AndroidColor(0xFF00FFFF),
        isLight = true
    )
