package com.example.programming_mobile_project.Home_Page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.programming_mobile_project.R


class HomePage: Fragment(){

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<MyAdapter.ViewHolder>? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.home_page, container, false)
        return v
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        //qua andra inizializzato la recyclerview
        super.onViewCreated(itemView, savedInstanceState)
        /*
        recycler_view.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
            // set the custom adapter to the RecyclerView
            adapter = MyAdapter()
        }
         */
    }
}