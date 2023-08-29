package application;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.UUID;

public class FileManager {
    public static void serializeObjToFile(Object obj, File file) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(obj);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if(fos != null) fos.close();
                if(oos != null) oos.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static Object deserializeObjectFromFile(File file) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if(fis != null) fis.close();
                if(ois != null) ois.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void saveImageFromPanel(JPanel panel, String extension) {
        int width = panel.getWidth();
        int height = panel.getHeight();

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();

        panel.paint(g2d);
        g2d.dispose();

        File file = getCustomFile("img", "cad-swing-view", extension);
        try {
            ImageIO.write(bufferedImage, "png", file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static File getCustomFile(String path, String name, String extension) {
        if(path != null) return new File(path + "/" + name + "_" + UUID.randomUUID().toString().substring(0, 3) + "." + extension);
        else return new File(name + "_" + UUID.randomUUID().toString().substring(0, 3) + "." + extension);
    }
}
