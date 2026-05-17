package com.example.kt6_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.kt6_1.di.AppModule
import com.example.kt6_1.domain.model.Photo
import com.example.kt6_1.presentation.detail.PhotoDetailScreen
import com.example.kt6_1.presentation.detail.PhotoDetailViewModel
import com.example.kt6_1.presentation.list.PhotoListScreen
import com.example.kt6_1.presentation.list.PhotoListViewModel
import com.example.kt6_1.ui.theme.Kt6_1Theme
import com.google.gson.Gson
import java.net.URLDecoder
import java.net.URLEncoder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val listViewModel = AppModule.providePhotoListViewModel()
        val detailViewModel = AppModule.providePhotoDetailViewModel()

        setContent {
            Kt6_1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(
                        listViewModel = listViewModel,
                        detailViewModel = detailViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun AppNavigation(
    listViewModel: PhotoListViewModel,
    detailViewModel: PhotoDetailViewModel
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "photo_list"
    ) {
        composable("photo_list") {
            PhotoListScreen(
                viewModel = listViewModel,
                onPhotoClick = { photo ->
                    val photoJson = URLEncoder.encode(Gson().toJson(photo), "UTF-8")
                    navController.navigate("photo_detail/$photoJson")
                }
            )
        }

        composable(
            route = "photo_detail/{photoJson}",
            arguments = listOf(
                navArgument("photoJson") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val photoJson = URLDecoder.decode(
                backStackEntry.arguments?.getString("photoJson") ?: "",
                "UTF-8"
            )
            val photo = Gson().fromJson(photoJson, Photo::class.java)

            PhotoDetailScreen(
                photo = photo,
                viewModel = detailViewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}