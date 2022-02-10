/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algoritmo_GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author ferna
 */
public final class Kruskal extends JFrame implements ActionListener, MouseListener, MouseMotionListener {

    final int NODE_RADIUS = 15;

    enum Botones {
        DEFAULT, AGREGA_VERTICE, ELIMINAR_VERTICE, AGREGA_ARISTA_1, AGREGA_ARISTA_2, ELIMINAR_ARISTA, ENVIAR_PESO, KRUSKAL
    }
    Botones boton = Botones.DEFAULT;
    int AGREGA_VERTICE = 0;
    int ELIMINAR_VERTICE = 1;
    int AGREGA_ARISTA_1 = 2;
    int AGREGA_ARISTA_2 = 3;
    int ELIMINAR_ARISTA = 4;
    int ENVIAR_PESO = 5;
    int KRUSKAL = 6;

    Arbol canvas;

    JPanel buttonPanel1;
    JPanel buttonPanel2;
    JButton addVertexButton, removeVertexButton, addEdgeButton,
            removeEdgeButton, setEdgeWeightButton, computeMstButton, clearButton, enterButton;
    JTextField weight;

    ArrayList<Vertice> vertices;
    ArrayList<Arista> aristas;
    ArrayList<Vertice> unionsh;
    ArrayList<Arista> arbol;

    int clickedVertexIndex;
    int clickedEdgeIndex;
    Arista temporaryEdge;
    int changeEdgeWeights;

    Dimension panelDim = null;

