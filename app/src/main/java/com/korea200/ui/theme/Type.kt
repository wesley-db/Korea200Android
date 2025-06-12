package com.korea200.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.korea200.R

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

/*val fontName = GoogleFont("Lobster Two")

val fontFamilyKorea200 = FontFamily(
    Font(googleFont = fontName, fontProvider = provider),
)*/

val fontFamilyKorea2002 = FontFamily(
    androidx.compose.ui.text.font.Font(R.font.lobster2_regular, FontWeight.Normal),
    androidx.compose.ui.text.font.Font(R.font.lobster2_bold, FontWeight.Bold),
    androidx.compose.ui.text.font.Font(R.font.lobster2_italic, FontWeight.Normal, FontStyle.Italic),
    androidx.compose.ui.text.font.Font(R.font.lobster2_bold_italic, FontWeight.Bold, FontStyle.Italic)
)

// Set of Material typography styles to start with
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = fontFamilyKorea2002,
        color = Color.White,
        fontSize = 75.sp,
        shadow = Shadow(color = Color.Black,
            offset = Offset(20f,15f),
            blurRadius = 1f,
        )
    ),
    headlineLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 23.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 17.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontStyle = FontStyle.Italic,
        fontSize = 12.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)

val linkSpanStyle = SpanStyle(
    color = Cobalt,
    fontFamily = Typography.labelLarge.fontFamily,
    fontSize = Typography.labelLarge.fontSize,
    fontStyle = Typography.labelLarge.fontStyle,
    fontWeight = Typography.labelLarge.fontWeight,
    textDecoration = TextDecoration.Underline,
    letterSpacing = Typography.labelLarge.letterSpacing
)