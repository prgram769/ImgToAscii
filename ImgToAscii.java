import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class ImgToAscii {
  public static BufferedImage resizeImage(File imgToResize, int width) {
    BufferedImage smallImg = null;

    try {
      BufferedImage originalImage = ImageIO.read(imgToResize);

      int newWidth = width;
      int newHight = (int) (originalImage.getHeight() * newWidth / (double) originalImage.getWidth() / 2);

      Image imgResized = originalImage.getScaledInstance(newWidth, newHight, Image.SCALE_SMOOTH);

      smallImg = new BufferedImage(newWidth, newHight, BufferedImage.TYPE_INT_RGB);

      Graphics2D g2d = smallImg.createGraphics();

      g2d.drawImage(imgResized, 0, 0, null);

      g2d.dispose();
    } catch (Exception e) {
      System.err.println(e);
    }

    return smallImg;
  }

  public static void convertImageToAscii(BufferedImage imgToConvert) {
    try {
      int height = imgToConvert.getHeight();
      int width = imgToConvert.getWidth();

      String symbols = "@&#%$*+=-;:,.'` ";

      for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
          int rgb = imgToConvert.getRGB(x, y);
          int r = (rgb >> 16) & 0xFF;
          int g = (rgb >> 8) & 0xFF;
          int b = rgb & 0xFF;

          double bright = 0.299 * r + 0.587 * g + 0.114 * b;

          int indexNumber = (int) (bright * (symbols.length() - 1) / 255);

          System.out.print(symbols.charAt(indexNumber));
        }
        System.out.println();
      }

      System.out.printf("\n\nMade with ❤️ by rugby01");
    } catch (Exception e) {
      System.err.println(e);
    }
  }

  public static void main(String[] args) {
    File myImage = null;

    int newWidth = 0;

    try {
      myImage = new File(args[0]);

      newWidth = Integer.parseInt(args[1]);
    } catch (Exception e) {
      System.err.printf("To use this program you need to specify in the terminal the file's path and the new width to resize it.\n");

      return;
    }

    BufferedImage resizedImg = resizeImage(myImage, newWidth);

    convertImageToAscii(resizedImg);
  }
}