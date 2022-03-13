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
import com.juangabriel.moviesfeed.movies.MoviesMVP.Presenter
import com.juangabriel.moviesfeed.movies.MoviesPresenter
import com.juangabriel.moviesfeed.movies.MoviesModel
import javax.inject.Singleton
import com.juangabriel.moviesfeed.movies.MoviesRepository
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver

class MoviesPresenter(private val model: MoviesMVP.Model?) : Presenter {
    private var view: MoviesMVP.View? = null
    private var subscription: Disposable? = null
    override fun loadData() {
        subscription = model.result()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<ViewModel?>() {
                override fun onNext(viewModel: ViewModel?) {
                    if (view != null) {
                        view.updateData(viewModel)
                    }
                }

                override fun onError(e: Throwable?) {
                    e.printStackTrace()
                    if (view != null) {
                        view.showSnackbar("Error al descargar las películas...")
                    }
                }

                override fun onComplete() {
                    if (view != null) {
                        view.showSnackbar("Información descargada con éxito")
                    }
                }
            })
    }

    override fun rxJavaUnsuscribe() {
        if (subscription != null && !subscription.isDisposed()) {
            subscription.dispose()
        }
    }

    override fun setView(view: MoviesMVP.View?) {
        this.view = view
    }
}