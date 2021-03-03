package com.roeticvampire.fleet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;

public class chatScreenActivity extends AppCompatActivity {
chatboxAdapter adapter;
    ImageButton send_btn;
    EditText sendText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);

        ArrayList<Message> animalNames = new ArrayList<>();
        animalNames.add(new Message(true,"Dhamaka Kardie ho ekdum kya hi bolen","11:29 AM"));
        animalNames.add(new Message(false,"Dhamaka Kardie ho ekdum kya hi bolen","11:29 AM"));
        animalNames.add(new Message(true,"Dhamaka Kardie ho ekdum kya hi bolen","11:29 AM"));

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.ChatScreenRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new chatboxAdapter(this, animalNames);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        sendText=findViewById(R.id.sendText_input);
        send_btn=findViewById(R.id.sendText_btn);
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(sendText.getText().toString().length()>0) {
                    animalNames.add(new Message(true, sendText.getText().toString(), "now"));
                    recyclerView.getAdapter().notifyDataSetChanged();
                    recyclerView.scrollToPosition(animalNames.size() - 1);
                    sendText.setText("");
                }
            }
        });

    }
}