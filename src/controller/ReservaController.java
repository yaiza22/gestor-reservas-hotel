package controller;

import model.entidades.*;
import model.facade.IReservasFacade;

import java.time.LocalDate;
import java.util.List;

public class ReservaController {
    private final IReservasFacade facade;

    public ReservaController(IReservasFacade facade) {
        this.facade = facade;
    }

    public Reserva crear(Reserva reserva, List<Habitacion> habitaciones) { return facade.registrarReserva(reserva, habitaciones); }
    public Reserva buscar(int id) { return facade.buscarReserva(id); }
    public List<Reserva> listar() { return facade.listarReservas(); }
    public boolean actualizar(Reserva reserva) { return facade.actualizarReserva(reserva); }
    public boolean eliminar(int id) { return facade.eliminarReserva(id); }
    public boolean verificarDisponibilidad(List<String> habitacionesId, LocalDate inicio, LocalDate fin) {
        return facade.verificarDisponibilidad(habitacionesId, inicio, fin);
    }
}
