package com.example.testingapi

import java.util.Date

data class Category(
    val id :Int,
    val title :String,
    val created_at : Date,
    val updated_at :Date,
    val clues_count :Int,
)