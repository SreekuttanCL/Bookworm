package com.example.bookworm.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bookworm.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText resetEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mAuth = FirebaseAuth.getInstance();

        resetEmail = findViewById(R.id.resetEmail);
    }

    public void submitBtn(View view) {

        String email = resetEmail.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this,"Enter a valid email",Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(this, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()) {
                        return;
                    }
                }
            });
        }

        Intent i = new Intent(ForgotPassword.this, MainActivity.class);
        startActivity(i);
    }
}
