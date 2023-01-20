package com.eurosport.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.eurosport.demo.di.PresentationFactory
import com.eurosport.demo.feature.home.ArticleListScreen
import com.eurosport.demo.feature.home.HomeContent
import com.eurosport.demo.feature.home.HomeViewModel
import com.eurosport.demo.ui.theme.EurosportTheme

class MainActivity : ComponentActivity() {

    private val viewModel: HomeViewModel by viewModels { PresentationFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EurosportTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    HomeContent(viewModel)
                    viewModel.start()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    EurosportTheme {
        ArticleListScreen(emptyList())
    }
}