package model.dao;

import model.entidades.Reserva;

import java.time.LocalDate;
import java.util.List;

public interface ReservaDAO {
    Reserva crear(Reserva reserva);
    Reserva buscarPorId(int id);
    List<Reserva> listarTodos();
    boolean actualizar(Reserva reserva);
    boolean eliminar(int id);

    boolean verificarDisponibilidad(List<String> habitacionesId, LocalDate fechaInicio, LocalDate fechaFin);
}
