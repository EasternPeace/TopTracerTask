import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toptracer.R
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _username = mutableStateOf("")
    val username: State<String> get() = _username

    private val _password = mutableStateOf("")
    val password: State<String> get() = _password

    fun onUsernameChanged(newUsername: String) {
        _username.value = newUsername
    }

    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword
    }

    fun login(onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            if (_username.value.isEmpty()) {
                onError("It looks like you forgot to provide a username.")
            } else if (_password.value == "password") {
                onSuccess()
            } else {
                onError("The password you provided doesn't match our records.")
            }
        }
    }

    fun logout() {
        _username.value = ""
        _password.value = ""
    }
}
