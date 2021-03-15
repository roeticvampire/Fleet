package com.roeticvampire.fleet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegisterActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText name_input,username_input,email_input,password_input,password2_input;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email_input=findViewById(R.id.input_email);
        name_input=findViewById(R.id.input_name);
        password2_input=findViewById(R.id.input_password2);
        password_input=findViewById(R.id.input_password);
        username_input=findViewById(R.id.username_input);

        if(verifyCredentials())
        createUser();


    }

    private boolean verifyCredentials() {
        return true;
    }

    private void createUser() {
        mAuth.createUserWithEmailAndPassword(email_input.getText().toString(),password_input.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            Toast.makeText(RegisterActivity.this, "We did it I guess?", Toast.LENGTH_SHORT).show();
        }
    });
    }
}