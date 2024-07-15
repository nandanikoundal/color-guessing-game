package com.nandani.project_work

import android.graphics.drawable.Drawable

data class QuizQuestion(var id:Int?=0, var question:String?=null,
                        var image1: Drawable?= null, var image2:Drawable?= null, var image3:Drawable?= null,
                         var options: ArrayList<QuizQOptions>?= ArrayList(),
                         var correctIndex:Int?=null,
)
//data class QuizQuestion3(var id:Int?=0, var question:String?=null,
//    var image1: Drawable?= null, var image2:Drawable?= null,var image3:Drawable?= null,
//    var options: ArrayList<QuizQOptions>?= ArrayList(),
//    var correctIndex:Int?=null,
//)

data class QuizQOptions(var options:String?= null)