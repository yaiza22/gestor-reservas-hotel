package model.facade;

import model.entidades.*;

import java.time.LocalDate;
import java.util.List;

public interface IReservasFacade {
    // Usuario
    Usuario registrarUsuario(Usuario usuario);
    Usuario buscarUsuario(String id);
    List<Usuario> listarUsuarios();
    boolean actualizarUsuario(Usuario usuario);
    boolean eliminarUsuario(String id);

    // Reserva
    Reserva registrarReserva(Reserva reserva, Habitacion habitacion);
    Reserva buscarReserva(int id);
    List<Reserva> listarReservas();
    boolean actualizarReserva(Reserva reserva);
    boolean eliminarReserva(int id);
    boolean verificarDisponibilidad(List<String> habitacionesId, LocalDate fechaInicio, LocalDate fechaFin);

    // Habitacion
    Habitacion registrarHabitacion(Habitacion habitacion);
    Habitacion buscarHabitacion(String id);
    List<Habitacion> listarHabitaciones();
    boolean actualizarHabitacion(Habitacion habitacion);
    boolean eliminarHabitacion(String id);
}
