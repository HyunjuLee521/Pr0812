package com.example.pr0812.domain.model

data class ResSearchBook(
    val meta: Meta,
    val documents: ArrayList<Document>
)

data class Meta(
    val total_count: Int,
    val pageable_count: Int,
    val is_end: Boolean
)

data class Document(
    val title: String,
    val contents: String,
    val url: String,
    val isbn: String,
    val datetime: String,
    val authors: ArrayList<String>,
    val publisher: String,
    val translators: ArrayList<String>,
    val price: Int,
    val sale_price: Int,
    val thumbnail: String,
    val status: String
)