package view;

import controller.HabitacionController;
import controller.ReservaController;
import model.entidades.Habitacion;
import model.entidades.Reserva;
import model.enums.Temporada;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

public class PanelReservas extends JPanel {

    private final ReservaController reservaController;
    private final HabitacionController habitacionController;
    private final DefaultTableModel modeloTabla;
    private final JTable tabla;

    private final JTextField campoId = new JTextField();
    private final JTextField campoClienteId = new JTextField();
    private final JTextField campoHabitacionesId = new JTextField();
    private final JTextField campoFechaInicio = new JTextField("aaaa-mm-dd");
    private final JTextField campoFechaFin = new JTextField("aaaa-mm-dd");
    private final JTextField campoCantHuespedes = new JTextField();
    private final DefaultListModel<String> modeloListaHabitaciones = new DefaultListModel<>();
    private final JList<String> listaHabitacionesVisual = new JList<>(modeloListaHabitaciones);
    private final JComboBox<Temporada> campoTemporada = new JComboBox<>(Temporada.values());

    public PanelReservas(ReservaController reservaController, HabitacionController habitacionController) {
        this.reservaController = reservaController;
        this.habitacionController = habitacionController;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Cliente", "Habitacion", "Inicio", "Fin", "Estado", "Precio"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabla = new JTable(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(construirFormulario(), BorderLayout.SOUTH);

        refrescarTabla();
    }

    private JPanel construirFormulario() {
        JPanel panel = new JPanel(new GridLayout(0, 4, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Datos de la reserva"));

        panel.add(new JLabel("ID (numero):"));   panel.add(campoId);
        panel.add(new JLabel("Cliente ID:"));    panel.add(campoClienteId);
        // --- NUEVO: Sub-panel para empaquetar el Input + Botones de Habitación ---
        JPanel panelAccionesHabitacion = new JPanel(new BorderLayout(2, 0));
        panelAccionesHabitacion.add(campoHabitacionesId, BorderLayout.CENTER);

        JPanel panelBotonesLista = new JPanel(new GridLayout(1, 2, 2, 0));
        panelBotonesLista.add(btnAgregarHabitaciones);
        panelBotonesLista.add(btnEliminarHabitaciones);
        panelAccionesHabitacion.add(panelBotonesLista, BorderLayout.EAST);

        // Agregamos la etiqueta y el sub-panel al GridLayout
        panel.add(new JLabel("Habitacion ID:"));
        panel.add(panelAccionesHabitacion);

        // --- NUEVO: Agregar la lista visual debajo en el formulario ---
        panel.add(new JLabel("Lista Habitaciones:"));
        listaHabitacionesVisual.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollLista = new JScrollPane(listaHabitacionesVisual);
        scrollLista.setPreferredSize(new Dimension(100, 50)); // Altura compacta para el Grid
        panel.add(scrollLista);

        // Eventos para la lista de habitaciones
        java.awt.event.ActionListener accionAgregar = e -> {
            String id = campoHabitacionesId.getText().trim();
            if (!id.isEmpty() && !modeloListaHabitaciones.contains(id)) {
                modeloListaHabitaciones.addElement(id);
                campoHabitacionesId.setText("");
            }
            campoHabitacionesId.requestFocus();
        };
        btnAgregarHabitaciones.addActionListener(accionAgregar);
        campoHabitacionesId.addActionListener(accionAgregar); // Enter en el teclado

        btnEliminarHabitaciones.addActionListener(e -> {
            int index = listaHabitacionesVisual.getSelectedIndex();
            if (index != -1) {
                modeloListaHabitaciones.remove(index);
            }
        });
        // --- Fin de la sección de habitaciones ---
        panel.add(new JLabel("Fecha inicio:"));  panel.add(campoFechaInicio);
        panel.add(new JLabel("Fecha fin:"));     panel.add(campoFechaFin);
        panel.add(new JLabel("Huespedes:"));     panel.add(campoCantHuespedes);
        panel.add(new JLabel("Temporada:"));     panel.add(campoTemporada);



        JButton btnCrear = new JButton("Crear reserva");
        JButton btnConfirmar = new JButton("Confirmar");
        JButton btnCheckin = new JButton("Check-in");
        JButton btnCheckout = new JButton("Check-out");
        JButton btnCancelar = new JButton("Cancelar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnAgregarHabitacion = new JButton("+");
        JButton btnEliminarHabitacion = new JButton("Eliminar");

        btnCrear.addActionListener(e -> crear());
        btnConfirmar.addActionListener(e -> cambiarEstado(Reserva::confirmar));
        btnCheckin.addActionListener(e -> cambiarEstado(Reserva::realizarCheckin));
        btnCheckout.addActionListener(e -> cambiarEstado(Reserva::realizarCheckout));
        btnCancelar.addActionListener(e -> cambiarEstado(Reserva::cancelar));
        btnEliminar.addActionListener(e -> eliminar());

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnCrear);
        panelBotones.add(btnConfirmar);
        panelBotones.add(btnCheckin);
        panelBotones.add(btnCheckout);
        panelBotones.add(btnCancelar);
        panelBotones.add(btnEliminar);

        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.add(panel, BorderLayout.CENTER);
        contenedor.add(panelBotones, BorderLayout.SOUTH);
        return contenedor;
    }

    private void crear() {
        try {
            String habitacionId = campoHabitacionesId.getText().trim();
            Habitacion habitacion = habitacionController.buscar(habitacionId);
            if (habitacion == null) {
                JOptionPane.showMessageDialog(this, "No existe una habitacion con ese ID.");
                return;
            }
            Reserva reserva = new Reserva(
                    Integer.parseInt(campoId.getText().trim()),
                    LocalDate.parse(campoFechaInicio.getText().trim()),
                    LocalDate.parse(campoFechaFin.getText().trim()),
                    Integer.parseInt(campoCantHuespedes.getText().trim()),
                    (Temporada) campoTemporada.getSelectedItem(),
                    campoClienteId.getText().trim(),
                    habitacionId
            );
            reservaController.crear(reserva, habitacion);
            refrescarTabla();
        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "No disponible", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Revisa los datos (formato de fecha: aaaa-mm-dd).",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cambiarEstado(Predicate<Reserva> accion) {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona una reserva de la tabla.");
            return;
        }
        int id = (int) modeloTabla.getValueAt(fila, 0);
        Reserva reserva = reservaController.buscar(id);
        if (reserva == null) return;
        boolean ok = accion.test(reserva);
        if (!ok) {
            JOptionPane.showMessageDialog(this, "Esa transicion no es valida desde el estado actual (" + reserva.getEstado() + ").");
            return;
        }
        reservaController.actualizar(reserva);
        refrescarTabla();
    }

    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) return;
        int id = (int) modeloTabla.getValueAt(fila, 0);
        reservaController.eliminar(id);
        refrescarTabla();
    }

    private void refrescarTabla() {
        modeloTabla.setRowCount(0);
        List<Reserva> reservas = reservaController.listar();
        for (Reserva r : reservas) {
            modeloTabla.addRow(new Object[]{r.getId(), r.getClienteId(), r.getHabitacionesId(),
                    r.getFechaInicio(), r.getFechaFin(), r.getEstado(), r.getPrecioTotal()});
        }
    }
}
