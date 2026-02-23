package com.example.ahmadr4_1;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddRecipeFragment extends Fragment {

    private EditText editRecipeName, editRecipeIngredients, editRecipeSteps;
    private Button buttonSaveRecipe;

    public AddRecipeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_recipe, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editRecipeName = view.findViewById(R.id.editRecipeName);
        editRecipeIngredients = view.findViewById(R.id.editRecipeIngredients);
        editRecipeSteps = view.findViewById(R.id.editRecipeSteps);
        buttonSaveRecipe = view.findViewById(R.id.buttonSaveRecipe);

        buttonSaveRecipe.setOnClickListener(v -> {
            String name = editRecipeName.getText().toString().trim();
            String ingredients = editRecipeIngredients.getText().toString().trim();
            String steps = editRecipeSteps.getText().toString().trim();

            if (name.isEmpty() || ingredients.isEmpty() || steps.isEmpty()) {
                Toast.makeText(getContext(), "يرجى إدخال جميع البيانات", Toast.LENGTH_SHORT).show();
            } else {

                FirebaseFirestore db = FirebaseFirestore.getInstance();

                Map<String, Object> recipe = new HashMap<>();
                recipe.put("name", name);
                recipe.put("ingredients", ingredients);
                recipe.put("steps", steps);

                db.collection("recipes")
                        .add(recipe)
                        .addOnSuccessListener(documentReference -> {

                            // 🔥 زيادة عدد الوصفات المحضّرة للمستخدم
                            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                            db.collection("users")
                                    .document(userId)
                                    .update("preparedCount", FieldValue.increment(1));

                            Toast.makeText(getContext(), "تم حفظ الوصفة بنجاح!", Toast.LENGTH_SHORT).show();

                            getParentFragmentManager().popBackStack();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(getContext(), "حدث خطأ أثناء الحفظ: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        });
            }
        });
    }
}