package com.tienda.service;

import com.tienda.domain.Categoria;
import com.tienda.repository.CategoriaRepository;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.tienda.dto.CategoriaCantidadDTO;
import com.tienda.domain.Producto;
import java.util.Comparator;

@Service
public class CategoriaService {
//Permite crear una única instancia de CategoriaRepository, y la orea automáticamente

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional(readOnly = true)
    public List<Categoria> getCategorias(boolean activo) {
        if (activo) {//Sólo activos...
            return categoriaRepository.findByActivoTrue();
        }
        return categoriaRepository.findAll();

    }

    @Transactional(readOnly = true)
    public Optional<Categoria> getCategoria(Integer idCategoria) {
        return categoriaRepository.findById(idCategoria);
    }

    @Autowired
    private FirebaseStorageService firebaseStorageService;

    @Transactional
    public void save(Categoria categoria, MultipartFile imagenFile) {
        categoria = categoriaRepository.save(categoria);
        if (!imagenFile.isEmpty()) { //Si no está vacio... pasaron una imagen...
            try {
                String rutaImagen = firebaseStorageService.uploadImage(
                        imagenFile, "categoria",
                        categoria.getIdCategoria());
                categoria.setRutaImagen(rutaImagen);
                categoriaRepository.save(categoria);
            } catch (IOException e) {

            }
        }
    }

    @Transactional
    public void delete(Integer idCategoria) {
        if (!categoriaRepository.existsById(idCategoria)) {
            throw new IllegalArgumentException("La categoria con ID" + idCategoria + "no existe.");
        }

        try {
            categoriaRepository.deleteById(idCategoria);
        } catch (DataIntegrityViolationException e) {

            throw new IllegalStateException("No se puede eliminar la categoria. Tiene datos asociados.", e);
        }
    }

    @Transactional(readOnly = true)
    public List<CategoriaCantidadDTO> consultaCategoriaAvanzadaDerivada(Integer cantidadMinProductos,
            String textoDescripcion) {

        var categorias = categoriaRepository
                .findDistinctByActivoTrueAndDescripcionContainingIgnoreCaseAndProductos_ActivoTrue(textoDescripcion);

        return categorias.stream()
                .map(c -> {
                    long cant = 0;
                    if (c.getProductos() != null) {
                        cant = c.getProductos().stream()
                                .filter(Producto::isActivo)
                                .count();
                    }
                    return new CategoriaCantidadDTO(c.getIdCategoria(), c.getDescripcion(), cant);
                })
                .filter(dto -> dto.getCantidad() >= cantidadMinProductos)
                .sorted(Comparator.comparing(CategoriaCantidadDTO::getCantidad).reversed())
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CategoriaCantidadDTO> consultaCategoriaAvanzadaJPQL(Integer cantidadMinProductos,
            String textoDescripcion) {
        return categoriaRepository.consultaCategoriaAvanzadaJPQL(cantidadMinProductos, textoDescripcion)
                .stream()
                .map(row -> new CategoriaCantidadDTO(
                (Integer) row[0],
                (String) row[1],
                (Long) row[2]
        ))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CategoriaCantidadDTO> consultaCategoriaAvanzadaSQL(Integer cantidadMinProductos,
            String textoDescripcion) {
        return categoriaRepository.consultaCategoriaAvanzadaSQL(cantidadMinProductos, textoDescripcion)
                .stream()
                .map(row -> {
                    Integer id = ((Number) row[0]).intValue();
                    String desc = (String) row[1];
                    Long cant = ((Number) row[2]).longValue();
                    return new CategoriaCantidadDTO(id, desc, cant);
                })
                .toList();
    }

}
