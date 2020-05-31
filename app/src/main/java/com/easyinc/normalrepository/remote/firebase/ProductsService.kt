package com.easyinc.normalrepository.remote.firebase

import android.net.Uri
import com.easyinc.mappractice.common.Logger
import com.easyinc.normalrepository.remote.model.ProductModel
import com.easyinc.normalrepository.remote.model.WarehouseModel
import com.google.firebase.database.*
import com.google.firebase.storage.StorageReference
import io.reactivex.Completable
import io.reactivex.Observable
import java.util.concurrent.CancellationException
import javax.inject.Inject

class ProductsService @Inject constructor(private val databaseRef: DatabaseReference, private val storageRef: StorageReference): FirebaseProductsService {


    override fun getProducts(key: String): Observable<List<ProductModel>> {



        return Observable.create { subscriber ->

            val productList = mutableListOf<ProductModel>()

            val ref = databaseRef.child(Constants.FIREBASE_REF)
                .child(Constants.PRODUCT)


            ref.orderByKey().startAt(key).limitToFirst(15).addChildEventListener(object : ChildEventListener {
                    override fun onCancelled(databaseError: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                    override fun onChildMoved(
                        dataSnapshot: DataSnapshot,
                        previousChildName: String?
                    ) {
                        val product: ProductModel? = dataSnapshot.getValue(ProductModel::class.java)

                        subscriber.onNext(productList)
                    }

                    override fun onChildChanged(
                        dataSnapshot: DataSnapshot,
                        previousChildName: String?
                    ) {
                        val product: ProductModel? = dataSnapshot.getValue(ProductModel::class.java)

                        val prevProduct = productList.find { it.id == product?.id }
                        val prevProductIndex = productList.indexOf(prevProduct)
                        productList[prevProductIndex] = product!!

                        subscriber.onNext(productList)
                    }

                    override fun onChildAdded(
                        dataSnapshot: DataSnapshot,
                        previousChildName: String?
                    ) {
                        val message = dataSnapshot.getValue(ProductModel::class.java)
                        productList.add(message!!)

                        subscriber.onNext(productList)
                    }

                    override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                        val product: ProductModel? = dataSnapshot.getValue(ProductModel::class.java)

                        val prevProduct = productList.find { it.id == product?.id }
                        val prevProductIndex = productList.indexOf(prevProduct)

                        productList.removeAt(prevProductIndex)

                        subscriber.onNext(productList)
                    }
                })

        }
    }

    override fun getProduct(id: String): Observable<ProductModel> {
        var product: ProductModel? = null

        return Observable.create {subscriber ->

            databaseRef.child(Constants.FIREBASE_REF)
                .child(Constants.PRODUCT).child(id)
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        throw CancellationException(error.message)
                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot) {

                        product = dataSnapshot.getValue(ProductModel::class.java)
                        subscriber.onNext(product ?: ProductModel())
                    }

                })

        }
    }

    override fun addProduct(productModel: ProductModel, productImageUri: Uri): Completable {

        return Completable.defer {

            uploadProductWithImage(productModel,productImageUri,true)

            Completable.complete()
        }
    }

    override fun deleteProduct(productId: String): Completable {

        return Completable.defer {
            databaseRef.child(Constants.FIREBASE_REF)
                .child(Constants.PRODUCT).child(productId).setValue(null)

            Completable.complete()
        }
    }

    override fun getWarehouse(id: String): Observable<WarehouseModel> {
        var warehouse: WarehouseModel? = null

        return Observable.create {subscriber ->

            databaseRef.child(Constants.FIREBASE_REF)
                .child(Constants.WAREHOUSE).child(id)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        throw CancellationException(error.message)
                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        warehouse = dataSnapshot.getValue(WarehouseModel::class.java)

                        subscriber.onNext(warehouse ?: WarehouseModel())
                    }

                })

        }
    }

    override fun updateProduct(productModel: ProductModel, productImageUri: Uri, changedImage: Boolean): Completable {

        return Completable.defer {

            if (changedImage)
                uploadProductWithImage(productModel,productImageUri,false)
            else
                uploadProductDetails(productModel,false)

            Completable.complete()
        }
    }


    private fun uploadProductDetails(productModel: ProductModel,push: Boolean) {
        if (push){
            val id = databaseRef.push().key
            productModel.id = id!!
            databaseRef.child(Constants.FIREBASE_REF)
                .child(Constants.PRODUCT).child(productModel.id).setValue(productModel)
        }else
        databaseRef.child(Constants.FIREBASE_REF)
            .child(Constants.PRODUCT).child(productModel.id).setValue(productModel)
    }

    private fun uploadProductWithImage(productModel: ProductModel, productImageUri: Uri,push: Boolean){

        val storage = storageRef.child(Constants.FIREBASE_REF).child(Constants.PRODUCT).child(productModel.name)

        storage.putFile(productImageUri).continueWithTask {
            if (!it.isSuccessful){
                it.exception.let {e ->
                    throw e!!
                }
            }
            storage.downloadUrl
        }.addOnSuccessListener {
            val uri = it.toString()
            productModel.image = uri
            uploadProductDetails(productModel,push)
        }
    }

}

interface FirebaseProductsService{

    fun getProducts(key: String): Observable<List<ProductModel>>

    fun getProduct(id: String): Observable<ProductModel>

    fun addProduct(productModel: ProductModel, productImageUri: Uri): Completable

    fun deleteProduct(productId: String): Completable

    fun updateProduct(productModel: ProductModel, productImageUri: Uri, changedImage: Boolean): Completable

    fun getWarehouse(id: String): Observable<WarehouseModel>

}