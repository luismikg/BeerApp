package com.example.beersapp.domain

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.beersapp.data.model.FavoriteItemEntity
import com.example.beersapp.data.model.ItemEntity

@Dao
interface ItemDao {

    @Query("SELECT * FROM FavoriteItemEntity")
    suspend fun getAllFavoriteItems(): List<FavoriteItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteItem(favoriteItemEntity: FavoriteItemEntity)

    @Query("SELECT * FROM ItemEntity")
    suspend fun getAllItems(): List<ItemEntity>

    @Query("DELETE FROM FavoriteItemEntity WHERE id = :id")
    suspend fun removeItemFromFavorite(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(itemEntity: ItemEntity)
}