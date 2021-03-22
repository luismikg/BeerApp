package com.example.beersapp.domain

import com.example.beersapp.data.model.*
import com.example.beersapp.valueObject.Resource

interface DataSource {
    suspend fun getDataFromServer(page: String): Resource<Bar>
    suspend fun getFavoriteItemsRoom(): Resource<List<FavoriteItemEntity>>
    suspend fun insertFavoriteItemRoom(favoriteItemEntity: FavoriteItemEntity)
    suspend fun insertItemRoom(itemEntity: ItemEntity)
    suspend fun getItemsRoom(): Resource<List<ItemEntity>>
    suspend fun removeItemFromFavoriteRoom(id: Int)
}