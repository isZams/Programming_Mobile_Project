package com.example.programming_mobile_project.Home_Page

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.programming_mobile_project.R
import com.example.programming_mobile_project.chalet_admin.Chalet
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class HomePage: Fragment(){

    //private val storage = Firebase.storage.reference
    private var database: DatabaseReference? = null
    private var chaletList : ArrayList<Chalet>? = null
    private var recyclerview : RecyclerView? = null
    private var adapter: RecyclerView.Adapter<MyAdapter.ViewHolder>? = null
    private val db: FirebaseDatabase = Firebase.database("https://book-beach-default-rtdb.europe-west1.firebasedatabase.app/")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.home_page, container, false)


        return view
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)

        recyclerview  = itemView.findViewById<RecyclerView>(R.id.recyclerview)
        database = db.getReference("chalets")
        recyclerview?.layoutManager = LinearLayoutManager(activity)
        recyclerview?.setHasFixedSize(true)

        chaletList = arrayListOf<Chalet>()
        adapter = MyAdapter(requireContext(), chaletList!!)
        recyclerview?.adapter = adapter

        Log.d("fuoriDataCHange", "fuoriDataChange")
        database?.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("dentroDataCHange", "dentroDataChange")
                for (h in snapshot.children){
                    val singlechalet: Chalet? = h.child("info").getValue(Chalet::class.java)
                    chaletList?.add(singlechalet!!)
                    Log.d("valore", h.value.toString())
                }
                adapter!!.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("error", "Failed to read value.", error.toException())
            }
        })
    }


}