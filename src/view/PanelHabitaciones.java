package view;

import controller.HabitacionController;
import model.entidades.Habitacion;
import model.enums.EstadoHabitacion;
import model.enums.TipoHabitacion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelHabitaciones extends JPanel {

    private final HabitacionController controller;
    private final DefaultTableModel modeloTabla;
    private final JTable tabla;

    private final JTextField campoId = new JTextField();
    private final JComboBox<TipoHabitacion> campoTipo = new JComboBox<>(TipoHabitacion.values());
    private final JTextField campoPiso = new JTextField();
    private final JTextField campoPrecioBase = new JTextField();
    private final JTextField campoCapacidad = new JTextField();
    private final JComboBox<EstadoHabitacion> campoEstado = new JComboBox<>(EstadoHabitacion.values());

    public PanelHabitaciones(HabitacionController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Tipo", "Piso", "Precio base", "Capacidad", "Estado"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabla = new JTable(modeloTabla);
        tabla.getSelectionModel().addListSelectionListener(e -> cargarSeleccionEnFormulario());
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(construirFormulario(), BorderLayout.SOUTH);

        refrescarTabla();
    }

    private JPanel construirFormulario() {
        JPanel panel = new JPanel(new GridLayout(0, 4, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Datos de la habitacion"));

        panel.add(new JLabel("ID:"));           panel.add(campoId);
        panel.add(new JLabel("Tipo:"));         panel.add(campoTipo);
        panel.add(new JLabel("Piso:"));         panel.add(campoPiso);
        panel.add(new JLabel("Precio base:"));  panel.add(campoPrecioBase);
        panel.add(new JLabel("Capacidad:"));    panel.add(campoCapacidad);
        panel.add(new JLabel("Estado:"));       panel.add(campoEstado);

        JButton btnCrear = new JButton("Crear");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");

        btnCrear.addActionListener(e -> crear());
        btnActualizar.addActionListener(e -> actualizar());
        btnEliminar.addActionListener(e -> eliminar());
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnCrear);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.add(panel, BorderLayout.CENTER);
        contenedor.add(panelBotones, BorderLayout.SOUTH);
        return contenedor;
    }

    private void crear() {
        try {
            Habitacion habitacion = new Habitacion(
                    (TipoHabitacion) campoTipo.getSelectedItem(),
                    Integer.parseInt(campoPiso.getText().trim()),
                    campoId.getText().trim(),
                    Float.parseFloat(campoPrecioBase.getText().trim()),
                    Integer.parseInt(campoCapacidad.getText().trim()),
                    (EstadoHabitacion) campoEstado.getSelectedItem()
                        );
            controller.crear(habitacion);
            refrescarTabla();
            limpiarFormulario();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Piso, precio base y capacidad deben ser numeros.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizar() {
        String id = campoId.getText().trim();
        Habitacion existente = controller.buscar(id);
        if (existente == null) {
            JOptionPane.showMessageDialog(this, "No existe una habitacion con ese ID.");
            return;
        }
        try {
            existente.setTipoHabitacion((TipoHabitacion) campoTipo.getSelectedItem());
            existente.setPiso(Integer.parseInt(campoPiso.getText().trim()));
            existente.setPrecioBase(Float.parseFloat(campoPrecioBase.getText().trim()));
            existente.setCapacidad(Integer.parseInt(campoCapacidad.getText().trim()));
            existente.setEstado((EstadoHabitacion) campoEstado.getSelectedItem());
            controller.actualizar(existente);
            refrescarTabla();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Piso, precio base y capacidad deben ser numeros.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminar() {
        String id = campoId.getText().trim();
        if (controller.eliminar(id)) {
            refrescarTabla();
            limpiarFormulario();
        } else {
            JOptionPane.showMessageDialog(this, "No existe una habitacion con ese ID.");
        }
    }

    private void cargarSeleccionEnFormulario() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) return;
        String id = (String) modeloTabla.getValueAt(fila, 0);
        Habitacion habitacion = controller.buscar(id);
        if (habitacion == null) return;
        campoId.setText(habitacion.getHabitacionId());
        campoTipo.setSelectedItem(habitacion.getTipoHabitacion());
        campoPiso.setText(String.valueOf(habitacion.getPiso()));
        campoPrecioBase.setText(String.valueOf(habitacion.getPrecioBase()));
        campoCapacidad.setText(String.valueOf(habitacion.getCapacidad()));
        campoEstado.setSelectedItem(habitacion.getEstado());
    }

    private void limpiarFormulario() {
        campoId.setText("");
        campoPiso.setText("");
        campoPrecioBase.setText("");
        campoCapacidad.setText("");
        tabla.clearSelection();
    }

    private void refrescarTabla() {
        modeloTabla.setRowCount(0);
        List<Habitacion> habitaciones = controller.listar();
        for (Habitacion h : habitaciones) {
            modeloTabla.addRow(new Object[]{h.getHabitacionId(), h.getTipoHabitacion(), h.getPiso(),
                    h.getPrecioBase(), h.getCapacidad(), h.getEstado()});
        }
    }
}
