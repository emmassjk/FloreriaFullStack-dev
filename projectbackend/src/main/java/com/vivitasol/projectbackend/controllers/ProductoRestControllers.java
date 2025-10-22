package com.vivitasol.projectbackend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.vivitasol.projectbackend.entities.Categoria;
import com.vivitasol.projectbackend.entities.Producto;
import com.vivitasol.projectbackend.services.CategoriaServices;
import com.vivitasol.projectbackend.services.ProductoServices;
import com.vivitasol.projectbackend.services.StorageService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/productos")
public class ProductoRestControllers {

    @Autowired
    private ProductoServices productoServices;

    @Autowired
    private StorageService storageService;

    @Autowired
    private CategoriaServices categoriaServices;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Producto> crearProducto(
            @RequestParam("nombre") String nombre,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("precio") Long precio,
            @RequestParam("stock") Integer stock,
            @RequestParam("categoriaId") Long categoriaId,
            @RequestParam(value = "imagen", required = false) MultipartFile imagen) {

        Categoria categoria = categoriaServices.obtenerId(categoriaId);
        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setPrecio(precio);
        producto.setStock(stock);
        producto.setCategoria(categoria);
        producto.setActivo(true);

        if (imagen != null && !imagen.isEmpty()) {
            String filename = storageService.store(imagen);
            String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/upload/")
                    .path(filename)
                    .toUriString();
            producto.setImagen(uri);
        }

        Producto nuevoProducto = productoServices.crear(producto);
        return ResponseEntity.ok(nuevoProducto);
    }

    @PostMapping(value = "/{id}/imagen", consumes = "multipart/form-data")
    public ResponseEntity<Producto> subirImagen(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        String filename = storageService.store(file);
        Producto producto = productoServices.obtenerId(id);

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/upload/")
                .path(filename)
                .toUriString();

        producto.setImagen(uri);
        Producto productoActualizado = productoServices.actualizar(id, producto);
        return ResponseEntity.ok(productoActualizado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Long id) {
        Producto producto = productoServices.obtenerId(id);
        return ResponseEntity.ok(producto);
    }

 
    @GetMapping
    public ResponseEntity<List<Producto>> listarProductos() {
        List<Producto> productos = productoServices.listarTodas();
        return ResponseEntity.ok(productos);
    }

  
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoServices.eliminar(id);
        return ResponseEntity.noContent().build();
    }

   
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto productoActualizado) {
        Producto producto = productoServices.actualizar(id, productoActualizado);
        return ResponseEntity.ok(producto);
    }


    
    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Producto> desactivar(@PathVariable Long id) {
        return ResponseEntity.ok(productoServices.desactivar(id));
    }

}
