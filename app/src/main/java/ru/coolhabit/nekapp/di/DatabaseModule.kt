package ru.coolhabit.nekapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
//    @Singleton
//    @Provides
//    fun provideFilmDao(context: Context) =
//        Room.databaseBuilder(
//            context,
//            AppDatabase::class.java,
//            "film_db"
//        ).addMigrations(MIGRATION_1_2)
//            .build().filmDao()
//
//    @Provides
//    @Singleton
//    fun provideRepository(filmDao: FilmDao) = MainRepository(filmDao)
//
//    companion object {
//        val MIGRATION_1_2 = object : Migration(1, 2) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                database.execSQL("ALTER TABLE cached_films ALTER COLUMN poster_path STRING")
//            }
//        }
//    }
}