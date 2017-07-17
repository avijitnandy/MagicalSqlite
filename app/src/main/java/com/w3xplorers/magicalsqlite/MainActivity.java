package com.w3xplorers.magicalsqlite;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editTextName,editTextSurname,editTextMarks,editTextId;
    Button addBtn,viewAll,updateBtn,deleteBtn;

    public void intialWork(){
        editTextName = (EditText) findViewById(R.id.editText_name);
        editTextSurname = (EditText) findViewById(R.id.editText_surname);
        editTextMarks = (EditText) findViewById(R.id.editText_marks);
        editTextId = (EditText) findViewById(R.id.editText_id);
        addBtn = (Button) findViewById(R.id.btnAdd);
        viewAll = (Button) findViewById(R.id.btnAll);
        updateBtn = (Button) findViewById(R.id.btnUpdate);
        deleteBtn = (Button) findViewById(R.id.btnDelete);


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDb.insertData(editTextName.getText().toString(),
                        editTextSurname.getText().toString(),editTextMarks.getText().toString());
                if(isInserted){
                    Toast.makeText(MainActivity.this,"Data inserted",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,"Data did not insert",Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDb.getAllData();

                if(res.getCount()==0){

                    //show message

                    showMsg("Error","No data found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();

                while (res.moveToNext()){
                    buffer.append("Id: "+res.getString(0)+"\n");
                    buffer.append("Name: "+res.getString(1)+"\n");
                    buffer.append("Surname: "+res.getString(2)+"\n");
                    buffer.append("Marks: "+res.getString(3)+"\n\n");
                }

                //show all data
                showMsg("Data",buffer.toString());
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdated = myDb.updateData(editTextId.getText().toString(),
                        editTextName.getText().toString(),editTextSurname.getText().toString(),editTextMarks.getText().toString());
                if(isUpdated){
                    Toast.makeText(MainActivity.this,"Data updated",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,"Data did not update",Toast.LENGTH_SHORT).show();
                }
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deletedRows = myDb.deleteData(editTextId.getText().toString());

                if(deletedRows > 0){
                    Toast.makeText(MainActivity.this,"Data deleted",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,"Data not deleted",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void showMsg(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);

        intialWork();
    }
}
