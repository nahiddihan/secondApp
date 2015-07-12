package com.jasim.mobileappdevelop.icare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class MedicalInfo extends Activity {

    TextView height;
    TextView weight;
    TextView bloodGroup;
    TextView diabetes;
    TextView sugarLevel;
    TextView hypertensiion;
    TextView hypertensionLevel;
    TextView allergies;
    Button update;
    DBHelper dbHelper;
    Integer profileId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medical_info);
        height=(TextView)findViewById(R.id.tvHeight);
        weight=(TextView)findViewById(R.id.tvWeight);
        bloodGroup=(TextView)findViewById(R.id.tvBloodGroup);

        diabetes=(TextView)findViewById(R.id.tvDiabetes);
        hypertensiion=(TextView)findViewById(R.id.tvHypertension);

        allergies=(TextView)findViewById(R.id.tvAllergies);
        update=(Button)findViewById(R.id.btUpdate);

        Intent intent=getIntent();
        profileId=intent.getIntExtra("profile_id",0);
        dbHelper = new DBHelper(getApplicationContext());
        HashMap<String, String> map = new HashMap<String, String>();
        map= dbHelper.getAboutOneProfile(profileId);
        height.setText(": "+map.get("height")+" inches");
        weight.setText(": "+map.get("weight")+" inches");
        bloodGroup.setText(": "+map.get("blood_group"));
        if(map.get("diabetes").equalsIgnoreCase("No")){
            diabetes.setText(": No");
        }else{
            diabetes.setText(": "+map.get("blood_suger")+" mmol/L");
        }
        if(map.get("high_pressure").equalsIgnoreCase("No")){
            hypertensiion.setText(": No");
        }else{
            hypertensiion.setText(": "+map.get("high_pressure_desc")+" mmHg");
        }
        if(map.get("allergies").equalsIgnoreCase("No")){
            allergies.setText(": No");
        }else{
            allergies.setText(": "+map.get("allergic_description"));
        }


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent basicInfoUpdateIntent = new Intent(getApplicationContext(), UpdateInfo.class);
                basicInfoUpdateIntent.putExtra("profile_id",profileId);
                startActivity(basicInfoUpdateIntent);
                finish();
            }
        });

      //  Toast.makeText(getApplicationContext(), "" + profileId, Toast.LENGTH_SHORT).show();

    }
}
