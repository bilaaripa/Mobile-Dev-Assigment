package com.example.input_listview;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ListView;
import android.view.View;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.widget.EditText;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {
    private ListView lv;
    private ImageView add;
    private kontakAdapter kAdapter;
    private SQLiteDatabase dbku;
    private SQLiteOpenHelper dbopen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = findViewById(R.id.listView);
        add = findViewById(R.id.add);

        ArrayList<kontak> listKontak = new ArrayList<>();
        kAdapter = new kontakAdapter(this, 0, listKontak);
        lv.setAdapter(kAdapter);

        dbopen = new SQLiteOpenHelper(this, "kontak.db", null, 1) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                db.execSQL("CREATE TABLE IF NOT EXISTS kontak(nama TEXT, nohp TEXT);");
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                // Code for database upgrade goes here
            }
        };

        dbku = dbopen.getWritableDatabase();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambah_data();
            }
        });

        // Load existing contacts from the database when the activity starts
        ambildata();
    }

    private void tambah_data() {
        AlertDialog.Builder buat = new AlertDialog.Builder(this);
        buat.setTitle("Add Kontak");
        View vAdd = LayoutInflater.from(this).inflate(R.layout.add_contact, null);
        final EditText nm = vAdd.findViewById(R.id.nm);
        final EditText hp = vAdd.findViewById(R.id.hp);

        buat.setView(vAdd);
        buat.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nama = nm.getText().toString();
                String nomor = hp.getText().toString();
                // Add the new contact to the database and list view
                add_item(nama, nomor);
                Toast.makeText(getBaseContext(), "Data Tersimpan", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
        buat.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        buat.show();
    }

    private void add_item(String nm, String hp) {
        ContentValues values = new ContentValues();
        values.put("nama", nm);
        values.put("nohp", hp);
        dbku.insert("kontak", null, values);

        // Update the ListView with the new contact
        kontak newKontak = new kontak(nm, hp);
        kAdapter.add(newKontak);
        kAdapter.notifyDataSetChanged(); // Notify adapter that dataset has changed
    }

    private void ambildata() {
        Cursor cursor = dbku.rawQuery("SELECT * FROM kontak", null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String nama = cursor.getString(cursor.getColumnIndex("nama"));
                String nomor = cursor.getString(cursor.getColumnIndex("nohp"));
                insertKontak(nama, nomor);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
    }

    private void insertKontak(String nm, String hp) {
        kontak newKontak = new kontak(nm, hp);
        kAdapter.add(newKontak);
        kAdapter.notifyDataSetChanged(); // Notify adapter that dataset has changed
    }
}