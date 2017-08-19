package com.mycompany.fincalc;

import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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


public class RetirementCalc extends AppCompatActivity {
    EditText edtRequiredIncome, edtSocialSecurity, edtPension,
            edtPartTimeIncome, edtRetirementAge,
            edtMoneyInSavings, edtRetiringIn;

    TextView textNeedToSave, textNeededForRetirement, textNeededPerYear;
    double requiredIncome = 0, socialSecurity = 0, pension = 0,
            partTimeIncome = 0, anticipatedRetirementAge = 0,
            moneyInSavings = 0, retiringIn = 0, retirementShortfall = 0,
            needToSave = 0, neededForRetirement = 0, neededPerYear = 0, tempResult = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retirement_calc);
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
        edtRequiredIncome = (EditText)findViewById(R.id.edtRequiredIncome);
        edtSocialSecurity = (EditText)findViewById(R.id.edtSocialSecurity);
        edtPension = (EditText)findViewById(R.id.edtPension);
        edtPartTimeIncome = (EditText)findViewById(R.id.edtPartTimeIncome);
        edtRetirementAge = (EditText)findViewById(R.id.edtRetirementAge);
        edtMoneyInSavings = (EditText)findViewById(R.id.edtMoneyInSavings);
        edtRetiringIn = (EditText)findViewById(R.id.edtRetiringIn);

        String text1 = edtRequiredIncome.getText().toString().trim();
        String text2 = edtSocialSecurity.getText().toString().trim();
        String text3 = edtPension.getText().toString().trim();
        String text4 = edtPartTimeIncome.getText().toString().trim();
        String text5 = edtRetirementAge.getText().toString().trim();
        String text6 = edtMoneyInSavings.getText().toString().trim();
        String text7 = edtRetiringIn.getText().toString().trim();

        if(text1.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter required income.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text2.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter Social Security.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text3.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter pension.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text4.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter part-time income.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text5.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter retirement age.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text6.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter $ in savings.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text7.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter years retiring in.",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            double requiredIncome1 = Double.parseDouble(edtRequiredIncome.getText().toString());
            double socialSecurity1 = Double.parseDouble(edtSocialSecurity.getText().toString());
            double pension1 = Double.parseDouble(edtPension.getText().toString());
            double partTimeIncome1 = Double.parseDouble(edtPartTimeIncome.getText().toString());
            double retirementAge1 = Double.parseDouble(edtRetirementAge.getText().toString());
            double moneyInSavings1 = Double.parseDouble(edtMoneyInSavings.getText().toString());
            double retiringIn1 = Double.parseDouble(edtRetiringIn.getText().toString());
            requiredIncome = requiredIncome1;
            socialSecurity = socialSecurity1;
            pension = pension1;
            partTimeIncome = partTimeIncome1;
            anticipatedRetirementAge = retirementAge1;
            moneyInSavings = moneyInSavings1;
            retiringIn = retiringIn1;

            retirementShortfall = requiredIncome - socialSecurity - pension - partTimeIncome;

            if(anticipatedRetirementAge <= 55)
            {
                needToSave = retirementShortfall * 21;
            }
            else if(anticipatedRetirementAge > 55  && anticipatedRetirementAge <= 60)
            {
                needToSave = retirementShortfall * 18.9;
            }
            else if(anticipatedRetirementAge > 60 && anticipatedRetirementAge <= 65)
            {
                needToSave = retirementShortfall * 16.4;
            }
            else
            {
                needToSave = retirementShortfall * 13.6;
            }

            if(retiringIn <= 10)
            {
                tempResult = moneyInSavings * 1.3;
            }
            else if(retiringIn > 10 && retiringIn <= 15)
            {
                tempResult = moneyInSavings * 1.6;
            }
            else if(retiringIn > 15 && retiringIn <= 20)
            {
                tempResult = moneyInSavings * 1.8;
            }
            else if(retiringIn > 20 && retiringIn <= 25)
            {
                tempResult = moneyInSavings * 2.1;
            }
            else if(retiringIn > 25 && retiringIn <= 30)
            {
                tempResult = moneyInSavings * 2.4;
            }
            else if(retiringIn > 30 && retiringIn <= 35)
            {
                tempResult = moneyInSavings * 2.8;
            }
            else
            {
                tempResult = moneyInSavings * 3.3;
            }

            neededForRetirement = needToSave - tempResult;
            if(retiringIn <= 10)
            {
                neededPerYear = neededForRetirement * .085;
            }
            else if(retiringIn > 10 && retiringIn <= 15)
            {
                neededPerYear = neededForRetirement * .052;
            }
            else if(retiringIn > 15 && retiringIn <= 20)
            {
                neededPerYear = neededForRetirement * .036;
            }
            else if(retiringIn > 20 && retiringIn <= 25)
            {
                neededPerYear = neededForRetirement * .027;
            }
            else if(retiringIn > 25 && retiringIn <= 30)
            {
                neededPerYear = neededForRetirement * .020;
            }
            else if(retiringIn > 30 && retiringIn <= 35)
            {
                neededPerYear = neededForRetirement * .016;
            }
            else
            {
                neededPerYear = neededForRetirement * .013;
            }
        }
        printResults();
        dismissKeyboard();
    }

    public void clearLogic(View v)
    {
        edtRequiredIncome = (EditText)findViewById(R.id.edtRequiredIncome);
        edtSocialSecurity = (EditText)findViewById(R.id.edtSocialSecurity);
        edtPension = (EditText)findViewById(R.id.edtPension);
        edtPartTimeIncome = (EditText)findViewById(R.id.edtPartTimeIncome);
        edtRetirementAge = (EditText)findViewById(R.id.edtRetirementAge);
        edtMoneyInSavings = (EditText)findViewById(R.id.edtMoneyInSavings);
        edtRetiringIn = (EditText)findViewById(R.id.edtRetiringIn);
        textNeedToSave = (TextView)findViewById(R.id.textNeedToSave);
        textNeededForRetirement = (TextView)findViewById(R.id.textNeededForRetirement);
        textNeededPerYear = (TextView)findViewById(R.id.textNeededPerYear);

        edtRequiredIncome.setText("");
        edtSocialSecurity.setText("");
        edtPension.setText("");
        edtPartTimeIncome.setText("");
        edtRetirementAge.setText("");
        edtMoneyInSavings.setText("");
        edtRetiringIn.setText("");

        textNeedToSave.setText("Need to Save: $");
        textNeededForRetirement.setText("Needed for Retirement: $");
        textNeededPerYear.setText("Needed per Year: $");
        dismissKeyboard();
    }

    private void printResults()
    {
        textNeedToSave = (TextView)findViewById(R.id.textNeedToSave);
        textNeededForRetirement = (TextView)findViewById(R.id.textNeededForRetirement);
        textNeededPerYear = (TextView)findViewById(R.id.textNeededPerYear);

        String strNeedToSave = String.format("%1.2f", needToSave);
        String strNeededForRetirement = String.format("%1.2f", neededForRetirement);
        String strNeededPerYear = String.format("%1.2f", neededPerYear);

        textNeedToSave.setText("Need to Save: $" + strNeedToSave);
        textNeededForRetirement.setText("Needed for Retirement: $" + strNeededForRetirement);
        textNeededPerYear.setText("Needed per Year: $" + strNeededPerYear);
    }

    private void dismissKeyboard()
    {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtRequiredIncome.getWindowToken(), 0);
    }

    public void sendInfo(View v)
    {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        edtRequiredIncome = (EditText)findViewById(R.id.edtRequiredIncome);
        edtSocialSecurity = (EditText)findViewById(R.id.edtSocialSecurity);
        edtPension = (EditText)findViewById(R.id.edtPension);
        edtPartTimeIncome = (EditText)findViewById(R.id.edtPartTimeIncome);
        edtRetirementAge = (EditText)findViewById(R.id.edtRetirementAge);
        edtMoneyInSavings = (EditText)findViewById(R.id.edtMoneyInSavings);
        edtRetiringIn = (EditText)findViewById(R.id.edtRetiringIn);
        textNeedToSave = (TextView)findViewById(R.id.textNeedToSave);
        textNeededForRetirement = (TextView)findViewById(R.id.textNeededForRetirement);
        textNeededPerYear = (TextView)findViewById(R.id.textNeededPerYear);
        String text1 = edtRequiredIncome.getText().toString().trim();
        String text2 = edtSocialSecurity.getText().toString().trim();
        String text3 = edtPension.getText().toString().trim();
        String text4 = edtPartTimeIncome.getText().toString().trim();
        String text5 = edtRetirementAge.getText().toString().trim();
        String text6 = edtMoneyInSavings.getText().toString().trim();
        String text7 = edtRetiringIn.getText().toString().trim();

        if(text1.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter required income.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text2.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter Social Security.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text3.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter pension.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text4.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter part-time income.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text5.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter retirement age.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text6.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter $ in savings.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(text7.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Enter years retiring in.",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            emailIntent.setType("plain/text");
            String strReqiuiredIncome = String.format("%1.2f", requiredIncome);
            String strSocialSecurity = String.format("%1.2f", socialSecurity);
            String strPension = String.format("%1.2f", pension);
            String strPartTimeIncome= String.format("%1.2f", partTimeIncome);
            String strRetirementAge = String.format("%1.0f", anticipatedRetirementAge);
            String strMoneyInSavings = String.format("%1.2f", moneyInSavings);
            String strRetiringIn = String.format("%1.0f", retiringIn);
            String strNeedToSave = String.format("%1.2f", needToSave);
            String strNeededForRetirement = String.format("%1.2f", neededForRetirement);
            String strNeededPerYear = String.format("%1.2f", neededPerYear);
            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Retirement Calculation Result");
            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,"Required Income: $" + strReqiuiredIncome
                                + "\nSocial Security: $" + strSocialSecurity + "\nPension: $" + strPension +
                                "\nPart-Time Income: $" + strPartTimeIncome + "\nRetirement Age: " +
                                strRetirementAge + " Years" + "\nMoney in Savings: $" + strMoneyInSavings
                                + "\nRetiring In: " + strRetiringIn + " Years" + "\nNeed to Save: $"
                                + strNeedToSave + "\nNeeded for Retirement: $" + strNeededForRetirement
                                + "\nNeeded per Year: $" + strNeededPerYear);
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
            File traceFile = new File(((Context)this).getExternalFilesDir(null), "Retirement.txt");
            if (!traceFile.exists()) {
                traceFile.createNewFile();
                // Adds a line to the trace file
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(traceFile, true /*append*/));
            String strReqiuiredIncome = String.format("%1.2f", requiredIncome);
            String strSocialSecurity = String.format("%1.2f", socialSecurity);
            String strPension = String.format("%1.2f", pension);
            String strPartTimeIncome= String.format("%1.2f", partTimeIncome);
            String strRetirementAge = String.format("%1.0f", anticipatedRetirementAge);
            String strMoneyInSavings = String.format("%1.2f", moneyInSavings);
            String strRetiringIn = String.format("%1.0f", retiringIn);
            String strNeedToSave = String.format("%1.2f", needToSave);
            String strNeededForRetirement = String.format("%1.2f", neededForRetirement);
            String strNeededPerYear = String.format("%1.2f", neededPerYear);
            writer.write(" " +  dateFormat.format(date) + " Required Income: $" + strReqiuiredIncome
                    + " Social Security: $" + strSocialSecurity + " Pension: $" + strPension +
                    " Part-Time Income: $" + strPartTimeIncome + " Retirement Age: " +
                    strRetirementAge + " Years" + "\nMoney in Savings: $" + strMoneyInSavings
                    + " Retiring In: " + strRetiringIn + " Years" + " Need to Save: $"
                    + strNeedToSave + " Needed for Retirement: $" + strNeededForRetirement
                    + " Needed per Year: $" + strNeededPerYear);
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
            Log.e("com.mycompany.FileTest", "Unable to write to the Retirement.txt file.");
        }
    }
}
