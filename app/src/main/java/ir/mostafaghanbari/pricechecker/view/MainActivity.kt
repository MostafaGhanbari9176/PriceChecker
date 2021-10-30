package ir.mostafaghanbari.pricechecker.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import dagger.hilt.android.AndroidEntryPoint
import ir.mostafaghanbari.pricechecker.model.ItemModel
import ir.mostafaghanbari.pricechecker.view.pages.BasketPage
import ir.mostafaghanbari.pricechecker.view.pages.MainPage
import ir.mostafaghanbari.pricechecker.view.theme.PriceCheckerTheme
import ir.mostafaghanbari.pricechecker.viewmodel.MainViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private lateinit var scannerLauncher: ActivityResultLauncher<ScanOptions>
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initScannerLauncher()

        setContent {
            PriceCheckerTheme {
                MyApp(mainViewModel, {
                    launchScanner()
                }, { id ->
                    mainViewModel.newItemScanned(id)
                })
            }
        }
    }

    private fun initScannerLauncher() {
        scannerLauncher = registerForActivityResult(ScanContract(),
            object : ActivityResultCallback<ScanIntentResult> {
                override fun onActivityResult(result: ScanIntentResult?) {
                    if (result?.contents == null)
                        Toast.makeText(this@MainActivity, "Failed", Toast.LENGTH_LONG).show()
                    else
                        mainViewModel.newItemScanned(result.contents)
                }
            })
    }

    private fun launchScanner() {
        scannerLauncher.launch(ScanOptions().apply {
            this.setOrientationLocked(false)
            this.setBarcodeImageEnabled(true)
            this.setPrompt("Prompt")
        })
    }

}

@Composable
fun MyApp(
    viewModel: MainViewModel,
    onScan: () -> Unit,
    onInsertIdentifier: (id: String) -> Unit
) {
    val items: List<ItemModel> by viewModel.orderItems.observeAsState(listOf())
    val totalPrice: String by viewModel.totalPrice.observeAsState(initial = "$0")
    val showDeleteDialog: Boolean by viewModel.showDeleteDialog.observeAsState(false)

    val navController = rememberNavController()

    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = "list"
    ) {
        composable("main") {
            MainPage()
        }
        composable("list") {
            BasketPage(
                items = items,
                totalPrice = totalPrice,
                showDeleteDialog = showDeleteDialog,
                onScan = onScan,
                onInsertIdentifier = onInsertIdentifier,
                onIncrease = { viewModel.increaseItemCounter(it.id) },
                onDecrease = { viewModel.decreaseItemCounter(it.id) },
                onDelete = { viewModel.onDelete(it) },
                onBack = {})
        }
    }
}