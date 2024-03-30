package com.example.appstore.ui.detail.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.appstore.ui.detail_chart.DetailChartFragment
import com.example.appstore.ui.detail_comments.DetailCommentFragment
import com.example.appstore.ui.detail_feature.DetailFeatureFragment

class PagerAdapter(manager: FragmentManager,lifeCycle:Lifecycle):FragmentStateAdapter(manager,lifeCycle){
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {

     return   when(position){

            0->{DetailCommentFragment()}
            1->{DetailFeatureFragment()}
            else->{DetailChartFragment()}

        }


    }

}