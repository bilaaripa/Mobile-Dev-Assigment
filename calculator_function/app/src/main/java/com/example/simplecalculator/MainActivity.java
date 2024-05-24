package com.example.simplecalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Button buttonExit, buttonCalc;
    private TextView hasilTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi elemen UI
        editText = findViewById(R.id.editTextText);
        buttonExit = findViewById(R.id.buttonExit);
        buttonCalc = findViewById(R.id.buttonCalc);
        hasilTextView = findViewById(R.id.hasil);

        // Menambahkan OnClickListener untuk tombol Exit
        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // Menutup aplikasi
            }
        });

        // Menambahkan OnClickListener untuk tombol Calculate
        buttonCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hitungOperasi();
            }
        });
    }

    private void hitungOperasi() {
        // Mendapatkan nilai dari EditText
        String inputText = editText.getText().toString().trim();

        // Memeriksa apakah input kosong
        if (inputText.isEmpty()) {
            hasilTextView.setText("Masukkan nilai terlebih dahulu.");
            return;
        }

        // Memeriksa apakah input mengandung operasi matematika
        if (!inputText.matches(".*[+\\-x/].*")) {
            hasilTextView.setText("Masukkan operasi matematika yang valid.");
            return;
        }

        // Membagi input menjadi dua bagian (bilangan1 dan bilangan2)
        String[] parts = inputText.split("[+\\-x/]");
        if (parts.length != 2) {
            hasilTextView.setText("Format input tidak valid.");
            return;
        }

        // Mengambil operator dari input
        char operator = inputText.replaceAll("[^+\\-x/]", "").charAt(0);

        // Mengkonversi nilai input menjadi bilangan
        double bilangan1 = Double.parseDouble(parts[0].trim());
        double bilangan2 = Double.parseDouble(parts[1].trim());

        // Melakukan operasi
        double hasil = 0;
        switch (operator) {
            case '+':
                hasil = bilangan1 + bilangan2;
                break;
            case '-':
                hasil = bilangan1 - bilangan2;
                break;
            case 'x':
                hasil = bilangan1 * bilangan2;
                break;
            case '/':
                // Memeriksa pembagian dengan nol
                if (bilangan2 != 0) {
                    hasil = bilangan1 / bilangan2;
                } else {
                    hasilTextView.setText("Tidak dapat melakukan pembagian dengan nol.");
                    return;
                }
                break;
        }

        // Menampilkan hasil operasi pada TextView tanpa digit nol di belakang koma
        hasilTextView.setText("Hasil: " + formatAngka(hasil));
    }

    // Fungsi untuk menghilangkan digit nol di belakang koma
    private String formatAngka(double angka) {
        if (angka == (int) angka) {
            // Jika angka merupakan bilangan bulat, tampilkan tanpa desimal
            return String.format("%d", (int) angka);
        } else {
            // Jika angka merupakan bilangan desimal, tampilkan dengan desimal tanpa digit nol di belakang koma
            return String.format("%s", angka);
        }
    }
}
