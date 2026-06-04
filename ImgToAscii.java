import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class ImgToAscii {

  public static void convertImageToAscii(File imgToConvert) {
    try {
      BufferedImage originalImage = ImageIO.read(imgToConvert);

      int test = originalImage.getHeight();
      int test2 = originalImage.getWidth();
      
      System.out.println(originalImage);
      System.out.println(test);
      System.out.println(test2);
    } catch (Exception e) {
      System.err.println(e);
    }
  }

  public static void main(String[] args) {
    File myImage = new File(args[0]);

    convertImageToAscii(myImage);
  }
}