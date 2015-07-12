package com.jasim.mobileappdevelop.icare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

/**
 * Created by Mobile App Develop on 17-6-15.
 */
public class HomeScreen extends Activity {
    GridView mainGridView;
    String[] gridTitle={"Basic Info","Medical Info","Growth Status","Diet","vaccine","Disease","Appointment","Medical history","Doctor","Medical center","note","emergency"};
    Integer[] gridImage={R.drawable.profile,R.drawable.medicalinfo,R.drawable.growthstatus,R.drawable.diet,R.drawable.vaccine,R.drawable.disease,R.drawable.appointment,R.drawable.medicalhistory,R.drawable.doctor,R.drawable.hospital,R.drawable.note,R.drawable.emmergency};

    Integer profileId;

    // ArrayList<String> gridTitle;
  //  ArrayList<Integer> gridImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        mainGridView =(GridView)findViewById(R.id.gridView1);
        Intent intent=getIntent();
        profileId=intent.getIntExtra("profile_id",0);

        mainGridView.setAdapter(new CustomAdapter(getApplicationContext(), gridTitle, gridImage));
        mainGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:

                        Intent basicInfoIntent = new Intent(getApplicationContext(), BasicInfo.class);
                        basicInfoIntent.putExtra("profile_id",profileId);
                        startActivity(basicInfoIntent);
                        break;

                    case 1:
                        Intent medicalInfoIntent = new Intent(getApplicationContext(), MedicalInfo.class);
                        medicalInfoIntent.putExtra("profile_id",profileId);
                        startActivity(medicalInfoIntent);
                        break;

                    case 2:
                        Intent growthStatusIntent = new Intent(getApplicationContext(), GrowthStatus.class);
                        growthStatusIntent.putExtra("profile_id",profileId);
                        startActivity(growthStatusIntent);
                        break;

                    case 3:

                        Intent dietIntent = new Intent(getApplicationContext(), Diet.class);
                        dietIntent.putExtra("profile_id",profileId);
                        startActivity(dietIntent);
                        break;
                    case 4:
                        Intent vaccineIntent = new Intent(getApplicationContext(), Vaccine.class);
                        vaccineIntent.putExtra("profile_id",profileId);
                        startActivity(vaccineIntent);
                        break;

                    case 5:
                        break;
                    case 6:
                        Intent appointmentIntent = new Intent(getApplicationContext(), Appointment.class);
                        appointmentIntent.putExtra("profile_id",profileId);
                        startActivity(appointmentIntent);
                        break;

                    case 7:
                        break;

                    case 8:
                        Intent doctorIntent = new Intent(getApplicationContext(), Doctor.class);
                        doctorIntent.putExtra("profile_id",profileId);
                        startActivity(doctorIntent);
                        break;
                    case 9:
                        Intent medicalCenterIntent = new Intent(getApplicationContext(), MedicalCenter.class);
                        medicalCenterIntent.putExtra("profile_id",profileId);
                        startActivity(medicalCenterIntent);
                        break;

                    case 10:
                        Intent noteIntent = new Intent(getApplicationContext(), Notes.class);
                        noteIntent.putExtra("profile_id",profileId);
                        startActivity(noteIntent);

                        break;

                    case 11:
                        break;


                }
            }
        });

    }
}