    public Kruskal() {
        // constructor
        super("KRUSKAL");
        setSize(new Dimension(1000, 700));

        canvas = null;

        buttonPanel1 = null;
        buttonPanel2 = null;
        weight = null;

        vertices = null;
        aristas = null;
        unionsh = null;
        arbol = null;

        clickedVertexIndex = -1;
        clickedEdgeIndex = -1;
        temporaryEdge = null;
        changeEdgeWeights = -1;

        boton = Botones.DEFAULT;

        initializeLists();

        //SE CREA PANEL
        Container contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane,
                BoxLayout.Y_AXIS));

        //AREA DEL DIBUJO
        canvas = new Arbol(this);
        canvas.addMouseListener(this);
        canvas.addMouseMotionListener(this);

        Dimension canvasSize = new Dimension(1000, 625);
        canvas.setMinimumSize(canvasSize);
        canvas.setPreferredSize(canvasSize);
        canvas.setMaximumSize(canvasSize);
        canvas.setBackground(Color.white);

        // PRIMER PANEL PARA LLENAR
        buttonPanel1 = new JPanel();
        Dimension panelSize = new Dimension(1000, 90);
        buttonPanel1.setMinimumSize(panelSize);
        buttonPanel1.setPreferredSize(panelSize);
        buttonPanel1.setMaximumSize(panelSize);
        buttonPanel1.setLayout(new BoxLayout(buttonPanel1,
                BoxLayout.X_AXIS));
        buttonPanel1.
                setBorder(BorderFactory.
                        createCompoundBorder(BorderFactory.
                                createLineBorder(Color.white),
                                buttonPanel1.getBorder()));

        // SEGUNDO PANEL PARA LLENAR
        buttonPanel2 = new JPanel();
        buttonPanel2.setMinimumSize(panelSize);
        buttonPanel2.setPreferredSize(panelSize);
        buttonPanel2.setMaximumSize(panelSize);
        buttonPanel2.setLayout(new BoxLayout(buttonPanel2,
                BoxLayout.X_AXIS));
        buttonPanel2.
                setBorder(BorderFactory.
                        createCompoundBorder(BorderFactory.
                                createLineBorder(Color.white),
                                buttonPanel2.getBorder()));

        Dimension buttonSize = new Dimension(200, 75);
        addVertexButton = new JButton("AGREGA VERTICE");
        addVertexButton.setMinimumSize(buttonSize);
        addVertexButton.setPreferredSize(buttonSize);
        addVertexButton.setMaximumSize(buttonSize);
        addVertexButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addVertexButton.setActionCommand("AGREGAVERTICE");
        addVertexButton.addActionListener(this);
        addVertexButton.
                setBorder(BorderFactory.
                        createCompoundBorder(BorderFactory.
                                createLineBorder(Color.blue),
                                addVertexButton.getBorder()));

        removeVertexButton = new JButton("ELIMINAR VERTICE");
        removeVertexButton.setMinimumSize(buttonSize);
        removeVertexButton.setPreferredSize(buttonSize);
        removeVertexButton.setMaximumSize(buttonSize);
        removeVertexButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        removeVertexButton.setActionCommand("ELIMINARVERTICE");
        removeVertexButton.addActionListener(this);
        removeVertexButton.
                setBorder(BorderFactory.
                        createCompoundBorder(BorderFactory.
                                createLineBorder(Color.blue),
                                removeVertexButton.getBorder()));

        addEdgeButton = new JButton("AGREGAR ARISTA");
        addEdgeButton.setMinimumSize(buttonSize);
        addEdgeButton.setPreferredSize(buttonSize);
        addEdgeButton.setMaximumSize(buttonSize);
        addEdgeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addEdgeButton.setActionCommand("AGREGARARISTA");
        addEdgeButton.addActionListener(this);
        addEdgeButton.
                setBorder(BorderFactory.
                        createCompoundBorder(BorderFactory.
                                createLineBorder(Color.blue),
                                addEdgeButton.getBorder()));

        removeEdgeButton = new JButton("ELIMINAR ARISTA");
        removeEdgeButton.setMinimumSize(buttonSize);
        removeEdgeButton.setPreferredSize(buttonSize);
        removeEdgeButton.setMaximumSize(buttonSize);
        removeEdgeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        removeEdgeButton.setActionCommand("ELIMINARARISTA");
        removeEdgeButton.addActionListener(this);
        removeEdgeButton.
                setBorder(BorderFactory.
                        createCompoundBorder(BorderFactory.
                                createLineBorder(Color.blue),
                                removeEdgeButton.getBorder()));

        setEdgeWeightButton = new JButton("ENVIAR PESO");
        setEdgeWeightButton.setMinimumSize(buttonSize);
        setEdgeWeightButton.setPreferredSize(buttonSize);
        setEdgeWeightButton.setMaximumSize(buttonSize);
        setEdgeWeightButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        setEdgeWeightButton.setActionCommand("ENVIARPESO");
        setEdgeWeightButton.addActionListener(this);
        setEdgeWeightButton.
                setBorder(BorderFactory.
                        createCompoundBorder(BorderFactory.
                                createLineBorder(Color.blue),
                                setEdgeWeightButton.getBorder()));

        weight = new JTextField("");
        weight.setMinimumSize(new Dimension(90, 50));
        weight.setPreferredSize(new Dimension(90, 50));
        weight.setMaximumSize(new Dimension(90, 50));
        weight.setAlignmentX(1.0f);
        weight.setEditable(false);
        weight.setActionCommand("ENVIARPESO");
        weight.addActionListener(this);
        weight.
                setBorder(BorderFactory.
                        createCompoundBorder(BorderFactory.
                                createLineBorder(Color.green),
                                weight.getBorder()));

        computeMstButton = new JButton("KRUSKAL");
        computeMstButton.setMinimumSize(buttonSize);
        computeMstButton.setPreferredSize(buttonSize);
        computeMstButton.setMaximumSize(buttonSize);
        computeMstButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        computeMstButton.setActionCommand("KRUSKAL");
        computeMstButton.addActionListener(this);
        computeMstButton.
                setBorder(BorderFactory.
                        createCompoundBorder(BorderFactory.
                                createLineBorder(Color.blue),
                                computeMstButton.getBorder()));

        enterButton = new JButton("ENVIAR");
        enterButton.setMinimumSize(buttonSize);
        enterButton.setPreferredSize(buttonSize);
        enterButton.setMaximumSize(buttonSize);
        enterButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        enterButton.setActionCommand("ENVIAR");
        enterButton.addActionListener(this);
        enterButton.
                setBorder(BorderFactory.
                        createCompoundBorder(BorderFactory.
                                createLineBorder(Color.blue),
                                enterButton.getBorder()));

        clearButton = new JButton("LIMPIAR");
        clearButton.setMinimumSize(buttonSize);
        clearButton.setPreferredSize(buttonSize);
        clearButton.setMaximumSize(buttonSize);
        clearButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        clearButton.setActionCommand("LIMPIAR");
        clearButton.addActionListener(this);
        clearButton.
                setBorder(BorderFactory.
                        createCompoundBorder(BorderFactory.
                                createLineBorder(Color.red),
                                clearButton.getBorder()));

        buttonPanel1.add(Box.createHorizontalGlue());
        buttonPanel1.add(addVertexButton);
        buttonPanel1.add(Box.createHorizontalGlue());
        buttonPanel1.add(removeVertexButton);
        buttonPanel1.add(Box.createHorizontalGlue());
        buttonPanel1.add(addEdgeButton);
        buttonPanel1.add(Box.createHorizontalGlue());
        buttonPanel1.add(removeEdgeButton);
        buttonPanel1.add(Box.createHorizontalGlue());

        buttonPanel2.add(Box.createHorizontalGlue());
        buttonPanel2.add(setEdgeWeightButton);
        buttonPanel2.add(Box.createHorizontalGlue());
        buttonPanel2.add(weight);
        buttonPanel2.add(Box.createHorizontalGlue());
        buttonPanel2.add(enterButton);
        buttonPanel2.add(Box.createHorizontalGlue());
        buttonPanel2.add(computeMstButton);
        buttonPanel2.add(Box.createHorizontalGlue());
        buttonPanel2.add(clearButton);
        buttonPanel2.add(Box.createHorizontalGlue());

        contentPane.add((Component) this.canvas);
        contentPane.add(this.buttonPanel1);
        contentPane.add(this.buttonPanel2);
    }

    public void initializeLists() {
        vertices = new ArrayList<>();
        aristas = new ArrayList<>();
        unionsh = new ArrayList<>();
        arbol = new ArrayList<>();
    }

    public static void main(String[] args) {
        // main method where we make and call an instance of KruskalsAlgorithm
        Kruskal trabajo = new Kruskal();
        trabajo.addWindowListener(new WindowAdapter() {
            public void
                    windowClosing(WindowEvent e) {
                System.exit(0);
            }
        }
        );
        trabajo.pack();
        trabajo.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        temporaryEdge = null;
        for (int i = 0; i < this.vertices.size(); ++i) {
            vertices.get(i).mouse = false; // NO RESALTAR LOS VERTICES ANTES
        }
        for (int i = 0; i < this.vertices.size(); ++i) {
            vertices.get(i).mouse = false; // NO RESALTAR ARISTAS ANTES
        }
        arbol.clear(); // BORRAR ARBOL
        canvas.repaint();

        String buttonIdentifier = e.getActionCommand();

        if (buttonIdentifier.equals("AGREGAVERTICE")) {
            boton = Botones.AGREGA_VERTICE;
        } else if (buttonIdentifier.equals("ELIMINARVERTICE")) {
            boton = Botones.ELIMINAR_VERTICE;
        } else if (buttonIdentifier.equals("AGREGARARISTA")) {
            boton = Botones.AGREGA_ARISTA_1;
        } else if (buttonIdentifier.equals("ELIMINARARISTA")) {
            boton = Botones.ELIMINAR_ARISTA;
        } else if (buttonIdentifier.equals("ENVIARPESO")) {
            boton = Botones.ENVIAR_PESO;
        } else if (buttonIdentifier.equals("KRUSKAL")) {
            boton = Botones.KRUSKAL;
            arbol = new Arbol(aristas).getArbol();
            canvas.repaint();
        } else if (buttonIdentifier.equals("LIMPIAR")) {
            vertices.clear();
            aristas.clear();
            unionsh.clear();
            boton = Botones.DEFAULT;
            canvas.repaint();
        } else if (buttonIdentifier.equals("ENVIAR") && boton == Botones.ENVIAR_PESO) {
            // ENTRADA DEL PESO
            String input = weight.getText();
            if (input.length() <= 7) {
                double double1;
                try {
                    double1 = Double.parseDouble(input);
                } catch (NumberFormatException ex) {
                    return;
                }
                Arista ed = aristas.get(changeEdgeWeights);
                ed.peso = double1;
                ed.mouse = false;
                changeEdgeWeights = -1;
                weight.setText("");
                weight.setEditable(false);
                canvas.repaint();
            }
        }

        if (boton != Botones.ENVIAR_PESO) {
            // SI NO HACE CLICK EN ENVIAR PESO NO PUEDE ESCRIBIR NADA EN LA CAJA
            weight.setText("");
            weight.setEditable(false);
            if (changeEdgeWeights > -1) {
                aristas.get(changeEdgeWeights).mouse = false;
                changeEdgeWeights = -1;
            }
            canvas.repaint();
        }
    }

    public int onVertex(Point point) {
        // si el punto del evento ocurrió en un vértice, devuelve el índice si ese vértice está en el vértice; si no, devuelve -1 
        int x = -1;

        for (int i = 0; i < vertices.size(); ++i) {

            Vertice vertex = vertices.get(i);

            if (point.distance(vertex.p) <= 8.0) {
                x = i;
                vertex.mouse = true;
                break;
            }
            vertex.mouse = false;

        }
        return x;
    }

    public int onEdge(Point point) {
        // si el punto del evento ocurrió en una arista, devuelve el índice de esa arista en aristas; si no, devuelve -1 
        int n = -1;

        if (clickedEdgeIndex > -1) {

            Arista edge = aristas.get(clickedEdgeIndex);
            double distanceFromEdge
                    = Line2D.ptSegDist(edge.v1.p.x, edge.v1.p.y, edge.v2.p.x, edge.v2.p.y, point.x, point.y);

            if (point.distance(edge.MedioAgregaPeso()) <= edge.v1.p.distance(edge.v2.p) / 2.0
                    && distanceFromEdge <= 8.0) {
                return clickedEdgeIndex;
            }
            edge.mouse = false;
        }

        for (int i = 0; i < aristas.size(); ++i) {

            Arista edge2 = aristas.get(i);
            double distanceFromEdge2
                    = Line2D.ptSegDist(edge2.v1.p.x, edge2.v1.p.y, edge2.v2.p.x, edge2.v2.p.y, point.x, point.y);

            if (point.distance(edge2.MedioAgregaPeso()) <= edge2.v1.p.distance(edge2.v2.p) / 2.0
                    && distanceFromEdge2 <= 8.0) {
                n = i;
                edge2.mouse = true;
                break;
            }
        }

        return n;
    }

    public void removeVertex(int n) {
        // ELIMINA UN VERTICE UNOOOOOOOOOOOOOOOOOOOOOOOOOOOOO NOMAS
        Vertice vertex = vertices.get(n);

        for (int i = 0; i < vertex.enAristas.size(); ++i) {
            aristas.remove(vertex.enAristas.get(i));
        }

        vertices.remove(n);
    }

    public void mouseClicked(MouseEvent e) {
        // overriding
        switch (boton) {

            case AGREGA_VERTICE: {
                Point point = e.getPoint();
                vertices.add(new Vertice(point)); // AGREGA UN VERTICE
                canvas.repaint();
                break;
            }
            case ELIMINAR_VERTICE: {
                clickedVertexIndex = onVertex(e.getPoint());
                if (clickedVertexIndex >= 0) // SI HACE CLICK EN UN VERTICE ELIMINA EL VERTICE
                {
                    removeVertex(clickedVertexIndex);
                }

                canvas.repaint();
                break;
            }
            case AGREGA_ARISTA_1: {
                clickedVertexIndex = onVertex(e.getPoint());
                if (clickedVertexIndex >= 0) {
                    // SI EL USUARIO HACE CLICK EN UN VERTICE, CAMBIE A AGREGAR VERTICE 2
                    boton = Botones.AGREGA_ARISTA_2;
                    canvas.repaint();
                }
                break;

            }
            case AGREGA_ARISTA_2: {
                int vertexIndex = onVertex(e.getPoint());
                if (vertexIndex != clickedVertexIndex && vertexIndex > -1) {
                    // SI SE HACE CLICK EN UN VERTICE Y ES DIFERENTE AL VERTICE 1 ENTONCES SE CREA UNA ARISTA
                    Vertice vertex = vertices.get(clickedVertexIndex);
                    Vertice vertex2 = vertices.get(vertexIndex);

                    Arista edge = new Arista(vertex, vertex2);
                    aristas.add(edge);
                    vertex.enAristas.add(edge);
                    vertex2.enAristas.add(edge);

                    Vertice vertex3 = vertex;
                    Vertice vertex4 = vertex2;

                    vertex4.mouse = false;
                    vertex3.mouse = false;
                    temporaryEdge = null;
                    boton = Botones.AGREGA_ARISTA_1;
                    canvas.repaint();
                }
                break;

            }
            case ELIMINAR_ARISTA: {
                if (clickedEdgeIndex > -1) {
                    // SI SE HACE CLICK EN LA ARISTA SE ELIMINA
                    Arista edge = aristas.get(clickedEdgeIndex);
                    Vertice vt1 = edge.v1;
                    Vertice vt2 = edge.v2;
                    vt1.enAristas.remove(edge);
                    vt2.enAristas.remove(edge);
                    aristas.remove(clickedEdgeIndex);
                    clickedEdgeIndex = -1;
                    canvas.repaint();
                }
                break;
            }
            case ENVIAR_PESO: {
                if (clickedEdgeIndex > -1 && clickedEdgeIndex != changeEdgeWeights) {
                    // SI SE HACE CLICL EN UNA ARISTA, CAMBIAR EL PESO DE LA ARISTA
                    aristas.get(clickedEdgeIndex).mouse = true;
                    weight.setEditable(true);
                    weight.setText("");
                    if (changeEdgeWeights > -1) {
                        aristas.get(changeEdgeWeights).mouse = false;
                    }
                    changeEdgeWeights = clickedEdgeIndex;
                    canvas.repaint();
                }
                break;
            }
        }
    }

    public boolean edgeExists(Point p1, Point p2) {
        // VERIFICAR SI EXISTE EL BORDE
        boolean exists = false;
        for (int i = 0; i < this.aristas.size(); ++i) {
            Arista edge = this.aristas.get(i);
            if ((edge.v1.p.x == p1.x && edge.v1.p.y == p1.y && edge.v2.p.x == p2.x && edge.v2.p.y == p2.y)
                    || (edge.v1.p.x == p2.x && edge.v1.p.y == p2.y && edge.v2.p.x == p1.x && edge.v2.p.y == p1.y)) {
                exists = true;
                break;
            }
        }
        return exists;
    }

    public void mouseMoved(MouseEvent e) {
        // Overriding
        switch (boton) {
            case DEFAULT: 
            case AGREGA_VERTICE: 
            case ELIMINAR_VERTICE: 
            case AGREGA_ARISTA_1: {
                onVertex(e.getPoint());
                canvas.repaint();
                break;
            }
            case AGREGA_ARISTA_2: {
                Vertice vertex = vertices.get(clickedVertexIndex);
                Point point = e.getPoint();
                temporaryEdge = new Arista(new Vertice(vertex.p), new Vertice(point));
                int location = onVertex(point);

                if (location > -1 && location != clickedVertexIndex) {
                    // if the location is on a vertex and it is not the same vertex as the one already selected for the new edge
                    Vertice vertex2 = vertices.get(location);
                    if (edgeExists(vertex.p, vertex2.p)) {
                        vertex2.mouse = false;
                    }
                }
                vertex.mouse = true;
                canvas.repaint();
                break;
            }
            case ELIMINAR_ARISTA: 
            case ENVIAR_PESO: {
                clickedEdgeIndex = onEdge(e.getPoint());

                if (changeEdgeWeights > -1) {
                    aristas.get(changeEdgeWeights).mouse = true;
                }
                this.canvas.repaint();
                break;
            }
            case KRUSKAL:
        }
    }

    // Overriding
    public void mouseExited(MouseEvent e) {
    }

    // Overriding
    public void mouseEntered(MouseEvent e) {
    }

    // Overriding
    public void mouseReleased(MouseEvent e) {
    }

    // Overriding
    public void mousePressed(MouseEvent e) {
    }

    // Overriding
    public void mouseDragged(MouseEvent e) {
    }

}
