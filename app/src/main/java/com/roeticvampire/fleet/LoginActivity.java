package com.roeticvampire.fleet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText user_email_input, user_password_input;
    ImageButton login_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        user_email_input =findViewById(R.id.input_email);
        user_password_input =findViewById(R.id.login_password);
        login_btn=findViewById(R.id.Continue_btn);
        login_btn.setOnClickListener(v -> {
            //
            String email_id= user_email_input.getText().toString();
            String password= user_password_input.getText().toString();

            //now we gotta check the regex for both our Strings
            if(verifyCredentials(email_id,password)){
                mAuth.signInWithEmailAndPassword(email_id,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            //Successful login, so we need to import the user details, that we saved back in register2 activity
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("Users");
                            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for(DataSnapshot user: snapshot.getChildren()){
                                        User tempUser=user.getValue(User.class);
                                        if(tempUser.getEmail_id().equals(email_id)){
                                            SharedPreferences sharedpreferences = getSharedPreferences("personal_details", Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedpreferences.edit();
                                            editor.putString("name", tempUser.getName());
                                            editor.putString("username", tempUser.getUsername());
                                            editor.putString("email_id", email_id);
                                            FirebaseStorage storage = FirebaseStorage.getInstance();
                                            StorageReference storageRef = storage.getReference();
                                            StorageReference imagesRef = storageRef.child("images/"+tempUser.getUsername()+".jpg");
                                            imagesRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(bytes -> {
                                                String encodedImage = Base64.encodeToString(bytes, Base64.DEFAULT);
                                                editor.putString("image_data",encodedImage);
                                                editor.commit();
                                                Intent intent= new Intent(LoginActivity.this,ChatlistActivity.class);
                                                startActivity(intent);
                                                finish();
                                            });

                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });


                            }
                        else
                            Toast.makeText(LoginActivity.this, "Wrong email or password!", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });


    }

    private boolean verifyCredentials(String email_id, String password) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if( !pat.matcher(email_id).matches())
            return false;
        if(password.length()>15||password.length()<6) return false;

        return true;
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }
}