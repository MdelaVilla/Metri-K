/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author manueldelavilla
 */
public class jfrmPergentino extends javax.swing.JFrame {

    /**
     * Creates new form jfrmPergentino
     */
    private File f_a_tratar = null;
    private final ArrayList<Clase> lista = new ArrayList<>();
    private final DefaultTableModel model = new DefaultTableModel();

    public jfrmPergentino(File f) {
        initComponents();
        f_a_tratar = f;
        tblResultados.setModel(model);

        model.addColumn("Clase");
        model.addColumn("WMC (Weighted Methods per Class)");
        model.addColumn("DIT (Depth of Inheritance Tree");
        model.addColumn("NOC (Number Of Children)");
        
        cargarTabla();
    }

    //******  M�TODOS A IMPLEMENTAR ******
    private void WMC() {
        //Calculamos los m�todos ponderados por clase
        //Teniendo en cuenta la complejidad ciclom�tica, normalizo la complejidad a c=1
        FileReader fr = null;
        BufferedReader br = null;
        int CC = 1; //Complejidad ciclom�tica

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            fr = new FileReader(f_a_tratar);
            br = new BufferedReader(fr);
            int posClase = 0;

            // Lectura del fichero
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
                //Ahora procedemos a tratar el fichero linea por linea dependiendo de nuestra necesidad
                if (linea.contains("class") && linea.contains("{")) {
                    posClase++;
                    boolean enc = false;
                    String[] tokens = linea.split(" ");
                    for (int i = 0; i < tokens.length && !enc; i++) {
                        if (tokens[i].equals("class")) {
                            lista.add(new Clase(tokens[i + 1]));
                            enc = true;
                        }
                    }
                } else if ((linea.contains("public") || linea.contains("private") || linea.contains("protected"))
                        && (linea.contains("(") && linea.contains(")"))) {
                    //Incrementamos el n�mero de m�todos mas uno y lo multiplicamos por su Complejidad Ciclom�tica
                    lista.get(posClase - 1).setNumMetodos((lista.get(posClase - 1).getNumMetodos() + 1) * CC);
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta 
            // una excepcion.
            try {
                if (null != fr) {
                    fr.close();
                }
                if (br != null) {
                    br.close();
                }
            } catch (Exception e2) {
                System.out.println("Error: " + e2.getMessage());
            }
        }

    }

