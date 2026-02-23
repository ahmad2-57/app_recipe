package com.example.ahmadr4_1;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment {

    private RecyclerView recyclerFavorites;
    private FavoritesAdapter adapter;
    private List<DishModel> list = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public FavoritesFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerFavorites = view.findViewById(R.id.recyclerFavorites);
        recyclerFavorites.setLayoutManager(new GridLayoutManager(getContext(), 3));

        adapter = new FavoritesAdapter(list);
        recyclerFavorites.setAdapter(adapter);
    }

    // يتم استدعاء onResume في كل مرة تظهر فيها الصفحة للمستخدم
    @Override
    public void onResume() {
        super.onResume();
        loadFavorites();
    }

    private void loadFavorites() {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) return;

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        db.collection("users").document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        List<String> favoriteIds = (List<String>) documentSnapshot.get("favoriteFoods");

                        // تنظيف القائمة الحالية قبل جلب البيانات المحدثة لمنع التكرار
                        list.clear();
                        adapter.notifyDataSetChanged();

                        if (favoriteIds != null && !favoriteIds.isEmpty()) {
                            fetchRecipesDetails(favoriteIds);
                        }
                    }
                });
    }

    private void fetchRecipesDetails(List<String> favoriteIds) {
        // نمر على كل ID ونجلب بياناته من مجموعة recipes
        for (String recipeId : favoriteIds) {
            db.collection("recipes").document(recipeId)
                    .get()
                    .addOnSuccessListener(doc -> {
                        if (doc.exists()) {
                            String name = doc.getString("name");
                            String image = doc.getString("imageUrl");
                            list.add(new DishModel(name, image));

                            // إبلاغ الـ Adapter بإضافة عنصر جديد ليظهر فوراً
                            adapter.notifyItemInserted(list.size() - 1);
                        }
                    });
        }
    }
}