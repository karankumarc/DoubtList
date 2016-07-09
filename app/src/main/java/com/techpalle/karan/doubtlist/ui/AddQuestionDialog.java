package com.techpalle.karan.doubtlist.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.techpalle.karan.doubtlist.R;

/**
 * Created by ADMIN on 6/26/2016.
 */
public class AddQuestionDialog extends AppCompatDialogFragment {

    LayoutInflater inflater;
    View view;
    QuestionAddedHandler handler;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        inflater = getActivity().getLayoutInflater();

        view = inflater.inflate(R.layout.dialog_add_topic, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(view).setIcon(R.drawable.ic_question_black_24dp).setPositiveButton("Ask", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Toast.makeText(getActivity(), "Ask", Toast.LENGTH_SHORT).show();
                EditText editTextQuestion = (EditText) view.findViewById(R.id.editTextAddQuestion);
                handler.questionAdded(editTextQuestion.getText().toString());
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getActivity(), "Cancel", Toast.LENGTH_SHORT).show();
            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        handler = (QuestionAddedHandler) context;
    }

    public interface QuestionAddedHandler{
        public void questionAdded(String question);
    }
}
