package model.dao;

import model.entidades.Usuario;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class UsuarioDAOMemoria implements UsuarioDAO {

    private final Map<String, Usuario> almacen = new LinkedHashMap<>();

    @Override
    public Usuario crear(Usuario usuario) {
        almacen.put(usuario.getId(), usuario);
        return usuario;
    }

    @Override
    public Usuario buscarPorId(String id) {
        return almacen.get(id);
    }

    @Override
    public List<Usuario> listarTodos() {
        return new ArrayList<>(almacen.values());
    }

    @Override
    public boolean actualizar(Usuario usuario) {
        if (!almacen.containsKey(usuario.getId())) return false;
        almacen.put(usuario.getId(), usuario);
        return true;
    }

    @Override
    public boolean eliminar(String id) {
        return almacen.remove(id) != null;
    }
}
