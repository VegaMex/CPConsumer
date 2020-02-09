package com.vegamex.cpconsumer;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateClass.class);
                startActivityForResult(intent, 1);
            }
        });

        lista = findViewById(R.id.lista);
        lista.setClickable(true);
        lista.setOnItemClickListener(this);

        Cursor cursor  =   getContentResolver().query(
                ContactCP.CONTENT_URI_CONTACTOS,
                ContactCP.PROJECTION_CONTACTOS,
                null, null,null);

        SimpleCursorAdapter simpleCursorAdapter =
                new SimpleCursorAdapter(
                        this,
                        android.R.layout.simple_list_item_2,
                        cursor,
                        new String[]{ContactCP.FIELD_USUARIO, ContactCP.FIELD_EMAIL},
                        new int[]{android.R.id.text1, android.R.id.text2
                        },
                        SimpleCursorAdapter.IGNORE_ITEM_VIEW_TYPE
                );
        lista.setAdapter(simpleCursorAdapter);
        //cursor.close();
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

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(parent == lista){
            Cursor cursor = (Cursor)lista.getItemAtPosition(position);
            String _id = cursor.getString( cursor.getColumnIndex("_id") );
            Cursor c2 = getContentResolver().query(ContactCP.CONTENT_URI_CONTACTOS, ContactCP.PROJECTION_CONTACTOS, "_id=?", new String[]{_id},null);
            Contacto contacto = null;
            if (c2.moveToFirst()) {
                do {
                    contacto =
                            new Contacto(c2.getInt(0), c2.getString(1),
                                    c2.getString(2), c2.getString(3), c2.getString(4));
                } while (c2.moveToNext());
            }
            c2.close();
            cursor.close();

            Intent intent = new Intent();
            Bundle bundle = new Bundle();

            bundle.putSerializable("contacto", contacto);
            intent.putExtras(bundle);
            intent.setClass(this, UpdateDeleteClass.class);
            startActivityForResult(intent, 10);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == MainActivity.RESULT_OK){
            Toast.makeText(this, "Operación realizada con éxito.", Toast.LENGTH_SHORT).show();
            refresh();
        }else{
            refresh();
        }
    }

    public void refresh(){
        Cursor cursor  =   getContentResolver().query(
                ContactCP.CONTENT_URI_CONTACTOS,
                ContactCP.PROJECTION_CONTACTOS,
                null, null,null);

        SimpleCursorAdapter simpleCursorAdapter =
                new SimpleCursorAdapter(
                        this,
                        android.R.layout.simple_list_item_2,
                        cursor,
                        new String[]{ContactCP.FIELD_USUARIO, ContactCP.FIELD_EMAIL},
                        new int[]{android.R.id.text1, android.R.id.text2
                        },
                        SimpleCursorAdapter.IGNORE_ITEM_VIEW_TYPE
                );
        lista.setAdapter(simpleCursorAdapter);
        //cursor.close();
    }
}
