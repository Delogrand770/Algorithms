// ====================================================================
// CS380 - Pex1 - Seam carving to reduce image size
// By: Dr. Wayne Brown, 9 Jan 2012
// Task: Read an image from a file and remove a seam of least energy
//       along a vertical path on each mouse click on the original image.
// ====================================================================

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.imageio.ImageIO;

//=====================================================================
// GUI windows and events for the seam carving operations.
public class SeamCarverMain extends MouseInputAdapter {

  private BufferedImage originalImage;
  private BufferedImage grayscaleImage;
  private BufferedImage gradientImage;
  private BufferedImage seamImage;
  private BufferedImage seamRemovedImage;
  private ImageWindow OriginalImageWindow;
  private ImageWindow grayscaleWindow = null;
  private ImageWindow gradientWindow = null;
  private ImageWindow seamImageWindow = null;
  private ImageWindow SeamRemovedWindow = null;

  //-------------------------------------------------------------------
  public static void main(String[] args) {
    new SeamCarverMain();
  }

  //-------------------------------------------------------------------
  public SeamCarverMain() {
    // Load the image to be "seam carved"
    originalImage = loadImage("cloud.jpg");

    // Display the image and wait for a mouse click on the image
    OriginalImageWindow = new ImageWindow("Original image - mouse click to seamcarve",
                                          originalImage, 0, 0, this);
  }

  //-------------------------------------------------------------------
  @Override
  public void mousePressed(MouseEvent e) {
    // If this is repeat operation, change the originalImage to the seamRemovedImage
    if (seamRemovedImage != null) {
      originalImage = seamRemovedImage;
    }

    // Do the seam carving work
    grayscaleImage = SeamCarving.toGrayscale(originalImage);
    gradientImage = SeamCarving.gradientImage(grayscaleImage);
    int[] seam = SeamCarving.findMinimumSeam(gradientImage);
    seamRemovedImage = SeamCarving.removeSeam(originalImage, seam);
    
    // Create an image that shows the seam that was found. This is 
    // purely to visualize how the algorithm works. 
    seamImage = SeamCarving.showSeamOnImage(originalImage, seam);

    // Update the screen
    if (grayscaleWindow == null) // this is the first seam removal, so create the windows
    {
      int y = originalImage.getHeight();
      int x = 0;
      int dx = originalImage.getWidth();
      grayscaleWindow = new ImageWindow("Grayscale", grayscaleImage, x, y, this);
      gradientWindow = new ImageWindow("Gradient", gradientImage, x + dx, y, this);
      seamImageWindow = new ImageWindow("Seam", seamImage, x + 2 * dx, y, this);
      SeamRemovedWindow = new ImageWindow("Seam removed", seamRemovedImage, x + 3 * dx, y, this);
    } else // this is a repeat seam removeal, so just update the existing windows.
    {
      grayscaleWindow.setImage(grayscaleImage);
      gradientWindow.setImage(gradientImage);
      seamImageWindow.setImage(seamImage);
      SeamRemovedWindow.setImage(seamRemovedImage);
    }
  }

  //-------------------------------------------------------------------
  public static BufferedImage loadImage(String filename) {
    try {
      // read the image file
      BufferedImage image = ImageIO.read(new java.io.File(filename));
      return image;
    } catch (java.io.IOException e) {
      System.err.println("Unable to read the image:\n" + e);
      return null;
    }
  }
} // end SeamCarverMain class

//=====================================================================
// A private class that will display an image in a JFrame window.
class ImageWindow extends JFrame {

  private BufferedImage image;

  //-------------------------------------------------------------------
  public ImageWindow(String name, BufferedImage image, 
                                  int xOffset, int yOffset,
                                  SeamCarverMain mainFrame) {
    super(name); // Create JFrame window with an appropriate name

    // Set the JFrame's attributes
    this.setSize(image.getWidth(), image.getHeight());
    this.setLocation(xOffset, yOffset);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(false);
    this.setVisible(true);
    this.addMouseListener(mainFrame);

    // Set the image to display in the window
    this.image = image;
  }

  //-------------------------------------------------------------------
  // Reset the image for the window and update the window size appropriately
  public void setImage(BufferedImage image) {
    this.image = image;

    // If the image is not the same size as the window, change the window's size
    if (image.getWidth() != this.getWidth()) {
      this.setSize(image.getWidth(), image.getHeight());
    }

    this.repaint();
  }

  //-------------------------------------------------------------------
  // Display the image in the window
  @Override
  public void paint(Graphics g) {
    if (getWidth() > image.getWidth() ) { // Clear the window
      g.setColor(Color.WHITE);
      g.fillRect(0, 0, getWidth(), getHeight());
    }
    
    // display the image
    g.drawImage(image, 0, 0, this);
  }
} // end ImageWindow class
