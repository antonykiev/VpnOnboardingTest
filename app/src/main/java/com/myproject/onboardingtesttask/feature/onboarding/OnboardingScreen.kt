package com.myproject.onboardingtesttask.feature.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.LottieCompositionFactory
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.myproject.onboardingtesttask.R
import com.myproject.onboardingtesttask.ui.isLowEndDevice
import com.myproject.onboardingtesttask.ui.theme.OnboardingTestTaskTheme
import com.myproject.onboardingtesttask.ui.theme.TextColorGrey
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen() {
    val viewModel = viewModel<OnboardingViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    val context = LocalContext.current

    viewModel.load(context)

    when (val state = uiState) {
        is OnboardingUiState.Loaded -> {
            val coroutineScope = rememberCoroutineScope()
            val pagerState = rememberPagerState()

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
                        .fillMaxWidth()
                        .height(48.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = if (pagerState.currentPage == 0)
                        Arrangement.End
                    else
                        Arrangement.SpaceBetween
                ) {
                    if (pagerState.currentPage != 0) {
                        IconButton(
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage - 1)
                                }
                            },
                            modifier = Modifier
                                .fillMaxHeight()
                                .clip(CircleShape)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.back_arrow),
                                contentDescription = null,
                                modifier = Modifier.clip(CircleShape),
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.surface)
                            )
                        }
                    }
                    Text(
                        modifier = Modifier
                            .clickable { }
                            .padding(16.dp),
                        color = TextColorGrey,
                        style = MaterialTheme.typography.bodySmall,
                        text = stringResource(R.string.skiip)
                    )
                }
                HorizontalPager(
                    count = state.pages.size,
                    state = pagerState,
                    modifier = Modifier.weight(1f)
                ) { pageIndex ->
                    val page = state.pages[pageIndex]
                    val composition = page.lottieComposition

                    val progress by animateLottieCompositionAsState(
                        composition = composition,
                        iterations = LottieConstants.IterateForever,
                        speed = if (isLowEndDevice(context)) 0.75F else 1F
                    )

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        LottieAnimation(
                            composition = composition,
                            progress = progress,
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.5F)
                        )

                        Text(
                            text = stringResource(page.title),
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(16.dp)
                        )
                        Text(
                            text = stringResource(page.description),
                            style = MaterialTheme.typography.bodyMedium,
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),

                    shape = RoundedCornerShape(6.dp),
                    onClick = {
                        coroutineScope.launch {
                            if (pagerState.currentPage < state.pages.lastIndex) {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        }
                    }) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.continue_str),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )
                    }
                }
            }
        }
        is OnboardingUiState.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(48.dp),
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 4.dp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GestureOnboardingScreenPreview() {
    OnboardingTestTaskTheme {
        OnboardingScreen()
    }
}