package com.example.bookworm.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.PopupMenu;

import com.example.bookworm.Adapters.BookListAdapter;
import com.example.bookworm.Models.BookDetails;
import com.example.bookworm.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    ListView bookList;

    List<BookDetails> books;

    DatabaseReference db;
    FirebaseAuth dbAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bookList = findViewById(R.id.bookList);
        dbAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseDatabase.getInstance().getReference("BookDetails");

        books = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                books.clear();

                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    BookDetails book = postSnapshot.getValue(BookDetails.class);
                    books.add(book);
                }
//
//                for(int i= 0; i <= books.size(); i++){
//                    Log.d("sjghdfbjs", "size of the  list: "+ books.get(i));
//
//                }

                BookListAdapter myBookAdapter =
                        new BookListAdapter(Home.this,books);
                bookList.setAdapter(myBookAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void menuBtn(View v) {

        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.main_menu, popup.getMenu());
        popup.show();
    }


    public void signOuut(MenuItem item) {

        showAlertDialogButtonClicked();



    }

    public void profileSettingBtn(MenuItem item) {
        Intent profile = new Intent(Home.this, Profile.class);
        startActivity(profile);
    }

    public void showAlertDialogButtonClicked() {
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert");
        builder.setMessage("Are you sure you want to Sign Out?");
        // add the buttons


        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // do something like...
                dbAuth.signOut();
                finish();

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
                //startActivity(new Intent(getApplicationContext(), Home.class));
            }
        });
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();


    }
}
