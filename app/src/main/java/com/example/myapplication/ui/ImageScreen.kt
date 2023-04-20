package com.example.myapplication.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myapplication.ImageTypeBird
import com.example.myapplication.ImageTypeCat
import com.example.myapplication.ImageTypeDog
import com.example.myapplication.imageTypes
import com.example.myapplication.ui.ImageViewModel

sealed class ImageType(val label: String)

val imageTypes = listOf(ImageType("Car"), ImageType("Country"), ImageTypeBird)
@Preview(showBackground = true)
@Composable
fun ImageMainScreen(modifier: Modifier = Modifier,
                    imgViewModel: ImageViewModel = viewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val imageUiState by imgViewModel.uiState.collectAsState()
        ImageInputLayout(isValid = false)
    }
}

@Composable
fun ImageInputLayout(
    isValid: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row() {
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Image size (1-999)") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = "",
                onValueChange = {

                },
                label = { Text("Image size (1-999)") },
                modifier = Modifier.fillMaxWidth()
            )
        }
        DropdownMenu(
            expanded = false,
            onDismissRequest = {},
            modifier = Modifier.fillMaxWidth()
        ) {
            imageTypes.forEach { type ->
                DropdownMenuItem(onClick = { imageType = type }) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = type.image,
                            contentDescription = type.label,
                            tint = Color.Unspecified,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(type.label)
                    }
                }
            }
        }
        RandomImageButton(imgX = 60, imgY = 60, imageType = "Your mom")
    }
}
@Composable
fun RandomImageButton(
    imgX: Int = 0,
    imgY: Int = 0,
    imageType: String,
    view: ImageViewModel,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = {
            view.imgValidation(imgX, imgY)
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Show Image")
    }
}

@Composable
fun DisplayImage(imgX: Int, imgY: Int, imageType: String) {
    AsyncImage(model = ImageRequest.Builder(LocalContext.current)
        .data("https://loremflickr.com/$imgX/$imgY/$imageType"),
        contentDescription = "An image of $imageType with $imgX width and $imgY height")
}