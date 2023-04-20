package com.example.myapplication.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myapplication.data.ImgTypes

sealed class ImageType(val label: String)

@Preview(showBackground = true)
@Composable
fun ImageMainScreen(modifier: Modifier = Modifier,
                    imgViewModel: ImageViewModel = viewModel()
) {
    val imageUiState by imgViewModel.uiState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Search random images",
        fontSize = 32.sp,
            fontWeight = FontWeight.Bold
            )
        Text(text = "Image must be larger than 200x200 pixel and not larger than 1000x1000 pixels",
        fontSize = 16.sp,
            color = Color.Gray
            )
        ImageInputLayout(
            imageUiState.isWidthValid,
            imageUiState.isHeightValid,
            imgViewModel,
            imgViewModel.width,
            imgViewModel.height,
            imgViewModel.type,
            { imgViewModel.updateWidth(it)},
            { imgViewModel.updateHeight(it)},
            { imgViewModel.updateImageType(it)}
            )
    }
}

@Composable
fun ImageInputLayout(
    isWidthValid: Boolean,
    isHeightValid: Boolean,
    imgViewModel: ImageViewModel,
    width: String,
    height: String,
    imgType: String,
    onWidthChange: (String) -> Unit,
    onHeightChange: (String) -> Unit,
    onImageTypeChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = width,
            onValueChange = onWidthChange,
            isError =  !isWidthValid,
            label = {
                    if(isWidthValid) {
                        Text("Width (pixels)")
                    }
                else {
                        Text("Width out of scope!")
                    }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = height,
            onValueChange = onHeightChange,
            isError = !isHeightValid,
            label = {
                if(isHeightValid) {
                Text("Height (pixels)")
            }
            else {
                Text("Height out of scope!")
            } },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = imgType,
            onValueChange = onImageTypeChange,
            label = { Text("Image Type (Can be empty)") },
            keyboardOptions = KeyboardOptions.Default,
            modifier = Modifier.fillMaxWidth()
        )
        RandomImageButton({imgViewModel.imgValidation(width.toInt(), height.toInt())})
        DisplayImage(url = imgViewModel.url)
    }
}
@Composable
fun RandomImageButton(
    validation: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = validation,
        modifier = Modifier.fillMaxWidth().padding(8.dp)
    ) {
        Text("Show Image", fontSize = 20.sp)
    }
}

@Composable
fun DisplayImage(url: String) {
    AsyncImage(model = ImageRequest.Builder(LocalContext.current)
        .data(url)
        .crossfade(true)
        .build(),
        contentDescription = "")
}