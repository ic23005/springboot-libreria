package com.ic23005.springboot_libreria.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "detalles_venta")
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Muchos detalles de venta pertenecen a una venta.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venta_id", nullable = false)
    private Venta venta;

    // Muchos detalles de venta (de diferentes ventas) pueden referirse a un libro.
    // Usualmente, un detalle de venta se refiere a UN libro específico.
    @ManyToOne(fetch = FetchType.EAGER) // EAGER para tener info del libro al cargar el detalle
    @JoinColumn(name = "libro_id", nullable = false)
    private Libro libro;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(name = "precio_unitario", nullable = false)
    private Double precioUnitario; // Precio del libro al momento de la venta

    // Constructores
    public DetalleVenta() {
    }

    public DetalleVenta(Libro libro, Integer cantidad) {
        this.libro = libro;
        this.cantidad = cantidad;
        if (libro != null) {
            this.precioUnitario = libro.getPrecio(); // Captura el precio actual del libro
        }
    }

    public DetalleVenta(Venta venta, Libro libro, Integer cantidad) {
        this.venta = venta;
        this.libro = libro;
        this.cantidad = cantidad;
        if (libro != null) {
            this.precioUnitario = libro.getPrecio(); // Captura el precio actual del libro
        }
    }


    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
        // Si el libro cambia, y el precio unitario no fue fijado por otro medio, actualizarlo.
        // Esto es opcional y depende de la lógica de negocio si se permite cambiar el libro de un detalle ya creado.
        if (this.precioUnitario == null && libro != null) {
            this.precioUnitario = libro.getPrecio();
        }
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    // Se debe permitir fijar el precio unitario, ya que el precio del libro puede cambiar.
    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    // Método calculado, no persistido directamente pero útil
    @Transient // No se mapea a una columna en la base de datos
    public Double getSubtotal() {
        if (this.precioUnitario == null || this.cantidad == null) {
            return 0.0;
        }
        return this.precioUnitario * this.cantidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DetalleVenta that = (DetalleVenta) o;
        return Objects.equals(id, that.id); // Usualmente el ID es suficiente para entidades persistidas
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "DetalleVenta{" +
               "id=" + id +
               ", libro=" + (libro != null ? libro.getTitulo() : "null") +
               ", cantidad=" + cantidad +
               ", precioUnitario=" + precioUnitario +
               '}';
    }
}
