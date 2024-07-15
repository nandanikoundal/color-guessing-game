package com.nandani.project_work.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.*

@Entity
@TypeConverters(DateConverter::class)
class UserAttempts(
    @PrimaryKey (autoGenerate = true)
    var id :Int =0,

    @ColumnInfo(name = "quizType")
    var quizType: Int? = null,

    @ColumnInfo(name = "totalAttempted")
    var totalAttempted: Int? = null,

    @ColumnInfo(name = "totalQuestions")
    var totalQuestions: Int? = null,

    @ColumnInfo(name = "score")
    var score: Int? = null,

    @ColumnInfo(name = "attemptedDate") var attemptedDate : Date?= null,

    )