package com.example.rpnaren.expense;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class weekly extends Activity {
    public void expense(View view)
    {
        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);

    }
    public void income(View view)
    {
        Intent i=new Intent(this,Income.class);
        startActivity(i);

    }
    public void weekly(View view)
    {
        Intent i=new Intent(this,weekly.class);
        startActivity(i);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly);
        ListView l=(ListView) findViewById(R.id.list);
        ArrayList li=new ArrayList();
        SharedPreferences sp=this.getSharedPreferences("com.example.rpnaren.expense",MODE_PRIVATE);
        final int week=sp.getInt("week",1);
        for(int i=week;i>=1;i--)
            li.add("WEEK "+i);
        ArrayAdapter<String> a=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,li);

        l.setAdapter(a);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent i1=new Intent(getApplicationContext(),db.class);
                i1.putExtra("week",week-i);
                startActivity(i1);
            }
        });
    }
}
