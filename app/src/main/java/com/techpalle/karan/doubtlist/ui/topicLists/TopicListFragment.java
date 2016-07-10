package com.techpalle.karan.doubtlist.ui.topicLists;

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
import com.techpalle.karan.doubtlist.model.Topic;
import com.techpalle.karan.doubtlist.utils.Constants;
import com.techpalle.karan.doubtlist.utils.RecyclerItemClickListener;
import com.techpalle.karan.doubtlist.utils.Utils;

import java.util.Date;

/**
 * Created by ADMIN on 6/28/2016.
 */
public class TopicListFragment extends Fragment {

    Firebase mBaseRef;
    FirebaseRecyclerAdapter<Topic, MessageViewHolder> recyclerAdapter;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topic_list, null);
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

        mBaseRef = new Firebase(Constants.FIREBASE_URL);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_add_doubt);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                AddTopicDialog questionDialog = new AddTopicDialog();
                questionDialog.show(getFragmentManager(), "question_dialog");
            }
        });

        return view;
    }

    public void addDoubt(String question){
        String owner = "Anonymous";
        Topic topic = new Topic(question, "Temp", owner);
        mBaseRef.child(Constants.FIREBASE_LOCATION_TOPICS).push().setValue(topic);
    }

    @Override
    public void onStart() {
        super.onStart();

        recyclerAdapter = new FirebaseRecyclerAdapter<Topic, MessageViewHolder>(
                Topic.class,
                R.layout.row_topic,
                MessageViewHolder.class,
                mBaseRef.child(Constants.FIREBASE_LOCATION_TOPICS)
        ) {
            @Override
            protected void populateViewHolder(MessageViewHolder messageViewHolder, Topic topic, int i) {
                messageViewHolder.textViewDoubt.setText(topic.getTopicTitle());
                messageViewHolder.textViewUser.setText(topic.getOwner());
                if(topic.getTimestampLastChanged() != null){
                    messageViewHolder.textViewEditTime.setText(
                            Utils.SIMPLE_DATE_FORMAT.format(
                                    new Date(topic.getTimestampLastChangedLong())
                            ));
                }
            }
        };
        recyclerView.setAdapter(recyclerAdapter);
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder{

        View mView;
        TextView textViewDoubt, textViewUser, textViewEditTime;

        public MessageViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            textViewDoubt = (TextView) itemView.findViewById(R.id.text_view_topic);
            textViewUser = (TextView) itemView.findViewById(R.id.text_view_created_by_user);
            textViewEditTime = (TextView) itemView.findViewById(R.id.text_view_edit_time);
        }

    }


}
