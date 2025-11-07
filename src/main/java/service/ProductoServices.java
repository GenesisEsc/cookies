package service;

import models.Producto;
import java.util.List;

/**
 * Interfaz ProductoServices.
 *
 * @author Genesis Escobar
 * @version 1.0
 * @since 2025-11-06
 *
 * Descripción:
 * Define el contrato que deben implementar las clases encargadas de manejar
 * las operaciones relacionadas con los objetos de tipo {@link Producto}.
 *
 * Esta interfaz permite desacoplar la lógica de negocio de la implementación
 * concreta, facilitando la reutilización y mantenimiento del código.
 */
public interface ProductoServices {

    /**
     * Metodo que devuelve una lista de productos disponibles.
     *
     * @return Lista de objetos {Producto}.
     *
     * Descripción:
     * Permite obtener todos los productos registrados en el sistema o
     * fuente de datos correspondiente. Su implementación dependerá de la
     * clase que la utilice, como {ProductosServicesImplement}.
     */
    List<Producto> listar();
}
