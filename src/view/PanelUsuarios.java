package view;

import controller.*;
import model.entidades.*;
import model.enums.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;

public class PanelUsuarios extends JPanel {
    private final UsuarioController controller;
    private final DefaultTableModel modeloTabla;
    private final JTable tabla;
    private final JTextField campoId = new JTextField();
    //private final JComboBox<TipoDocIdentidad> campoTipoDoc = new JComboBox<>(TipoDocIdentidad.values());
    private final JComboBox<Object> campoTipoDoc = new JComboBox<>();
    private final JTextField campoNumDoc = new JTextField();
    private final JTextField campoNombre = new JTextField();
    private final JTextField campoTelefono = new JTextField();
    private final JTextField campoCorreo = new JTextField();
    private final JPasswordField campoPassword = new JPasswordField();
    private final JComboBox<String> campoTipoUsuario = new JComboBox<>(new String[]{"", "CLIENTE", "EMPLEADO"});
    //private final JComboBox<Rol> campoRol = new JComboBox<>(Rol.values());
    private final JComboBox<Object> campoRol = new JComboBox<>();
    private final JTextField campoNacionalidad = new JTextField();
    private final JTextField campoPaisResidencia = new JTextField();

    private JButton btnCrear;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnLimpiar;

    private final JCheckBox mostrarPassword = new JCheckBox("Ver");

