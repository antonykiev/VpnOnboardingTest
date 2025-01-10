package com.myproject.onboardingtesttask.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.myproject.onboardingtesttask.R

private val DarkColorScheme = darkColorScheme(
    primary = Blue,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    surface = TextColorGrey
)

private val LightColorScheme = lightColorScheme(
    primary = Blue,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    surface = IconLight

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun OnboardingTestTaskTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val Roboto = FontFamily(
        Font(R.font.roboto_regular, FontWeight.Normal),
        Font(R.font.roboto_bold, FontWeight.Bold),
        Font(R.font.roboto_italic, FontWeight.Normal)
    )

    val configuration = LocalConfiguration.current
    val isSmallDevice = configuration.screenWidthDp <= 320

    val Typography = if (!isSmallDevice) {
        Typography(
            bodyLarge = TextStyle(
                fontFamily = Roboto,
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp,
                lineHeight = 32.sp,
                letterSpacing = 0.5.sp
            ),
            bodyMedium = TextStyle(
                fontFamily = Roboto,
                fontWeight = FontWeight.Normal,
                fontSize = 17.sp,
                lineHeight = 28.sp,
                letterSpacing = 0.5.sp
            ),
            bodySmall = TextStyle(
                fontFamily = Roboto,
                fontWeight = FontWeight.Normal,
                fontSize = 15.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.5.sp
            )
        )
    } else {
        Typography(
            bodyLarge = TextStyle(
                fontFamily = Roboto,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.5.sp
            ),
            bodyMedium = TextStyle(
                fontFamily = Roboto,
                fontWeight = FontWeight.Normal,
                fontSize = 13.sp,
                lineHeight = 17.sp,
                letterSpacing = 0.5.sp
            ),
            bodySmall = TextStyle(
                fontFamily = Roboto,
                fontWeight = FontWeight.Normal,
                fontSize = 11.sp,
                lineHeight = 14.sp,
                letterSpacing = 0.5.sp
            )
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}