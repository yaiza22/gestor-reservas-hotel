package model.dao;

import model.entidades.Habitacion;

import java.util.List;

public interface HabitacionDAO {
    Habitacion crear(Habitacion habitacion);
    Habitacion buscarPorId(String habitacionId);
    List<Habitacion> listarTodos();
    boolean actualizar(Habitacion habitacion);
    boolean eliminar(String habitacionId);
}
