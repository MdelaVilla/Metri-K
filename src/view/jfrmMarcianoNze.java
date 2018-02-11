/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author manueldelavilla
 */
public class jfrmMarcianoNze extends javax.swing.JFrame {

    /**
     * Creates new form jfrmMarcianoNze
     */
    File f_a_tratar = null; //repositorios

    private ArrayList<String> variables; //AQUI ALMACENO LAS VARIABLES FILTRADAS SIN REPETICION
    ArrayList<String> textos;

    Pattern patron; //PATRON
    Matcher m;//PATRON
    private String cadena,secuenciaAccesoMetodos=""; //GUARDA LA SECUENCIA DE ATRIBUTOS/METODOS EN CADA ITERACIÓN
    private String nombreClase = "";
    ArrayList<ArrayList> arrayInterseccion; //VECTOR QUE ALMACENA LOS VECTORES DE METODOS/ATRIBUTOS
    private int LCOM = 0,//determinara el numero de conjuntos o grupos que se obtiene como resultado de la LCOM o cohesion
            lsin = 0,//almacena el numero de metodos de la clase tratada excluido el constructor
            lcom = 0;//almacena el numero de intersecciones que se obtiene en el estudio de cohesion

    //CALCULO DE LCOM SEGUN LOS METODO Y ATRIBUTOS-->COHESION
    public jfrmMarcianoNze(File f) {    //parte de marcys okomo, el marcys. lo subo ahora
        initComponents();
        f_a_tratar = f;

    }

    //AQUI REALIZO LOS CALCULOS DE LCOM O NIVEL DE COHESION. SE ADJUNTA LA SECUANCIA DE PRUEBAS DE CONSOLA
    public int calculo_LCOM() throws IOException {
        LCOM =lsin=lcom=0;//determinara el numero de conjuntos o grupos que se obtiene como resultado de la LCOM o cohesion
           
        obtenerAtributosXmetodos();
        ArrayList<ArrayList> arrayCopia = arrayInterseccion;
        int i = 0;
        while (arrayInterseccion.size() > 1) {
            ArrayList<String> v1 = arrayInterseccion.get(0);
            arrayInterseccion.remove(0);
            System.out.println("El array vale despues de borrar el primero " + arrayInterseccion.size());
            System.out.println("valores que tiene:      " + arrayInterseccion.toString());
            System.out.println("tamanio del nuevo arrray" + arrayInterseccion.size());
            for (int j = 0; j < arrayInterseccion.size(); j++) {
                System.out.println("estoy pasando" + arrayInterseccion.get(j));
                if (compara(v1, arrayInterseccion.get(j)) != 0) { //llamo al metodo que hace la busqueda de coicidencias
                    lcom++;
                }
            }
        }

        System.out.println("existe interseccion clara...." + lcom + " coicidencias en total");
        lsin = textos.size();
        return (LCOM = lsin - lcom);
    }

//METODO QUE SIRVE PARA CALCULAR LA COIDENCIA ENTRE LOS CONJUNTOS DEL GRAFO DE ACCESIBILIDAD
    public int compara(ArrayList<String> v1, ArrayList<String> v2) {
        int i = 0, j;
        while (i < v1.size()) {
            String x = v1.get(i);
            j = 0;

            while (j < v2.size()) {
                if (x.equals(v2.get(j))) {
                    return 1;
                } else {
                    j++;
                }
            }
            i++;
        }

        return 0;
    }

    //METODO QUE SE ENCARGA DE DETERMINAR SI ESTAMOS ACCESIDIENDO A UN ATRIBUTO O NO
    //LO CONSIGO FILTRAR GRACIAS A LAS LIBRERIAS PATTER Y MATCHER 
    public void leerEscribeAtributo(String linea) {
        //&& o.equals(o.getClass().getDeclaredFields()
        int j = 0, cuenta = 0;
        // System.out.println("linea pasada " + linea);
        String[] l = linea.split(" |,|;");
        for (String o : l) {
            //DEFINO LA REGLA
            String r2 = "[a-z]<+|Scanner+|new+|=+|private+|float+|final+|static+|public+|int+|String+|<+|>+|char+|Iterator+|List+|PrintWriter+|FileReader+|FileWriter+|BufferedReader+|Object+|const+|null+";
            patron = Pattern.compile(r2);
            m = patron.matcher(o);
            if (!m.find()) {
                if (!o.equals("")) {
                    boolean yalotiene = false;
                    int i = 0;
                    while (i < variables.size() && !yalotiene) {
                        if (variables.get(i).equals(o)) {
                            yalotiene = true;
                        } else {
                            i++;
                        }
                    }
                    if (yalotiene == false) {
                        variables.add(o);
                    }

                }
            }
        }

    }

