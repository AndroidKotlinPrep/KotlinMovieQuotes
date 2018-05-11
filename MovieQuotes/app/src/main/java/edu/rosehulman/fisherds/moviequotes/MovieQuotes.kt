package edu.rosehulman.fisherds.moviequotes

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

class MovieQuote {

    @get:Exclude var id = ""
    @ServerTimestamp var lastTouched: Date? = null
    var movie = ""
    var quote = ""

    constructor(quote: String, movie: String) {
        this.quote = quote
        this.movie = movie
    }

    companion object {
        const val LAST_TOUCHED_KEY = "lastTouched"

        fun fromSnapshot(documentSnapshot: DocumentSnapshot): MovieQuote {
            val mq = documentSnapshot.toObject(MovieQuote::class.java)
            mq.id = documentSnapshot.id
            return mq
        }
    }
}