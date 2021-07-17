package raum.muchbeer.foodktx.di.api

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import raum.muchbeer.foodktx.db.FoodDao
import raum.muchbeer.foodktx.db.FoodDb
import raum.muchbeer.foodktx.repository.datasource.CacheDataSource
import raum.muchbeer.foodktx.repository.impl.CacheImpl
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) : FoodDb {
        return FoodDb.getFoodInstance(context)
    }


    @Singleton
    @Provides
    fun provideFoodDao(dataDB: FoodDb) : FoodDao {
        return dataDB.foodDao()
    }

    @Singleton
    @Provides
    fun provideDBDataSource(foodDao: FoodDao) : CacheDataSource {
        return CacheImpl(foodDao)
    }

}