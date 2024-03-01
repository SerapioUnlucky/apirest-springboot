package com.bolsasdete.springboot.apirestspringboot.Models;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "customers")
public class CustomerModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "El nombre no puede estar vacío")
    @NotBlank(message = "El nombre no puede estar vacío")
    @Pattern(regexp = "^[A-Za-záéíóúÁÉÍÓÚüÜñÑ]*$", message = "El nombre solo puede contener letras")
    @Size(min = 4, max = 12, message = "El tamaño del nombre debe estar entre 4 y 12 caracteres")
    @Column(nullable = false)
    private String name;

    @NotEmpty(message = "El apellido no puede estar vacío")
    @NotBlank(message = "El apellido no puede estar vacío")
    @Pattern(regexp = "^[A-Za-záéíóúÁÉÍÓÚüÜñÑ]*$", message = "El apellido solo puede contener letras")
    @Size(min = 4, max = 12, message = "El tamaño del apellido debe estar entre 4 y 12 caracteres")
    @Column(nullable = false)
    private String lastname;

    @NotEmpty(message = "El email no puede estar vacío")
    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El email no esta en un formato válido")
    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "created_at")
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    private String photo;

    @NotNull(message = "La región no puede estar vacía")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private RegionModel region;

    @PrePersist
    public void prePersist() {

        createdAt = new Date();

    }

    public Long getId() {

        return id;

    }

    public void setId(Long id) {

        this.id = id;

    }

    public String getName() {

        return name;

    }

    public void setName(String name) {

        this.name = name;

    }

    public String getLastname() {

        return lastname;

    }

    public void setLastname(String lastname) {

        this.lastname = lastname;

    }

    public String getEmail() {

        return email;

    }

    public void setEmail(String email) {

        this.email = email;

    }

    public Date getCreatedAt() {

        return createdAt;

    }

    public void setCreatedAt(Date createdAt) {

        this.createdAt = createdAt;

    }

    public String getPhoto() {

        return photo;

    }

    public void setPhoto(String photo) {

        this.photo = photo;

    }

    public RegionModel getRegion() {

        return region;

    }

    public void setRegion(RegionModel region) {

        this.region = region;

    }

    private static final long serialVersionUID = 1L;

}
