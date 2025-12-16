package com.matrix.android108_android.presentation.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.chip.Chip
import com.matrix.android108_android.R
import com.matrix.android108_android.databinding.FragmentProductsBinding

class ProductsFragment : Fragment() {
    lateinit var binding: FragmentProductsBinding
    private lateinit var productAdapter:ProductAdapter

    lateinit var viewModel: ProductViewModel
//    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.findViewById<BottomNavigationView>(R.id.bottomNav)?.visibility = View.VISIBLE
        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        productAdapter = ProductAdapter(emptyList()){
            productDto ->
            viewModel.itemClick(productDto)
        }
        binding.productRcy.apply {
            adapter = productAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        viewModel.fillCategories()
        viewModel.loadProducts()
        observers()




    }

    fun observers(){
        viewModel.categories.observe(viewLifecycleOwner){
            categories->
            bindDataToChip(categories)
        }
        viewModel.products.observe(viewLifecycleOwner){ products ->

                    productAdapter.updateData(products)


        }
        viewModel.loading.observe(viewLifecycleOwner){
            isLoading->
            binding.progressBar.visibility = if(isLoading) View.VISIBLE else View.GONE
            binding.productRcy.visibility = if (isLoading) View.GONE else View.VISIBLE
        }
    }

    fun bindDataToChip(categories: List<String>){
        binding.chipGroup.removeAllViews()
        categories.forEach { // do not use foreach loop for showing products it causes lagging
            category ->

            val chip = Chip(requireContext()).apply {
                text = category
                isCheckable = true
                isClickable = true
                binding.chipGroup.addView(this)
        }
        }
        binding.chipGroup.setOnCheckedStateChangeListener { // rely on chip group and separate ids of categories
            // setOnCheckedChangeListener deprecated version suitable for single selection

                group, checkedIds -> // better for multiple selection
            if(checkedIds.isEmpty()){
                viewModel.loadProducts()
            }
            else{

                val category = group.findViewById<Chip>(checkedIds[0]).text.toString()
                viewModel.loadProductsByCategory(category)
            }


    }
    }
}