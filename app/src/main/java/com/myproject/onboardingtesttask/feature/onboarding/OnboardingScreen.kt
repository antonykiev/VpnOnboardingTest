package com.myproject.onboardingtesttask.feature.onboarding

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.LottieCompositionFactory
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.myproject.onboardingtesttask.R
import com.myproject.onboardingtesttask.ui.isLowEndDevice
import com.myproject.onboardingtesttask.ui.optimizeSizeByHeight
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen() {
    val context = LocalContext.current

    val viewModel = viewModel<OnboardingViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    val pagerState = rememberPagerState()

    val coroutineScope = rememberCoroutineScope()

    DisposableEffect(Unit) {
        onDispose {
            LottieCompositionFactory.clearCache(context)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = if (pagerState.currentPage == 0)
                Arrangement.End
            else
                Arrangement.SpaceBetween
        ) {
            if (pagerState.currentPage != 0) {
                Text(
                    modifier = Modifier
                        .clickable {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            }
                        }
                        .padding(16.dp),
                    text = stringResource(R.string.back)
                )
            }
            Text(
                modifier = Modifier
                    .clickable { }
                    .padding(16.dp),
                text = stringResource(R.string.skiip)
            )
        }
        HorizontalPager(
            count = uiState.pages.size,
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            val composition by rememberLottieComposition(uiState.pages[page].lottieCompositionSpec)
            val progress by animateLottieCompositionAsState(
                composition = composition,
                iterations = LottieConstants.IterateForever,
                speed = if (isLowEndDevice(context)) 0.75F else 1F
            )

            Column {
                LottieAnimation(
                    composition = composition,
                    progress = progress,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(optimizeSizeByHeight({ 0.5F }, { 0.35F }))
                )

                Text(
                    text = stringResource(uiState.pages[page].title),
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = optimizeSizeByHeight({ 22.sp }, { 16.sp }),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
                Text(
                    text = stringResource(uiState.pages[page].description),
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = optimizeSizeByHeight({ 20.sp }, { 14.sp }),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 16.dp),
            activeColor = MaterialTheme.colorScheme.primary,
            inactiveColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
        )

        Button(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(6.dp),
            onClick = {
                coroutineScope.launch {
                    if (pagerState.currentPage < uiState.pages.lastIndex) {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }
            }) {
            Text(text = stringResource(R.string.continue_str))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GestureOnboardingScreenPreview() {
    OnboardingScreen()
}