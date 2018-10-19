package com.daffaalmerf.footballmatchschedulesqlite.presenter

import com.daffaalmerf.footballmatchschedulesqlite.api.ApiRepository
import com.daffaalmerf.footballmatchschedulesqlite.api.TheSportDBApi
import com.daffaalmerf.footballmatchschedulesqlite.model.MatchDetails
import com.daffaalmerf.footballmatchschedulesqlite.model.MatchDetailsResponse
import com.daffaalmerf.footballmatchschedulesqlite.utils.TestContextProvider
import com.daffaalmerf.footballmatchschedulesqlite.view.MatchDetailsView
import com.google.gson.Gson
import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class MatchDetailsPresenterTest {

    @Mock
    private lateinit var view: MatchDetailsView

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var gson: Gson

    private lateinit var presenter: MatchDetailsPresenter

    @Before
    fun setupEnv() {
        MockitoAnnotations.initMocks(this)
        presenter = MatchDetailsPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getMatchDetails() {
        val data: MutableList<MatchDetails> = mutableListOf()
        val response = MatchDetailsResponse(data)
        val id = "1234"

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getMatchDetails(id)),
                MatchDetailsResponse::class.java)
        ).thenReturn(response)

        presenter.getMatchDetails(id, id)

        verify(view).showLoading()
        verify(view).showMatchDetails(response.teams!!, response.teams!!)
        verify(view).hideLoading()
    }
}