package com.examplee.radu.getrich.Login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.examplee.radu.getrich.MainMenuActivity;
import com.examplee.radu.getrich.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;

    EditText emailText, passwordText;
    ProgressBar progressBar;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        emailText = (EditText) findViewById(R.id.email);
        passwordText = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        findViewById(R.id.signUp).setOnClickListener(this);
        findViewById(R.id.login).setOnClickListener(this);

        intent = new Intent(this, MainMenuActivity.class);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.signUp:
                startActivity(new Intent(this,SignUp.class));
                break;
            case R.id.login:
                logUser();
                break;
        }
    }

    private void logUser() {
        String email = emailText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();

        if(email.isEmpty())
        {
            emailText.setError("Email is required");
            emailText.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailText.setError("Enter a valid email");
            emailText.requestFocus();
            return;
        }

        if(password.isEmpty())
        {
            passwordText.setError("Password is required");
            passwordText.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if(task.isSuccessful())
                        {
                            startActivity(intent);

                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Nu te-ai putut conecta",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
