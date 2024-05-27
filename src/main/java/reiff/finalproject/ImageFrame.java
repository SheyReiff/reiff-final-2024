package reiff.finalproject;

import javax.imageio.ImageIO;
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
            URL url = new URL(imageUrl);
            Image image = ImageIO.read(url);

            Image scaledImage = image.getScaledInstance(800, -1, Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(scaledImage);

            JLabel label = new JLabel();
            label.setIcon(imageIcon);

            JScrollPane scrollPane = new JScrollPane(label);
            add(scrollPane, BorderLayout.CENTER);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading image: " + e.getMessage());
        }
    }
}

