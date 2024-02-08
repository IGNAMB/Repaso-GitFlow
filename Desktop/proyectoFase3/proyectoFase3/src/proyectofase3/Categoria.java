/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofase3;

/**
 *
 * @author fping
 */
public class Categoria {
    private int idCategoria;
    private String nombreCategoria;

    // Constructor
    public Categoria() {
    }

    // Constructor con parámetros
    public Categoria(int idCategoria, String nombreCategoria) {
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
    }

    // Getter para ID de categoría
    public int getIdCategoria() {
        return idCategoria;
    }

    // Setter para ID de categoría
    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    // Getter para nombre de categoría
    public String getNombreCategoria() {
        return nombreCategoria;
    }

    // Setter para nombre de categoría
    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    // Otros métodos y funciones relacionados con la clase Categoría
}