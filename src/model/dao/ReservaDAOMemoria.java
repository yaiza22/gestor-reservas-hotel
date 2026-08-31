package model.dao;

import model.entidades.Reserva;
import model.enums.EstadoReserva;

import java.time.LocalDate;
import java.util.*;

public class ReservaDAOMemoria implements ReservaDAO {
    private final Map<Integer, Reserva> almacen = new LinkedHashMap<>();

    @Override
    public Reserva crear(Reserva reserva) {
        almacen.put(reserva.getId(), reserva);
        return reserva;
    }

    @Override
    public Reserva buscarPorId(int id) {
        return almacen.get(id);
    }

    @Override
    public List<Reserva> listarTodos() {
        return new ArrayList<>(almacen.values());
    }

    @Override
    public boolean actualizar(Reserva reserva) {
        if (!almacen.containsKey(reserva.getId())) return false;
        almacen.put(reserva.getId(), reserva);
        return true;
    }

    @Override
    public boolean eliminar(int id) {
        return almacen.remove(id) != null;
    }

    @Override
    public boolean verificarDisponibilidad(List<String> habitacionesId, LocalDate fechaInicio, LocalDate fechaFin) {
        for (Reserva r : almacen.values()) {
            //if (!r.getHabitacionId().equals(habitacionId)) continue;
            if (Collections.disjoint(r.getHabitacionesId(), habitacionesId)) continue;
            if (r.getEstado() == EstadoReserva.CANCELADA) continue;
            boolean seCruzan = fechaInicio.isBefore(r.getFechaFin()) && r.getFechaInicio().isBefore(fechaFin);
            if (seCruzan) return false;
        }
        return true;
    }
}
