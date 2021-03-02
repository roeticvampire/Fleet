package com.roeticvampire.fleet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class ChatlistActivity extends AppCompatActivity {
    chatlistAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatlist);

        ArrayList<chatlist_component> animalNames = new ArrayList<>();
        animalNames.add(new chatlist_component("Spiderman", "jaal khatam ho raha... need help", R.drawable.default_profile_image, "11:39PM"));
        animalNames.add(new chatlist_component("Spiderman", "jaal khatam ho raha... need help", R.drawable.default_profile_image, "11:39PM"));
        animalNames.add(new chatlist_component("Spiderman", "jaal khatam ho raha... need help", R.drawable.default_profile_image, "11:39PM"));


        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.chatlist_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new chatlistAdapter(this, animalNames);
        recyclerView.setAdapter(adapter);

    }
}