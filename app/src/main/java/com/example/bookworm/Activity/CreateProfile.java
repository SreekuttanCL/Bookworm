package com.example.bookworm.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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

//
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
    }

    public void profilebtn(View view) {



    }

    public void editbtn(View view) {
//        try {
//            dispatchTakePictureIntent();
//        } catch (IOException exception) {
//
//        }
//        Intent e = new Intent (CreateProfile.this, MainActivity.class);
//        startActivity(e);

        Intent image = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(image,REQUEST_TAKE_PHOTO);
    }

//    public void browseLibraryButtonClick(View view) {
//    }
//
//    private void dispatchTakePictureIntent() throws IOException {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            File photoFile = createImageFile();
//
//            if (photoFile != null) {
//                Uri photoURI = FileProvider.getUriForFile(this,
//                        BuildConfig.APPLICATION_ID + ".provider", photoFile);
//
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                startActivityForResult(takePictureIntent,REQUEST_TAKE_PHOTO);
//            }
//        }
//    }
//
//    private File createImageFile() throws IOException {
//        String timestamp = new SimpleDateFormat("yyyyMMdd HHmmss").format(new Date());
//
//        File storageDir = new File(Environment.
//                getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Camera");
//
//        if (!storageDir.exists()) {
//            storageDir.mkdirs();
//        }
//
//        File image = File.createTempFile(timestamp, ".jpg", storageDir);
//
//        currentPhotoPath = "file:" + image.getAbsolutePath();
//
//        return image;
//    }

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

                Uri file = Uri.fromFile(new File(imageUri.getPath()));
                StorageReference proImage = profileImageStorage.child("ProfileImage");

                proImage.putFile(file)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
            } catch (IOException e) {
               // Log.i("TAG", "Some exception " + e);
            }


          //  profileImage.setImageURI(imageUri);


        }
    }



}
