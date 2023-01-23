package com.eurosport.demo.ui.navigation

import android.content.Intent
import android.net.Uri
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.eurosport.demo.di.PresentationFactory
import com.eurosport.demo.feature.detail.DetailScreen
import com.eurosport.demo.feature.home.ArticleItem
import com.eurosport.demo.feature.home.HomeScreen
import com.eurosport.demo.feature.home.HomeViewModel
import com.eurosport.demo.feature.player.PlayerScreen
import com.google.gson.Gson

@Composable
fun NavGraph(navController: NavHostController, viewModelFactory: PresentationFactory) {

    NavHost(
        navController = navController,
        startDestination = NavRoute.Home.path
    ) {
        addHomeScreen(viewModelFactory, navController, this)

        addDetailScreen(navController, this)

        addPlayerScreen(navController, this)
    }
}

private fun addHomeScreen(
    viewModelFactory: PresentationFactory,
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = NavRoute.Home.path) {
        val viewModel: HomeViewModel = viewModel(factory = viewModelFactory)
        viewModel.start()
        HomeScreen(
            viewModel = viewModel,
            navigateToDetail = { item ->
                val json = Uri.encode(Gson().toJson(item))
                navController.navigate(NavRoute.Detail.withArgs(json))
            },
            navigateToPlayer = { videoUrl ->
                navController.navigate(NavRoute.Player.withArgs(videoUrl))
            },
            popBackStack = { navController.popBackStack() }
        )
    }
}

private fun addDetailScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(
        route = NavRoute.Detail.withArgsFormat(NavRoute.Detail.item),
        arguments = listOf(
            navArgument(NavRoute.Detail.item) {
                type = StoryItemType()
            })
    ) { navBackStackEntry ->
        val args = navBackStackEntry.arguments ?: return@composable
        val item = args.parcelable<ArticleItem.StoryItem>(NavRoute.Detail.item) ?: return@composable
        DetailScreen(item = item) {
            navController.popBackStack()
        }
    }
}

class StoryItemType : NavType<ArticleItem.StoryItem>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): ArticleItem.StoryItem? {
        return bundle.parcelable(key)
    }

    override fun parseValue(value: String): ArticleItem.StoryItem {
        return Gson().fromJson(value, ArticleItem.StoryItem::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: ArticleItem.StoryItem) {
        bundle.putParcelable(key, value)
    }
}

private fun addPlayerScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(
        route = NavRoute.Player.withArgsFormat(NavRoute.Player.videoUrl),
        arguments = listOf(
            navArgument(NavRoute.Player.videoUrl) {
                type = NavType.StringType
            })
    ) { navBackStackEntry ->

        val args = navBackStackEntry.arguments ?: return@composable
        PlayerScreen(videoUrl = args.getString(NavRoute.Player.videoUrl) ?: "") {
            navController.popBackStack()
        }
    }
}

inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}
