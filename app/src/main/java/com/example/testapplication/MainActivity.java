package com.example.testapplication;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.testapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding binding;


    EditText editFirstNumber, editSecondNumber;
    Button btnMultiply;
    TextView txtMultiplyResult;

    MultiplyInterface myInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // =============

        editFirstNumber = (EditText) findViewById(R.id.number1);
        editSecondNumber = (EditText) findViewById(R.id.number2);
        btnMultiply = (Button) findViewById(R.id.multiply);
        txtMultiplyResult = (TextView) findViewById(R.id.result);

        btnMultiply.setOnClickListener(MainActivity.this);

        Intent multiplyIntent = new Intent(MainActivity.this, MultiplicationService.class);

        bindService(multiplyIntent, myServiceConnection, Context.BIND_AUTO_CREATE);

    }

    ServiceConnection myServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //We are connecting multiplication service to this interface
            myInterface = MultiplyInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public void onClick(View v) {
        int firstNumber = Integer.parseInt(editFirstNumber.getText().toString());
        int secondNumber = Integer.parseInt(editSecondNumber.getText().toString());

        try {
            int result = myInterface.multiplyTwoValues(firstNumber, secondNumber);
            txtMultiplyResult.setText(result + "");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}