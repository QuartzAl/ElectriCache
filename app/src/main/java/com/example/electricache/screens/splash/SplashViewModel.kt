package com.example.electricache.screens.splash

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.electricache.HOME_SCREEN
import com.example.electricache.SPLASH_SCREEN
import com.example.electricache.model.service.AccountService
import com.google.firebase.auth.FirebaseAuthException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel
@Inject
constructor(private val accountService: AccountService): ViewModel() {
    val showError = mutableStateOf(false)

    fun onAppStart(openAndPopUp: (String, String) -> Unit) {

        showError.value = false
        if (accountService.hasUser) openAndPopUp(HOME_SCREEN, SPLASH_SCREEN)
        else createAnonymousAccount()
    }

    private fun createAnonymousAccount() {
        viewModelScope.launch {
            try {
                accountService.createAnonymousAccount()
            } catch (ex: FirebaseAuthException) {
                showError.value = true
                throw ex
            }
        }
    }
}