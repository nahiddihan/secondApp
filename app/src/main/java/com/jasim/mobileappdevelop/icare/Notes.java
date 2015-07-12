package com.jasim.mobileappdevelop.icare;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class Notes extends Activity {
    ImageView addNote;
    ListView notesListView;
    Integer profileId;
    DBHelper mydb;
    ArrayList<HashMap<String, String>> noteListDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note);
        addNote=(ImageView)findViewById(R.id.btAddNote);
        notesListView=(ListView)findViewById(R.id.noteList);
        Intent intent=getIntent();
        profileId=intent.getIntExtra("profile_id",0);
        noteListDetail=new ArrayList<HashMap<String, String>>();
        mydb = new DBHelper(this);
        noteListDetail = mydb.getAllNote(profileId);
        ListAdapter adapter = new SimpleAdapter(
                Notes.this, noteListDetail,
                R.layout.note_list_item, new String[] { "title","date","time"},
                new int[] { R.id.tvNoteTitle, R.id.tvNoteDate,R.id.tvNoteTime});

        notesListView.setAdapter(adapter);
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent noteIntent = new Intent(getApplicationContext(), NotePage.class);
                noteIntent.putExtra("profile_id",profileId);
                startActivity(noteIntent);
                finish();

            }
        });

        notesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Integer noteId=Integer.parseInt(noteListDetail.get(position).get("note_id"));
                Intent notePageIntent = new Intent(getApplicationContext(), NotePage.class);
                notePageIntent.putExtra("profile_id",profileId);
                notePageIntent.putExtra("note_id",noteId);
                notePageIntent.putExtra("update","true");
                startActivity(notePageIntent);
                finish();
            }
        });

        notesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Integer noteId=Integer.parseInt(noteListDetail.get(position).get("note_id"));
                String noteTitle=noteListDetail.get(position).get("title");

                final  AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Notes.this);
                alertDialogBuilder.setTitle(noteTitle);
                alertDialogBuilder
                        .setMessage("Do you want to delete this note ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                mydb.deleteNote(noteId);
                                finish();
                                startActivity(getIntent());
                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                return false;
            }
        });


    }
}
