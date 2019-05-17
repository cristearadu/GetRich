package com.examplee.radu.getrich.User;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.examplee.radu.getrich.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class EditProfile extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth.AuthStateListener mAuthListener;

    EditText name, location, description;
    TextView level, value;
    Button update;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        name = (EditText) findViewById(R.id.name);
        level = (TextView) findViewById(R.id.level);
        value = (TextView) findViewById(R.id.value);
        location = (EditText) findViewById(R.id.location);
        description = (EditText) findViewById(R.id.description);
        update = (Button) findViewById(R.id.updateMoney);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        uid = user.getUid();


        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                showData(dataSnapshot);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUser();
            }
        });
    }

    private void updateUser() {
        final DatabaseReference mUserDB = FirebaseDatabase.getInstance().getReference().child("user").child(uid);
        String nameText = name.getText().toString();
        String locationText = location.getText().toString();
        String descriptionText = description.getText().toString();
        if(locationText.isEmpty())
        {
            location.setError("Name is requierd");
            location.requestFocus();
            return;
        }
        if(descriptionText.isEmpty())
        {
            description.setError("Name is requierd");
            description.requestFocus();
            return;
        }
        if(nameText.isEmpty())
        {
            name.setError("Name is requierd");
            name.requestFocus();
            return;
        }

        update.onEditorAction(EditorInfo.IME_ACTION_DONE);

        Map<String,Object> hopperUpdates = new HashMap<>();
        hopperUpdates.put("name",nameText);
        hopperUpdates.put("location",locationText);
        hopperUpdates.put("description",descriptionText);
        mUserDB.updateChildren(hopperUpdates);

        Toast.makeText(getApplicationContext(),"S-a facut un update",Toast.LENGTH_SHORT).show();
    }

    private void showData(DataSnapshot dataSnapshot) {
        UserInformation uInfo = new UserInformation();

        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            uInfo.setName(ds.child(uid).getValue(UserInformation.class).getName());
            uInfo.setLevel(ds.child(uid).getValue(UserInformation.class).getLevel());
            uInfo.setValue(ds.child(uid).getValue(UserInformation.class).getValue());
            uInfo.setLocation(ds.child(uid).getValue(UserInformation.class).getLocation());
            uInfo.setDescription(ds.child(uid).getValue(UserInformation.class).getDescription());


        }

        name.setText(uInfo.getName());
        level.setText(uInfo.getLevel());
        value.setText(Long.toString(uInfo.getValue()));
        location.setText(uInfo.getLocation());
        description.setText(uInfo.getDescription());

    }

}
