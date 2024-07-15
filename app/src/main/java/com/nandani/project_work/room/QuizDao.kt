package com.nandani.project_work.room

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery

@Dao
interface QuizDao {

    @Query("select * from QuizQuestion where type=:type")
    fun getQuizQuestion(type:Int) : List<QuizQuestion>

    @Insert
    fun insertQuizQuestion(vararg quizQuestion: QuizQuestion)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insertAllQuizQuestions(quizQuestion: List<QuizQuestion>)


    @RawQuery(observedEntities = arrayOf(QuizQuestion::class))
    fun getCount(query: SupportSQLiteQuery): Int


    @Query("select * from UserAttempts")
    fun getUserAttempts() : List<UserAttempts>

    @Insert
    fun insertUserAttempt(vararg userAttempts: UserAttempts)

    @Query("Delete from UserAttempts")
    fun deleteScores()



}