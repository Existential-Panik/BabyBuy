package com.example.babybuy;

import static com.example.babybuy.DatabaseHelper.TABLE_NAME;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.ByteArrayOutputStream;

public class AddItems extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private Button buttonAddItem;
    private EditText editTextName, editTextPrice, editTextDescription;
    private ImageView imageView;
    private Uri imageUri;

    public static Intent getIntent(Context context) {

        return new Intent(context, AddItems.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);

        findId();
        databaseHelper = new DatabaseHelper(this);

        imageUri = Uri.EMPTY;
        imageView.setOnClickListener(this::pickImage);
        buttonAddItem.setOnClickListener(this::saveItem);
    }

    private void findId() {
        buttonAddItem = findViewById(R.id.btn_addItems);
        editTextName = findViewById(R.id.addName);
        editTextPrice = findViewById(R.id.addPrice);
        editTextDescription = findViewById(R.id.addDis);
        imageView = findViewById(R.id.addImage);
    }

    private void saveItem(View view) {
        String name = editTextName.getText().toString();
        if (name.isEmpty()) {
            editTextName.setError("Name field is empty");
            editTextName.requestFocus();
        }
        double price = 0;
        try {
            price = Double.parseDouble(editTextPrice.getText().toString());
        } catch (NullPointerException e) {
            Toast.makeText(
                    getApplicationContext(),
                    "Something wrong with price.",
                    Toast.LENGTH_SHORT
            ).show();
        } catch (NumberFormatException e) {
            Toast.makeText(
                    getApplicationContext(),
                    "Price should be a number",
                    Toast.LENGTH_SHORT
            ).show();
        }
        if (price <= 0) {
            editTextPrice.setError("Price should be greater than 0.");
            editTextPrice.requestFocus();
        }
        String description = editTextDescription.getText().toString();
        if(description.isEmpty()) {
            editTextDescription.setError("Description is empty.");
            editTextDescription.requestFocus();
        }

        if (databaseHelper.insert(name, price, description, imageUri.toString(), false)) {
            Toast.makeText(getApplicationContext(), "Saved Successfully",
                    Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Failed to save", Toast.LENGTH_SHORT).show();
        }
    }

    private void pickImage(View view) {
        ImagePickerUtility.pickImage(view, AddItems.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (data != null) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}