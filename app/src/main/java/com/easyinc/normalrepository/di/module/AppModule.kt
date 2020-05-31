package com.easyinc.normalrepository.di.module

import android.R
import android.app.Application
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.easyinc.normalrepository.cache.ProductsDao
import com.easyinc.normalrepository.cache.ProductsDatabase
import com.easyinc.normalrepository.common.Constants
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideDatabase(application: Application): ProductsDatabase{
        return Room
            .databaseBuilder(application,ProductsDatabase::class.java, Constants.DB_NAME)
            .build()
    }

    @Singleton
    @Provides
    fun provideProductsDao(productsDatabase: ProductsDatabase): ProductsDao{
        return productsDatabase.productDao()
    }


    @Singleton
    @Provides
    fun provideGlideInstance(application: Application?): RequestManager? {
        return Glide.with(application!!)
    }

    @Singleton
    @Provides
    fun provideImagePicker(): CropImage.ActivityBuilder {
        return CropImage.activity().setGuidelines(CropImageView.Guidelines.ON)
    }


}