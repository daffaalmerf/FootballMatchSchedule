package com.daffaalmerf.footballmatchschedulesqlite.activity

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.widget.*
import com.daffaalmerf.footballmatchschedulesqlite.R
import com.daffaalmerf.footballmatchschedulesqlite.adapter.ViewAdapter
import com.daffaalmerf.footballmatchschedulesqlite.api.ApiRepository
import com.daffaalmerf.footballmatchschedulesqlite.model.Leagues
import com.daffaalmerf.footballmatchschedulesqlite.model.LeaguesResponse
import com.daffaalmerf.footballmatchschedulesqlite.model.Match
import com.daffaalmerf.footballmatchschedulesqlite.presenter.MatchPresenter
import com.daffaalmerf.footballmatchschedulesqlite.utils.gone
import com.daffaalmerf.footballmatchschedulesqlite.utils.invisible
import com.daffaalmerf.footballmatchschedulesqlite.utils.visible
import com.daffaalmerf.footballmatchschedulesqlite.view.MatchView
import com.google.gson.Gson
import org.jetbrains.anko.*
import com.daffaalmerf.footballmatchschedulesqlite.R.id.*
import org.jetbrains.anko.design.bottomNavigationView
import org.jetbrains.anko.recyclerview.v7.recyclerView

class MainActivity() : AppCompatActivity(), MatchView {

    lateinit var presenter: MatchPresenter
    lateinit var adapter: ViewAdapter

    lateinit var spinner: Spinner
    lateinit var progressBar: ProgressBar
    lateinit var listMatch: RecyclerView
    lateinit var emptyView: LinearLayout

    lateinit var league: Leagues

    var events: MutableList<Match> = mutableListOf()

    private val navigation = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        defaultLayout()

        presenter = MatchPresenter(this, ApiRepository(), Gson())
        adapter = ViewAdapter(events) { startActivity<DetailsActivity>(DETAIL_VIEW to it) }

        presenter.getLeagues()
        listMatch.adapter = adapter

    }

    override fun onResume() {
        super.onResume()

        if (presenter.viewFragment == 3) presenter.getFavoriteMatchList(ctx)
    }

    private fun defaultLayout() {
        linearLayout {
            orientation = LinearLayout.VERTICAL

            linearLayout {
                orientation = LinearLayout.VERTICAL

                spinner = spinner {
                    id = spinnerMatch
                    padding = dip(8)
                    minimumHeight = dip(40)
                }
            }

            relativeLayout {
                emptyView = linearLayout {
                    orientation = LinearLayout.VERTICAL

                    textView {
                        gravity = Gravity.CENTER
                        padding = dip(8)
                        text = context.getString(R.string.emptyFragment)
                    }
                }.lparams {
                    centerInParent()
                }

                listMatch = recyclerView {
                    id = list_match
                    layoutManager = LinearLayoutManager(ctx)
                }.lparams(matchParent, matchParent) {
                    topOf(navigation)
                }

                progressBar = progressBar {
                }.lparams {
                    centerInParent()
                }

                bottomNavigationView {
                    id = navigation
                    backgroundColor = Color.WHITE

                    menu.apply {
                        add(0, R.id.prev_match, 0, "Prev Match")
                                .setIcon(R.drawable.ic_trophy)
                                .setOnMenuItemClickListener {
                                    presenter.getPrevMatchList(league.idLeague!!)
                                    false
                                }

                        add(0, R.id.next_match, 0,"Next Match")
                                .setIcon(R.drawable.ic_event_black)
                                .setOnMenuItemClickListener {
                                    presenter.getNextMatchList(league.idLeague!!)
                                    false
                                }

                        add(0, R.id.favorite_match, 0,"Favorites")
                                .setIcon(R.drawable.ic_baseline_stars)
                                .setOnMenuItemClickListener{
                                    presenter.getFavoriteMatchList(ctx)
                                    false
                                }
                    }
                }.lparams(matchParent, wrapContent) {
                    alignParentBottom()
                }
            }
        }
    }

    override fun showLeagues(data: LeaguesResponse) {

        spinner.adapter = ArrayAdapter(ctx, R.layout.support_simple_spinner_dropdown_item, data.leagues)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                league = spinner.selectedItem as Leagues

                when (presenter.viewFragment) {
                    1 -> presenter.getPrevMatchList(league.idLeague!!)
                    2 -> presenter.getNextMatchList(league.idLeague!!)
                }
            }
        }
    }


    override fun showLoading() {
        progressBar.visible()
        listMatch.invisible()
        emptyView.invisible()

        if (presenter.viewFragment == 3) spinner.gone()
        else spinner.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
        listMatch.visible()
        emptyView.invisible()
    }

    override fun emptyView() {
        progressBar.invisible()
        listMatch.invisible()
        emptyView.visible()
    }

    override fun showPrevMatchList(data: List<Match>) {
        events.clear()
        events.addAll(data)
        adapter.notifyDataSetChanged()
        listMatch.visible()
        listMatch.scrollToPosition(0)
    }

    override fun showNextMatchList(data: List<Match>) {
        events.clear()
        events.addAll(data)
        adapter.notifyDataSetChanged()
        listMatch.visible()
        listMatch.scrollToPosition(0)
    }

    override fun showMatchList(data: List<Match>) {
        events.clear()
        events.addAll(data)
        adapter.notifyDataSetChanged()
        listMatch.visible()
        listMatch.scrollToPosition(0)
    }

}