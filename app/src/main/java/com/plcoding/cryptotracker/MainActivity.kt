package com.plcoding.cryptotracker

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.plcoding.cryptotracker.core.navigation.AdaptiveCoinListDetailPane
import com.plcoding.cryptotracker.ui.theme.CryptoTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                // show splash screen until the app is ready to be displayed
                false
            }
            setOnExitAnimationListener { screen ->
                Handler(Looper.getMainLooper()).postDelayed({
                    val zoomX = ObjectAnimator.ofFloat(
                        screen.iconView,
                        View.SCALE_X,
                        0.5f,
                        0.0f
                    )
                    zoomX.interpolator = OvershootInterpolator()
                    zoomX.duration = 1000L
                    zoomX.doOnEnd { screen.remove() }
                    val zoomY = ObjectAnimator.ofFloat(
                        screen.iconView,
                        View.SCALE_Y,
                        0.5f,
                        0.0f
                    )
                    zoomY.interpolator = OvershootInterpolator()
                    zoomY.duration = 1000L
                    zoomY.doOnEnd { screen.remove() }

                    zoomX.start()
                    zoomY.start()
                }, 4000L)
            }
        }
        setContent {
            CryptoTrackerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AdaptiveCoinListDetailPane(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CryptoTrackerTheme {
        Greeting("Android")
    }
}