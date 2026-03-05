/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda.repository;

import com.tienda.domain.Categoria;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    public List<Categoria> findByActivoTrue();

    @EntityGraph(attributePaths = {"productos"})
    List<Categoria> findDistinctByActivoTrueAndDescripcionContainingIgnoreCaseAndProductos_ActivoTrue(
            String textoDescripcion
    );


    @Query("""
           SELECT c.idCategoria, c.descripcion, COUNT(p)
           FROM Categoria c
             JOIN c.productos p
           WHERE c.activo = true
             AND p.activo = true
             AND LOWER(c.descripcion) LIKE LOWER(CONCAT('%', :textoDescripcion, '%'))
           GROUP BY c.idCategoria, c.descripcion
           HAVING COUNT(p) >= :cantidadMinProductos
           ORDER BY COUNT(p) DESC
           """)
    List<Object[]> consultaCategoriaAvanzadaJPQL(
            @Param("cantidadMinProductos") Integer cantidadMinProductos,
            @Param("textoDescripcion") String textoDescripcion
    );


    @Query(value = """
           SELECT c.id_categoria, c.descripcion, COUNT(p.id_producto) AS cantidad
           FROM categoria c
             INNER JOIN producto p ON p.id_categoria = c.id_categoria
           WHERE c.activo = 1
             AND p.activo = 1
             AND c.descripcion LIKE CONCAT('%', :textoDescripcion, '%')
           GROUP BY c.id_categoria, c.descripcion
           HAVING COUNT(p.id_producto) >= :cantidadMinProductos
           ORDER BY cantidad DESC
           """, nativeQuery = true)
    List<Object[]> consultaCategoriaAvanzadaSQL(
            @Param("cantidadMinProductos") Integer cantidadMinProductos,
            @Param("textoDescripcion") String textoDescripcion
    );

}
