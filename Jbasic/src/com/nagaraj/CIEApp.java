package com.nagaraj;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


class Student{ 
    public String computeGrade(int totalMarks) {
        // Compute the grade based on total marks
        if (totalMarks >= 90 && totalMarks <= 100) {
            return "S";
        } else if (totalMarks >= 80 && totalMarks < 90) {
            return "A";
        } else if (totalMarks >= 70 && totalMarks < 80) {
            return "B";
        } else if (totalMarks >= 60 && totalMarks < 70) {
            return "C";
        } else if (totalMarks >= 50 && totalMarks < 60) {
            return "D";
        } else if (totalMarks >= 40 && totalMarks < 50) {
            return "E";
        } else {
            return "F";
        }
    }
}

// GUI class extending JFrame
class GUI extends JFrame {
    private JTextField ia1Field, ia2Field, ia3Field, ctaField, seeField;
    private JLabel totalMarksLabel, gradeLabel,detained,totalMarksLabel2,gradeLabel1;
    private JButton calculateButton, resetButton;

    public GUI() {
        setTitle("Students' Grading System");
        setSize(500, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a panel with custom background color
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                // Create a gradient paint for background
                GradientPaint gradientPaint = new GradientPaint(
                        0, 0, new Color(253, 187, 45), getWidth(), getHeight(), new Color(255, 101, 101));

                Graphics2D g2d = (Graphics2D) g;
                g2d.setPaint(gradientPaint);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(new GridBagLayout());
        
        GridBagConstraints constraints = new GridBagConstraints();
        
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.insets = new Insets(10,0,10, 0);

        // College LOGO 
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("new-logo.png"));
        Image logoImage = logoIcon.getImage().getScaledInstance(300, 100, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(logoImage));
        panel.add(logoLabel, constraints);

        constraints.gridwidth = 0;
        constraints.gridy++;
        constraints.anchor = GridBagConstraints.CENTER;
        JLabel titleLabel = new JLabel("GRADE CALCULATOR");
        Font titleFont = new Font("Verdana", Font.BOLD, 20);
        titleLabel.setFont(titleFont);
        panel.add(titleLabel, constraints);

        // Input fields for marks
        constraints.gridy++;
        constraints.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel("IA-1 Marks:"), constraints);

        constraints.gridx++;
        ia1Field = new JTextField(10);
//        ia1Field.setPreferredSize(new Dimension(150, 20));
        panel.add(ia1Field, constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        panel.add(new JLabel("IA-2 Marks:"), constraints);

        constraints.gridx++;
        ia2Field = new JTextField(10);
        panel.add(ia2Field, constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        panel.add(new JLabel("IA-3 Marks:"), constraints);

        constraints.gridx++;
        ia3Field = new JTextField(10);
        panel.add(ia3Field, constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        panel.add(new JLabel("CTA Marks:"), constraints);

        constraints.gridx++;
        ctaField = new JTextField(10);
        panel.add(ctaField, constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        panel.add(new JLabel("SEE Marks:"), constraints);

        constraints.gridx++;
        seeField = new JTextField(10);
        panel.add(seeField, constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        constraints.gridwidth = 2;

        // Calculate Grade button
        calculateButton = new JButton("Calculate Grade");
        calculateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calculateGrade();
            }
        });
        panel.add(calculateButton, constraints);

        constraints.gridy++;

        // Reset button
        resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetFields();
            }
        });
        panel.add(resetButton, constraints);

        // Label to display total marks

        constraints.gridy++;
         
        totalMarksLabel2 = new JLabel("Total Marks: "); 
        panel.add(totalMarksLabel2, constraints); 
        constraints.gridx++;
        totalMarksLabel = new JLabel(); 
        panel.add(totalMarksLabel, constraints); 
        // Label to display grade
        constraints.gridy++; 
        constraints.gridx =0;
        gradeLabel1 = new JLabel("Grade: "); 
        panel.add(gradeLabel1, constraints); 
        
        constraints.gridx++;        
        gradeLabel = new JLabel(); 
        panel.add(gradeLabel, constraints); 
       
        
        constraints.gridy++; 
        constraints.gridx =0;
        detained = new JLabel(); 
        panel.add(detained, constraints); 
        add(panel);
        
        
        
        
        
        
        
    }

    // Calculate the grade based on entered marks
    private void calculateGrade() {
        try {
            int ia1 = Integer.parseInt(ia1Field.getText());
            int ia2 = Integer.parseInt(ia2Field.getText());
            int ia3 = Integer.parseInt(ia3Field.getText());
            int cta = Integer.parseInt(ctaField.getText());
            int see = Integer.parseInt(seeField.getText());
            detained.setText(null);
            // Validate the entered marks
            if (ia1 < 0 || ia1 > 20 || ia2 < 0 || ia2 > 20 || ia3 < 0 || ia3 > 20 || cta < 0 || cta > 10 || see < 0 || see > 100) {
                throw new IllegalArgumentException("Invalid marks entered!");
            }

            // Calculate CIE marks
            int cie = Math.max(Math.max(ia1 + ia2, ia1 + ia3), ia2 + ia3) + cta;

            // Check if student is detained from SEE
            if (cie < 20) {
                detained.setText("Student is detained from taking SEE");
                detained.setFont(detained.getFont().deriveFont(18f)); // Increase font size
                detained.setFont(detained.getFont().deriveFont(Font.BOLD)); // Set font style to bold
                detained.setForeground(Color.RED);
                
                totalMarksLabel.setText("");
                gradeLabel.setText("");
            } else {
                // Adjust SEE marks if it falls in the range 38-39
                if (see == 38 || see == 39) {
                    see = 40;
                }

                // Calculate total marks
                int totalMarks = cie + (see / 2);
                totalMarksLabel.setText(String.valueOf(totalMarks));

                // Compute the grade using Student class
                Student student = new Student();
                String grade = student.computeGrade(totalMarks);
                gradeLabel.setText(grade);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(GUI.this, "Invalid marks entered!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(GUI.this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Reset all input fields and labels
    private void resetFields() {
        ia1Field.setText("");
        ia2Field.setText("");
        ia3Field.setText("");
        ctaField.setText("");
        seeField.setText("");
        totalMarksLabel.setText("Total Marks: ");
        gradeLabel.setText("Grade: ");
        detained.setText(null);
    }
}

public class CIEApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                GUI gui = new GUI();
                gui.setVisible(true);
            }
        });
    }
}
