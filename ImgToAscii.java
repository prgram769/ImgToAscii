import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
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

  public static void convertImageToAscii(BufferedImage imgToConvert, String imgFile) {
    try {
      int height = imgToConvert.getHeight();
      int width = imgToConvert.getWidth();
      
      String symbols = "@&#%$*+=-;:,. ";

      if (imgFile.equals("yes")) {
        int charWidth = 7;
        int charHeight = 14;
        int imgWidth = charWidth * width;
        int imgHeight = charHeight * height;

        BufferedImage asciiImg = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);

        Font font = new Font("Courier New", Font.PLAIN, 12);

        Graphics2D g2d = asciiImg.createGraphics();

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, asciiImg.getWidth(), asciiImg.getHeight());
        g2d.setColor(Color.BLACK);
        g2d.setFont(font);

        for (int y = 0; y < height; y++) {
          for (int x = 0; x < width; x++) {
            int rgb = imgToConvert.getRGB(x, y);
            int r = (rgb >> 16) & 0xFF;
            int g = (rgb >> 8) & 0xFF;
            int b = rgb & 0xFF;

            double bright = 0.299 * r + 0.587 * g + 0.114 * b;

            int indexNumber = (int) (bright * (symbols.length() - 1) / 255);

            g2d.drawString(String.valueOf(symbols.charAt(indexNumber)), x * charWidth, (y + 1) * charHeight);
          }
        }

        g2d.dispose();

        ImageIO.write(asciiImg, "png", new File("asciiArt.png"));

        System.out.println("Image saved as asciiArt.png");
        System.out.printf("\n\nMade with ❤️ by rugby01");

        return;
      }

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

    String imgFile = "";
    String errorMessage = "To use this program you need to specify in the terminal the file's path and the new width to resize it. If you want to convert the output in one new img you need to specify in the 3rd field of the terminal yes, if not no\n";

    try {
      myImage = new File(args[0]);

      newWidth = Integer.parseInt(args[1]);

      imgFile = args[2].toLowerCase();

      if (!imgFile.equals("yes") && !imgFile.equals("no")) {
        throw new Exception(errorMessage);
      }
    } catch (Exception e) {
      System.err.printf(errorMessage);

      return;
    }

    BufferedImage resizedImg = resizeImage(myImage, newWidth);

    convertImageToAscii(resizedImg, imgFile);
  }
}