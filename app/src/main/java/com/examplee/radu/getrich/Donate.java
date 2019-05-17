package com.examplee.radu.getrich;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.examplee.radu.getrich.User.UserInformation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Donate extends AppCompatActivity {
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    String uid;

    TextView level, value;
    EditText donate;
    Button update;
    UserInformation uInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        uInfo = new UserInformation();

        donate = (EditText) findViewById(R.id.donate);
        update = (Button) findViewById(R.id.updateMoney);
        level = (TextView) findViewById(R.id.level);
        value = (TextView) findViewById(R.id.value);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        //Read from database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateMoney();
            }
        });
    }

    private void showData(DataSnapshot dataSnapshot) {
//        UserInformation uInfo = new UserInformation();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            uInfo.setLevel(ds.child(uid).getValue(UserInformation.class).getLevel());
            uInfo.setValue(ds.child(uid).getValue(UserInformation.class).getValue());
        }
        value.setText(Long.toString(uInfo.getValue()) + "€");
        level.setText(uInfo.getLevel());
    }

    private void updateMoney() {
        final DatabaseReference mUserDB = FirebaseDatabase.getInstance().getReference().child("user").child(uid);

        String donateText = donate.getText().toString();

        if (donateText.isEmpty()) {
            donate.setError("You must donate more than 0 €");
            donate.requestFocus();
            return;
        }

        donate.onEditorAction(EditorInfo.IME_ACTION_DONE);

        uInfo.setValue(uInfo.getValue() + Long.parseLong(donateText));
        Map<String, Object> hopperUpdates = new HashMap<>();
        hopperUpdates.put("value", uInfo.getValue());

        if (uInfo.getValue() > 99) {
            if (uInfo.getValue() <= 290) {
                uInfo.setLevel("Saracut");
                hopperUpdates.put("level", uInfo.getLevel());
                mUserDB.updateChildren(hopperUpdates);
            } else if (uInfo.getValue() > 290 && uInfo.getValue() <= 390) {
                uInfo.setLevel("Baietas");
                hopperUpdates.put("level", uInfo.getLevel());
                mUserDB.updateChildren(hopperUpdates);
            } else if (uInfo.getValue() > 390 && uInfo.getValue() <= 490) {
                uInfo.setLevel("Decent");
                hopperUpdates.put("level", uInfo.getLevel());
                mUserDB.updateChildren(hopperUpdates);
            } else if (uInfo.getValue() > 490 && uInfo.getValue() <= 590) {
                uInfo.setLevel("Decent");
                hopperUpdates.put("level", uInfo.getLevel());
                mUserDB.updateChildren(hopperUpdates);
            } else if (uInfo.getValue() > 590 && uInfo.getValue() <= 890) {
                uInfo.setLevel("Smecher");
                hopperUpdates.put("level", uInfo.getLevel());
                mUserDB.updateChildren(hopperUpdates);
            } else if (uInfo.getValue() > 890 && uInfo.getValue() <= 1490) {
                uInfo.setLevel("BO$$");
                hopperUpdates.put("level", uInfo.getLevel());
                mUserDB.updateChildren(hopperUpdates);
            } else if (uInfo.getValue() > 1490 && uInfo.getValue() <= 2000) {
                uInfo.setLevel("Seful");
                hopperUpdates.put("level", uInfo.getLevel());
                mUserDB.updateChildren(hopperUpdates);
            } else if (uInfo.getValue() > 2000) {
                uInfo.setLevel("ZEU");
                hopperUpdates.put("level", uInfo.getLevel());
                mUserDB.updateChildren(hopperUpdates);
            }


        } else if (uInfo.getValue() <= 99) {
            mUserDB.updateChildren(hopperUpdates);
        }

        Toast.makeText(getApplicationContext(), "Felicitari! Ai donat: " + donateText + " €", Toast.LENGTH_LONG).show();
    }
}
