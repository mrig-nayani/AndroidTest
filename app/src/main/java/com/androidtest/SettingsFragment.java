package com.androidtest;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class SettingsFragment extends Fragment implements View.OnClickListener {

    TextInputEditText currentUsernameTIED, currentNoOfAddedPatientsTIED, maxNumberOfPatientsTIED;
    Button saveButton;
    AppData appData;
    Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Settings");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.settings, container, false);

        currentUsernameTIED = (TextInputEditText) rootView.findViewById(R.id.currentUsernameTIED);
        currentNoOfAddedPatientsTIED = (TextInputEditText) rootView.findViewById(R.id.currentNoOfAddedPatientsTIED);
        maxNumberOfPatientsTIED = (TextInputEditText) rootView.findViewById(R.id.maxNumberOfPatientsTIED);
        context = getActivity();
        appData = new AppData(context);
        saveButton = (Button) rootView.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(this);
        System.out.println("appData.getMaxNoOfPatientsAllowed() = " + appData.getMaxNoOfPatientsAllowed());
        if (appData.getMaxNoOfPatientsAllowed() == 0){
            appData.setMaxNoOfPatientsAllowed(5);
            System.out.println("appData.getMaxNoOfPatientsAllowed() = " + appData.getMaxNoOfPatientsAllowed());
        }
        currentUsernameTIED.setText(appData.getCurrentUserName());
        maxNumberOfPatientsTIED.setText(String.valueOf(appData.getMaxNoOfPatientsAllowed()));
        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.saveButton:
                appData.setCurrentUserName(currentUsernameTIED.getText().toString());
                appData.setCurrentNoOfAddedPatients(Integer.parseInt(currentNoOfAddedPatientsTIED.getText().toString()));
                appData.setMaxNoOfPatientsAllowed(Integer.parseInt(maxNumberOfPatientsTIED.getText().toString()));

                Toast.makeText(context, "Hi again, " + currentUsernameTIED.getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
