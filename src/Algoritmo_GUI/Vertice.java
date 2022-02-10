/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algoritmo_GUI;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author ferna
 */
public class Vertice {

    Point p;
    boolean mouse;
    Unionsh Recta;
    ArrayList<Arista> enAristas;

    public Vertice(Point x) {
        p = x;
        mouse = false;
        enAristas = new ArrayList<>();
        Recta = new Unionsh();
    }
}
