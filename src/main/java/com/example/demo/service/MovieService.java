package com.example.demo.service;

import com.example.demo.model.Movie;
import com.example.demo.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {

    @Autowired
    MovieRepository movieRepository;

    public List getAllMovies() {
        List movies = new ArrayList();
        movieRepository.findAll().forEach(movie -> movies.add(movie));
        return movies;
    }

    public Movie getMovieById(int id) {
        return movieRepository.findById(id).get();
    }

    public void saveOrUpdate(Movie mvoie) {
        movieRepository.save(mvoie);
    }

    public void delete(int id) {
        movieRepository.deleteById(id);
    }
}