package com.example.ap_listar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper
{
//     Constructor de la clase
    public DBHelper(@Nullable Context context)
    {
//     Invocar al constructor de la clase padre
        super(context, "DBHelper.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        Tabla roles
        db.execSQL("CREATE TABLE roles (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT UNIQUE NOT NULL," +
                "descripcion TEXT)");

//        Nueva Tabla Usuarios
        db.execSQL("CREATE TABLE usuarios (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT," +
                "apellido TEXT," +
                "correo TEXT UNIQUE," +
                "contrasena TEXT," +
                "telefono TEXT," +
                "fecha_nac TEXT," +
                "verificado INTEGER DEFAULT 0," +
                "id_rol INTEGER," +
                "FOREIGN KEY (id_rol) REFERENCES roles (id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        db.execSQL("DROP TABLE IF EXISTS roles");
        onCreate(db);
    }

    public boolean insertarRol(String nombre,String descripcion)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("descripcion", descripcion);

        long resultado = db.insert("roles", null, values);
        return resultado != -1;
    }

    public boolean insertarRol_Clase(Rol rol)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", rol.getNombre());
        values.put("descripcion", rol.getDescripcion());

        long resultado = db.insert("roles", null, values);
        return resultado != -1;
    }
    public ArrayList<Rol> obtenerRoles()
    {
        ArrayList<Rol> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM roles", null);
        if(cursor.moveToFirst())
        {
            do{
                Rol rol = new Rol();
                rol.setId(cursor.getInt(0));
                rol.setNombre(cursor.getString(1));
                rol.setDescripcion(cursor.getString(2));
                lista.add(rol);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return lista;
    }
}
