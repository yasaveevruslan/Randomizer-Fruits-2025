package roboSimJava;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class RandomFruit {

    private static final int[] tree = {4, 5, 11, 26, 27, 33, 48, 49, 55};

    private static final String[] greenFruits = {
            "pearGreen.png",
            "AppleGreen.png",
            "smallAppleGreen.png"};

    private static final String[] greenFruitsUnrip = {
            "unripPearGreen.png",
            "unripAppleGreen.png",
            "unripSmallAppleGreen.png"};

    private ArrayList<String> greenFruitsArray = new ArrayList<>();
    private ArrayList<String> fruitsArray = new ArrayList<>();

    public void generateG1(String name, String zone) throws IOException {
        cleanLabels();
        int image = searchElement(TabbedPane.name, name);
        int[] positionFruits = random(5, Window.labelArray.size(), zone, new int[0], true);
        if (image < 4 || image > 6) {
            for (int i = 0; i < 15; i++) {
                greenFruitsArray.add(greenFruits[i / 5]);
            }
        } else {
            for (int i = 0; i < 15; i++) {
                if (i / 5 != image % 4){
                    greenFruitsArray.add(greenFruits[i / 5]);
                }
            }
        }
        for (int i : positionFruits) {
            Window.labelArray.get(i).setIcon(Window.changeStringToImage(TabbedPane.images[image - 1]));
        }

        int[] positionGreen = (random(9, Window.labelArray.size(),"смежное", positionFruits, false));
        int[] green = (random(9, greenFruitsArray.size(), "смежное", positionFruits, true));
            for (int i = 0; i < positionGreen.length; i++) {
            Window.labelArray.get(positionGreen[i]).setIcon(Window.changeStringToImage(greenFruitsArray.get(green[i])));
        }
    }

    public void generateG2(ArrayList<String> name, String zone, int number) throws IOException{
        cleanLabels();
        ArrayList <String> driveFruits = new ArrayList<>();
        for (int i = 0; i < (name.size() <= 2 ? number : (name.size() * 2)); i++) {
            if (name.size() == 1) {
                fruitsArray.add(TabbedPane.images[searchElement(TabbedPane.name, name.getFirst()) - 1]);
            } else if (name.size() == 2) {
                fruitsArray.add(TabbedPane.images[searchElement(TabbedPane.name, name.get(i / 3)) - 1]);
            } else {
                fruitsArray.add(TabbedPane.images[searchElement(TabbedPane.name, name.get(i / 2)) - 1]);
            }
        }
        int[] positionFruits = random(number, Window.labelArray.size(), zone, new int[0], true);
        int[] arrayFruits = random(number, fruitsArray.size(), zone, new int[0], false);

        for (int i = 0; i < arrayFruits.length; i++){
            driveFruits.add(fruitsArray.get(arrayFruits[i]));
            Window.labelArray.get(positionFruits[i]).setIcon(Window.changeStringToImage(fruitsArray.get(arrayFruits[i])));
        }

        for (int i = 0; i < 15; i++) {
            greenFruitsArray.add(greenFruits[i / 5]);
        }

        for (String driveFruit : driveFruits) {
            int index = searchElement(greenFruitsUnrip, driveFruit);
            if (index != -1) {
                greenFruitsArray.remove(index * 5);
            }
        }

        int[] positionGreen = (random(9, Window.labelArray.size(),"смежное", positionFruits, false));
        int[] green = (random(9, greenFruitsArray.size(), "смежное", positionFruits, true));
        for (int i = 0; i < positionGreen.length; i++) {
            Window.labelArray.get(positionGreen[i]).setIcon(Window.changeStringToImage(greenFruitsArray.get(green[i])));
        }
    }

    public void generateD(ArrayList<String> name, boolean checkTree, int tree, boolean checkSizeZone, int sizeZone) throws IOException{
        cleanLabels();
        ArrayList <String> driveFruits = new ArrayList<>();
        for (int i = 0; i < (name.size() <= 2 ? 6 : (name.size() * 2)); i++) {
            if (name.size() == 1) {
                fruitsArray.add(TabbedPane.images[searchElement(TabbedPane.name, name.getFirst()) - 1]);
            } else if (name.size() == 2) {
                fruitsArray.add(TabbedPane.images[searchElement(TabbedPane.name, name.get(i / 3)) - 1]);
            } else {
                fruitsArray.add(TabbedPane.images[searchElement(TabbedPane.name, name.get(i / 2)) - 1]);
            }
        }

        int[] positionFruits = new int[6];
        if (checkSizeZone) {
            for (int i = 0; i < 3; i++) {
                int[] newMas = random(sizeZone, Window.labelArray.size(), "земля", new int[0], true);
                if (checkTree) {
                    int[] newTreeArray = random(tree, Window.labelArray.size(), "дерево", new int[0], true);
                    System.arraycopy(newTreeArray, 0, positionFruits, i * sizeZone, newTreeArray.length);
                    System.arraycopy(newMas, 0, positionFruits, (i * sizeZone + newTreeArray.length) - 1, newMas.length);
                } else {
                    System.arraycopy(newMas, 0, positionFruits, i * sizeZone, newMas.length);
                }
            }
        } else {
            for (int i = 0; i < 3; i++) {
                if (checkTree) {
                    int[] newMas = random(6 / (tree * 3) - 1, Window.labelArray.size(), "земля", new int[0], true);
                    int[] newTreeArray = random(tree, Window.labelArray.size(), "дерево", new int[0], true);
                    System.arraycopy(newTreeArray, 0, positionFruits, i * sizeZone, newTreeArray.length);
                    System.arraycopy(newMas, 0, positionFruits, (i * sizeZone + newTreeArray.length) - 1, newMas.length);
                } else {
                    int[] newMas = random(2, Window.labelArray.size(), "земля", new int[0], true);
                    System.arraycopy(newMas, 0, positionFruits, i * sizeZone, newMas.length);
                }
            }
        }
        int[] arrayFruits = random(6, fruitsArray.size(), "zone", new int[0], false);

        for (int i = 0; i < arrayFruits.length; i++){
            driveFruits.add(fruitsArray.get(arrayFruits[i]));
            Window.labelArray.get(positionFruits[i]).setIcon(new ImageIcon(ImageIO.read(new File(fruitsArray.get(arrayFruits[i])))));
        }

        for (int i = 0; i < 15; i++) {
            greenFruitsArray.add(greenFruits[i / 5]);
        }

        for (String driveFruit : driveFruits) {
            int index = searchElement(greenFruitsUnrip, driveFruit);
            if (index != -1) {
                greenFruitsArray.remove(index * 5);
            }
        }

        int[] positionGreen = (random(9, Window.labelArray.size(),"смежное", positionFruits, false));
        int[] green = (random(9, greenFruitsArray.size(), "смежное", positionFruits, true));
        for (int i = 0; i < positionGreen.length; i++) {
            Window.labelArray.get(positionGreen[i]).setIcon(new ImageIcon(ImageIO.read(new File(greenFruitsArray.get(green[i])))));
        }
    }

    private void cleanLabels() throws IOException {
        greenFruitsArray.clear();
        fruitsArray.clear();
        for (JLabel l : Window.labelArray) {
            l.setIcon(Window.changeStringToImage("clean.png"));
        }
    }

    private static int searchElement(String[] array, String name) {
        int result = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(name)) {
                result = i;
                break;
            }
        }
        return result;
    }

    private static boolean searchElementBool(int[] array, int name) {
        boolean result = false;
        for (int i : array) {
            if (i == name) {
                result = true;
                break;
            }
        }
        return result;
    }

    public static int[] random(int size, int max, String zone, int[] position, boolean checkPos) {
        int[] array = new int[size];
        boolean useNumber;
        for (int i = 0; i < size; ) {
            useNumber = false;
            int randomValue = (int) (Math.random() * max);

            if (!checkPos) {
                useNumber = !searchElementBool(position, randomValue);
            } else {
                if (searchElement(TabbedPane.cort, zone) == 1) {
                    useNumber = searchTree(randomValue);
                } else if (searchElement(TabbedPane.cort, zone) == 2) {
                    useNumber = !searchTree(randomValue);
                } else {
                    useNumber = true;
                }
            }




            if (!checkRandom(array, i, randomValue) && useNumber) {
                array[i] = randomValue;
                i++;
            }
        }

        return array;
    }

    private static boolean checkRandom(int[] array, int max, int number) {
        boolean check = false;
        for (int j = 0; j < max; j++) {
            if (array[j] == number) {
                check = true;
                break;
            }
        }
        return check;
    }

    private static boolean searchTree(int value) {
        boolean check = false;
        for (int i : tree) {
            if (i == value) {
                check = true;
                break;
            }
        }
        return check;
    }


}
