package com.example.Movie

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Movie.Activities.DetailsActivity
import com.example.Movie.cache.MoviesDbHelper
import com.example.Movie.data.Movies
import com.example.Movie.data.MoviesRepository

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UpcomingFragment(private  var moviesDbHelper: MoviesDbHelper) : Fragment() {
    // TODO: Rename and change types of parameters
    //private var param1: String? = null
    //private var param2: String? = null

    lateinit var upcomingMovies: RecyclerView
    lateinit var upcomingMoviesAdapter: MoviesAdapter
    private var upcomingMoviesPage = 1
    private lateinit var upcomingMoviesLayoutMgr: LinearLayoutManager
    //protected lateinit var rootView: View
   // lateinit var moviesDbHelper: MoviesDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return  inflater.inflate(R.layout.fragment_blank2, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Toast.makeText(activity,"on view called", Toast.LENGTH_SHORT).show()
        upcomingMovies=view.findViewById(R.id.upcoming_movies)
        upcomingMoviesLayoutMgr= LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )
        upcomingMoviesAdapter= MoviesAdapter(mutableListOf()) { movie -> showMovieDetails(movie) }
        upcomingMovies.layoutManager=upcomingMoviesLayoutMgr
        upcomingMovies.adapter=upcomingMoviesAdapter


        getUpcomingMovies()
    }
    fun getUpcomingMovies() {
        MoviesRepository.getUpcomingMovies(
            upcomingMoviesPage,
            ::onUpcomingMoviesFetched,
            ::onError
        )
    }

    fun onError() {
        var movs = moviesDbHelper.readAllMovies11()
        upcomingMoviesAdapter.appendMovies(movs)
        Toast.makeText(activity,"Error fetching.Please wait", Toast.LENGTH_SHORT).show()
    }
    fun onUpcomingMoviesFetched(movies: List<Movies>) {
        //Toast.makeText(activity,"got movies", Toast.LENGTH_SHORT).show()
        upcomingMoviesAdapter.appendMovies(movies)

        for (i in movies!!) {
            if (i.posterPath != null) {
                for (i in movies!!) {
                    if(i.posterPath!=null) {
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
                        moviesDbHelper.insertMovie1(m)
                    }
                }
            }
        }
        attachPopularMoviesOnScrollListener()
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
    fun attachPopularMoviesOnScrollListener() {
        upcomingMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = upcomingMoviesLayoutMgr.itemCount
                val visibleItemCount = upcomingMoviesLayoutMgr.childCount
                val firstVisibleItem = upcomingMoviesLayoutMgr.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    upcomingMovies.removeOnScrollListener(this)
                    upcomingMoviesPage++
                    getUpcomingMovies()
                }
            }
        })
    }


}