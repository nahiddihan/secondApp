package com.jasim.mobileappdevelop.icare;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;


public class DoctorInfo extends Activity {
    TextView name;
    TextView mobile;
    TextView email;
    TextView address;
    Button updateButton;
    DBHelper dbHelper;
    Integer doctorId;
    String DoctorMobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_info);
        name=(TextView)findViewById(R.id.tvDoctorName);
        mobile=(TextView)findViewById(R.id.tvDoctorMobile);
        email=(TextView)findViewById(R.id.tvDoctorEmail);
        address=(TextView)findViewById(R.id.tvDoctorAddress);
        updateButton=(Button)findViewById(R.id.btDoctorUpdate);
        Intent intent=getIntent();
        doctorId=intent.getIntExtra("doctor_id",0);
        dbHelper = new DBHelper(getApplicationContext());
        HashMap<String, String> map = new HashMap<String, String>();
        map= dbHelper.getAboutOneDoctor(doctorId);
        name.setText(map.get("name"));
        mobile.setText(map.get("mobile"));
        email.setText(map.get("email"));
        address.setText(map.get("address"));
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent updateDoctorIntent = new Intent(getApplicationContext(), UpdateDoctor.class);
                updateDoctorIntent.putExtra("doctor_id",doctorId);
                startActivity(updateDoctorIntent);
                finish();

            }
        });

    }

    public void doctorCall(View view){
        Uri call = Uri.parse("tel:" + mobile.getText().toString());
        Intent dcal = new Intent(Intent.ACTION_CALL, call);
        startActivity(dcal);


    }
}
