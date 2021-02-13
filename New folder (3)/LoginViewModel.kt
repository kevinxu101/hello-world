package com.example.samplelogin.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.example.samplelogin.data.LoginRepository
import com.example.samplelogin.data.Result

import com.example.samplelogin.R
import com.google.android.material.snackbar.Snackbar

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {
        // can be launched in a separate asynchronous job
        val result = loginRepository.login(username, password)
        var usernames = arrayOf("eric","kevin","david","joaquin")
        var passwords = arrayOf("erictest","password","1234567","DLSCSB")

        /*
        THIS DOESN'T WORK WHEN I FOR LOOP THIS??? IDK WHY BUT IT SUCKS

        */
        var counter1 = 0

        for(items in usernames.indices){
            for(items2 in passwords.indices){
                if(username == usernames[0] && password == passwords[0] ){
                    if (result is Result.Success) {
                        _loginResult.value = LoginResult(success = LoggedInUserView(displayName = result.data.displayName))
                        break;
                    }
                }
                if(username == usernames[1] && password == passwords[1] ){
                    if (result is Result.Success) {
                        _loginResult.value = LoginResult(success = LoggedInUserView(displayName = result.data.displayName))
                        break;
                    }
                }
                if(username == usernames[2] && password == passwords[2] ){
                    if (result is Result.Success) {
                        _loginResult.value = LoginResult(success = LoggedInUserView(displayName = result.data.displayName))
                        break;
                    }
                }
                if(username == usernames[3] && password == passwords[3] ){
                    if (result is Result.Success) {
                        _loginResult.value = LoginResult(success = LoggedInUserView(displayName = result.data.displayName))
                        break;
                    }
                }
                else if(username != usernames[items] && password != passwords[items2] ){
                    _loginResult.value = LoginResult(error = R.string.login_failed)



                    break;

                }
            }
        }

    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}