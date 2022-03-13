package com.juangabriel.moviesfeed

import android.support.v7.app.AppCompatActivity
import com.juangabriel.moviesfeed.movies.MoviesMVP
import com.juangabriel.moviesfeed.MainActivity
import butterknife.BindView
import com.juangabriel.moviesfeed.R
import android.view.ViewGroup
import android.support.v7.widget.RecyclerView
import javax.inject.Inject
import com.juangabriel.moviesfeed.movies.MoviesMVP.Presenter
import android.os.Bundle
import butterknife.ButterKnife
import com.juangabriel.moviesfeed.root.App
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.design.widget.Snackbar
import android.util.Log
import com.juangabriel.moviesfeed.movies.ListAdapter
import com.juangabriel.moviesfeed.movies.ViewModel
import java.util.ArrayList

class MainActivity : AppCompatActivity(), MoviesMVP.View {
    private val TAG = MainActivity::class.java.name

    @BindView(R.id.activity_root_view)
    var rootView: ViewGroup? = null

    @BindView(R.id.recycler_view_movies)
    var recyclerView: RecyclerView? = null

    @Inject
    var presenter: Presenter? = null
    private var listAdapter: ListAdapter? = null
    private val resultList: MutableList<ViewModel> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        (application as App).component.inject(this)
        listAdapter = ListAdapter(resultList)
        recyclerView!!.adapter = listAdapter
        recyclerView!!.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.HORIZONTAL
            )
        )
        recyclerView!!.itemAnimator = DefaultItemAnimator()
        recyclerView!!.setHasFixedSize(false)
        recyclerView!!.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        super.onResume()
        presenter!!.setView(this)
        presenter!!.loadData()
    }

    override fun onStop() {
        super.onStop()
        presenter!!.rxJavaUnsuscribe()
        resultList.clear()
        listAdapter!!.notifyDataSetChanged()
    }

    override fun updateData(viewModel: ViewModel) {
        resultList.add(viewModel)
        listAdapter!!.notifyItemInserted(resultList.size - 1)
        Log.d(TAG, "Información nueva: " + viewModel.name)
    }

    override fun showSnackbar(message: String) {
        Snackbar.make(rootView!!, message, Snackbar.LENGTH_SHORT).show()
    }
}