package model.facade;

import model.entidades.*;
import model.enums.Rol;
import model.enums.TipoDocIdentidad;

import java.time.LocalDate;
import java.util.List;

public interface IReservasFacade {
    // Usuario
    Usuario registrarUsuario(String tipoUsuario, String id, TipoDocIdentidad tipoDoc, String numDoc,
                             String nombre, String telefono, String correo, String password, Rol rol,
                             String nacionalidad, String paisResidencia);
    Usuario buscarUsuario(String id);
    List<Usuario> listarUsuarios();
    boolean actualizarUsuario(Usuario usuario);
    boolean eliminarUsuario(String id);

    // Reserva
    Reserva registrarReserva(Reserva reserva, List<Habitacion> habitaciones);
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
