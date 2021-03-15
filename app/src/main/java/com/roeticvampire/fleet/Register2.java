package com.roeticvampire.fleet;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.theartofdev.edmodo.cropper.CropImage;

public class Register2 extends AppCompatActivity {

    TextView tap_to_change;
    ImageView imageChooser;
    private static final int IMAGE_PICK_CODE=1000;
    private static final int PERMISSION_CODE=1001;
    String name,username,email_id;
    ImageButton register_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent= getIntent();
        name=intent.getStringExtra("name");
        username=intent.getStringExtra("username");
        email_id=intent.getStringExtra("email_id");


        setContentView(R.layout.activity_register2);
        tap_to_change=findViewById(R.id.rand);
        imageChooser=findViewById(R.id.other_profileImage);
        imageChooser.setMinimumHeight(imageChooser.getWidth());
        tap_to_change.setOnLongClickListener(new View.OnLongClickListener(){

            @Override
            public boolean onLongClick(View v) {
                imageChooser.setImageResource(R.drawable.default_profile_image);
                return false;
            }
        });

        imageChooser.setImageResource(R.drawable.default_profile_image);
        imageChooser.setOnClickListener(v -> {
            if(Build.VERSION.SDK_INT>+Build.VERSION_CODES.M){
                if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                    String[] permissions ={Manifest.permission.READ_EXTERNAL_STORAGE};
                    requestPermissions(permissions, PERMISSION_CODE);
                }
            }//maga iff
        pickImageFromGallery();

        });





register_btn=findViewById(R.id.Continue_btn);
        register_btn.setOnClickListener(v->{
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Users");
            myRef.child(username).setValue(new User(name,username,email_id,((BitmapDrawable) imageChooser.getDrawable()).getBitmap()));
        });

    }

    private void pickImageFromGallery() {
    Intent intent = new Intent(Intent.ACTION_PICK);
    intent.setType("image/*");
    startActivityForResult(intent,IMAGE_PICK_CODE);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {

            CropImage.activity(data.getData()).setAspectRatio(1,1)
                    .start(this);


        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                imageChooser.setImageURI(resultUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}