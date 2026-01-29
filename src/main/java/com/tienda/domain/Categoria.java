/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda.domain;

import jakarta.persistence.Column;
import jakarta.persistence. Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table (name ="categoria")
public class Categoria implements Serializable {
// Se recomienda añadir un serialVersionUID
private static final long serialVersionUID = 1L;
@Id
@GeneratedValue (strategy = GenerationType.IDENTITY)
@Column(name = "id_categoria")
public Integer idCategoria;

@Column (unique = true, nullable = false, length = 50)
@NotNull
@Size (max = 50)
public String descripcion;

@Column (length = 1024)
@Size (max = 1024)
public String rutaImagen;

@Column (name = "activo")
public Boolean activo;

}

