package com.example.nupyandroid.model

data class ChatBalloon(
    val id: Int = 0,
    val isNupy: Boolean,
    val content: String? = null,
    val content2: String? = null,
    val image: String? = null,
    val icon: String? = null
) {
    fun getFirstNupy(): Boolean = isNupy
    fun getTowNupy(): Boolean = isNupy && (content2 != null)
    fun getFirstUser(): Boolean = !isNupy && (content != null)
    fun getUserImage(): Boolean = !isNupy && (image != null)
}