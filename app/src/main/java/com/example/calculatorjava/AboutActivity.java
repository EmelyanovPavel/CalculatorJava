package com.example.calculatorjava;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.menu.databinding.ActivityAboutBinding;

public class AboutActivity extends AppCompatActivity {

    private ActivityAboutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Инициализация ViewBinding
        binding = ActivityAboutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Настройка содержимого экрана "О приложении"
        setupAboutContent();
    }

    private void setupAboutContent() {
        // Текст о приложении
        String aboutText = "Calculator v1.0\n\n" +
                "Developer: Pavel\n" +
                "Email: example@email.com\n\n" +
                "Description:\n" +
                "A simple calculator app with a navigation menu.\n\n" +
                "Used technologies:\n" +
                "- Android SDK\n" +
                "- ViewBinding\n" +
                "- Material Components\n" +
                "- Navigation Drawer";

        binding.tvAbout.setText(aboutText);

        // Дополнительно: можно добавить логотип или GIF
        // binding.imageView.setImageResource(R.drawable.logo);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Освобождаем ресурсы ViewBinding
        binding = null;
    }
}
