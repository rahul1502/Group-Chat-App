package com.example.rahul.cookingrecipes;

import android.app.ListActivity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dashboard extends ListActivity {
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    ArrayList<String> groupList = new ArrayList<String>();
    ArrayList<String> groupIdList = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    // food pal
    Button createGroup;
    // get uid
    String groupName;
    String groupId;


    // Database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //https://stackoverflow.com/questions/4540754/dynamically-add-elements-to-a-listview-android
        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                groupList);
        setListAdapter(adapter);

        //
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                groupName = groupList.get(i);
                groupId = groupIdList.get(i);

                Intent intent = new Intent(Dashboard.this, GroupScreen.class);
                intent.putExtra("GROUPNAME",groupName);
                intent.putExtra("GROUPID",groupId);
                startActivity(intent);

            }
        });

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference gRef = mDatabaseReference.child("Groups");
        gRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //https://stackoverflow.com/questions/45168071/android-firebase-retrieve-all-data-in-arraylist-and-randomly-select-string-fro
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    HashMap map =(HashMap) childSnapshot.getValue();
                    if(map!=null) {
                        groupList.add(map.get("Name").toString());
                        groupIdList.add(childSnapshot.getKey());
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        createGroup = (Button)findViewById(R.id.createGroup);

        createGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dashboard.this,CreateGroup.class));
            }
        });

    }
}
