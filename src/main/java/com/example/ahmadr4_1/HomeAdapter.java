package com.example.ahmadr4_1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// جاهز للصور لاحقًا (غير مفعّل الآن)
// import com.bumptech.glide.Glide;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private List<DishModel> list;

    public HomeAdapter(List<DishModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_dish, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DishModel dish = list.get(position);

        holder.textDishName.setText(dish.getName());

        // تعيين الصورة (حالياً تضع صورة افتراضية)
        holder.imageDish.setImageResource(R.drawable.ic_launcher_background);

        // --- الكود الجديد المضاف للانتقال ---
        holder.itemView.setOnClickListener(v -> {
            // 1. إنشاء Intent للانتقال من الصفحة الحالية إلى صفحة التفاصيل
            android.content.Intent intent = new android.content.Intent(v.getContext(), RecipeDetailActivity.class);

            // 2. تمرير اسم الطبق لكي تبحث عنه صفحة التفاصيل في Firestore
            intent.putExtra("recipe_name", dish.getName());

            // 3. بدء النشاط
            v.getContext().startActivity(intent);
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageDish;
        TextView textDishName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageDish = itemView.findViewById(R.id.imageDish);
            textDishName = itemView.findViewById(R.id.textDishName);
        }
    }
}