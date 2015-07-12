package com.jasim.mobileappdevelop.icare;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class Doctor extends Activity {
    private ListView DoctorListView;
    DBHelper mydb;
    ArrayList<HashMap<String, String>> doctorListDetail;
    ArrayList<String> array_list;
    ImageView addDoctor;
    Integer profileId;
    Integer doctorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor);
        DoctorListView =(ListView)findViewById(R.id.doctorList);
        addDoctor=(ImageView)findViewById(R.id.ivAddDoctor);
        Intent intent=getIntent();
        profileId=intent.getIntExtra("profile_id",0);
        doctorListDetail=new ArrayList<HashMap<String, String>>();
        mydb = new DBHelper(this);
        doctorListDetail = mydb.getAllDoctor(profileId);
        array_list=new ArrayList<>();
        for(int i=0;i<doctorListDetail.size();i++){

            array_list.add(doctorListDetail.get(i).get("name"));
        }
        //  Toast.makeText(this,profileListArray.get(0).get("id").toString(),Toast.LENGTH_LONG).show();
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1, array_list);
        DoctorListView.setAdapter(arrayAdapter);
        DoctorListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),DoctorInfo.class);
                Integer doctorId=Integer.parseInt(doctorListDetail.get(position).get("id"));
                intent.putExtra("doctor_id",doctorId);
                startActivity(intent);

            }
        });

        DoctorListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Integer doctorId=Integer.parseInt(doctorListDetail.get(position).get("id"));
                String doctorName=doctorListDetail.get(position).get("name");

                final  AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Doctor.this);
                alertDialogBuilder.setTitle(doctorName);
                alertDialogBuilder
                        .setMessage("Do you want to delete all data of this  doctor ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                mydb.deleteDoctor(doctorId);
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


       addDoctor.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent addDoctorIntent=new Intent(getApplicationContext(),AddDoctor.class);
               addDoctorIntent.putExtra("profile_id",profileId);
               startActivity(addDoctorIntent);
               finish();

           }
       });

    }
}
