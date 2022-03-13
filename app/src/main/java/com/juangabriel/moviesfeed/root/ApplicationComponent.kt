package com.juangabriel.moviesfeed.root

import javax.inject.Singleton
import com.juangabriel.moviesfeed.root.ApplicationModule
import com.juangabriel.moviesfeed.movies.MoviesModule
import com.juangabriel.moviesfeed.http.MovieTitleApiModule
import com.juangabriel.moviesfeed.http.MovieExtraInfoApiModule
import com.juangabriel.moviesfeed.MainActivity
import dagger.Component

@Singleton
@Component(modules = [ApplicationModule::class, MoviesModule::class, MovieTitleApiModule::class, MovieExtraInfoApiModule::class])
interface ApplicationComponent {
    fun inject(target: MainActivity?)
}