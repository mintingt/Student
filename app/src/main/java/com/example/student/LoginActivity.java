package com.example.student;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class LoginActivity extends AppCompatActivity {
    //1. 定义控件对象
    private EditText etuserName;
    private EditText etpassword;
    private CheckBox mCb_yh;
    private Button btnLogin,btnExit;
    private String fileName = "login.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //2.获取控件对象
        etuserName=findViewById(R.id.et_username);
        etpassword=findViewById(R.id.et_password);
        mCb_yh=findViewById(R.id.cb_yh);
        btnLogin=findViewById(R.id.btn_login);
        btnExit=findViewById(R.id.btn_exit);
        //3.获取存储的用户信息，若有则写入
       // String username = readPrfs();
        //String username = readDataInternal(fileName);
        String username = readPrivateExStorage(fileName);

        if(username!=null){
            etuserName.setText(username);
        }

        //4.设置登录 按钮的点击事件的监听器

        //5.处理点击事件
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //5.1
                String username = etuserName.getText().toString();
                String password = etpassword.getText().toString();
                //5.2 判断“记住用户名”是否勾选，若已选则存储用户名，未选则清空存储的用
                if(mCb_yh.isChecked()){
                    savePref(username);
                    saveData(fileName,username);//写

                }else {
                    clearpref();
                }
                if("xuan".equals(username) && "123".equals(password)) {

                    Intent intent = new Intent(LoginActivity.this, RamActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText ( LoginActivity.this, "用户名或密码错误", Toast.LENGTH_LONG ).show ( );
                }
            }
        });


    }

    // 删除外部私有存储的文件
    private void deletePrivateExStorage(String fileName) {
        File file = new File(getExternalFilesDir(""), fileName);
        if (file.isFile()) {
            if (file.delete()) {
                Toast.makeText(this, "删除外部公有文件成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "删除外部公有文件失败", Toast.LENGTH_SHORT).show();
            }
        }
    }
    //2.内部存储
    //保存
    private void saveData(String fileName, String username) {
        //内部存储目录；data/data/包名/files/
        try {
            //1.打开文件输出流
            FileOutputStream out = openFileOutput(fileName,MODE_PRIVATE);
            //2.创建BufferedWriter对象
            BufferedWriter Writer = new BufferedWriter(new OutputStreamWriter(out));
            //3.写入数据
            Writer.write(username);
            //4.关闭输出流
            Writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    //读取
    private String readPrivateExStorage(String fileName){
        //存储东西:data
        String data = null;
        try {
            //1.打开文件读取流
            FileInputStream in = openFileInput(fileName);
            //2.创建BufferedReader对象
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            //读取数据
            data = reader.readLine();
            //4.关闭读取流
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //返回读取的数据
        return data;
    }
    //外部写
    private void saveDataPrivate(String fileName, String username) {
        //外部存储目录；data/data/包名/files/
        try {
            //1.打开文件输出流
            File file = new File(getExternalFilesDir(""),fileName);
           // FileOutputStream out = openFileOutput(fileName,MODE_PRIVATE);
            FileOutputStream out = new FileOutputStream(file);
            //2.创建BufferedWriter对象
            BufferedWriter Writer = new BufferedWriter(new OutputStreamWriter(out));
            //3.写入数据
            Writer.write(username);
            //4.关闭输出流
            Writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    //内部读取
    private String readDataInternalprivate(String fileName){
        //存储东西:data
        String data = null;
        try {
            //1.打开文件读取流
           // FileInputStream in = openFileInput(fileName);
            File file = new File(getExternalFilesDir(""),fileName);
            FileInputStream in =new FileInputStream(file);
            //2.创建BufferedReader对象
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            //读取数据
            data = reader.readLine();
            //4.关闭读取流
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //返回读取的数据
        return data;
    }
    // 删除内部存储文件
    private void deleteDataFile(String fileName) {
        if (deleteFile(fileName)) {
            Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
        }
    }

    private void savePref(String username) {
        SharedPreferences.Editor editor = getSharedPreferences("userInfo",
                MODE_PRIVATE).edit();
        editor.putString("username",username);
        editor.apply();
    }
    private void clearpref() {
        SharedPreferences.Editor editor = getSharedPreferences("userInfo",
                MODE_PRIVATE).edit();

        editor.clear().apply();
    }

    private String readPrfs() {
        SharedPreferences sp = getSharedPreferences("userInfo", MODE_PRIVATE);

        return sp.getString("username","");
    }

    private class FileInputStram {
    }
}
