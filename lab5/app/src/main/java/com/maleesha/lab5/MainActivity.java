package com.maleesha.lab5;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

// Import Files
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Create database object
    DatabaseHelper myDb;
    EditText editTitle, editDate, editDescription, updateTasks;
    Button btnAddData, btnViewAll, btnUpdateData, btnDeleteData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        editTitle = findViewById(R.id.textInputEditText1);
        editDate = findViewById(R.id.textInputEditText2);
        editDescription = findViewById(R.id.textInputEditText3);
        updateTasks = findViewById(R.id.textInputEditText4);
        btnAddData = findViewById(R.id.addDataButton);
        btnViewAll = findViewById(R.id.viewDataButton);
        btnUpdateData = findViewById(R.id.updateDataButton);
        btnDeleteData = findViewById(R.id.deleteDataButton);

        // Calling addData Method
        addData();

        // Calling View All method
        viewAll();

        // Calling update data method
        updateData();

        // Calling Delete data method
        deleteData();
    }

    //  Add Data Method - Add tasks to the database
    public void addData(){
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertData(editTitle.getText().toString(),editDate.getText().toString(),
                                editDescription.getText().toString());
                        if (isInserted == true)
                            Toast.makeText(MainActivity.this,"New Task Added Successfully!",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Task Not Added",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
    
    // View Data Method - View all tasks in database
    public void viewAll(){
        btnViewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor results = myDb.getAllData();
                        if (results.getCount()==0){
                            showMessage("Error Message","No Data Found");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while(results.moveToNext()){
                            buffer.append("ID :" + results.getString(0) + "\n");
                            buffer.append("Title :" + results.getString(1) + "\n");
                            buffer.append("Date :" + results.getString(2) + "\n");
                            buffer.append("Description :" + results.getString(3) + "\n\n");

                            showMessage("My Tasks List :", buffer.toString());
                        }

                    }

                }
        );
    }

    private void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    // Implement update method - Update task in database using ID
    public void updateData(){
        btnUpdateData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = myDb.updateData(updateTasks.getText().toString(),editTitle.getText().toString(),
                                editDate.getText().toString(),editDescription.getText().toString());
                        if (isUpdate == true)
                            Toast.makeText(MainActivity.this,"Task Updated Successfully!",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Task Not Updated",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    // Implement Delete Method - Delete task in database using ID
    public void deleteData(){
        btnDeleteData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedatarows = myDb.deleteData(updateTasks.getText().toString());
                        if (deletedatarows>0)
                            Toast.makeText(MainActivity.this,"Task Deleted Successfully!",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Task Not Deleted",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
    
}