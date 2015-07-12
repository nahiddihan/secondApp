package com.jasim.mobileappdevelop.icare;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends ActionBarActivity {
    private ListView profileList;
    DBHelper mydb;
    ArrayList<HashMap<String, String>> profileListArray;
    ArrayList<String> array_list;
    Integer profileId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        profileList=(ListView)findViewById(R.id.list);
        profileListArray=new ArrayList<HashMap<String, String>>();
        mydb = new DBHelper(this);
        profileListArray = mydb.getAllProfile();
        array_list=new ArrayList<>();
        for(int i=0;i<profileListArray.size();i++){

            array_list.add(profileListArray.get(i).get("name"));
        }


    //  Toast.makeText(this,profileListArray.get(0).get("id").toString(),Toast.LENGTH_LONG).show();
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,R.layout.profile_list_item,R.id.tvProfileListItem, array_list);
        profileList.setAdapter(arrayAdapter);

        profileList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),HomeScreen.class);
                profileId=Integer.parseInt(profileListArray.get(position).get("id"));

                intent.putExtra("profile_id",profileId);
                startActivity(intent);

            }
        });
        profileList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                profileId=Integer.parseInt(profileListArray.get(position).get("id"));

                final  AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setTitle("profile name");
                alertDialogBuilder
                        .setMessage("Do you want to delete all data of this user ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                mydb.deleteProfile(profileId);
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


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add) {
            Intent createProfileIntent=new Intent(this,AddNewProfile.class);
            startActivity(createProfileIntent);
            finish();

        }

        return super.onOptionsItemSelected(item);
    }
}
