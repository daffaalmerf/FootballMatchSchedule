package com.daffaalmerf.footballmatchschedulesqlite.activity

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.ScrollView
import com.daffaalmerf.footballmatchschedulesqlite.R
import com.daffaalmerf.footballmatchschedulesqlite.R.color.colorAccent
import com.daffaalmerf.footballmatchschedulesqlite.api.ApiRepository
import com.daffaalmerf.footballmatchschedulesqlite.model.Match
import com.daffaalmerf.footballmatchschedulesqlite.model.MatchDetails
import com.daffaalmerf.footballmatchschedulesqlite.presenter.MatchDetailsPresenter
import com.daffaalmerf.footballmatchschedulesqlite.utils.DateTime
import com.daffaalmerf.footballmatchschedulesqlite.utils.invisible
import com.daffaalmerf.footballmatchschedulesqlite.utils.visible
import com.daffaalmerf.footballmatchschedulesqlite.view.MatchDetailsView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.swipeRefreshLayout

const val DETAIL_VIEW = "DETAIL_VIEW"

class DetailsActivity : AppCompatActivity(), MatchDetailsView {

    private lateinit var presenter: MatchDetailsPresenter
    private lateinit var progressBar: ProgressBar
    private lateinit var scrollView: ScrollView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var homeTeamBadge: ImageView
    private lateinit var awayTeamBadge: ImageView
    private lateinit var details: Match
    private var menuItem: Menu? = null
    private var favoriteState: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        details = intent.getParcelableExtra<Match>(DETAIL_VIEW)

        defaultLayout(details)

        presenter = MatchDetailsPresenter(this, ApiRepository(), Gson())
        presenter.getMatchDetails(details.idHomeTeam?: "", details.idAwayTeam?: "")
        favoriteState = presenter.favoriteState(ctx, details)

