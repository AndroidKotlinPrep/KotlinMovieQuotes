package edu.rosehulman.fisherds.moviequotes

import android.content.Context
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.dialog_add.view.*

class MovieQuotesAdapter : RecyclerView.Adapter<MovieQuotesAdapter.MovieQuoteViewHolder> {

    private val mContext: Context
    private val mMovieQuotes = ArrayList<MovieQuote>()

    constructor(context: Context) {
        mContext = context
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

    fun removeMovieQuote(movieQuote: MovieQuote) {
        mMovieQuotes.remove(movieQuote)
        notifyDataSetChanged()
    }

    fun showAddEditDialog(movieQuote: MovieQuote? = null) {
        val builder = AlertDialog.Builder(mContext)
                .setTitle(if (movieQuote == null) "Add a movie quote" else "Edit the quote")
        val view = LayoutInflater.from(mContext).inflate(R.layout.dialog_add, null)
        builder.setView(view)
        if (movieQuote != null) {
        }
        view.dialog_add_quote_text.setText(movieQuote?.quote)
        view.dialog_add_movie_text.setText(movieQuote?.movie)
        builder.setPositiveButton(android.R.string.ok, { dialog, whichButton ->
            val newQuote = view.dialog_add_quote_text.text.toString()
            val newMovie = view.dialog_add_movie_text.text.toString()
            if (movieQuote == null) {
                add(MovieQuote(newQuote, newMovie))
            } else {
                movieQuote.quote = newQuote
                movieQuote.movie = newMovie
                notifyDataSetChanged()
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
                removeMovieQuote(mMovieQuotes[adapterPosition])
                true
            }
        }
    }

}
