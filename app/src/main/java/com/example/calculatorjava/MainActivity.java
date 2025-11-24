package com.example.calculatorjava;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.drawerlayout.widget.DrawerLayout;
import com.example.calculatorjava.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;
import android.widget.Button;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding; //Homework 11
    private DrawerLayout drawerLayout; //Homework 11
    private NavigationView navView; //Homework 11
    private double currentNumber = 0; //Homework 11
    private double storedNumber = 0;//Homework 11
    private String operation = ""; //Homework 11
    private boolean isOperationClicked = false; //Homework 11
    private boolean isDecimalAdded = false; //Homework 11

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initSomeTexts();
        AppCompatImageView image = findViewById(R.id.imageView);
        loadImageFromAsset(image);
        initList();

        //Homework 11
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        drawerLayout = binding.drawerLayout;
        navView = binding.navView;

        // Инициализация калькулятора
        initCalculator();

        // Настройка навигационного меню
        navView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_about) {
                startActivity(new Intent(this, AboutActivity.class));
                drawerLayout.closeDrawers();
            } else if (id == R.id.nav_exit) {
                finish();
            }
            return true;
        });
    }

    private void initCalculator() {
        binding.tvResult.setText("0");

        // Обработчики числовых кнопок (0–9)
        setNumberButtonClickListener(binding.button_0, "0");
        setNumberButtonClickListener(binding.button_1, "1");
        setNumberButtonClickListener(binding.button_2, "2");
        setNumberButtonClickListener(binding.button_3, "3");
        setNumberButtonClickListener(binding.button_4, "4");
        setNumberButtonClickListener(binding.button_5, "5");
        setNumberButtonClickListener(binding.button_6, "6");
        setNumberButtonClickListener(binding.button_7, "7");
        setNumberButtonClickListener(binding.button_8, "8");
        setNumberButtonClickListener(binding.button_9, "9");

        // Кнопка точки (десятичный разделитель)
        binding.button_point.setOnClickListener(v -> {
            if (!isDecimalAdded) {
                if (!binding.tvResult.getText().toString().equals("0")) {
                    binding.tvResult.append(".");
                } else {
                    binding.tvResult.setText("0.");
                }
                isDecimalAdded = true;
            }
        });

        // Операции
        binding.button_plus.setOnClickListener(v -> setOperation("+"));
        binding.button_minus.setOnClickListener(v -> setOperation("-"));
        binding.button_multiply.setOnClickListener(v -> setOperation("*"));
        binding.button_divide.setOnClickListener(v -> setOperation("/"));
        // Кнопка равно
        binding.button_equal.setOnClickListener(v -> calculateResult());
        // Кнопка сброса
        binding.button_clear.setOnClickListener(v -> resetCalculator());
    }

    private void setNumberButtonClickListener(Button button, String digit) {
        button.setOnClickListener(v -> {
            String currentText = binding.tvResult.getText().toString();

            if (currentText.equals("0") || isOperationClicked) {
                binding.tvResult.setText(digit);
                isOperationClicked = false;
            } else {
                binding.tvResult.append(digit);
            }
        });
    }

    // Установка операции
    private void setOperation(String op) {
        if (!binding.tvResult.getText().toString().equals("0")) {
            storedNumber = Double.parseDouble(binding.tvResult.getText().toString());
            operation = op;
            isOperationClicked = true;
            isDecimalAdded = false; // Сбрасываем флаг десятичной точки
        }
    }

    // Вычисление результата
    private void calculateResult() {
        if (!operation.isEmpty() && !binding.tvResult.getText().toString().equals("0")) {
            currentNumber = Double.parseDouble(binding.tvResult.getText().toString());

            switch (operation) {
                case "+":
                    currentNumber = storedNumber + currentNumber;
                    break;
                case "-":
                    currentNumber = storedNumber - currentNumber;
                    break;
                case "*":
                    currentNumber = storedNumber * currentNumber;
                    break;
                case "/":
                    if (currentNumber != 0) {
                        currentNumber = storedNumber / currentNumber;
                    } else {
                        binding.tvResult.setText("Error");
                        return;
                    }
                    break;
            }

            // Форматируем результат (убираем .0 для целых чисел)
            if (currentNumber == (long) currentNumber) {
                binding.tvResult.setText(String.valueOf((long) currentNumber));
            } else {
                binding.tvResult.setText(String.valueOf(currentNumber));
            }

            operation = ""; // Очищаем операцию
            isDecimalAdded = String.valueOf(currentNumber).contains(".");
        }
    }

    // Сброс калькулятора
    private void resetCalculator() {
        currentNumber = 0;
        storedNumber = 0;
        operation = "";
        isOperationClicked = false;
        isDecimalAdded = false;
        binding.tvResult.setText("0");
    }

    //Добавить минимум 1 сообщение в приложение калькулятор (любое, кроме Toast)
    private void showStatusMessage(String message) {
        binding.statusMessage.setText(message);
        binding.statusMessage.setVisibility(View.VISIBLE);
    }

    private void hideStatusMessage() {
        binding.statusMessage.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    void errorMessages() {
        showStatusMessage("Внимание: результат приблизительный!");
// Через 3 секунды скрыть:
        new Handler(Looper.getMainLooper()).postDelayed(this::hideStatusMessage, 3000);
    }
    
//Homework #9
    private void initSomeTexts() {
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/lao_ui.ttf");
        ((TextView)findViewById(R.id.textView)).setTypeface(tf);
    }

    private void loadImageFromAsset(ImageView image) {
        try {
            InputStream ims = getAssets().open("android_redmi.png");
            // загружаем как Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // выводим картинку в ImageView
            image.setImageDrawable(d);
        }
        catch(IOException ex) {
            return;
        }
    }

    private void initList(){
        LinearLayout layoutList = findViewById(R.id.layoutList);

        // Получить массив со строками из ресурсов
        String[] versions = getResources().getStringArray(R.array.version_names);
        // Получить из ресурсов массив указателей на изображения
        TypedArray imgs = getResources().obtainTypedArray(R.array.version_logos);
        // При помощи этого объекта будем доставать элементы, спрятанные в android_item.xml
        LayoutInflater ltInflater = getLayoutInflater();

        for (int i = 0; i < versions.length; i++){
            String version = versions[i];
            // Достаем элемент из item.xml
            View item = ltInflater.inflate(R.layout.item_for_android, layoutList, false);
            // Находим в этом элементе TextView
            TextView tv = item.findViewById(R.id.textAndroid);
            tv.setText(version);
            // Выбрать по индексу подходящее изображение
            AppCompatImageView imgLogo = item.findViewById(R.id.imageAndroid);
            imgLogo.setImageResource(imgs.getResourceId(i, -1));

            layoutList.addView(item);
        }
    }
}
