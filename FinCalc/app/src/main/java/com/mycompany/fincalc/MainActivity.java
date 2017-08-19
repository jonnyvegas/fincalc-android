package com.mycompany.fincalc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import org.achartengine.chart.PieChart;

public class MainActivity extends AppCompatActivity {

    private PieChart mChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void certOfDep(View v)
    {
        Intent intent = new Intent(this, CertificateOfDepositCalc.class);
        startActivity(intent);
    }

    public void mortgageCalc(View v)
    {
        Intent intent = new Intent(this, MortgageCalc.class);
        startActivity(intent);
    }

    public void autoLoan(View v)
    {
        Intent intent = new Intent(this, AutoLoanCalc.class);
        startActivity(intent);
    }

    public void creditCard(View v)
    {
        Intent intent = new Intent(this, CreditCardCalc.class);
        startActivity(intent);
    }
    public void autoLease(View v)
    {
        Intent intent = new Intent(this, AutoLeaseCalc.class);
        startActivity(intent);
    }
    public void retirement(View v)
    {
        Intent intent = new Intent(this, RetirementCalc.class);
        startActivity(intent);
    }
    public void savingsRecurring(View v)
    {
        Intent intent = new Intent(this, SavingsRecurringCalc.class);
        startActivity(intent);
    }
}
