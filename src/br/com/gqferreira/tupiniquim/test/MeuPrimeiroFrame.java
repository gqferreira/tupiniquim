package br.com.gqferreira.tupiniquim.test;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Classe
 *
 * @author Gustavo Ferreira www.gqferreira.com.br Copyright 2013 gustavo.
 */
public class MeuPrimeiroFrame extends JFrame {

    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu10;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenu jMenu9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    //private org.jdesktop.beansbinding.BindingGroup bindingGroup;

    public static void main (String[] args){
        new MeuPrimeiroFrame().setVisible(true);
    }
    
    public MeuPrimeiroFrame(){
        initComponents();
    }
    
    private void initComponents() {
        //bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jMenu9 = new javax.swing.JMenu();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenu10 = new javax.swing.JMenu();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenu5 = new javax.swing.JMenu();
        jMenu6 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jMenu7 = new javax.swing.JMenu();
        jMenu8 = new javax.swing.JMenu();

        jMenu9.setText("Menu");

        jMenuItem11.setText("Item");
        jMenu9.add(jMenuItem11);

        jMenuItem10.setText("Item");
        jMenu9.add(jMenuItem10);

        jMenuItem9.setText("Item");
        jMenu9.add(jMenuItem9);

        jMenuItem8.setText("Item");
        jMenu9.add(jMenuItem8);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Administração");
        setFont(new java.awt.Font("Arial Narrow", 1, 12)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 0, Short.MAX_VALUE));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 0, Short.MAX_VALUE));

//        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/siscontábil/Imagenspng/ativa mes 02.PNG"))); // NOI18N

        //org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jButton1, org.jdesktop.beansbinding.ELProperty.create("${actionCommand}"), jButton1, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        //bindingGroup.addBinding(binding);

        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JOptionPane.showMessageDialog(null, "Buu");
            }
        });

//        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/siscontábil/Imagenspng/eventos.PNG"))); // NOI18N
//
//        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/siscontábil/Imagenspng/empresas.PNG"))); // NOI18N
//
//        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/siscontábil/Imagenspng/encerra mes 02.PNG"))); // NOI18N
//
//        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/siscontábil/Imagenspng/escritorio.PNG"))); // NOI18N
//
//        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/siscontábil/Imagenspng/contas a pagar.PNG"))); // NOI18N
//
//        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/siscontábil/Imagenspng/contas a receber.PNG"))); // NOI18N

        jMenu1.setBackground(new java.awt.Color(153, 153, 153));
        //jMenu1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(204, 204, 204)));
        jMenu1.setText("Arquivo");
        jMenu1.setFont(new java.awt.Font("Arial Narrow", 1, 12));

        jMenuItem1.setText("Item");
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Item");
        jMenu1.add(jMenuItem2);

        jMenuItem3.setText("Item");
        jMenu1.add(jMenuItem3);

        jMenuItem4.setText("Item");
        jMenu1.add(jMenuItem4);

        jMenuItem5.setText("Item");
        jMenu1.add(jMenuItem5);

        jMenuItem6.setText("Item");
        jMenu1.add(jMenuItem6);

        jMenu10.setText("Menu");

        jMenuItem12.setText("Item");
        jMenu10.add(jMenuItem12);

        jMenuItem13.setText("Item");
        jMenu10.add(jMenuItem13);

        jMenuItem14.setText("Item");
        jMenu10.add(jMenuItem14);

        jMenuItem15.setText("Item");
        jMenu10.add(jMenuItem15);

        jMenu1.add(jMenu10);

        jMenuItem7.setText("Item");
        jMenu1.add(jMenuItem7);

        jMenuBar1.add(jMenu1);

        jMenu3.setBackground(new java.awt.Color(153, 153, 153));
        //jMenu3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(204, 204, 204)));
        jMenu3.setText("Relatórios");
        jMenu3.setFont(new java.awt.Font("Arial Narrow", 1, 12));
        jMenuBar1.add(jMenu3);

        jMenu5.setBackground(new java.awt.Color(153, 153, 153));
        //jMenu5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(204, 204, 204)));
        jMenu5.setText("Tabelas");
        jMenu5.setFont(new java.awt.Font("Arial Narrow", 1, 12));
        jMenuBar1.add(jMenu5);

        jMenu6.setBackground(new java.awt.Color(153, 153, 153));
        //jMenu6.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(204, 204, 204)));
        jMenu6.setText("Diversos");
        jMenu6.setFont(new java.awt.Font("Arial Narrow", 1, 14));
        jMenuBar1.add(jMenu6);

        jMenu4.setBackground(new java.awt.Color(153, 153, 153));
        //jMenu4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(204, 204, 204)));
        jMenu4.setText("Utilitários");
        jMenu4.setFont(new java.awt.Font("Arial Narrow", 1, 14));
        jMenuBar1.add(jMenu4);

        jMenu7.setBackground(new java.awt.Color(153, 153, 153));
        //jMenu7.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(204, 204, 204)));
        jMenu7.setText("Clássico");
        jMenu7.setFont(new java.awt.Font("Arial Narrow", 1, 14));
        jMenuBar1.add(jMenu7);

        jMenu8.setBackground(new java.awt.Color(153, 153, 153));
        //jMenu8.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(153, 153, 153)));
        jMenu8.setText("Tributos");
        jMenu8.setFont(new java.awt.Font("Arial Narrow", 1, 14));
        jMenuBar1.add(jMenu8);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, 0, 0, Short.MAX_VALUE)
                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                .addComponent(jButton7, 0, 0, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING, 0, 0, Short.MAX_VALUE)
                .addComponent(jButton5, javax.swing.GroupLayout.Alignment.LEADING, 0, 0, Short.MAX_VALUE)
                .addComponent(jButton6, javax.swing.GroupLayout.Alignment.LEADING, 0, 0, Short.MAX_VALUE)
                .addComponent(jButton4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(534, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(328, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addGap(5130, 5130, 5130))
                .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 57, Short.MAX_VALUE)
                .addGap(8, 8, 8)
                .addComponent(jButton7)
                .addGap(5110, 5110, 5110)));

        //bindingGroup.bind();

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width - 650) / 2, (screenSize.height - 580) / 2, 650, 580);
    }                       
}
