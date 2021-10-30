package ir.mostafaghanbari.pricechecker.view.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ir.mostafaghanbari.pricechecker.view.theme.PriceCheckerTheme

@Composable
fun DialogInsertIdentifier(
    onOk: (id: String) -> Unit,
    onCancel: () -> Unit
) {
    var id = ""
    Card(elevation = 6.dp) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Please Insert Item Identifier")
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = "",
                onValueChange = { id = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii),
                label = { Text(text = "Id") }
            )

            Row(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                OutlinedButton(onClick = onCancel) {
                    Icon(imageVector = Icons.Rounded.Cancel, contentDescription = "Cancel")
                    Text(text = "Cancel")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { onOk(id) }) {
                    Icon(imageVector = Icons.Rounded.Add, contentDescription = "Add Item")
                    Text(text = "Add")
                }
            }

        }
    }
}

@Composable
fun DialogDeleteItem(
    onOk: () -> Unit,
    onCancel: () -> Unit
) {
    Card(elevation = 6.dp) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Delete, Item?")
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                OutlinedButton(onClick = onCancel) {
                    Icon(imageVector = Icons.Rounded.Cancel, contentDescription = "Cancel")
                    Text(text = "Cancel")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = onOk) {
                    Icon(imageVector = Icons.Rounded.Delete, contentDescription = "Add Item")
                    Text(text = "Delete")
                }
            }

        }
    }
}

@Preview
@Composable
fun PreviewOfInsertIdentifier() {
    PriceCheckerTheme {
        DialogInsertIdentifier(onOk = {}) {

        }
    }
}

@Preview
@Composable
fun PreviewOfDeleteItem() {
    PriceCheckerTheme {
        DialogDeleteItem(onOk = {}) {

        }
    }
}

