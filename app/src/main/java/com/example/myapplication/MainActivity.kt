package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.*
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                    RandomImageScreen()
                }
            }
        }
    }
}

sealed class ImageType(val label: String, val image: ImageVector)
object ImageTypeCat : ImageType("Cat", ImageVector.vectorResource(R.drawable.ic_cat))
object ImageTypeDog : ImageType("Dog", ImageVector.vectorResource(R.drawable.ic_dog))
object ImageTypeBird : ImageType("Bird", ImageVector.vectorResource(R.drawable.ic_bird))

val imageTypes = listOf(ImageTypeCat, ImageTypeDog, ImageTypeBird)

@Composable
fun RandomImageScreen() {
    var imageSize by remember { mutableStateOf("") }
    var imageType by remember { mutableStateOf(imageTypes[0]) }
    var showImage by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Random Image") })
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Image size input
            OutlinedTextField(
                value = imageSize,
                onValueChange = {
                    if (it.matches(Regex("[1-9]\\d{0,2}"))) {
                        imageSize = it
                    }
                },
                label = { Text("Image size (1-999)") },
                modifier = Modifier.fillMaxWidth()
            )

            // Image type selection
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

            // Show image button
            Button(
                onClick = {
                    if (imageSize.isNotBlank()) {
                        showImage = true
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Show Image")
            }

            // Display image
            if (showImage) {
                Image(
                    imageVector = imageType.image,
                    contentDescription = imageType.label,
                    modifier = Modifier.size(imageSize.toInt().dp)
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}