    //Aqui empieza todo. parseamos el fichero y hacemos uso de expresiones regulares -->MATTER y MATCHER
    public void evaluarCohesion_LCOM() throws IOException {
        int atributos = 0;
        variables = new ArrayList<>();
        textos = new ArrayList<>();
        FileReader f = new FileReader(f_a_tratar);
        BufferedReader b = new BufferedReader(f);
        String linea = "";
        int contador = 1, y = 0;
        while ((cadena = b.readLine()) != null) { //no llegamos al final EOF
            String regla3 = "class"; //defino una regla que determina si he leido la linea y entrar en clase

            patron = Pattern.compile(regla3);
            // if(!cadena.equals(""))
            Matcher m1 = patron.matcher(cadena); //captura una clase
            // System.out.println("cadena impresa.." + cadena);
            if (!m1.find()) {
                System.out.println("No es una clase");
            } else if (y == 0) {

                //Capturo el nombre de la clase que se esta tratando.
                String lineaclase[] = cadena.split(" ");
                int existe = 0, sale = 0;
                while (existe < lineaclase.length && sale == 0) {
                    if (lineaclase[existe].equals(regla3)) {
                        sale = existe + 1;
                        nombreClase = lineaclase[sale];
                        System.out.println("Nombre de la clase es : " + nombreClase);
                        System.out.println("La clase se llama " + lineaclase[sale] + " y se encuantra en la posicion : " + sale);
                    } else {
                        existe++;
                    }
                }

                //AQUI PASO LAS LINEAS PARA CAPTURAR LOS ATRIBUTOS GLOBALES ANTES DE LLEGAR A LOS PUBLIC DE LAS CLASES
                System.out.println("Es una clase " + cadena);
                while (!(cadena = b.readLine()).contains("public")) {
                    if (!cadena.startsWith("//") && !cadena.startsWith("/*") && !cadena.startsWith("{") && !cadena.startsWith("}")) {

                        leerEscribeAtributo(cadena); //llamo al metodo que se dedica a filtrar los atributos
                        y += 1;
                    }
                }
            }
            if (y != 0) { //anadimos el cuerpo del metodo
                String r1 = "public|private";
                patron = Pattern.compile(r1);
                m = patron.matcher(cadena);
                if (m.find()) {
                    if (!linea.equals("")) {
                        textos.add(linea);
                    }
                    linea = "";
                }
                if (!m.find()) {
                    linea += cadena + "\n";
                }
            }
        }
        if (!linea.equals("")) {
            textos.add(linea);
        }
        b.close();
        
        limitaConstructor();//limito el acceso del constructor

    }

    //limito el acceso a las variables por medio del constructor. no debo tener en cuenta el constructor si quiero estudiar la cohesion
    //en cuyo caso tendriamos que todos los metodos tienen la mismas referencia casi muchas veces. no entra en mi estudio
    public void limitaConstructor() {
        String r1 = "[ ]+" + nombreClase + "[(]+";
        patron = Pattern.compile(r1);
        System.out.println(" textos tiene--> " + textos);
        for (int i = 0; i < textos.size(); i++) {
            m = patron.matcher(textos.get(i).split("\n")[0]);
            if (m.find()) {
                textos.remove(0);
            }
        }
        //obtenerAtributosXmetodos(); //llamo a obtener atributos despues de limitar el acceso del aconstructor
    }

