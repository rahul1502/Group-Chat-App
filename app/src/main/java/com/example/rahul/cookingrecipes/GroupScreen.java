package com.example.rahul.cookingrecipes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GroupScreen extends AppCompatActivity {

    String groupName;
    String groupId;
    Button groupChat;
    Button joinGroup;

    private DatabaseReference groupDatabaseReference;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_screen);


        Intent i = getIntent();
        groupName = i.getStringExtra("GROUPNAME");
        groupId = i.getStringExtra("GROUPID");

        setTitle("Group: " + groupName);
        groupChat = (Button)findViewById(R.id.groupChat);
        joinGroup = (Button)findViewById(R.id.joinGroup);

        groupChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GroupScreen.this,GroupChat.class);
                intent.putExtra("GROUPNAME",groupName);
                intent.putExtra("GROUPID",groupId);
                startActivity(intent);
            }
        });


        joinGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();

                final FirebaseUser mUser = firebaseAuth.getCurrentUser();
                groupDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Groups").child(groupId);
                groupDatabaseReference.child("Members").push().setValue(uid);

                Toast.makeText(GroupScreen.this, "You have joined this group !",Toast.LENGTH_SHORT).show();

            }
        });
    }
}
