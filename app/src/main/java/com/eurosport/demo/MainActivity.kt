package com.eurosport.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.eurosport.demo.ui.navigation.NavGraph
import com.eurosport.demo.ui.theme.EurosportTheme
import com.eurosport.presentation.PresentationFactory

class MainActivity : ComponentActivity() {

    private val viewModelFactory: PresentationFactory = PresentationFactory()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(viewModelFactory)
        }
    }
}

@Composable
private fun MainScreen(viewModelFactory: PresentationFactory) {
    EurosportTheme {
        val navController = rememberNavController()
        NavGraph(navController, viewModelFactory)
    }
}