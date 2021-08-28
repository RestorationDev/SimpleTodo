package com.example.kkulkarni.simpletodo ;


import org.apache.commons.io.FileUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> items;
    Button btnAdd;
    EditText editTxt;
    RecyclerView rvItems;
    ItemAdapter itemsAdapter;
    private Log log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd=findViewById(R.id.btnAdd);
        editTxt=findViewById(R.id.editTxt);
        rvItems=findViewById(R.id.rvItems);




        loadItems();

        ItemAdapter.OnLongClickListener onLongClickListener = new ItemAdapter.OnLongClickListener(){
            @Override
            public void onItemLongClocked(int position) {
                //delete item from model, notify adapter
                items.remove(position);
                itemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(),"Item was removed",Toast.LENGTH_SHORT).show();
                saveItems();

            }
        };
        itemsAdapter = new ItemAdapter(items,onLongClickListener);
        rvItems.setAdapter(itemsAdapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String todoItem = editTxt.getText().toString();
                items.add(todoItem);
                itemsAdapter.notifyItemInserted(items.size()-1); //inserts at last element
                editTxt.setText(""); //clears the text
                Toast.makeText(getApplicationContext(),"Item was added",Toast.LENGTH_SHORT).show();
                saveItems();
            }
        });
    }

    private File getDataFile(){
        return new File(getFilesDir(), "data.txt");
    }

    // this method loads items by reading line by line
    private void loadItems(){
        try{
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        }catch(IOException e){
            e.printStackTrace();
            log.e("MainActivity","Error reading items",e);
            items = new ArrayList<>();
        }
    }
    // this method saves items by writing line by line
    private void saveItems(){
        try {
            FileUtils.writeLines(getDataFile(), items);
        }catch(IOException e){
        e.printStackTrace();
        log.e("MainActivity","Error writing items",e);
    }
    }

}