package com.capgemini.MovieService.services;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.capgemini.MovieService.dao.MovieDAO;
import com.capgemini.MovieService.exceptions.MovieNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.MovieService.entities.Movie;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class having functionality to interact with Movies
 *
 * @author Vinay Pratap Singh
 */
@Service
@Transactional
public class MovieServiceImpl implements MovieService{

    private final MovieDAO movieDAO;

    @Autowired
    public MovieServiceImpl(MovieDAO movieDAO ) {
        this.movieDAO = movieDAO;
    }

    public Movie findById(String id) {
        return movieDAO.findById(id).orElseThrow(MovieNotFoundException::new);
    }

    public List<Movie> findAll() {
        return StreamSupport
                .stream(movieDAO.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Movie addMovie(Movie movie) {
        return movieDAO.save(movie);
    }

    public void deleteMovie(String id) {
        Movie movie = findById(id);
        movieDAO.delete(movie);
    }

    public Movie updateMovie(String id, Movie movie) {
        findById(id); // if this movie didn't exist previously, an exception will be thrown
        return movieDAO.save(movie);
    }

    public void addMultipleMovies(List<Movie> movies) {
        List<Movie> filteredMovies = Optional.ofNullable(movies)
                .orElseThrow(NullPointerException::new)
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        movieDAO.saveAll(filteredMovies);
    }

    public Set<Movie> getMoviesByIds(Set<String> movieIds) {
        return findAll().stream()
                .filter(movie -> movieIds.contains(movie.getMovieId()))
                .collect(Collectors.toSet());
    }

    public List<Movie> getLatestMovies() {
        var curr = Instant.now();
        return findAll()
                .stream()
                .sorted(Comparator.comparing(movie -> Duration.between(movie.getDateReleased().toInstant(), curr)))
                .limit(5)
                .collect(Collectors.toList());
    }

}
