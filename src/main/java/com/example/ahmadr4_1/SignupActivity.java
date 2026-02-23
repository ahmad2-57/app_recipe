package com.example.ahmadr4_1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button buttonSignup;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonSignup = findViewById(R.id.buttonSignup);

        mAuth = FirebaseAuth.getInstance();

        buttonSignup.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(SignupActivity.this, "أدخل البريد وكلمة المرور", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {

                            String userId = mAuth.getCurrentUser().getUid();
                            FirebaseFirestore db = FirebaseFirestore.getInstance();

                            // تحضير بيانات المستخدم الجديد
                            Map<String, Object> userData = new HashMap<>();
                            userData.put("email", email);
                            userData.put("name", "مستخدم جديد"); // اسم افتراضي
                            userData.put("age", 0);
                            userData.put("preparedCount", 0);
                            userData.put("favoritesCount", 0);
                            userData.put("num_food", 0);
                            // 🔥 إنشاء القائمة كـ Array في Firestore لتخزين IDs الأكلات لاحقاً
                            userData.put("favoriteFoods", new ArrayList<String>());

                            db.collection("users")
                                    .document(userId)
                                    .set(userData)
                                    .addOnSuccessListener(a -> {
                                        Toast.makeText(SignupActivity.this, "تم إنشاء الحساب بنجاح", Toast.LENGTH_SHORT).show();
                                        finish();
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(SignupActivity.this, "فشل حفظ البيانات: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                    });

                        } else {
                            Toast.makeText(SignupActivity.this,
                                    "فشل إنشاء الحساب: " + task.getException().getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
        });
    }
}