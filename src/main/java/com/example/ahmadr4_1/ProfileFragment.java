package com.example.ahmadr4_1;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

public class ProfileFragment extends Fragment {

    private TextView textUserName, textFavoritesCount, textPreparedCount, textCookedCount, textAge;
    private Button buttonEditProfile, buttonLogout;
    private ListenerRegistration profileListener; // لإيقاف التسمع عند الخروج

    public ProfileFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textUserName = view.findViewById(R.id.textUserName);
        textFavoritesCount = view.findViewById(R.id.textFavoritesCount);
        textPreparedCount = view.findViewById(R.id.textPreparedCount);
        textCookedCount = view.findViewById(R.id.textCookedCount);
        textAge = view.findViewById(R.id.textAge);
        buttonEditProfile = view.findViewById(R.id.buttonEditProfile);
        buttonLogout = view.findViewById(R.id.buttonLogout);

        buttonEditProfile.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), EditProfileActivity.class);
            startActivity(intent);
        });

        buttonLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            // وجه المستخدم لصفحة تسجيل الدخول إذا لزم الأمر
        });

        // بدء التسمع اللحظي للبيانات
        listenToUserData();
    }

    private void listenToUserData() {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) return;

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // SnapshotListener: يحدث الواجهة تلقائياً عند تغيير أي قيمة في Firestore
        profileListener = db.collection("users").document(userId)
                .addSnapshotListener((documentSnapshot, error) -> {
                    if (error != null) {
                        return;
                    }

                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        String name = documentSnapshot.getString("name");
                        Long age = documentSnapshot.getLong("age");
                        Long preparedCount = documentSnapshot.getLong("preparedCount");
                        Long favoritesCount = documentSnapshot.getLong("favoritesCount");
                        Long numFood = documentSnapshot.getLong("num_food");

                        textUserName.setText("الاسم: " + (name != null ? name : "غير محدد"));
                        textAge.setText("العمر: " + (age != null ? age : 0));
                        textPreparedCount.setText("الوصفات المحضّرة: " + (preparedCount != null ? preparedCount : 0));
                        textFavoritesCount.setText("عدد المفضلة: " + (favoritesCount != null ? favoritesCount : 0));
                        textCookedCount.setText("عدد الأكلات: " + (numFood != null ? numFood : 0));
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // إيقاف التسمع لتوفير البطارية والبيانات عند إغلاق الـ Fragment
        if (profileListener != null) profileListener.remove();
    }
}