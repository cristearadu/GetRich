package com.examplee.radu.getrich.Saraci;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.widget.Toast;

import com.examplee.radu.getrich.R;
import com.examplee.radu.getrich.User.Profile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VeziSaraci extends AppCompatActivity {

    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<Profile> list;
    SaraciAdapter adapter;
    String uid;
    long value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vezi_saraci);

        recyclerView = (RecyclerView) findViewById(R.id.myRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<Profile>();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();

        reference = FirebaseDatabase.getInstance().getReference().child("user");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean readyToShow = false;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.getKey().equals(uid)) {
                        Profile userLogged = ds.getValue(Profile.class);
                        if (userLogged.getValue() == 0) {
                            Toast.makeText(getApplicationContext(), "Doneaza bani ca sa vezi toti saracii!", Toast.LENGTH_LONG).show();
                            readyToShow = false;
                        } else if (userLogged.getValue() != 0) {
                            value = userLogged.getValue();
                            readyToShow = true;
                        }

                    }
                }

                if (readyToShow == true) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        if (!ds.getKey().equals(uid)) {
                            Profile p = ds.getValue(Profile.class);
                            if (value > p.getValue()) {
                                list.add(p);
                            }
                        }
                    }
                    adapter = new SaraciAdapter(VeziSaraci.this, list);
                    recyclerView.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(VeziSaraci.this, "Probleme la conexiune", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
