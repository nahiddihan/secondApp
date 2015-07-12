package com.jasim.mobileappdevelop.icare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;


public class BasicInfo extends Activity {
    TextView name;
    TextView birthDate;
    TextView gender;
    TextView relation;
    TextView email;
    TextView mobile;
    Button call;
    Button sms;
    Button updateInfo;
    DBHelper dbHelper;
    Integer profileId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_info);

        name=(TextView)findViewById(R.id.tvName);
        birthDate=(TextView)findViewById(R.id.tvNBirthDate);
        gender=(TextView)findViewById(R.id.tvGender);
        relation=(TextView)findViewById(R.id.tvRelation);
        email=(TextView)findViewById(R.id.tvEmail);
        mobile=(TextView)findViewById(R.id.tvMobile);

        updateInfo=(Button)findViewById(R.id.btUpdate);

        Intent intent=getIntent();
        profileId=intent.getIntExtra("profile_id",0);
     //   Toast.makeText(getApplicationContext(),""+profileId,Toast.LENGTH_SHORT).show();

         dbHelper = new DBHelper(getApplicationContext());
        HashMap<String, String> map = new HashMap<String, String>();
        map= dbHelper.getAboutOneProfile(profileId);
        Log.v("map value",map.toString());
        name.setText(map.get("name"));
        birthDate.setText(map.get("birth_date"));
        gender.setText(map.get("gender"));
        relation.setText(map.get("relation"));
        email.setText(map.get("email"));
        mobile.setText(map.get("mobile"));

        updateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent basicInfoUpdateIntent = new Intent(getApplicationContext(), UpdateInfo.class);
                basicInfoUpdateIntent.putExtra("profile_id",profileId);
                startActivity(basicInfoUpdateIntent);
                finish();

            }
        });

    }
}
