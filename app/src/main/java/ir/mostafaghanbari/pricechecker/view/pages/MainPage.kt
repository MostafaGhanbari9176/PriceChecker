package ir.mostafaghanbari.pricechecker.view.pages


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.mostafaghanbari.pricechecker.view.theme.PriceCheckerTheme

@Composable
fun MainPage(onclick: () -> Unit) {

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopAppBar() {
                Text("Price Checker", style = MaterialTheme.typography.h5)
            }
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Welcome",
                style = MaterialTheme.typography.h3,
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center
            )
            Button(onClick = { onclick() }) {
                Text(text = "Go To Basket")
            }
        }

    }
}

@Preview
@Composable
fun Preview(){
    PriceCheckerTheme {
        MainPage {

        }
    }
}

