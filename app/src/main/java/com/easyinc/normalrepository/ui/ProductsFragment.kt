package com.easyinc.normalrepository.ui

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.easyinc.mappractice.common.Resource

import com.easyinc.normalrepository.R
import com.easyinc.normalrepository.base.fragment.BaseFragment
import com.easyinc.normalrepository.common.extentions.androidLazy
import com.easyinc.normalrepository.common.extentions.snack
import com.easyinc.normalrepository.presentation.ProductsViewModel
import com.easyinc.normalrepository.ui.adapter.ProductsAdapter
import com.easyinc.tasking.common.viewmodel.ViewModelFactory
import com.easyinc.tasking.common.viewmodel.getViewModel
import kotlinx.android.synthetic.main.fragment_products.*
import kotlinx.android.synthetic.main.products_toolbar.*
import javax.inject.Inject

class ProductsFragment : BaseFragment() {


    @Inject
    lateinit var productsAdapter: ProductsAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<ProductsViewModel>
    private val productViewModel by androidLazy {
        getViewModel<ProductsViewModel>(viewModelFactory)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productViewModel.getProducts()

    }

    override fun layoutId(): Int {
        return R.layout.fragment_products
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        showProgressBar()

        handleAddProduct()

        initRecycler()

        handleAdapterClickListener()

        observeProducts()
    }

    private fun initRecycler(){
        products_recycler.adapter = productsAdapter
        products_recycler.setHasFixedSize(true)
        products_recycler.itemAnimator = null
        products_recycler.layoutManager = GridLayoutManager(context,2)
    }

    private fun handleAddProduct(){
        add_product.setOnClickListener {
            navController.navigate(R.id.action_productsFragment_to_addProductFragment)
        }
    }

    private fun handleAdapterClickListener(){
        productsAdapter.clickListener = {
            if (it != ""){
                val bundle = bundleOf("productId" to it)
                navController.navigate(R.id.action_productsFragment_to_productFragment,bundle)
            } else {
                view?.snack("Error has been occurred, please try again later.")
            }
        }
    }

    private fun observeProducts() {
        productViewModel.observeProducts().observe(viewLifecycleOwner, Observer {
            when(it.status){
                Resource.Status.SUCCESS -> {
                    hideProgressBar()


                    //val recyclerViewState = products_recycler.layoutManager?.onSaveInstanceState()

                    productsAdapter.submitList(it.data)

                    //productsAdapter.notifyItemRangeChanged(0, productsAdapter.itemCount)

                    //productsAdapter.notifyDataSetChanged()
                    //products_recycler.layoutManager?.onRestoreInstanceState(recyclerViewState)


                }
                Resource.Status.ERROR -> {
                    hideProgressBar()
                    view?.snack("Error has been occurred.")
                }
                Resource.Status.LOADING -> {
                    showProgressBar()
                }
            }
        })
    }


    private fun showProgressBar(){
        products_progress.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        products_progress.visibility = View.GONE
    }

}
