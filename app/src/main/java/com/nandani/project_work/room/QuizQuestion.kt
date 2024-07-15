package com.nandani.project_work.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity
@TypeConverters(Converters::class)
class QuizQuestion(
    @PrimaryKey
    var id: Int? = 0,

    @ColumnInfo(name = "question")
    var question: String? = null,

    @ColumnInfo(name = "image1")
    var image1: String? = null,

    @ColumnInfo(name = "image2")
    var image2: String? = null,

    @ColumnInfo(name = "image3")
    var image3: String? = null,

    @ColumnInfo(name = "options")
    var options: List<String>? = mutableListOf(),

    @ColumnInfo(name = "correctIndex")
    var correctIndex: Int? = null,

    @ColumnInfo(name = "type")
    var type: Int? = null) {
    companion object {
        fun populateData(): List<QuizQuestion> {
            return mutableListOf(
                QuizQuestion(
                    1, "What Are Three Primary Colors?", null,
                    null, null, mutableListOf("Blue,Yellow,Red", "Green,Red,Orange", "Red,Orange,Blue", "Orange,Yellow,Blue"), 0,0
                ),

                QuizQuestion(
                    2, "What Are Three Secondary Colors?",
                    null,
                    null, null, mutableListOf("Purple,Black,White", "Orange,White,Grey", "Orange,Green,Purple", "Pink,Green,Purple"), 2,0
                ),

                QuizQuestion(
                    3, "By Mixing Red , Blue And Yellow We Get Black?",
                    null,
                    null, null, mutableListOf("True", "False",), 0,0
                ),
                QuizQuestion(
                    4, "What color is found on 75% of world flags?",
                    null,
                    null, null, mutableListOf("Red", "Black", "Purple", "Orange"), 0,0
                ),
                QuizQuestion(
                    5, "What Is The Combination Of Colors Of Our Indian Flag?" ,
                    null,
                    null, null,  mutableListOf("Green,White,Saffron", "Saffron,White,Green", "Saffron,Blue,White", "Green,Blue,White"), 1,0
                ),
                QuizQuestion(
                    6, "How many colours does rainbow have?",
                    null,
                    null,null, mutableListOf("5", "8", "9", "7"), 3,0
                ),
                QuizQuestion(
                    7, "Question", "box2",
                    "box3", null, mutableListOf("Blue", "Orange", "Pink", "Yellow"), 1,1
                ),
                QuizQuestion(
                    8, "Question",
                    "box1",
                    "box3", null, mutableListOf("Brown", "Black", "Purple", "Green"), 2,1
                ),
                QuizQuestion(
                    9, "Question",
                    "box1",
                    "box2", null, mutableListOf("Black", "Red", "Pink", "Green"), 3,1
                ),

                QuizQuestion(
                    10, "Question", "box3",
                    "box2", "box1", mutableListOf("Blue", "Black", "Green", "Brown"), 3,1
                ),
                QuizQuestion(
                    11, "Question", "box3",
                    "box6", "box1", mutableListOf("Black", "Purple", "Gray", "Brown"), 0,1
                ),
                QuizQuestion(
                    12, "Question", "box4",
                    "box5", "box1", mutableListOf("Brown", "Black", "Chocolate", "Gray"), 3,1
                )
                ,QuizQuestion(
                    13, "Question", "ic__550353656",
                    "ic_k", null, mutableListOf("Red", "Pink", "Yellow", "White"), 1,2
                ),QuizQuestion(
                    14, "Question", "ic_g",
                    "ic_sunrays_svgrepo_com", null, mutableListOf("Gray", "Green", "Black", "Magenta"), 0,2
                ),QuizQuestion(
                    15, "Question", "ic_y",
                    "ic_scoop_it_svgrepo_com", null, mutableListOf("Red", "Pink", "Brown", "White"), 3,2
                ),QuizQuestion(
                    16, "Question", "ic_yell",
                    "ic_low_volume", null, mutableListOf("Red", "Pink", "Yellow", "White"), 2,2
                ),QuizQuestion(
                    17, "Question", "ic_b_letter_svgrepo_com",
                    "ic__04084", "ic_n", mutableListOf("Pink", "Brown", "Green", "White"), 1,2
                ),QuizQuestion(
                    18, "Question", "ic_mother_svgrepo_com",
                    "ic_gentleman_svgrepo_com", "ic_tata_logo", mutableListOf("Magenta", "Black", "Pink", "White"), 0,2
                )
            )
        }
    }
}