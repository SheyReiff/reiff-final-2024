package reiff.finalproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

import com.andrewoid.ApiKey;
import io.reactivex.rxjava3.core.Single;

public class RijksSearchFrame extends JFrame {
    private final JTextField searchField;
    private final JPanel resultsPanel;
    private int currentPage = 1;
    private final RijksMuseumService service;

    public RijksSearchFrame() {
        setTitle("Rijksmuseum Art Search");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout());
        searchField = new JTextField();
        JButton prevButton = new JButton("Previous Page");
        JButton nextButton = new JButton("Next Page");
        searchPanel.add(prevButton, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(nextButton, BorderLayout.EAST);

        topPanel.add(searchPanel, BorderLayout.NORTH);

        add(topPanel, BorderLayout.NORTH);

        resultsPanel = new JPanel();
        resultsPanel.setLayout(new GridLayout(2, 5));
        add(resultsPanel, BorderLayout.CENTER);

        service = new RijksMuseumServiceFactory().getService();

        prevButton.addActionListener(e -> {
            if (currentPage > 1) {
                currentPage--;
                fetchAndDisplayResults();
            }
        });

        nextButton.addActionListener(e -> {
            currentPage++;
            fetchAndDisplayResults();
        });


        fetchAndDisplayResults();
    }

    private void fetchAndDisplayResults() {

        resultsPanel.removeAll();
        resultsPanel.revalidate();
        resultsPanel.repaint();

        String query = searchField.getText();

        Single<ArtObjects> call;
        ApiKey key = new ApiKey();
        if (query.isEmpty()) {
            call = service.artObjectsPageNumber(key.get(), currentPage);
        } else {
            call = service.artObjectsSearch(key.get(), query, currentPage);
        }

        call.subscribe(artObjects -> {
            for (ArtObject artObject : artObjects.artObjects) {
                try {
                    URL imageUrl = new URL(artObject.webImage.url);
                    Image originalImage = ImageIO.read(imageUrl);


                    int Width = 200;
                    Image scaledImage = originalImage.getScaledInstance(Width, -1, Image.SCALE_DEFAULT);
                    JLabel label = getjLabel(artObject, scaledImage);

                    resultsPanel.add(label);

                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error loading image: " + ex.getMessage());
                }
            }

            resultsPanel.revalidate();
            resultsPanel.repaint();
        }, throwable -> {
            JOptionPane.showMessageDialog(this, "Error fetching data: " + throwable.getMessage());
        });
    }

    private static JLabel getjLabel(ArtObject artObject, Image scaledImage) {
        ImageIcon icon = new ImageIcon(scaledImage);

        JLabel label = new JLabel(icon);
        label.setToolTipText(artObject.title + " by " + artObject.principalOrFirstMaker);

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new ImageFrame(artObject.webImage.url).setVisible(true);
            }
        });
        return label;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new RijksSearchFrame().setVisible(true);
       });
    }
}
