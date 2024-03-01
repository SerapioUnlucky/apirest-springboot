package com.bolsasdete.springboot.apirestspringboot.Models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "regions")
public class RegionModel implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "El nombre no puede estar vacío")
    @NotBlank(message = "El nombre no puede estar vacío")
    @Pattern(regexp = "^[A-Za-záéíóúÁÉÍÓÚüÜñÑ]*$", message = "El nombre solo puede contener letras")
    @Size(min = 4, max = 12, message = "El tamaño del nombre debe estar entre 4 y 12 caracteres")
    @Column(nullable = false)
    private String name;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    private static final long serialVersionUID = 1L;

}
