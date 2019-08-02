package com.example.bookworm.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bookworm.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {

    EditText email, pswd, confirmPswd;
    Button signUp;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = findViewById(R.id.signUpEmail);
        pswd = findViewById(R.id.signUpPswd);
        confirmPswd = findViewById(R.id.confirmPswd);
        signUp = findViewById(R.id.signupbtn);

        mAuth = FirebaseAuth.getInstance();
    }

    public void register() {

        String username = email.getText().toString().trim();
        String password = pswd.getText().toString().trim();
        String confirmPassword = confirmPswd.getText().toString().trim();

        if (TextUtils.isEmpty(username)){
            Toast.makeText(this,"Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(confirmPassword)){
            Toast.makeText(this,"Please confirm your password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (confirmPassword.equals(password)) {

            mAuth.createUserWithEmailAndPassword(username, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(SignUp.this,"Sucessfully registered", Toast.LENGTH_SHORT).show();
                                Intent profile = new Intent(SignUp.this, CreateProfile.class);
                                startActivity(profile);
                            }
                            else{
                                Toast.makeText(SignUp.this,"Registration Error..", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }
        else {
            Toast.makeText(this,"Confirm password is incorrect", Toast.LENGTH_SHORT).show();
            return;
        }


    }

    public void signUpBtn(View view) {

        register();
    }

    public void loginBtn(View view) {

        Intent i = new Intent(SignUp.this, MainActivity.class);
        startActivity(i);
    }

}
