package com.jasim.mobileappdevelop.icare;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class Dose extends Activity {

    ImageView addImage;
    private DatePickerDialog dueDatePicker;
    private SimpleDateFormat dateFormatter;
    Button dateButton;
    Integer vaccineId;
    EditText doseTitle;
    ListView doseListView;
    DBHelper mydb;
    TextView vaccineTitle;
    ArrayList<HashMap<String, String>> doseList;
    String vaccineTitleText;
    Integer doseId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dose_list);
        doseListView=(ListView)findViewById(R.id.lvDoseLst);
        vaccineTitle=(TextView)findViewById(R.id.tvDoseListVaccineTitle);
        Intent intent=getIntent();
        vaccineTitleText=intent.getStringExtra("vaccine_title");
        vaccineId=intent.getIntExtra("vaccine_id",0);
        vaccineTitle.setText(vaccineTitleText+"dose List");
        doseList=new ArrayList<HashMap<String, String>>();
        mydb = new DBHelper(this);
        doseList=mydb.getAllVaccineDose(vaccineId);
        ListAdapter adapter = new SimpleAdapter(
                Dose.this, doseList,
                R.layout.dose_list_item, new String[] { "dose_title","date"},
                new int[] { R.id.tvVaccineDose, R.id.tvDueDate,});

        doseListView.setAdapter(adapter);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        addImage=(ImageView)findViewById(R.id.ivAdd);
        setDueDate();

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(Dose.this);
                dialog.setTitle(vaccineTitleText);
                dialog.setContentView(R.layout.dose_dialog);

               doseTitle = (EditText) dialog.findViewById(R.id.etDoseTitle);
                 dateButton=(Button)dialog.findViewById(R.id.btDoseDate);
                Button saveButton=(Button)dialog.findViewById(R.id.btnDoseDialogSave);
                Button cancelButton=(Button)dialog.findViewById(R.id.btnDoseDialogClose);
                dialog.show();

                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String doseTitleString=doseTitle.getText().toString();
                        String doseDueDate=dateButton.getText().toString();
                        String vaccineIdString= vaccineId.toString();
                        DBHelper db=new DBHelper(getApplicationContext());
                        db.insertDose(doseTitleString, doseDueDate, vaccineIdString);
                        Intent backToDose=new Intent(getApplicationContext(),Dose.class);
                        backToDose.putExtra("vaccine_id",vaccineId);
                        backToDose.putExtra("vaccine_title",vaccineTitleText);
                        startActivity(backToDose);
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


                dateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dueDatePicker.show();
                    }
                });


            }
        });
doseListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        final  AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Dose.this);
         doseId=Integer.parseInt(doseList.get(position).get("dose_id"));
        String doseTitle=doseList.get(position).get("dose_title");

        alertDialogBuilder.setTitle(doseTitle);
        alertDialogBuilder
                .setMessage("Do you want to delete ?")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        mydb.deleteDose(doseId);
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

    public void setDueDate(){

        Calendar newCalendar = Calendar.getInstance();

        dueDatePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateButton.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }
}
