package com.bayutb.ui.component.animations

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.bayutb.ui.R

@Composable
fun Loading(modifier: Modifier = Modifier) {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.loading)
    )
    LottieAnimation(
        composition = composition,
        modifier = modifier,
        iterations = LottieConstants.IterateForever
    )
}
