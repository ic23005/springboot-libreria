package com.ic23005.springboot_libreria.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 100)
    private String apellido;

    @Column(unique = true, length = 100)
    private String email;

    @Column(length = 200)
    private String direccion;

    // Un cliente puede tener muchas ventas.
    // mappedBy="cliente" indica que la FK está en la entidad Venta, en el campo "cliente".
    // CascadeType.ALL: operaciones en Cliente se propagan a Venta.
    // orphanRemoval=true: si una Venta se elimina de esta lista, se borra de la BD.
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Venta> ventas = new ArrayList<>();

    // Constructores
    public Cliente() {
    }

    public Cliente(String nombre, String apellido, String email, String direccion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.direccion = direccion;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<Venta> getVentas() {
        return ventas;
    }

    public void setVentas(List<Venta> ventas) {
        this.ventas = ventas;
    }

    // Métodos helper para la relación bidireccional con Venta
    public void addVenta(Venta venta) {
        this.ventas.add(venta);
        venta.setCliente(this);
    }

    public void removeVenta(Venta venta) {
        this.ventas.remove(venta);
        venta.setCliente(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(id, cliente.id) && Objects.equals(email, cliente.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }

    @Override
    public String toString() {
        return "Cliente{" +
               "id=" + id +
               ", nombre='" + nombre + '\'' +
               ", apellido='" + apellido + '\'' +
               ", email='" + email + '\'' +
               ", direccion='" + direccion + '\'' +
               '}';
    }
}