/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
//el marcys okomo de CMEPPS-->17/01/2018
/**
 *
 * @author manueldelavilla
 */
public class jfrmPrincipal extends javax.swing.JFrame {

    /**
     * Creates new form jfrmPrincipal
     */
    File fichero = null;
    
    public jfrmPrincipal() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jlblFiletext = new javax.swing.JLabel();
        jlblFile = new javax.swing.JLabel();
        jmnuBarra = new javax.swing.JMenuBar();
        jmnuProyecto = new javax.swing.JMenu();
        jmnuPAbrir = new javax.swing.JMenuItem();
        jmnuPCerrar = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jmnuPSalir = new javax.swing.JMenuItem();
        jmnuMetriClasicas = new javax.swing.JMenu();
        jmnuMLOC = new javax.swing.JMenuItem();
        jmnuMHalstead = new javax.swing.JMenuItem();
        jmnuCC = new javax.swing.JMenuItem();
        jmnuMetriOO = new javax.swing.JMenu();
        jmnuMM_WMC = new javax.swing.JMenuItem();
        jmnuMM_CBO = new javax.swing.JMenuItem();
        jmnuMM_LCOM = new javax.swing.JMenuItem();
        jmnuMetriMood = new javax.swing.JMenu();
        jmnuMM_MHF = new javax.swing.JMenuItem();
        jmnuMM_MIF = new javax.swing.JMenuItem();
        jmnuMM_PF = new javax.swing.JMenuItem();
        jmnuMetriLK = new javax.swing.JMenu();
        jmnuMM_Tamano = new javax.swing.JMenuItem();
        jmnuMM_Herencia = new javax.swing.JMenuItem();
        jmnuMM_CIC = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jlblFiletext.setText("Fichero a tratar:");

        jlblFile.setText(" ");

        jmnuProyecto.setText("Proyecto");

