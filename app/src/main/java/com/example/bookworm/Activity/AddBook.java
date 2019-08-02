package com.example.bookworm.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.bookworm.Models.BookDetails;
import com.example.bookworm.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

public class AddBook extends AppCompatActivity {

    private static final int REQUEST_TAKE_PHOTO = 1;

    EditText bookName, author, edition, price;
    ImageView bookImage,bookImageEdit;

    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        bookName = findViewById(R.id.booknameTxt);
        author = findViewById(R.id.authorTxt);
        edition = findViewById(R.id.editionTxt);
        price = findViewById(R.id.priceTxt);
        bookImage = findViewById(R.id.bookImage);
        bookImageEdit = findViewById(R.id.bookImageEdit);

        db = FirebaseDatabase.getInstance().getReference("BookDetails");

    }

    public void btnNext(View view) {

        Intent done = new Intent(AddBook.this, Home.class);
        startActivity(done);
    }

    public void addBookBtn(View view) {

        addBook();
    }

    private void addBook(){

        String name = bookName.getText().toString().trim();
        String authorName = author.getText().toString().trim();
        String editionNo = edition.getText().toString().trim();
        String priceNo = price.getText().toString().trim();

       String id = db.push().getKey();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        {
            if (user != null) {

                BookDetails BD = new BookDetails(name,authorName,editionNo,priceNo);
               // db.child(id).push().child("BookDetails").setValue(BD);
                db.child(id).setValue(BD);

            }
        }
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
            } catch (IOException e) {
                // Log.i("TAG", "Some exception " + e);
            }


            //  profileImage.setImageURI(imageUri);


        }
    }
}