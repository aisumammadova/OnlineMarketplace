package com.matrix.android108_android.presentation.favourite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matrix.android108_android.roomdb.DatabaseClient
import com.matrix.android108_android.roomdb.ProductEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouriteViewModel: ViewModel() {

    private val productDao = DatabaseClient.instance.productDao()
    private val _favs= MutableLiveData<MutableList<ProductEntity>>()
    val favs: LiveData<MutableList<ProductEntity>> = _favs


    fun loadFavs(){

        viewModelScope.launch(Dispatchers.IO) {
            try{ val favs = productDao
                .getLikedProducts().toMutableList()
            _favs.postValue(favs)}
            catch (e: Exception){
                e.printStackTrace()
            }
            }
        }

    fun itemClick(product: ProductEntity){
        Log.d("ala xvatit", "loaded")
        viewModelScope.launch(Dispatchers.IO) {
            productDao.unlikeProduct(product)
        }

    }
}