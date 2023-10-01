package com.example.pokedexapp.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toPixelMap

object ColorExtractor {

    fun forBgFromImage(image: ImageBitmap?): Color? {
        val dominantColor: Int? = extractDominantColor(image)
        val lightenedColor = dominantColor?.let { lightenColor(it) }
        val backgroundColor = lightenedColor?.let { Color(it) }

        return backgroundColor
    }

    private fun extractDominantColor(image: ImageBitmap?): Int? {
        val colorBuffer: IntArray? = image?.toPixelMap()?.buffer
        val filteredBuffer = colorBuffer?.filter { pixel ->
            pixel !in setOf<Int>(0, -1, -16777216) // filter out black, white and transparent
        }?.toIntArray()
        // look for most used color in filteredBuffer:
        val colorToCountMap: Map<Int, Int> = filteredBuffer?.groupBy { it }?.mapValues { it.value.size } ?: emptyMap()
        return colorToCountMap.maxByOrNull { it.value }?.key
    }

    private fun lightenColor(color: Int, maxLightness: Float = 200f, factor: Float = 0.4f): Int {
        val alpha = color ushr 24
        var red = (color ushr 16) and 0xFF
        var green = (color ushr 8) and 0xFF
        var blue = color and 0xFF

        // Calculate the lightness of the color (0 to 255)
        val lightness = (red + green + blue) / 3.0f

        // Calculate the factor based on the current lightness
        val adjustedFactor = 1 - ((lightness / maxLightness) * (1 - factor))

        // Lighten the color by increasing the RGB values
        red += ((255 - red) * adjustedFactor).toInt()
        green += ((255 - green) * adjustedFactor).toInt()
        blue += ((255 - blue) * adjustedFactor).toInt()

        // Make sure the values stay within the [0, 255] range
        red = red.coerceIn(0, 255)
        green = green.coerceIn(0, 255)
        blue = blue.coerceIn(0, 255)

        // Recreate the color with the modified RGB values
        return (alpha shl 24) or (red shl 16) or (green shl 8) or blue
    }
}