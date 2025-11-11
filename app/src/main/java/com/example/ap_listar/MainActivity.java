package com.example.ap_listar;

import android.os.Bundle;
import android.view.View;
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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText edtNombreRol, edtDescripcionRol;
    Button btnGuardarRol;
    ListView lvRoles;
    DBHelper db;
    ArrayAdapter<Rol> adapter;
    ArrayList<Rol> listaRoles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        edtNombreRol = findViewById(R.id.edtNombreRol);
        edtDescripcionRol = findViewById(R.id.edtDescripcionRol);
        btnGuardarRol = findViewById(R.id.btnGuardarRol);
        lvRoles = findViewById(R.id.lvRoles);
        db = new DBHelper(this);
        cargarRoles();
        btnGuardarRol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = edtNombreRol.getText().toString().trim();
                String descripcion = edtDescripcionRol.getText().toString().trim();
                if (nombre.isEmpty()){
                    Toast.makeText(MainActivity.this, "El nombre es obligatorio", Toast.LENGTH_SHORT);
                    return;
                }

                Rol nuevoRol = new Rol(0, nombre, descripcion);
//                nuevoRol.setNombre(nombre);
//                nuevoRol.setDescripcion(descripcion);

                boolean exito = db.insertarRol_Clase(nuevoRol);

                if (exito){
                    Toast.makeText(MainActivity.this, "Rol Guardado", Toast.LENGTH_SHORT);
                    edtNombreRol.setText("");
                    edtDescripcionRol.setText("");
                    cargarRoles();
                } else {
                    Toast.makeText(MainActivity.this, "Error al guardar", Toast.LENGTH_SHORT);
                }
            }
        });
    }

    private void cargarRoles() {
        listaRoles = db.obtenerRoles();
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                listaRoles);
        lvRoles.setAdapter(adapter);
    }
}