package com.jasim.mobileappdevelop.icare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

public class NotePage extends Activity {
    TextView save;
    TextView cancel;
    EditText noteTitle;
    EditText noteDetails;
    Integer profileId;
    DBHelper dbhelper;
    String updateStatus;
    Integer noteId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_page);
        updateStatus=" ";
        save=(TextView)findViewById(R.id.tvSave);
        cancel=(TextView)findViewById(R.id.tvCancel);
        noteTitle=(EditText)findViewById(R.id.etNoteTitle);
        noteDetails=(EditText)findViewById(R.id.etNoteDetail);
        Intent intent=getIntent();
        profileId=intent.getIntExtra("profile_id",0);
        updateStatus=intent.getStringExtra("update");
        noteId=intent.getIntExtra("note_id",0);


      //  Toast.makeText(getApplicationContext(),""+noteId,Toast.LENGTH_LONG).show();
        dbhelper = new DBHelper(this);
        HashMap<String, String> map = new HashMap<String, String>();
        map=dbhelper.getAboutOneNote(noteId);

     //   Toast.makeText(getApplicationContext(),""+profileId,Toast.LENGTH_SHORT).show();
      //  if(updateStatus.equalsIgnoreCase("true")){
      //      noteTitle.setText(map.get("title"));
            noteDetails.setText(map.get("description"));
      //  }


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title=noteTitle.getText().toString();
                String description=noteDetails.getText().toString();
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String noteDate = df.format(c.getTime());
                SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
                timeFormat.setTimeZone(TimeZone.getTimeZone("GMT+6:00"));
                String noteTime= timeFormat.format(c.getTime());

            //    if (updateStatus.equalsIgnoreCase("true")){
             //       dbhelper.updateNote(title,description,noteDate,noteTime,noteId);
           //     }else{
                    dbhelper.insertNote(title,description,noteDate,noteTime,profileId);

             //   }
                Intent backToNotes=new Intent(getApplicationContext(),Notes.class);
                backToNotes.putExtra("profile_id",profileId);
                startActivity(backToNotes);
                finish();

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
