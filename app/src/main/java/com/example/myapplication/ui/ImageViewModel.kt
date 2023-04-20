package com.example.myapplication.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ImageViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(ImageUiState())
    val uiState: StateFlow<ImageUiState> = _uiState.asStateFlow()

    var width by mutableStateOf("")
    var height by mutableStateOf("")
    var type by mutableStateOf("")
    var url by mutableStateOf("")

    fun imgValidation(x: Int, y: Int){
        var x: Int = width.toInt()
        var y: Int = height.toInt()
        var check: Boolean = true
        if (x < 200 || x > 1000) {
            _uiState.update {
                    currentState -> currentState.copy(isWidthValid = false)
            }
            check = false
        }
        else {
            _uiState.update {
                    currentState -> currentState.copy(isWidthValid = true)
            }
        }
        if (y < 200 || y > 1000) {
            _uiState.update {
                    currentState -> currentState.copy(isHeightValid = false)
            }
            check = false
        }
        else {
            _uiState.update {
                    currentState -> currentState.copy(isHeightValid = true)
            }
        }
        if (check) {
            url = "https://loremflickr.com/$width/$height/$type"
        }
    }
    fun updateWidth(x: String) {
        width = x
    }

    fun updateHeight(y: String) {
        height = y
    }

    fun updateImageType(type: String) {
        this.type = type
    }
}