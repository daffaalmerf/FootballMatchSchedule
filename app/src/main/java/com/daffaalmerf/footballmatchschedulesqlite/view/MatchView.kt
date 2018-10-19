package com.daffaalmerf.footballmatchschedulesqlite.view

import android.content.Context
import com.daffaalmerf.footballmatchschedulesqlite.model.LeaguesResponse
import com.daffaalmerf.footballmatchschedulesqlite.model.Match

interface MatchView {
    fun showLoading()
    fun hideLoading()
    fun emptyView()
    fun showLeagues(data: LeaguesResponse)
    fun showPrevMatchList(data: List<Match>)
    fun showNextMatchList(data: List<Match>)
    fun showMatchList(data: List<Match>)
}