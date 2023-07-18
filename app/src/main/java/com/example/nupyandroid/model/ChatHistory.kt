package com.example.nupyandroid.model

data class ChatHistory(
    val id: Int,
    val title: String,
    val icon: String,
    val description: String,
    val timeline: String,
    val complete: Boolean,
)