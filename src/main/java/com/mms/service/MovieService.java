package com.mms.service;

import com.mms.entity.Movie;
import com.mms.exception.MovieNotFoundException;
import com.mms.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MovieService {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found with id: " + id));
    }

    public Movie createMovie(Movie movie) {
        if (movie == null) {
            throw new IllegalArgumentException("Movie cannot be null");
        }
        return movieRepository.save(movie);
    }

    public Movie updateMovie(Long id, Movie movieDetails) {
        if (movieDetails == null) {
            throw new IllegalArgumentException("Movie details cannot be null");
        }
        
        Movie movie = getMovieById(id);
        
        if (movieDetails.getTitle() != null) {
            movie.setTitle(movieDetails.getTitle());
        }
        if (movieDetails.getGenre() != null) {
            movie.setGenre(movieDetails.getGenre());
        }
        if (movieDetails.getDirector() != null) {
            movie.setDirector(movieDetails.getDirector());
        }
        if (movieDetails.getReleaseYear() != null) {
            movie.setReleaseYear(movieDetails.getReleaseYear());
        }
        if (movieDetails.getRating() != null) {
            movie.setRating(movieDetails.getRating());
        }
        
        return movieRepository.save(movie);
    }

    public void deleteMovie(Long id) {
        Movie movie = getMovieById(id);
        movieRepository.delete(movie);
    }

    public List<Movie> getMoviesByGenre(String genre) {
        return movieRepository.findByGenre(genre);
    }

    public List<Movie> getMoviesByDirector(String director) {
        return movieRepository.findByDirector(director);
    }

    public List<Movie> getMoviesByReleaseYear(Integer releaseYear) {
        return movieRepository.findByReleaseYear(releaseYear);
    }

    public List<Movie> getMoviesByRatingGreaterThanEqual(Double rating) {
        return movieRepository.findByRatingGreaterThanEqual(rating);
    }
}

