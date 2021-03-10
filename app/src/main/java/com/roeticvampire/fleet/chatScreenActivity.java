package com.roeticvampire.fleet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class chatScreenActivity extends AppCompatActivity {
    chatboxAdapter adapter;
    ImageButton send_btn;
    Handler handler;
    EditText sendText;
    RecyclerView recyclerView;

    String tableName= "roetess";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);

        ArrayList<Message> animalNames = new ArrayList<>();
       // animalNames.add(new Message(true,"Dhamaka Kardie ho ekdum kya hi bolen","11:29 AM"));
       // animalNames.add(new Message(false,"Dhamaka Kardie ho ekdum kya hi bolen","11:29 AM"));
        //animalNames.add(new Message(true,"Dhamaka Kardie ho ekdum kya hi bolen","11:29 AM"));


        UserListDBHelper userListDbHelper = new UserListDBHelper(this);
        //userListDbHelper.insertUser("SYED","roet");
        //userListDbHelper.insertUser("SYD","roetron");
        //userListDbHelper.insertUser("SED","roetie");

ChatlistDBHelper chatlistDBHelper=new ChatlistDBHelper(this);
        //chatlistDBHelper.addUser("roetess");
        //chatlistDBHelper.insertMessage("roetess","But you will never be left behind...",true);
       // chatlistDBHelper.insertMessage("roetess","Never a place to run in through my blood",false);
        //chatlistDBHelper.insertMessage("roetess","But I can't escape.. it's still in my hand!!",false);

        Cursor csr= chatlistDBHelper.getAllMessages(tableName);
        if(csr.getCount()>0){
            while(csr.moveToNext()){
                int msgId=csr.getInt(0);
                boolean isUser= csr.getInt(1)==1?true:false;
                String msg= csr.getString(2);
                String timing= csr.getString(3);
                animalNames.add(new Message(msgId,isUser,msg,timing));


            }
        }


        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Cursor csr=chatlistDBHelper.getNewMessages("roetess",animalNames.get(animalNames.size() - 1).msgId);
                if(csr.getCount()>0){
                    while(csr.moveToNext()){
                        int msgId=csr.getInt(0);
                        boolean isUser= csr.getInt(1)==1?true:false;
                        String msg= csr.getString(2);
                        String timing= csr.getString(3);
                        animalNames.add(new Message(msgId,isUser,msg,timing));
                    }
                    recyclerView.getAdapter().notifyDataSetChanged();
                    recyclerView.scrollToPosition(animalNames.size() - 1);
                }

                handler.postDelayed(this,1000);
            }


        },20000);


        // set up the RecyclerView
        recyclerView = findViewById(R.id.ChatScreenRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new chatboxAdapter(this, animalNames);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        recyclerView.scrollToPosition(animalNames.size() - 1);
        sendText=findViewById(R.id.sendText_input);
        send_btn=findViewById(R.id.sendText_btn);
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(sendText.getText().toString().length()>0) {
                    //animalNames.add(new Message(true, sendText.getText().toString(), "now"));

                    //will change this one later I guess
                   chatlistDBHelper.insertMessage(tableName,sendText.getText().toString(),true);
                    updateChats();
                    recyclerView.getAdapter().notifyDataSetChanged();
                    recyclerView.scrollToPosition(animalNames.size() - 1);
                    sendText.setText("");
                }


            }

            private void updateChats(){
                Cursor csr=chatlistDBHelper.getNewMessages(tableName,animalNames.get(animalNames.size() - 1).msgId);
                if(csr.getCount()>0){
                    while(csr.moveToNext()){
                        int msgId=csr.getInt(0);
                        boolean isUser= csr.getInt(1)==1?true:false;
                        String msg= csr.getString(2);
                        String timing= csr.getString(3);
                        animalNames.add(new Message(msgId,isUser,msg,timing));


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