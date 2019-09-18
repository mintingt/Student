package com.example.student;

import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText mEtuserName;
    private EditText mEtpassword;
    private CheckBox mCb_yh;
    private Button btnLogin,btnExit;

    Cursor cursor = getContentResolver().query(
            ContactsContract.CommonDataKinds.Contactables.Phone.CONTENT_URI,null
            null,null);
    contacts = new ArrayList<>();
    if(
            cursor != null && cursor.moveToFirst()){
        do{
            String name = cursor.getString(cursor.getColumnIndex(
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phone = cursor.getString(

            )
        }
    }
            )
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEtuserName=findViewById(R.id.et_username);
        mEtpassword=findViewById(R.id.et_password);
        mCb_yh=findViewById(R.id.cb_yh);
        btnLogin=findViewById(R.id.btn_login);
        btnExit=findViewById(R.id.btn_exit);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if("Duan".equals(mEtuserName.getText().toString()) && "123".equals(mEtpassword.getText().toString())) {

                    Intent intent = new Intent(MainActivity.this, DataStorageActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText ( MainActivity.this, "用户名或密码错误", Toast.LENGTH_LONG ).show ( );
                }
            }
        });
    }

}


