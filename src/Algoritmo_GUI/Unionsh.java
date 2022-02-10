/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algoritmo_GUI;

import java.util.ArrayList;

/**
 *
 * @author ferna
 */
public class Unionsh {

    ArrayList<Vertice> vertices;

    public Unionsh() {
        vertices = new ArrayList<>();
    }

    public void addToUnionsh(Vertice v) {
        // Agregar un Vertice a la Union
        this.vertices.add(v);
    }

    public static void merge(Unionsh c1, Unionsh c2) {
        //Unir dos aristas, o hacer la Union
        for (int i = 0; i < c2.vertices.size(); i++) {
            Vertice v = c2.vertices.get(i);
            c1.vertices.add(v);
            v.Recta = c1;
        }
    }
}
