package ir.mostafaghanbari.pricechecker.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ItemModel::class], version = 1)
abstract class RoomDB:RoomDatabase() {

    abstract fun itemsDAO(): ItemsDAO

}