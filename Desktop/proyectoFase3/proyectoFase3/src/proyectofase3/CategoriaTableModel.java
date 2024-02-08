/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofase3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author fping
 */
public class CategoriaTableModel extends DefaultTableModel {
    private List<String> nombresCategorias = new ArrayList<>();
    Connection conexion;
    CategoriaDAO categoriaDAO;
    private List<Categoria> categorias = new ArrayList<>();
    
    // Constructor
    public CategoriaTableModel() {
        // Especifica las columnas que quieres en tu tabla
        super(new Object[]{"ID", "Nombre"}, 0);
        try {
            this.conexion = ObtenerConexion();
            categoriaDAO = new CategoriaDAO(conexion);
            // Cargar las categorías desde la base de datos al iniciar
            cargarCategorias();
        } catch (SQLException ex) {
            Logger.getLogger(CategoriaTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    // Método para agregar una fila a la tabla
    public void agregarFila(int idCategoria, String nombreCategoria) {
        // Agrega los datos de la fila
        addRow(new Object[]{idCategoria, nombreCategoria});
    }
    
    // Método para cargar las categorías desde la base de datos
    private void cargarCategorias() {
        // Limpiar las filas existentes
        setRowCount(0);

        // Obtener las categorías desde la base de datos
        List<Categoria> listaCategorias = categoriaDAO.obtenerCategoriasDesdeBaseDeDatos();

        // Agregar cada categoría al modelo de tabla
        for (Categoria categoria : listaCategorias) {
            agregarFila(categoria);
        }
    }
    
    // Método para agregar una fila a la tabla
    public void agregarFila(Categoria categoria) {
        // Agregar los datos de la fila
        addRow(new Object[]{categoria.getIdCategoria(), categoria.getNombreCategoria()});
        categorias.add(categoria);
    }
    
    // Método para obtener los nombres de las categorías desde la base de datos
    public List<String> obtenerNombresCategorias() {
        List<String> categorias = new ArrayList<>();

        // Supongamos que existe un método en CategoriaDAO para obtener todas las categorías
        List<Categoria> listaCategorias = categoriaDAO.obtenerCategoriasDesdeBaseDeDatos();

        // Recorremos la lista y añadimos los nombres a la lista de categorías
        for (Categoria categoria : listaCategorias) {
            categorias.add(categoria.getNombreCategoria());
        }

        return categorias;
    }
    
    // Método para obtener una categoría en una posición específica
    public Categoria getCategoriaAt(int rowIndex) {
        return categorias.get(rowIndex);
    }
    
    public static Connection ObtenerConexion() throws SQLException {
        String url = "jdbc:sqlserver://DESKTOP-OKOLF5L\\BDD1;databaseName=proyectoisfase2";
        String usuario = "proyectoFase2";
        String contrasena = "proyectoFase2.";
        return DriverManager.getConnection(url, usuario, contrasena);
    }
    
    // Método para limpiar el modelo
    public void limpiar() {
        setRowCount(0);  
        fireTableDataChanged(); 
    }
    // Agrega un método para limpiar los datos del modelo
    public void clear() {
        // Elimina todas las filas del modelo
        int rowCount = getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            removeRow(i);
        }
    }
}