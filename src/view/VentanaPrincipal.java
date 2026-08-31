package view;

import controller.*;

import javax.swing.*;

public class VentanaPrincipal extends JFrame {
    public VentanaPrincipal(UsuarioController usuarioController,
                            HabitacionController habitacionController,
                            ReservaController reservaController) {
        super("Sistema de Gestion de Reservas de Hotel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 600);
        setLocationRelativeTo(null);

        JTabbedPane pestanas = new JTabbedPane();
        pestanas.addTab("Usuarios", new PanelUsuarios(usuarioController));
        pestanas.addTab("Habitaciones", new PanelHabitaciones(habitacionController));
        pestanas.addTab("Reservas", new PanelReservas(reservaController, habitacionController));

        add(pestanas);
    }
}