    //AQUI CAPTURO LOS ATRIBUTOS QUE TIENE CADA METODO Y LOS ALMACENO EN UNA ARRAAY DE ARRAY PARA LUEGO PROCESARLO
    public void obtenerAtributosXmetodos() throws IOException { 
        evaluarCohesion_LCOM();
        arrayInterseccion = new ArrayList<>();
        for (int i = 0; i < textos.size(); i++) { //POR CADA METODO CAPTURO LOS ATRIBUTOS QUE HACE REFERENCIA
            int j =i+1;
            secuenciaAccesoMetodos += "\nEl metodo " +j + " accede a los atributos:  " ;
            ArrayList<Object> nuevo = new ArrayList<>();
            for (String variable : variables) {
                int numerometodos = 0;

                //Aqui defino la regla que determina si un atributo se usa en un el metodo <i>, lo hago por cada atributo
                String r1 = "[ ]" + variable + "+|[.]" + variable + "+ |[=]" + variable + "+|[(]" + variable + "+|[)]" + variable + "+"
                        + "|[)]" + variable + "|[-]" + variable + "+|[*]" + variable + "+|[/]" + variable + "+";
                Pattern patron1 = Pattern.compile(r1);

                Matcher m1 = patron1.matcher(textos.get(i));
                if (m1.find()) {
                    nuevo.add(variable);//anado el atributo a la lista de los atributos que se usan en la clase.
                    numerometodos++;
                    secuenciaAccesoMetodos += "  " +variable; //APTURO LA SECUANCIA
                }
            }
            
            arrayInterseccion.add(nuevo); //ANADO UN NUEVO VECTOR DE ATRIBUTOS O SEA METODO
        }
        System.out.println("Los metodos acceden como sigue:........" + secuenciaAccesoMetodos);

        System.out.println("Los resultados de la interseccion son: ...\n");
        for (int i = 0; i < arrayInterseccion.size(); i++) {
            int k = i + 1;
            System.out.println("\n\nlos atribtos del metodo: --> " + k);
            for (int j = 0; j < arrayInterseccion.get(i).size(); j++) {
                System.out.print(arrayInterseccion.get(i).get(j) + ", ");
            }
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

        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jButtonInfos = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaMostrarDatos = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jButtonEstadistica = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaEsdistica = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jButtonExplicacion = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextAreaExplicacion = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextAreaDetallesConjunto = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("MOOSE 3 - LCOM - Marciano Nze Nzeme");

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jTabbedPane1.setForeground(new java.awt.Color(255, 255, 255));

        jButtonInfos.setBackground(new java.awt.Color(0, 0, 51));
        jButtonInfos.setFont(new java.awt.Font("Sylfaen", 1, 14)); // NOI18N
        jButtonInfos.setForeground(new java.awt.Color(255, 255, 255));
        jButtonInfos.setText("Consultar Atributos");
        jButtonInfos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInfosActionPerformed(evt);
            }
        });

        jTextAreaMostrarDatos.setBackground(new java.awt.Color(0, 51, 51));
        jTextAreaMostrarDatos.setColumns(20);
        jTextAreaMostrarDatos.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jTextAreaMostrarDatos.setForeground(new java.awt.Color(255, 255, 255));
        jTextAreaMostrarDatos.setRows(5);
        jScrollPane1.setViewportView(jTextAreaMostrarDatos);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(142, 142, 142)
                        .addComponent(jButtonInfos))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 468, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonInfos, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(106, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Información", jPanel1);

        jButtonEstadistica.setBackground(new java.awt.Color(0, 0, 51));
        jButtonEstadistica.setFont(new java.awt.Font("Sylfaen", 1, 14)); // NOI18N
        jButtonEstadistica.setForeground(new java.awt.Color(255, 255, 255));
        jButtonEstadistica.setText("Consultar estadístoca");
        jButtonEstadistica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEstadisticaActionPerformed(evt);
            }
        });

        jTextAreaEsdistica.setBackground(new java.awt.Color(0, 51, 51));
        jTextAreaEsdistica.setColumns(20);
        jTextAreaEsdistica.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jTextAreaEsdistica.setForeground(new java.awt.Color(255, 255, 255));
        jTextAreaEsdistica.setRows(5);
        jScrollPane2.setViewportView(jTextAreaEsdistica);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 635, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(153, 153, 153)
                .addComponent(jButtonEstadistica)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jButtonEstadistica, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Estadística de atr/métodos", jPanel2);

        jButtonExplicacion.setBackground(new java.awt.Color(0, 0, 51));
        jButtonExplicacion.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButtonExplicacion.setForeground(new java.awt.Color(255, 255, 255));
        jButtonExplicacion.setText("Consultar explicación");
        jButtonExplicacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExplicacionActionPerformed(evt);
            }
        });

        jTextAreaExplicacion.setBackground(new java.awt.Color(0, 102, 102));
        jTextAreaExplicacion.setColumns(20);
        jTextAreaExplicacion.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jTextAreaExplicacion.setForeground(new java.awt.Color(255, 255, 255));
        jTextAreaExplicacion.setRows(5);
        jScrollPane3.setViewportView(jTextAreaExplicacion);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(179, 179, 179)
                        .addComponent(jButtonExplicacion))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 453, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(180, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonExplicacion, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Explicación detallada por cohesión", jPanel3);

        jTextAreaDetallesConjunto.setBackground(new java.awt.Color(0, 102, 102));
        jTextAreaDetallesConjunto.setColumns(20);
        jTextAreaDetallesConjunto.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jTextAreaDetallesConjunto.setForeground(new java.awt.Color(255, 255, 255));
        jTextAreaDetallesConjunto.setRows(5);
        jScrollPane4.setViewportView(jTextAreaDetallesConjunto);

        jButton1.setBackground(new java.awt.Color(0, 0, 51));
        jButton1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Detalles/Conjuntos");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(263, 263, 263)
                .addComponent(jButton1)
                .addContainerGap(227, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(20, 20, 20)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 601, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(34, Short.MAX_VALUE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addContainerGap(291, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                    .addGap(36, 36, 36)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jTabbedPane1.addTab("Detalles Cohesión/Conjuntos", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(jLabel1)
                .addContainerGap(423, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 664, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonExplicacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExplicacionActionPerformed

        try {
            jTextAreaExplicacion.requestFocus();
            jTextAreaExplicacion.setText("");
            calculo_LCOM();
            jTextAreaExplicacion.setText("EXPLICACION DEL GRADO DE COHESIÓN DE LA CLASE SEGUN ATRIBUTOS POR SEPARADOS:\n");
            for (String variable : variables) {
                jTextAreaExplicacion.setText(jTextAreaExplicacion.getText() + "\nCOHESIÓN RESPECTO AL ATRIBUTO  <" + variable + "> :");
                int numerometodos = 0;

                String r1 = "[ ]" + variable + "+|[.]" + variable + "+ |[=]" + variable + "+|[(]" + variable + "+|[)]" + variable + "+"
                        + "|[)]" + variable + "|[-]" + variable + "+|[*]" + variable + "+|[/]" + variable + "+";
                Pattern patron1 = Pattern.compile(r1);
                for (int i = 0; i < textos.size(); i++) {

                    Matcher m1 = patron1.matcher(textos.get(i));
                    if (m1.find()) {

                        numerometodos++;
                    }
                }
                if (textos.size() > numerometodos) {
                    jTextAreaExplicacion.setText(jTextAreaExplicacion.getText() + "\n\tEl atributo " + variable + " se utiliza en "
                            + " " + numerometodos + " métodos,\n\t menor que numero de métodos-->" + textos.size() + "-->baja cohesión");
                }

            }
            jTextAreaExplicacion.setText(jTextAreaExplicacion.getText()
                    + "\n\nEs mejor que los atributos se usen en todos los métodos de la clase,"
                    + "\n para que el software sea de mejor y mucho más estable y fácil de"
                    + "\nser escalable. Si los atributos no se usan en todos los métodos eso "
                    + "\nnos da entender que se debería modular mejor la programación. Un mejor"
                    + "\n desarrollo sofware siempre se consigue, entre otas, con una alta COHESIÓN."
                    + "\na Diferencia del grado del acopla miento entre objetos en el desarrollo"
                    + "\nde software, a bajo acoplamiento mejor sofware ");

        } catch (IOException ex) {

        }
    }//GEN-LAST:event_jButtonExplicacionActionPerformed


    private void jButtonEstadisticaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEstadisticaActionPerformed

        try {
            calculo_LCOM();
            jTextAreaEsdistica.setText("");
            jTextAreaEsdistica.requestFocus();

            jTextAreaEsdistica.setText(jTextAreaEsdistica.getText() + " \n"
                    + "LOS DATOS ESTADISTICOS DEL ESTUDIO REALIZADO. ");
            for (String variable : variables) {
                jTextAreaEsdistica.setText(jTextAreaEsdistica.getText() + "\nEstadística del atribuo  <" + variable + ">  es:");
                int numerometodos = 0;

                String r1 = "[ ]" + variable + "+|[.]" + variable + "+ |[=]" + variable + "+|[(]" + variable + "+|[)]" + variable + "+";
                Pattern patron1 = Pattern.compile(r1);
                for (int i = 0; i < textos.size(); i++) {

                    Matcher m1 = patron1.matcher(textos.get(i));
                    if (m1.find()) {

                        numerometodos++;
                    }
                }

                jTextAreaEsdistica.setText(jTextAreaEsdistica.getText() + "\n\t El atributo  <" + variable + ">  se utilisa en " + numerometodos + " métodos");
                numerometodos = 0;
            }
        } catch (IOException ex) {
            Logger.getLogger(jfrmMarcianoNze.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_jButtonEstadisticaActionPerformed

    private void jButtonInfosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInfosActionPerformed
        try {
            jTextAreaMostrarDatos.setText("");
            jTextAreaMostrarDatos.requestFocus();

            calculo_LCOM();
            jTextAreaMostrarDatos.setText(jTextAreaMostrarDatos.getText() + " "
                    + "AQUÍ SE DESCRIBE EL NUMERO DE ATRIBUTOS Y MÉTODOS ENCONTRADOS.\n");
            jTextAreaMostrarDatos.setText(jTextAreaMostrarDatos.getText() + "\n Tenemos un total de  " + variables.size() + " atributos en la clase y, \n\n Tnemos un total de " + textos.size() + " metodos en la clase\n\n");

            jTextAreaMostrarDatos.setText(jTextAreaMostrarDatos.getText() + " \n "
                    + "Este caso concreto, al hacer un estudio de la clase o fichero"
                    + "\npasado por paramtro,la información sobre los atribuso y métodos "
                    + "\ntal como se ha descrito en las lineas anteriores.");
        } catch (IOException ex) {
            Logger.getLogger(jfrmMarcianoNze.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonInfosActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            jTextAreaDetallesConjunto.setText("");
            jTextAreaDetallesConjunto.requestFocus();
            calculo_LCOM();
            jTextAreaDetallesConjunto.setText( jTextAreaDetallesConjunto.getText() + " "
            +" LOS RESULTADOS OBTENIDOS DEL GRAFO INTERSECCION SE DESCRIBEN COMO SIGUE: \n\n"
                    + "Conjuntos de metodos según atributos: "
                    + secuenciaAccesoMetodos + "\n\nTenemos que: Numero de metodos : " + textos.size() + ""
                            + "\nNumero de atributos " + variables.size()+" \n\n"
                                    + " Haciendo el calculo de interseccion, LCOM=lsin-lcom,\ncon lsin-->" +textos.size()+""
                                            + "y lcom-->" + lcom +"\n"
                                                    + "\tLCOM(nivel de Accesibilidad/Coheion es -->)" + LCOM + "\n"
                                  + "\nDeducciones:\n El numero de clases necesarias coincide con LCOM\n"
                                                            + ", Por efectos de mantimiento y efeiciencia se deberia disenar mejor la\n"
                                                            + "programacion de la clase."
                                                            + "Si LCOM =1, decimos que hay una muy buena cohesión\n"
                                                            + "Para valores de LCOM<0 -->Buena cohesion\n"
                                                            + "Pero, si LCOM>1, tenemos que hacer mejor la modulación");
        } catch (IOException ex) {
            Logger.getLogger(jfrmMarcianoNze.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonEstadistica;
    private javax.swing.JButton jButtonExplicacion;
    private javax.swing.JButton jButtonInfos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextAreaDetallesConjunto;
    private javax.swing.JTextArea jTextAreaEsdistica;
    private javax.swing.JTextArea jTextAreaExplicacion;
    private javax.swing.JTextArea jTextAreaMostrarDatos;
    // End of variables declaration//GEN-END:variables
}
