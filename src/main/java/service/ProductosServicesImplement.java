package service;

import models.Producto;

import java.util.Arrays;
import java.util.List;

public class ProductosServicesImplement implements ProductoServices {
    @Override
    public List<Producto> listar() {
        return Arrays.asList(new Producto(1L, "laptop", "computacion", 455.90),
                new Producto( 2L, "mouse", "computacion", 25.50),
                new Producto(3L, "cocina", "cocina", 599.60)
        );
    }
}
