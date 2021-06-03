package com.example.programming_mobile_project.Home_Page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.programming_mobile_project.R


class HomePage: Fragment(){

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<MyAdapter.ViewHolder>? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        /*
        val view = inflater.inflate(R.layout.home_page, container, false)
        val recycler_view  = view.findViewById<RecyclerView>(R.id.recyclerview)
        recycler_view.layoutManager = LinearLayoutManager(activity)
        recycler_view.adapter = MyAdapter()
        return view
        */
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
    }

    fun getRowsDatabase(){

    }

}