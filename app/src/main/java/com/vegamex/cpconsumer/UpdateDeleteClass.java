package com.vegamex.cpconsumer;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class UpdateDeleteClass extends AppCompatActivity implements View.OnClickListener {

    private Button btnEliminarUD, btnEditarUD, btnFechaUD;
    private TextView txtFechaUD;
    private EditText txtUsuarioUD, txtEmailUD, txtTelefonoUD;

    private static final String CERO = "0";
    private static final String BARRA = "/";
    public final Calendar calendar = Calendar.getInstance();
    final int mes = calendar.get(Calendar.MONTH);
    final int dia = calendar.get(Calendar.DAY_OF_MONTH);
    final int año = calendar.get(Calendar.YEAR);

    private int idActual;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_delete_main);

        btnFechaUD = findViewById(R.id.btnFechaUD);
        btnEditarUD = findViewById(R.id.btnEditarUD);
        btnEliminarUD = findViewById(R.id.btnEliminarUD);
        txtFechaUD = findViewById(R.id.txtFechaUD);
        txtUsuarioUD = findViewById(R.id.txtUsuarioUD);
        txtEmailUD = findViewById(R.id.txtEmailUD);
        txtTelefonoUD = findViewById(R.id.txtTelefonoUD);

        btnFechaUD.setOnClickListener(this);
        btnEliminarUD.setOnClickListener(this);
        btnEditarUD.setOnClickListener(this);

        Bundle b = this.getIntent().getExtras();
        if (b != null){
            Contacto contacto = (Contacto)b.getSerializable("contacto");
            txtUsuarioUD.setText(contacto.getUsuario());
            txtEmailUD.setText(contacto.getEmail());
            txtTelefonoUD.setText(contacto.getTelefono());
            txtFechaUD.setText(contacto.getFechaNacimiento());
            idActual = contacto.id;
        }else{
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btnFechaUD:
                obtenerFecha();
                break;
            case R.id.btnEditarUD:
                modificar();
                break;
            case R.id.btnEliminarUD:
                Snackbar.make(view, "¿Eliminar?", Snackbar.LENGTH_LONG)
                        .setActionTextColor(Color.WHITE)
                        .setAction("Sí", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                eliminar();
                            }
                        })
                        .show();
                break;
        }
    }

    public void eliminar(){
        Intent intent = new Intent(UpdateDeleteClass.this, MainActivity.class);
        try {
            getContentResolver().delete(ContactCP.CONTENT_URI_CONTACTOS,"_id = ?", new String[]{String.valueOf(idActual)});
            setResult(RESULT_OK, null);
            finish();
        }catch (Exception ex){
            setResult(RESULT_CANCELED, null);
            finish();
        }
    }

    public void modificar(){
        ContentValues contentValues = new ContentValues();

        contentValues.put("_usuario", txtUsuarioUD.getText().toString());
        contentValues.put("_email", txtEmailUD.getText().toString());
        contentValues.put("_telefono", txtTelefonoUD.getText().toString());
        contentValues.put("_fechaNacimiento", txtFechaUD.getText().toString());

        try {
            getContentResolver().update(ContactCP.CONTENT_URI_CONTACTOS, contentValues, "_id = ?", new String[]{String.valueOf(idActual)});
            setResult(RESULT_OK, null);
            finish();
        }catch (Exception ex){
            setResult(RESULT_CANCELED, null);
            finish();
        }
    }

    public void obtenerFecha(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                //Muestro la fecha con el formato deseado
                txtFechaUD.setText(year + BARRA + mesFormateado + BARRA + diaFormateado);
            }
        }, año, mes, dia);
        datePickerDialog.show();
    }
}