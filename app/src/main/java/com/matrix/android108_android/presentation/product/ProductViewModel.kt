package com.matrix.android108_android.presentation.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.matrix.android108_android.Dto.ProductDto
import com.matrix.android108_android.api.ProductsService
import com.matrix.android108_android.api.RetrofitClient
import com.matrix.android108_android.mapper.toDto
import com.matrix.android108_android.roomdb.DatabaseClient
import com.matrix.android108_android.roomdb.ProductEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class ProductViewModel : ViewModel() {

    private val productsService = RetrofitClient.retrofit.create(ProductsService::class.java)
    private val productDao = DatabaseClient.instance.productDao()
    private val _products = MutableLiveData<List<ProductDto>>()
    val products: LiveData<List<ProductDto>> = _products

    private val _categories = MutableLiveData<List<String>>()
    val categories: LiveData<List<String>> = _categories

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading
    fun fillCategories() {
        _loading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val categories = productsService.getCategoryList()
                _categories.postValue(categories)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _loading.postValue(false)
            }
        }

    }

    fun loadProductsByCategory(category: String) {
        _loading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = productsService.getProductsByCategory(category)
                val favList = productDao.getLikedProducts()
                response.body()?.products?.let { apiResult ->
                    val list = apiResult.map { product ->
                        val isLiked = favList.any { x -> x.id == product.id }
                        product.toDto(isLiked)
                    }
                    _products.postValue(list)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _loading.postValue(false)
            }


        }
    }

    fun loadProducts() {
        _loading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val favList = productDao.getLikedProducts()
                productsService.getList().body()?.let { apiResult ->
                    val result = apiResult.products.map {
                        val isLiked = favList.any { x -> x.id == it.id }
                        it.toDto(isLiked)
                    }
                    _products.postValue(result)

                }
            } catch (e: kotlin.Exception) {
                e.printStackTrace()
            } finally {
                _loading.postValue(false)
            }
        }
    }

    fun itemClick(productDto: ProductDto) {
        viewModelScope.launch(Dispatchers.IO) {

            if (productDto.isLiked) {

                val entity = ProductEntity(
                    productDto.id,
                    productDto.title,
                    productDto.price,
                    productDto.thumbnail,
                    true
                )
                productDao.likeProduct(entity)

            } else {
                val entity = ProductEntity(
                    productDto.id,
                    productDto.title,
                    productDto.price,
                    productDto.thumbnail,
                    false
                )
                productDao.unlikeProduct(entity)
            }
        }
    }
}


