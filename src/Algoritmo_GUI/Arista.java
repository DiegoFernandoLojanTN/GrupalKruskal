/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algoritmo_GUI;

import java.awt.Point;

/**
 *
 * @author ferna
 */
public class Arista implements Comparable {

    Vertice v1; // Es el vertice segun la arista
    Vertice v2; // El vertice que conecta la otra arista
    boolean mouse; // Para el mouse se encuntra encima de la Arista/pinta de color
    double peso; // Para el peso de la arista

    public Arista(Vertice x, Vertice y) {
        v1 = x;
        v2 = y;
        mouse = false;
        peso = 1.0; // Pondremos siempre que la arista tenga peso de 1
    }

    @Override
    public int compareTo(Object o) {
        // overriding- para invalidar arista con peso -1
        return (this.peso < ((Arista) o).peso ? -1 : (this.peso == ((Arista) o).peso ? 0 : 1));
    }

    public Point MedioAgregaPeso() {
        //Encuentra la arista para agregar una etiqueta de peso
        return new Point((int) (0.5 * (this.v1.p.x + this.v2.p.x)),
                (int) (0.5 * (this.v1.p.y + this.v2.p.y)));
    }
}
