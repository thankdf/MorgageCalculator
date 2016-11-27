package com.example.kd.mortgagecalculator;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kd on 11/21/16.
 */
public class MoneyFilter implements InputFilter
{
    Pattern moneyPattern;

    public MoneyFilter(int digitsBeforeZero,int digitsAfterZero)
    {
        moneyPattern=Pattern.compile("[0-9]{0," + (digitsBeforeZero-1) + "}+((\\.[0-9]{0," + (digitsAfterZero-1) + "})?)||(\\.)?");
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

        Matcher matcher = moneyPattern.matcher(dest);
        if(!matcher.matches())
            return "";
        return null;
    }

}