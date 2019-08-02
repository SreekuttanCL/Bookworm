package com.example.bookworm.Activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

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

                BookListAdapter myArtistAdapter =
                        new BookListAdapter(Home.this,books);
                bookList.setAdapter(myArtistAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
