package com.google.android.stardroid.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.stardroid.R;
import com.google.android.stardroid.data.Transient;
import com.google.android.stardroid.data.TransientData;

import java.util.ArrayList;

public class CaughtTransientList extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caught_transient_list);

        ListView list = (ListView) findViewById(R.id.clistT);

        ArrayList<String> titles = new ArrayList<>();
        for(Transient t : TransientData.INSTANCE.getCaught()){
                titles.add(t.gettId());
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, titles);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                int p = position;

                Intent detailIntent = new Intent(CaughtTransientList.this, DetailTransientActivity.class);

                detailIntent.putExtra("location", p);

                startActivity(detailIntent);
            }
        });


    }
}
