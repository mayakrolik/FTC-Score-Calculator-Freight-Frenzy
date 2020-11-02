package com.example.ftcscorecalculatorbeta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.example.ftcscorecalculatorbeta.ui.calculator.CalculatorFragment;
import com.example.ftcscorecalculatorbeta.ui.home.HomeFragment;
import com.example.ftcscorecalculatorbeta.ui.progress.ProgressFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FragmentManager fragmentManager = getSupportFragmentManager();

        final Fragment cacl_frag = new CalculatorFragment();
        final Fragment home_frag = new HomeFragment();
        final Fragment prog_frag = new ProgressFragment();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = cacl_frag;

                switch (item.getItemId()) {
                    case R.id.navigation_calculator:
                        fragment = cacl_frag;
                        //Toast.makeText(MainActivity.this, "Calc", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigation_home:
                        fragment = home_frag;
                        //Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigation_progress:
                        fragment = prog_frag;
                        //Toast.makeText(MainActivity.this, "Progress", Toast.LENGTH_SHORT).show();
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();
                return true;
            }
        });

    }

}