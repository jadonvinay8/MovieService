package com.capgemini.MovieService.entities;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

import javax.validation.constraints.*;


@DynamoDBTable(tableName = "Movie")
public class Movie {

    private String movieId;

    @NotBlank
    @NotNull
    private String name;

    @NotBlank
    @NotNull
    private String genre;

    @NotBlank
    @NotNull
    private String duration;

    @NotNull
    private Dimension movieDimension;

    @Min(1)
    @Max(10)
    @NotNull
    private Double rating;

    @NotNull
    private Date dateReleased;


    private List<String> casts;
    private List<String> languages;

    public Movie() {
        // Default Constructor
    }

    public enum Dimension {
        _2D, _3D
    }

    public Movie(String name, String genre, String duration, Dimension movieDimension, Double rating, Date dateReleased,
                 List<String> casts, List<String> languages) {
        super();
        this.name = name;
        this.genre = genre;
        this.duration = duration;
        this.movieDimension = movieDimension;
        this.rating = rating;
        this.dateReleased = dateReleased;
        this.casts = casts;
        this.languages = languages;
    }

    @DynamoDBHashKey
    @DynamoDBAutoGeneratedKey
    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    @DynamoDBAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBAttribute
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @DynamoDBAttribute
    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @DynamoDBAttribute
    @DynamoDBTypeConvertedEnum
    public Dimension getMovieDimension() {
        return movieDimension;
    }

    public void setMovieDimension(Dimension movieDimension) {
        this.movieDimension = movieDimension;
    }

    @DynamoDBAttribute
    public Date getDateReleased() {
        return dateReleased;
    }

    public void setDateReleased(Date dateReleased) {
        this.dateReleased = dateReleased;
    }

    @DynamoDBAttribute
    public List<String> getCasts() {
        return casts;
    }

    public void setCasts(List<String> casts) {
        this.casts = casts;
    }

    @DynamoDBAttribute
    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public Double getRating() {
        return rating;
    }

    @DynamoDBAttribute
    public void setRating(Double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Movie{" +
          "movieId='" + movieId + '\'' +
          ", name='" + name + '\'' +
          ", genre='" + genre + '\'' +
          ", duration='" + duration + '\'' +
          ", movieDimension=" + movieDimension +
          ", rating=" + rating +
          ", dateReleased=" + dateReleased +
          ", casts=" + casts +
          ", languages=" + languages +
          '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(movieId, movie.movieId) &&
                Objects.equals(name, movie.name) &&
                Objects.equals(genre, movie.genre) &&
                Objects.equals(duration, movie.duration) &&
                movieDimension == movie.movieDimension &&
                Objects.equals(rating, movie.rating) &&
                Objects.equals(dateReleased, movie.dateReleased);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId, name, genre, duration, movieDimension, rating, dateReleased);
    }
}
