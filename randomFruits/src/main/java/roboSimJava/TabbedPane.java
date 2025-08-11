package roboSimJava;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class TabbedPane extends JTabbedPane {

    private RandomFruit random = new RandomFruit();

    public TabbedPane() {

        setSize(380, 600);
        addTab("Г1", addPaneFirst());
        addTab("Г2", addPaneSecond());
        addTab("Д", addPaneThird());
        addTab("легенда", addLegendPane());
    }

    public static final String[] name = {"выберите фрукт:", "гнилые груши", "гнилые яблоки",
            "гнилые малые яблоки", "гнилые неспелые груши",
            "гнилые неспелые яблоки", "гнилые неспелые малые яблоки",
            "груши", "яблоки", "малые яблоки"};

    public static final String[] cort = {"выберете откуда вывозить:",
            "дерево", "земля", "смежное"};

    public static final String[] components = {
            "AppleGreen.png",           "Зеленое яблоко",
            "AppleRed.png",             "Красное яблоко",
            "pearGreen.png",            "Зеленая груша",
            "pearYellow.png",           "Желтая груша",
            "smallAppleGreen.png",      "Зеленое малое яблоко",
            "smallAppleRed.png",        "Красное малое яблоко",

            "unripAppleGreen.png",      "Зеленое гнилое яблоко",
            "unripAppleRed.png",        "Красное гнилое яблоко",
            "unripPearGreen.png",       "Зеленая гнилая груша",
            "unripPearYellow.png",      "Желтая гнилая груша",
            "unripSmallAppleGreen.png", "Зеленое малое гнилое яблоко",
            "unripSmallAppleRed.png",   "Красное малое гнилое яблоко"};

    public static final String[] images = {
            "unripPearYellow.png",
            "unripAppleRed.png",
            "unripSmallAppleRed.png",
            "unripPearGreen.png",
            "unripAppleGreen.png",
            "unripSmallAppleGreen.png",
            "pearYellow.png",
            "AppleRed.png",
            "smallAppleRed.png"};

    public static final String[] sizeZoneFruits = {"количество фруктов в зоне:", "0", "1", "2", "3"};
    public static final String[] sizeTreeFruits = {"количество фруктов на дереве:", "0", "1", "2", "3"};

    public ArrayList<String> arrayFruitsG1 = new ArrayList<>();
    public ArrayList<String> arrayFruitsG2 = new ArrayList<>();
    public ArrayList<String> arrayFruitsD = new ArrayList<>();


    private JScrollPane addPaneFirst() {
        JPanel tab1 = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 5, 20, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;

        JComboBox box = new JComboBox(name);
        constructorJComponent(box, tab1, gbc, true, 16);

        JComboBox useBox = new JComboBox(cort);
        constructorJComponent(useBox, tab1, gbc, true, 16);

        JButton generate = new JButton("Cгенерировать");
        constructorJComponent(generate, tab1, gbc, true, 28);

        generate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean boxSelect = false;
                boolean useSelect = false;
                if (box.getSelectedIndex() != 0) {
                    arrayFruitsG1.add(name[box.getSelectedIndex()]);
                    boxSelect = true;
                } else {
                    JOptionPane.showMessageDialog(null, "Выберите какие фрукты генерировать");
                }
                if (useBox.getSelectedIndex() != 0){
                    useSelect = true;
                } else {
                    JOptionPane.showMessageDialog(null, "Выберите откуда вывозить");
                }

                try {
                    if (useSelect && boxSelect) {
                        random.generateG1(name[box.getSelectedIndex()], cort[useBox.getSelectedIndex()]);
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(tab1);
        scrollPane.setBounds(0, 0, 300, 600);

        return scrollPane;
    }

    private JScrollPane addPaneSecond() {
        JPanel tab1 = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;

        JCheckBox[] checkBoxes = new JCheckBox[9];
        for (int i = 1; i < 10; i++){
            checkBoxes[i-1] = new JCheckBox(name[i]);
            constructorJComponent(checkBoxes[i-1], tab1, gbc, true, 16);
        }
        for (JCheckBox check : checkBoxes){
            check.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (check.isSelected()){
                        arrayFruitsG2.add(check.getText());
                    } else {
                        arrayFruitsG2.remove(check.getText());
                    }
                }
            });
        }

        JComboBox useBox = new JComboBox(cort);
        constructorJComponent(useBox, tab1, gbc, true, 16);

        JButton generate = new JButton("Cгенерировать");
        constructorJComponent(generate, tab1, gbc, true, 28);

        generate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    if (useBox.getSelectedIndex() != 0 && !arrayFruitsG2.isEmpty()) {
                        random.generateG2(arrayFruitsG2, cort[useBox.getSelectedIndex()], 5);
                    } else if (useBox.getSelectedIndex() == 0 && arrayFruitsG2.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Выберите откуда вывозить и какие фрукты");
                    } else if (useBox.getSelectedIndex() == 0) {
                        JOptionPane.showMessageDialog(null, "Выберите откуда вывозить");
                    } else {
                        JOptionPane.showMessageDialog(null, "Выберите фрукты");
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(tab1);
        scrollPane.setBounds(0, 0, 300, 600);

        return scrollPane;
    }

    private JScrollPane addPaneThird() {
        JPanel tab1 = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;

        JCheckBox[] checkBoxes = new JCheckBox[9];
        for (int i = 1; i < 10; i++){
            checkBoxes[i-1] = new JCheckBox(name[i]);
            constructorJComponent(checkBoxes[i-1], tab1, gbc, true, 16);
        }

        for (JCheckBox check : checkBoxes){
            check.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (check.isSelected()){
                        arrayFruitsD.add(check.getText());
                    } else {
                        arrayFruitsD.remove(check.getText());
                    }
                }
            });
        }

