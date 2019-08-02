package com.example.bookworm.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bookworm.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    ImageView loginBackground;
    EditText email;
    EditText pswd;
    Button loginBtn;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginBackground = findViewById(R.id.loginBackground);
      //  loginBackground.setImageResource(R.drawable.background);

        email = findViewById(R.id.emailEdittext);
        pswd = findViewById(R.id.pswdEdittext);
        loginBtn = findViewById(R.id.loginBtn);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart(){
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
       // updateUI(currentUser);
    }

    private void signIn() {

        String username = email.getText().toString().trim();
        String password = pswd.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this,"Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    Intent login = new Intent(MainActivity.this, Home.class);
                    startActivity(login);
                }
                else {
                    Toast.makeText(MainActivity.this, "LogIn failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void loginBtn(View view) {

        signIn();
    }

    public void signUpBtn(View view) {

        Intent i = new Intent(MainActivity.this, SignUp.class);
        startActivity(i);
    }

    public void forgotPswdBtn(View view) {
        Intent intent = new Intent(MainActivity.this, ForgotPassword.class);
        startActivity(intent);
    }
}
