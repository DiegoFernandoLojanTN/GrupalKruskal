/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algoritmo_GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author ferna
 */
public class Arbol extends JPanel {

    Kruskal principal = null;
    ArrayList<Arista> DibujaAristas;
    ArrayList<Vertice> DibujaVertices;
    ArrayList<Arista> arbol;
    Color ponColor = Color.green;

    public Arbol(Kruskal _principal) {
        // constructor
        super();
        principal = _principal;
        DibujaVertices = principal.vertices;
        DibujaAristas = principal.aristas;
        arbol = principal.arbol;
    }

    public Arbol() {
        DibujaAristas = new ArrayList<>();
        DibujaVertices = new ArrayList<>();
        arbol = new ArrayList<>();
    }

    public Arbol(ArrayList<Arista> grafico) {
        // Constructor cuando el único parámetro es un Arraylist de Aristas 
        DibujaAristas = grafico;
        arbol = new ArrayList<>();
        DibujaVertices = new ArrayList<>();
    }

    public void paintComponent(Graphics g) {
        // DIBUJA   
        super.paintComponent(g);

        for (int i = 0; i < DibujaVertices.size(); ++i) {

            g.setColor(ponColor);
            Vertice verticeActual = DibujaVertices.get(i);

            if (verticeActual.mouse) {
                g.setColor(Color.black);
            }

            g.fillOval(verticeActual.p.x - principal.NODE_RADIUS,
                    verticeActual.p.y - principal.NODE_RADIUS,
                    2 * principal.NODE_RADIUS, 2 * principal.NODE_RADIUS);

        }

        for (int i = 0; i < DibujaAristas.size(); ++i) {

            g.setColor(ponColor);
            Arista edge = DibujaAristas.get(i);

            if (edge.mouse) {
                g.setColor(Color.red);
            }

            Point p1 = edge.v1.p;
            Point p2 = edge.v2.p;
            g.drawLine(p1.x, p1.y, p2.x, p2.y);

            Point midpoint = edge.MedioAgregaPeso();
            g.drawString("Peso: " + edge.peso, midpoint.x, midpoint.y);

        }

        if (principal.arbol.size() > 0) {
            for (int i = 0; i < principal.arbol.size(); ++i) {

                g.setColor(Color.blue);

                Arista edge = principal.arbol.get(i);
                Vertice vt1 = edge.v1;
                Vertice vt2 = edge.v2;

                g.fillOval(vt1.p.x - principal.NODE_RADIUS, vt1.p.y - principal.NODE_RADIUS,
                        2 * principal.NODE_RADIUS, 2 * principal.NODE_RADIUS);
                g.fillOval(vt2.p.x - principal.NODE_RADIUS, vt2.p.y - principal.NODE_RADIUS,
                        2 * principal.NODE_RADIUS, 2 * principal.NODE_RADIUS);

                g.drawLine(vt1.p.x, vt1.p.y, vt2.p.x, vt2.p.y);
                Point midpoint2 = edge.MedioAgregaPeso();
                g.drawString("Peso: " + edge.peso, midpoint2.x, midpoint2.y);
            }
        }

    }

    public void cambiaColor() {
        if (ponColor.equals(Color.green)) {
            ponColor = Color.blue;
        } else {
            ponColor = Color.green;
        }
    }

    public ArrayList<Arista> getArbol() {
        // Devuelve un Arraylist de Aristas en el Arbol
        IniciaUnion();
        ArrayList<Arista> temp = new ArrayList<>();

        while (DibujaAristas.size() > 0) {

            Arista e = DibujaAristas.remove(0);
            temp.add(e);
            Vertice vertex1 = e.v1;
            Vertice vertex2 = e.v2;
            Unionsh c1 = vertex1.Recta;
            Unionsh c2 = vertex2.Recta;

            if (c1 != c2) {
                arbol.add(e);
                Unionsh.merge(c1, c2);
            }
        }

        DibujaAristas.addAll(temp);

        return arbol;
    }

    public void IniciaUnion() {
        // create the cloud by adding the appropriate vertices
        for (int i = 0; i < DibujaAristas.size(); ++i) {
            DibujaAristas.sort(null);

            Arista edge = DibujaAristas.get(i);
            Vertice vertex1 = edge.v1;
            Vertice vertex2 = edge.v2;

            if (vertex1.Recta.vertices.size() == 0) {
                vertex1.Recta.addToUnionsh(vertex1);
            }
            if (vertex2.Recta.vertices.size() == 0) {
                vertex2.Recta.addToUnionsh(vertex2);
            }
        }
    }

    public static void main(String args[]) {

        Vertice v1 = new Vertice(new Point());
        Vertice v2 = new Vertice(new Point());
        Arista e1 = new Arista(v1, v2);
        e1.peso = 0;
        Arista e2 = new Arista(v1, v1);
        e2.peso = 2;
        Arista e3 = new Arista(v2, v2);
        e3.peso = -3;

        ArrayList<Arista> edges = new ArrayList<>();
        edges.add(e1);
        edges.add(e2);
        edges.add(e3);
        edges.sort(null);

        for (Arista e : edges) {
            System.out.println(e.peso);
        }
    }
}
