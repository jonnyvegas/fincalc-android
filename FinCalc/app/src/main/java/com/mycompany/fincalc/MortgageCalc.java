package com.mycompany.fincalc;

import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MortgageCalc extends AppCompatActivity {

    EditText edtHousePrice, edtDownPayment, edtInterestRate, edtTerm;
    TextView textBiMonthlyPayment, textMonthlyPayment, textAmtToInterest, textAmtToPrincipal;

    double housePrice = 0;
    double downPayment = 0;
    double interestRate = 0;
    double term = 0;
    double totalMortgage = 0;
    double biMonthlyPayment = 0;
    double monthlyPayment = 0;
    double amountToInterest = 0;
    double amountToPrincipal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mortgage_calc);
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void calculateLogic(View v)
    {
        edtHousePrice = (EditText)findViewById(R.id.edtHousePrice);
        edtDownPayment = (EditText)findViewById(R.id.edtDownPayment);
        edtInterestRate = (EditText)findViewById(R.id.edtInterestRate);
        edtTerm = (EditText)findViewById(R.id.edtPaymentAmount);

        textBiMonthlyPayment = (TextView)findViewById(R.id.textViewBiMonthly);
        textMonthlyPayment = (TextView)findViewById(R.id.textViewMonthly);

        String text1 = edtHousePrice.getText().toString().trim();
        String text2 = edtDownPayment.getText().toString().trim();
        String text3 = edtInterestRate.getText().toString().trim();
        String text4 = edtTerm.getText().toString().trim();

        if(text1.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter house price.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text2.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter down payment.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text3.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter interest rate.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text4.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter term.",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            double housePrice1 = Double.parseDouble(edtHousePrice.getText().toString());
            double downPayment1 = Double.parseDouble(edtDownPayment.getText().toString());
            double interestRate1 = Double.parseDouble(edtInterestRate.getText().toString());
            double term1 = Double.parseDouble(edtTerm.getText().toString());
            housePrice = housePrice1;
            downPayment = downPayment1;
            interestRate = interestRate1;
            term = term1;
            interestRate = interestRate * 0.01;
            housePrice = housePrice - downPayment;
            monthlyPayment = housePrice * ((interestRate / 12) / (1 - Math.pow((1  + interestRate / 12), term * -12)));
            biMonthlyPayment = monthlyPayment / 2;
        }
        printResults();
        keyboardDismiss();
    }

    public void clearLogic(View v)
    {
        edtHousePrice = (EditText)findViewById(R.id.edtHousePrice);
        edtDownPayment = (EditText)findViewById(R.id.edtDownPayment);
        edtInterestRate = (EditText)findViewById(R.id.edtInterestRate);
        edtTerm = (EditText)findViewById(R.id.edtPaymentAmount);

        textBiMonthlyPayment = (TextView)findViewById(R.id.textViewBiMonthly);
        textMonthlyPayment = (TextView)findViewById(R.id.textViewMonthly);
        textAmtToInterest = (TextView)findViewById(R.id.textAmtToInterest);
        textAmtToPrincipal = (TextView)findViewById(R.id.textAmtToPrincipal);

        edtHousePrice.setText("");
        edtDownPayment.setText("");
        edtInterestRate.setText("");
        edtTerm.setText("");

        textBiMonthlyPayment.setText("");
        textMonthlyPayment.setText("");
        textAmtToInterest.setText("");
        textAmtToPrincipal.setText("");

        keyboardDismiss();
    }

    private void printResults()
    {
        textBiMonthlyPayment = (TextView)findViewById(R.id.textViewBiMonthly);
        textMonthlyPayment = (TextView)findViewById(R.id.textViewMonthly);
        textAmtToInterest = (TextView)findViewById(R.id.textAmtToInterest);
        textAmtToPrincipal = (TextView)findViewById(R.id.textAmtToPrincipal);
        String strMonthly = String.format("%1.2f", biMonthlyPayment);
        String strBiMonthly = String.format("%1.2f" , monthlyPayment);
        amountToInterest = monthlyPayment * interestRate;
        amountToPrincipal = monthlyPayment - (monthlyPayment * interestRate);
        String strInterestAmt = String.format("%1.2f",amountToInterest);
        String strPrincipalAmt = String.format("%1.2f", amountToPrincipal);
        textBiMonthlyPayment.setText("$" + strMonthly);
        textMonthlyPayment.setText("$" + strBiMonthly);
        textAmtToInterest.setText("$" + strInterestAmt);
        textAmtToPrincipal.setText("$" + strPrincipalAmt);

    }

    private void keyboardDismiss()
    {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtDownPayment.getWindowToken(), 0);
    }

    public void send(View v)
    {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        edtHousePrice = (EditText)findViewById(R.id.edtHousePrice);
        edtDownPayment = (EditText)findViewById(R.id.edtDownPayment);
        edtInterestRate = (EditText)findViewById(R.id.edtInterestRate);
        edtTerm = (EditText)findViewById(R.id.edtPaymentAmount);
        String text1 = edtHousePrice.getText().toString().trim();
        String text2 = edtDownPayment.getText().toString().trim();
        String text3 = edtInterestRate.getText().toString().trim();
        String text4 = edtTerm.getText().toString().trim();

        textBiMonthlyPayment = (TextView)findViewById(R.id.textViewBiMonthly);
        textMonthlyPayment = (TextView)findViewById(R.id.textViewMonthly);
        textAmtToInterest = (TextView)findViewById(R.id.textAmtToInterest);
        textAmtToPrincipal = (TextView)findViewById(R.id.textAmtToPrincipal);
        if(text1.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter house price.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text2.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter down payment.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text3.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter interest rate.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text4.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter term.",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            emailIntent.setType("plain/text");
            String strDownPayment = String.format("%1.2f", downPayment);
            String strHousePrice = String.format("%1.2f", housePrice);
            String strMonthly = String.format("%1.2f", biMonthlyPayment);
            String strBiMonthly = String.format("%1.2f" , monthlyPayment);
            String strInterestAmt = String.format("%1.2f",amountToInterest);
            String strPrincipalAmt = String.format("%1.2f", amountToPrincipal);
            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Mortgage Calculation Results");
            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "House Price: $" + strHousePrice +
                                "\nDown Payment: $" + strDownPayment + "\nInterest Rate: " + interestRate * 100 + "% "
                            + "\nTerm: " + term + " years " + "\nBiMonthly Payment: $" + strBiMonthly + "\nMonthly Payment: $"
                            + strMonthly + "\nAmount to Interest: $" + strInterestAmt
                            + "\nAmount to Principal: $" + strPrincipalAmt);
            startActivity(Intent.createChooser(emailIntent, "Send your email in:"));
        }
    }

    public void saveInfo(View v)
    {
        try
        {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            // Creates a trace file in the primary external storage space of the
            // current application.
            // If the file does not exists, it is created.
            File traceFile = new File(((Context)this).getExternalFilesDir(null), "Mortgage.txt");
            if (!traceFile.exists()) {
                traceFile.createNewFile();
                // Adds a line to the trace file
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(traceFile, true /*append*/));
            String strDownPayment = String.format("%1.2f", downPayment);
            String strHousePrice = String.format("%1.2f", housePrice);
            String strMonthly = String.format("%1.2f", biMonthlyPayment);
            String strBiMonthly = String.format("%1.2f" , monthlyPayment);
            String strInterestAmt = String.format("%1.2f",amountToInterest);
            String strPrincipalAmt = String.format("%1.2f", amountToPrincipal);
            writer.write( " " + dateFormat.format(date) + " House Price: $" + strHousePrice +
                    " Down Payment: $" + strDownPayment + " Interest Rate: " + interestRate * 100 + "% "
                    + " Term: " + term + " years " + " BiMonthly Payment: $" + strBiMonthly + " Monthly Payment: $"
                    + strMonthly + " Amount to Interest: $" + strInterestAmt
                    + " Amount to Principal: $" + strPrincipalAmt);
            writer.close();
            // Refresh the data so it can seen when the device is plugged in a
            // computer. You may have to unplug and replug the device to see the
            // latest changes. This is not necessary if the user should not modify
            // the files.
            MediaScannerConnection.scanFile((Context) (this),
                    new String[]{traceFile.toString()},
                    null,
                    null);

            Toast.makeText(getApplicationContext(), "Info has been saved.",
                    Toast.LENGTH_SHORT).show();
        }
        catch (IOException e)
        {
            Log.e("com.mycompany.FileTest", "Unable to write to the Mortgage.txt file.");
        }
    }
}
