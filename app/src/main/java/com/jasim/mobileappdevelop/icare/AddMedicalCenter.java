package com.jasim.mobileappdevelop.icare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by NAKODIKO on 22-Jun-15.
 */
public class AddMedicalCenter extends Activity {
    EditText name;
    EditText mobile;
    EditText email;
    EditText address;
    Button saveButton;
    Integer profileId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_medical_center);
        name=(EditText)findViewById(R.id.etMedicalCenterName);
        mobile=(EditText)findViewById(R.id.etMedicalCenterMobile);
        email=(EditText)findViewById(R.id.etMedicalCenterEmail);
        address=(EditText)findViewById(R.id.etMedicalCenterAddress);
        saveButton=(Button)findViewById(R.id.btNewMedicalCenterSave);
        Intent intent=getIntent();
         profileId=intent.getIntExtra("profile_id",0);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String medicalCenterName=name.getText().toString();
                String medicalCenterMobile=mobile.getText().toString();
                String medicalCenterEmail=email.getText().toString();
                String medicalCenterAddress=address.getText().toString();
                DBHelper db=new DBHelper(getApplicationContext());
                db.insertMedicalCenter(medicalCenterName,medicalCenterMobile,medicalCenterEmail,medicalCenterAddress,profileId);
                Intent backToMedicalCenterList=new Intent(getApplicationContext(),MedicalCenter.class);
                backToMedicalCenterList.putExtra("profile_id",profileId);
                startActivity(backToMedicalCenterList);
                finish();
            }
        });

    }
}
