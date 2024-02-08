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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fping
 */
public class ProductoDAO {
    private Connection connection;

    public ProductoDAO(Connection connection) {
        this.connection = connection;
    }

    // Método para llamar a un procedimiento almacenado
    public void llamarProcedimiento(String nombreProcedimiento, Object... parametros) {
        try (CallableStatement cstmt = connection.prepareCall("{call " + nombreProcedimiento + "}")) {
            int index = 1;
            for (Object parametro : parametros) {
                if (parametro instanceof String) {
                    cstmt.setString(index, (String) parametro);
                } else if (parametro instanceof Integer) {
                    cstmt.setInt(index, (int) parametro);
                } else if (parametro instanceof Double) {
                    cstmt.setDouble(index, (double) parametro);
                }
                // Agregar más tipos de datos según sea necesario (por ejemplo, para campos DECIMAL o DATE)
                index++;
            }
            cstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para insertar un producto en la tabla
    public void insertarProducto(int idCategoria, String nombreProducto, String descripcion, double precio, String numeroLote, int existencia) {
        llamarProcedimiento("GestionarProducto(?, ?, ?, ?, ?, ?, ?, ?)", "I", 0, idCategoria, nombreProducto, descripcion, precio, "", existencia);
    }

    // Método para consultar un producto por ID
    public Producto consultarProducto(int idProducto) {
        String sql = "SELECT * FROM producto WHERE idproducto = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idProducto);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Producto producto = new Producto();
                producto.setIdProducto(rs.getInt("idproducto"));
                producto.setIdCategoria(rs.getInt("idcategoria"));
                producto.setNombreProducto(rs.getString("nombreproducto"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setNumeroLote(rs.getString("numerolote"));
                producto.setExistencia(rs.getInt("existencia"));
                return producto;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Método para actualizar un producto por ID
    public void actualizarProducto(int idProducto, int idCategoria, String nombreProducto, String descripcion, double precio, String numeroLote, int existencia) {
        llamarProcedimiento("GestionarProducto(?, ?, ?, ?, ?, ?, ?, ?)", "U", idProducto, idCategoria, nombreProducto, descripcion, precio, "", existencia);
    }

    // Método para eliminar un producto por ID
    public void eliminarProducto(int idProducto) {
        llamarProcedimiento("GestionarProducto(?, ?, ?, ?, ?, ?, ?, ?)", "D", idProducto, 0, "", "", 0, "", 0);
    }

    // Método para verificar si un producto existe por ID
    public boolean existeProductoPorId(int idProducto) {
        String sql = "SELECT COUNT(*) FROM producto WHERE idproducto = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idProducto);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Producto2> consultarProductoTodos() {
        List<Producto2> productos = new ArrayList<>();
        String sql = "SELECT p.idproducto, p.idcategoria, c.nombrecategoria, p.nombreproducto, p.descripcion, p.precio, p.numerolote, p.existencia FROM producto p inner join categoria c on p.idcategoria = c.idcategoria";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Producto2 producto = new Producto2();
                producto.setIdProducto(rs.getInt("idproducto"));
                producto.setIdCategoria(rs.getInt("idcategoria"));
                producto.setNombreCategoria(rs.getString("nombrecategoria"));
                producto.setNombreProducto(rs.getString("nombreproducto"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setNumeroLote(rs.getString("numerolote"));
                producto.setExistencia(rs.getInt("existencia"));
                productos.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productos;
    }
    
    public int obtenerIdCategoriaPorNombre(String nombreCategoria) {
    String sql = "SELECT idcategoria FROM categoria WHERE nombrecategoria = ?";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setString(1, nombreCategoria);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("idcategoria");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return 0;  // O algún valor predeterminado si no se encuentra
}
    
}
