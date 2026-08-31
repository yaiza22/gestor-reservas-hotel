package controller;

import model.entidades.Usuario;
import model.facade.IReservasFacade;
import java.util.List;


public class UsuarioController {
    private final IReservasFacade facade;

    public UsuarioController(IReservasFacade facade) {
        this.facade = facade;
    }

    public Usuario crear(Usuario usuario) { return facade.registrarUsuario(usuario); }
    public Usuario buscar(String id) { return facade.buscarUsuario(id); }
    public List<Usuario> listar() { return facade.listarUsuarios(); }
    public boolean actualizar(Usuario usuario) { return facade.actualizarUsuario(usuario); }
    public boolean eliminar(String id) { return facade.eliminarUsuario(id); }
}
