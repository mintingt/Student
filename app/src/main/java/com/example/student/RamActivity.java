package com.example.student;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class RamActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ram);

        imageView = findViewById(R.id.img_1);
        Button btn_red = findViewById(R.id.btn_red);
        Button btn_wri = findViewById(R.id.btn_wri);

        btn_red.setOnClickListener(this);
        btn_wri.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_wri:
                saveToSD("wan.jpg");
                break;
            case R.id.btn_red:
                readToSD("比心.jpg");
                break;
        }

    }

    private void saveToSD(String filename){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            if(ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                return;
            }
        }
        //写SD卡的步骤
        //1 获取SD的Download目录，创建需要存储的文件
        String path = Environment.getExternalStoragePublicDirectory("").getPath()
                + File.separator
                +Environment.DIRECTORY_PICTURES;
        File file = new File(path, filename);

        try {
            if(file.createNewFile()){
                //2 获取ImageView的图片对象
                BitmapDrawable drawable = (BitmapDrawable)imageView.getDrawable();
                Bitmap bitmap = drawable.getBitmap();

                //3 将Bitmap对象写入SD卡
                FileOutputStream outputStream  = new FileOutputStream(file);
                //带缓冲
                //BufferedOutputStream bufferedOutputStream(outputStream);
                bitmap.compress(Bitmap.CompressFormat.JPEG,100, outputStream);
                //4 关闭请求的资源
                outputStream.flush();
                outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void readToSD(String filename){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            if(ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},2);
                return;
            }
        }
        //写SD卡的步骤
        //1 获取SD的Download目录，创建需要存储的文件
        String path = Environment.getExternalStoragePublicDirectory("").getPath()
                + File.separator
                +Environment.DIRECTORY_PICTURES;
        File file = new File(path, filename);

        try {

                  FileInputStream inputStream = new FileInputStream(file);
                //3 将文件流写入imageview
                imageView.setImageBitmap(BitmapFactory.decodeStream(inputStream));
                //4 关闭输入流
                inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED){

            Toast.makeText(this,"权限申请 被拒绝",Toast.LENGTH_SHORT).show();
            return;

        }
        switch (requestCode){
            case 1:
                saveToSD("wan.jpg");
                break;
            case 2:
                readToSD("比心.jpg");
                break;
        }

    }
}
