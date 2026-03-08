package com.tienda.dto;

public class CategoriaCantidadDTO {

    private Integer idCategoria;
    private String descripcion;
    private Long cantidad;

    public CategoriaCantidadDTO() {
    }

    public CategoriaCantidadDTO(Integer idCategoria, String descripcion, Long cantidad) {
        this.idCategoria = idCategoria;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }
}