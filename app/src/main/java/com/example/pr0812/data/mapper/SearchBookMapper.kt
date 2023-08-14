package com.example.pr0812.data.mapper

import com.example.pr0812.data.entity.Book
import com.example.pr0812.domain.model.Document


fun Document.toBook(): Book {
    return Book(
        thumbnail,
        title,
        authors.toAuthor(),
        datetime,
        contents,
        toPrice()
    )
}

private fun ArrayList<String>.toAuthor(): String {
    var authors = ""
    forEachIndexed { index, s ->
        authors += if (index == 0) {
            s
        } else {
            ", $s"
        }
    }

    return authors
}


private fun Document.toPrice(): String {
    return "판매가:$sale_price (정가:$price)"
}
