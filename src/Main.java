import controller.*;
import model.dao.*;
import model.facade.*;
import view.*;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        UsuarioDAO usuarioDAO = new UsuarioDAOMemoria();
        ReservaDAO reservaDAO = new ReservaDAOMemoria();
        HabitacionDAO habitacionDAO = new HabitacionDAOMemoria();

        IReservasFacade facade = new ReservasFacade(usuarioDAO, reservaDAO, habitacionDAO);

        UsuarioController usuarioController = new UsuarioController(facade);
        HabitacionController habitacionController = new HabitacionController(facade);
        ReservaController reservaController = new ReservaController(facade);

        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal(usuarioController, habitacionController, reservaController);
            ventana.setVisible(true);
        });
    }
}
