package com.easyinc.normalrepository.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
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
import kotlinx.android.synthetic.main.edit_product_toolbar.*
import kotlinx.android.synthetic.main.fragment_edit_product.*
import java.io.File
import javax.inject.Inject


class EditProductFragment : BaseFragment() {

    private var imageUri: Uri? = null

    private lateinit var product: ProductEntity

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<ProductViewModel>
    private val productViewModel by androidLazy {
        getViewModel<ProductViewModel>(viewModelFactory)
    }

    override fun layoutId(): Int {
        return R.layout.fragment_edit_product
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        product = requireArguments().getParcelable("product")!!
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        drawData()

        edit_image.setOnClickListener {
            pickImage()
        }

        save_product.setOnClickListener {
            saveProduct()
        }

        edit_product_backpress.setOnClickListener {
            activity?.onBackPressed()
        }

        observeEditProduct()

    }

    private fun observeEditProduct() {
        productViewModel.observeUpdateProduct().observe(viewLifecycleOwner, Observer {
            when(it.status){
                Resource.Status.SUCCESS -> {
                    hideProgressBar()
                    view?.snack("Product has been edited successfully.")
                    navController.navigate(R.id.action_editProductFragment_to_productsFragment)
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

    private fun saveProduct() {
        val title = product_title.text.toString()
        val price = product_price.text.toString()

        product.name = title
        product.price = price

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(price)){
            view?.snack("Please fill all information about the product.")
        }else if (!isConnected)
            view?.snack("Please, check your internet connection.")
        else if (imageUri == null) {

            showProgressBar()
            val oldUri = Uri.parse(product.image)
            productViewModel.updateProduct(
                UpdatedProduct(product, oldUri, false)
            )

        } else {

            showProgressBar()
            productViewModel.updateProduct(
                UpdatedProduct(product, imageUri!!, true)
            )
    }

}

    private fun drawData() {
        product_title.setText(product.name)
        product_price.setText(product.price)

        requestManager.load(product.image)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(product_image)
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
                val file = File(uri?.path ?: Uri.parse(product.image).path)
                val compressedFile = ImageZipper(context).compressToFile(file)
                imageUri = Uri.fromFile(compressedFile)

                product_image.setImageURI(uri)
            }
        }
    }

    private fun showProgressBar(){
        edit_product_progress.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        edit_product_progress.visibility = View.GONE
    }

}