package com.example.simpledatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;

import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private EditText nrp, nama;
    private Button simpan, ambildata, update, delete;
    private SQLiteDatabase dbku;
    private SQLiteOpenHelper Opendb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nrp = findViewById(R.id.nrp);
        nama = findViewById(R.id.nama);
        simpan = findViewById(R.id.Simpan);
        ambildata = findViewById(R.id.ambildata);
        update = findViewById(R.id.update);
        delete = findViewById(R.id.delete);

        simpan.setOnClickListener(operasi);
        ambildata.setOnClickListener(operasi);
        update.setOnClickListener(operasi);
        delete.setOnClickListener(operasi);

        Opendb = new SQLiteOpenHelper(this, "db.sql", null, 1) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                db.execSQL("create table if not exists mhs(nrp TEXT, nama TEXT);");
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                db.execSQL("DROP TABLE IF EXISTS mhs");
                onCreate(db);
            }
        };
        dbku = Opendb.getWritableDatabase();
    }

    @Override
    protected void onStop() {
        dbku.close();
        Opendb.close();
        super.onStop();
    }

    View.OnClickListener operasi = v -> {
        int id = v.getId();
        if (id == R.id.Simpan) {
            simpan();
        } else if (id == R.id.ambildata) {
            ambildata();
        } else if (id == R.id.update) {
            update();
        } else if (id == R.id.delete) {
            delete();
        }
    };

    private void simpan() {
        ContentValues dataku = new ContentValues();
        dataku.put("nrp", nrp.getText().toString());
        dataku.put("nama", nama.getText().toString());
        dbku.insert("mhs", null, dataku);
        Toast.makeText(this, "Data Tersimpan", Toast.LENGTH_LONG).show();
        Log.d("MainActivity", "Data Tersimpan");
    }

    private void ambildata() {
        Cursor cur = dbku.rawQuery("select * from mhs where nrp='" + nrp.getText().toString() + "'", null);

        if (cur.getCount() > 0) {
            Toast.makeText(this, "Data Ditemukan Sejumlah " + cur.getCount(), Toast.LENGTH_LONG).show();
            cur.moveToFirst();
            nama.setText(cur.getString(cur.getColumnIndex("nama")));
        } else
            Toast.makeText(this, "Data Tidak Ditemukan", Toast.LENGTH_LONG).show();

        cur.close();
    }

    private void update() {
        ContentValues dataku = new ContentValues();
        dataku.put("nama", nama.getText().toString());
        dbku.update("mhs", dataku, "nrp=?", new String[]{nrp.getText().toString()});
        Toast.makeText(this, "Data Terupdate", Toast.LENGTH_LONG).show();
    }

    private void delete() {
        dbku.delete("mhs", "nrp=?", new String[]{nrp.getText().toString()});
        Toast.makeText(this, "Data Terhapus", Toast.LENGTH_LONG).show();
    }
}
