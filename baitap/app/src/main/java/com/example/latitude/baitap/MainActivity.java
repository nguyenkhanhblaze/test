package com.example.latitude.baitap;

import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listView;
    TextView tvPath;
    String mPath;
    ArrayList<String> mFileNames;
    ArrayList<String> mFilePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        mPath= Environment.getDataDirectory().getPath();
        tvPath=(TextView)findViewById(R.id.textPath);
        getDirectory(mPath);
    }

    public void getDirectory(String pathDir){
        tvPath.setText(pathDir);
        mFileNames= new ArrayList<>();
        mFilePath=new ArrayList<>();
        File dir=new File(pathDir);
        mFileNames.add("../");
        mFilePath.add(dir.getParent());
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (int i = 0; i < files.length; i++) {
                mFileNames.add(files[i].getName());
                mFilePath.add(files[i].getAbsolutePath());
            }
        }
        ArrayAdapter<String> adapter= new ArrayAdapter<>(this,android.R.layout.simple_expandable_list_item_1,mFileNames);
        listView.setAdapter(adapter);
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        File file= new File(mFilePath.get(i));
        if(file.isDirectory()&& !file.isHidden()&&file.canRead()){
            getDirectory(file.getAbsolutePath());
        }
          else{
            if(file.getAbsoluteFile().contains(".jpg")){
                String[] files= file.getParentFile().list(new ExtensionsNameFilter(ExtensionsNameFilter.IMAGE_FILTER));
                Intent intent = new Intent(this, ActivityGalleryScreen.class);
                intent.putExtra("listImage",files);
                intent.putExtra("parent",file.getParent()+"/");
                startActivity(intent);
            } else
                Toast.makeText(this,file.getName(),Toast.LENGTH_SHORT).show();
        }
    }
}