        supportActionBar?.title = "Match Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    @SuppressLint("RtlHardcoded")
    fun defaultLayout(match: Match) {
        relativeLayout {

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                scrollView = scrollView {
                    linearLayout {
                        orientation = LinearLayout.VERTICAL
                        padding = dip(16)

                        textView {
                            gravity = Gravity.CENTER
                            textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                            text = DateTime.getLongDate(match.dateEvent!!)
                        }

                        linearLayout {
                            gravity = Gravity.CENTER

                            verticalLayout {
                                homeTeamBadge = imageView() {
                                }.lparams {
                                    width = dip(75)
                                    height = dip(75)
                                    gravity = Gravity.CENTER
                                }

                                textView {
                                    gravity = Gravity.CENTER
                                    textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                                    textSize = 18f
                                    text = match.strHomeTeam
                                }

                                textView {
                                    gravity = Gravity.CENTER
                                    text = match.strHomeFormation
                                }

                            }.lparams(wrapContent, wrapContent, 1f)

                            textView {
                                textSize = 32f
                                setTypeface(null, Typeface.BOLD)
                                text = match.intHomeScore
                            }

                            textView {
                                padding = dip(16)
                                textSize = 24f
                                text = context.getString(R.string.versus)
                            }

                            textView {
                                textSize = 32f
                                setTypeface(null, Typeface.BOLD)
                                text = match.intAwayScore
                            }

                            linearLayout {
                                orientation = LinearLayout.VERTICAL

                                awayTeamBadge = imageView() {
                                }.lparams {
                                    width = dip(75)
                                    height = dip(75)
                                    gravity = Gravity.CENTER
                                    padding = dip(8)
                                }

                                textView {
                                    gravity = Gravity.CENTER
                                    textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                                    textSize = 18f
                                    text = match.strAwayTeam
                                }

                                textView {
                                    gravity = Gravity.CENTER
                                    text = match.strAwayFormation
                                }

                            }.lparams(wrapContent, wrapContent, 1f)

                        }

                        view {
                            backgroundColor = ContextCompat.getColor(ctx, R.color.colorDivider)
                        }.lparams(matchParent, dip(1)) {
                            topMargin = dip(8)
                        }

                        linearLayout {
                            topPadding = dip(8)

                            textView {
                                text = match.strHomeGoalDetails
                            }.lparams(matchParent, wrapContent, 1f)

                            textView {
                                leftPadding = dip(8)
                                rightPadding = dip(8)
                                textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                                text = context.getString(R.string.goals)
                            }

                            textView {
                                gravity = Gravity.RIGHT
                                text = match.strAwayGoalDetails
                            }.lparams(matchParent, wrapContent, 1f)
                        }

                        linearLayout {
                            topPadding = dip(16)

                            textView {
                                text = match.intHomeShots
                            }.lparams(matchParent, wrapContent, 1f)

                            textView {
                                leftPadding = dip(8)
                                rightPadding = dip(8)
                                textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                                text = context.getString(R.string.shots)
                            }

                            textView {
                                gravity = Gravity.RIGHT
                                text = match.intAwayShots
                            }.lparams(matchParent, wrapContent, 1f)
                        }

                        view {
                            backgroundColor = ContextCompat.getColor(ctx, R.color.colorDivider)
                        }.lparams(matchParent, dip(1)) {
                            topMargin = dip(8)
                        }

                        textView {
                            topPadding = dip(8)
                            gravity = Gravity.CENTER
                            textSize = 18f
                            text = context.getString(R.string.lineups)
                        }

                        linearLayout {
                            topPadding = dip(16)

                            textView {
                                text = match.strHomeLineupGoalkeeper
                            }.lparams(matchParent, wrapContent, 1f)

                            textView {
                                leftPadding = dip(8)
                                rightPadding = dip(8)
                                textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                                text = context.getString(R.string.goal_keeper)
                            }

                            textView {
                                gravity = Gravity.RIGHT
                                text = match.strAwayLineupGoalkeeper
                            }.lparams(matchParent, wrapContent, 1f)
                        }

                        linearLayout {
                            topPadding = dip(16)

                            textView {
                                text = match.strHomeLineupDefense
                            }.lparams(matchParent, wrapContent, 1f)

                            textView {
                                leftPadding = dip(8)
                                rightPadding = dip(8)
                                textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                                text = context.getString(R.string.defense)
                            }

                            textView {
                                gravity = Gravity.RIGHT
                                text = match.strAwayLineupDefense
                            }.lparams(matchParent, wrapContent, 1f)
                        }

                        linearLayout {
                            topPadding = dip(16)

                            textView {
                                text = match.strHomeLineupMidfield
                            }.lparams(matchParent, wrapContent, 1f)

                            textView {
                                leftPadding = dip(8)
                                rightPadding = dip(8)
                                textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                                text = context.getString(R.string.midfield)
                            }

                            textView {
                                gravity = Gravity.RIGHT
                                text = match.strAwayLineupMidfield
                            }.lparams(matchParent, wrapContent, 1f)
                        }

                        linearLayout {
                            topPadding = dip(16)

                            textView {
                                text = match.strHomeLineupForward
                            }.lparams(matchParent, wrapContent, 1f)

                            textView {
                                leftPadding = dip(8)
                                rightPadding = dip(8)
                                textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                                text = context.getString(R.string.forward)
                            }

                            textView {
                                gravity = Gravity.RIGHT
                                text = match.strAwayLineupForward
                            }.lparams(matchParent, wrapContent, 1f)
                        }

                        linearLayout {
                            topPadding = dip(16)

                            textView {
                                text = match.strHomeLineupSubstitutes
                            }.lparams(matchParent, wrapContent, 1f)

                            textView {
                                leftPadding = dip(8)
                                rightPadding = dip(8)
                                textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                                text = context.getString(R.string.subs)
                            }

                            textView {
                                gravity = Gravity.RIGHT
                                text = match.strAwayLineupSubstitutes
                            }.lparams(matchParent, wrapContent, 1f)
                        }
                    }
                }
            }

            progressBar = progressBar {
            }.lparams {
                centerInParent()
            }
        }
    }

    override fun showMatchDetails(homeTeam: List<MatchDetails>, awayTeam: List<MatchDetails>) {
        Picasso.get()
                .load(homeTeam[0].strTeamBadge)
                .into(homeTeamBadge)

        Picasso.get()
                .load(awayTeam[0].strTeamBadge)
                .into(awayTeamBadge)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        setFavorite()

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.add_to_favorite -> {
                if (favoriteState) {
                    presenter.removeFromFavorite(ctx, details)
                } else {
                    presenter.addToFavorite(ctx, details)
                }

                favoriteState = !favoriteState
                setFavorite()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setFavorite() {
        if (favoriteState)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
    }

    override fun showLoading() {
        progressBar.visible()
        scrollView.invisible()
    }

    override fun hideLoading() {
        progressBar.invisible()
        scrollView.visible()
    }

}