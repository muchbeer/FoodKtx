package raum.muchbeer.foodktx.di.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import raum.muchbeer.foodktx.BuildConfig
import raum.muchbeer.foodktx.api.FoodInstance
import raum.muchbeer.foodktx.api.FoodService
import raum.muchbeer.foodktx.repository.datasource.NetworkDataSource
import raum.muchbeer.foodktx.repository.impl.NetworkDataSourceImpl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun providesGSONBuilder(): Gson {
        return GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(gson: Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
    }


@Singleton
@Provides
fun provideFoodService() : FoodService {
    return FoodInstance.foodInstance()
}


    @Singleton
    @Provides
    fun provideNetworkDataSource(dataService: FoodService): NetworkDataSource {
        return NetworkDataSourceImpl(dataService)
    }


}