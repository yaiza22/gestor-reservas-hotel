package model.entidades;

import model.enums.EstadoHabitacion;
import model.enums.Temporada;
import model.enums.TipoHabitacion;

public class Habitacion {
    private String habitacionId;
    private TipoHabitacion tipoHabitacion;
    private int piso;
    private float precioBase;
    private int capacidad;
    private EstadoHabitacion estado;

    // Constructor
    public Habitacion(TipoHabitacion tipoHabitacion, int piso, String habitacionId, float precioBase,
                      int capacidad, EstadoHabitacion estado) {
        this.tipoHabitacion = tipoHabitacion;
        this.piso = piso;
        this.habitacionId = habitacionId;
        this.precioBase = precioBase;
        this.capacidad = capacidad;
        this.estado = (estado != null) ? estado : EstadoHabitacion.DISPONIBLE;
    }

    public float calcularPrecio(Temporada temporada) {
        float multiplicadorTipo = switch (tipoHabitacion) {
            case INDIVIDUAL -> 1.0f;
            case DOBLE, TWIN -> 1.4f;   //aumento 40%
            case TRIPLE -> 1.8f;        //aumento 80%
            case FAMILIAR -> 2.2f;      //aumento 120%
        };
        float multiplicadorTemporada = (temporada == Temporada.ALTA) ? 1.3f : 1.0f; //aumento temp. alta 30%
        return precioBase * multiplicadorTipo * multiplicadorTemporada;
    }

    // Getters y setters
    public String getHabitacionId() {
        return habitacionId;
    }
    public void setHabitacionId(String habitacionId) {
        this.habitacionId = habitacionId;
    }
    public TipoHabitacion getTipoHabitacion() {
        return tipoHabitacion;
    }
    public void setTipoHabitacion(TipoHabitacion tipoHabitacion) {
        this.tipoHabitacion = tipoHabitacion;
    }
    public int getPiso() {
        return piso;
    }
    public void setPiso(int piso) {
        this.piso = piso;
    }
    public EstadoHabitacion getEstado() {
        return estado;
    }
    public void setEstado(EstadoHabitacion estado) {
        this.estado = estado;
    }
    public float getPrecioBase() {
        return precioBase;
    }
    public void setPrecioBase(float precioBase) {
        this.precioBase = precioBase;
    }
    public int getCapacidad() {
        return capacidad;
    }
    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    @Override
    public String toString() {
        return habitacionId + " - " + tipoHabitacion + " (piso " + piso + ") - " + estado;
    }
}
