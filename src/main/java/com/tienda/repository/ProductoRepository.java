package com.tienda.repository;

import com.tienda.domain.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;

@Repository

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    List<Producto> findByActivoTrue();

    // Derivada
    List<Producto> findByPrecioBetweenOrderByPrecioAsc(BigDecimal precioInf, BigDecimal precioSup);

    // JPQL
    @Query("SELECT p FROM Producto p WHERE p.precio BETWEEN :precioInf AND :precioSup ORDER BY p.precio ASC")
    List<Producto> consultaJPQL(@Param("precioInf") BigDecimal precioInf,
            @Param("precioSup") BigDecimal precioSup);

    // SQL nativo
    @Query(value = "SELECT * FROM producto p WHERE p.precio BETWEEN :precioInf AND :precioSup ORDER BY p.precio ASC",
            nativeQuery = true)
    List<Producto> consultaSQL(@Param("precioInf") BigDecimal precioInf,
            @Param("precioSup") BigDecimal precioSup);

    List<Producto> findByActivoTrueAndPrecioBetweenAndExistenciasGreaterThanEqualAndCategoria_ActivoIsTrueAndCategoria_DescripcionContainingIgnoreCaseOrderByPrecioAsc(
            BigDecimal precioMin,
            BigDecimal precioMax,
            Integer existenciasMin,
            String descripcionCategoria
    );

// 2) JPQL
    @Query("""
       SELECT p
       FROM Producto p
         JOIN p.categoria c
       WHERE p.activo = true
         AND p.precio BETWEEN :precioMin AND :precioMax
         AND p.existencias >= :existenciasMin
         AND c.activo = true
         AND LOWER(c.descripcion) LIKE LOWER(CONCAT('%', :descripcionCategoria, '%'))
       ORDER BY p.precio ASC
       """)
    List<Producto> consultaProductoAvanzadaJPQL(
            @Param("precioMin") BigDecimal precioMin,
            @Param("precioMax") BigDecimal precioMax,
            @Param("existenciasMin") Integer existenciasMin,
            @Param("descripcionCategoria") String descripcionCategoria
    );

// 3) SQL nativa
    @Query(value = """
       SELECT p.*
       FROM producto p
         INNER JOIN categoria c ON p.id_categoria = c.id_categoria
       WHERE p.activo = 1
         AND p.precio BETWEEN :precioMin AND :precioMax
         AND p.existencias >= :existenciasMin
         AND c.activo = 1
         AND c.descripcion LIKE CONCAT('%', :descripcionCategoria, '%')
       ORDER BY p.precio ASC
       """, nativeQuery = true)
    List<Producto> consultaProductoAvanzadaSQL(
            @Param("precioMin") BigDecimal precioMin,
            @Param("precioMax") BigDecimal precioMax,
            @Param("existenciasMin") Integer existenciasMin,
            @Param("descripcionCategoria") String descripcionCategoria
    );
    
    
}
