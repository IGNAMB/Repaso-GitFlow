/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofase3;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;


/**
 *
 * @author fping
 */
public class UsuarioDAO {
     private Connection connection;

    // Constructor que recibe la conexión a la base de datos

    public UsuarioDAO(Connection connection) {
        this.connection = connection;
    }

    // Método para gestionar usuarios (Insertar, Actualizar, Eliminar, Login)
    public Object gestionarUsuario(String accion, String idUsuario, String password, String nombreCompleto, Date fechaIng, String email) {
        try {
            String sql = "{call GestionarUsuario (?, ?, ?, ?, ?, ?)}";

            try (CallableStatement cstmt = connection.prepareCall(sql)) {
                cstmt.setString(1, accion);
                cstmt.setString(2, idUsuario);
                cstmt.setString(3, password);
                cstmt.setString(4, nombreCompleto);
                cstmt.setDate(5, fechaIng);
                cstmt.setString(6, email);

                if (accion.equals("L")) {
                    // Si la acción es Login, esperamos un resultado
                    ResultSet rs = cstmt.executeQuery();

                    if (rs.next()) {
                        return rs.getInt("UserId");
                    }
                } else if (accion.equals("I")){
                    // Si la acción es Login, esperamos un resultado
                    ResultSet rs2 = cstmt.executeQuery();
                    
                    ResultSetMetaData metaData = rs2.getMetaData();
                    int columnCount = metaData.getColumnCount();
                    boolean hasErrorMessageColumn = false;

                    for (int i = 1; i <= columnCount; i++) {
                        if ("ErrorMessage".equalsIgnoreCase(metaData.getColumnName(i))) {
                            hasErrorMessageColumn = true;
                            break;
                        }
                    }
                    
                    if (hasErrorMessageColumn && rs2.next()) {
                        return rs2.getString("ErrorMessage");
                    }
                } else {
                    // Si la acción no es Login, no esperamos un resultado
                    cstmt.execute();
                }
            }

            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
    
     // Método para realizar el login
    public int login(String usuario, String password) {
        // Llamar al método gestionarUsuario con la acción 'L'
        Object result = gestionarUsuario("L", null, password, null, null, usuario);
        if (result instanceof Integer) {
            // El resultado es un ID de usuario, lo devolvemos
            return (Integer) result;
        } else {
            // El resultado no es un ID de usuario, indicando un login fallido
            return -1;
        }
    }
    
    public String obtenerNombreUsuarioPorId(int userId) {
        String nombreUsuario = null;

        try {
            String sql = "SELECT nombreCompleto FROM usuario WHERE idUsuario = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setInt(1, userId);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        nombreUsuario = rs.getString("nombreCompleto");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de excepciones según tus necesidades
        }

        return nombreUsuario;
    }
    
}
