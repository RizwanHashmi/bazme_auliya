package com.zikre.bazmeauliya.di

import android.util.Base64
import android.util.Log
import com.google.gson.GsonBuilder
import com.zikre.bazmeauliya.api.ApiInterface
import com.zikre.bazmeauliya.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.MessageDigest
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Singleton
    @Provides
    fun okHttpClient(tokenInterceptor: TokenInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(300, TimeUnit.SECONDS)
            .connectTimeout(300, TimeUnit.SECONDS)
            .addInterceptor(tokenInterceptor) // ✅ Add the interceptor here
            .apply {
//                if (BuildConfig.DEBUG) {
                    // Log SSL Pin
                    addNetworkInterceptor { chain ->
                        val response = chain.proceed(chain.request())
                        val handshake = response.handshake
                        handshake?.peerCertificates?.forEach { cert ->
                            if (cert is X509Certificate) {
                                val pubKey = cert.publicKey.encoded
                                val sha256 = MessageDigest.getInstance("SHA-256").digest(pubKey)
                                val pin = "sha256/" + Base64.encodeToString(sha256, Base64.NO_WRAP)

                                val expiry = cert.notAfter
                                val subject = cert.subjectX500Principal.name

                                Log.d("SSL_CERT", "Subject: $subject")
                                Log.d("SSL_CERT", "Pin: $pin")
                                Log.d("SSL_CERT", "Expires on: $expiry")
                            }
                        }
                        response
                    }

                    // Log HTTP body
                    val logging = HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }
                    addInterceptor(logging)
//                }
            }
            .build()
    }

    var gson = GsonBuilder()
        .setLenient()
        .create()

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Singleton
    @Provides
    fun providerNoteApi(retrofit: Retrofit.Builder, okHttpClient: OkHttpClient): ApiInterface {
        return retrofit.client(okHttpClient).build().create(ApiInterface::class.java)
    }

}



