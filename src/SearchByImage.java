import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;


public class SearchByImage
{
    // !!!user can't see the error messages
    // !!!only returns the fist similar image found
    // returns the image path or "0"
    // finds image in folder that is at least 95% similar to the input image and returns its file path
    public static String Search(String inputFolderPath, String inputImgPath)
    {
        // creates File objects
        File inputFolder = new File(inputFolderPath);
        File inputImg = new File(inputImgPath);

        // checks folder
        if (!inputFolder.exists() || !inputFolder.isDirectory())
        {
            System.out.println("Invalid input folder: " + inputFolderPath);
            return "0";
        }

        // checks image file
        if (!inputImg.exists() || !inputImg.isFile())
        {
            System.out.println("Invalid input image");
            return "0";
        }

        // lists all image files in the folder
        List<File> folderImgs = GetImgsFromFolder(inputFolder);

        if (folderImgs == null)
        {
            System.out.println("Cannot list files in folder: " + inputFolderPath);
            return "0";
        }

        // compares images from the folder to the input image and returns the file path of the matching image
        for (File folderImg : folderImgs)
        {
            boolean areSame = CompareImgs(folderImg, inputImg);

            if (areSame)
            {
                String outputImgPath = folderImg.getAbsolutePath();
                return outputImgPath;
            }
        }

        return "0";
    }


    // returns an ArrayList with all image files in a folder, including the ones in subfolders
    private static List<File> GetImgsFromFolder(File folder)
    {
        List<File> imgs = new ArrayList<>();

        File[] topLevelFiles = folder.listFiles();

        for(File file : topLevelFiles)
        {
            // image file
            if(file.isFile() &&
            (file.getName().toLowerCase().endsWith(".jpg") ||
            file.getName().toLowerCase().endsWith(".jpeg") ||
            file.getName().toLowerCase().endsWith(".png")))
            {
                imgs.add(file);
            }
            // subfolder
            else if(file.isDirectory())
            {
                imgs.addAll(GetImgsFromFolder(file));
            }
        }

        return imgs;
    }


    // compares two images and returns true if they are at least 95% similar
    private static boolean CompareImgs(File fileImgA, File fileImgB)
    {
        // initially assigning null
        BufferedImage imgA = null;
        BufferedImage imgB = null;

        // try block to check for exception
        try
        {
            // reading files
            imgA = ImageIO.read(fileImgA);
            imgB = ImageIO.read(fileImgB);
        }

        // catch block to check for exceptions
        catch (IOException e)
        {
            // display the exceptions
            System.out.println(e);
        }

        if (imgA == null || imgB == null)
        {
            //System.out.println("One of the images could not be read");
            return false;
        }

        // assigning dimensions to image
        int width1 = imgA.getWidth();
        int width2 = imgB.getWidth();
        int height1 = imgA.getHeight();
        int height2 = imgB.getHeight();

        // checking whether the images are of same size or not
        if ((width1 != width2) || (height1 != height2))
        {
            //System.out.println("Images dimensions mismatch");
            return false;
        }
        else
        {
            // by now, images are of same size

            long difference = 0;

            // treating images like 2D matrix

            // outer loop for rows(height)
            for (int y = 0; y < height1; y++)
            {
                // inner loop for columns(width)
                for (int x = 0; x < width1; x++)
                {
                    int rgbA = imgA.getRGB(x, y);
                    int rgbB = imgB.getRGB(x, y);
                    int redA = (rgbA >> 16) & 0xff;
                    int greenA = (rgbA >> 8) & 0xff;
                    int blueA = (rgbA) & 0xff;
                    int redB = (rgbB >> 16) & 0xff;
                    int greenB = (rgbB >> 8) & 0xff;
                    int blueB = (rgbB) & 0xff;

                    difference += Math.abs(redA - redB);
                    difference += Math.abs(greenA - greenB);
                    difference += Math.abs(blueA - blueB);
                }
            }

            // total number of red pixels = width * height
            // total number of blue pixels = width * height
            // total number of green pixels = width * height
            // so total number of pixels = width * height * 3
            double total_pixels = width1 * height1 * 3;

            // normalizing the value of different pixels for accuracy

            // average pixels per color component
            double avg_different_pixels = difference / total_pixels;

            // there are 255 values of pixels in total
            double percentage_difference = (avg_different_pixels / 255) * 100;

            if(percentage_difference <= 5)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    }
}