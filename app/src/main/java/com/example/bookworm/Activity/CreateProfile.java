package com.example.bookworm.Activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bookworm.Models.UserDetails;
import com.example.bookworm.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

public class CreateProfile extends AppCompatActivity {

    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_LOAD_IMAGE = 1;

    ImageView profileImage,editBtn;
    EditText address, city, province, postalCode;
    Button btnNext;
    String currentPhotoPath;
    public Uri imageUri;

   private FirebaseDatabase fireDB;
   private DatabaseReference db;
   private StorageReference profileImageStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        profileImage = findViewById(R.id.profileImage);
        address = findViewById(R.id.addresstxt);
        city = findViewById(R.id.citytxt);
        province = findViewById(R.id.provincetxt);
        postalCode = findViewById(R.id.postalcodetxt);
        btnNext = findViewById(R.id.next);
        editBtn = findViewById(R.id.profileEdit);

        profileImageStorage = FirebaseStorage.getInstance().getReference("ProfileImages");
        db = FirebaseDatabase.getInstance().getReference("UserDetails");


//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
//                != PackageManager.PERMISSION_DENIED) {
//            editBtn.setEnabled(false);
//
//            ActivityCompat.requestPermissions(this, new String[] {
//
//                    Manifest.permission.CAMERA,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE
//
//            }, 0);
//        }
    }

    public void btnNext(View view) {

        addUserDetails();

        Intent next = new Intent(CreateProfile.this, AddBook.class);
        startActivity(next);
    }

    private void addUserDetails(){

        String address1 = address.getText().toString().trim();
        String city1 = city.getText().toString().trim();
        String province1 = province.getText().toString().trim();
        String postal = postalCode.getText().toString().trim();

        String id = db.push().getKey();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        {
            if (user != null) {

                UserDetails UD = new UserDetails(address1,city1,province1,postal);
                db.child(id).setValue(UD);

            }
        }

        //imageUpload();
    }

//    public String getExtension(Uri uri){
//        ContentResolver cr = getContentResolver();
//        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
//        return  mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
//    }

    public void imageUpload(Uri uri){

        imageUri = uri;
        StorageReference proImage = profileImageStorage.child("ProfileImage");
        proImage.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //Uri downloadUrl = taskSnapshot.getUploadSessionUri();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    public void profilebtn(View view) {

    }

    public void editbtn(View view) {

        Intent image = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(image,REQUEST_TAKE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
//            Uri imageUri = Uri.parse(currentPhotoPath);
//
            //File file = new File(imageUri.getPath());

            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                profileImage.setImageBitmap(bitmap);

                //Uri file = Uri.fromFile(new File(imageUri.getPath()));
                imageUpload(imageUri);


            } catch (IOException e) {
               // Log.i("TAG", "Some exception " + e);
            }


          //  profileImage.setImageURI(imageUri);


        }
    }



}
