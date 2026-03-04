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

    // JPQL (usa ENTIDAD Producto y params correctos)
    @Query("SELECT p FROM Producto p WHERE p.precio BETWEEN :precioInf AND :precioSup ORDER BY p.precio ASC")
    List<Producto> consultaJPQL(@Param("precioInf") BigDecimal precioInf,
                               @Param("precioSup") BigDecimal precioSup);

    // SQL nativo (usa tabla producto y SELECT *)
    @Query(value = "SELECT * FROM producto p WHERE p.precio BETWEEN :precioInf AND :precioSup ORDER BY p.precio ASC",
           nativeQuery = true)
    List<Producto> consultaSQL(@Param("precioInf") BigDecimal precioInf,
                              @Param("precioSup") BigDecimal precioSup);
}