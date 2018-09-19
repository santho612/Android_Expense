package com.example.rpnaren.expense;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class db extends Activity {
    SQLiteDatabase db;
    int res=0;
    TextView t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);
        Intent i=getIntent();
        int week=i.getIntExtra("week",1);
        t=(TextView) findViewById(R.id.t);
        t.append("Week "+Integer.toString(week)+"\n\n\n");
        t.append("\n");
        try {
            db = this.openOrCreateDatabase("db", MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS expense(name VARCHAR,cost INT(5),ie INT(1),week INT(3))");
            Cursor c=db.rawQuery("select * from expense where week="+week,null);
            int n1=c.getColumnIndex("name");
            int n2=c.getColumnIndex("cost");
            int n3=c.getColumnIndex("ie");
            c.moveToFirst();
            while(!c.isAfterLast())
            {
                String name=c.getString(n1);
                int cost=c.getInt(n2);
                int ie=c.getInt(n3);
                if(ie==0) {
                    t.append(name + " =\u0009+" + Integer.toString(cost));
                    res=res+cost;
                }
                else
                {
                    t.append(name + " =\u0009-" + Integer.toString(cost));
                    res=res-cost;
                }

                t.append("\n");
                c.moveToNext();
            }
            t.append("\n\n\u0009"+Integer.toString(res));
        }
        catch(Exception e)
        {e.printStackTrace();}
    }
}
