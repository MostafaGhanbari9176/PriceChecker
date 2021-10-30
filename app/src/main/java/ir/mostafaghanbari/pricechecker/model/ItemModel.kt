package ir.mostafaghanbari.pricechecker.model

import androidx.lifecycle.MutableLiveData
import androidx.room.*

@Entity(tableName = "Items")
data class ItemModel(
    @PrimaryKey
    val id: String,
    val name: String,
    val qrUrl: String,
    val thumbnail: String,
    val price: String,
    @Ignore
    var count: MutableLiveData<Int> = MutableLiveData(1)
)

@Dao
interface ItemsDAO {

    @Query("select * from Items where id == :id")
    fun selectItem(id: String): ItemModel?

}
