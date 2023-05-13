import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.toptracer.ui.state.LoginScreenUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class LoginViewModel : ViewModel() {
    private val _loginScreenUiState = MutableStateFlow(LoginScreenUiState())
    val loginScreenUiState: StateFlow<LoginScreenUiState> = _loginScreenUiState.asStateFlow()

    var username by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    fun updateUsername(nameInput: String) {
        username = nameInput
    }

    fun updatePassword(passwordInput: String) {
        password = passwordInput
    }

    fun onLoginClicked(onSuccessLogin: () -> Unit) {
        if (validateUsername()) {
            validatePassword(onSuccessLogin)
        }
    }

    private fun validateUsername(): Boolean {
        return if (username.isEmpty()) {
            _loginScreenUiState.update { currentState ->
                currentState.copy(
                    isError = true,
                    errorText = "It looks like you forgot to provide a username."
                )
            }
            false
        } else true
    }

    private fun validatePassword(onSuccessLogin: () -> Unit) {
        if (password == "password") {
            _loginScreenUiState.update { currentState ->
                currentState.copy(
                    errorText = ""
                )
            }
            onSuccessLogin.invoke()
            resetInputFields()
        } else {
            _loginScreenUiState.update { currentState ->
                currentState.copy(
                    isError = true,
                    errorText = "The password you provided doesn't match our records."
                )
            }
        }
    }

    fun resetInputFields() {
        username = ""
        password = ""
    }

    fun resetErrorState() {
        _loginScreenUiState.update { currentState ->
            currentState.copy(
                isError = false,
                errorText = ""
            )
        }
    }
}
