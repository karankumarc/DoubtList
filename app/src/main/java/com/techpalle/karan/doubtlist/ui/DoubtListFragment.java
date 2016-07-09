package com.techpalle.karan.doubtlist.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.techpalle.karan.doubtlist.R;
import com.techpalle.karan.doubtlist.model.Doubt;
import com.techpalle.karan.doubtlist.utils.Constants;
import com.techpalle.karan.doubtlist.utils.RecyclerItemClickListener;
import com.techpalle.karan.doubtlist.utils.Utils;

import java.util.Date;

/**
 * Created by ADMIN on 6/28/2016.
 */
public class DoubtListFragment extends Fragment {

    Firebase mBaseRef;
    FirebaseRecyclerAdapter<Doubt, MessageViewHolder> recyclerAdapter;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doubt_list, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setIcon(R.drawable.ic_question_black_24dp).setTitle("Delete question")
                        .setMessage("Are you sure you want to delete question?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i1) {
                                Firebase firebase = recyclerAdapter.getRef(position);
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
            public void onLongItemClick(View view, int position) {

            }
        }));

        mBaseRef = new Firebase(Constants.BASE_URL);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_add_doubt);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                AddQuestionDialog questionDialog = new AddQuestionDialog();
                questionDialog.show(getFragmentManager(), "question_dialog");
            }
        });

        return view;
    }


   /* @Override
    public void questionAdded(String question) {
    }*/

    public void addDoubt(String question){
        String owner = "Anonymous";
        Doubt doubt = new Doubt(question, "Temp", owner);
        mBaseRef.child(Constants.QUESTIONS).push().setValue(doubt);
    }

    @Override
    public void onStart() {
        super.onStart();

        recyclerAdapter = new FirebaseRecyclerAdapter<Doubt, MessageViewHolder>(
                Doubt.class,
                R.layout.row_doubt,
                MessageViewHolder.class,
                mBaseRef.child(Constants.QUESTIONS)
        ) {
            @Override
            protected void populateViewHolder(MessageViewHolder messageViewHolder, Doubt doubt, int i) {
                messageViewHolder.textViewDoubt.setText(doubt.getDoubtTitle());
                messageViewHolder.textViewUser.setText(doubt.getOwner());
                if(doubt.getTimestampLastChanged() != null){
                    messageViewHolder.textViewEditTime.setText(
                            Utils.SIMPLE_DATE_FORMAT.format(
                                    new Date(doubt.getTimestampLastChangedLong())
                            ));
                }
            }

            /*@Override
            protected void populateViewHolder(MessageViewHolder messageViewHolder, String s, int i) {
                //messageViewHolder.textView.setText(s);
                *//*messageViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setIcon(R.drawable.ic_question_black_24dp).setTitle("Delete question")
                                .setMessage("Are you sure you want to delete question?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i1) {
                                        Firebase firebase = recyclerAdapter.getRef(i1);
                                        firebase.removeValue();
                                    }
                                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        builder.create().show();
                    }
                });*//*
            }*/
        };

        recyclerView.setAdapter(recyclerAdapter);
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder{

        View mView;
        TextView textViewDoubt, textViewUser, textViewEditTime;

        public MessageViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            textViewDoubt = (TextView) itemView.findViewById(R.id.text_view_doubt);
            textViewUser = (TextView) itemView.findViewById(R.id.text_view_created_by_user);
            textViewEditTime = (TextView) itemView.findViewById(R.id.text_view_edit_time);
        }

    }


}
