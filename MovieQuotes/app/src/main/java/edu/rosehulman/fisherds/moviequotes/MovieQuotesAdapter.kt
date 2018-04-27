package edu.rosehulman.fisherds.moviequotes

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class MovieQuotesAdapter : RecyclerView.Adapter<MovieQuotesAdapter.MovieQuoteViewHolder> {

    private val mContext: Context
    private val mMovieQuotes = ArrayList<MovieQuote>()

    constructor(context: Context) {
        mContext = context
        mMovieQuotes.add(MovieQuote("I'll be back!", "The Terminator"))
        mMovieQuotes.add(MovieQuote("I'll be back!", "The Terminator"))
        mMovieQuotes.add(MovieQuote("I'll be back!", "The Terminator"))
        mMovieQuotes.add(MovieQuote("I'll be back!", "The Terminator"))
        mMovieQuotes.add(MovieQuote("I'll be back!", "The Terminator"))
        mMovieQuotes.add(MovieQuote("I'll be back!", "The Terminator"))
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

    fun add(movieQuote: MovieQuote) {
        mMovieQuotes.add(0, movieQuote)
        notifyDataSetChanged()
    }

    class MovieQuoteViewHolder : RecyclerView.ViewHolder {
         val quoteTextView: TextView
         val movieTextView: TextView

        constructor(itemView: View) : super(itemView) {
            quoteTextView = itemView.findViewById(R.id.quote_text_view)
            movieTextView = itemView.findViewById(R.id.movie_text_view)
        }
    }

}