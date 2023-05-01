package com.example.census.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.census.MainActivity;
import com.example.census.R;
import com.example.census.enums.Action;
import com.example.census.enums.Role;
import com.example.census.sqliteDatabase.MyDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class ViewInfoCitizenActivity extends AppCompatActivity {

    private Role   role;
    private Action action;
    private String username;

    private long citizenTIN;

    private MyDatabaseHelper myDB;

    private LinearLayout citizenInfoLayout;
    private LinearLayout fieldLayout;
    private TextView fieldHeader;
    private TextView fieldBody;

    private Button btnLogOut;
    private TextView txtHouseholdInfo;

    private List<String> citizenInfo = new ArrayList() {{
        add("TIN:"); add("Age:"); add("Number:"); add("Sex:"); add("Birth:");
        add("Owner rel:"); add("Citizenship:"); add("Location:"); add("Nation:"); add("Language:");
        add("Other language:"); add("Education:"); add("Live period:"); add("Other live period:"); add("Status:");
        add("Marriage year:"); add("Marriage sum:"); add("Job period:"); add("Job sphere:"); add("Job location:");
        add("Part time:"); add("Income sum type:");
    }};

    private List<String> censusFormInfo;

    private List<String> householdInfo = new ArrayList() {{
        add("Address:"); add("Type:"); add("Year:"); add("Floor:"); add("Material:");
        add("Landscape:"); add("Size:"); add("Wo size:"); add("Room:"); add("Owner:");
    }};

    private List<String> householdFormInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_info_citizen);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            role = (Role) extras.get("role");
            action = (Action) extras.get("action");
            username = (String) extras.get("username");

            System.out.println("CbBzxWws :: role : " + role);
            System.out.println("cAhzA7DS :: action : " + action);
            System.out.println("22p97FpC :: username : " + username);
        }

        myDB = new MyDatabaseHelper(ViewInfoCitizenActivity.this);

        citizenTIN = getCitizenTinWithUsername(username, citizenTIN);

        System.out.println("YBT2w0iY :: citizenTIN : " + citizenTIN);

        censusFormInfo = getCensusFormByCitizenTIN(citizenTIN);

        if (censusFormInfo.isEmpty()) {
            System.out.println("FgsAi58B :: Census Form Info with citizenTIN = " + citizenTIN + " not found in DB");
            toastShow("Census Form Info with citizenTIN = " + citizenTIN + " not found in DB");
            return;
        }

        for (int i = 0; i < citizenInfo.size(); i++) {
            System.out.println("00sU132a :: citizenInfo.get(i) = " + citizenInfo.get(i));
        }

        for (int i = 0; i < householdInfo.size(); i++) {
            System.out.println("zA8aUjJ0 :: householdInfo.get(i) = " + householdInfo.get(i));
        }

        // Get a reference to the parent layout
        LinearLayout parentLayout = findViewById(R.id.parentLayout);

        LinearLayout censusFormLayout = new LinearLayout(this);
        censusFormLayout.setOrientation(LinearLayout.VERTICAL);

        // Iterate through the citizenInfo and inflate the layout for each one
        for (int i = 0; i < citizenInfo.size(); i++) {
            // Inflate the layout
            View censusFormView = LayoutInflater.from(this).inflate(R.layout.activity_citizen_info_layout, null);

            citizenInfoLayout = censusFormView.findViewById(R.id.citizenInfoLayout);
            fieldLayout = censusFormView.findViewById(R.id.fieldLayout);
            fieldHeader = censusFormView.findViewById(R.id.fieldHeader);
            fieldBody = censusFormView.findViewById(R.id.fieldBody);

            System.out.println("9M06tJPH :: " + citizenInfo.get(i) + " : " + censusFormInfo.get(i));

            fieldHeader.setText(citizenInfo.get(i).toString().trim());
            fieldBody.setText(censusFormInfo.get(i).toString().trim());

            // Add the censusForm view to the parent layout
            censusFormLayout.addView(censusFormView);
        }

        parentLayout.addView(censusFormLayout);

        txtHouseholdInfo = findViewById(R.id.txtHouseholdInfo);
        parentLayout.removeView(txtHouseholdInfo);
        parentLayout.addView(txtHouseholdInfo);

        householdFormInfo = getHouseholdFormByCitizenTIN(citizenTIN);

        if (householdFormInfo.isEmpty()) {
            System.out.println("zh69ctVK :: Household Form Info with citizenTIN = " + citizenTIN + " not found in DB");
            toastShow("Household Form Info with citizenTIN = " + citizenTIN + " not found in DB");
            return;
        }

        LinearLayout householdFormLayout = new LinearLayout(this);
        householdFormLayout.setOrientation(LinearLayout.VERTICAL);

        // Iterate through the householdInfo and inflate the layout for each one
        for (int i = 0; i < householdInfo.size(); i++) {
            // Inflate the layout
            View householdFormView = LayoutInflater.from(this).inflate(R.layout.activity_citizen_info_layout, null);

            citizenInfoLayout = householdFormView.findViewById(R.id.citizenInfoLayout);
            fieldLayout = householdFormView.findViewById(R.id.fieldLayout);
            fieldHeader = householdFormView.findViewById(R.id.fieldHeader);
            fieldBody = householdFormView.findViewById(R.id.fieldBody);

            System.out.println("9GQS6vp3 :: " + householdInfo.get(i) + " : " + householdFormInfo.get(i));

            fieldHeader.setText(householdInfo.get(i).toString().trim());
            fieldBody.setText(householdFormInfo.get(i).toString().trim());

            // Add the householdForm view to the parent layout
            householdFormLayout.addView(householdFormView);
        }

        parentLayout.addView(householdFormLayout);

        btnLogOut = findViewById(R.id.btnLogOut);
        parentLayout.removeView(btnLogOut);
        parentLayout.addView(btnLogOut);

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOut(view);
            }
        });

    }

    private long getCitizenTinWithUsername(String username, long citizenTIN) {
        Cursor cursor = myDB.selectFromTable("select citizen_tin " +
                                                    "from citizen inner join citizen_login " +
                                                        "where citizen_login.username = '" + username + "' " +
                                                            "and citizen.username_id = citizen_login._id");
        if (cursor.getCount() == 0) {
            return citizenTIN;
        } else {
            while (cursor.moveToNext()) {
                citizenTIN = cursor.getLong(0);
            }
        }
        return citizenTIN;
    }

    private List<String> getHouseholdFormByCitizenTIN(long citizenTIN) {
        Cursor cursor = myDB.selectFromTable("select household_address, " +
                                                        "household_type, " +
                                                        "household_year, " +
                                                        "household_floor, " +
                                                        "household_material, " +
                                                        "household_landscape, " +
                                                        "household_size, " +
                                                        "household_wo_size, " +
                                                        "household_room, " +
                                                        "household_owner " +
                                                        "from household where citizen_tin = " + citizenTIN);
        List<String> householdFormInfoList = new ArrayList<>();
        if (cursor.getCount() == 0) {
            return householdFormInfoList;
        } else {
            while (cursor.moveToNext()) {
                householdFormInfoList.add(cursor.getString(0)); // address
                householdFormInfoList.add(String.valueOf(cursor.getInt(1))); // type
                householdFormInfoList.add(String.valueOf(cursor.getInt(2))); // year
                householdFormInfoList.add(String.valueOf(cursor.getInt(3))); // floor
                householdFormInfoList.add(cursor.getString(4)); // material
                householdFormInfoList.add(cursor.getString(5)); // landscape
                householdFormInfoList.add(String.valueOf(cursor.getInt(6))); // size
                householdFormInfoList.add(String.valueOf(cursor.getInt(7))); // wo_size
                householdFormInfoList.add(String.valueOf(cursor.getInt(8))); // room
                householdFormInfoList.add(cursor.getString(9)); // owner
            }
        }
        return householdFormInfoList;
    }

    private void logOut(View view) {
        Intent mainActivity = new Intent(ViewInfoCitizenActivity.this, MainActivity.class);
        startActivity(mainActivity);
    }

    private List<String> getCensusFormByCitizenTIN(long citizenTIN) {
        Cursor cursor = myDB.selectFromTable("select * from census_form where citizen_tin = " + citizenTIN);
        List<String> censusFormInfoList = new ArrayList<>();
        if (cursor.getCount() == 0) {
            return censusFormInfoList;
        } else {
            while (cursor.moveToNext()) {
                censusFormInfoList.add(String.valueOf(citizenTIN)); // tin
                censusFormInfoList.add(String.valueOf(cursor.getInt(2))); // age
                censusFormInfoList.add(String.valueOf(cursor.getLong(3))); // number
                censusFormInfoList.add(cursor.getString(4)); // sex
                censusFormInfoList.add(cursor.getString(5)); // birth
                censusFormInfoList.add(cursor.getString(6)); // owner_rel
                censusFormInfoList.add(cursor.getString(7)); // citizenship
                censusFormInfoList.add(cursor.getString(8)); // location
                censusFormInfoList.add(cursor.getString(9)); // nation
                censusFormInfoList.add(cursor.getString(10)); // language
                censusFormInfoList.add(cursor.getString(11)); // o_language
                censusFormInfoList.add(cursor.getString(12)); // education
                censusFormInfoList.add(String.valueOf(cursor.getInt(13))); // live_period
                censusFormInfoList.add(String.valueOf(cursor.getInt(14))); // o_live_period
                censusFormInfoList.add(cursor.getString(15)); // status
                censusFormInfoList.add(String.valueOf(cursor.getInt(16))); // marriage_year
                censusFormInfoList.add(String.valueOf(cursor.getInt(17))); // marriage_sum
                censusFormInfoList.add(String.valueOf(cursor.getInt(18))); // job_period
                censusFormInfoList.add(cursor.getString(19)); // job_sphere
                censusFormInfoList.add(cursor.getString(20)); // job_location
                censusFormInfoList.add(cursor.getString(21)); // part_time
                censusFormInfoList.add(cursor.getString(22)); // income_sum_type
            }
        }
        return censusFormInfoList;
    }

    private void toastShow(CharSequence text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
