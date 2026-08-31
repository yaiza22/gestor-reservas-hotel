package view;

import controller.*;
import model.entidades.Usuario;
import model.enums.*;
import model.factory.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelUsuarios extends JPanel {
    private final UsuarioController controller;
    private final DefaultTableModel modeloTabla;
    private final JTable tabla;

    private final JTextField campoId = new JTextField();
    private final JComboBox<TipoDocIdentidad> campoTipoDoc = new JComboBox<>(TipoDocIdentidad.values());
    private final JTextField campoNumDoc = new JTextField();
    private final JTextField campoNombre = new JTextField();
    private final JTextField campoTelefono = new JTextField();
    private final JTextField campoCorreo = new JTextField();
    private final JPasswordField campoPassword = new JPasswordField();
    private final JComboBox<String> campoTipoUsuario = new JComboBox<>(new String[]{"CLIENTE", "EMPLEADO"});
    private final JComboBox<Rol> campoRol = new JComboBox<>(Rol.values());

    public PanelUsuarios(UsuarioController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Tipo", "Nombre", "Documento", "Correo"}, 0) {
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
        panel.setBorder(BorderFactory.createTitledBorder("Datos del usuario"));

        panel.add(new JLabel("ID:"));                 panel.add(campoId);
        panel.add(new JLabel("Tipo doc.:"));          panel.add(campoTipoDoc);
        panel.add(new JLabel("Num. doc.:"));          panel.add(campoNumDoc);
        panel.add(new JLabel("Nombre:"));             panel.add(campoNombre);
        panel.add(new JLabel("Telefono:"));           panel.add(campoTelefono);
        panel.add(new JLabel("Correo:"));             panel.add(campoCorreo);
        panel.add(new JLabel("Password:"));           panel.add(campoPassword);
        panel.add(new JLabel("Tipo usuario:"));       panel.add(campoTipoUsuario);
        panel.add(new JLabel("Rol (si Empleado):"));  panel.add(campoRol);

        JButton btnCrear = new JButton("Crear");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");

        btnCrear.addActionListener(e -> crearUsuario());
        btnActualizar.addActionListener(e -> actualizarUsuario());
        btnEliminar.addActionListener(e -> eliminarUsuario());
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

    private void crearUsuario() {
        try {
            Usuario usuario = UsuarioFactory.crear(
                    (String) campoTipoUsuario.getSelectedItem(),
                    campoId.getText().trim(),
                    (TipoDocIdentidad) campoTipoDoc.getSelectedItem(),
                    campoNumDoc.getText().trim(),
                    campoNombre.getText().trim(),
                    campoTelefono.getText().trim(),
                    campoCorreo.getText().trim(),
                    new String(campoPassword.getPassword()),
                    (Rol) campoRol.getSelectedItem()
            );
            controller.crear(usuario);
            refrescarTabla();
            limpiarFormulario();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al crear: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarUsuario() {
        String id = campoId.getText().trim();
        Usuario existente = controller.buscar(id);
        if (existente == null) {
            JOptionPane.showMessageDialog(this, "No existe un usuario con ese ID.");
            return;
        }
        existente.actualizarDatos(campoNombre.getText().trim(), campoTelefono.getText().trim(), campoCorreo.getText().trim());
        controller.actualizar(existente);
        refrescarTabla();
    }

    private void eliminarUsuario() {
        String id = campoId.getText().trim();
        if (controller.eliminar(id)) {
            refrescarTabla();
            limpiarFormulario();
        } else {
            JOptionPane.showMessageDialog(this, "No existe un usuario con ese ID.");
        }
    }

    private void cargarSeleccionEnFormulario() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) return;
        String id = (String) modeloTabla.getValueAt(fila, 0);
        Usuario usuario = controller.buscar(id);
        if (usuario == null) return;
        campoId.setText(usuario.getId());
        campoTipoDoc.setSelectedItem(usuario.getTipoDocIdentidad());
        campoNumDoc.setText(usuario.getNumDocIdentidad());
        campoNombre.setText(usuario.getNombre());
        campoTelefono.setText(usuario.getTelefono());
        campoCorreo.setText(usuario.getCorreo());
        campoTipoUsuario.setSelectedItem(usuario.getTipoUsuario());
    }

    private void limpiarFormulario() {
        campoId.setText("");
        campoNumDoc.setText("");
        campoNombre.setText("");
        campoTelefono.setText("");
        campoCorreo.setText("");
        campoPassword.setText("");
        tabla.clearSelection();
    }

    private void refrescarTabla() {
        modeloTabla.setRowCount(0);
        List<Usuario> usuarios = controller.listar();
        for (Usuario u : usuarios) {
            modeloTabla.addRow(new Object[]{u.getId(), u.getTipoUsuario(), u.getNombre(), u.getNumDocIdentidad(), u.getCorreo()});
        }
    }
}
