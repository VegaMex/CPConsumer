package com.vegamex.cpconsumer;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
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

import java.util.Calendar;

public class CreateClass extends AppCompatActivity implements View.OnClickListener {

    private Button btnFechaCreate, btnCancelarCreate, btnAgregarCreate;
    private TextView txtFechaCreate;
    private EditText txtUsuarioCreate, txtEmailCreate, txtTelefonoCreate;

    private static final String CERO = "0";
    private static final String BARRA = "/";
    public final Calendar calendar = Calendar.getInstance();
    final int mes = calendar.get(Calendar.MONTH);
    final int dia = calendar.get(Calendar.DAY_OF_MONTH);
    final int año = calendar.get(Calendar.YEAR);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_main);

        btnFechaCreate = findViewById(R.id.btnFechaCreate);
        btnAgregarCreate = findViewById(R.id.btnAgregarCreate);
        btnCancelarCreate = findViewById(R.id.btnCancelarCreate);
        txtFechaCreate = findViewById(R.id.txtFechaCreate);
        txtUsuarioCreate = findViewById(R.id.txtUsuarioCreate);
        txtEmailCreate = findViewById(R.id.txtEmailCreate);
        txtTelefonoCreate = findViewById(R.id.txtTelefonoCreate);

        btnFechaCreate.setOnClickListener(this);
        btnAgregarCreate.setOnClickListener(this);
        btnCancelarCreate.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnFechaCreate:
                obtenerFecha();
                break;
            case R.id.btnAgregarCreate:
                Intent intent = new Intent(CreateClass.this, MainActivity.class);
                try {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(ContactCP.PROJECTION_CONTACTOS[1],
                            txtUsuarioCreate.getText().toString());
                    contentValues.put(ContactCP.PROJECTION_CONTACTOS[2],
                            txtEmailCreate.getText().toString());
                    contentValues.put(ContactCP.PROJECTION_CONTACTOS[3],
                            txtTelefonoCreate.getText().toString());
                    contentValues.put(ContactCP.PROJECTION_CONTACTOS[4],
                            txtFechaCreate.getText().toString());
                    Uri miuri =  getContentResolver().insert(
                            ContactCP.CONTENT_URI_CONTACTOS,
                            contentValues);
                    Log.d("MIURI", miuri.toString());
                    setResult(RESULT_OK, null);
                    finish();
                }catch (Exception ex){
                    setResult(RESULT_CANCELED, null);
                    finish();
                }
                break;
            case R.id.btnCancelarCreate:
                finish();
                break;
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
                txtFechaCreate.setText(year + BARRA + mesFormateado + BARRA + diaFormateado);
            }
        }, año, mes, dia);
        datePickerDialog.show();
    }
}