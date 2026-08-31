package model.dao;

import model.entidades.Habitacion;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HabitacionDAOMemoria implements HabitacionDAO {
    private final Map<String, Habitacion> almacen = new LinkedHashMap<>();

    @Override
    public Habitacion crear(Habitacion habitacion) {
        almacen.put(habitacion.getHabitacionId(), habitacion);
        return habitacion;
    }

    @Override
    public Habitacion buscarPorId(String habitacionId) {
        return almacen.get(habitacionId);
    }

    @Override
    public List<Habitacion> listarTodos() {
        return new ArrayList<>(almacen.values());
    }

    @Override
    public boolean actualizar(Habitacion habitacion) {
        if (!almacen.containsKey(habitacion.getHabitacionId())) return false;
        almacen.put(habitacion.getHabitacionId(), habitacion);
        return true;
    }

    @Override
    public boolean eliminar(String habitacionId) {
        return almacen.remove(habitacionId) != null;
    }
}
