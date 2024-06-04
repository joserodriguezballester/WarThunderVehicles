package com.example.warthundervehicles.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.warthundervehicles.R
val FontARMY = FontFamily(
    Font(R.font.capsmall_clean))
val Army = FontFamily(Font(R.font.capsmall_clean),

  //  Font(R.font.montserrat_bold, FontWeight.Bold),
 //   Font(R.font.montserrat_italic, FontWeight.Normal, FontStyle.Italic)
)

//val Typography = androidx.compose.material.Typography(
//    defaultFontFamily = Montserrat,
//    h1 = TextStyle(
//        fontFamily = Montserrat,
//        fontWeight = FontWeight.Bold,
//        fontSize = 30.sp
//    ),
    // Define other text styles as needed
//)
// Set of Material typography styles to start with
val Typography = Typography(

    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    // Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = Army,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
  /*
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)