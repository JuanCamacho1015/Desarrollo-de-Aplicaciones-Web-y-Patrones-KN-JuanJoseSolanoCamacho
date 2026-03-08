package com.tienda.controller;

import com.tienda.dto.CategoriaCantidadDTO;
import com.tienda.service.CategoriaService;
import com.tienda.service.ProductoService;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author Juan Camacho
 */
@Controller
@RequestMapping("/consultas/avanzadas")
public class ConsultaAvanzadaController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;

    public ConsultaAvanzadaController(ProductoService productoService, CategoriaService categoriaService) {
        this.productoService = productoService;
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public String pantalla(Model model) {
        model.addAttribute("productosAv", List.of());
        model.addAttribute("categoriasAv", List.of());
        return "/consultas/avanzadas";
    }

    // PRODUCTO (3 variantes)
    @PostMapping("/producto/derivada")
    public String productoDerivada(@RequestParam BigDecimal precioMin,
            @RequestParam BigDecimal precioMax,
            @RequestParam Integer existenciasMin,
            @RequestParam String descripcionCategoria,
            Model model) {

        var productos = productoService.consultaProductoAvanzadaDerivada(
                precioMin, precioMax, existenciasMin, descripcionCategoria);

        model.addAttribute("metodoProducto", "Derivada");
        model.addAttribute("productosAv", productos);
        model.addAttribute("categoriasAv", List.of());
        model.addAttribute("precioMin", precioMin);
        model.addAttribute("precioMax", precioMax);
        model.addAttribute("existenciasMin", existenciasMin);
        model.addAttribute("descripcionCategoria", descripcionCategoria);
        return "/consultas/avanzadas";
    }

    @PostMapping("/producto/jpql")
    public String productoJPQL(@RequestParam BigDecimal precioMin,
            @RequestParam BigDecimal precioMax,
            @RequestParam Integer existenciasMin,
            @RequestParam String descripcionCategoria,
            Model model) {

        var productos = productoService.consultaProductoAvanzadaJPQL(
                precioMin, precioMax, existenciasMin, descripcionCategoria);

        model.addAttribute("metodoProducto", "JPQL");
        model.addAttribute("productosAv", productos);
        model.addAttribute("categoriasAv", List.of());
        model.addAttribute("precioMin", precioMin);
        model.addAttribute("precioMax", precioMax);
        model.addAttribute("existenciasMin", existenciasMin);
        model.addAttribute("descripcionCategoria", descripcionCategoria);
        return "/consultas/avanzadas";
    }

    @PostMapping("/producto/sql")
    public String productoSQL(@RequestParam BigDecimal precioMin,
            @RequestParam BigDecimal precioMax,
            @RequestParam Integer existenciasMin,
            @RequestParam String descripcionCategoria,
            Model model) {

        var productos = productoService.consultaProductoAvanzadaSQL(
                precioMin, precioMax, existenciasMin, descripcionCategoria);

        model.addAttribute("metodoProducto", "SQL");
        model.addAttribute("productosAv", productos);
        model.addAttribute("categoriasAv", List.of());
        model.addAttribute("precioMin", precioMin);
        model.addAttribute("precioMax", precioMax);
        model.addAttribute("existenciasMin", existenciasMin);
        model.addAttribute("descripcionCategoria", descripcionCategoria);
        return "/consultas/avanzadas";
    }

    @PostMapping("/categoria/derivada")
    public String categoriaDerivada(@RequestParam Integer cantidadMinProductos,
            @RequestParam String textoDescripcion,
            Model model) {

        List<CategoriaCantidadDTO> categorias = categoriaService
                .consultaCategoriaAvanzadaDerivada(cantidadMinProductos, textoDescripcion);

        model.addAttribute("metodoCategoria", "Derivada");
        model.addAttribute("categoriasAv", categorias);
        model.addAttribute("productosAv", List.of());
        model.addAttribute("cantidadMinProductos", cantidadMinProductos);
        model.addAttribute("textoDescripcion", textoDescripcion);
        return "/consultas/avanzadas";
    }

    @PostMapping("/categoria/jpql")
    public String categoriaJPQL(@RequestParam Integer cantidadMinProductos,
            @RequestParam String textoDescripcion,
            Model model) {

        List<CategoriaCantidadDTO> categorias = categoriaService
                .consultaCategoriaAvanzadaJPQL(cantidadMinProductos, textoDescripcion);

        model.addAttribute("metodoCategoria", "JPQL");
        model.addAttribute("categoriasAv", categorias);
        model.addAttribute("productosAv", List.of());
        model.addAttribute("cantidadMinProductos", cantidadMinProductos);
        model.addAttribute("textoDescripcion", textoDescripcion);
        return "/consultas/avanzadas";
    }

    @PostMapping("/categoria/sql")
    public String categoriaSQL(@RequestParam Integer cantidadMinProductos,
            @RequestParam String textoDescripcion,
            Model model) {

        List<CategoriaCantidadDTO> categorias = categoriaService
                .consultaCategoriaAvanzadaSQL(cantidadMinProductos, textoDescripcion);

        model.addAttribute("metodoCategoria", "SQL");
        model.addAttribute("categoriasAv", categorias);
        model.addAttribute("productosAv", List.of());
        model.addAttribute("cantidadMinProductos", cantidadMinProductos);
        model.addAttribute("textoDescripcion", textoDescripcion);
        return "/consultas/avanzadas";
    }
}
