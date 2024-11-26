package com.xai.helloworld.ui.mainscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xai.helloworld.network.getXAiApi
import kotlinx.coroutines.launch

class MainScreenViewModel : ViewModel() {
    internal var messages: List<Message> by mutableStateOf(emptyList())
    private val xAiApi by lazy { getXAiApi() }

    init {
        viewModelScope.launch {
            val apiKeyInfo = xAiApi.getApiKeyInfo()
            messages += Message(apiKeyInfo.toString())
        }
    }

    fun onUserMessage(msg: String) {
        messages += Message(msg)
    }

}

enum class Role {
    System,
    Assistant,
    User
}

data class Message(
    val msg: String,
    val role: Role = Role.User,
    val id: Long = Companion.id++,
    val pending: Boolean = true
) {
    companion object {
        var id = 0L
    }
}