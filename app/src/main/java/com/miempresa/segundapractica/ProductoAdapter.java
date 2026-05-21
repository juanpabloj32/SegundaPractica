package com.miempresa.segundapractica;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ViewHolder> {

    Context context;
    List<Producto> lista;
    ApiService apiService;

    public ProductoAdapter(Context context, List<Producto> lista, ApiService apiService) {
        this.context = context;
        this.lista = lista;
        this.apiService = apiService;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_producto, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Producto producto = lista.get(position);

        holder.tvNombre.setText(producto.getTitle());
        holder.tvDescripcion.setText(producto.getBody());

        holder.btnEditar.setOnClickListener(v -> mostrarDialogoEditar(producto, position));

        holder.btnEliminar.setOnClickListener(v -> {
            apiService.eliminarProducto(producto.getId()).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    lista.remove(position);
                    notifyItemRemoved(position);
                    Toast.makeText(context, "Producto eliminado", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(context, "Error al eliminar", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void mostrarDialogoEditar(Producto producto, int position) {
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_agregar_producto, null);

        EditText etNombre = dialogView.findViewById(R.id.etNombreProducto);
        EditText etDescripcion = dialogView.findViewById(R.id.etDescripcionProducto);

        etNombre.setText(producto.getTitle());
        etDescripcion.setText(producto.getBody());

        new AlertDialog.Builder(context)
                .setTitle("Editar producto")
                .setView(dialogView)
                .setPositiveButton("Actualizar", (dialog, which) -> {

                    Producto productoActualizado = new Producto(
                            producto.getId(),
                            etNombre.getText().toString(),
                            etDescripcion.getText().toString()
                    );

                    apiService.actualizarProducto(producto.getId(), productoActualizado).enqueue(new Callback<Producto>() {
                        @Override
                        public void onResponse(Call<Producto> call, Response<Producto> response) {
                            lista.set(position, productoActualizado);
                            notifyItemChanged(position);
                            Toast.makeText(context, "Producto actualizado", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Producto> call, Throwable t) {
                            Toast.makeText(context, "Error al actualizar", Toast.LENGTH_SHORT).show();
                        }
                    });
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNombre, tvDescripcion;
        ImageButton btnEditar, btnEliminar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
}