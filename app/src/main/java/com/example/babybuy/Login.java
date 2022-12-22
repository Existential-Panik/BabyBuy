package com.example.babybuy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText etemail,etpassword;
    TextView forgotPassword, login, signUp;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth=FirebaseAuth.getInstance();
        init();
    }
    public void init() {
        signUp = findViewById(R.id.txtSignUp);
        login = findViewById(R.id.txtLogin);
        etemail = findViewById(R.id.edtEmail);
        etpassword = findViewById(R.id.EdtPassword);
        forgotPassword = findViewById(R.id.txtForgetPassword);

        forgotPassword.setOnClickListener(this::startPasswordRecoveryProcess);
        signUp.setOnClickListener(this::startSignUpActivity);
        login.setOnClickListener(this::login);

    }

    private void byPass(View view) {
        startActivity( new Intent(getApplicationContext(), MainActivity.class));
    }

    private void login(View view) {
        String email = etemail.getText().toString().trim();
        String password = etpassword.getText().toString().trim();
        if (email.isEmpty()) {
            Toast.makeText(Login.this, "Enter Email", Toast.LENGTH_SHORT).show();
            etemail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            Toast.makeText(Login.this, "Enter Password", Toast.LENGTH_SHORT).show();
            etpassword.requestFocus();
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void startPasswordRecoveryProcess(View view) {
        Intent intent = new Intent(getApplicationContext(), ForgetPassword.class);
        startActivity(intent);
    }

    private void startSignUpActivity(View view) {
        Intent intent = new Intent(getApplicationContext(), SignUp.class);
        startActivity(intent);
    }

}