package com.sean.permitly.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = OceanBlue,
    onPrimary = PureWhite,
    primaryContainer = MistBlue,
    onPrimaryContainer = MidnightBlue,
    secondary = SteelGrey,
    onSecondary = PureWhite,
    secondaryContainer = CloudGrey,
    onSecondaryContainer = Charcoal,
    tertiary = CocoaBrown,
    onTertiary = PureWhite,
    tertiaryContainer = Cream,
    onTertiaryContainer = Espresso,
    background = Alabaster,
    onBackground = Onyx,
    surface = Ivory,
    onSurface = Onyx,
    surfaceVariant = PebbleGrey,
    onSurfaceVariant = SlateGrey,
    surfaceTint = DeepOcean,
    inverseSurface = Graphite,
    inverseOnSurface = CloudWhite,
    inversePrimary = SkyBlue,
    error = Crimson,
    onError = PureWhite,
    errorContainer = RoseCream,
    onErrorContainer = DarkMaroon,
    outline = StoneGrey,
    outlineVariant = AshGrey,
    scrim = JetBlack,
    surfaceBright = LinenWhite,
    surfaceContainer = FogGrey,
    surfaceContainerHigh = LightStone,
    surfaceContainerHighest = MistGrey,
    surfaceContainerLow = SoftAlabaster,
    surfaceContainerLowest = PureWhite,
    surfaceDim = OffWhite
)

@Composable
fun PermitlyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        shapes = Shapes,
        typography = Typography,
        content = content
    )
}