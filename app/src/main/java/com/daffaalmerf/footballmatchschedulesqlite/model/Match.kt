package com.daffaalmerf.footballmatchschedulesqlite.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Match(
        val id: Long?,
        val idEvent: String?,
        val dateEvent: String?,
        val idHomeTeam: String?,
        val strHomeTeam: String?,
        val intHomeScore: String?,
        val strHomeGoalDetails: String?,
        val intHomeShots: String?,
        val strHomeFormation: String?,
        val strHomeLineupGoalkeeper: String?,
        val strHomeLineupDefense: String?,
        val strHomeLineupMidfield: String?,
        val strHomeLineupForward: String?,
        val strHomeLineupSubstitutes: String?,
        val idAwayTeam: String?,
        val strAwayTeam: String?,
        val intAwayScore: String?,
        val strAwayGoalDetails: String?,
        val intAwayShots: String?,
        val strAwayFormation: String?,
        val strAwayLineupGoalkeeper: String?,
        val strAwayLineupDefense: String?,
        val strAwayLineupMidfield: String?,
        val strAwayLineupForward: String?,
        val strAwayLineupSubstitutes: String?): Parcelable {

    companion object {
        const val TABLE_FAVORITE = "TABLE_FAVORITE"
        const val ID = "ID"
        const val EVENT_ID = "EVENT_ID"
        const val DATE = "DATE"
        const val HOME_ID = "HOME_ID"
        const val HOME_NAME = "HOME_NAME"
        const val HOME_SCORE = "HOME_SCORE"
        const val HOME_GOAL_DETAILS = "HOME_GOAL_DETAILS"
        const val HOME_SHOTS = "HOME_SHOTS"
        const val HOME_FORMATION = "HOME_FORMATION"
        const val HOME_GOALKEEPER = "HOME_GOALKEEPER"
        const val HOME_DEFENSE = "HOME_DEFENSE"
        const val HOME_MIDFIELD = "HOME_MIDFIELD"
        const val HOME_FORWARD = "HOME_FORWARD"
        const val HOME_SUBS = "HOME_SUBS"
        const val AWAY_ID = "AWAY_ID"
        const val AWAY_NAME = "AWAY_NAME"
        const val AWAY_SCORE = "AWAY_SCORE"
        const val AWAY_GOAL_DETAILS = "AWAY_GOAL_DETAILS"
        const val AWAY_SHOTS = "AWAY_SHOTS"
        const val AWAY_FORMATION = "AWAY_FORMATION"
        const val AWAY_GOALKEEPER = "AWAY_GOALKEEPER"
        const val AWAY_DEFENSE = "AWAY_DEFENSE"
        const val AWAY_MIDFIELD = "AWAY_MIDFIELD"
        const val AWAY_FORWARD = "AWAY_FORWARD"
        const val AWAY_SUBS = "AWAY_SUBS"
    }
}