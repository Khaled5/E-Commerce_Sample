package com.easyinc.normalrepository.ui

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.easyinc.mappractice.common.Resource
import com.easyinc.normalrepository.R
import com.easyinc.normalrepository.base.fragment.BaseFragment
import com.easyinc.normalrepository.common.extentions.androidLazy
import com.easyinc.normalrepository.common.extentions.snack
import com.easyinc.normalrepository.data.model.ProductEntity
import com.easyinc.normalrepository.data.model.WarehouseEntity
import com.easyinc.normalrepository.presentation.ProductViewModel
import com.easyinc.tasking.common.viewmodel.ViewModelFactory
import com.easyinc.tasking.common.viewmodel.getViewModel
import kotlinx.android.synthetic.main.fragment_product.*
import kotlinx.android.synthetic.main.fragment_product.view.*
import kotlinx.android.synthetic.main.product_toolbar.*
import kotlinx.android.synthetic.main.product_toolbar.view.*
import javax.inject.Inject


class ProductFragment : BaseFragment(), View.OnClickListener {

    private var productId: String? = null

    private lateinit var product: ProductEntity
    private lateinit var warehouse: WarehouseEntity

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<ProductViewModel>
    private val productViewModel by androidLazy {
        getViewModel<ProductViewModel>(viewModelFactory)
    }

    override fun layoutId(): Int {
        return R.layout.fragment_product
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        productId = requireArguments().getString("productId")

        productViewModel.getProductAndThenWarehouse(productId!!)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        view.product_backpress.setOnClickListener(this)
        view.product_delete.setOnClickListener(this)
        view.product_edit.setOnClickListener(this)
        view.product_warehouse.setOnClickListener(this)

        showProgressBar()

        observeProduct()
        observeWarehouse()
        observeDeleteProduct()

    }

    private fun drawData(){
        requestManager
            .load(product.image)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(product_image)

        product_title.text = product.name
        product_price.text = "${product.price}$"
        product_warehouse.text = "Warehouse: ${warehouse.name}"
    }

    private fun observeProduct(){
        productViewModel.observeProduct().observe(viewLifecycleOwner, Observer {
            when(it.status){
                Resource.Status.SUCCESS -> {
                    product = it.data!!
                }

                Resource.Status.ERROR -> {
                    view?.snack("Error has been occurred.")
                    showProgressBar()
                }

                Resource.Status.LOADING -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun observeWarehouse(){
        productViewModel.observeWarehouse().observe(viewLifecycleOwner, Observer {
            when(it.status){
                Resource.Status.SUCCESS -> {
                    hideProgressBar()
                    warehouse = it.data!!
                    drawData()
                }

                Resource.Status.ERROR -> {
                    showProgressBar()
                    view?.snack("Error has been occurred.")
                }

                Resource.Status.LOADING -> {
                    showProgressBar()
                }
            }
        })
    }



    private fun observeDeleteProduct(){
        productViewModel.observeDeleteProduct().observe(viewLifecycleOwner, Observer {
            when(it.status){
                Resource.Status.SUCCESS -> {
                    hideProgressBar()
                    view?.snack("Product has been deleted successfully.")
                    activity?.onBackPressed()
                }

                Resource.Status.ERROR -> {
                    hideProgressBar()
                    view?.snack("Error has been occurred, please try again later.")
                }

                Resource.Status.LOADING -> {
                    showProgressBar()
                }
            }
        })
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.product_backpress -> activity?.onBackPressed()

            R.id.product_delete -> productViewModel.deleteProduct(productId!!)

            R.id.product_edit -> {
                if (product != null){
                    val bundle = bundleOf("product" to product)
                    navController.navigate(R.id.action_productFragment_to_editProductFragment,bundle)
                } else {
                    view.snack("Error has been occurred.")
                }
            }

            R.id.product_warehouse -> {
                if (warehouse != null){
                    val bundle = bundleOf("warehouse" to warehouse)
                    navController.navigate(R.id.action_productFragment_to_warehouseFragment,bundle)
                } else {
                    view.snack("Error has been occurred.")
                }
            }
        }
    }

    private fun showProgressBar(){
        product_progress.visibility = View.VISIBLE
        product_delete.visibility = View.GONE
        product_edit.visibility = View.GONE
    }

    private fun hideProgressBar(){
        product_progress.visibility = View.GONE
        product_delete.visibility = View.VISIBLE
        product_edit.visibility = View.VISIBLE
    }

}