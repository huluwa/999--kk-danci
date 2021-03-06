package org.ling0322.danci;


import java.util.*;


import android.app.*;
import android.graphics.*;
import android.util.Log;
import android.view.*;
import android.widget.*;

public class DefinitionView {
    
    public static View getDefinitionView(Activity activity, String word) {
        return getDefinitionView(activity, word, false);
        
    }
    
    public static Typeface lingoesFont = null;
    
    public static View getDefinitionView(Activity activity, String word, boolean onlyWord) {
        
        Dictionary dict = Dictionary.getInstance();
        Dictionary.Word dictWord = dict.getDefinition(word);
        String pron = dictWord.pron;
        ArrayList<String> defi = dictWord.definitions;
        ArrayList<String> examplesOrig = dictWord.examplesOrig;
        ArrayList<String> examplesTrans = dictWord.examplesTrans;
        
        if (lingoesFont == null) {
            lingoesFont = Typeface.createFromAsset(activity.getAssets(), "lingoes.ttf");
        }
        
        Log.d("lia", "get_definition_view: " + word);
        //
        // parse word pron, definition and examples from json
        //

        
        //
        // create scroll view
        //
        ScrollView sv = new ScrollView(activity);
        
        //
        // create list layout
        //
        LinearLayout ll = new LinearLayout(activity);
        ll.setOrientation(LinearLayout.VERTICAL);
        ScrollView.LayoutParams lp = new ScrollView.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, 
            LinearLayout.LayoutParams.MATCH_PARENT
            );
        lp.setMargins(0, 0, 0, 0);
        lp.gravity = Gravity.FILL_HORIZONTAL;
        ll.setLayoutParams(lp);
        sv.addView(ll);
        
        
        //
        // set the word text view
        //
        TextView wordView = new TextView(activity);
        wordView.setText(word);
        wordView.setTextSize(28);
        wordView.setTypeface(null, Typeface.NORMAL);
        wordView.setTextColor(0xFF000088);
        ll.addView(wordView);
        
        //
        // set the pron text view
        //
        if (pron != null && pron.equals("null") == false && pron.equals("") == false) {
            TextView pronView = new TextView(activity);
            pronView.setText(String.format(" /%s/", pron));
            pronView.setTextSize(18);
            pronView.setTypeface(lingoesFont);
            pronView.setTextColor(0xFF000000);
            ll.addView(pronView);
        }
        
        if (onlyWord == true) {
            return sv;
        }
       
        
        //
        // set the definition indicator text view
        //
        if (defi != null) {
            TextView defIndicatorView = new TextView(activity);
            defIndicatorView.setText("释义");
            defIndicatorView.setTextSize(14);
            defIndicatorView.setTextColor(0xFF000000);
            defIndicatorView.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.definition_indicatoir));
            LinearLayout.LayoutParams lpIndic = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 
                LinearLayout.LayoutParams.WRAP_CONTENT
                );
            lpIndic.setMargins(0, 8, 0, 8);
            defIndicatorView.setLayoutParams(lpIndic);
            ll.addView(defIndicatorView);            
        }
        
        //
        // defi text view
        //
        if (defi != null && defi.size() != 0) {
            for (String defiText : defi) {
                TextView origView = new TextView(activity);
                origView.setText(defiText);
                origView.setTextSize(16);
                origView.setTextColor(0xFF000000);
                ll.addView(origView);                        
            }        
        }        

        if (examplesOrig.size() != 0) {
            TextView examplesView = new TextView(activity);
            examplesView.setText("例句");
            examplesView.setTextSize(14);
            examplesView.setTextColor(0xFF000000);
            examplesView.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.definition_indicatoir));
            LinearLayout.LayoutParams lpIndic = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 
                LinearLayout.LayoutParams.WRAP_CONTENT
                );
            lpIndic.setMargins(0, 16, 0, 8);
            examplesView.setLayoutParams(lpIndic);
            ll.addView(examplesView);    
        }
        
        //
        // Examples
        //
        for (int i = 0; i < examplesOrig.size(); ++i) {
            if (defi != null) {
                TextView origView = new TextView(activity);
                origView.setText(examplesOrig.get(i).replace("<em>", "").replace("</em>", ""));
                origView.setTextSize(16);
                origView.setTextColor(0xFF000000);
                ll.addView(origView);            
            }
            
            if (defi != null) {
                TextView transView = new TextView(activity);
                transView.setText(examplesTrans.get(i));
                transView.setTextSize(16);
                LinearLayout.LayoutParams lpTrans = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, 
                        LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                lpTrans .setMargins(0, 0, 0, 0);
                transView.setLayoutParams(lpTrans);
                transView.setTextColor(0xFF888888);
                ll.addView(transView);            
            }
        
        }
        
        return sv;
    }
}
