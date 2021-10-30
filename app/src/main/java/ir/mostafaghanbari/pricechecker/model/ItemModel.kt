package ir.mostafaghanbari.pricechecker.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.room.*

@Entity(tableName = "Items")
data class ItemModel(
    @PrimaryKey
    val id: String,
    val name: String,
    val qrUrl: String,
    val thumbnail: String,
    val price: String
){
    @Ignore
    var count: MutableState<Int> = mutableStateOf(1)
}

@Dao
interface ItemsDAO {

    @Query("select * from Items where id == :id")
    suspend fun selectItem(id: String): ItemModel?

}
