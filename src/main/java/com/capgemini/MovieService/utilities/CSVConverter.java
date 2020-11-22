package com.capgemini.MovieService.utilities;

import com.capgemini.MovieService.entities.Movie;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class CSVConverter {

    public static List<Movie> csvToMovies(InputStream stream) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader,
               CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            List<Movie> movies = new ArrayList<>();
            List<CSVRecord> records = csvParser.getRecords();

            records.forEach(record -> {
                var name = record.get("name");
                var genre = record.get("genre");
                var duration = record.get("duration");
                var dimension = Movie.Dimension.valueOf(record.get("dimension"));
                var rating = Double.parseDouble(record.get("rating"));
                Date dateReleased = null;
                try {
                    dateReleased = CSVConverter.StringToDate(record.get("dateReleased"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                var casts = CSVConverter.getTokens(record.get("cast"));
                var languages = CSVConverter.getTokens(record.get("languages"));

                var movie = new Movie(name,genre, duration, dimension, rating, dateReleased, casts, languages);
                movies.add(movie);
            });

            return movies;

        } catch (IOException e) {
            throw new RuntimeException("Can't parse CSV");
        }
    }

    private static List<String> getTokens(String items) {
        if (items == null || items.length() == 0) {
            return new ArrayList<>();
        }
        if (items.contains("|")) {
            String[] tokens = items.split("\\|");
            return Arrays.asList(tokens);
        } else {
            return List.of(items);
        }

    }

    private static Date StringToDate(String dob) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.parse(dob);
    }

}
