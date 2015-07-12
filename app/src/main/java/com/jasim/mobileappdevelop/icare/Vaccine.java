package com.jasim.mobileappdevelop.icare;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.HashMap;


public class Vaccine extends Activity {
    EditText vaccineTitle;
    EditText vaccineReason;
    ImageView addVaccine;
    private ListView vaccineListView;
    DBHelper mydb;
    ArrayList<HashMap<String, String>> vaccineListDetails;
    ArrayList<String> array_list;
    Integer vaccineId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vaccine);
        Intent intent=getIntent();
        final Integer profileId=intent.getIntExtra("profile_id",0);
        addVaccine=(ImageView)findViewById(R.id.ibtAddVaccine);
        vaccineListView=(ListView)findViewById(R.id.vaccineListView);
        vaccineListDetails=new ArrayList<HashMap<String, String>>();
        mydb = new DBHelper(this);
        vaccineListDetails = mydb.getAllVaccine(profileId);
        array_list=new ArrayList<>();

        for(int i=0;i<vaccineListDetails.size();i++){
            array_list.add(vaccineListDetails.get(i).get("title"));
        }

        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1, array_list);
        vaccineListView.setAdapter(arrayAdapter);

        addVaccine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent addVaccineIntent=new Intent(getApplicationContext(),AddVaccine.class);
//                startActivity(addVaccineIntent);
//                finish();
                final Dialog dialog = new Dialog(Vaccine.this);
                dialog.setTitle("Add New Vaccine");
                dialog.setContentView(R.layout.add_vaccine_dialog);
                vaccineTitle = (EditText) dialog.findViewById(R.id.etVaccineName);
                vaccineReason = (EditText) dialog.findViewById(R.id.etVaccineDescription);
                Button saveButton=(Button)dialog.findViewById(R.id.btSaveVaccine);
                Button cancelButton=(Button)dialog.findViewById(R.id.btCancelVaccine);
                dialog.show();

                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String vaccineName=vaccineTitle.getText().toString();
                        String vaccineReasonString=vaccineReason.getText().toString();
                        String userId= profileId.toString();
                        DBHelper db=new DBHelper(getApplicationContext());
                        db.insertVaccine(vaccineName, vaccineReasonString, userId);
                        Intent backToVaccine=new Intent(getApplicationContext(),Vaccine.class);
                        backToVaccine.putExtra("profile_id",profileId);
                        startActivity(backToVaccine);
                        dialog.dismiss();
                        finish();

                    }
                });
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }
        });

        vaccineListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),Dose.class);
                Integer vaccineId=Integer.parseInt(vaccineListDetails.get(position).get("vaccine_id"));
                String vaccineTitle=vaccineListDetails.get(position).get("title");
                intent.putExtra("vaccine_id",vaccineId);
                intent.putExtra("vaccine_title",vaccineTitle);
                startActivity(intent);
            }
        });

        vaccineListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final  AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Vaccine.this);
                vaccineId=Integer.parseInt(vaccineListDetails.get(position).get("vaccine_id"));
                String vaccineTitle=vaccineListDetails.get(position).get("title");

                alertDialogBuilder.setTitle(vaccineTitle);
                alertDialogBuilder
                        .setMessage("Do you want to delete ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                mydb.deleteVaccine(vaccineId);
                                finish();
                                startActivity(getIntent());
                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {

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
