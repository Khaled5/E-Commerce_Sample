package com.easyinc.normalrepository.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.developers.imagezipper.ImageZipper
import com.easyinc.mappractice.common.Resource
import com.easyinc.normalrepository.R
import com.easyinc.normalrepository.base.fragment.BaseFragment
import com.easyinc.normalrepository.common.extentions.androidLazy
import com.easyinc.normalrepository.common.extentions.snack
import com.easyinc.normalrepository.data.model.ProductEntity
import com.easyinc.normalrepository.domain.model.UpdatedProduct
import com.easyinc.normalrepository.presentation.ProductViewModel
import com.easyinc.tasking.common.viewmodel.ViewModelFactory
import com.easyinc.tasking.common.viewmodel.getViewModel
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.add_product_toolbar.*
import kotlinx.android.synthetic.main.fragment_add_product.*
import java.io.File
import javax.inject.Inject


class AddProductFragment : BaseFragment() {

    private var imageUri: Uri? = null
    private var spinnerItem: String? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<ProductViewModel>
    private val productViewModel by androidLazy {
        getViewModel<ProductViewModel>(viewModelFactory)
    }

    override fun layoutId(): Int {
        return R.layout.fragment_add_product
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        edit_image.setOnClickListener {
            pickImage()
        }

        save_product.setOnClickListener {
            getSpinner()
        }

        add_product_backpress.setOnClickListener {
            activity?.onBackPressed()
        }

        observeAddProduct()

    }

    private fun getSpinner(){

         spinnerItem = when (add_product_warehouse_spinner.selectedItemPosition) {
             1 -> resources.getString(R.string.gucci)
             2 -> resources.getString(R.string.polo)
             3 -> resources.getString(R.string.h_m)
             else -> ""
         }

        if (spinnerItem.equals(""))
            view?.snack("Please, choose a warehouse for your product.")
        else
            addProduct()
    }

    private fun pickImage(){
        picker.start(requireContext(), this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK){

                val  uri = result.uri
                val file = File(uri.path!!)
                val compressedFile = ImageZipper(context).compressToFile(file)
                imageUri = Uri.fromFile(compressedFile)

                product_image.setImageURI(uri)
            }
        }
    }

    private fun addProduct(){
        val title = product_title.text.toString()
        val price = product_price.text.toString()

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(price) || imageUri == null){
            view?.snack("Please fill all information about the product.")
        }else {
            if (isConnected){
                showProgressBar()
                val product = ProductEntity("",title,spinnerItem!!,"",price)
                productViewModel.addProduct(
                    UpdatedProduct(product, imageUri!!)
                )
            }else{
                view?.snack("Please, check your internet connection.")
            }
        }
    }

    private fun observeAddProduct(){
        productViewModel.observeAddProduct().observe(viewLifecycleOwner, Observer {
            when(it.status){
                Resource.Status.SUCCESS -> {
                    hideProgressBar()
                    view?.snack("Product has been added successfully.")
                    navController.navigate(R.id.action_addProductFragment_to_productsFragment)
                }
                Resource.Status.ERROR -> {
                    view?.snack("Error has been occurred, please try again later.")
                }
                Resource.Status.LOADING -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun showProgressBar(){
        add_product_progress.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        add_product_progress.visibility = View.GONE
    }


}