    private void DIT() {
        //Calculamos la profundidad del arbol de herencia de una clase
                FileReader fr = null;
        BufferedReader br = null;

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            fr = new FileReader(f_a_tratar);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
                //Buscamos si hay alguna herencia
                if (linea.contains("class") && linea.contains("extends") && linea.contains("{") ) {
                    boolean enc = false;
                    String[] tokens = linea.split(" ");
                    for (int i = 0; i < tokens.length && !enc; i++) {
                        if (tokens[i].equals("extends")) {
                            //Buscamos la clase en la lista
                            boolean encontrado = false;
                            String hijo = tokens[i-1];
                            String padre = tokens[i+1];
                            int posHijo = -1;
                            
                            //Buscamos al hijo y le asignamos a su padre
                            for (int j = 0; j < lista.size() && !encontrado; j++) {
                                if( lista.get(j).getNombre().equals( hijo ) ){
                                    lista.get(j).setPadre( padre );
                                    System.out.println("Padre: " + padre + " E Hijo: " + hijo);
                                    encontrado = true;
                                    posHijo = j;
                                }
                            }
                            
                            //Buscamos al padre y le asignamos al hijo la profundidad del padre+1
                            encontrado = false;
                            for (int j = 0; j < lista.size() && !encontrado; j++) {
                                if( lista.get(j).getNombre().equals( padre ) ){
                                    int profPadre = lista.get(j).getProfundidad();
                                    lista.get(posHijo).setProfundidad( profPadre + 1 );
                                    encontrado = true;
                                }
                            }
                            enc = true;
                        }
                        
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error DIT: " + e.getMessage());
        } finally {
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta 
            // una excepcion.
            try {
                if (null != fr) {
                    fr.close();
                }
                if (br != null) {
                    br.close();
                }
            } catch (Exception e2) {
                System.out.println("Error: " + e2.getMessage());
            }
        }
    }

    private void NOC() {
        //Calculamos el n�mero de hijos de una clase
        FileReader fr = null;
        BufferedReader br = null;

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            fr = new FileReader(f_a_tratar);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
                //Ahora procedemos a tratar el fichero linea por linea dependiendo de nuestra necesidad
                if (linea.contains("class") && linea.contains("{")) {
                    boolean enc = false;
                    String[] tokens = linea.split(" ");
                    for (int i = 0; i < tokens.length && !enc; i++) {
                        if (tokens[i].equals("class")) {
                            int posClase = -1;
                            boolean es = false;
                            
                            //Buscamos la posici�n de la clase en la lista
                            for (int j = 0; j < lista.size() && !es; j++) {
                                if( lista.get(j).getNombre().equals( tokens[i+1]) ){
                                    es = true;
                                    posClase = j;
                                }
                            }
                            
                            //Buscamos las clases que tienen a esta clase como padre, y le vamos incrementando el numero de hijos a la clase
                            for (int j = 0; j < lista.size(); j++) {
                                    
                                if( lista.get(j).getPadre().equals( tokens[i+1] ) ){
                                    lista.get(posClase).setHijos( lista.get(posClase).getHijos() + 1 );
                                }
                            }
                            enc = true;
                        }
                    }
                } 
            }

        } catch (Exception e) {
            System.out.println("Error NOC: " + e.getMessage());
        } finally {
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta 
            // una excepcion.
            try {
                if (null != fr) {
                    fr.close();
                }
                if (br != null) {
                    br.close();
                }
            } catch (Exception e2) {
                System.out.println("Error: " + e2.getMessage());
            }
        }
    }

    private void cargarTabla() {

        //Llamamos primero a los m�todos que realizan las m�tricas
        WMC();
        DIT(); 
        NOC(); 

        Object[] fila = new Object[4];
        for (Clase item : lista) {
            fila[0] = item.getNombre();
            fila[1] = item.getNumMetodos();
            fila[2] = item.getProfundidad();
            fila[3] = item.getHijos();
            //insertamos la fila en la tabla resultados
            model.addRow(fila);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpPrincipal = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblResultados = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MOOSE 1 (WMC, DIT Y NOC)");
        setMinimumSize(new java.awt.Dimension(650, 400));

        jpPrincipal.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "MOOSE 1 (WMC, DIT Y NOC) Pergentino L. Edjang Nchama", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        tblResultados.setFont(new java.awt.Font("Century", 0, 14)); // NOI18N
        tblResultados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Clase", "WMC", "DIT", "NOC"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblResultados);

        javax.swing.GroupLayout jpPrincipalLayout = new javax.swing.GroupLayout(jpPrincipal);
        jpPrincipal.setLayout(jpPrincipalLayout);
        jpPrincipalLayout.setHorizontalGroup(
            jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 492, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpPrincipalLayout.setVerticalGroup(
            jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpPrincipalLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(129, 129, 129))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel jpPrincipal;
    private javax.swing.JTable tblResultados;
    // End of variables declaration//GEN-END:variables
}

class Clase {

    private String nombre;
    private int profundidad;
    private int numMetodos;
    private String padre;
    private int hijos;

    public Clase(String nombre) {
        this.nombre = nombre;
        this.padre = "";
        this.numMetodos = 0;
        this.hijos = 0;
        this.profundidad = 0;
    }
    
    public String getPadre() {
        return padre;
    }

    public void setPadre(String padre) {
        this.padre = padre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getProfundidad() {
        return profundidad;
    }

    public void setProfundidad(int profundidad) {
        this.profundidad = profundidad;
    }

    public int getHijos() {
        return hijos;
    }

    public void setHijos(int hijos) {
        this.hijos = hijos;
    }

    public int getNumMetodos() {
        return numMetodos;
    }

    public void setNumMetodos(int numMetodos) {
        this.numMetodos = numMetodos;
    }

}
