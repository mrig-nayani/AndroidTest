package com.androidtest;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener {

    TextInputEditText fullNameTIET, ageTIET, emailTIET;
    Button addButton;
    Context context;
    AppData appData;
    RadioGroup radioSex;
    RadioButton radioMale, radioFemale, radioOther;
    List<String> storedArrayList;
    ArrayList<String> newArrayList;
    ListView listView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Home");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        fullNameTIET = (TextInputEditText) rootView.findViewById(R.id.fullNameTIET);
        ageTIET = (TextInputEditText) rootView.findViewById(R.id.ageTIET);
        emailTIET = (TextInputEditText) rootView.findViewById(R.id.emailTIET);
        addButton = (Button) rootView.findViewById(R.id.addButton);
        radioSex = (RadioGroup) rootView.findViewById(R.id.radioSex);
        radioMale = (RadioButton) rootView.findViewById(R.id.radioMale);
        radioFemale = (RadioButton) rootView.findViewById(R.id.radioFemale);
        radioOther = (RadioButton) rootView.findViewById(R.id.radioOther);
        listView = (ListView) rootView.findViewById(R.id.listView);
        context = getActivity();
        appData = new AppData(context);
        newArrayList = new ArrayList<String>();

        addButton.setOnClickListener(this);

        ((MainActivity)getActivity()).setFragmentRefreshListener(new MainActivity.FragmentRefreshListener() {
            @Override
            public void onRefresh() {
                fullNameTIET.setText("");
                ageTIET.setText("");
                emailTIET.setText("");
                List <String> myList = new ArrayList<>();
                ArrayAdapter<String> arrayAdapter =
                        new ArrayAdapter<String>(context,R.layout.activity_listview, R.id.patientDetailTV, myList);
                listView.setAdapter(arrayAdapter);
            }
        });


        return rootView;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.addButton:
                String strSex = "";
                System.out.println("fullname.gettext = " + fullNameTIET.getText().toString().isEmpty());
                if (fullNameTIET.getText().toString().isEmpty() || ageTIET.getText().toString().isEmpty() ||
                        emailTIET.getText().toString().isEmpty())
                {
                    Toast.makeText(context, "Enter the Data", Toast.LENGTH_SHORT).show();
                }
                else if (!isValidEmail(emailTIET.getText().toString())) {
                    Toast.makeText(context, "Your email is not valid", Toast.LENGTH_SHORT).show();
                }
                else {
                    List<String> myList1 = new ArrayList<String>(Arrays.asList(appData.getStoreArray().split(";")));
                    System.out.println("getStoreArray = " + appData.getStoreArray().trim().length());
                    System.out.println("getMaxNoOfPatientsAllowed = " + appData.getMaxNoOfPatientsAllowed());
                    if (myList1.size() < appData.getMaxNoOfPatientsAllowed()){
                        storedArrayList  = new ArrayList<>();
                        int checkedRadioId = radioSex.getCheckedRadioButtonId();

                        if(checkedRadioId== R.id.radioMale) {
                            strSex = "Male";
                        } else if(checkedRadioId== R.id.radioFemale ) {
                            strSex = "Female";
                        } else if(checkedRadioId== R.id.radioOther) {
                            strSex = "Other";
                        }
                        storedArrayList.add(fullNameTIET.getText().toString());
                        storedArrayList.add(strSex);
                        storedArrayList.add(ageTIET.getText().toString());
                        storedArrayList.add(emailTIET.getText().toString());
                        System.out.println("storedArrayList= " + storedArrayList);
                        String strStore = appData.getStoreArray();
                        System.out.println("appData.getStoreArray() = " + strStore);
                        System.out.println("appData.getStoreArray().trim().length() = " + appData.getStoreArray().length());
                        strStore = strStore.equals("") ? storedArrayList.toString() : strStore + ";" + storedArrayList;
                        System.out.println("ab + storedArrayList = " + strStore);

                        List<String> myList = new ArrayList<String>(Arrays.asList(strStore.split(";")));
                        System.out.println("myList = " + myList);
                        appData.setStoreArray("");
                        appData.setStoreArray(strStore);

                        ArrayAdapter<String> arrayAdapter =
                                new ArrayAdapter<String>(context,R.layout.activity_listview, R.id.patientDetailTV, myList);
                        listView.setAdapter(arrayAdapter);

                        Toast.makeText(context, "Patient Added!", Toast.LENGTH_SHORT).show();
                        fullNameTIET.setText("");
                        ageTIET.setText("");
                        emailTIET.setText("");
                    }
                    else{
                        Toast.makeText(context, "You can't add more patient, Maximum no. of Patients reached", Toast.LENGTH_SHORT).show();
                    }

                }
                break;
        }

    }
    public void adapterFunction(){

    }
    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
