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
import android.widget.ImageView;

import java.util.ArrayList;

public class ChatlistActivity extends AppCompatActivity {
    chatlistAdapter adapter;
    ImageView User_profile_image;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatlist);

        ArrayList<chatlist_component> chatListMembers = new ArrayList<>();

        //retrieiving all user details
        UserListDBHelper userListDBHelper=new UserListDBHelper(this);
        Cursor csr=userListDBHelper.getAllUsers();
        if(csr.getCount()>0){
            while(csr.moveToNext()){
                String name= csr.getString(1);
                String username= csr.getString(2);
                chatListMembers.add(new chatlist_component(name,username,"Ye aakhiri likha hai jo",R.drawable.floating_btn,"Yesterday"));

            }
        }

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.chatlist_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new chatlistAdapter(this, chatListMembers);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));


    }
}