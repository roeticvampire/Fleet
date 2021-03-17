package com.roeticvampire.fleet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class chatScreenActivity extends AppCompatActivity {
    chatboxAdapter adapter;
    ImageButton send_btn;
    Handler handler;
    EditText sendText;
    RecyclerView recyclerView;
    ImageButton back_btn;
    ImageView profileImage;
    TextView nameView,usernameView;
    String name;
    String username;
    String tableName;
    Bitmap profileImageData;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);

        ArrayList<Message> messageArrayList = new ArrayList<>();

        Intent intent=getIntent();
        name= intent.getStringExtra("name");
        username= intent.getStringExtra("username");
        profileImageData= BitmapFactory.decodeByteArray(intent.getByteArrayExtra("profileImage"), 0, intent.getByteArrayExtra("profileImage").length);
        tableName="Fleet_"+username;
        UserListDBHelper userListDbHelper = new UserListDBHelper(this);

        ChatlistDBHelper chatlistDBHelper=new ChatlistDBHelper(this);
        chatlistDBHelper.addUser(tableName);
        //chatlistDBHelper.insertMessage("roetess","But you will never be left behind...",true);

        Cursor csr= chatlistDBHelper.getAllMessages(tableName);
        if(csr.getCount()>0){
            while(csr.moveToNext()){
                int msgId=csr.getInt(0);
                boolean isUser= csr.getInt(1) == 1;
                String msg= csr.getString(2);
                String timing= csr.getString(3);
                messageArrayList.add(new Message(msgId,isUser,msg,timing));
            }
        }


        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Cursor csr=chatlistDBHelper.getNewMessages(tableName,messageArrayList.get(messageArrayList.size() - 1).msgId);
                if(csr.getCount()>0){
                    while(csr.moveToNext()){
                        int msgId=csr.getInt(0);
                        boolean isUser= csr.getInt(1)==1?true:false;
                        String msg= csr.getString(2);
                        String timing= csr.getString(3);
                        messageArrayList.add(new Message(msgId,isUser,msg,timing));
                    }
                    recyclerView.getAdapter().notifyDataSetChanged();
                    recyclerView.scrollToPosition(messageArrayList.size() - 1);
                }

                handler.postDelayed(this,4000);
            }


        },20000);


        // set up the RecyclerView
        recyclerView = findViewById(R.id.ChatScreenRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new chatboxAdapter(this, messageArrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
         recyclerView.scrollToPosition(messageArrayList.size() - 1);

        back_btn=findViewById(R.id.back_btn);
        profileImage=findViewById(R.id.profileImage);
        usernameView=findViewById(R.id.user_username);
        nameView=findViewById(R.id.user_name);
        sendText=findViewById(R.id.username_input);
        send_btn=findViewById(R.id.sendText_btn);

        back_btn.setOnClickListener(v -> finish());
        profileImage.setImageBitmap(profileImageData);
        usernameView.setText("@"+username);
        nameView.setText(name);
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(sendText.getText().toString().length()>0) {
                    //messageArrayList.add(new Message(true, sendText.getText().toString(), "now"));

                    //will change this one later I guess
                   chatlistDBHelper.insertMessage(tableName,sendText.getText().toString(),true);
                    updateChats();
                    recyclerView.getAdapter().notifyDataSetChanged();
                    recyclerView.scrollToPosition(messageArrayList.size() - 1);
                    sendText.setText("");
                }


            }

            private void updateChats(){int _id=0;
                try {
                    _id = messageArrayList.get(messageArrayList.size() - 1).msgId;
                }
                catch(Exception e){}
                Cursor csr=chatlistDBHelper.getNewMessages(tableName,_id);
                if(csr.getCount()>0){
                    while(csr.moveToNext()){
                        int msgId=csr.getInt(0);
                        boolean isUser= csr.getInt(1)==1?true:false;
                        String msg= csr.getString(2);
                        String timing= csr.getString(3);
                        messageArrayList.add(new Message(msgId,isUser,msg,timing));


                    }
                }

            }

            private void gettingAllUserDetails() {
                Cursor csr=userListDbHelper.getAllUsers();
                if(csr.getCount()>0){
                    while(csr.moveToNext()){
                        String str="";
                        str+="\nID: "+csr.getInt(0);
                        str+="\nNAME: "+csr.getString(1);
                        str+="\nUSERNAME: "+csr.getString(2);
                        str+="\nTABLE NAME: "+csr.getString(3);
                    }
                }

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}