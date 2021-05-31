package com.example.weatherapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText editTextTextPersonName;
    private Button button;
    private TextView textView;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;



    String Url = "api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}";
    String ApiKey = "cb50f8c1ffd4294e5c5cf8d3a0f5ad4e";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        editTextTextPersonName = findViewById(R.id.editTextTextPersonName);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);

        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);

    }

    public void getweather(View view) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);


        Call<Example> exampleCall = apiInterface.getweather(editTextTextPersonName.getText().toString().trim(),ApiKey);

       exampleCall.enqueue(new Callback<Example>() {
           @Override
           public void onResponse(Call<Example> call, Response<Example> response) {
               if (response.code() == 404)
               {
                   Toast.makeText(MainActivity.this, "Please Enter A valid City", Toast.LENGTH_LONG).show();
               }
               else if (!response.isSuccessful())
               {
                   Toast.makeText(MainActivity.this, response.code(), Toast.LENGTH_LONG).show();
               }
               else {
                   Example main = response.body();

                   Main Data =  main.getMain();

                   Double temp = Data.getTemp();
                   Integer integer = (int) (temp - 273.15);
                   int Presure = Data.getPressure();
                   int humedity = Data.getHumidity();
                   Double temp_max = Data.getTempMax();
                   Integer ten_maxint = (int)(temp_max-273.5);
                   textView.setText(String.valueOf(integer) + "C");
                   textView1.setText(Presure +" mbar");
                   textView2.setText(humedity+" %");
                   textView3.setText(ten_maxint+" C");
               }
           }

           @Override
           public void onFailure(Call<Example> call, Throwable t) {
               Toast.makeText(MainActivity.this, t.getLocalizedMessage() , Toast.LENGTH_LONG).show();
           }
       });
    }
}