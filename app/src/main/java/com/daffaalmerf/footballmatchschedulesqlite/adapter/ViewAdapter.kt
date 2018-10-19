package com.daffaalmerf.footballmatchschedulesqlite.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.daffaalmerf.footballmatchschedulesqlite.R
import com.daffaalmerf.footballmatchschedulesqlite.R.id.*
import com.daffaalmerf.footballmatchschedulesqlite.model.Match
import com.daffaalmerf.footballmatchschedulesqlite.utils.DateTime
import org.jetbrains.anko.*

class ViewAdapter(val match: List<Match>, private val listener: (Match) -> Unit): RecyclerView.Adapter<ViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(MatchUI().createView(AnkoContext.create(parent.context, parent)))
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(match[position], listener)
    }

    override fun getItemCount(): Int = match.size

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        val date: TextView = view.findViewById(dateId)
        private val homeTeamName: TextView = view.findViewById(homeTeamNameId)
        private val homeTeamScore: TextView = view.findViewById(homeTeamScoreId)
        private val awayTeamName: TextView = view.findViewById(awayTeamNameId)
        private val awayTeamScore: TextView = view.findViewById(awayTeamScoreId)

        fun bind(match: Match, listener: (Match) -> Unit) {
            date.text = DateTime.getLongDate(match.dateEvent!!)
            homeTeamName.text = match.strHomeTeam
            homeTeamScore.text = match.intHomeScore
            awayTeamName.text = match.strAwayTeam
            awayTeamScore.text = match.intAwayScore

            itemView.setOnClickListener{ listener(match) }

        }
    }

    inner class MatchUI : AnkoComponent<ViewGroup> {
        override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
            linearLayout {
                lparams(matchParent, wrapContent)
                orientation = android.widget.LinearLayout.VERTICAL

                linearLayout {
                    backgroundColor = android.graphics.Color.WHITE
                    orientation = android.widget.LinearLayout.VERTICAL
                    padding = dip(8)

                    textView {
                        id = dateId
                        textColor = android.support.v4.content.ContextCompat.getColor(ctx, R.color.colorPrimary)
                        gravity = android.view.Gravity.CENTER
                    }.lparams(matchParent, wrapContent)

                    linearLayout {
                        gravity = android.view.Gravity.CENTER_VERTICAL

                        textView {
                            id = homeTeamNameId
                            gravity = android.view.Gravity.CENTER
                            textSize = 18f
                            text = context.getString(R.string.home)
                        }.lparams(matchParent, wrapContent, 1f)

                        linearLayout {
                            gravity = android.view.Gravity.CENTER_VERTICAL

                            textView {
                                id = homeTeamScoreId
                                padding = dip(8)
                                textSize = 20f
                                setTypeface(null, android.graphics.Typeface.BOLD)
                                text = "0"
                            }

                            textView {
                                text = context.getString(R.string.versus)
                                textSize = 16f
                            }

                            textView {
                                id = awayTeamScoreId
                                padding = dip(8)
                                textSize = 20f
                                setTypeface(null, android.graphics.Typeface.BOLD)
                                text = "0"
                            }
                        }

                        textView {
                            id = awayTeamNameId
                            gravity = android.view.Gravity.CENTER
                            textSize = 18f
                            text = context.getString(R.string.Away)
                        }.lparams(matchParent, wrapContent, 1f)
                    }
                }.lparams(matchParent, matchParent) {
                    setMargins(dip(16), dip(8), dip(16), dip(8))
                }
            }
        }
    }
}