package ir.proglovving.fitapp;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import ir.proglovving.cfviews.CTypefaceProvider;

public class Utilities {
    public static void applyFontForNavigationView(Context context, NavigationView navigationView) {
        Typeface typeface = CTypefaceProvider.getVazir(context);

        Menu m = navigationView.getMenu();
        for (int i = 0; i < m.size(); i++) {
            MenuItem mi = m.getItem(i);
            applyFontToMenuItem(mi, typeface);
        }
    }

    private static void applyFontToMenuItem(MenuItem mi, Typeface typeface) {
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", typeface), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

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
