package model.facade;

import model.dao.*;
import model.entidades.*;
import model.enums.Rol;
import model.enums.TipoDocIdentidad;
import model.factory.UsuarioFactory;

import java.time.LocalDate;
import java.util.List;

public class ReservasFacade implements IReservasFacade {
    private final UsuarioDAO usuarioDAO;
    private final ReservaDAO reservaDAO;
    private final HabitacionDAO habitacionDAO;

    public ReservasFacade(UsuarioDAO usuarioDAO, ReservaDAO reservaDAO, HabitacionDAO habitacionDAO) {
        this.usuarioDAO = usuarioDAO;
        this.reservaDAO = reservaDAO;
        this.habitacionDAO = habitacionDAO;
    }

    @Override
    public Usuario registrarUsuario(String tipoUsuario, String id, TipoDocIdentidad tipoDoc, String numDoc,
                                    String nombre, String telefono, String correo, String password, Rol rol,
                                    String nacionalidad, String paisResidencia) {
        Usuario usuario = UsuarioFactory.crear(tipoUsuario, id, tipoDoc, numDoc, nombre, telefono, correo, password, rol, nacionalidad, paisResidencia);
        return usuarioDAO.crear(usuario);
    }

    @Override
    public Usuario buscarUsuario(String id) { return usuarioDAO.buscarPorId(id); }

    @Override
    public List<Usuario> listarUsuarios() { return usuarioDAO.listarTodos(); }

    @Override
    public boolean actualizarUsuario(Usuario usuario) { return usuarioDAO.actualizar(usuario); }

    @Override
    public boolean eliminarUsuario(String id) { return usuarioDAO.eliminar(id); }

    @Override
    public Reserva registrarReserva(Reserva reserva, List<Habitacion> habitaciones) {
        if (!verificarDisponibilidad(reserva.getHabitacionesId(), reserva.getFechaInicio(), reserva.getFechaFin())) {
            throw new IllegalStateException("La habitacion no está disponible en esas fechas");
        }
        reserva.calcularPrecio(habitaciones);
        return reservaDAO.crear(reserva);
    }

    @Override
    public Reserva buscarReserva(int id) { return reservaDAO.buscarPorId(id); }

    @Override
    public List<Reserva> listarReservas() { return reservaDAO.listarTodos(); }

    @Override
    public boolean actualizarReserva(Reserva reserva) { return reservaDAO.actualizar(reserva); }

    @Override
    public boolean eliminarReserva(int id) { return reservaDAO.eliminar(id); }

    @Override
    public boolean verificarDisponibilidad(List<String> habitacionesId, LocalDate fechaInicio, LocalDate fechaFin) {
        return reservaDAO.verificarDisponibilidad(habitacionesId, fechaInicio, fechaFin);
    }

    @Override
    public Habitacion registrarHabitacion(Habitacion habitacion) { return habitacionDAO.crear(habitacion); }

    @Override
    public Habitacion buscarHabitacion(String id) { return habitacionDAO.buscarPorId(id); }

    @Override
    public List<Habitacion> listarHabitaciones() { return habitacionDAO.listarTodos(); }

    @Override
    public boolean actualizarHabitacion(Habitacion habitacion) { return habitacionDAO.actualizar(habitacion); }

    @Override
    public boolean eliminarHabitacion(String id) { return habitacionDAO.eliminar(id); }
}
