package model.dao;

import model.entidades.Usuario;
import java.util.List;


public interface UsuarioDAO {
    Usuario crear(Usuario usuario);
    Usuario buscarPorId(String id);
    List<Usuario> listarTodos();
    boolean actualizar(String id, String nombre, String telefono, String correo);
    boolean eliminar(String id);
}
