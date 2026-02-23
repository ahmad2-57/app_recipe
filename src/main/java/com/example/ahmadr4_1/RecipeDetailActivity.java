package com.example.ahmadr4_1;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity {

    private TextView textTitle, textIngredients, textSteps, textTime;
    private Button btnFavorite, btnDone;
    private String recipeId;
    private boolean isFavorite = false; // حالة المفضلة الحالية

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        textTitle = findViewById(R.id.textTitle);
        textIngredients = findViewById(R.id.textIngredients);
        textSteps = findViewById(R.id.textSteps);
        textTime = findViewById(R.id.textTime);
        btnFavorite = findViewById(R.id.btnFavorite);
        btnDone = findViewById(R.id.btnDone);

        String recipeName = getIntent().getStringExtra("recipe_name");

        if (recipeName != null) {
            loadRecipeData(recipeName);
        } else {
            Toast.makeText(this, "خطأ في تمرير اسم الوصفة", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadRecipeData(String name) {
        FirebaseFirestore.getInstance().collection("recipes")
                .whereEqualTo("name", name)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        DocumentSnapshot doc = queryDocumentSnapshots.getDocuments().get(0);
                        recipeId = doc.getId();

                        String title = doc.getString("name");
                        String ingredients = doc.getString("ingredients");
                        String steps = doc.getString("steps");

                        Object timeObj = doc.get("timeRequired");
                        String time = (timeObj != null) ? String.valueOf(timeObj) : "0";

                        textTitle.setText(title);
                        textTime.setText("الوقت: " + time + " دقيقة");
                        textIngredients.setText(ingredients);
                        textSteps.setText(steps);

                        // فحص هل الطبق في المفضلة بعد جلب الـ ID
                        checkIfFavorite();
                        setupButtons();
                    } else {
                        Toast.makeText(this, "الوصفة غير موجودة في Firestore", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "خطأ في الاتصال بقاعدة البيانات", Toast.LENGTH_SHORT).show();
                });
    }

    private void checkIfFavorite() {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) return;

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore.getInstance().collection("users").document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        List<String> favorites = (List<String>) documentSnapshot.get("favoriteFoods");
                        if (favorites != null && favorites.contains(recipeId)) {
                            isFavorite = true;
                        } else {
                            isFavorite = false;
                        }
                        updateFavoriteButtonUI();
                    }
                });
    }

    private void setupButtons() {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) return;
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        btnFavorite.setOnClickListener(v -> {
            if (recipeId == null) return;

            if (!isFavorite) {
                // إضافة للمفضلة
                FirebaseFirestore.getInstance().collection("users").document(userId)
                        .update("favoriteFoods", FieldValue.arrayUnion(recipeId),
                                "favoritesCount", FieldValue.increment(1))
                        .addOnSuccessListener(a -> {
                            isFavorite = true;
                            updateFavoriteButtonUI();
                            Toast.makeText(this, "تمت الإضافة للمفضلة ❤️", Toast.LENGTH_SHORT).show();
                        });
            } else {
                // إزالة من المفضلة
                FirebaseFirestore.getInstance().collection("users").document(userId)
                        .update("favoriteFoods", FieldValue.arrayRemove(recipeId),
                                "favoritesCount", FieldValue.increment(-1))
                        .addOnSuccessListener(a -> {
                            isFavorite = false;
                            updateFavoriteButtonUI();
                            Toast.makeText(this, "تمت الإزالة من المفضلة", Toast.LENGTH_SHORT).show();
                        });
            }
        });

        btnDone.setOnClickListener(v -> {
            FirebaseFirestore.getInstance().collection("users").document(userId)
                    .update("preparedCount", FieldValue.increment(1))
                    .addOnSuccessListener(a -> Toast.makeText(this, "هنيئاً لك! تم التحديث 🍳", Toast.LENGTH_SHORT).show());
        });
    }

    private void updateFavoriteButtonUI() {
        if (isFavorite) {
            btnFavorite.setText("إزالة من المفضلة");
            // تغيير اللون للأحمر عند الإزالة
            btnFavorite.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
        } else {
            btnFavorite.setText("أضف للمفضلة");
            // العودة للون الأخضر الافتراضي الخاص بك
            btnFavorite.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.green)));
        }
    }
}