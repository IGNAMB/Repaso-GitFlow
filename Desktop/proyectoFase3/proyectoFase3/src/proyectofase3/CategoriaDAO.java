/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofase3;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author fping
 */
public class CategoriaDAO {
    private Connection connection; // Debes establecer esta conexión en tu clase

    public CategoriaDAO(Connection connection) {
        this.connection = connection;
    }

    public void gestionarCategoria(String accion, Integer idcategoria, String nombrecategoria) {
        try {
            // Llama al procedimiento almacenado
            String storedProcedure = "{call GestionarCategoria(?, ?, ?)}";
            CallableStatement cstmt = connection.prepareCall(storedProcedure);
            cstmt.setString(1, accion);
            if (idcategoria != null) {
                cstmt.setInt(2, idcategoria);
            } else {
                cstmt.setNull(2, java.sql.Types.INTEGER);
            }
            cstmt.setString(3, nombrecategoria);            
            cstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Método para verificar si una categoría existe por nombre
    public boolean existeCategoria(String nombreCategoria) {
        String sql = "SELECT COUNT(*) FROM categoria WHERE nombrecategoria = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nombreCategoria);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para consultar una categoría por nombre o ID
    public Categoria consultarCategoria(String consultaCategoria) {
        String sql = "SELECT * FROM categoria WHERE nombrecategoria = ? OR idcategoria = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            try {
                int id = Integer.parseInt(consultaCategoria);
                stmt.setNull(1, java.sql.Types.VARCHAR);
                stmt.setInt(2, id);
            } catch (NumberFormatException e) {
                stmt.setString(1, consultaCategoria);
                stmt.setNull(2, java.sql.Types.INTEGER);
            }

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Categoria categoria = new Categoria();
                categoria.setIdCategoria(rs.getInt("idcategoria"));
                categoria.setNombreCategoria(rs.getString("nombrecategoria"));
                return categoria;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Método para consultar todas las categorías
    public List<Categoria> consultarCategorias() {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT * FROM categoria";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Categoria categoria = new Categoria();
                categoria.setIdCategoria(rs.getInt("idcategoria"));
                categoria.setNombreCategoria(rs.getString("nombrecategoria"));
                categorias.add(categoria);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categorias;
    }

    // Método para verificar si una categoría existe por ID
    public boolean existeCategoriaPorId(int idCategoria) {
        String sql = "SELECT COUNT(*) FROM categoria WHERE idcategoria = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idCategoria);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para obtener todas las categorías desde la base de datos
    public List<Categoria> obtenerCategoriasDesdeBaseDeDatos() {
        List<Categoria> categorias = new ArrayList<>();

        String sql = "SELECT idcategoria, nombrecategoria FROM categoria";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idCategoria = rs.getInt("idcategoria");
                String nombreCategoria = rs.getString("nombrecategoria");

                // Crea un objeto Categoria y agrégalo a la lista
                Categoria categoria = new Categoria(idCategoria, nombreCategoria);
                categorias.add(categoria);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categorias;
    }
    
    // Método para obtener los nombres de todas las categorías desde la base de datos
    public List<String> obtenerNombresCategorias() {
        List<String> nombresCategorias = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            String sql = "SELECT nombreCategoria FROM categoria";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String nombreCategoria = resultSet.getString("nombreCategoria");
                nombresCategorias.add(nombreCategoria);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nombresCategorias;
    }
    
    public String obtenerDescripcionCategoriaPorNombre(String nombreCategoria) {
        try {
            String sql = "SELECT descripcion FROM categoria WHERE nombrecategoria = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, nombreCategoria);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getString("descripcion");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Categoria consultarCategoriaPorId(int idCategoria) {
        String sql = "SELECT * FROM categoria WHERE idcategoria = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idCategoria);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Categoria categoria = new Categoria();
                categoria.setIdCategoria(rs.getInt("idcategoria"));
                categoria.setNombreCategoria(rs.getString("nombrecategoria")); 
                return categoria;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    public Categoria buscarCategoriaPorNombre(String nombreCategoria) {
        try {
            String sql = "SELECT * FROM categoria WHERE nombrecategoria = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, nombreCategoria);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    Categoria categoria = new Categoria();
                    categoria.setIdCategoria(rs.getInt("idcategoria"));
                    categoria.setNombreCategoria(rs.getString("nombrecategoria")); 
                    return categoria;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
