package controller;

import model.entidades.Habitacion;
import model.facade.IReservasFacade;
import java.util.List;

public class HabitacionController {
    private final IReservasFacade facade;

    public HabitacionController(IReservasFacade facade) {
        this.facade = facade;
    }

    public Habitacion crear(Habitacion habitacion) { return facade.registrarHabitacion(habitacion); }
    public Habitacion buscar(String id) { return facade.buscarHabitacion(id); }
    public List<Habitacion> listar() { return facade.listarHabitaciones(); }
    public boolean actualizar(Habitacion habitacion) { return facade.actualizarHabitacion(habitacion); }
    public boolean eliminar(String id) { return facade.eliminarHabitacion(id); }
}
