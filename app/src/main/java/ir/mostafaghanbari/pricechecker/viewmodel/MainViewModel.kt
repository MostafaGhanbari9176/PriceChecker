package ir.mostafaghanbari.pricechecker.viewmodel

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.mostafaghanbari.pricechecker.model.ItemModel
import ir.mostafaghanbari.pricechecker.model.RoomDB
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val db: RoomDB
) : ViewModel() {

    val actionErrors = MutableLiveData<String>()
    val orderItems = MutableLiveData<MutableList<ItemModel>>(mutableListOf())
    val totalPrice: LiveData<String> = Transformations.map(orderItems) { orderItems ->
        var price = 0

        var itemPrice = ""
        orderItems.forEach { i ->
            itemPrice = i.price.replace("$", "")

            if (itemPrice.isDigitsOnly())
                price += itemPrice.toInt()
            else
                return@map "Error on data"
        }

        "$price$"
    }
    val showDeleteDialog = MutableLiveData(false)


    fun newItemScanned(id: String) {
        val item = orderItems.value?.find { i -> i.id == id }
        if (item != null)
            increaseItemCounter(id)
        else
            addItemToOrder(id)
    }

    private fun addItemToOrder(id: String) {
        val item = db.itemsDAO().selectItem(id)
        if (item == null)
            actionErrors.value = ActionErrors.ItemNotFound.name
        else
            orderItems.value?.add(item)
    }

    fun increaseItemCounter(id: String) {
        val item = orderItems.value?.find { i -> i.id == id }
        item?.count?.value?.plus(1)
    }

    fun decreaseItemCounter(id: String) {
        val item = orderItems.value?.find { i -> i.id == id }
        if (item?.count?.value == 1) {
            showDeleteDialog.value = true
        } else {
            item?.count?.value?.minus(1)
        }
    }

    fun storeOrder(){
        //todo
    }

    enum class ActionErrors {
        ItemNotFound
    }

}