package ir.mostafaghanbari.pricechecker.view.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.rememberImagePainter
import ir.mostafaghanbari.pricechecker.model.ItemModel
import ir.mostafaghanbari.pricechecker.view.theme.PriceCheckerTheme

@Composable
fun BasketPage(
    items: List<ItemModel>,
    totalPrice: String,
    showDeleteDialog: Boolean,
    onScan: () -> Unit,
    onInsertIdentifier: (id:String) -> Unit,
    onIncrease: (ItemModel) -> Unit,
    onDecrease: (ItemModel) -> Unit,
    onDelete: (delete: Boolean) -> Unit,
    onBack: () -> Unit
) {

    var showInsertIdentifierDialog by remember {
        mutableStateOf(false)
    }

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colors.primaryVariant,
    ) {
        Column {

            if (showDeleteDialog)
                Dialog(onDismissRequest = { onDelete(false) }) {
                    DialogDeleteItem(onOk = { onDelete(true) }, onCancel = { onDelete(false) })
                }

            if (showInsertIdentifierDialog)
                Dialog(onDismissRequest = { showInsertIdentifierDialog = false }) {
                    DialogInsertIdentifier(
                        onOk = { showInsertIdentifierDialog = false; onInsertIdentifier(it)},
                        onCancel = { showInsertIdentifierDialog = false }
                    )
                }

            AppBar(totalPrice, onBack)
            ItemList(items, onIncrease, onDecrease, Modifier.weight(1f))
            Buttons(onScan, {showInsertIdentifierDialog = true})
        }
    }
}

@Composable
fun AppBar(totalPrice: String, onBack: () -> Unit) {
    TopAppBar() {
        IconButton(onClick = onBack) {
            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back")
        }
        TotalPrice(totalPrice = totalPrice)
    }
}

@Composable
fun Buttons(onScan: () -> Unit, onInsertIdentifier: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = onInsertIdentifier, modifier = Modifier
                .padding(end = 4.dp)
                .weight(0.5f)
        ) {
            Icon(imageVector = Icons.Filled.Input, contentDescription = "Insert Identifier")
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "Insert Id")
        }
        Button(
            onClick = onScan, modifier = Modifier
                .padding(start = 4.dp)
                .weight(0.5f)
        ) {
            Icon(imageVector = Icons.Filled.QrCodeScanner, contentDescription = "Scan QRCode")
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "Scan QrCode")
        }
    }
}

@Composable
fun TotalPrice(totalPrice: String) {
    Text(
        text = "Total Price: $totalPrice",
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Medium)
    )
}

@Composable
fun ItemList(
    data: List<ItemModel>,
    onIncrease: (ItemModel) -> Unit,
    onDecrease: (ItemModel) -> Unit,
    weight: Modifier
) {
    LazyColumn(
        modifier = weight
            .clip(
                RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomEnd = 8.dp,
                    bottomStart = 8.dp
                )
            )
            .background(color = Color.White)
            .fillMaxWidth()
    ) {
        items(data) { item ->
            Item(
                item,
                { onIncrease(item) },
                { onDecrease(item) }
            )
        }
    }
}


@Composable
fun Item(
    data: ItemModel,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        elevation = 6.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TopOfTheItem(data)
            BottomOfTheItem(
                data,
                onIncrease,
                onDecrease
            )
        }
    }
}

@Composable
fun BottomOfTheItem(
    data: ItemModel,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "$${data.totalPrice.value}",
            style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Medium),
            modifier = Modifier.weight(1f)
        )
        CounterSection(
            data.count.value,
            Modifier.padding(start = 8.dp, end = 8.dp),
            onIncrease,
            onDecrease
        )

    }
}

@Composable
fun CounterSection(
    value: Int,
    modifier: Modifier,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onDecrease,
            modifier = Modifier
                .clip(CircleShape)
                .background(Color.Red),
        ) {
            Icon(
                imageVector = Icons.Rounded.Remove,
                contentDescription = "decrease"
            )
        }

        Text(
            text = value.toString(),
            modifier = Modifier
                .padding(start = 4.dp, end = 4.dp)
                .width(24.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.subtitle1
        )

        IconButton(
            onClick = onIncrease,
            Modifier
                .clip(CircleShape)
                .background(Color.Green)
        ) {
            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = "increase"
            )
        }
    }
}

@Composable
fun TopOfTheItem(data: ItemModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier
                .size(60.dp),
            shape = CircleShape,
            color = MaterialTheme.colors.background
        ) {
            Image(
                painter = rememberImagePainter(data = data.thumbnail),
                contentDescription = "${data.name} thumbnail",
            )
        }
        Text(
            text = data.name,
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}


@Preview
@Composable
fun PreviewOfBasketPage() {
    PriceCheckerTheme {
        BasketPage(
            items = listOf(
                ItemModel(
                    "001",
                    "Apple",
                    "",
                    "https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/apple/237/red-apple_1f34e.png",
                    "$100"
                )
            ), "", false, {}, {}, {}, {}, {}, {})
    }
}

