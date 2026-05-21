package com.miempresa.segundapractica;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    @GET("posts")
    Call<List<Producto>> obtenerProductos();

    @POST("posts")
    Call<Producto> crearProducto(@Body Producto producto);

    @PUT("posts/{id}")
    Call<Producto> actualizarProducto(@Path("id") int id, @Body Producto producto);

    @DELETE("posts/{id}")
    Call<Void> eliminarProducto(@Path("id") int id);
}