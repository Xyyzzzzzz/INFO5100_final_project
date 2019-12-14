import javax.imageio.ImageIO;
import java.util.List;
import java.awt.*;
import java.io.File;
import java.awt.image.BufferedImage;

public class changeFormat{
    public static void change(List<File> files, String path){

        //get the path of the folder and format of images
        String folder = path.substring(0, path.lastIndexOf('.'));
        String extention = path.substring(path.lastIndexOf('.'));

        //print the extention and folder path
        System.out.println("extention: " + extention);
        System.out.println("folder: " + folder);

        for (File file : files) {
            String filePath = file.getName();
            String fileName = filePath.substring(0, filePath.lastIndexOf('.'));

            String destinationPath = folder + fileName + extention;

            System.out.println("source path: " + file.getPath());
            System.out.println("destination path: " + destinationPath);

            //download converted images to target folder
            try {
                BufferedImage bufferedImage = ImageIO.read(new File(file.getPath()));

                // create a blank, RGB, same width and height, and a white background
                BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),
                        bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
                newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);

                // write to desired format
                String format = extention.substring(1);
                ImageIO.write(newBufferedImage, format, new File(destinationPath));
            }catch (Exception e){
                System.out.println(e.toString());
            }
        }
    }
}