        jmnuPAbrir.setText("Abrir");
        jmnuPAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmnuPAbrirActionPerformed(evt);
            }
        });
        jmnuProyecto.add(jmnuPAbrir);

        jmnuPCerrar.setText("Cerrar");
        jmnuPCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmnuPCerrarActionPerformed(evt);
            }
        });
        jmnuProyecto.add(jmnuPCerrar);
        jmnuProyecto.add(jSeparator1);

        jmnuPSalir.setText("Salir");
        jmnuPSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmnuPSalirActionPerformed(evt);
            }
        });
        jmnuProyecto.add(jmnuPSalir);

        jmnuBarra.add(jmnuProyecto);

        jmnuMetriClasicas.setText("Metricas cl�sicas");

        jmnuMLOC.setText("M�tricas LOC");
        jmnuMetriClasicas.add(jmnuMLOC);

        jmnuMHalstead.setText("M�tricas Halstead");
        jmnuMetriClasicas.add(jmnuMHalstead);

        jmnuCC.setText("Complejidad ciclom�tica");
        jmnuCC.setEnabled(false);
        jmnuCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmnuCCActionPerformed(evt);
            }
        });
        jmnuMetriClasicas.add(jmnuCC);

        jmnuBarra.add(jmnuMetriClasicas);

        jmnuMetriOO.setText("Moose");

        jmnuMM_WMC.setText("WMC, DIT y NOC");
        jmnuMM_WMC.setEnabled(false);
        jmnuMM_WMC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmnuMM_WMCActionPerformed(evt);
            }
        });
        jmnuMetriOO.add(jmnuMM_WMC);

        jmnuMM_CBO.setText("CBO y RFC");
        jmnuMM_CBO.setEnabled(false);
        jmnuMM_CBO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmnuMM_CBOActionPerformed(evt);
            }
        });
        jmnuMetriOO.add(jmnuMM_CBO);

        jmnuMM_LCOM.setText("LCOM");
        jmnuMM_LCOM.setEnabled(false);
        jmnuMM_LCOM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmnuMM_LCOMActionPerformed(evt);
            }
        });
        jmnuMetriOO.add(jmnuMM_LCOM);

        jmnuBarra.add(jmnuMetriOO);

        jmnuMetriMood.setText("Mood");

        jmnuMM_MHF.setText("MHF y AHF");
        jmnuMetriMood.add(jmnuMM_MHF);

        jmnuMM_MIF.setText("MIF y AIF");
        jmnuMM_MIF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmnuMM_MIFActionPerformed(evt);
            }
        });
        jmnuMetriMood.add(jmnuMM_MIF);

        jmnuMM_PF.setText("PF");
        jmnuMetriMood.add(jmnuMM_PF);

        jmnuBarra.add(jmnuMetriMood);

        jmnuMetriLK.setText("Lorenz y Kidd");

        jmnuMM_Tamano.setText("Tama?o");
        jmnuMetriLK.add(jmnuMM_Tamano);

        jmnuMM_Herencia.setText("Herencia");
        jmnuMetriLK.add(jmnuMM_Herencia);

        jmnuMM_CIC.setText("Caract. internas");
        jmnuMetriLK.add(jmnuMM_CIC);

        jmnuBarra.add(jmnuMetriLK);

        setJMenuBar(jmnuBarra);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jlblFiletext)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlblFile)
                .addContainerGap(424, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(256, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblFiletext)
                    .addComponent(jlblFile))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jmnuMM_MIFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmnuMM_MIFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jmnuMM_MIFActionPerformed

    private void jmnuPAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmnuPAbrirActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Java, c++ y c", "java", "cpp", "c");
        fileChooser.setFileFilter(filter);

        int seleccion = fileChooser.showOpenDialog(this);
        if (seleccion == JFileChooser.APPROVE_OPTION)
        {
            //File fichero = fileChooser.getSelectedFile(); //Errata, lo he comentado por doble declacion y eso nunca funciona
             fichero = fileChooser.getSelectedFile();
            //Fichero disponible para procesamiento posterior
            jlblFile.setText(fichero.getName());
        }       
        
        jmnuMM_WMC.setEnabled(true);
        jmnuCC.setEnabled(true);
        jmnuMM_LCOM.setEnabled(true);
        jmnuMM_CBO.setEnabled(true);
    }//GEN-LAST:event_jmnuPAbrirActionPerformed

    private void jmnuPCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmnuPCerrarActionPerformed
        fichero = null;
        jlblFile.setText(" ");
        jmnuMM_WMC.setEnabled(false);
        jmnuCC.setEnabled(false);
        jmnuMM_LCOM.setEnabled(false);
        jmnuMM_CBO.setEnabled(false);

    }//GEN-LAST:event_jmnuPCerrarActionPerformed

    private void jmnuPSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmnuPSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_jmnuPSalirActionPerformed

    private void jmnuCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmnuCCActionPerformed
        JFrame jf= new jfrmFJBlandon(fichero);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(jf.DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_jmnuCCActionPerformed

    private void jmnuMM_CBOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmnuMM_CBOActionPerformed
        JFrame jf= new jfrmAntonioRuiz(fichero);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(jf.DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_jmnuMM_CBOActionPerformed

    private void jmnuMM_WMCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmnuMM_WMCActionPerformed
        JFrame jf= new jfrmPergentino(fichero);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(jf.DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_jmnuMM_WMCActionPerformed

    private void jmnuMM_LCOMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmnuMM_LCOMActionPerformed
        JFrame jf= new jfrmMarcianoNze(fichero);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(jf.DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_jmnuMM_LCOMActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(jfrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jfrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jfrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jfrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new jfrmPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JLabel jlblFile;
    private javax.swing.JLabel jlblFiletext;
    private javax.swing.JMenuBar jmnuBarra;
    private javax.swing.JMenuItem jmnuCC;
    private javax.swing.JMenuItem jmnuMHalstead;
    private javax.swing.JMenuItem jmnuMLOC;
    private javax.swing.JMenuItem jmnuMM_CBO;
    private javax.swing.JMenuItem jmnuMM_CIC;
    private javax.swing.JMenuItem jmnuMM_Herencia;
    private javax.swing.JMenuItem jmnuMM_LCOM;
    private javax.swing.JMenuItem jmnuMM_MHF;
    private javax.swing.JMenuItem jmnuMM_MIF;
    private javax.swing.JMenuItem jmnuMM_PF;
    private javax.swing.JMenuItem jmnuMM_Tamano;
    private javax.swing.JMenuItem jmnuMM_WMC;
    private javax.swing.JMenu jmnuMetriClasicas;
    private javax.swing.JMenu jmnuMetriLK;
    private javax.swing.JMenu jmnuMetriMood;
    private javax.swing.JMenu jmnuMetriOO;
    private javax.swing.JMenuItem jmnuPAbrir;
    private javax.swing.JMenuItem jmnuPCerrar;
    private javax.swing.JMenuItem jmnuPSalir;
    private javax.swing.JMenu jmnuProyecto;
    // End of variables declaration//GEN-END:variables
}
