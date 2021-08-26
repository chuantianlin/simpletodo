package com.example.todo;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;


public class MainActivity extends AppCompatActivity {
    List<String>items;
    Button btnAdd;
    EditText etItem;
    RecyclerView rvItems;
    ItemAdapter itemAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAdd=findViewById(R.id.button3);
        etItem=findViewById(R.id.editTextTextPersonName);
        rvItems=findViewById(R.id.recycle);

        loadItems();

       ItemAdapter.OnLongClickListener onLongClickListener=new ItemAdapter.OnLongClickListener(){
            @Override
            public void onItemLongClicked(int position) {
                items.remove(position);
                itemAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(),"Items was  removed",Toast.LENGTH_SHORT).show();
                saveItems();

            }
        };
        itemAdapter = new ItemAdapter(items,onLongClickListener);
        rvItems.setAdapter(itemAdapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoItem=etItem.getText().toString();
                items.add(todoItem);
                itemAdapter.notifyItemInserted(items.size()-1);
                etItem.setText("");
                Toast.makeText(getApplicationContext(),"Items was add",Toast.LENGTH_SHORT).show();
                saveItems();


            }
        });

    }
    private File getDateFile(){
        return new File(getFilesDir(),"data.txt");

    }
    private void loadItems()  {


        try
        {
            items = new ArrayList<>(FileUtils.readLines(getDateFile(),Charset.defaultCharset()));

        }catch (IOException e){
            Log.e("MainActivity","Error reading message",e);
            items = new ArrayList<>();
        }


    }
      private void saveItems()
      {
           try
           {
               FileUtils.writeLines(getDateFile(),items);

           }catch (IOException e){
               Log.e("MainActivity","Error writing message",e);
           }

      }

}