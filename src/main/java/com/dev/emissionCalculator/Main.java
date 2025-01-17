package com.dev.emissionCalculator;

import com.dev.emissionCalculator.model.VehicleType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame{

    private JPanel MainPanel;
    private JLabel jlStartCity;
    private JTextField jtStartCity;
    private JLabel jlEndCity;
    private JTextField jtEndCity;
    private JComboBox cbVehicle;
    private JLabel jlVehicle;
    private JButton btnCalculate;
    private JButton btnEnter;
    private JTable table1;

    public Main(){
        setContentPane(MainPanel);
        setTitle("CO2 Emission Calculator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400,300);
        setLocationRelativeTo(null);
        setVisible(true);

        for (VehicleType vehicleType:VehicleType.values()){
            cbVehicle.addItem(vehicleType.name());
        }

        btnCalculate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String start = jtStartCity.getText();
                String end = jtEndCity.getText();
                String vehicle = (String) cbVehicle.getSelectedItem();



                JOptionPane.showMessageDialog(Main.this,"Hello WOrld "+ start);
            }
        });
    }

    public static void main(String[] args) {
        new Main();


    }


}
