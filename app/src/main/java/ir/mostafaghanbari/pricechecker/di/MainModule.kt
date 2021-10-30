package ir.mostafaghanbari.pricechecker.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.mostafaghanbari.pricechecker.model.RoomDB
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    @Singleton
    fun provide_database(@ApplicationContext ctx: Context) =
        Room.databaseBuilder(ctx, RoomDB::class.java, "price_checker")
            .createFromAsset("database.sql")
            .build()


}