//        JCheckBox useTreeBox = new JCheckBox("задать количество фруктов на дереве");
//        constructorJComponent(useTreeBox, tab1, gbc, true, 16);
//
//
//        JComboBox treeBox = new JComboBox(sizeTreeFruits);
//        constructorJComponent(treeBox, tab1, gbc, false, 16);
//
//        useTreeBox.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                treeBox.setVisible(useTreeBox.isSelected());
//            }
//        });
//
//        JCheckBox useFruitsBox = new JCheckBox("задать количество фруктов в зоне");
//        constructorJComponent(useFruitsBox, tab1, gbc, true, 16);

//        JComboBox maxFruitsBox = new JComboBox(sizeZoneFruits);
//        constructorJComponent(maxFruitsBox, tab1, gbc, false, 16);
//
//        useFruitsBox.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                maxFruitsBox.setVisible(useFruitsBox.isSelected());
//            }
//        });

        JButton generate = new JButton("Cгенерировать");
        constructorJComponent(generate, tab1, gbc, true, 28);

        generate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    if (!arrayFruitsD.isEmpty()){
                        random.generateG2(arrayFruitsD, cort[3], 6);
                    } else {
                        JOptionPane.showMessageDialog(null, "Выберите фрукты");
                    }

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        JScrollPane scrollPane = new JScrollPane(tab1);
        scrollPane.setBounds(0, 0, 300, 600);

        return scrollPane;
    }

    private JScrollPane addLegendPane() {
        JPanel tab = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel[] labels = new JLabel[24];
        for (int i = 0; i < labels.length; i+=2){
            gbc.gridx = 0;
            labels[i] = new JLabel(changeSize(components[i], i));
            labels[i].setFont(new Font(Font.SERIF, Font.BOLD, 16));
            tab.add(labels[i], gbc);
            gbc.gridx++;
            labels[i+1] = new JLabel(components[i+1]);
            labels[i+1].setFont(new Font(Font.SERIF, Font.BOLD, 16));
            tab.add(labels[i+1], gbc);
            gbc.gridy++;
        }

        JScrollPane scrollPane = new JScrollPane(tab);
        scrollPane.setBounds(0, 0, 300, 600);

        return scrollPane;
    }

    private static ImageIcon changeSize(String name, int element){
//        ImageIcon imageIcon = new ImageIcon(name);
        ImageIcon imageIcon = Window.changeStringToImage(name);
        Image image = imageIcon.getImage();
        int size = element == 8 || element == 10 || element == 20 || element == 22 ? 20 : 40;
        Image newImg = image.getScaledInstance(size, size,  java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newImg);

        return imageIcon;
    }

    private static void constructorJComponent(JComponent comboBox, JPanel tab1, GridBagConstraints gbc, boolean visible, int sizeText){
        comboBox.setFont(new Font(Font.SERIF, Font.BOLD, sizeText));
        comboBox.setPreferredSize(new Dimension(330, sizeText == 16 ? 30 : 50));
        tab1.add(comboBox, gbc);
        gbc.gridy++;
        comboBox.setVisible(visible);
    }

}