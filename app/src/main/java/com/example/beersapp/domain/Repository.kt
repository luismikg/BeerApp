package com.example.beersapp.domain

import com.example.beersapp.data.model.*
import com.example.beersapp.valueObject.Resource

interface Repository {
    suspend fun getDataFromServer(page: String): Resource<Bar>
    suspend fun getFavorites(): Resource<List<BarItem>>
    suspend fun removeItemFromFavorite(id: Int)
    suspend fun insertFavoriteItem(favoriteItemEntity: FavoriteItemEntity)
    suspend fun insertItem(itemEntity: ItemEntity)
    suspend fun getItems(): Resource<Bar>
}