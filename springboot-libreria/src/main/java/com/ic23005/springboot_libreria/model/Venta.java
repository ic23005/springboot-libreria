package com.ic23005.springboot_libreria.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "ventas")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_venta", nullable = false)
    private LocalDateTime fechaVenta;

    // Muchas ventas pueden pertenecer a un cliente.
    @ManyToOne(fetch = FetchType.LAZY) // LAZY para no cargar el cliente a menos que se necesite explícitamente
    @JoinColumn(name = "cliente_id", nullable = false) // FK en la tabla ventas
    private Cliente cliente;

    // Una venta tiene muchos detalles de venta.
    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER) // EAGER para cargar los detalles con la venta
    private List<DetalleVenta> detallesVenta = new ArrayList<>();

    @Column(name = "total_venta", nullable = false)
    private Double totalVenta;

    // Constructores
    public Venta() {
        this.fechaVenta = LocalDateTime.now(); // Fecha actual por defecto
        this.totalVenta = 0.0;
    }

    public Venta(Cliente cliente) {
        this();
        this.cliente = cliente;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(LocalDateTime fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<DetalleVenta> getDetallesVenta() {
        return detallesVenta;
    }

    public void setDetallesVenta(List<DetalleVenta> detallesVenta) {
        this.detallesVenta = detallesVenta;
        // Recalcular total si se establece la lista directamente
        this.recalcularTotalVenta();
    }

    public Double getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(Double totalVenta) {
        this.totalVenta = totalVenta;
    }

    // Métodos helper para gestionar la relación bidireccional con DetalleVenta y calcular total
    public void addDetalleVenta(DetalleVenta detalle) {
        this.detallesVenta.add(detalle);
        detalle.setVenta(this);
        recalcularTotalVenta();
    }

    public void removeDetalleVenta(DetalleVenta detalle) {
        this.detallesVenta.remove(detalle);
        detalle.setVenta(null);
        recalcularTotalVenta();
    }

    public void recalcularTotalVenta() {
        this.totalVenta = this.detallesVenta.stream()
                .mapToDouble(dv -> dv.getPrecioUnitario() * dv.getCantidad())
                .sum();
    }

    @PrePersist
    @PreUpdate
    private void onUpdateOrPersist() {
        recalcularTotalVenta(); // Asegura que el total esté calculado antes de guardar
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Venta venta = (Venta) o;
        return Objects.equals(id, venta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Venta{" +
               "id=" + id +
               ", fechaVenta=" + fechaVenta +
               ", clienteId=" + (cliente != null ? cliente.getId() : "null") +
               ", totalVenta=" + totalVenta +
               ", numeroDetalles=" + detallesVenta.size() +
               '}';
    }
}
