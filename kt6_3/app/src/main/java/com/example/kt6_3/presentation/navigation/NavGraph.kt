package com.example.kt6_3.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.kt6_3.data.local.TokenStorage
import com.example.kt6_3.data.remote.KtorClient
import com.example.kt6_3.data.remote.api.AuthApi
import com.example.kt6_3.data.repository.AuthRepositoryImpl
import com.example.kt6_3.domain.usecase.GetUserDetailUseCase
import com.example.kt6_3.domain.usecase.GetUsersUseCase
import com.example.kt6_3.domain.usecase.LoginUseCase
import com.example.kt6_3.presentation.screen.LoginScreen
import com.example.kt6_3.presentation.screen.UserDetailScreen
import com.example.kt6_3.presentation.screen.UsersListScreen
import com.example.kt6_3.presentation.viewmodel.LoginViewModel
import com.example.kt6_3.presentation.viewmodel.UserDetailViewModel
import com.example.kt6_3.presentation.viewmodel.UsersListViewModel
import androidx.compose.ui.platform.LocalContext

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object UsersList : Screen("users_list")
    object UserDetail : Screen("user_detail/{userId}") {
        fun createRoute(userId: Int) = "user_detail/$userId"
    }
}

@Composable
fun NavGraph(navController: NavHostController) {
    val context = LocalContext.current
    val tokenStorage = TokenStorage(context)
    val httpClient = KtorClient.createHttpClient(context)
    val authApi = AuthApi(httpClient)
    val repository = AuthRepositoryImpl(authApi, tokenStorage)

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            val loginUseCase = LoginUseCase(repository)
            val viewModel: LoginViewModel = viewModel(
                factory = LoginViewModel.Factory(loginUseCase)
            )

            LoginScreen(
                viewModel = viewModel,
                onLoginSuccess = {
                    navController.navigate(Screen.UsersList.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.UsersList.route) {
            val getUsersUseCase = GetUsersUseCase(repository)
            val viewModel: UsersListViewModel = viewModel(
                factory = UsersListViewModel.Factory(getUsersUseCase)
            )

            UsersListScreen(
                viewModel = viewModel,
                onUserClick = { userId ->
                    navController.navigate(Screen.UserDetail.createRoute(userId))
                }
            )
        }

        composable(
            route = Screen.UserDetail.route,
            arguments = listOf(
                navArgument("userId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0
            val getUserDetailUseCase = GetUserDetailUseCase(repository)
            val viewModel: UserDetailViewModel = viewModel(
                factory = UserDetailViewModel.Factory(getUserDetailUseCase, repository)
            )

            UserDetailScreen(
                userId = userId,
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}