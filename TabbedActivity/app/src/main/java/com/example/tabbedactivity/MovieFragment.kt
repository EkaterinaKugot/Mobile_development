package com.example.tabbedactivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import org.w3c.dom.Text


class MovieFragment : Fragment() {

    companion object {
        private const val ARG_TITLE = "title"
        private const val ARG_RATING = "rating"
        private const val ARG_DESCRIPTION = "description"
        private const val ARG_GENRE = "genry"
        private const val ARG_COUNTRY = "country"


        fun newInstance(title: String, rating: String,
                        description: String, genre:String,
                        country: String): MovieFragment {
            val fragment = MovieFragment()
            val args = Bundle()
            args.putString(ARG_TITLE, title)
            args.putString(ARG_RATING, rating)
            args.putString(ARG_DESCRIPTION, description)
            args.putString(ARG_GENRE, genre)
            args.putString(ARG_COUNTRY, country)
            fragment.arguments = args


            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val title = arguments?.getString(ARG_TITLE) ?: ""
        val rating = arguments?.getString(ARG_RATING) ?: ""
        val description = arguments?.getString(ARG_DESCRIPTION) ?: ""
        val genre = arguments?.getString(ARG_GENRE) ?: ""
        val country = arguments?.getString(ARG_COUNTRY) ?: ""

        var movieTitle = view.findViewById<TextView>(R.id.movieTitle)
        var movieRating = view.findViewById<TextView>(R.id.movieRating)
        var movieDescription = view.findViewById<TextView>(R.id.movieDescription)
        var movieGenre = view.findViewById<TextView>(R.id.movieGenre)
        var movieCountry = view.findViewById<TextView>(R.id.movieCountry)

        movieTitle.text = title
        movieRating.text = "Рейтинг: $rating"
        movieDescription.text = description
        movieGenre.text = "Жанр: $genre"
        movieCountry.text = "Страна: $country"
    }
}