package com.example.android.mvvmarchitectureexample

import android.arch.lifecycle.Observer
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.android.mvvmarchitectureexample.model.MainModel
import com.example.android.mvvmarchitectureexample.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private lateinit var mMainViewModel: MainViewModel
    private lateinit var mAddressAdapter: AddressAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMainViewModel = MainViewModel(MainModel())
        loadView()
        respondToClicks()
        listenToObservables()
    }

    private fun listenToObservables() {
        mMainViewModel.getItemObservable().observe(this, Observer { goToDetailActivity(it!!) })
        mMainViewModel.getResultListObservable().observe(this, Observer {
            hideProgressBar()
            updateMovieList(it!!)
        })
        mMainViewModel.getResultListErrorObservable().observe(this, Observer {
            hideProgressBar()
            showErrorMessage(it!!.message())
        })
    }

    private fun loadView() {
        setContentView(R.layout.activity_main)
        mAddressAdapter = AddressAdapter()
        main_activity_recyclerView.adapter = mAddressAdapter
    }

    private fun respondToClicks() {
        main_activity_button.setOnClickListener({
            showProgressBar()
            mMainViewModel.findAddress(main_activity_editText.text.toString())
        })
        mAddressAdapter setItemClickMethod {
            mMainViewModel.doOnItemClick(it)
        }
    }

    fun showProgressBar() {
        main_activity_progress_bar.visibility = View.VISIBLE
    }

    fun hideProgressBar() {
        main_activity_progress_bar.visibility = View.GONE
    }

    fun showErrorMessage(errorMsg: String) {
        Toast.makeText(this, "Error retrieving data: $errorMsg", Toast.LENGTH_SHORT).show()
    }

    fun updateMovieList(t: List<String>) {
        mAddressAdapter.updateList(t)
        mAddressAdapter.notifyDataSetChanged()
    }

    fun goToDetailActivity(item: MainModel.ResultEntity) {
        var bundle = Bundle()
        bundle.putString(DetailsActivity.Constants.RATING, item.rating)
        bundle.putString(DetailsActivity.Constants.TITLE, item.title)
        bundle.putString(DetailsActivity.Constants.YEAR, item.year)
        bundle.putString(DetailsActivity.Constants.DATE, item.date)
        var intent = Intent(this, DetailsActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }
}
