package com.daffaalmerf.footballmatchschedulesqlite.presenter

import com.daffaalmerf.footballmatchschedulesqlite.api.ApiRepository
import com.daffaalmerf.footballmatchschedulesqlite.api.TheSportDBApi
import com.daffaalmerf.footballmatchschedulesqlite.model.Leagues
import com.daffaalmerf.footballmatchschedulesqlite.model.LeaguesResponse
import com.daffaalmerf.footballmatchschedulesqlite.model.Match
import com.daffaalmerf.footballmatchschedulesqlite.model.MatchResponse
import com.daffaalmerf.footballmatchschedulesqlite.utils.TestContextProvider
import com.daffaalmerf.footballmatchschedulesqlite.view.MatchView
import com.google.gson.Gson
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class MatchPresenterTest {

    @Mock
    private lateinit var view: MatchView

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var gson: Gson

    private lateinit var presenter: MatchPresenter

    @Before
    fun setupEnv() {
        MockitoAnnotations.initMocks(this)
        presenter = MatchPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun testGetLeagues() {
        val data: MutableList<Leagues> = mutableListOf()
        val response = LeaguesResponse(data)

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getLeagues()),
                LeaguesResponse::class.java)
        ).thenReturn(response)

        presenter.getLeagues()

        verify(view).showLoading()
        verify(view).showLeagues(response)
        verify(view).hideLoading()
    }

    @Test
    fun testGetPrevMatchList() {
        val match: MutableList<Match> = mutableListOf()
        val response = MatchResponse(match)
        val id = "1234"

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getPrevMatchList(id)),
                MatchResponse::class.java)
        ).thenReturn(response)

        presenter.getPrevMatchList(id)

        verify(view).showLoading()
        verify(view).showPrevMatchList(match)
        verify(view).hideLoading()
    }

    @Test
    fun testGetNextMatchList() {
        val match: MutableList<Match> = mutableListOf()
        val response = MatchResponse(match)
        val id = "1234"

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getNextMatchList(id)),
                MatchResponse::class.java)
        ).thenReturn(response)

        presenter.getNextMatchList(id)

        verify(view).showLoading()
        verify(view).showNextMatchList(match)
        verify(view).hideLoading()
    }
}