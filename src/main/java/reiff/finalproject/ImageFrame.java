package reiff.finalproject;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ImageFrame extends JFrame {
    public ImageFrame(String imageUrl) {
        setTitle("Art Image");
        setSize(800, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        try {
            ImageIcon imageIcon = new ImageIcon(new URL(imageUrl));
            Image image = imageIcon.getImage();
            int width = image.getWidth(null);
            int height = image.getHeight(null);

            if (width > 800) {
                int newHeight = (int) ((800.0 / width) * height);
                image = image.getScaledInstance(800, newHeight, Image.SCALE_SMOOTH);
                imageIcon = new ImageIcon(image);
            }

            JLabel imageLabel = new JLabel(imageIcon);

            JScrollPane scrollPane = new JScrollPane(imageLabel);
            add(scrollPane, BorderLayout.CENTER);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading image: " + e.getMessage());
        }
    }
}

