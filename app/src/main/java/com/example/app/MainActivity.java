package com.example.app;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
int version = 0;
Button button;

EditText editText;
ListView listView;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        button=findViewById(R.id.button);
        editText=findViewById(R.id.editText);
        listView=findViewById(R.id.listView);

        listView.setOnItemClickListener((adapterView, view, i, l) ->{
            Toast.makeText(getApplicationContext(),"asfd",Toast.LENGTH_LONG).show();

        });

        button.setOnClickListener((v) -> {
            String txt = editText.getText().toString();
            ContentValues cv = new ContentValues();
            cv.put("titulo", txt);
           int status =(int)db.insert("notas",null,cv);

            String msgStatus;
            if (status > 0) {
                msgStatus="Nota Inserida"+status;
                editText.setText("");
                editText.requestFocus();
                listarNotas();
            }else{
                msgStatus="Falha na inserção da nota";
            }
        });

        setup();

    }
    public void setup(){
        db=openOrCreateDatabase("bdnotas",MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS notas (id INTEGER PRIMARY KEY AUTOINCREMENT, titulo VARCHAR, txt VARCHAR)");

        listarNotas();
    }

    public void listarNotas(){
        Cursor cursor=db.rawQuery("SELECT * FROM notas LIMIT ?", new String [] {"100"});
        cursor.moveToFirst();
        ArrayList<String> notas =new ArrayList<>();

        while(!cursor.isAfterLast()){

            String s=cursor.getString((int)cursor.getColumnIndex("titulo"));
            notas.add(s);
            cursor.moveToNext();
        }

        //listagem com listview
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getApplicationContext(), android.R.layout.simple_list_item_1,
                android.R.id.text1,
                notas
        );

        listView.setAdapter(adapter);
    }
    }