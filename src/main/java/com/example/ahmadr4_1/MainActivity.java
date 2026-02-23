package com.example.ahmadr4_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;
    FloatingActionButton buttonAddRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigation = findViewById(R.id.bottomNavigation);
        buttonAddRecipe = findViewById(R.id.buttonAddRecipe);

        // أول صفحة افتراضية
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new HomeFragment())
                .commit();

        // التنقل بين الصفحات
        bottomNavigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new HomeFragment())
                        .commit();
                return true;
            } else if (id == R.id.nav_search) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new SearchFragment())
                        .commit();
                return true;
            } else if (id == R.id.nav_favorites) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new FavoritesFragment())
                        .commit();
                return true;
            } else if (id == R.id.nav_profile) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new ProfileFragment())
                        .commit();
                return true;
            }

            return false;
        });

        // زر إضافة وصفة
        buttonAddRecipe.setOnClickListener(v -> {
            Fragment addRecipeFragment = new AddRecipeFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, addRecipeFragment)
                    .addToBackStack(null)
                    .commit();
        });
    }
}