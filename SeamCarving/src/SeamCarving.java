// ====================================================================
// CS380 - Pex1 - Seam carving to reduce image size
// By: Dr. Wayne Brown, 9 Jan 2012
// Task: Perform the calculations and image manipulation required to cut
//       a vertical seam of "least energy" from an image.
// ====================================================================

import java.awt.Color;
import java.awt.image.BufferedImage;

public class SeamCarving
{
  //-------------------------------------------------------------------
  public static BufferedImage toGrayscale(BufferedImage image) {
  
    int xWidth  = image.getWidth();
    int yHeight = image.getHeight();
    BufferedImage grayscaleImage = new BufferedImage(xWidth, yHeight,
                                                      BufferedImage.TYPE_BYTE_GRAY);

    for (int y=0; y<yHeight; y++) {
      for (int x=0; x<xWidth; x++) {
        grayscaleImage.setRGB(x, y, image.getRGB(x, y));
      }
    }
    return grayscaleImage;
  }

  //-------------------------------------------------------------------
  public static BufferedImage gradientImage(BufferedImage grayscaleImage) {
  
    BufferedImage gradientImage;
    int           s00, s01, s02;    // samples above the current pixel
    int           s10,      s12;    // samples left and right of the current pixel
    int           s20, s21, s22;    // samples below the current pixel

    int xWidth  = grayscaleImage.getWidth();
    int yHeight = grayscaleImage.getHeight();
    gradientImage = new BufferedImage(xWidth, yHeight, BufferedImage.TYPE_BYTE_GRAY);

    // Retrieve the pixel values from the grayscale image - one integer per pixel
    int [][] pixels = new int[yHeight][xWidth];
    for (int y=0; y<yHeight; y++) {
      grayscaleImage.getRaster().getPixels(0, y, xWidth, 1, pixels[y]);
    }
    
    // Calculate the amount of change around each pixel
    for (int y=1; y<yHeight-1; y++) {
      for (int x=1; x<xWidth-1; x++) {
        // Get the surrounding pixels
        s00 = pixels[y-1][x-1]; s01 = pixels[y-1][x]; s02 = pixels[y-1][x+1];
        s10 = pixels[y  ][x-1];                       s12 = pixels[y  ][x+1];
        s20 = pixels[y+1][x-1]; s21 = pixels[y+1][x]; s22 = pixels[y+1][x+1];
        
        // Calculate the amount of change around pixel[y][x]
        int gradient = Math.abs(s00 - s20) + Math.abs(s01 - s21) + Math.abs(s02 - s22)
                     + Math.abs(s00 - s02) + Math.abs(s10 - s12) + Math.abs(s20 - s22);
        gradient = Math.min(gradient, 255);  // limit to 255
        
        Color c = new Color(gradient,gradient,gradient);
        gradientImage.setRGB(x, y, c.getRGB() );
      }
    }

    // We avoided all the border pixels. Now set the border pixels to the same
    // values as the interior pixels next to them.
    int bottomRow = 0;
    int topRow    = yHeight-1;
    for (int x=1; x<xWidth-1; x++) {
      gradientImage.setRGB(x, bottomRow, gradientImage.getRGB(x,1) );
      gradientImage.setRGB(x, topRow,    gradientImage.getRGB(x,topRow-1) );
    }

    int leftCol  = 0;
    int rightCol = xWidth-1;
    for (int y=1; y<yHeight-1; y++) {
      gradientImage.setRGB(leftCol,  y, gradientImage.getRGB(1,y) );
      gradientImage.setRGB(rightCol, y, gradientImage.getRGB(rightCol-1,y) );
    }

    // Set the 4 corners using their diagonal pixels
    gradientImage.setRGB(0,        0,      gradientImage.getRGB(1,1) );
    gradientImage.setRGB(rightCol, 0,      gradientImage.getRGB(rightCol-1,1) );
    gradientImage.setRGB(rightCol, topRow, gradientImage.getRGB(rightCol-1,topRow-1) );
    gradientImage.setRGB(0,        topRow, gradientImage.getRGB(1,topRow-1) );

    return gradientImage;
  }

