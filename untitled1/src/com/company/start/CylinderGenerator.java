package com.company.start;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CylinderGenerator extends JFrame {
    private JTextField radiusTextField;
    private JTextField heightTextField;
    private JButton generateButton;
    private JButton colorButton;
    private JPanel drawingPanel;
    private Color shapeColor;

    public CylinderGenerator() {
        setTitle("Cylinder 3D Shape Generator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create a container panel for input and top panels
        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));

        // Create a top panel for the back button and topic
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);

        // Create the back button
        ImageIcon backIcon = new ImageIcon("E:\\untitled1\\media\\back.png");
        JButton backButton = new JButton(backIcon);
        backButton.setOpaque(true);
        backButton.setBorderPainted(false);
        backButton.setBackground(Color.WHITE);
        backButton.addActionListener(new CylinderGenerator.BackButtonActionListener());
        topPanel.add(backButton, BorderLayout.WEST);

        // Add the shape categories panel on top
        JPanel topicPanel = new JPanel();
        topicPanel.setBackground(Color.WHITE);
        topicPanel.setPreferredSize(new Dimension(getWidth(), 150));

        JLabel topicLabel = new JLabel("Let's Create a Cylinder");
        topicLabel.setFont(new Font("Segoe UI Black", Font.BOLD, 80));
        topicLabel.setOpaque(true);
        topicLabel.setBackground(Color.YELLOW); // Background color for the topic label
        topicLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topicPanel.add(topicLabel);
        topPanel.add(topicPanel, BorderLayout.CENTER);

        containerPanel.add(topPanel);

        // Create input panel
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel radiusLabel = new JLabel("Radius (mm):");
        radiusLabel.setFont(new Font("Segoe UI Black", Font.BOLD, 50));
        radiusTextField = new JTextField(10);
        radiusTextField.setPreferredSize(new Dimension(100, 50));
        radiusTextField.setFont(radiusTextField.getFont().deriveFont(32f).deriveFont(Font.BOLD)); // Increase font size
        JLabel heightLabel = new JLabel("Height (mm):");
        heightLabel.setFont(new Font("Segoe UI Black", Font.BOLD, 50));
        heightTextField = new JTextField(10);
        heightTextField.setPreferredSize(new Dimension(100, 50));
        heightTextField.setFont(heightTextField.getFont().deriveFont(32f).deriveFont(Font.BOLD)); // Increase font size
        ImageIcon generateIcon = new ImageIcon("E:\\untitled1\\media\\generate.png");
        generateButton = new JButton();
        generateButton.setIcon(generateIcon);
        generateButton.setText("Generate");
        generateButton.setBackground(Color.WHITE);
        ImageIcon changeCol = new ImageIcon("E:\\untitled1\\media\\changeCol.png");
        colorButton = new JButton();
        colorButton.setIcon(changeCol);
        colorButton.setText("Change Color");
        colorButton.setBackground(Color.WHITE);
        inputPanel.add(radiusLabel);
        inputPanel.add(radiusTextField);
        inputPanel.add(heightLabel);
        inputPanel.add(heightTextField);
        inputPanel.add(colorButton);
        inputPanel.add(generateButton);
        containerPanel.add(inputPanel);

        add(containerPanel, BorderLayout.NORTH);

        // Create drawing panel
        drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Get the radius and height values from the text fields
                String radiusText = radiusTextField.getText();
                String heightText = heightTextField.getText();

                if (!radiusText.isEmpty() && !heightText.isEmpty()) {
                    try {
                        double radius = Double.parseDouble(radiusText);
                        double height = Double.parseDouble(heightText);

                        // Convert the radius and height from millimeters to pixels based on the screen resolution
                        double pixelsPerMillimeter = Toolkit.getDefaultToolkit().getScreenResolution() / 25.4;
                        int diameter = (int) (2 * radius * pixelsPerMillimeter);
                        int cylinderHeight = (int) (height * pixelsPerMillimeter);

                        // Calculate the center coordinates of the base circle
                        int centerX = getWidth() / 2;
                        int centerY = getHeight() / 2;

                        // Calculate the top-left corner coordinates of the base circle
                        int x = centerX - (diameter / 2);
                        int y = centerY - (diameter / 2);

                        // Set the fill color
                        g.setColor(shapeColor);

                        // Draw the base circle
                        g.fillOval(x, y, diameter, diameter);

                        // Draw the cylinder body
                        g.fillRect(x, y + (diameter / 2), diameter, cylinderHeight);

                        // Draw the top circle
                        g.fillOval(x, y + (diameter / 2) + cylinderHeight, diameter, diameter);
                    } catch (NumberFormatException ex) {
                        // Handle the case where the radius or height value is not a valid number
                        System.out.println("Invalid radius or height value");
                    }
                }
            }
        };
        add(drawingPanel, BorderLayout.CENTER);

        // Attach action listener to the generate button
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingPanel.repaint();
            }
        });

        // Attach action listener to the color button
        colorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color selectedColor = JColorChooser.showDialog(CylinderGenerator.this, "Choose Color", shapeColor);
                if (selectedColor != null) {
                    shapeColor = selectedColor;
                }
            }
        });

        shapeColor = Color.RED; // Initial color

        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Set to fullscreen
        setVisible(true);
    }

    public class BackButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Circle circle = new Circle();
            circle.setVisible(true);
            dispose();
        }
    }
}
