package ir.proglovving.fitapp;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class Utilities {

    public static String englishToPersianNumber(int number){
        return String.valueOf(number)
                .replace('0','۰')
                .replace('1','۱')
                .replace('2','۲')
                .replace('3','۳')
                .replace('4','۴')
                .replace('5','۵')
                .replace('6','۶')
                .replace('7','۷')
                .replace('8','۸')
                .replace('9','۹');
    }
}
