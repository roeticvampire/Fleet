package com.roeticvampire.fleet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;

public class ChatlistActivity extends AppCompatActivity {
    chatlistAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatlist);

        ArrayList<chatlist_component> animalNames = new ArrayList<>();
       //animalNames.add(new chatlist_component("Spiderman", "jaal khatam ho raha... need help", R.drawable.default_profile_image, "11:39PM"));
        //animalNames.add(new chatlist_component("Spiderman", "jaal khatam ho raha... need help", R.drawable.default_profile_image, "11:39PM"));
        //animalNames.add(new chatlist_component("Spiderman", "jaal khatam ho raha... need help", R.drawable.default_profile_image, "11:39PM"));
UserListDBHelper userListDBHelper=new UserListDBHelper(this);
//userListDBHelper.insertUser("Syed","roetess");
Cursor csr=userListDBHelper.getAllUsers();
        if(csr.getCount()>0){
            while(csr.moveToNext()){
                String name= csr.getString(1);
                String timing= csr.getString(2);
                animalNames.add(new chatlist_component(name,"nothing",R.drawable.default_profile_image,"zindagi"));


            }
        }

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.chatlist_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new chatlistAdapter(this, animalNames);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));





    }
}