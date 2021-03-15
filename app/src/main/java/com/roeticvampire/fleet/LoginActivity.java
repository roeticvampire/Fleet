package com.roeticvampire.fleet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
        login_btn=findViewById(R.id.login_login_btn);
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
                            Intent intent= new Intent(LoginActivity.this,ChatlistActivity.class);
                        startActivity(intent);}
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