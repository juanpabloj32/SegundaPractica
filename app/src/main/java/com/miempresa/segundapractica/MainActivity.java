package com.miempresa.segundapractica;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFragment(new ProductosFragment());

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);

        bottomNav.setOnItemSelectedListener(item -> {

            Fragment fragment = null;

            if(item.getItemId() == R.id.menu_productos){
                fragment = new ProductosFragment();
            }else if(item.getItemId() == R.id.menu_ventas){
                fragment = new VentasFragment();
            }else if(item.getItemId() == R.id.menu_vendidos){
                fragment = new ProductosVendidosFragment();
            }else if(item.getItemId() == R.id.menu_configuracion){
                fragment = new ConfiguracionFragment();
            }

            return loadFragment(fragment);
        });
    }

    private boolean loadFragment(Fragment fragment){
        if(fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}