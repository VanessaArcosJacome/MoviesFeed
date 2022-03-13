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

class Result {
    @SerializedName("vote_count")
    @Expose
    private var voteCount: Int? = null

    @SerializedName("id")
    @Expose
    private var id: Int? = null

    @SerializedName("video")
    @Expose
    private var video: Boolean? = null

    @SerializedName("vote_average")
    @Expose
    private var voteAverage: Double? = null

    @SerializedName("title")
    @Expose
    private var title: String? = null

    @SerializedName("popularity")
    @Expose
    private var popularity: Double? = null

    @SerializedName("poster_path")
    @Expose
    private var posterPath: String? = null

    @SerializedName("original_language")
    @Expose
    private var originalLanguage: String? = null

    @SerializedName("original_title")
    @Expose
    private var originalTitle: String? = null

    @SerializedName("genre_ids")
    @Expose
    private var genreIds: MutableList<Int?>? = null

    @SerializedName("backdrop_path")
    @Expose
    private var backdropPath: String? = null

    @SerializedName("adult")
    @Expose
    private var adult: Boolean? = null

    @SerializedName("overview")
    @Expose
    private var overview: String? = null

    @SerializedName("release_date")
    @Expose
    private var releaseDate: String? = null
    fun getVoteCount(): Int? {
        return voteCount
    }

    fun setVoteCount(voteCount: Int?) {
        this.voteCount = voteCount
    }

    fun getId(): Int? {
        return id
    }

    fun setId(id: Int?) {
        this.id = id
    }

    fun getVideo(): Boolean? {
        return video
    }

    fun setVideo(video: Boolean?) {
        this.video = video
    }

    fun getVoteAverage(): Double? {
        return voteAverage
    }

    fun setVoteAverage(voteAverage: Double?) {
        this.voteAverage = voteAverage
    }

    fun getTitle(): String? {
        return title
    }

    fun setTitle(title: String?) {
        this.title = title
    }

    fun getPopularity(): Double? {
        return popularity
    }

    fun setPopularity(popularity: Double?) {
        this.popularity = popularity
    }

    fun getPosterPath(): String? {
        return posterPath
    }

    fun setPosterPath(posterPath: String?) {
        this.posterPath = posterPath
    }

    fun getOriginalLanguage(): String? {
        return originalLanguage
    }

    fun setOriginalLanguage(originalLanguage: String?) {
        this.originalLanguage = originalLanguage
    }

    fun getOriginalTitle(): String? {
        return originalTitle
    }

    fun setOriginalTitle(originalTitle: String?) {
        this.originalTitle = originalTitle
    }

    fun getGenreIds(): MutableList<Int?>? {
        return genreIds
    }

    fun setGenreIds(genreIds: MutableList<Int?>?) {
        this.genreIds = genreIds
    }

    fun getBackdropPath(): String? {
        return backdropPath
    }

    fun setBackdropPath(backdropPath: String?) {
        this.backdropPath = backdropPath
    }

    fun getAdult(): Boolean? {
        return adult
    }

    fun setAdult(adult: Boolean?) {
        this.adult = adult
    }

    fun getOverview(): String? {
        return overview
    }

    fun setOverview(overview: String?) {
        this.overview = overview
    }

    fun getReleaseDate(): String? {
        return releaseDate
    }

    fun setReleaseDate(releaseDate: String?) {
        this.releaseDate = releaseDate
    }
}