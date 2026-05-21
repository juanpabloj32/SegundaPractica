package com.miempresa.segundapractica;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductosFragment extends Fragment {

    RecyclerView recyclerProductos;
    Button btnAgregar;
    ApiService apiService;
    ProductoAdapter adapter;
    List<Producto> listaProductos = new ArrayList<>();

    public ProductosFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_productos_fragment, container, false);

        recyclerProductos = view.findViewById(R.id.recyclerProductos);
        btnAgregar = view.findViewById(R.id.btnAgregarProducto);

        recyclerProductos.setLayoutManager(new LinearLayoutManager(getContext()));

        apiService = RetrofitClient.getClient().create(ApiService.class);

        adapter = new ProductoAdapter(getContext(), listaProductos, apiService);
        recyclerProductos.setAdapter(adapter);

        cargarProductos();

        btnAgregar.setOnClickListener(v -> mostrarDialogoAgregar());

        return view;
    }

    private void cargarProductos() {
        apiService.obtenerProductos().enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaProductos.clear();
                    listaProductos.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void mostrarDialogoAgregar() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_agregar_producto, null);

        EditText etNombre = dialogView.findViewById(R.id.etNombreProducto);
        EditText etDescripcion = dialogView.findViewById(R.id.etDescripcionProducto);

        new AlertDialog.Builder(getContext())
                .setTitle("Agregar producto")
                .setView(dialogView)
                .setPositiveButton("Guardar", (dialog, which) -> {

                    String nombre = etNombre.getText().toString().trim();
                    String descripcion = etDescripcion.getText().toString().trim();

                    if(nombre.isEmpty() || descripcion.isEmpty()){
                        Toast.makeText(getContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Producto producto = new Producto(nombre, descripcion);

                    apiService.crearProducto(producto).enqueue(new Callback<Producto>() {
                        @Override
                        public void onResponse(Call<Producto> call, Response<Producto> response) {
                            if(response.isSuccessful() && response.body() != null){

                                Producto nuevoProducto = response.body();

                                listaProductos.add(0, nuevoProducto);
                                adapter.notifyItemInserted(0);
                                recyclerProductos.scrollToPosition(0);

                                Toast.makeText(getContext(), "Producto agregado", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Producto> call, Throwable t) {
                            Toast.makeText(getContext(), "Error al agregar: " + t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}