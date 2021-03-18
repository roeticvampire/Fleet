package com.roeticvampire.fleet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatlistActivity extends AppCompatActivity {
    chatlistAdapter adapter;
    ImageView User_profile_image;
    String name,username,email_id;
    TextView username_view,name_view,email_id_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatlist);



        SharedPreferences sharedpreferences = getSharedPreferences("personal_details", Context.MODE_PRIVATE);


        name=sharedpreferences.getString("name","");
        username=sharedpreferences.getString("username","");
        ((CustomApplication)ChatlistActivity.this.getApplication()).setUsername(username);
        email_id=sharedpreferences.getString("email_id","");
        String previouslyEncodedImage = sharedpreferences.getString("image_data", "");
        User_profile_image=findViewById(R.id.profileImage);
        name_view=findViewById(R.id.user_name);
        email_id_view=findViewById(R.id.user_email);
        username_view=findViewById(R.id.user_username);

        name_view.setText(name);
        username_view.setText(username);
        email_id_view.setText(email_id);




        if( !previouslyEncodedImage.equalsIgnoreCase("") ){
            byte[] b = Base64.decode(previouslyEncodedImage, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            User_profile_image.setImageBitmap(bitmap);
        }












        ArrayList<chatlist_component> chatListMembers = new ArrayList<>();


        //retrieiving all user details
        UserListDBHelper userListDBHelper=new UserListDBHelper(this);
        Cursor csr=userListDBHelper.getAllUsers();
        if(csr.getCount()>0){
            while(csr.moveToNext()){
                String name= csr.getString(1);
                String username= csr.getString(2);
                String last_message=csr.getString(4);
                String last_message_time=csr.getString(5);
                byte[] profile_image=csr.getBlob(6);
                chatListMembers.add(new chatlist_component(name,username,last_message,profile_image,last_message_time));

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