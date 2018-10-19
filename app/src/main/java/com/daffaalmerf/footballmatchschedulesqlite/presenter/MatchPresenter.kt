package com.daffaalmerf.footballmatchschedulesqlite.presenter

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.provider.SyncStateContract.Helpers.insert
import com.daffaalmerf.footballmatchschedulesqlite.api.ApiRepository
import com.daffaalmerf.footballmatchschedulesqlite.api.TheSportDBApi
import com.daffaalmerf.footballmatchschedulesqlite.db.database
import com.daffaalmerf.footballmatchschedulesqlite.model.LeaguesResponse
import com.daffaalmerf.footballmatchschedulesqlite.model.Match
import com.daffaalmerf.footballmatchschedulesqlite.model.MatchResponse
import com.daffaalmerf.footballmatchschedulesqlite.utils.CoroutineContextProvider
import com.daffaalmerf.footballmatchschedulesqlite.view.MatchView
import com.google.gson.Gson
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.selects.select
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.custom.async
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import java.nio.file.Files.delete

class MatchPresenter(private val view: MatchView,
                     private val apiRepository: ApiRepository,
                     private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    var viewFragment = 1

    fun getLeagues() {
        view.showLoading()

        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getLeagues()),
                        LeaguesResponse::class.java
                )
            }
            view.showLeagues(data.await())
            view.hideLoading()
        }
    }

    fun getPrevMatchList(id: String) {
        viewFragment = 1
        view.showLoading()

        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getPrevMatchList(id)),
                        MatchResponse::class.java
                )
            }

            view.showPrevMatchList(data.await().events!!)
            view.hideLoading()
        }
    }

    fun getNextMatchList(id: String) {
        viewFragment = 2
        view.showLoading()

        async(context.main)  {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getNextMatchList(id)),
                        MatchResponse::class.java
                )
            }

            view.showNextMatchList(data.await().events!!)
            view.hideLoading()

        }
    }

    fun getFavoriteMatchList(context: Context) {
        viewFragment = 3
        view.showLoading()

        val data: MutableList<Match> = mutableListOf()

        context.database.use {
            val favorites = select(Match.TABLE_FAVORITE)
                    .parseList(classParser<Match>())

            data.addAll(favorites)
        }

        view.hideLoading()

        if (data.size > 0) {
            view.showMatchList(data)
        } else {
            view.emptyView()
        }
    }


}