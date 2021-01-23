package com.example.Movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Movie.Activities.DetailsActivity
import com.example.Movie.cache.MoviesDbHelper
import com.example.Movie.data.Movies
import com.example.Movie.data.MoviesRepository.getPopularMovies


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TopFragment(private var moviesDbHelper: MoviesDbHelper) : Fragment() {
    // TODO: Rename and change types of parameters
    //private var param1: String? = null
    //private var param2: String? = null

     lateinit var popularMovies: RecyclerView
     lateinit var popularMoviesAdapter: MoviesAdapter
    private var popularMoviesPage = 1
    private lateinit var popularMoviesLayoutMgr: LinearLayoutManager
     // lateinit var moviesDbHelper: MoviesDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
      return  inflater.inflate(R.layout.fragment_blank, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Toast.makeText(activity,"on view called", Toast.LENGTH_SHORT).show()
        popularMovies=view.findViewById(R.id.popular_movies)
        popularMoviesLayoutMgr= LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )
        popularMoviesAdapter= MoviesAdapter(mutableListOf()) { movie -> showMovieDetails(movie) }
        popularMovies.layoutManager=popularMoviesLayoutMgr
        popularMovies.adapter=popularMoviesAdapter


        getPopularMovies()
    }
    fun getPopularMovies() {
        getPopularMovies(
            popularMoviesPage,
            ::onPopularMoviesFetched,
            ::onError
        )
    }


     fun onError() {
         var movs = moviesDbHelper.readAllMovies()
         popularMoviesAdapter.appendMovies(movs)
        Toast.makeText(activity,"Error Fetching. Please wait", Toast.LENGTH_SHORT).show()
    }
    private fun showMovieDetails(movie: Movies) {
       // val intent = Intent (getActivity(), Main::class.java)

        val intent = Intent(activity, DetailsActivity::class.java)
        intent.putExtra("backdrop", movie.backdropPath)
        intent.putExtra("poster", movie.posterPath)
        intent.putExtra("title", movie.title)
        intent.putExtra("rating", movie.rating)
        intent.putExtra("releasedate", movie.releaseDate)
        intent.putExtra("overview", movie.overview)
        activity?.startActivity(intent)
    }
     fun onPopularMoviesFetched(movies: List<Movies>) {
        // Toast.makeText(activity,"got movies", Toast.LENGTH_SHOR.inT).show()
        popularMoviesAdapter.appendMovies(movies)
         for (i in movies!!) {
             var m = MovieModel(
                 i.id.toString(),
                 i.title, i.overview,
                 i.posterPath,
                 "",
                 i.rating,
                 i.releaseDate,
                 i.adult,
                 i.runtime,
                 i.origlang,
                 i.budget,
                 i.genres
             )
             moviesDbHelper.insertUser(m)
         }
         attachPopularMoviesOnScrollListener()
    }
     fun attachPopularMoviesOnScrollListener() {
        popularMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = popularMoviesLayoutMgr.itemCount
                val visibleItemCount = popularMoviesLayoutMgr.childCount
                val firstVisibleItem = popularMoviesLayoutMgr.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    popularMovies.removeOnScrollListener(this)
                    popularMoviesPage++
                    getPopularMovies()
                }
            }
        })
    }


}