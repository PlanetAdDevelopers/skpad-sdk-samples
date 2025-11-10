package com.skplanet.app.skpadbenefitsample

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.skplanet.skpad.benefit.presentation.feed.header.FeedHeaderViewAdapter

class CustomFeedHeaderViewAdapter: FeedHeaderViewAdapter {
    override fun onCreateView(context: Context?, parent: ViewGroup?): View {
        val inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        return inflater.inflate(R.layout.view_custom_feed_header, parent, false)
    }

    override fun onBindView(view: View?, reward: Int) {

        val rewardTextView = view?.findViewById<TextView>(R.id.total_reward_text)
        rewardTextView?.text = "적립가능한 포인트 : $reward"
    }

    override fun onDestroyView() {
    }
}