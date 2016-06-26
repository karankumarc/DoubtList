package com.techpalle.karan.doubtlist.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseListAdapter;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.techpalle.karan.doubtlist.R;
import com.techpalle.karan.doubtlist.utils.Constants;

import java.util.ArrayList;

public class DoubtListMainActivity extends AppCompatActivity implements AddQuestionDialog.QuestionAddedHandler, AdapterView.OnItemClickListener{

    Firebase mBaseRef, mQuestionsRef;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> arrayList;
    FirebaseListAdapter<String> adapter;
    FirebaseRecyclerAdapter<String, MessageViewHolder> recyclerAdapter;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doubt_list_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);



        /*listView = (ListView) findViewById(R.id.listView);
        arrayList = new ArrayList<String>();

        listView.setOnItemClickListener(this);*/

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
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                AddQuestionDialog questionDialog = new AddQuestionDialog();
                questionDialog.show(getSupportFragmentManager(), "question_dialog");
            }
        });
    }

    @Override
    public void questionAdded(String question) {
        mQuestionsRef.push().setValue(question);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_question_black_24dp).setTitle("Delete question")
                .setMessage("Are you sure you want to delete question?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i1) {
                        Firebase firebase = recyclerAdapter.getRef(i);
                        firebase.removeValue();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
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

        //region FirebaseArrayAdapter
        /*adapter = new FirebaseListAdapter<String>(this,
                String.class,
                android.R.layout.simple_list_item_1,
                mQuestionsRef) {
            @Override
            protected void populateView(View view, String s, int i) {
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                textView.setText(s);
            }
        };
        listView.setAdapter(adapter);*/
        //endregion

        //region FirebaseRecyclerAdapter
        recyclerAdapter = new FirebaseRecyclerAdapter<String, MessageViewHolder>(
                String.class,
                android.R.layout.two_line_list_item,
                MessageViewHolder.class,
                mQuestionsRef
        ) {
            @Override
            protected void populateViewHolder(MessageViewHolder messageViewHolder, String s, final int i) {
                messageViewHolder.textView.setText(s);
                messageViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(DoubtListMainActivity.this);
                        builder.setIcon(R.drawable.ic_question_black_24dp).setTitle("Delete question")
                                .setMessage("Are you sure you want to delete question?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i1) {
                                        Firebase firebase = recyclerAdapter.getRef(i);
                                        firebase.removeValue();
                                    }
                                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        builder.create().show();
                    }
                });
            }
        };

        recyclerView.setAdapter(recyclerAdapter);
        //endregion

    }

    public void onItemDeleted(int pos){
        Firebase firebase = recyclerAdapter.getRef(pos);
        firebase.removeValue();
    }


    public static class MessageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        View mView;
        TextView textView;

        public MessageViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            textView = (TextView) itemView.findViewById(android.R.id.text1);
        }

        @Override
        public void onClick(View view) {
           /* deleteQuestionAt(getAdapterPosition());*/
            //DoubtListMainActivity.this.onItemDeleted(getAdapterPosition());
        }
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
