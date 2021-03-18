package com.roeticvampire.fleet;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.SQLException;

public class CustomApplication extends Application {
    ChatlistDBHelper chatlistDBHelper;
    UserListDBHelper userListDBHelper;
    String username="";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        chatlistDBHelper=new ChatlistDBHelper(getApplicationContext());
        userListDBHelper=new UserListDBHelper(getApplicationContext());



        // Required initialization logic here!
    }
    public void startIncomingMsgs(){
        //we gotta add a child listener, but uske liye pehle realtime ka database refer krna hoga
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("messages").child(username);
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                FirebaseMessage fb = snapshot.getValue(FirebaseMessage.class);

                //process it into a msg, then delete this shit
                try{
                    chatlistDBHelper.insertMessage("Fleet_" + fb.getUsername(), fb.getMessage(), false);
                }catch(Exception e){
                    chatlistDBHelper.addUser("Fleet_" + fb.getUsername());
                    chatlistDBHelper.insertMessage("Fleet_" + fb.getUsername(), fb.getMessage(), false);
                }
            userListDBHelper.updateLastText(fb.getUsername(),fb.getMessage());
                myRef.child(snapshot.getKey()).removeValue();


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}
