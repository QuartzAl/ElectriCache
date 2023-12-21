package com.example.electricache

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.example.electricache.model.service.impl.AccServiceImpl
import com.example.electricache.model.service.module.FirebaseModule
import com.example.electricache.screens.splash.SplashScreen
import com.example.electricache.screens.splash.SplashViewModel
import com.example.electricache.ui.theme.ElectriCacheTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ElectriCacheTheme {
                val splashViewModel = SplashViewModel(AccServiceImpl(FirebaseModule.auth()))
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SplashScreen(viewModel = splashViewModel)

                }
            }
        }
        lifecycleScope.launch {
            delay(2000)
            finish()
        }
    }
}