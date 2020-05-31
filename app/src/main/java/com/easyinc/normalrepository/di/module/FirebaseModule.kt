package com.easyinc.normalrepository.di.module

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FirebaseModule {

    @Singleton
    @Provides
    fun provideFirebaseDatabase(): FirebaseDatabase{
        return FirebaseDatabase.getInstance()
    }

    @Singleton
    @Provides
    fun provideDatabaseReference(firebaseDatabase: FirebaseDatabase): DatabaseReference{
        return firebaseDatabase.reference
    }

    @Singleton
    @Provides
    fun provideFirebaseStorage(): FirebaseStorage{
        return FirebaseStorage.getInstance()
    }

    @Singleton
    @Provides
    fun provideStorageReference(firebaseStorage: FirebaseStorage): StorageReference{
        return firebaseStorage.reference
    }

}