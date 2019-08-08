package com.example.bookworm.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bookworm.Models.BookDetails;
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

public class AddBook extends AppCompatActivity {

    private static final int REQUEST_TAKE_PHOTO = 1;

    EditText bookName, author, edition, price;
    ImageView bookImage,bookImageEdit;

    private DatabaseReference db;
    private StorageReference bookStorage;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        bookName = findViewById(R.id.booknameTxt);
        author = findViewById(R.id.authorTxt);
        edition = findViewById(R.id.editionTxt);
        price = findViewById(R.id.priceTxt);
        bookImage = findViewById(R.id.bookBackground);
        bookImageEdit = findViewById(R.id.bookImageEdit);

        db = FirebaseDatabase.getInstance().getReference("BookDetails");
        bookStorage = FirebaseStorage.getInstance().getReference("BookImages");

    }

    public void btnNext(View view) {

        Intent done = new Intent(AddBook.this, Home.class);
        startActivity(done);
    }

    public void addBookBtn(View view) {

        addBook();
    }

    private void addBook(){

        ImageView bImage = bookImage;
        String name = bookName.getText().toString().trim();
        String authorName = author.getText().toString().trim();
        String editionNo = edition.getText().toString().trim();
        String priceNo = price.getText().toString().trim();

        if (TextUtils.isEmpty(name)){
            Toast.makeText(this,"Please enter Name", Toast.LENGTH_SHORT).show();
            return;
        }

       else if (TextUtils.isEmpty(authorName)){
            Toast.makeText(this,"Please enter Author Name", Toast.LENGTH_SHORT).show();
            return;
        }

       else if (TextUtils.isEmpty(editionNo)){
            Toast.makeText(this,"Please enter Book Edition", Toast.LENGTH_SHORT).show();
            return;
        }

        else if (TextUtils.isEmpty(priceNo)){
            Toast.makeText(this,"Please enter Price", Toast.LENGTH_SHORT).show();
            return;
        }

        else {
            String id = db.push().getKey();

            try {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                {
                    if (user != null) {

                        BookDetails BD = new BookDetails(name, authorName, editionNo, priceNo);
                        // db.child(id).push().child("BookDetails").setValue(BD);
                        db.child(id).setValue(BD);

                    }
                }
            } finally {

            }
        }


    }

    public void imageUpload(Uri uri){

        imageUri = uri;
        //File imageFile = File.createTempFile("BookImages","jpg");
        StorageReference bookImage = bookStorage.child("BookImage");
        bookImage.putFile(imageUri)
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

    public void bookImageEdit(View view) {

        Intent image = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(image,REQUEST_TAKE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
//            Uri imageUri = Uri.parse(currentPhotoPath);
//
//            File file = new File(imageUri.getPath());

            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                bookImage.setImageBitmap(bitmap);

                imageUpload(imageUri);
            } catch (IOException e) {
                // Log.i("TAG", "Some exception " + e);
            }


            //  profileImage.setImageURI(imageUri);


        }
    }
}
