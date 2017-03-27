public class Interface extends javax.swing.JFrame {
    private static Cruncher cruncher;

    /**
     * Creates new form Interface
     */
    public Interface() {
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

        citySelector = new javax.swing.JComboBox<>();
        cityLabel = new javax.swing.JLabel();
        yearLabel = new javax.swing.JLabel();
        monthLabel = new javax.swing.JLabel();
        yearSelector = new javax.swing.JComboBox<>();
        monthSelector = new javax.swing.JComboBox<>();
        valueButton = new javax.swing.JButton();
        similarityButton = new javax.swing.JButton();
        clusterButton = new javax.swing.JButton();
        title = new javax.swing.JLabel();
        responseField = new javax.swing.JLabel();
        clusterField = new javax.swing.JTextArea(8, 20);
        clusterField.setEditable(false);
        clusterPane = new javax.swing.JScrollPane(clusterField);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        citySelector.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        citySelector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                citySelectorActionPerformed(evt);
            }
        });

        cityLabel.setText("City:");

        yearLabel.setText("Year:");

        monthLabel.setText("Month:");

        yearSelector.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015" }));
        yearSelector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yearSelectorActionPerformed(evt);
            }
        });

        monthSelector.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
        monthSelector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                monthSelectorActionPerformed(evt);
            }
        });

        valueButton.setText("Value");
        valueButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valueButtonActionPerformed(evt);
            }
        });

        similarityButton.setText("Similarity");
        similarityButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                similarityButtonActionPerformed(evt);
            }
        });

        clusterButton.setText("Cluster");
        clusterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clusterButtonActionPerformed(evt);
            }
        });

        title.setText("Monthly Average Temperatures of Cities");

        responseField.setText("Response will display here");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(title)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(valueButton)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(cityLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(citySelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                        .addComponent(yearLabel)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(yearSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(monthLabel))
                                    .addComponent(similarityButton))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(monthSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(clusterButton)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(186, 186, 186)
                        .addComponent(responseField)))
                .addContainerGap(64, Short.MAX_VALUE))

                    .addComponent(clusterPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(title)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(citySelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cityLabel)
                    .addComponent(yearSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(yearLabel)
                    .addComponent(monthSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(monthLabel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(valueButton)
                        .addComponent(clusterButton)
                    .addComponent(similarityButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(responseField)
                    .addComponent(clusterPane)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        citySelector.setModel(new javax.swing.DefaultComboBoxModel<>(cruncher.getCities()));
        yearSelector.setModel(new javax.swing.DefaultComboBoxModel<>(cruncher.getYears()));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void citySelectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_citySelectorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_citySelectorActionPerformed

    private void yearSelectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yearSelectorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_yearSelectorActionPerformed

    private void monthSelectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monthSelectorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_monthSelectorActionPerformed

    private void valueButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_valueButtonActionPerformed
        // TODO add your handling code here:
        String city = (String)citySelector.getSelectedItem();
        String year = (String)yearSelector.getSelectedItem();
        String month = (String)monthSelector.getSelectedItem();
        String key = city + "." + year + "." + month;

        responseField.setText(String.format("%.2f", cruncher.getValue(key)));
    }//GEN-LAST:event_valueButtonActionPerformed

    private void similarityButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_similarityButtonActionPerformed
        // TODO add your handling code here:
        String city = (String)citySelector.getSelectedItem();
        responseField.setText(cruncher.getMostSimilar(city));
    }//GEN-LAST:event_similarityButtonActionPerformed

    private void clusterButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String city = (String)citySelector.getSelectedItem();
        String year = (String)yearSelector.getSelectedItem();
        String month = (String)monthSelector.getSelectedItem();
        String key = city + "." + year + "." + month;
        String[] keys = cruncher.getKeysInCategory(key);
        String clusterKeys = "";
        for(String k : keys)
            clusterKeys += k + "\n";
        clusterField.setText(clusterKeys);
        String cluster = cruncher.getCategory(key);

        responseField.setText("Cluster: " + cluster + " -- " + keys.length + " keys in this cluster.");
    }

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
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        String treeName = "Temperatures";
        Loader.update(treeName);
        cruncher = new Cruncher(treeName);

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Interface().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel cityLabel;
    private javax.swing.JComboBox<String> citySelector;
    private javax.swing.JLabel monthLabel;
    private javax.swing.JComboBox<String> monthSelector;
    private javax.swing.JLabel responseField;
    private javax.swing.JButton similarityButton;
    private javax.swing.JLabel title;
    private javax.swing.JButton valueButton;
    private javax.swing.JButton clusterButton;
    private javax.swing.JLabel yearLabel;
    private javax.swing.JComboBox<String> yearSelector;
    private javax.swing.JScrollPane clusterPane;
    private javax.swing.JTextArea clusterField;
    // End of variables declaration//GEN-END:variables
}