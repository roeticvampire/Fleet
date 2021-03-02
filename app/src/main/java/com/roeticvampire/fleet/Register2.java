package com.roeticvampire.fleet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class Register2 extends AppCompatActivity {

    TextView tap_to_change;
    ImageView imageChooser;
    private static final int IMAGE_PICK_CODE=1000;
    private static final int PERMISSION_CODE=1001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        tap_to_change=findViewById(R.id.rand);
        imageChooser=findViewById(R.id.Imagechooser);
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