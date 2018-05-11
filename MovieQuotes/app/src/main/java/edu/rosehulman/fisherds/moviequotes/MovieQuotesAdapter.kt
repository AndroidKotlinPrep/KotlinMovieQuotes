package edu.rosehulman.fisherds.moviequotes

import android.content.Context
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.dialog_add.view.*
import java.util.*
import kotlin.collections.ArrayList

class MovieQuotesAdapter : RecyclerView.Adapter<MovieQuotesAdapter.MovieQuoteViewHolder> {

    private val mContext: Context
    private var mMovieQuotes = ArrayList<MovieQuote>()
    private val mMovieQuotesRef: CollectionReference
    private lateinit var mMovieQuotesListener: ListenerRegistration

    constructor(context: Context) {
        mContext = context
        mMovieQuotesRef = FirebaseFirestore.getInstance().collection(Constants.QUOTES_PATH)
    }

    fun addSnapshotListener() {
        mMovieQuotesListener = mMovieQuotesRef.orderBy(MovieQuote.LAST_TOUCHED_KEY, Query.Direction.DESCENDING).addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.e(Constants.TAG, "Error getting movie quotes", error)
            }
            if (snapshot != null) {
                mMovieQuotes.clear()
                for (documentSnapshot in snapshot.documents) {
//                    val movieQuote = documentSnapshot.toObject(MovieQuote::class.java)
//                    movieQuote.id = documentSnapshot.id
//                    mMovieQuotes.add(movieQuote)

                    mMovieQuotes.add(MovieQuote.fromSnapshot(documentSnapshot))
                }
                notifyDataSetChanged()
            }
        }
    }

    fun removeSnapshotListener() {
        mMovieQuotesListener.remove()
    }

    override fun getItemCount() = mMovieQuotes.size

    override fun onBindViewHolder(holder: MovieQuoteViewHolder, position: Int) {
        holder.quoteTextView.text = mMovieQuotes[position].quote
        holder.movieTextView.text = mMovieQuotes[position].movie
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieQuoteViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.movie_quote_row, parent, false)
        return MovieQuoteViewHolder(view)
    }

    private fun add(quote: String, movie: String) {
//        mMovieQuotes.add(0, movieQuote)
//        notifyDataSetChanged()
//        val movieQuote = HashMap<String, Any>()
//        movieQuote[Constants.QUOTE_KEY] = quote
//        movieQuote[Constants.MOVIE_KEY] = movie
//        movieQuote[Constants.CREATED_KEY] = Date()
//        mMovieQuotesRef.add(movieQuote)

        mMovieQuotesRef.add(MovieQuote(quote, movie))

//        mMovieQuotesRef.add(mapOf(MovieQuote.QUOTE_KEY to quote,
//                MovieQuote.MOVIE_KEY to movie, MovieQuote.CREATED_KEY to Date()))
    }

    private fun edit(documentId: String, quote: String, movie: String) {
        mMovieQuotesRef.document(documentId).set(MovieQuote(quote, movie))

//        mMovieQuotesRef.document(documentId).update(mapOf(Constants.QUOTE_KEY to quote,
//                Constants.MOVIE_KEY to movie))
    }

    private fun removeMovieQuote(id: String) {
//        mMovieQuotes.remove(movieQuote)
//        notifyDataSetChanged()
        mMovieQuotesRef.document(id).delete()
    }

    fun showAddEditDialog(movieQuote: MovieQuote? = null) {
        val builder = AlertDialog.Builder(mContext)
                .setTitle(if (movieQuote == null) "Add a movie quote" else "Edit the quote")
        val view = LayoutInflater.from(mContext).inflate(R.layout.dialog_add, null)
        builder.setView(view)
        view.dialog_add_quote_text.setText(movieQuote?.quote)
        view.dialog_add_movie_text.setText(movieQuote?.movie)
        builder.setPositiveButton(android.R.string.ok, { dialog, whichButton ->
            val newQuote = view.dialog_add_quote_text.text.toString()
            val newMovie = view.dialog_add_movie_text.text.toString()
            if (movieQuote == null) {
                add(newQuote, newMovie)
            } else {
//                movieQuote.quote = newQuote
//                movieQuote.movie = newMovie
//                notifyDataSetChanged()

//                movieQuote.id?.let {
//                    edit(it, newQuote, newMovie)
//                }

                edit(movieQuote.id, newQuote, newMovie)

            }
        })
        builder.show()
    }

    inner class MovieQuoteViewHolder : RecyclerView.ViewHolder {
         val quoteTextView: TextView
         val movieTextView: TextView

        constructor(itemView: View) : super(itemView) {
            quoteTextView = itemView.findViewById(R.id.quote_text_view)
            movieTextView = itemView.findViewById(R.id.movie_text_view)
            itemView.setOnClickListener {
                showAddEditDialog(mMovieQuotes[adapterPosition])
            }

            itemView.setOnLongClickListener {
//                val documentId = mMovieQuotes[adapterPosition].id
//                if (documentId != null) {
//                    removeMovieQuote(documentId)
//                }

//                mMovieQuotes[adapterPosition].id?.let { documentId ->
//                    removeMovieQuote(documentId)
//                }

//                mMovieQuotes[adapterPosition].id?.let {
//                    removeMovieQuote(it)
//                }

                removeMovieQuote(mMovieQuotes[adapterPosition].id)

                true
            }
        }
    }

}
