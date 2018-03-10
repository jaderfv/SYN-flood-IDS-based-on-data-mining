package detectorddos;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class MainWindow extends javax.swing.JFrame {
    
   String attack,arquivoCSV = "/var/log/snort/alerts.csv";
   int parar=0;
   int indexAlgorithmRule = 0;
   ArrayList<String> data = new ArrayList<>();
   String ipMonitorado = "192.168.0.12";

   
    public MainWindow() {
        initComponents();
        tfIpMonitorado.setText(ipMonitorado);
        jComboAlgorithm.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent arg0) {
                indexAlgorithmRule = jComboAlgorithm.getSelectedIndex();

                if(indexAlgorithmRule == 1){
                    jcomboRules.removeAllItems();
                    String[] dados = {"Rule 1 - J48","Rule 2 - J48", "Rule 3 - J48"};
                    for(int i = 0; i < dados.length; i++) {
                        jcomboRules.addItem(dados[i]);
                    }
                }else if(indexAlgorithmRule == 2){
                    jcomboRules.removeAllItems();

                    String[] dados = {"Rule 1 - Random Tree","Rule 2 - Random Tree", "Rule 3 - Random Tree"};
                    for(int i = 0; i < dados.length; i++) {
                        jcomboRules.addItem(dados[i]);
                    }
                }       
            }    
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        groupAttack = new javax.swing.ButtonGroup();
        groupModeOperation = new javax.swing.ButtonGroup();
        jMenu1 = new javax.swing.JMenu();
        btExec = new javax.swing.JButton();
        btParar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jcomboRules = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jComboAlgorithm = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        tfIpMonitorado = new javax.swing.JTextField();

        jMenu1.setText("jMenu1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btExec.setText("Execute");
        btExec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btExecActionPerformed(evt);
            }
        });
        getContentPane().add(btExec, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 300, 100, 40));

        btParar.setText("Stop");
        btParar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btPararActionPerformed(evt);
            }
        });
        getContentPane().add(btParar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 80, 40));

        jLabel6.setText(" Monitored ip");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 120, 30));

        jLabel7.setText("Rule");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, -1, 30));

        jcomboRules.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select a Rule" }));
        getContentPane().add(jcomboRules, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 180, -1));

        jLabel8.setText("Algorithm ");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, 30));

        jComboAlgorithm.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select a Algorithm", "J48", "Random Tree" }));
        getContentPane().add(jComboAlgorithm, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 180, -1));

        jLabel9.setText("Attack:");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, 30));

        jLabel10.setText("SynFlood (Neptune)");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, -1, 30));
        getContentPane().add(tfIpMonitorado, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 180, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btExecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btExecActionPerformed

        ipMonitorado = tfIpMonitorado.getText();
        CsvController csv = new CsvController();
        csv.eraseLog();
        csv.alteraArquivoConf(ipMonitorado, jComboAlgorithm.getSelectedIndex());
        csv.eraseCsv();
       
        new Thread() {			
            @Override

            public void run() {
                
                parar=0;
                while(parar != 1){
                    ArrayList<String> packetLine = new ArrayList<>();
                    CsvController csv = new CsvController();
                    ArrayList<Packet> packets = new ArrayList<>();
                    Ids ids = new Ids();
                    String csvDivisor = ",";
                    packetLine = csv.readCsv();

                    for(int x=0;x<packetLine.size();x++){
                        String[] fields = packetLine.get(x).split(csvDivisor);
                        Packet packet = new Packet();
                        packet.setPort(fields[0]);
                        packet.setMsg(fields[1]);
                        packet.setIpSrc(fields[2]);
                        packet.setIpDst(fields[3]);
                        packet.setProtocol(fields[4]);
                        
                        packets.add(packet);
                    }
                    ids.openPackets(jComboAlgorithm.getSelectedIndex(), jcomboRules.getSelectedIndex(), packets, ipMonitorado);          
                }
            }
	}.start();    
    
    }//GEN-LAST:event_btExecActionPerformed

    private void btPararActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btPararActionPerformed
        parar = 1;
    }//GEN-LAST:event_btPararActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btExec;
    private javax.swing.JButton btParar;
    private javax.swing.ButtonGroup groupAttack;
    private javax.swing.ButtonGroup groupModeOperation;
    private javax.swing.JComboBox<String> jComboAlgorithm;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JComboBox<String> jcomboRules;
    private javax.swing.JTextField tfIpMonitorado;
    // End of variables declaration//GEN-END:variables
}
