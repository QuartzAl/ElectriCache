package com.example.electricache.screens.splash

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.electricache.model.service.AccountService
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashViewModel
@Inject
constructor(private val accountService: AccountService): ViewModel() {
    val showError = mutableStateOf(false)

    fun onAppStart() {

        showError.value = false
        if (!accountService.hasUser) createAnonymousAccount()

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