    public PanelUsuarios(UsuarioController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Tipo", "Nombre", "Documento", "Correo"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
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

        panel.add(new JLabel("ID (alfanumérico):"));    panel.add(campoId);

        campoId.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) { usuarioExiste(campoId.getText().trim()); }
        });

        panel.add(new JLabel("Tipo doc.:"));          panel.add(campoTipoDoc);
        panel.add(new JLabel("Num. doc.:"));          panel.add(campoNumDoc);
        panel.add(new JLabel("Nombre:"));             panel.add(campoNombre);
        panel.add(new JLabel("Telefono:"));           panel.add(campoTelefono);
        panel.add(new JLabel("Correo:"));             panel.add(campoCorreo);

        //panel.add(new JLabel("Password:"));           panel.add(campoPassword);
        //panel.add(mostrarPassword);                        panel.add(new JLabel(""));

        JPanel panelPassword = new JPanel(new BorderLayout(5, 0));
        panelPassword.add(campoPassword, BorderLayout.CENTER);
        panelPassword.add(mostrarPassword, BorderLayout.EAST);
        panel.add(new JLabel("Password:"));           panel.add(panelPassword);

        panel.add(new JLabel("Tipo usuario:"));       panel.add(campoTipoUsuario);
        panel.add(new JLabel("(Si es EMPLEADO)"));    panel.add(new JLabel(""));
        panel.add(new JLabel("Rol:"));                panel.add(campoRol);
        panel.add(new JLabel("(Si es CLIENTE)"));     panel.add(new JLabel(""));
        panel.add(new JLabel(""));                    panel.add(new JLabel(""));
        panel.add(new JLabel("Nacionalidad:"));       panel.add(campoNacionalidad);
        panel.add(new JLabel("Pais Residencia:"));    panel.add(campoPaisResidencia);

        btnCrear = new JButton("Crear");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");

        btnCrear.addActionListener(e -> crearUsuario());
        btnActualizar.addActionListener(e -> actualizarUsuario());
        btnEliminar.addActionListener(e -> eliminarUsuario());
        btnLimpiar.addActionListener(e -> limpiarFormulario(true));

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnCrear);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        btnCrear.setEnabled(true);
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);

        campoTipoUsuario.addActionListener(e -> actualizarEstadoCampos());
        campoTipoDoc.addItem("-- Seleccione --");
        for (TipoDocIdentidad tipoDoc : TipoDocIdentidad.values()) {
            campoTipoDoc.addItem(tipoDoc);
        }

        campoRol.addItem("-- Seleccione --");
        for (Rol rol : Rol.values()) {
            campoRol.addItem(rol);
        }

        mostrarPassword.addActionListener(e -> {
            if (mostrarPassword.isSelected()) {
                campoPassword.setEchoChar((char) 0);
            } else {
                campoPassword.setEchoChar('•');
            }
        });
        actualizarEstadoCampos();

        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.add(panel, BorderLayout.CENTER);
        contenedor.add(panelBotones, BorderLayout.SOUTH);
        return contenedor;
    }

    private void usuarioExiste(String id) {
        try {
            if (controller.buscar(id) != null) {
                btnCrear.setEnabled(false);
                btnActualizar.setEnabled(true);
                btnEliminar.setEnabled(true);
                cargarDatosUsuario(id);
                campoNombre.setEnabled(true);
                campoCorreo.setEnabled(true);
                campoTelefono.setEnabled(true);
                campoId.setEnabled(false);
                campoTipoDoc.setEnabled(false);
                campoNumDoc.setEnabled(false);
                campoPassword.setEnabled(false);
                campoTipoUsuario.setEnabled(false);
                campoRol.setEnabled(false);
                campoNacionalidad.setEnabled(false);
                campoPaisResidencia.setEnabled(false);
            } else {
                limpiarFormulario(false);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al verificar usuario existe: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarEstadoCampos() {
        if ("".equals(campoTipoUsuario.getSelectedItem())) {
            campoNacionalidad.setEnabled(false);
            campoPaisResidencia.setEnabled(false);
            campoRol.setEnabled(false);
            campoRol.setSelectedIndex(0);
            campoNacionalidad.setText("");
            campoPaisResidencia.setText("");
        } else {
            boolean esEmpleado = "EMPLEADO".equals(campoTipoUsuario.getSelectedItem());
            campoNacionalidad.setEnabled(!esEmpleado);
            campoPaisResidencia.setEnabled(!esEmpleado);
            campoRol.setEnabled(esEmpleado);

            if (esEmpleado) {
                campoNacionalidad.setText("");
                campoPaisResidencia.setText("");
            } else {
                campoRol.setSelectedIndex(0);
            }
        }
    }

    private void crearUsuario() {
        try {
            if (!validarCampos("crear")) { return; }
            if("CLIENTE".equals(campoTipoUsuario.getSelectedItem())) { campoRol.setSelectedIndex(1); }
            controller.crear((String) campoTipoUsuario.getSelectedItem(),
                    campoId.getText().trim(),
                    (TipoDocIdentidad) campoTipoDoc.getSelectedItem(),
                    campoNumDoc.getText().trim(),
                    campoNombre.getText().trim(),
                    campoTelefono.getText().trim(),
                    campoCorreo.getText().trim(),
                    new String(campoPassword.getPassword()),
                    (Rol) campoRol.getSelectedItem(),
                    campoNacionalidad.getText().trim(),
                    campoPaisResidencia.getText().trim()
            );
            JOptionPane.showMessageDialog(this, "Usuario creado con éxito.");
            refrescarTabla();
            limpiarFormulario(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al crear: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarUsuario() {
        try {
            if (!validarCampos("actualizar")) { return; }
            String id = campoId.getText().trim();
            //Usuario existente = controller.buscar(id);
            if (controller.buscar(id) == null) {
                JOptionPane.showMessageDialog(this, "No existe un usuario con ese ID.");
                return;
            }

            String nuevoNombre = campoNombre.getText().trim();
            String nuevoTelefono = campoTelefono.getText().trim();
            String nuevoCorreo = campoCorreo.getText().trim();
            boolean actualizado = controller.actualizar(id, nuevoNombre, nuevoTelefono, nuevoCorreo);
            if (actualizado) {
                JOptionPane.showMessageDialog(this, "Usuario actualizado con éxito: Nombre, correo y telefono.");
                refrescarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar el usuario.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al crear: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void eliminarUsuario() {
        if (!validarCampos("eliminar")) { return; }
        String id = campoId.getText().trim();
        if (controller.eliminar(id)) {
            refrescarTabla();
            limpiarFormulario(true);
        } else {
            JOptionPane.showMessageDialog(this, "No existe un usuario con ese ID.");
        }
    }

    private boolean validarCampos(String accion) {
        switch (accion) {
            case "crear" -> {
                if (campoId.getText().trim().isEmpty()) {
                    mostrarError("El ID de usuario es obligatorio.", campoId);
                    return false;
                }
                if (campoTipoDoc.getSelectedItem() == null || campoTipoDoc.getSelectedIndex() <= 0) {
                    mostrarError("Debe seleccionar un tipo de documento.", campoTipoDoc);
                    return false;
                }
                if (campoNumDoc.getText().trim().isEmpty()) {
                    mostrarError("El número de documento es obligatorio.", campoNumDoc);
                    return false;
                }
                if (campoNombre.getText().trim().isEmpty()) {
                    mostrarError("El nombre es obligatorio.", campoNombre);
                    return false;
                }
                if (campoTelefono.getText().trim().isEmpty()) {
                    mostrarError("El teléfono es obligatorio.", campoTelefono);
                    return false;
                }
                String correo = campoCorreo.getText().trim();
                String regexCorreo = "^[A-Za-z0-9+_.-]+@(.+)$"; // Estructura básica texto@texto.com

                if (correo.isEmpty() || !correo.matches(regexCorreo)) {
                    mostrarError("El formato del correo electrónico no es válido.", campoCorreo);
                    return false;
                }
                if (campoPassword.getPassword().length == 0) {
                    mostrarError("La contraseña es obligatoria.", campoPassword);
                    return false;
                }
                if (campoTipoUsuario.getSelectedIndex() <= 0) {
                    mostrarError("Debe seleccionar un tipo de usuario válido.", campoTipoUsuario);
                    return false;
                }

                if (campoTipoUsuario.getSelectedItem() == "EMPLEADO") {
                    if (campoRol.getSelectedItem() == null || campoRol.getSelectedIndex() <= 0) {
                        mostrarError("Debe seleccionar un rol.", campoRol);
                        return false;
                    }
                }
                if (campoTipoUsuario.getSelectedItem() == "CLIENTE") {
                    if (campoNacionalidad.getText().trim().isEmpty()) {
                        mostrarError("La nacionalidad es obligatoria.", campoNacionalidad);
                        return false;
                    }
                    if (campoPaisResidencia.getText().trim().isEmpty()) {
                        mostrarError("El país de residencia es obligatorio.", campoPaisResidencia);
                        return false;
                    }
                }
                return true;
            }
            case "actualizar" -> {
                if (campoNombre.getText().trim().isEmpty()) {
                    mostrarError("El nombre es obligatorio.", campoNombre);
                    return false;
                }
                if (campoTelefono.getText().trim().isEmpty()) {
                    mostrarError("El teléfono es obligatorio.", campoTelefono);
                    return false;
                }
                String correo = campoCorreo.getText().trim();
                String regexCorreo = "^[A-Za-z0-9+_.-]+@(.+)$"; // Estructura básica texto@texto.com

                if (correo.isEmpty() || !correo.matches(regexCorreo)) {
                    mostrarError("El formato del correo electrónico no es válido.", campoCorreo);
                    return false;
                }
                return true;
            }
            case "eliminar" -> {
                if (campoId.getText().trim().isEmpty()) {
                    mostrarError("El ID de usuario es obligatorio.", campoId);
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    private void mostrarError(String mensaje, javax.swing.JComponent componente) {
        JOptionPane.showMessageDialog(this, mensaje, "Error de Validación", JOptionPane.WARNING_MESSAGE);
        componente.requestFocus();
    }

    private void cargarSeleccionEnFormulario() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) return;
        String id = (String) modeloTabla.getValueAt(fila, 0);
        usuarioExiste(id);
    }

    private void cargarDatosUsuario(String id) {
        Usuario usuario = controller.buscar(id);
        if (usuario == null) return;
        campoId.setText(usuario.getId());
        campoTipoDoc.setSelectedItem(usuario.getTipoDocIdentidad());
        campoNumDoc.setText(usuario.getNumDocIdentidad());
        campoNombre.setText(usuario.getNombre());
        campoTelefono.setText(usuario.getTelefono());
        campoCorreo.setText(usuario.getCorreo());
        campoTipoUsuario.setSelectedItem(usuario.getTipoUsuario());
        campoPassword.setText(usuario.getPassword());
        actualizarEstadoCampos();
        if (usuario instanceof Empleado empleado) {
            campoRol.setSelectedItem(empleado.getRol());
        } else if (usuario instanceof Cliente cliente) {
            campoNacionalidad.setText(cliente.getNacionalidad());
            campoPaisResidencia.setText(cliente.getPaisResidencia());
        }
    }

    private void limpiarFormulario(boolean borrar) {
        btnCrear.setEnabled(true);
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);

        campoNombre.setEnabled(true);
        campoCorreo.setEnabled(true);
        campoTelefono.setEnabled(true);
        campoId.setEnabled(true);
        campoTipoUsuario.setEnabled(true);
        actualizarEstadoCampos();
        campoTipoDoc.setEnabled(true);
        campoNumDoc.setEnabled(true);
        campoPassword.setEnabled(true);

        if (borrar) {
            campoId.setText("");
            campoNumDoc.setText("");
            campoNombre.setText("");
            campoTelefono.setText("");
            campoCorreo.setText("");
            campoPassword.setText("");
            campoTipoDoc.setSelectedIndex(0);
            campoTipoUsuario.setSelectedIndex(0);
            campoRol.setSelectedIndex(0);
            campoTipoDoc.setSelectedItem(0);
            campoNacionalidad.setText("");
            campoPaisResidencia.setText("");
            tabla.clearSelection();
        }
    }

    private void refrescarTabla() {
        modeloTabla.setRowCount(0);
        List<Usuario> usuarios = controller.listar();
        for (Usuario u : usuarios) {
            modeloTabla.addRow(new Object[]{u.getId(), u.getTipoUsuario(), u.getNombre(), u.getNumDocIdentidad(), u.getCorreo()});
        }
    }
}