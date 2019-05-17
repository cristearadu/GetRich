package com.examplee.radu.getrich.Login;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.examplee.radu.getrich.R;
import com.examplee.radu.getrich.User.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    EditText emailText, passwordText;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        emailText = (EditText) findViewById(R.id.email);
        passwordText = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        findViewById(R.id.signUp).setOnClickListener(this);
        findViewById(R.id.login).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signUp:

                registerUser();
                break;
            case R.id.login:
//            startActivity(new Intent(this,MainActivity.class));
                finish();
                break;
        }
    }

    private void registerUser() {

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

        if(password.length() < 6)
        {//TODO CHECK IF WORKS
            passwordText.setError("Password must be longer than 6 characters");
            passwordText.requestFocus();
            return;
        }
        //TODO IF EMAIL = ADMIN@..... THEN
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if(task.isSuccessful())
                        {
                            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            if(user != null)
                            {
                                final DatabaseReference mUserDB = FirebaseDatabase.getInstance().getReference().child("user").child(user.getUid());
                                mUserDB.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if(!dataSnapshot.exists()){
                                            User userName = new User();

                                            Map<String, Object> userMap = new HashMap<>();
                                            userMap.put("UID", user.getUid());
//                                    userMap.put("name",userName.getName());

                                            mUserDB.updateChildren(userMap);
                                            mUserDB.setValue(userName);

                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        Log.w("AddToDatabase", "Failed to read value.", databaseError.toException());
                                    }
                                });
                            }
                            Toast.makeText(getApplicationContext(),"Te-ai inregistrat cu succes",Toast.LENGTH_LONG).show();
                            finish();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"A aparut o problema la conexiune",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
