package com.mms.service;

import com.mms.entity.Movie;
import com.mms.exception.MovieNotFoundException;
import com.mms.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Movie Service Tests")
class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    private Movie movie1;
    private Movie movie2;

    @BeforeEach
    void setUp() {
        movie1 = new Movie();
        movie1.setId(1L);
        movie1.setTitle("The Matrix");
        movie1.setGenre("Sci-Fi");
        movie1.setDirector("Wachowski Brothers");
        movie1.setReleaseYear(1999);
        movie1.setRating(8.7);

        movie2 = new Movie();
        movie2.setId(2L);
        movie2.setTitle("Inception");
        movie2.setGenre("Sci-Fi");
        movie2.setDirector("Christopher Nolan");
        movie2.setReleaseYear(2010);
        movie2.setRating(8.8);
    }

    @Test
    @DisplayName("Should return all movies")
    void testGetAllMovies() {
        // Given
        List<Movie> movies = Arrays.asList(movie1, movie2);
        when(movieRepository.findAll()).thenReturn(movies);

        // When
        List<Movie> result = movieService.getAllMovies();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("The Matrix", result.get(0).getTitle());
        assertEquals("Inception", result.get(1).getTitle());
        verify(movieRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return movie by id when movie exists")
    void testGetMovieById_Success() {
        // Given
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie1));

        // When
        Movie result = movieService.getMovieById(1L);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("The Matrix", result.getTitle());
        assertEquals("Sci-Fi", result.getGenre());
        verify(movieRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should throw MovieNotFoundException when movie does not exist")
    void testGetMovieById_NotFound() {
        // Given
        when(movieRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        MovieNotFoundException exception = assertThrows(
                MovieNotFoundException.class,
                () -> movieService.getMovieById(999L)
        );
        assertEquals("Movie not found with id: 999", exception.getMessage());
        verify(movieRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Should create and return new movie")
    void testCreateMovie_Success() {
        // Given
        Movie newMovie = new Movie();
        newMovie.setTitle("Interstellar");
        newMovie.setGenre("Sci-Fi");
        newMovie.setDirector("Christopher Nolan");
        newMovie.setReleaseYear(2014);
        newMovie.setRating(8.6);

        when(movieRepository.save(any(Movie.class))).thenReturn(newMovie);

        // When
        Movie result = movieService.createMovie(newMovie);

        // Then
        assertNotNull(result);
        assertEquals("Interstellar", result.getTitle());
        assertEquals("Christopher Nolan", result.getDirector());
        verify(movieRepository, times(1)).save(newMovie);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when creating null movie")
    void testCreateMovie_NullMovie() {
        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> movieService.createMovie(null)
        );
        assertEquals("Movie cannot be null", exception.getMessage());
        verify(movieRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should update existing movie")
    void testUpdateMovie_Success() {
        // Given
        Movie updatedDetails = new Movie();
        updatedDetails.setTitle("The Matrix Reloaded");
        updatedDetails.setRating(7.2);

        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie1));
        when(movieRepository.save(any(Movie.class))).thenReturn(movie1);

        // When
        Movie result = movieService.updateMovie(1L, updatedDetails);

        // Then
        assertNotNull(result);
        verify(movieRepository, times(1)).findById(1L);
        verify(movieRepository, times(1)).save(any(Movie.class));
    }

    @Test
    @DisplayName("Should throw MovieNotFoundException when updating non-existent movie")
    void testUpdateMovie_NotFound() {
        // Given
        Movie updatedDetails = new Movie();
        updatedDetails.setTitle("Updated Title");

        when(movieRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        MovieNotFoundException exception = assertThrows(
                MovieNotFoundException.class,
                () -> movieService.updateMovie(999L, updatedDetails)
        );
        assertEquals("Movie not found with id: 999", exception.getMessage());
        verify(movieRepository, times(1)).findById(999L);
        verify(movieRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when updating with null movie details")
    void testUpdateMovie_NullDetails() {
        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> movieService.updateMovie(1L, null)
        );
        assertEquals("Movie details cannot be null", exception.getMessage());
        verify(movieRepository, never()).findById(anyLong());
        verify(movieRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should delete movie when movie exists")
    void testDeleteMovie_Success() {
        // Given
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie1));
        doNothing().when(movieRepository).delete(movie1);

        // When
        movieService.deleteMovie(1L);

        // Then
        verify(movieRepository, times(1)).findById(1L);
        verify(movieRepository, times(1)).delete(movie1);
    }

    @Test
    @DisplayName("Should throw MovieNotFoundException when deleting non-existent movie")
    void testDeleteMovie_NotFound() {
        // Given
        when(movieRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        MovieNotFoundException exception = assertThrows(
                MovieNotFoundException.class,
                () -> movieService.deleteMovie(999L)
        );
        assertEquals("Movie not found with id: 999", exception.getMessage());
        verify(movieRepository, times(1)).findById(999L);
        verify(movieRepository, never()).delete(any());
    }

    @Test
    @DisplayName("Should return movies by genre")
    void testGetMoviesByGenre() {
        // Given
        List<Movie> sciFiMovies = Arrays.asList(movie1, movie2);
        when(movieRepository.findByGenre("Sci-Fi")).thenReturn(sciFiMovies);

        // When
        List<Movie> result = movieService.getMoviesByGenre("Sci-Fi");

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(movieRepository, times(1)).findByGenre("Sci-Fi");
    }

    @Test
    @DisplayName("Should return movies by director")
    void testGetMoviesByDirector() {
        // Given
        List<Movie> nolanMovies = Arrays.asList(movie2);
        when(movieRepository.findByDirector("Christopher Nolan")).thenReturn(nolanMovies);

        // When
        List<Movie> result = movieService.getMoviesByDirector("Christopher Nolan");

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Inception", result.get(0).getTitle());
        verify(movieRepository, times(1)).findByDirector("Christopher Nolan");
    }

    @Test
    @DisplayName("Should return movies by release year")
    void testGetMoviesByReleaseYear() {
        // Given
        List<Movie> movies1999 = Arrays.asList(movie1);
        when(movieRepository.findByReleaseYear(1999)).thenReturn(movies1999);

        // When
        List<Movie> result = movieService.getMoviesByReleaseYear(1999);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("The Matrix", result.get(0).getTitle());
        verify(movieRepository, times(1)).findByReleaseYear(1999);
    }

    @Test
    @DisplayName("Should return movies with rating greater than or equal to specified rating")
    void testGetMoviesByRatingGreaterThanEqual() {
        // Given
        List<Movie> highRatedMovies = Arrays.asList(movie1, movie2);
        when(movieRepository.findByRatingGreaterThanEqual(8.5)).thenReturn(highRatedMovies);

        // When
        List<Movie> result = movieService.getMoviesByRatingGreaterThanEqual(8.5);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(movieRepository, times(1)).findByRatingGreaterThanEqual(8.5);
    }
}

