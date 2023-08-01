package com.example.testingapi

import java.util.Date

data class ApiVariables (
    val id :Int,
    val answer :String,
    val question :String,
    val value :Int,
    val airdate :Date,
    val created_at :Date,
    val updated_at :Date,
    val category_id :Int,
    val game_id :Int,
    val invalid_count :String,
    val category :Category
)