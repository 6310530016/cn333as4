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
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.*
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.*
import com.example.myapplication.data.ImageType
import com.example.myapplication.ui.ImageMainScreen

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
                    ImageMainScreen()
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