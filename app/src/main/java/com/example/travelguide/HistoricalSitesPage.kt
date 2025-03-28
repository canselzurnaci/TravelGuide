package com.example.travelguide

import PostsAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class HistoricalSitesPage : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var postsAdapter: PostsAdapter
    private lateinit var databaseReference: DatabaseReference
    private var historicalSites = "Historical Sites"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historical_sites_page)

        recyclerView = findViewById(R.id.recyclerViewHistoricalSites)
        recyclerView.layoutManager = LinearLayoutManager(this)
        postsAdapter = PostsAdapter(historicalSites)
        recyclerView.adapter = postsAdapter

        databaseReference = FirebaseDatabase.getInstance().reference.child("Posts").child(historicalSites)
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val postsList = ArrayList<DataSnapshot>() // Change to DataSnapshot list
                for (postSnapshot in dataSnapshot.children) {
                    postsList.add(postSnapshot) // Add DataSnapshot objects directly
                }
                postsAdapter.setPosts(postsList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error
            }
        })
    }
}