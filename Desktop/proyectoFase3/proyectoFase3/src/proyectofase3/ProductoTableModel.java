/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofase3;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author fping
 */
public class ProductoTableModel extends AbstractTableModel {
    private final List<Producto2> productos;
    private final String[] columnNames = {"Categoría", "Nombre", "Descripción", "Precio", "Existencia"};

    public ProductoTableModel(List<Producto2> productos) {
        this.productos = productos;
    }

    @Override
    public int getRowCount() {
        return productos.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Producto2 producto = productos.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return producto.getNombreCategoria(); 
            case 1:
                return producto.getNombreProducto();
            case 2:
                return producto.getDescripcion();
            case 3:
                return producto.getPrecio();
            case 4:
                return producto.getExistencia();
            default:
                return null;
        }
    }
    
     // Método para obtener un producto por índice
    public Producto2 getProductoAt(int rowIndex) {
        if (productos != null && rowIndex >= 0 && rowIndex < productos.size()) {
            return productos.get(rowIndex);
        }
        return null;
    }
}
