package ir.mostafaghanbari.pricechecker.view.pages

import android.graphics.drawable.PaintDrawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.materialIcon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.mostafaghanbari.pricechecker.model.ItemModel

@Composable
fun BasketPage(
    items: List<ItemModel>,
    onScan: () -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Box() {
            ItemList(items)
            FloatingActionButton(
                onClick = onScan,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Scan")
            }
        }
    }
}

@Composable
fun ItemList(data: List<ItemModel>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(data) { item ->
            Item(data = item)
        }
    }
}


@Composable
fun Item(data: ItemModel) {
    Column(modifier = Modifier.fillMaxWidth().border(1.dp, Color.Blue)) {
        Text(text = "id = ${data.id}")
        Text(text = "name = ${data.name}")
        Text(text = "price = ${data.price}")
        Text(text = "counter = ${data.count.value}")
    }
}


@Preview
@Composable
fun PreviewOfBasketPage() {
    BasketPage(
        items = listOf(
            ItemModel(
                "001",
                "Apple",
                "",
                "",
                "$100"
            )
        ), {})
}

