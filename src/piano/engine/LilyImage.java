package piano.engine;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class LilyImage {

    public String name;

    public Vector<BufferedImage> images = new Vector<BufferedImage>();

    public LilyImage(String name) {
        this.name = name;
        try {
            String fileLocation = "data/out/" + name + ".png";
            File file = new File(fileLocation);
            if (file.exists()) {
                images.add(ImageIO.read(file));
            } else {
                int p = 1;
                fileLocation = "data/out/" + name + "-page" + p + ".png";
                file = new File(fileLocation);
                while (file.exists()) {
                    images.add(ImageIO.read(file));
                    ++p;
                    fileLocation = "data/out/" + name + "-page" + p + ".png";
                    file = new File(fileLocation);
                }
            }
            if (images.isEmpty()) {
                System.err.println("Lilypond failed to produce any files");
            }
        } catch (IOException e) {
            System.err.println("Could not read images files for " + name);
            e.printStackTrace();
        }
    }

    public BufferedImage get(int n) {
      if (images.size() > n) {
        return images.get(n);
      }
      return null;
    }
}
