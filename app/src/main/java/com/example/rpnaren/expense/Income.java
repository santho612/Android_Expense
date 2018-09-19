package com.example.rpnaren.expense;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Income extends Activity {
    SharedPreferences sp;
    SharedPreferences.Editor e;
    int saving,wsaving,ssaving;
    EditText amount,source,sourcemoney;
    TextView save;
    int temp;
    int week;
    SQLiteDatabase db;
    String tem;
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
    public void addmoneyfromsavings(View view)
    {temp=0;
         temp=Integer.parseInt(amount.getText().toString());

        if(saving<temp)
        {
            new AlertDialog.Builder(this).setTitle("Error").setMessage("Savings amount is less").setPositiveButton("OK",null).show();
            return;
        }

        new AlertDialog.Builder(this).setTitle("Confirm transaction").setPositiveButton("Confirm?", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialogInterface, int i) {
               e.putInt("saving",saving-temp);
               e.putInt("wsaving",wsaving+temp);
               e.putInt("ssaving",ssaving+temp);
               e.commit();
               saving=sp.getInt("saving",0);
               wsaving=sp.getInt("wsaving",0);
               ssaving=sp.getInt("ssaving",0);
               save.setText(Integer.toString(saving));
               amount.setText("");
               try{
                   db.execSQL("INSERT INTO expense VALUES('SAVINGS',"+temp+",0,"+week+")");
               }
               catch(Exception e)
               {e.printStackTrace();}
           }
       }).setNegativeButton("Cancel",null).show();

    }

    public void addmoneyfromothersource(View view)
    { temp=0;
        temp=Integer.parseInt(sourcemoney.getText().toString());
       tem=source.getText().toString();
        new AlertDialog.Builder(this).setTitle("Confirm transaction").setPositiveButton("Confirm?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                e.putInt("wsaving",wsaving+temp);
                e.putInt("ssaving",ssaving+temp);
                e.commit();
                wsaving=sp.getInt("wsaving",0);
                ssaving=sp.getInt("ssaving",0);
                source.setText("");
                sourcemoney.setText("");
                try{
                    db.execSQL("INSERT INTO expense VALUES('"+tem+"',"+temp+",0,"+week+")");
                }
                catch(Exception e)
                {e.printStackTrace();}
            }
        }).setNegativeButton("Cancel",null).show();

    }

    public void closethisweek(View view)
    {
       new AlertDialog.Builder(this).setTitle("CLOSE").setMessage("Do you want to close this week's table and start a new one?")
               .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       e.putInt("week",week+1);
                       e.commit();
                       week=sp.getInt("week",1);
                       e.putInt("saving",saving+wsaving);
                       e.putInt("wsaving",0);
                       e.putInt("ssaving",0);
                       Intent in=new Intent(getApplicationContext(),db.class);
                       in.putExtra("week",week-1);
                       startActivity(in);
                       e.commit();
                       wsaving=sp.getInt("wsaving",0);
                       ssaving=sp.getInt("ssaving",0);
                       saving=sp.getInt("saving",0);

                       save.setText(Integer.toString(saving));
                   }
               }).setNegativeButton("NO",null).show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);
        sp=this.getSharedPreferences("com.example.rpnaren.expense",MODE_PRIVATE);
        e=sp.edit();
        week=sp.getInt("week",1);
        saving=sp.getInt("saving",0);
        wsaving=sp.getInt("wsaving",0);
        ssaving=sp.getInt("ssaving",0);
        save=(TextView) findViewById(R.id.save);
        amount=(EditText) findViewById(R.id.amount);
        source=(EditText) findViewById(R.id.Source);
        sourcemoney=(EditText) findViewById(R.id.Sourcemoney);

        save.setText(Integer.toString(saving));
        try {
            db = this.openOrCreateDatabase("db", MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS expense(name VARCHAR,cost INT(5),ie INT(1),week INT(3))");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
