package com.daffaalmerf.footballmatchschedulesqlite.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.daffaalmerf.footballmatchschedulesqlite.model.Match
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "Favorite.db", null, 1) {
    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance as MyDatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Here you create tables
        db.createTable(Match.TABLE_FAVORITE, true,
                Match.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                Match.EVENT_ID to TEXT,
                Match.DATE to TEXT,
                Match.HOME_ID to TEXT,
                Match.HOME_NAME to TEXT,
                Match.HOME_SCORE to TEXT,
                Match.HOME_GOAL_DETAILS to TEXT,
                Match.HOME_SHOTS to TEXT,
                Match.HOME_FORMATION to TEXT,
                Match.HOME_GOALKEEPER to TEXT,
                Match.HOME_DEFENSE to TEXT,
                Match.HOME_MIDFIELD to TEXT,
                Match.HOME_FORWARD to TEXT,
                Match.HOME_SUBS to TEXT,
                Match.AWAY_ID to TEXT,
                Match.AWAY_NAME to TEXT,
                Match.AWAY_SCORE to TEXT,
                Match.AWAY_GOAL_DETAILS to TEXT,
                Match.AWAY_SHOTS to TEXT,
                Match.AWAY_FORMATION to TEXT,
                Match.AWAY_GOALKEEPER to TEXT,
                Match.AWAY_DEFENSE to TEXT,
                Match.AWAY_MIDFIELD to TEXT,
                Match.AWAY_FORWARD to TEXT,
                Match.AWAY_SUBS to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(Match.TABLE_FAVORITE, true)
    }
}

val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)