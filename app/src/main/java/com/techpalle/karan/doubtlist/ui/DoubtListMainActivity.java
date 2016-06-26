package com.techpalle.karan.doubtlist.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseListAdapter;
import com.techpalle.karan.doubtlist.R;
import com.techpalle.karan.doubtlist.utils.Constants;

import java.util.ArrayList;
import java.util.Map;

public class DoubtListMainActivity extends AppCompatActivity {

    Firebase mBaseRef, mQuestionsRef;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> arrayList;
    FirebaseListAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doubt_list_main);

        listView = (ListView) findViewById(R.id.listView);
        arrayList = new ArrayList<String>();

        /*arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                arrayList);*/

        //listView.setAdapter(arrayAdapter);

        mBaseRef = new Firebase(Constants.BASE_URL);
        mQuestionsRef = new Firebase(Constants.QUESTIONS_URL);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        //region Add value event listener
       /* mBaseRef.child(Constants.QUESTIONS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, String> map = dataSnapshot.getValue(Map.class);
                String hello= map.get("hello");
                Log.e("TEST_FB",hello);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });*/
        //endregion

        //region Add child event listener
        /*mBaseRef.child(Constants.QUESTIONS).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String s1=dataSnapshot.getValue(String.class);
                Log.d("TEST_FB","added "+s1);
                arrayList.add(s1);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d("TEST_FB","changed "+dataSnapshot.getValue(String.class));
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d("TEST_FB","removed "+dataSnapshot.getValue(String.class));
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });*/
        //endregion

        adapter = new FirebaseListAdapter<String>(this,
                String.class,
                android.R.layout.simple_list_item_1,
                mQuestionsRef) {
            @Override
            protected void populateView(View view, String s, int i) {
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                textView.setText(s);
            }
        };
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_doubt_list_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
