package roboSimJava;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URI;

public class MenuWindow {

    public JMenuBar menu;

    private JFrame frame;

    public void addMenu(JFrame frame){
        this.frame = frame;
        menu = new JMenuBar();
        menu.add(addFileMenu());
        menu.add(addViewMenu());
        menu.add(addSettingMenu());
        menu.add(addAboutMenu());
    }

    private JMenu addFileMenu(){
        JMenu fileMenu = new JMenu("Файл");
        fileMenu.setFont(new Font(Font.SERIF, Font.TRUETYPE_FONT, 16));

        JMenuItem createItem = new JMenuItem("Создать");
        createItem.setFont(new Font(Font.SERIF, Font.TRUETYPE_FONT, 16));
        fileMenu.add(createItem);
        fileMenu.add(new JSeparator());

        JMenuItem saveItem = new JMenuItem("Сохранить");
        saveItem.setFont(new Font(Font.SERIF, Font.PLAIN, 16));
        saveItem.addActionListener(e -> {
            takeScreenshotWithTab(frame, Window.tabbed);
        });
        fileMenu.add(saveItem);
        fileMenu.add(new JSeparator());

        JMenuItem closeItem = new JMenuItem("Закрыть");
        closeItem.setFont(new Font(Font.SERIF, Font.TRUETYPE_FONT, 16));
        fileMenu.add(closeItem);


        return fileMenu;
    }
    private static void takeScreenshotWithTab(JFrame frame, JTabbedPane tabbedPane) {

        int index = tabbedPane.getSelectedIndex();
        hideOpenMenus(frame);

        SwingUtilities.invokeLater(() -> {
            tabbedPane.setSelectedIndex(3);

            Timer timer = new Timer(100, ev -> {
                try {
                    // 4. Делаем скриншот после обновления интерфейса
                    Robot robot = new Robot();
                    Rectangle area = new Rectangle(
                            frame.getLocationOnScreen(),
                            frame.getContentPane().getSize()
                    );
                    BufferedImage screenshot = robot.createScreenCapture(area);

                    // 5. Копируем в буфер обмена
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(new TransferableImage(screenshot), null);

                    JOptionPane.showMessageDialog(
                            frame,
                            "Скриншот сохранен",
                            "Успех",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    tabbedPane.setSelectedIndex(index);

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(
                            frame,
                            "Ошибка: " + ex.getMessage(),
                            "Ошибка",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            });
            timer.setRepeats(false);
            timer.start();
        });

    }

    /** Закрывает все открытые выпадающие меню */
    private static void hideOpenMenus(JFrame frame) {
        MenuSelectionManager.defaultManager().clearSelectedPath();
    }

    /** Класс для передачи изображения в буфер обмена */
    static class TransferableImage implements Transferable {
        private final BufferedImage image;

        public TransferableImage(BufferedImage image) {
            this.image = image;
        }

        @Override
        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[]{DataFlavor.imageFlavor};
        }

        @Override
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return DataFlavor.imageFlavor.equals(flavor);
        }

        @Override
        public Object getTransferData(DataFlavor flavor) {
            if (isDataFlavorSupported(flavor)) {
                return image;
            }
            return null;
        }
    }

    private JMenu addViewMenu(){
        JMenu viewMenu = new JMenu("Вид");
        viewMenu.setFont(new Font(Font.SERIF, Font.TRUETYPE_FONT, 16));

        JMenuItem resetViewItem = new JMenuItem("Сбросить вывод");
        resetViewItem.setFont(new Font(Font.SERIF, Font.TRUETYPE_FONT, 16));
        viewMenu.add(resetViewItem);
        viewMenu.add(new JSeparator());

        JMenuItem searchItem = new JMenuItem("Поиск");
        searchItem.setFont(new Font(Font.SERIF, Font.TRUETYPE_FONT, 16));
        viewMenu.add(searchItem);
        viewMenu.add(new JSeparator());

        JMenuItem sizeItem = new JMenuItem("Сменить размер");
        sizeItem.setFont(new Font(Font.SERIF, Font.TRUETYPE_FONT, 16));
        viewMenu.add(sizeItem);

        return viewMenu;
    }

    private JMenu addSettingMenu() {
        JMenu settingsMenu = new JMenu("Настройки");
        settingsMenu.setFont(new Font(Font.SERIF, Font.TRUETYPE_FONT, 16));

        JMenuItem colorItem = new JMenuItem("Цвет окна");
        colorItem.setFont(new Font(Font.SERIF, Font.TRUETYPE_FONT, 16));
        settingsMenu.add(colorItem);
        settingsMenu.add(new JSeparator());

        settingsMenu.add(addLanguageMenu());
        settingsMenu.add(new JSeparator());

        JMenuItem changeImageItem = new JMenuItem("Изображения");
        changeImageItem.setFont(new Font(Font.SERIF, Font.TRUETYPE_FONT, 16));
        settingsMenu.add(changeImageItem);

        return settingsMenu;
    }

    private JMenu addAboutMenu(){
        JMenu aboutMenu = new JMenu("Информация");
        aboutMenu.setFont(new Font(Font.SERIF, Font.TRUETYPE_FONT, 16));

        JMenuItem aboutItem = new JMenuItem("О приложении");
        aboutItem.setFont(new Font(Font.SERIF, Font.TRUETYPE_FONT, 16));
        aboutMenu.add(aboutItem);
        aboutMenu.add(new JSeparator());

        aboutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "тут пусто");
            }
        });

        JMenuItem infoItem = new JMenuItem("Сайт");
        infoItem.setFont(new Font(Font.SERIF, Font.TRUETYPE_FONT, 16));
        aboutMenu.add(infoItem);

        infoItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openWebsite("https://mck-ktits.ru/itcube/");
            }
        });

        return aboutMenu;
    }

    private static void openWebsite(String url) {
        try {
            Desktop desktop = Desktop.getDesktop();
            desktop.browse(new URI(url));
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Не удалось открыть сайт: " + ex.getMessage(),
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private JMenu addLanguageMenu() {
        JMenu languageItem = new JMenu("Язык");
        languageItem.setFont(new Font(Font.SERIF, Font.TRUETYPE_FONT, 16));

        JMenuItem ruItem = new JMenuItem("Русский");
        ruItem.setFont(new Font(Font.SERIF, Font.TRUETYPE_FONT, 16));
        languageItem.add(ruItem);
        languageItem.add(new JSeparator());

        JMenuItem enItem = new JMenuItem("English");
        enItem.setFont(new Font(Font.SERIF, Font.TRUETYPE_FONT, 16));
        languageItem.add(enItem);
        languageItem.add(new JSeparator());

        JMenuItem frItem = new JMenuItem("French");
        frItem.setFont(new Font(Font.SERIF, Font.TRUETYPE_FONT, 16));
        languageItem.add(frItem);

        return languageItem;
    }
}
