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

public class AutoLoanCalc extends AppCompatActivity {

    EditText edtLoanAmount, edtTerm, edtRate;
    TextView textMonthlyPayment, textAmountTowardPrincipal, textAmountTowardInterest,
              textTotalForLoan, textTotalToInterest;

    double principal = 0, interestRate = 0, term = 0, monthlyPayment = 0,
            totalForLoan = 0, totalToInterest = 0, amountToInterest = 0, amountToPrincipal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_loan_calc);
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
        edtLoanAmount = (EditText)findViewById(R.id.edtLoanAmount);
        edtTerm = (EditText)findViewById(R.id.edtPaymentAmount);
        edtRate = (EditText)findViewById(R.id.edtRate);
        textMonthlyPayment = (TextView)findViewById(R.id.textTimeToPayoff);
        textTotalForLoan = (TextView)findViewById(R.id.textTotalForLoan);
        textTotalToInterest = (TextView)findViewById(R.id.textTotalToInterest);

        String text1 = edtLoanAmount.getText().toString().trim();
        String text2 = edtTerm.getText().toString().trim();
        String text3 = edtRate.getText().toString().trim();

        if(text1.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter loan amount.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text2.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter term.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text3.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter interest rate.",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            double principal1 = Double.parseDouble(edtLoanAmount.getText().toString());
            double term1 = Double.parseDouble(edtTerm.getText().toString());
            double rate1 = Double.parseDouble(edtRate.getText().toString());
            principal = principal1;
            term = term1;
            interestRate = rate1;
            interestRate = interestRate * 0.01;
            monthlyPayment = ((principal * (interestRate / 12)) / (1 - (Math.pow((1 + interestRate / 12),term * -12))));
            //totalForLoan = (principal * Math.pow((1 + (interestRate / 12)),(12 * term)));
            //totalToInterest = totalForLoan - principal;
        }
        printResults();
        keyboardDismiss();

    }

    public void clearLogic(View v)
    {
        edtLoanAmount = (EditText)findViewById(R.id.edtLoanAmount);
        edtTerm = (EditText)findViewById(R.id.edtPaymentAmount);
        edtRate = (EditText)findViewById(R.id.edtRate);
        textMonthlyPayment = (TextView)findViewById(R.id.textTimeToPayoff);
        textTotalForLoan = (TextView)findViewById(R.id.textTotalForLoan);
        textTotalToInterest = (TextView)findViewById(R.id.textTotalToInterest);
        textAmountTowardInterest = (TextView)findViewById(R.id.textAmountTowardInterest);
        textAmountTowardPrincipal = (TextView)findViewById(R.id.textAmountTowardPrincipal);

        edtLoanAmount.setText("");
        edtTerm.setText("");
        edtRate.setText("");
        textMonthlyPayment.setText("Monthly Payment: $");
        textTotalForLoan.setText("Total for Loan: $");
        textTotalToInterest.setText("Total to Interest: $");
        textAmountTowardPrincipal.setText("Amount toward Principal: $");
        textAmountTowardInterest.setText("Amount toward Interest: $");
        principal = 0;
        interestRate = 0;
        term = 0;
        monthlyPayment = 0;
        totalForLoan = 0;
        totalToInterest = 0;
        keyboardDismiss();
    }

    private void printResults()
    {
        textMonthlyPayment = (TextView)findViewById(R.id.textTimeToPayoff);
        textTotalForLoan = (TextView)findViewById(R.id.textTotalForLoan);
        textTotalToInterest = (TextView)findViewById(R.id.textTotalToInterest);
        textAmountTowardInterest = (TextView)findViewById(R.id.textAmountTowardInterest);
        textAmountTowardPrincipal = (TextView)findViewById(R.id.textAmountTowardPrincipal);

        String strMonthly = String.format("%1.2f", monthlyPayment);
        amountToInterest = monthlyPayment * interestRate;
        amountToPrincipal = monthlyPayment - (monthlyPayment * interestRate);
        totalToInterest = monthlyPayment * term * 12 - principal;
        totalForLoan = totalToInterest + principal;

        String strAmountToInterest = String.format("%1.2f", amountToInterest);
        String strAmountToPrincipal = String.format("%1.2f", amountToPrincipal);
        String strTotalToInterest = String.format("%1.2f", totalToInterest);
        String strTotalForLoan = String.format("%1.2f", totalForLoan);
        textMonthlyPayment.setText("Monthly Payment: $" + strMonthly);
        textAmountTowardInterest.setText("Amount to Interest: $" + strAmountToInterest);
        textAmountTowardPrincipal.setText("Amount to Principal: $" + strAmountToPrincipal);
        textTotalToInterest.setText("Total to Interest: $" + strTotalToInterest);
        textTotalForLoan.setText("Total for Loan: $" + strTotalForLoan);
    }

    private void keyboardDismiss()
    {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtTerm.getWindowToken(), 0);
    }

    public void sendInfo(View v)
    {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

        edtLoanAmount = (EditText)findViewById(R.id.edtLoanAmount);
        edtTerm = (EditText)findViewById(R.id.edtPaymentAmount);
        edtRate = (EditText)findViewById(R.id.edtRate);
        textMonthlyPayment = (TextView)findViewById(R.id.textTimeToPayoff);
        textTotalForLoan = (TextView)findViewById(R.id.textTotalForLoan);
        textTotalToInterest = (TextView)findViewById(R.id.textTotalToInterest);
        textAmountTowardInterest = (TextView)findViewById(R.id.textAmountTowardInterest);
        textAmountTowardPrincipal = (TextView)findViewById(R.id.textAmountTowardPrincipal);
        String text1 = edtLoanAmount.getText().toString().trim();
        String text2 = edtTerm.getText().toString().trim();
        String text3 = edtRate.getText().toString().trim();

        if(text1.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter loan amount.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text2.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter term.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text3.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter interest rate.",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            double tempInterest = interestRate * 100;
            emailIntent.setType("plain/text");
            String strAmountToInterest = String.format("%1.2f", amountToInterest);
            String strAmountToPrincipal = String.format("%1.2f", amountToPrincipal);
            String strTotalToInterest = String.format("%1.2f", totalToInterest);
            String strTotalForLoan = String.format("%1.2f", totalForLoan);
            String strLoanAmount = String.format("%1.2f", principal);
            String strTerm = String.format("%1.0f", term);
            String strInterestRate = String.format("%1.2f", tempInterest);
            String strMonthlyPayment = String.format("%1.2f", monthlyPayment);
            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Auto Loan Calculation Results");
            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Loan Amount: $" + strLoanAmount +
                                "\nTerm: " + strTerm + " Years" + "\nInterest Rate: " + strInterestRate
                                + "%" + "\nMonthly Payment: $" + strMonthlyPayment + "\nAmount to Principal: $"
                                + strAmountToPrincipal + "\nAmount to Interest: $" + strAmountToInterest +
                                "\nTotal for Loan: $" + strTotalForLoan + "\nTotal to Interest: $"
                                + strTotalToInterest);
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
            File traceFile = new File(((Context)this).getExternalFilesDir(null), "AutoLoan.txt");
            if (!traceFile.exists()) {
                traceFile.createNewFile();
                // Adds a line to the trace file
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(traceFile, true /*append*/));
            String strAmountToInterest = String.format("%1.2f", amountToInterest);
            String strAmountToPrincipal = String.format("%1.2f", amountToPrincipal);
            String strTotalToInterest = String.format("%1.2f", totalToInterest);
            String strTotalForLoan = String.format("%1.2f", totalForLoan);
            String strLoanAmount = String.format("%1.2f", principal);
            String strTerm = String.format("%1.0f", term);
            String strInterestRate = String.format("%1.2f", interestRate * 100);
            String strMonthlyPayment = String.format("%1.2f", monthlyPayment);
            writer.write(" " +  dateFormat.format(date) + " Loan Amount: $" + strLoanAmount +
                    "Term: " + strTerm + " Years" + " Interest Rate: " + strInterestRate
                    + "%" + " Monthly Payment: $" + strMonthlyPayment + " Amount to Principal: $"
                    + strAmountToPrincipal + " Amount to Interest: $" + strAmountToInterest +
                    " Total for Loan: $" + strTotalForLoan + " Total to Interest: $"
                    + strTotalToInterest);
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
            Log.e("com.mycompany.FileTest", "Unable to write to the AutoLoan.txt file.");
        }
    }
}
