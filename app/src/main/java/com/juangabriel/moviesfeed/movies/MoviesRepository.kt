package com.juangabriel.moviesfeed.movies

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
import com.juangabriel.moviesfeed.http.apimodel.Result
import com.juangabriel.moviesfeed.movies.MoviesMVP.Presenter
import com.juangabriel.moviesfeed.movies.MoviesPresenter
import com.juangabriel.moviesfeed.movies.MoviesModel
import javax.inject.Singleton
import com.juangabriel.moviesfeed.movies.MoviesRepository
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.observers.DisposableObserver
import java.util.ArrayList

class MoviesRepository(
    private val moviesApiService: MoviesApiService?,
    private val extraInfoApisService: MoviesExtraInfoApisService?
) : Repository {
    private val countries: MutableList<String?>?
    private val results: MutableList<Result?>?
    private var lastTimestamp: Long
    fun isUpdated(): Boolean {
        return System.currentTimeMillis() - lastTimestamp < CACHE_LIFETIME
    }

    override fun getResultFromNetwork(): Observable<Result?>? {
        val topMoviesRatedObservable = moviesApiService.getTopMoviesRated(1) /*.concatWith(moviesApiService.getTopMoviesRated(2))
                .concatWith(moviesApiService.getTopMoviesRated(3))*/
        return topMoviesRatedObservable
            .concatMap(object : Function<TopMoviesRated?, Observable<Result?>?> {
                override fun apply(topMoviesRated: TopMoviesRated?): Observable<Result?>? {
                    return Observable.fromIterable(topMoviesRated.getResults())
                }
            }).doOnNext { result -> results.add(result) }
    }

    override fun getResultFromCache(): Observable<Result?>? {
        return if (isUpdated()) {
            Observable.fromIterable(
                results
            )
        } else {
            lastTimestamp = System.currentTimeMillis()
            results.clear()
            Observable.empty()
        }
    }

    override fun getResultData(): Observable<Result?>? {
        return resultFromCache.switchIfEmpty(resultFromNetwork)
    }

    override fun getCountryFromNetwork(): Observable<String?>? {
        return resultFromNetwork.concatMap(object : Function<Result?, Observable<OmdbApi?>?> {
            override fun apply(result: Result?): Observable<OmdbApi?>? {
                return extraInfoApisService.getExtraInfoMovie(result.getTitle())
            }
        }).concatMap(object : Function<OmdbApi?, Observable<String?>?> {
            override fun apply(omdbApi: OmdbApi?): Observable<String?>? {
                return if (omdbApi == null || omdbApi.country == null) {
                    Observable.just("Desconocido")
                } else {
                    Observable.just(omdbApi.country)
                }
            }
        }).doOnNext { country -> countries.add(country) }
    }

    override fun getCountryFromCache(): Observable<String?>? {
        return if (isUpdated()) {
            Observable.fromIterable(countries)
        } else {
            lastTimestamp = System.currentTimeMillis()
            countries.clear()
            Observable.empty()
        }
    }

    override fun getCountryData(): Observable<String?>? {
        return countryFromCache.switchIfEmpty(countryFromNetwork)
    }

    companion object {
        private const val CACHE_LIFETIME = (20 * 1000 //Cache que durará 20 segundos
                ).toLong()
    }

    init {
        lastTimestamp = System.currentTimeMillis()
        countries = ArrayList()
        results = ArrayList()
    }
}