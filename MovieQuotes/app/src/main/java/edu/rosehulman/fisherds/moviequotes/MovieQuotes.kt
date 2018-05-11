package edu.rosehulman.fisherds.moviequotes

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

class MovieQuote {

    companion object {
        const val QUOTE_KEY = "quote"
        const val MOVIE_KEY = "movie"
        const val LAST_TOUCHED_KEY = "lastTouched"
    }

    @get:Exclude var id: String? = null

    var movie: String = ""
    var quote: String = ""

    @ServerTimestamp
    var lastTouched: Date? = null

    constructor(quote: String, movie: String) {
        this.quote = quote
        this.movie = movie
    }

    constructor(documentSnapshot: DocumentSnapshot) {
        this.quote = documentSnapshot.getString(QUOTE_KEY)
        this.movie = documentSnapshot.getString(MOVIE_KEY)
        this.id = documentSnapshot.id
    }
}