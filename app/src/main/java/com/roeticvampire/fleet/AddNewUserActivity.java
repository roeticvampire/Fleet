package com.roeticvampire.fleet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AddNewUserActivity extends AppCompatActivity {
    EditText searchbar;
    TextView userPrompt;
    String username;
    Button search_btn;
    int btn_code=0; //0 for find user, 1 for found hogya chalo ab add krdo entry me
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adduser_fragment);
        searchbar=findViewById(R.id.search_username);
        userPrompt=findViewById(R.id.userPrompt);
        search_btn=findViewById(R.id.addUserButton);

        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        getWindow().setLayout((int)(0.8*dm.widthPixels),(int)(0.4*dm.heightPixels));
        WindowManager.LayoutParams params=getWindow().getAttributes();
        params.gravity= Gravity.CENTER;
        params.x=0;
        params.y=-10;
        getWindow().setAttributes(params);




        search_btn.setOnClickListener(v->{
            username=searchbar.getText().toString();
            if(username.length()>0) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Users");
                if (btn_code == 0) {
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(username)) {
                                userPrompt.setTextColor(getResources().getColor(R.color.green_success));
                                userPrompt.setText("User found");
                                userPrompt.setVisibility(View.VISIBLE);
                                btn_code = 1;
                                search_btn.setText("ADD");


                            } else {
                                userPrompt.setTextColor(getResources().getColor(R.color.red_error));
                                userPrompt.setText("User not found");
                                userPrompt.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else {
                    //btn is now code 1, user is found
                    myRef.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            User tempUser = snapshot.getValue(User.class);
                            FirebaseStorage storage = FirebaseStorage.getInstance();
                            StorageReference storageRef = storage.getReference();
                            StorageReference imagesRef = storageRef.child("images/" + tempUser.getUsername() + ".jpg");
                            imagesRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(bytes -> {
                                UserListDBHelper userListDBHelper = new UserListDBHelper(getApplicationContext());
                                userListDBHelper.insertUser(tempUser.getName(), tempUser.getUsername(), bytes);
                                finish();
                            });


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

            }
        });





    }
}