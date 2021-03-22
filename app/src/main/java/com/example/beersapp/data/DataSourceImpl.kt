package com.example.beersapp.data

import com.example.beersapp.data.model.*
import com.example.beersapp.domain.DataSource
import com.example.beersapp.domain.ItemDao
import com.example.beersapp.valueObject.Resource
import com.example.beersapp.valueObject.RetrofitClient
import javax.inject.Inject

class DataSourceImpl @Inject constructor(private val itemDao: ItemDao) : DataSource {

    override suspend fun getDataFromServer(page: String): Resource<Bar> {
        return Resource.Success(RetrofitClient.werService.getData(page))
    }

    override suspend fun getFavoriteItemsRoom(): Resource<List<FavoriteItemEntity>> {
        return Resource.Success(itemDao.getAllFavoriteItems())
    }

    override suspend fun insertFavoriteItemRoom(favoriteItemEntity: FavoriteItemEntity) {
        itemDao.insertFavoriteItem(favoriteItemEntity)
    }

    override suspend fun insertItemRoom(itemEntity: ItemEntity) {
        itemDao.insertItem(itemEntity)
    }

    override suspend fun getItemsRoom(): Resource<List<ItemEntity>> {
        return Resource.Success(itemDao.getAllItems())
    }

    override suspend fun removeItemFromFavoriteRoom(id: Int) {
        itemDao.removeItemFromFavorite(id)
    }
}