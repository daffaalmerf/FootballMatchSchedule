package com.daffaalmerf.footballmatchschedulesqlite.view

import com.daffaalmerf.footballmatchschedulesqlite.model.MatchDetails

interface MatchDetailsView{
    fun showLoading()
    fun hideLoading()
    fun showMatchDetails(homeTeam: List<MatchDetails>, awayTeam: List<MatchDetails>)
}