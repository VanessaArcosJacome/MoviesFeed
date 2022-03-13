package com.juangabriel.moviesfeed.http.apimodel

import org.junit.runner.RunWith
import android.support.test.runner.AndroidJUnit4
import android.support.test.InstrumentationRegistry
import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import retrofit2.http.GET
import com.juangabriel.moviesfeed.http.apimodel.TopMoviesRated
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.Interceptor
import kotlin.Throws
import okhttp3.HttpUrl
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import com.juangabriel.moviesfeed.http.MoviesApiService
import com.juangabriel.moviesfeed.http.MoviesExtraInfoApisService
import com.juangabriel.moviesfeed.http.apimodel.OmdbApi
import android.app.Application
import com.juangabriel.moviesfeed.root.ApplicationComponent
import com.juangabriel.moviesfeed.root.ApplicationModule
import com.juangabriel.moviesfeed.movies.MoviesModule
import com.juangabriel.moviesfeed.http.MovieTitleApiModule
import com.juangabriel.moviesfeed.http.MovieExtraInfoApiModule
import android.support.v7.widget.RecyclerView
import com.juangabriel.moviesfeed.movies.ListAdapter.ListItemViewHolder
import android.view.ViewGroup
import android.view.LayoutInflater
import com.juangabriel.moviesfeed.R
import butterknife.BindView
import android.widget.TextView
import butterknife.ButterKnife
import com.juangabriel.moviesfeed.movies.MoviesMVP.Presenter
import com.juangabriel.moviesfeed.movies.MoviesPresenter
import com.juangabriel.moviesfeed.movies.MoviesModel
import javax.inject.Singleton
import com.juangabriel.moviesfeed.movies.MoviesRepository
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver

class OmdbApi {
    @SerializedName("Title")
    @Expose
    private var title: String? = null

    @SerializedName("Year")
    @Expose
    private var year: String? = null

    @SerializedName("Rated")
    @Expose
    private var rated: String? = null

    @SerializedName("Released")
    @Expose
    private var released: String? = null

    @SerializedName("Runtime")
    @Expose
    private var runtime: String? = null

    @SerializedName("Genre")
    @Expose
    private var genre: String? = null

    @SerializedName("Director")
    @Expose
    private var director: String? = null

    @SerializedName("Writer")
    @Expose
    private var writer: String? = null

    @SerializedName("Actors")
    @Expose
    private var actors: String? = null

    @SerializedName("Plot")
    @Expose
    private var plot: String? = null

    @SerializedName("Language")
    @Expose
    private var language: String? = null

    @SerializedName("Country")
    @Expose
    private var country: String? = null

    @SerializedName("Awards")
    @Expose
    private var awards: String? = null

    @SerializedName("Poster")
    @Expose
    private var poster: String? = null

    @SerializedName("Ratings")
    @Expose
    private var ratings: MutableList<Rating?>? = null

    @SerializedName("Metascore")
    @Expose
    private var metascore: String? = null

    @SerializedName("imdbRating")
    @Expose
    private var imdbRating: String? = null

    @SerializedName("imdbVotes")
    @Expose
    private var imdbVotes: String? = null

    @SerializedName("imdbID")
    @Expose
    private var imdbID: String? = null

    @SerializedName("Type")
    @Expose
    private var type: String? = null

    @SerializedName("DVD")
    @Expose
    private var dVD: String? = null

    @SerializedName("BoxOffice")
    @Expose
    private var boxOffice: String? = null

    @SerializedName("Production")
    @Expose
    private var production: String? = null

    @SerializedName("Website")
    @Expose
    private var website: String? = null

    @SerializedName("Response")
    @Expose
    private var response: String? = null
    fun getTitle(): String? {
        return title
    }

    fun setTitle(title: String?) {
        this.title = title
    }

    fun getYear(): String? {
        return year
    }

    fun setYear(year: String?) {
        this.year = year
    }

    fun getRated(): String? {
        return rated
    }

    fun setRated(rated: String?) {
        this.rated = rated
    }

    fun getReleased(): String? {
        return released
    }

    fun setReleased(released: String?) {
        this.released = released
    }

    fun getRuntime(): String? {
        return runtime
    }

    fun setRuntime(runtime: String?) {
        this.runtime = runtime
    }

    fun getGenre(): String? {
        return genre
    }

    fun setGenre(genre: String?) {
        this.genre = genre
    }

    fun getDirector(): String? {
        return director
    }

    fun setDirector(director: String?) {
        this.director = director
    }

    fun getWriter(): String? {
        return writer
    }

    fun setWriter(writer: String?) {
        this.writer = writer
    }

    fun getActors(): String? {
        return actors
    }

    fun setActors(actors: String?) {
        this.actors = actors
    }

    fun getPlot(): String? {
        return plot
    }

    fun setPlot(plot: String?) {
        this.plot = plot
    }

    fun getLanguage(): String? {
        return language
    }

    fun setLanguage(language: String?) {
        this.language = language
    }

    fun getCountry(): String? {
        return country
    }

    fun setCountry(country: String?) {
        this.country = country
    }

    fun getAwards(): String? {
        return awards
    }

    fun setAwards(awards: String?) {
        this.awards = awards
    }

    fun getPoster(): String? {
        return poster
    }

    fun setPoster(poster: String?) {
        this.poster = poster
    }

    fun getRatings(): MutableList<Rating?>? {
        return ratings
    }

    fun setRatings(ratings: MutableList<Rating?>?) {
        this.ratings = ratings
    }

    fun getMetascore(): String? {
        return metascore
    }

    fun setMetascore(metascore: String?) {
        this.metascore = metascore
    }

    fun getImdbRating(): String? {
        return imdbRating
    }

    fun setImdbRating(imdbRating: String?) {
        this.imdbRating = imdbRating
    }

    fun getImdbVotes(): String? {
        return imdbVotes
    }

    fun setImdbVotes(imdbVotes: String?) {
        this.imdbVotes = imdbVotes
    }

    fun getImdbID(): String? {
        return imdbID
    }

    fun setImdbID(imdbID: String?) {
        this.imdbID = imdbID
    }

    fun getType(): String? {
        return type
    }

    fun setType(type: String?) {
        this.type = type
    }

    fun getDVD(): String? {
        return dVD
    }

    fun setDVD(dVD: String?) {
        this.dVD = dVD
    }

    fun getBoxOffice(): String? {
        return boxOffice
    }

    fun setBoxOffice(boxOffice: String?) {
        this.boxOffice = boxOffice
    }

    fun getProduction(): String? {
        return production
    }

    fun setProduction(production: String?) {
        this.production = production
    }

    fun getWebsite(): String? {
        return website
    }

    fun setWebsite(website: String?) {
        this.website = website
    }

    fun getResponse(): String? {
        return response
    }

    fun setResponse(response: String?) {
        this.response = response
    }
}