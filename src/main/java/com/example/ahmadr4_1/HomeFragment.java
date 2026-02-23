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

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerHome;
    private HomeAdapter adapter;
    private List<DishModel> list = new ArrayList<>();

    public HomeFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerHome = view.findViewById(R.id.recyclerHome);
        recyclerHome.setLayoutManager(new GridLayoutManager(getContext(), 3));

        adapter = new HomeAdapter(list);
        recyclerHome.setAdapter(adapter);

        loadRecipes();
    }

    private void loadRecipes() {

        FirebaseFirestore.getInstance()
                .collection("recipes")   // ← هنا التعديل المهم
                .get()
                .addOnSuccessListener(query -> {

                    list.clear();

                    for (DocumentSnapshot doc : query) {
                        String name = doc.getString("name");
                        String image = doc.getString("imageUrl"); // جاهز للصور لاحقًا

                        list.add(new DishModel(name, image));
                    }

                    // ترتيب عشوائي
                    Collections.shuffle(list);

                    adapter.notifyDataSetChanged();
                });
    }
}