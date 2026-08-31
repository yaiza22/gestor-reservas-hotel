package model.entidades;

import model.enums.EstadoReserva;
import model.enums.Temporada;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Reserva {
    private int id;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private float precioTotal;
    private EstadoReserva estado;
    private LocalTime horaEntrada;
    private LocalTime horaSalida;
    private int cantHuespedes;
    private Temporada temporada;
    private String clienteId;
    //private String habitacionId;
    private List<String> habitacionesId;

    // Constructor
    public Reserva(int id, LocalDate fechaInicio, LocalDate fechaFin, int cantHuespedes,
                   Temporada temporada, String clienteId, List<String> habitacionesId) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = EstadoReserva.PENDIENTE;
        this.cantHuespedes = cantHuespedes;
        this.temporada = temporada;
        this.clienteId = clienteId;
        this.habitacionesId = habitacionesId;
    }



    public float calcularPrecio(List<Habitacion> habitaciones) {
        long noches = ChronoUnit.DAYS.between(fechaInicio, fechaFin);
        if (noches < 1) noches = 1;
        float sumaTotal = 0;
        for (Habitacion h : habitaciones) {
            sumaTotal += h.calcularPrecio(temporada) * noches;
        }
        this.precioTotal = sumaTotal;
        return precioTotal;
    }

    public boolean confirmar() {
        if (estado != EstadoReserva.PENDIENTE) return false;
        estado = EstadoReserva.CONFIRMADA;
        return true;
    }

    public boolean cancelar() {
        if (estado == EstadoReserva.ENCURSO || estado == EstadoReserva.FINALIZADA || estado == EstadoReserva.CANCELADA) return false;
        estado = EstadoReserva.CANCELADA;
        return true;
    }

    public boolean realizarCheckin() {
        if (estado != EstadoReserva.CONFIRMADA) return false;
        estado = EstadoReserva.ENCURSO;
        horaEntrada = LocalTime.now();
        return true;
    }

    public boolean realizarCheckout() {
        if (estado != EstadoReserva.ENCURSO) return false;
        estado = EstadoReserva.FINALIZADA;
        horaSalida = LocalTime.now();
        return true;
    }

    // Getters y setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }
    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    public LocalDate getFechaFin() {
        return fechaFin;
    }
    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
    public float getPrecioTotal() {
        return precioTotal;
    }
    public void setPrecioTotal(float precioTotal) {
        this.precioTotal = precioTotal;
    }
    public EstadoReserva getEstado() {
        return estado;
    }
    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }
    public LocalTime getHoraEntrada() {
        return horaEntrada;
    }
    public void setHoraEntrada(LocalTime horaEntrada) {
        this.horaEntrada = horaEntrada;
    }
    public LocalTime getHoraSalida() {
        return horaSalida;
    }
    public void setHoraSalida(LocalTime horaSalida) {
        this.horaSalida = horaSalida;
    }
    public int getCantHuespedes() {
        return cantHuespedes;
    }
    public void setCantHuespedes(int cantHuespedes) {
        this.cantHuespedes = cantHuespedes;
    }
    public Temporada getTemporada() {
        return temporada;
    }
    public void setTemporada(Temporada temporada) {
        this.temporada = temporada;
    }
    public String getClienteId() {
        return clienteId;
    }
    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }
    public List<String> getHabitacionesId() {
        return habitacionesId;
    }

    public void setHabitacionesId(List<String> habitacionesId) {
        this.habitacionesId = habitacionesId;
    }

    @Override
    public String toString() {
        return "Reserva #" + id + " [" + fechaInicio + " a " + fechaFin + "] - " + estado;
    }



}
