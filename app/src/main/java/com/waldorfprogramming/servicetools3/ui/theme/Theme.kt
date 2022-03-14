package com.waldorfprogramming.servicetools3.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = DARKPRIMARYCOLOR,
    primaryVariant = LIGHTPRIMARYCOLOR,
    secondary = ACCENTCOLOR,
    secondaryVariant = GREY,
    background = LIGHTPRIMARYCOLOR,
    surface = LIGHTPRIMARYCOLOR,
    onPrimary = BLACK,
    onSecondary = WHITE,
    onBackground = BLACK,
    onSurface = BLACK,
)

private val LightColorPalette = lightColors(
    primary = DARKPRIMARYCOLOR,
    primaryVariant = LIGHTPRIMARYCOLOR,
    secondary = ACCENTCOLOR,
    secondaryVariant = GREY,
    background = LIGHTPRIMARYCOLOR,
    surface = LIGHTPRIMARYCOLOR,
    onPrimary = BLACK,
    onSecondary = WHITE,
    onBackground = BLACK,
    onSurface = BLACK,

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun ServiceTools3Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}