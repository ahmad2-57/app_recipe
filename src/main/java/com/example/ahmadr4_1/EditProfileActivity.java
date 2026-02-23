package com.example.ahmadr4_1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditProfileActivity extends AppCompatActivity {

    private EditText editName, editAge;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        editName = findViewById(R.id.editName);
        editAge = findViewById(R.id.editAge);
        buttonSave = findViewById(R.id.buttonSave);

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // تحميل البيانات الحالية
        db.collection("users").document(userId).get()
                .addOnSuccessListener(doc -> {
                    if (doc.exists()) {
                        editName.setText(doc.getString("name"));
                        Long age = doc.getLong("age");
                        if (age != null) editAge.setText(String.valueOf(age));
                    }
                });

        // حفظ التعديلات
        buttonSave.setOnClickListener(v -> {
            String name = editName.getText().toString().trim();
            String ageStr = editAge.getText().toString().trim();

            if (name.isEmpty() || ageStr.isEmpty()) {
                Toast.makeText(this, "املأ جميع الحقول", Toast.LENGTH_SHORT).show();
                return;
            }

            int age = Integer.parseInt(ageStr);

            db.collection("users").document(userId)
                    .update("name", name, "age", age)
                    .addOnSuccessListener(a -> {
                        Toast.makeText(this, "تم حفظ التعديلات", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "فشل الحفظ", Toast.LENGTH_SHORT).show();
                    });
        });
    }
}