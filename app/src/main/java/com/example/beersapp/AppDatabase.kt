package com.example.beersapp

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.beersapp.data.model.FavoriteItemEntity
import com.example.beersapp.data.model.ItemEntity
import com.example.beersapp.data.util.TypeConverter
import com.example.beersapp.domain.ItemDao

@Database(
    entities = [ItemEntity::class, FavoriteItemEntity::class], version = 1
)

@TypeConverters(TypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDao
}