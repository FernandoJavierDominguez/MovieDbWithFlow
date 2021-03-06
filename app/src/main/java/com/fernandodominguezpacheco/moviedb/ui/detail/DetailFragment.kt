package com.fernandodominguezpacheco.moviedb.ui.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.fernandodominguezpacheco.moviedb.MovieApp
import com.fernandodominguezpacheco.moviedb.R
import com.fernandodominguezpacheco.moviedb.databinding.FragmentDetailBinding
import com.fernandodominguezpacheco.moviedb.utils.Constants
import com.fernandodominguezpacheco.moviedb.utils.SharedViewModel
import com.fernandodominguezpacheco.moviedb.utils.loadUrl
import com.fernandodominguezpacheco.moviedb.utils.observer
import javax.inject.Inject


class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val detailViewModel: DetailViewModel by viewModels{viewModelFactory}

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private var movieId = 0

    private val actorAdapter = ActorAdapter{
        sharedViewModel.selectActor(it)
        requireView().findNavController().navigate(R.id.action_detailFragment_to_actorFragment)
    }
    private val genreAdapter = GenreAdapter{

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.actors.adapter = actorAdapter
        binding.genres.adapter = genreAdapter

        observer(sharedViewModel.getSelectedMovie()) {
            detailViewModel.addActorsByMovie(it)
            detailViewModel.addGenresByMovie(it)
            movieId = it.id

        }
        observer(detailViewModel.movieItem){
            val movie = it.find { it.id == movieId }
            binding.urlImage.loadUrl("${Constants.BASE_IMAGE_URL}${movie?.urlImage}")
            binding.title.text = movie?.title
            binding.overview.text = movie?.overview
            actorAdapter.items = movie!!.actors
            genreAdapter.items = movie.genres
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAttach(context: Context) {
        (context.applicationContext as MovieApp).getComponent().inject(this)
        super.onAttach(context)
    }



}