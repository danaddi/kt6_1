package com.example.kt6_2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.kt6_2.di.AppModule
import com.example.kt6_2.presentation.detail.NobelPrizeDetailScreen
import com.example.kt6_2.presentation.detail.NobelPrizeDetailViewModel
import com.example.kt6_2.presentation.list.NobelPrizeListScreen
import com.example.kt6_2.presentation.list.NobelPrizeListViewModel
import com.example.kt6_2.ui.theme.Kt6_2Theme
import com.google.gson.Gson
import java.net.URLEncoder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val listViewModel = AppModule.provideNobelPrizeListViewModel()
        val detailViewModel = AppModule.provideNobelPrizeDetailViewModel()

        setContent {
            Kt6_2Theme {
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
private fun AppNavigation(
    listViewModel: NobelPrizeListViewModel,
    detailViewModel: NobelPrizeDetailViewModel
) {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = "nobel_list"
    ) {
        composable("nobel_list") {
            NobelPrizeListScreen(
                viewModel = listViewModel,
                onLaureateClick = { prize ->
                    val laureate = prize.laureates.firstOrNull() ?: return@NobelPrizeListScreen
                    val prizeJson = URLEncoder.encode(Gson().toJson(prize), "UTF-8")
                    navController.navigate("laureate_detail/${laureate.id}/$prizeJson")
                }
            )
        }

        composable(
            route = "laureate_detail/{laureateId}/{prizeJson}",
            arguments = listOf(
                navArgument("laureateId") { type = NavType.StringType },
                navArgument("prizeJson") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val laureateId = backStackEntry.arguments?.getString("laureateId") ?: ""

            NobelPrizeDetailScreen(
                laureateId = laureateId,
                viewModel = detailViewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}