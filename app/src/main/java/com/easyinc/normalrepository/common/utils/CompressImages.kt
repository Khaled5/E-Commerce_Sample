package com.easyinc.normalrepository.common.utils

import android.content.Context
import android.net.Uri
import com.developers.imagezipper.ImageZipper
import java.io.File
import java.io.IOException


class CompressImages {
    var file: File? = null
    var imageZipperFile: File? = null
    fun compressImage(
        context: Context?,
        resultUri: Uri?
    ): Uri {
        //file = MyUtils.getFile(context, resultUri)
        try {
            imageZipperFile = ImageZipper(context).compressToFile(file)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return Uri.fromFile(imageZipperFile)
    }
}