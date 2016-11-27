package com.example.kd.mortgagecalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.Calendar;


public class MainScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        final EditText homeValue = (EditText) findViewById(R.id.homeValueTextField);
        homeValue.setFilters(new InputFilter[]{new MoneyFilter(8, 2)});
        final EditText downPayment = (EditText) findViewById(R.id.downPaymentTextField);
        downPayment.setFilters(new InputFilter[]{new MoneyFilter(8, 2)});
        final EditText interestRate = (EditText) findViewById(R.id.interestRateTextField);
        interestRate.setFilters(new InputFilter[]{new MoneyFilter(3, 2)});
        final NumberPicker terms = (NumberPicker) findViewById(R.id.termsNumberPicker);
        String[] yearValues = {"15", "20", "25", "30", "40"};
        terms.setDisplayedValues(yearValues);
        final EditText propertyTax = (EditText) findViewById(R.id.propertyTaxTextField);
        propertyTax.setFilters(new InputFilter[]{new MoneyFilter(3, 2)});
        final TextView totalTaxPaid = (TextView) findViewById(R.id.totalTaxPaidTextField);
        totalTaxPaid.setFilters(new InputFilter[]{new MoneyFilter(8, 2)});
        final TextView totalInterestPaid = (TextView) findViewById(R.id.totalInterestPaidTextField);
        final TextView monthlyPayment = (TextView) findViewById(R.id.monthlyPaymentTextField);
        final TextView payOffDate = (TextView) findViewById(R.id.payOffDateTextField);
        Button calculate = (Button) findViewById(R.id.calculateButton);
        calculate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                double monthlyRate = Double.valueOf(interestRate.getText().toString()) / 1200.0;
                int numberOfPeriods = terms.getValue() * 12;
                double loanValue = Double.valueOf(homeValue.getText().toString()) - Double.valueOf(downPayment.getText().toString());
                double mortgagePayment = loanValue * (monthlyRate * Math.pow(1 + monthlyRate, numberOfPeriods))/(Math.pow(1+monthlyRate, numberOfPeriods) - 1);
                double interestPaidTotal = mortgagePayment * numberOfPeriods - loanValue;
                double propertyTaxTotal = Integer.valueOf(homeValue.getText().toString()) * Double.valueOf(propertyTax.getText().toString())/100.0 * terms.getValue();
                double monthlyPaymentTotal = propertyTaxTotal/numberOfPeriods + mortgagePayment;
                Calendar calendar = Calendar.getInstance();
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                String endMonth = "";
                switch(month)
                {
                    case(0):
                        endMonth = "December";
                        break;
                    case(1):
                        endMonth = "January";
                        break;
                    case(2):
                        endMonth = "February";
                        break;
                    case(3):
                        endMonth = "March";
                        break;
                    case(4):
                        endMonth = "April";
                        break;
                    case(5):
                        endMonth = "May";
                        break;
                    case(6):
                        endMonth = "June";
                        break;
                    case(7):
                        endMonth = "July";
                        break;
                    case(8):
                        endMonth = "August";
                        break;
                    case(9):
                        endMonth = "September";
                        break;
                    case(10):
                        endMonth = "October";
                        break;
                    case(11):
                        endMonth = "November";
                        break;
                    default:
                        break;
                }
                int endYear = year + terms.getValue();
                String endOfPayOff = endMonth + " " + String.valueOf(endYear);
                totalTaxPaid.setText(toTwoDecimalPlaces(String.valueOf(propertyTaxTotal)));
                totalInterestPaid.setText(toTwoDecimalPlaces(String.valueOf(interestPaidTotal)));
                monthlyPayment.setText(toTwoDecimalPlaces(String.valueOf(monthlyPaymentTotal)));
                payOffDate.setText(String.valueOf(endOfPayOff));
            }
        });
        Button reset = (Button) findViewById(R.id.resetButton);
        reset.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                homeValue.setText("");
                downPayment.setText("");
                interestRate.setText("");
                terms.setValue(15);
                propertyTax.setText("");
                totalTaxPaid.setText("");
                totalInterestPaid.setText("");
                monthlyPayment.setText("");
                payOffDate.setText("");
            }
        });
    }

    /*
    Converts all resulting numbers to two decimal places
     */
    private String toTwoDecimalPlaces(String number)
    {
        String newString;
        if(number.contains("."))
        {
            int decimalIndex = number.indexOf(".") + 1;
            newString = number.substring(0, decimalIndex);
            if(number.substring(decimalIndex).length() < 2)
            {
                newString = newString + number.substring(decimalIndex) + "0";
            }
            else if(number.substring(decimalIndex).length() > 2)
            {
                newString += number.substring(decimalIndex, decimalIndex + 2);
            }
            else
            {
                newString = newString + number.substring(decimalIndex);
            }
        }
        else
        {
            newString = number + ".00";
        }
        return newString;
    }
}
