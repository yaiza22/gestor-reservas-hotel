package controller;

import model.entidades.Usuario;
import model.enums.Rol;
import model.enums.TipoDocIdentidad;
import model.facade.IReservasFacade;

import java.util.List;


public class UsuarioController {
    private final IReservasFacade facade;

    public UsuarioController(IReservasFacade facade) {
        this.facade = facade;
    }

    public Usuario crear(String tipoUsuario, String id, TipoDocIdentidad tipoDoc, String numDoc,
                         String nombre, String telefono, String correo, String password, Rol rol,
                         String nacionalidad, String paisResidencia) {
        return facade.registrarUsuario(tipoUsuario, id, tipoDoc, numDoc, nombre, telefono, correo, password, rol, nacionalidad, paisResidencia);
    }
    public Usuario buscar(String id) { return facade.buscarUsuario(id); }
    public List<Usuario> listar() { return facade.listarUsuarios(); }
    public boolean actualizar(String id, String nombre, String telefono, String correo) { return facade.actualizarUsuario(id, nombre, telefono, correo); }
    public boolean eliminar(String id) { return facade.eliminarUsuario(id); }
}
