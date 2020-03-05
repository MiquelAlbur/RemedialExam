package com.example.remedialexam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditProduct extends AppCompatActivity {

    private EditText ename, ecount;
    private String name;
    private int count;
    private Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        ename = findViewById(R.id.nameedit);
        ecount = findViewById(R.id.countedit);
        name = getIntent().getStringExtra("name");
        count = getIntent().getIntExtra("count", 0);
        ename.setText(name);
        ecount.setText(Integer.toString(count));
        b = findViewById(R.id.save);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }

    private void save() {
        name = ename.getText().toString();
        count = Integer.parseInt(ecount.getText().toString());
        if (name.equals("")) {
            Toast.makeText(this, "You have to type a name", Toast.LENGTH_SHORT).show();
        } else {
            Intent ri = new Intent();
            ri.putExtra("id",getIntent().getIntExtra("id",0));
            ri.putExtra("name", name);
            ri.putExtra("count", count);
            setResult(RESULT_OK, ri);
            finish();
        }
    }
}
