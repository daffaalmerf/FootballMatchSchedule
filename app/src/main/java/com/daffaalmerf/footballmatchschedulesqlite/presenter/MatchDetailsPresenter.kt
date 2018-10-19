package com.daffaalmerf.footballmatchschedulesqlite.presenter

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import com.daffaalmerf.footballmatchschedulesqlite.api.ApiRepository
import com.daffaalmerf.footballmatchschedulesqlite.api.TheSportDBApi
import com.daffaalmerf.footballmatchschedulesqlite.db.database
import com.daffaalmerf.footballmatchschedulesqlite.model.Match
import com.daffaalmerf.footballmatchschedulesqlite.model.MatchDetailsResponse
import com.daffaalmerf.footballmatchschedulesqlite.utils.CoroutineContextProvider
import com.daffaalmerf.footballmatchschedulesqlite.view.MatchDetailsView
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class MatchDetailsPresenter(private val view: MatchDetailsView,
                            private val apiRepository: ApiRepository,
                            private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getMatchDetails(idHomeTeam: String, idAwayTeam: String) {
        view.showLoading()

        async(context.main) {
            val dataHomeTeam = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getMatchDetails(idHomeTeam)),
                        MatchDetailsResponse::class.java
                )
            }

            val dataAwayTeam = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getMatchDetails(idAwayTeam)),
                        MatchDetailsResponse::class.java
                    )
                }

            view.showMatchDetails(dataHomeTeam.await().teams!!, dataAwayTeam.await().teams!!)
            view.hideLoading()
            }
        }

    fun favoriteState(context: Context, data: Match): Boolean {

        var state = false

        context.database.use {
            val result = select(Match.TABLE_FAVORITE).whereArgs(Match.EVENT_ID + " = {id}",
                    "id" to data.idEvent.toString()).parseList(classParser<Match>())

            state = !result.isEmpty()

        }

        return state

    }

    fun addToFavorite(context: Context, data: Match){
        try {
            context.database.use {
                insert(Match.TABLE_FAVORITE,
                        Match.EVENT_ID to data.idEvent,
                        Match.DATE to data.dateEvent,
                        Match.HOME_ID to data.idHomeTeam,
                        Match.HOME_NAME to data.strHomeTeam,
                        Match.HOME_SCORE to data.intHomeScore,
                        Match.HOME_GOAL_DETAILS to data.strHomeGoalDetails,
                        Match.HOME_SHOTS to data.intHomeShots,
                        Match.HOME_FORMATION to data.strHomeFormation,
                        Match.HOME_GOALKEEPER to data.strHomeLineupGoalkeeper,
                        Match.HOME_DEFENSE to data.strHomeLineupDefense,
                        Match.HOME_MIDFIELD to data.strHomeLineupMidfield,
                        Match.HOME_FORWARD to data.strHomeLineupForward,
                        Match.HOME_SUBS to data.strHomeLineupSubstitutes,
                        Match.AWAY_ID to data.idAwayTeam,
                        Match.AWAY_NAME to data.strAwayTeam,
                        Match.AWAY_SCORE to data.intAwayScore,
                        Match.AWAY_GOAL_DETAILS to data.strAwayGoalDetails,
                        Match.AWAY_SHOTS to data.intAwayShots,
                        Match.AWAY_FORMATION to data.strAwayFormation,
                        Match.AWAY_GOALKEEPER to data.strAwayLineupGoalkeeper,
                        Match.AWAY_DEFENSE to data.strAwayLineupDefense,
                        Match.AWAY_MIDFIELD to data.strAwayLineupMidfield,
                        Match.AWAY_FORWARD to data.strAwayLineupForward,
                        Match.AWAY_SUBS to data.strAwayLineupSubstitutes)
            }
        } catch (e: SQLiteConstraintException) {
            context.toast("Error: ${e.message}")
        }
    }

    fun removeFromFavorite(context: Context, data: Match) {
        try {
            context.database.use {
                delete(Match.TABLE_FAVORITE, Match.EVENT_ID + " = {id}", "id" to data.idEvent.toString())
            }
        } catch (e: SQLiteConstraintException) {
            context.toast("Error: ${e.message}")
        }
    }

}