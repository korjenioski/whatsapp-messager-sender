package com.phaneronsoft.messagetest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.phaneronsoft.messagelib.MessageUtil;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Context mContext = this;
    EditText editTextNumber, editText;
    Button buttonSend;
    AlertDialog alertDialogType;
    int selectedType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        editTextNumber = findViewById(R.id.editTextNumber);
        editText = findViewById(R.id.editTextMessage);
        buttonSend = findViewById(R.id.buttonSend);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // block to open only one dialog
                    if (alertDialogType != null && alertDialogType.isShowing()) {
                        return;
                    }

                    AlertDialog.Builder adb = new AlertDialog.Builder(mContext);
                    // Load contact string list
                    List<String> contactList = Arrays.asList(getResources().getStringArray(R.array.contact_array));
                    // List to charsequence
                    final CharSequence[] items = contactList.toArray(new CharSequence[contactList.size()]);
                    adb.setSingleChoiceItems(items, selectedType, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface d, int n) {
                            selectedType = n;
                        }
                    });
                    adb.setPositiveButton(getString(R.string.btn_confirm), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                switch (selectedType) {
                                    case 0:
                                        MessageUtil.send(mContext, editText.getText().toString(), editTextNumber.getText().toString(), MessageUtil.App.WHATSAPP);
                                        break;

                                    case 1:
                                        MessageUtil.send(mContext, editTextNumber.getText().toString(), MessageUtil.App.MESSENGER);
                                        break;
                                }
                                alertDialogType.dismiss();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    adb.setNegativeButton(getString(R.string.btn_cancel), null);
                    adb.setTitle(getString(R.string.title_which_one));
                    alertDialogType = adb.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}