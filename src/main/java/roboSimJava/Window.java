package roboSimJava;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class Window extends JFrame {

    public static HashMap<String, BufferedImage> imagesMap = new HashMap<>();

    private final MenuWindow menuWindow = new MenuWindow();

    public static JTabbedPane tabbed;
    public static ArrayList<JLabel> labelArray = new ArrayList<>();

    public Window() {
        generationFrame();
    }

    private void generationFrame() {
        setTitle("рандомайзер фруктов");
        setIconImage(imagesMap.get("logo.png"));
        setSize(1220, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        menuWindow.addMenu(this);
        setJMenuBar(menuWindow.menu);

        ImageIcon background = changeStringToImage("background.png");
        JLabel imageBackground = new JLabel(background);
        imageBackground.setBounds(50, 50, 1200, 600);
        imageBackground.setLayout(new BorderLayout());
        setContentPane(imageBackground);

        setLayout(null);

        JLabel[] zone1 = new JLabel[22];
        JLabel[] zone2 = new JLabel[22];
        JLabel[] zone3 = new JLabel[22];
        addZones(zone1, 42, 19);
        addZones(zone2, 42, 268);
        addZones(zone3, 442, 19);
        for (JLabel l : zone1) {
            add(l);
            labelArray.add(l);
        }
        for (JLabel l : zone2) {
            add(l);
            labelArray.add(l);
        }
        for (JLabel l : zone3) {
            add(l);
            labelArray.add(l);
        }

        JPanel overlayPanel = new JPanel(new BorderLayout());
        overlayPanel.setBounds(820, 0, 380, 600);
        tabbed = new TabbedPane();
        overlayPanel.add(tabbed);
        getContentPane().add(overlayPanel);

        pack();
        setVisible(true);
    }


    private static void addZones(JLabel[] zone, int x, int y) {
        int index = 0;
        for (int i = 0; i < 28; i++) {
            if (i == 0 || (i > 5 && i != 10)) {
                ImageIcon clean = changeStringToImage("clean.png");
                zone[index] = new JLabel(clean);
                zone[index].setBounds(x + ((i % 7) * 50), y + ((i / 7) * 51), 50, 50);
                zone[index].setHorizontalAlignment(JLabel.CENTER);
                index++;
            }
        }
    }

    public static ImageIcon changeStringToImage(String name) {
        Image searchImage = imagesMap.get(name);
        if (searchImage == null) {

            throw new RuntimeException();
        }
        ImageIcon image = new ImageIcon(searchImage);
        return  image;
    }
    public static void initializeImage() {
        // Список всех изображений
        String[] imagePaths = {
                "image/fruits/AppleGreen.png",
                "image/fruits/AppleRed.png",
                "image/fruits/pearGreen.png",
                "image/fruits/pearYellow.png",
                "image/fruits/smallAppleGreen.png",
                "image/fruits/smallAppleRed.png",
                "image/fruits/unripAppleGreen.png",
                "image/fruits/unripAppleRed.png",
                "image/fruits/unripPearGreen.png",
                "image/fruits/unripPearYellow.png",
                "image/fruits/unripSmallAppleGreen.png",
                "image/fruits/unripSmallAppleRed.png",
                "image/fruits/clean.png",
                "image/background.png",
                "image/logo.png"
        };

        for (String path : imagePaths) {
            try (InputStream stream = Window.class.getClassLoader().getResourceAsStream(path)) {
                if (stream == null) {
                    continue;
                }

                BufferedImage img = ImageIO.read(stream);
                if (img == null) {
                    continue;
                }

                String key = path.substring(path.lastIndexOf('/') + 1);
                imagesMap.put(key, img);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // Проверка загрузки критически важных изображений
        if (!imagesMap.containsKey("background.png")) {
            JOptionPane.showMessageDialog(null,
                    "Фоновое изображение не найдено!\nПроверьте наличие файла background.png в ресурсах",
                    "Ошибка загрузки", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
}
