package com.capgemini.MovieService.controllers;

import com.capgemini.MovieService.dto.MicroserviceResponse;
import com.capgemini.MovieService.entities.Movie;
import com.capgemini.MovieService.services.MovieService;
import com.capgemini.MovieService.utilities.CSVConverter;

import com.capgemini.MovieService.utilities.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("v1/movies")
@CrossOrigin
public class MovieController {

	private final MovieService movieService;
	private static final String SUCCESS_MESSAGE = "Operation Completed successfully";


	@Autowired
	public MovieController(MovieService movieService) {
		this.movieService = movieService;
	}

	@GetMapping
	public ResponseEntity<MicroserviceResponse> getAllMovies() {
		MicroserviceResponse response = ResponseBuilder.build(HttpStatus.OK.value(), movieService.findAll(), null);
		return ResponseEntity.ok(response);
	}

	@GetMapping("latest")
	public ResponseEntity<MicroserviceResponse> getLatestMovies() {
		MicroserviceResponse response = ResponseBuilder.build(HttpStatus.OK.value(), movieService.getLatestMovies(), null);
		return ResponseEntity.ok(response);
	}

	@PostMapping("findByIds")
	public ResponseEntity<MicroserviceResponse> getMoviesByIds(@RequestBody Set<String> movieIds) {
		MicroserviceResponse response = ResponseBuilder.build(HttpStatus.OK.value(), movieService.getMoviesByIds(movieIds), null);
		return ResponseEntity.ok(response);
	}

	@PostMapping
	public ResponseEntity<MicroserviceResponse> addMovie(@Valid @RequestBody Movie movie) {
		MicroserviceResponse response = ResponseBuilder.build(HttpStatus.CREATED.value(), movieService.addMovie(movie), null);
		return ResponseEntity.ok(response);
	}

	@PostMapping("batch")
	public ResponseEntity<MicroserviceResponse> addMovies(@RequestBody List<@NotNull @Valid Movie> movies) {
		movieService.addMultipleMovies(movies);
		MicroserviceResponse response = ResponseBuilder.build(HttpStatus.CREATED.value(), SUCCESS_MESSAGE, null);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<MicroserviceResponse> findMovie(@PathVariable("id") String id) {
		MicroserviceResponse response = ResponseBuilder.build(HttpStatus.OK.value(), movieService.findById(id), null);
		return ResponseEntity.ok(response);
	}

	@PutMapping("{id}")
	public ResponseEntity<MicroserviceResponse> updateMovie(@PathVariable("id") String id, @Valid @RequestBody Movie movie) {
		MicroserviceResponse response = ResponseBuilder.build(HttpStatus.OK.value(), movieService.updateMovie(id, movie), null);
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<MicroserviceResponse> deleteMovie(@PathVariable("id") String id) {
		movieService.deleteMovie(id);
		MicroserviceResponse response = ResponseBuilder.build(HttpStatus.NO_CONTENT.value(), SUCCESS_MESSAGE, null);
		return ResponseEntity.ok(response);

	}

	@PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<MicroserviceResponse> addMoviesViaFile(@RequestParam("file") MultipartFile file) throws IOException {
		var movies = CSVConverter.csvToMovies(file.getInputStream());
		movieService.addMultipleMovies(movies);
		MicroserviceResponse response = ResponseBuilder.build(HttpStatus.CREATED.value(), SUCCESS_MESSAGE, null);
		return ResponseEntity.ok(response);

	}
	
}
