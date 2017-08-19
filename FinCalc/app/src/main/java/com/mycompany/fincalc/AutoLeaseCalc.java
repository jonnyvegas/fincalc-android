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

public class AutoLeaseCalc extends AppCompatActivity {

    final double CASALESTAX = 0.0975;
    EditText edtMsrp, edtTermOfLease, edtInterestRate, edtInvoicePrice, edtResidualValue;
    TextView textMonthlyPayment, textMonthlyPaymentInCa, textAmtToPrincipal,
            textAmountToInterest, textTotalForLease;
    double msrp = 0, moneyFactor = 0, termOfLease = 0, residualValue = 0,
            monthlyPayment = 0, monthlyPaymentInCa = 0, amtToPrincipal = 0,
            amtToInterest = 0, totalForLease = 0, invoicePrice = 0, interestRate = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_lease_calc);
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
        edtMsrp = (EditText)findViewById(R.id.edtMsrp);
        edtInterestRate = (EditText)findViewById(R.id.edtInterestRate);
        edtTermOfLease = (EditText)findViewById(R.id.edtTermOfLease);
        edtResidualValue = (EditText)findViewById(R.id.edtResidualValue);
        edtInvoicePrice = (EditText)findViewById(R.id.edtInvoicePrice);

        String text1 = edtMsrp.getText().toString().trim();
        String text2 = edtResidualValue.getText().toString().trim();
        String text3 = edtTermOfLease.getText().toString().trim();
        String text4 = edtInterestRate.getText().toString().trim();
        String text5 = edtInvoicePrice.getText().toString().trim();

        if(text1.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter MSRP.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text2.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter residual value.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text3.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter term of lease.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text4.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter interest rate.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text5.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter invoice price.",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            double msrp1 = Double.parseDouble(edtMsrp.getText().toString());
            double residualValue1 = Double.parseDouble(edtResidualValue.getText().toString());
            double termOfLease1 = Double.parseDouble(edtTermOfLease.getText().toString());
            double interestRate1 = Double.parseDouble(edtInterestRate.getText().toString());
            double invoicePrice1 = Double.parseDouble(edtInvoicePrice.getText().toString());

            msrp = msrp1;
            residualValue = residualValue1;
            termOfLease = termOfLease1;
            interestRate = interestRate1;
            invoicePrice = invoicePrice1;

            residualValue *= 0.01;
            moneyFactor = interestRate / 2400;
            interestRate *= 0.01;
            double residualValueOfCar = msrp * residualValue;
            double depreciation = invoicePrice - residualValueOfCar;
            double monthlyDepreciationPayment = depreciation / termOfLease;
            double numTimesMoneyFactor = invoicePrice + residualValueOfCar;
            double interestPayment = numTimesMoneyFactor * moneyFactor;
            monthlyPayment = interestPayment + monthlyDepreciationPayment;
            monthlyPaymentInCa = monthlyPayment + (monthlyPayment * CASALESTAX);
            amtToInterest = monthlyPayment * (interestRate * 100) / 12;
            amtToPrincipal = monthlyPayment - amtToInterest;
            totalForLease = monthlyPayment * termOfLease;
        }
        printResults();
        dismissKeyboard();
    }

    public void clearLogic(View v)
    {
        edtMsrp = (EditText)findViewById(R.id.edtMsrp);
        edtInvoicePrice = (EditText)findViewById(R.id.edtInvoicePrice);
        edtTermOfLease = (EditText)findViewById(R.id.edtTermOfLease);
        edtInterestRate = (EditText)findViewById(R.id.edtInterestRate);
        edtResidualValue = (EditText)findViewById(R.id.edtResidualValue);

        textMonthlyPayment = (TextView)findViewById(R.id.textMonthlyPayment);
        textMonthlyPaymentInCa = (TextView)findViewById(R.id.textMonthlyPaymentInCa);
        textAmtToPrincipal = (TextView)findViewById(R.id.textAmtToPrincipal);
        textAmountToInterest = (TextView)findViewById(R.id.textAmountToInterest);
        textTotalForLease = (TextView)findViewById(R.id.textTotalForLease);

        edtMsrp.setText("");
        edtInvoicePrice.setText("");
        edtTermOfLease.setText("");
        edtInterestRate.setText("");
        edtResidualValue.setText("");

        textMonthlyPayment.setText("Monthly Payment: $");
        textMonthlyPaymentInCa.setText("Monthly Payment in CA: $");
        textAmtToPrincipal.setText("Amount to Principal: $");
        textAmountToInterest.setText("Amount to Interest: $");
        textTotalForLease.setText("Total For Lease: $");

        dismissKeyboard();

    }

    public void dismissKeyboard()
    {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtMsrp.getWindowToken(), 0);
    }
    public void printResults()
    {
        edtMsrp = (EditText)findViewById(R.id.edtMsrp);
        edtInvoicePrice = (EditText)findViewById(R.id.edtInvoicePrice);
        edtTermOfLease = (EditText)findViewById(R.id.edtTermOfLease);
        edtInterestRate = (EditText)findViewById(R.id.edtInterestRate);
        edtResidualValue = (EditText)findViewById(R.id.edtResidualValue);

        textMonthlyPayment = (TextView)findViewById(R.id.textMonthlyPayment);
        textMonthlyPaymentInCa = (TextView)findViewById(R.id.textMonthlyPaymentInCa);
        textAmtToPrincipal = (TextView)findViewById(R.id.textAmtToPrincipal);
        textAmountToInterest = (TextView)findViewById(R.id.textAmountToInterest);
        textTotalForLease= (TextView)findViewById(R.id.textTotalForLease);

        String strMonthly = String.format("%1.2f", monthlyPayment);
        String strMonthlyInCa = String.format("%1.2f", monthlyPaymentInCa);
        String strAmtToPrincipal = String.format("%1.2f", amtToPrincipal);
        String strAmtToInterest = String.format("%1.2f", amtToInterest);
        String strTotalForLease = String.format("%1.2f", totalForLease);
        textMonthlyPayment.setText("Monthly Payment: $" + strMonthly);
        textMonthlyPaymentInCa.setText("Monthly Payment in CA: $" + strMonthlyInCa);
        textAmtToPrincipal.setText("Amount to Principal: $" + strAmtToPrincipal);
        textAmountToInterest.setText("Amount to Interest: $" + strAmtToInterest);
        textTotalForLease.setText("Total for Lease: $" + strTotalForLease);

    }

    public void sendInfo(View v)
    {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        edtMsrp = (EditText)findViewById(R.id.edtMsrp);
        edtInvoicePrice = (EditText)findViewById(R.id.edtInvoicePrice);
        edtTermOfLease = (EditText)findViewById(R.id.edtTermOfLease);
        edtInterestRate = (EditText)findViewById(R.id.edtInterestRate);
        edtResidualValue = (EditText)findViewById(R.id.edtResidualValue);

        textMonthlyPayment = (TextView)findViewById(R.id.textMonthlyPayment);
        textMonthlyPaymentInCa = (TextView)findViewById(R.id.textMonthlyPaymentInCa);
        textAmtToPrincipal = (TextView)findViewById(R.id.textAmtToPrincipal);
        textAmountToInterest = (TextView)findViewById(R.id.textAmountToInterest);
        textTotalForLease= (TextView)findViewById(R.id.textTotalForLease);



        String text1 = edtMsrp.getText().toString().trim();
        String text2 = edtResidualValue.getText().toString().trim();
        String text3 = edtTermOfLease.getText().toString().trim();
        String text4 = edtInterestRate.getText().toString().trim();
        String text5 = edtInvoicePrice.getText().toString().trim();

        if(text1.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter MSRP.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text2.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter residual value.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text3.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter term of lease.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text4.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter interest rate.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text5.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter invoice price.",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            emailIntent.setType("plain/text");
            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Auto Lease Calculation Info");
            String strMsrp = String.format("%1.2f", msrp);
            String strResidualValue = String.format("%1.1f", residualValue * 100);
            String strTerm = String.format("%1.0f", termOfLease);
            String strInterestRate = String.format("%1.1f", interestRate * 100);
            String strInvoicePrice = String.format("%1.2f", invoicePrice);
            String strMonthly = String.format("%1.2f", monthlyPayment);
            String strMonthlyInCa = String.format("%1.2f", monthlyPaymentInCa);
            String strAmtToPrincipal = String.format("%1.2f", amtToPrincipal);
            String strAmtToInterest = String.format("%1.2f", amtToInterest);
            String strTotalForLease = String.format("%1.2f", totalForLease);
            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "MSRP: $" + strMsrp +
                    "\n Residual Value: " + strResidualValue + "%"+ "\nTerm: " + strTerm + " months"
                    + "\n Interest Rate: " + strInterestRate + "%" + "\n Invoice Price: $" + strInvoicePrice
                    + "\n Monthly Payment: $" + strMonthly + "\n Monthly Payment in CA: $" + strMonthlyInCa
                    + "\n Amount to Principal: $" + strAmtToPrincipal + "\n Amount to Interest: $"
                    + strAmtToInterest + " \n Total for Lease: $" + strTotalForLease);
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
            File traceFile = new File(((Context)this).getExternalFilesDir(null), "AutoLease.txt");
            if (!traceFile.exists()) {
                traceFile.createNewFile();
                // Adds a line to the trace file
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(traceFile, true /*append*/));
            String strMsrp = String.format("%1.2f", msrp);
            String strResidualValue = String.format("%1.1f", residualValue * 100);
            String strTerm = String.format("%1.0f", termOfLease);
            String strInterestRate = String.format("%1.1f", interestRate * 100);
            String strInvoicePrice = String.format("%1.2f", invoicePrice);
            String strMonthly = String.format("%1.2f", monthlyPayment);
            String strMonthlyInCa = String.format("%1.2f", monthlyPaymentInCa);
            String strAmtToPrincipal = String.format("%1.2f", amtToPrincipal);
            String strAmtToInterest = String.format("%1.2f", amtToInterest);
            String strTotalForLease = String.format("%1.2f", totalForLease);
            writer.write(" " +  dateFormat.format(date) + " MSRP: $" + strMsrp +
                    " Residual Value: " + strResidualValue + "%"+ " Term: " + strTerm + " months"
                    + " Interest Rate: " + strInterestRate + "%" + " Invoice Price: $" + strInvoicePrice
                    + " Monthly Payment: $" + strMonthly + " Monthly Payment in CA: $" + strMonthlyInCa
                    + " Amount to Principal: $" + strAmtToPrincipal + " Amount to Interest: $"
                    + strAmtToInterest + "  Total for Lease: $" + strTotalForLease);
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
            Log.e("com.mycompany.FileTest", "Unable to write to the AutoLease.txt file.");
        }
    }
}
