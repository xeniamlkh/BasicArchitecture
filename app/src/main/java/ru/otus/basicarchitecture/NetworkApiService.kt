package ru.otus.basicarchitecture

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST

private const val BASE_URL = "https://suggestions.dadata.ru/suggestions/"

private fun okHttpClient(
   apiKey: String = "9f3a95fd4664f39ca38a51afdedbd8b9b5bff444"
) = OkHttpClient().newBuilder()
    .addInterceptor(
        object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val request: Request = chain.request()
                    .newBuilder()
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .header("Authorization", "Token $apiKey")
                    .build()
                return chain.proceed(request)
            }
        }
    )

private val json = Json {
    ignoreUnknownKeys = true
}

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(okHttpClient().build())
    .addConverterFactory(
        json.asConverterFactory("application/json".toMediaType())
    )
    .build()

interface NetworkApiService {
    @POST("api/4_1/rs/suggest/address")
    suspend fun getHints(
        @Body query: AddressQuery
    ): AddressResponse
}

object NetworkApi {
    val retrofitService: NetworkApiService by lazy { retrofit.create(NetworkApiService::class.java) }
}