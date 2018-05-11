package edu.rosehulman.fisherds.moviequotes

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class MovieQuote(val quote: String, val movie: String) {

    @get:Exclude var id = ""
    @ServerTimestamp var lastTouched: Date? = null

    constructor(): this("","") {}
    
    companion object {
        const val LAST_TOUCHED_KEY = "lastTouched"

        fun fromSnapshot(documentSnapshot: DocumentSnapshot): MovieQuote {
            val mq = documentSnapshot.toObject(MovieQuote::class.java)
            mq.id = documentSnapshot.id
            return mq
        }
    }
}