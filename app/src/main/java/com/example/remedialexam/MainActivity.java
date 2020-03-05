package com.example.remedialexam;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerAdapter.OnNoteListener, ProductDialog.ProductDialogListener{
    private MyDB _db;
    private RecyclerView _rv;
    private ArrayList<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this._db = new MyDB(this);
        this._rv = findViewById(R.id.rv);
        setAdapter();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Do you want to create a new Product?", Snackbar.LENGTH_LONG)
                        .setAction("CREATE", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                createNewProduct();
                            }
                        }).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void setAdapter(){
        Cursor c = _db.selectRecords();
        products = new ArrayList<Product>();
        if (c.getCount() > 0) {
            do {
                products.add(new Product(c.getInt(0), c.getString(1), c.getInt(2)));
            } while (c.moveToNext());
        }
        RecyclerAdapter ra = new RecyclerAdapter(products, this, this, c);
        _rv.setAdapter(ra);
        _rv.setLayoutManager(new LinearLayoutManager(this));
    }
    public void editProduct(int pos){
        Intent i = new Intent(this,EditProduct.class);
        i.putExtra("id",products.get(pos).getId());
        i.putExtra("name",products.get(pos).getName());
        i.putExtra("count",products.get(pos).getCount());
        startActivityForResult(i,1);
    }
    public void deleteProduct(int pos){
        _db.deleteItem(pos);
        setAdapter();
        Toast.makeText(this,"ITEM DELETED",Toast.LENGTH_SHORT).show();
    }

    public void createNewProduct(){
        ProductDialog pd = new ProductDialog();
        pd.show(getSupportFragmentManager(),"dialog");

    }
    @Override
    public void onNoteClick(int position) {
        editProduct(position);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                _db.updateRecords(data.getIntExtra("id",0),data.getStringExtra("name"),data.getIntExtra("count",0));
                Toast.makeText(this,"Product Updated",Toast.LENGTH_SHORT).show();
                setAdapter();
            }
        }
    }

    @Override
    public void applyTexts(String name, int count) {
        _db.createRecords(name,count);
        Toast.makeText(this,"Product Created",Toast.LENGTH_SHORT).show();
        setAdapter();
    }
}
