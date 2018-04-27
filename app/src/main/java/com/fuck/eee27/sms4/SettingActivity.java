package com.fuck.eee27.sms4;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.Switch;

public class SettingActivity extends AppCompatActivity {
    
    Switch darkThemeSwc = null;
    Switch englishSwc = null;
    
    Resources resources = null;
    DisplayMetrics dm = null;
    Configuration config = null;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    
        InitAllItem();
        BindAllAction();
    
    
    }
    
    
    private boolean InitAllItem() {
        darkThemeSwc = (Switch) findViewById(R.id.switch_theme);
        englishSwc = (Switch) findViewById(R.id.switch_english);
        
        resources = this.getApplicationContext().getResources();
        dm = resources.getDisplayMetrics();
        config = resources.getConfiguration();
        
        
        return true;
    }
    
    private boolean BindAllAction() {
        
        
        return true;
    }
    
}