  //-------------------------------------------------------------------
  public static int [] findMinimumSeam(BufferedImage gradientImage) {
  
    int yHeight = gradientImage.getHeight();
    int xWidth  = gradientImage.getWidth();

    float [][] gradient  = new float[yHeight][xWidth];
    float [][] energy    = new float[yHeight][xWidth];

    // Get the gradient pixel values out of the gradientImage
    for (int y=0; y<yHeight; y++) {
      gradientImage.getRaster().getPixels(0, y, xWidth, 1, gradient[y]);
    }
    
    // For each pixel in the image, calculate its "minimum accumulated energy",
    // assuming a seam was working its way up from the bottom row of the image
    // to the top of the image.

    // The first row of the energy matrix is just the gradient values
    for (int x=0; x < xWidth; x++) {
      energy[0][x] = gradient[0][x];
    }
    
    // Now for the remaining rows, calculate the minimum energy for each pixel
    for (int y = 1; y < yHeight; y++) {
      // The first, left pixel is a special case because it has no below-left pixel
      energy[y][0] = gradient[y][0] + Math.min( energy[y-1][0], 
                                                energy[y-1][1]);

      // Calculate the minimum energy on all columns that have 3 pixels below it
      for (int x=1; x < xWidth-1; x++) {
        energy[y][x] = gradient[y][x] + Math.min(energy[y-1][x-1],
                                        Math.min(energy[y-1][x], 
                                                 energy[y-1][x+1]));
      }
      
      // The last, right pixel is a special case because it has no below-right pixel
      energy[y][xWidth-1] = gradient[y][xWidth-1]
                          + Math.min( energy[y-1][xWidth-2], 
                                      energy[y-1][xWidth-1]);
    }

    // Use the energy matrix to find the vertical seam with minimum energy.

    // Find the minimum energy value on the top row
    int   seamStartX = 0;
    int   topRow     = yHeight - 1;
    float minEnergy  = energy[topRow][seamStartX];
    for (int x=0; x<xWidth - 1; x++) {
      if (energy[topRow][x] < minEnergy) {
        minEnergy = energy[topRow][x];
        seamStartX = x;
      }
    }

    // Now work down the image, using the energy matrix to determine whether
    // the seam should go left, straight down, or right from the current pixel.
    int [] seam = new int[yHeight];
    seam[topRow] = seamStartX;
    for (int y = topRow-1; y >= 0; y--) {
      int x = seam[y+1];
      if (x <= 0) { // we are on the left border and can't go left
        if (energy[y+1][0] < energy[y+1][1]) {
          seam[y] = 0;
        } else {
          seam[y] = 1;
        }
      } else if (x >= xWidth-1) {// we are on the right border and can't go right
        if (energy[y+1][xWidth-2] < energy[y+1][xWidth-1]) {
          seam[y] = xWidth-2;
        } else {
          seam[y] = xWidth-1;
        }
      } else { // the normal case where you can go left, down, or right
        float min = Math.min(energy[y+1][x-1],
                    Math.min(energy[y+1][x], 
                             energy[y+1][x+1]));
             if (min == energy[y+1][x-1]) seam[y] = x-1;
        else if (min == energy[y+1][x])   seam[y] = x;
             else                         seam[y] = x+1;
      }
    }
    return seam;
  }

  //-------------------------------------------------------------------
  public static BufferedImage removeSeam(BufferedImage originalImage, int [] seam) {
  
    int xWidth  = originalImage.getWidth();
    int yHeight = originalImage.getHeight();
    BufferedImage smallerImage = new BufferedImage(xWidth-1, yHeight,
                                                    BufferedImage.TYPE_INT_RGB);

    for (int y=0; y<yHeight; y++) {
      for (int x=0, xNew=0; x<xWidth; x++) {
        if (x != seam[y])
          smallerImage.setRGB(xNew++, y, originalImage.getRGB(x, y));
      }
    }
    
    return smallerImage;
  }
  
  //-------------------------------------------------------------------
  public static BufferedImage showSeamOnImage(BufferedImage originalImage, int [] seam)
  {
    int xWidth  = originalImage.getWidth();
    int yHeight = originalImage.getHeight();
    BufferedImage seamImage = new BufferedImage(xWidth, yHeight,
                                                 BufferedImage.TYPE_INT_RGB);
    int red = Color.RED.getRGB();

    for (int y=0; y<yHeight; y++) {
      for (int x=0; x<xWidth; x++) {
        if (x != seam[y]) {
          seamImage.setRGB(x, y, originalImage.getRGB(x, y));
        } else {
          seamImage.setRGB(x, y, red);
        }
      }
    }

    return seamImage;
  }

} // end of SeamCarving class
