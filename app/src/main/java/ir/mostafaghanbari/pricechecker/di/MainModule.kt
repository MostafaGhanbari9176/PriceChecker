package ir.mostafaghanbari.pricechecker.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import ir.mostafaghanbari.pricechecker.model.RoomDB

@Module
@InstallIn(ViewModelComponent::class)
object MainModule {

    @Provides
    fun provide_database(@ApplicationContext ctx:Context):RoomDB{
        val db = Room.databaseBuilder(ctx, RoomDB::class.java, "price_checker")
            .createFromAsset("database.sql")
            .build()

        return db
    }

}