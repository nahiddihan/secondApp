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
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;

public class MedicalCenter extends Activity {
    private ListView medicalCenterListView;
    DBHelper mydb;
    ArrayList<HashMap<String, String>> medicalCenterList;
    ArrayList<String> array_list;
    ImageView addMedicalCenter;
    Integer profileId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medical_center);
        Intent intent=getIntent();
        profileId=intent.getIntExtra("profile_id",0);
        Toast.makeText(getApplicationContext(),""+profileId,Toast.LENGTH_SHORT).show();
        medicalCenterListView =(ListView)findViewById(R.id.healthCareList);
        addMedicalCenter=(ImageView)findViewById(R.id.ivAddMedicalCenter);
        medicalCenterList =new ArrayList<HashMap<String, String>>();
        mydb = new DBHelper(this);
        medicalCenterList = mydb.getAllMedicalCenter(profileId);
        array_list=new ArrayList<>();
        for(int i=0;i<medicalCenterList.size();i++){

            array_list.add(medicalCenterList.get(i).get("name"));

        }
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1, array_list);
        medicalCenterListView.setAdapter(arrayAdapter);

        medicalCenterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),MedicalCenterInfo.class);
                Integer centerId=Integer.parseInt(medicalCenterList.get(position).get("id"));
                intent.putExtra("center_id",centerId);
                startActivity(intent);

            }
        });

        medicalCenterListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Integer centerId=Integer.parseInt(medicalCenterList.get(position).get("id"));
                String healthCareName=medicalCenterList.get(position).get("name");

                final  AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MedicalCenter.this);
                alertDialogBuilder.setTitle(healthCareName);
                alertDialogBuilder
                        .setMessage("Do you want to delete all this Health Care Center ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                mydb.deleteHealthCare(centerId);
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

        addMedicalCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addMedicalCEnterIntent=new Intent(getApplicationContext(),AddMedicalCenter.class);
                addMedicalCEnterIntent.putExtra("profile_id",profileId);
                startActivity(addMedicalCEnterIntent);
                finish();
            }
        });

    }
}
