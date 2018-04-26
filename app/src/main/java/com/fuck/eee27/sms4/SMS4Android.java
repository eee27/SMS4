package com.fuck.eee27.sms4;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;

public class SMS4Android extends AppCompatActivity {
    
    
    CheckBox testChk = null;
    CheckBox timeChk = null;
    EditText inputText = null;
    EditText inputTimeText = null;
    EditText outputText = null;
    EditText outputTimeText = null;
    Button encodeBtn = null;
    Button decodeBtn = null;
    Button copyBtn = null;
    Button copyTimeBtn = null;
    
    TextInputLayout inputTextLayout = null;
    TextInputLayout inputTimeTextLayout = null;
    TextInputLayout outputTextLayout = null;
    TextInputLayout outputTimeTextLayout = null;
    
    private static boolean isTestKey = false;
    private static boolean isTimeKey = false;
    
    private ClipboardManager clipboardManager;
    private ClipData clipData;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
    
        InitAllItem();
        BindAllAction();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getOrder()) {
            case 100:
                Intent intent = new Intent(SMS4Android.this, SettingActivity.class);
                startActivity(intent);
                break;
            case 101:
                Toast.makeText(this, "1.0", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    
    
    private boolean InitAllItem() {
        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        
        testChk = (CheckBox) findViewById(R.id.testKeyChk);
        timeChk = (CheckBox) findViewById(R.id.timeKeyChk);
        inputText = (EditText) findViewById(R.id.inputText);
        inputTimeText = (EditText) findViewById(R.id.inputTimeText);
        outputText = (EditText) findViewById(R.id.outputText);
        outputTimeText = (EditText) findViewById(R.id.outputTimeText);
        encodeBtn = (Button) findViewById(R.id.enBtn);
        decodeBtn = (Button) findViewById(R.id.deBtn);
        copyBtn = (Button) findViewById(R.id.copyBtn);
        copyTimeBtn = (Button) findViewById(R.id.copyTimeBtn);
        
        inputTextLayout = (TextInputLayout) findViewById(R.id.text_input_layout_input);
        inputTimeTextLayout = (TextInputLayout) findViewById(R.id.text_input_layout_inputTime);
        outputTextLayout = (TextInputLayout) findViewById(R.id.text_input_layout_output);
        outputTimeTextLayout = (TextInputLayout) findViewById(R.id.text_input_layout_outputTime);
        
        /*
        inputTextLayout.setCounterEnabled(true);
        inputTextLayout.setCounterMaxLength(16);
        inputTimeTextLayout.setCounterEnabled(true);
        inputTimeTextLayout.setCounterMaxLength(16);
        
        outputTextLayout.setCounterEnabled(true);
        outputTextLayout.setCounterMaxLength(32);
        outputTimeTextLayout.setCounterEnabled(true);
        outputTimeTextLayout.setCounterMaxLength(32);
        */
        
        inputTimeText.setEnabled(false);
        outputTimeText.setEnabled(false);
        
        return true;
        
    }
    
    private boolean BindAllAction() {
        testChk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    isTestKey = true;
                    
                    timeChk.setEnabled(false);
                } else {
                    isTestKey = false;
                    
                    timeChk.setEnabled(true);
                }
            }
        });
        timeChk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    isTimeKey = true;
                    inputTimeText.setEnabled(true);
                    outputTimeText.setEnabled(true);
                    copyTimeBtn.setEnabled(true);
                    
                    testChk.setEnabled(false);
                } else {
                    isTimeKey = false;
                    inputTimeText.setEnabled(false);
                    outputTimeText.setEnabled(false);
                    copyTimeBtn.setEnabled(false);
                    inputTimeText.setText("");
                    outputTimeText.setText("");
                    
                    testChk.setEnabled(true);
                }
            }
        });
        encodeBtn.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             SMS4Encode();
                                         }
                                     }
        
        );
        
        decodeBtn.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             SMS4Decode();
                                         }
                                     }
        
        );
        
        copyBtn.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           CopyToClip();
                                       }
                                   }
        
        );
        
        copyTimeBtn.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {
                                               CopyTimeToClip();
                                           }
                                       }
        
        );
        return true;
        
    }
    
    private void SMS4Encode() {
        SMS4 sms4 = new SMS4();
        String result = "";
        if (isTestKey) {
            try {
                sms4.globalKey = "stemsoft12345678".getBytes("utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            result = SMS4.encodeSMS4toString(inputText.getText().toString());
        } else if (isTimeKey) {
            result = SMS4.encodeSMS4toStringWithMD5Time(inputText.getText().toString());
        } else {
            try {
                sms4.globalKey = "7ujm^YHN5tgb$RFV".getBytes("utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            result = SMS4.encodeSMS4toString(inputText.getText().toString());
        }
        
        if (isTimeKey) {
            String[] args = result.split(":");
            outputText.setText(args[1]);
            outputTimeText.setText(args[0]);
        } else {
            outputText.setText(result);
        }
        Toast.makeText(this, "Encode Done!", Toast.LENGTH_SHORT).show();
    }
    
    private void SMS4Decode() {
        SMS4 sms4 = new SMS4();
        String result = "";
        if (isTestKey) {
            try {
                sms4.globalKey = "stemsoft12345678".getBytes("utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            result = SMS4.decodeSMS4toString(inputText.getText().toString());
        } else if (isTimeKey) {
            result = SMS4.decodeSMS4toStringWithMD5Time(inputTimeText.getText().toString(), inputText.getText().toString());
        } else {
            try {
                sms4.globalKey = "7ujm^YHN5tgb$RFV".getBytes("utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            result = SMS4.decodeSMS4toString(inputText.getText().toString());
        }
        
        if (isTimeKey) {
            String[] args = result.split(":");
            outputText.setText(args[1]);
            outputTimeText.setText(args[0]);
        } else {
            outputText.setText(result);
        }
        Toast.makeText(this, "Decode Done!", Toast.LENGTH_SHORT).show();
    }
    
    private void CopyToClip() {
        clipData = ClipData.newPlainText("text", outputText.getText().toString());
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(this, "Result Already Copy!", Toast.LENGTH_SHORT).show();
    }
    
    private void CopyTimeToClip() {
        clipData = ClipData.newPlainText("text", outputTimeText.getText().toString());
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(this, "Time Key Already Copy!", Toast.LENGTH_SHORT).show();
    }
}
