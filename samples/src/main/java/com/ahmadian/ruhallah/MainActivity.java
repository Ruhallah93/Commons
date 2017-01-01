package com.ahmadian.ruhallah;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ahmadian.ruhallah.commons.widgets.SmartSpinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SmartSpinner spinner = (SmartSpinner) findViewById(R.id.spinner);
        List<SmartSpinner.StringWithTag> list = new ArrayList<>();
        list.add(new SmartSpinner.StringWithTag("A", "1"));
        list.add(new SmartSpinner.StringWithTag("B", "2"));
        list.add(new SmartSpinner.StringWithTag("C", "3"));
        list.add(new SmartSpinner.StringWithTag("SmartTextView", "5"));
        spinner.setEntries(list);
    }
}
