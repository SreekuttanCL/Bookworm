package com.example.bookworm.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookworm.Models.UserDetails;
import com.example.bookworm.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    private TextView username;
    private TextView address;
    private TextView email;
    private TextView city;
    private TextView province;
    private TextView postalCode;
    private ImageView profileBackground;

    private DatabaseReference db;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        address = findViewById(R.id.addressprofile);
        city = findViewById(R.id.cityprofile);
        province = findViewById(R.id.provinceProfile);
        postalCode = findViewById(R.id.postalCodeProfile);
        profileBackground = findViewById(R.id.profileBackground);

        db = FirebaseDatabase.getInstance().getReference("UserDetails");
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        retrieveDetails();
    }

    public void retrieveDetails(){

        if (currentUser != null){
            db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    UserDetails post = dataSnapshot.getValue(UserDetails.class);
                    System.out.println(post);


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });

           // String name = currentUser.getDisplayName();
            String email1 = currentUser.getEmail();
            String id = currentUser.getUid();
            //String address1 = String.valueOf(currentUser.getProviderId().);

            email.setText(email1);
            //address.setText(address1);
        }
    }

    public void profileeditbtn(View view) {
    }
}
