package com.example.randomfilmk

data class Movie(
    val name: String, val year: Int, val rating: Float, val age_limit: Int,
    val duration: Int, val genre: Array<String>
) {
    // поля будут описаны автоматически, так как data class

}