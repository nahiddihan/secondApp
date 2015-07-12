package com.jasim.mobileappdevelop.icare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;


public class GrowthStatus extends Activity {
    TextView prevWeight;
    TextView presentWeight;
    TextView prevHeight;
    TextView presentHeight;
    TextView prevBloodSugar;
    TextView presentBloodSugar;
    TextView presentBloodPressure;
    TextView prevBloodPressure;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.growth_status);
        prevWeight=(TextView)findViewById(R.id.tvPrevWeight);
        presentWeight=(TextView)findViewById(R.id.tvPresentWeight);
        prevHeight=(TextView)findViewById(R.id.tvPrevHeight);
        presentHeight=(TextView)findViewById(R.id.tvPresentHeight);
        prevBloodSugar=(TextView)findViewById(R.id.tvPrevSugar);
        presentBloodSugar=(TextView)findViewById(R.id.tvPresentSugar);
        presentBloodPressure=(TextView)findViewById(R.id.tvPresentBloodPressure);
        prevBloodPressure=(TextView)findViewById(R.id.tvPrevBloodPressure);

        Intent intent=getIntent();
        Integer profileId=intent.getIntExtra("profile_id",0);


        dbHelper = new DBHelper(getApplicationContext());
        HashMap<String, String> map = new HashMap<String, String>();

        map= dbHelper.getAboutOneProfile(profileId);
        Toast.makeText(getApplicationContext(), "" + map.get("weight"), Toast.LENGTH_SHORT).show();
        prevWeight.setText(map.get("weight_prev")+" Kg");
        presentWeight.setText(map.get("weight")+" Kg");
        prevHeight.setText(map.get("height_prev")+" inches");
        presentHeight.setText(map.get("height")+" inches");
        prevBloodSugar.setText(map.get("suger_prev")+" mmol/L");
        presentBloodSugar.setText(map.get("blood_suger")+" mmol/L");
        presentBloodPressure.setText(map.get("high_pressure_desc")+" mmHg");
        prevBloodPressure.setText(map.get("high_pressure_prev")+" mmHg");

    }
}
