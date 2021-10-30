package ir.mostafaghanbari.pricechecker.viewmodel

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.mostafaghanbari.pricechecker.model.ItemModel
import ir.mostafaghanbari.pricechecker.model.RoomDB
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val db: RoomDB
) : ViewModel() {

    private val _actionErrors = MutableLiveData<String>()
    val actionErrors: LiveData<String> = _actionErrors

    private val _orderItems = MutableLiveData(listOf<ItemModel>())
    val orderItems: LiveData<List<ItemModel>> = _orderItems

    private val _showDeleteDialog = MutableLiveData(false)
    val showDeleteDialog: LiveData<Boolean> = _showDeleteDialog

    private val _totalPrice = MutableLiveData("$0")
    val totalPrice = _totalPrice


    private fun changeTotalPrice(increase: Boolean, amountString: String?) {
        val currentPriceString = totalPrice.value?.replace("$", "")
        val currentPrice = currentPriceString?.toFloat() ?: 0f

        val amount = amountString?.replace("$", "")?.toFloat() ?: 0f

        val newPrice = if (increase)
            currentPrice + amount
        else
            currentPrice - amount

        _totalPrice.value = "$$newPrice"
    }


    fun newItemScanned(id: String) {
        val item = orderItems.value?.find { i -> i.id == id }
        if (item != null)
            increaseItemCounter(id)
        else {
            addItemToOrder(id)
        }
    }

    private fun addItemToOrder(id: String) {
        viewModelScope.launch {
            val item = db.itemsDAO().selectItem(id)
            if (item == null)
                _actionErrors.value = ActionErrors.ItemNotFound.name
            else {
                _orderItems.value = listOf(item).plus(_orderItems.value ?: listOf())
                changeTotalPrice(true, item.price)
            }
        }
    }

    fun increaseItemCounter(id: String) {
        val item = orderItems.value?.find { i -> i.id == id }
        item?.count?.value = item?.count?.value?.inc() ?: 1
        changeTotalPrice(true, item?.price)
    }

    fun decreaseItemCounter(id: String) {
        val item = orderItems.value?.find { i -> i.id == id }
        if (item?.count?.value == 1) {
            _showDeleteDialog.value = true
        } else {
            item?.count?.value = item?.count?.value?.dec() ?: 1
            changeTotalPrice(false, item?.price)
        }
    }

    fun storeOrder() {
        //todo
    }

    enum class ActionErrors {
        ItemNotFound
    }

}