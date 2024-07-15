package com.nandani.project_work.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import java.util.concurrent.Executors

@Database(entities = [QuizQuestion::class, UserAttempts::class], version = 1)
abstract class QuizDatabase : RoomDatabase() {
    abstract fun quizDao(): QuizDao

    companion object {
        var quizDatabase: QuizDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): QuizDatabase {
            if (quizDatabase == null) {
                quizDatabase = Room.databaseBuilder(context, QuizDatabase::class.java, "quiz")
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            Executors.newSingleThreadScheduledExecutor().execute(Runnable {
                                getDatabase(context).quizDao().insertAllQuizQuestions(QuizQuestion.populateData())
                            })
                        }
                    }).build()
            }
            return quizDatabase!!
        }

    }

}