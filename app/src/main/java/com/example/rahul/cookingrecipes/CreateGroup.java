package com.example.rahul.cookingrecipes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateGroup extends AppCompatActivity {

    EditText groupName;
    Button createGroup;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    int chatMax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        groupName = (EditText)findViewById(R.id.groupName);
        createGroup = (Button)findViewById(R.id.createGroup);

        createGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Groups").push();
                final FirebaseUser mUser = firebaseAuth.getCurrentUser();
                mDatabaseReference.child("Name").setValue(groupName.getText().toString());
                mDatabaseReference.child("GroupAdmin").setValue(mUser.getUid());
                mDatabaseReference.child("Members").setValue(mUser.getUid());


                Toast.makeText(CreateGroup.this, "Group Created",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(CreateGroup.this, Dashboard.class));

            }
        });

    }
}
