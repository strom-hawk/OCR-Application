package com.example.ocrapplication.di

import android.app.Application
import android.content.Context
import com.example.datalayer.repositories.TextRecognizerRepoImpl
import com.example.domainlayer.repositories.TextRecognizerRepo
import com.example.domainlayer.usecases.TextRecognizerUseCase
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OCRModule {
    @Provides
    @Singleton
    fun provideApplicationContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideTextRecognizer(): TextRecognizer {
        return TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    }

    @Provides
    @Singleton
    fun providesTextRecognizerRepository(recognizer: TextRecognizer, context: Context): TextRecognizerRepo =
        TextRecognizerRepoImpl(
            context = context,
            recognizer = recognizer,
        )

    @Provides
    @Singleton
    fun providesTextRecognizerUseCase(repo: TextRecognizerRepo) =
        TextRecognizerUseCase(repo)
}