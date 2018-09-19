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
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    SharedPreferences sp;
    SharedPreferences.Editor e;
    int saving,wsaving,ssaving;
    EditText expense,expensecost;
    SeekBar s;
    TextView amount;
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

    public void update(View view)
    {
       temp=Integer.parseInt(expensecost.getText().toString());
        tem=expense.getText().toString();
        if(wsaving<temp)
        {
            new AlertDialog.Builder(this).setTitle("Error").setMessage("Amount is less").setPositiveButton("OK",null).show();
            return;
        }
        new AlertDialog.Builder(this).setTitle("Confirm transaction").setPositiveButton("Confirm?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                e.putInt("wsaving",wsaving-temp);

                e.commit();
                wsaving=sp.getInt("wsaving",0);

                expense.setText("");
                expensecost.setText("");
                amount.setText(Integer.toString(wsaving));
                s.setProgress(wsaving);
                try{
                    db.execSQL("INSERT INTO expense VALUES('"+tem+"',"+temp+",1,"+week+")");
                }
                catch(Exception e)
                {e.printStackTrace();}

            }
        }).setNegativeButton("Cancel",null).show();


    }
    public void seethisweekexpense(View view)
    {
        Intent i=new Intent(this,db.class);
        i.putExtra("week",week);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp=this.getSharedPreferences("com.example.rpnaren.expense",MODE_PRIVATE);
        e=sp.edit();
//        e.clear();
//        e.commit();
        week=sp.getInt("week",1);

        expense=(EditText) findViewById(R.id.Expensename);
        expensecost=(EditText) findViewById(R.id.Expensecost);
        saving=sp.getInt("saving",0);
        wsaving=sp.getInt("wsaving",0);
        ssaving=sp.getInt("ssaving",0);
        s=(SeekBar) findViewById(R.id.seekbar);
        amount=(TextView) findViewById(R.id.amount);
        s.setMax(ssaving);
        s.setProgress(wsaving);
        s.setEnabled(false);
        amount.setText(Integer.toString(wsaving));
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
