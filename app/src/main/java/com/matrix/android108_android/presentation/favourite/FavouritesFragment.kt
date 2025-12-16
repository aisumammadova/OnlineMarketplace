package com.matrix.android108_android.presentation.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.matrix.android108_android.databinding.FragmentFavouritesBinding


class FavouritesFragment : Fragment() {
    lateinit var binding: FragmentFavouritesBinding
    lateinit var viewModel: FavouriteViewModel
    //private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentFavouritesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(FavouriteViewModel::class.java)
        viewModel.loadFavs()
        observers()

    }
    fun observers(){
        viewModel.favs.observe(viewLifecycleOwner){
            favs->
            val adapter = FavouriteAdapter(favs){
                productEntity ->
                viewModel.itemClick(productEntity)
            }
            binding.favRcy.adapter =adapter
            binding.favRcy.layoutManager = LinearLayoutManager(requireContext())
        }

    }







}