/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facedetect;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.*;
import java.awt.Toolkit;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.objdetect.CascadeClassifier;

/**
 *
 * @author Eason Lu
 */
public class FaceDetector {

    public void run() throws FileNotFoundException, IOException {
        System.out.println("\nRunning DetectFaceDemo");
        System.out.println(getClass().getResource("lbpcascade_frontalface.xml").getPath().substring(1));
        String xmlfilePath = getClass().getResource("lbpcascade_frontalface.xml").getPath().substring(1);
        CascadeClassifier faceDetector = new CascadeClassifier(xmlfilePath);
        Mat image = Highgui.imread(getClass().getResource("test.jpg").getPath().substring(1));
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image, faceDetections);

        System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));

//        ImageFilter cropFilter; 
//        BufferedImage tag = new BufferedImage(92, 112, BufferedImage.TYPE_INT_RGB);
        for (Rect rect : faceDetections.toArray()) {
            ImageFilter cropFilter = new CropImageFilter(rect.x, rect.y, rect.width, rect.height);
            BufferedImage tag = new BufferedImage(rect.width, rect.height, BufferedImage.TYPE_INT_RGB);
            File file = new File("D:\\java\\FaceDetect\\src\\facedetect\\test.jpg");
            BufferedImage src = ImageIO.read(file);
            Image img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(src.getSource(), cropFilter));
            BufferedImage output = new BufferedImage(92,112, BufferedImage.TYPE_INT_RGB);
            Graphics g = output.getGraphics();
            g.drawImage(img, 0, 0, 92,112,null);
            g.dispose();
            String dir = "D:\\java\\Github\\JavaCVFaceRecSample\\editpic\\cut_image.jpg";
            File f = new File(dir);
            ImageIO.write(output, "JPEG", f);
//            Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
        }
//        String filename = "faceDetection.png";
//        System.out.println(String.format("Writing %s", filename));
//        Highgui.imwrite(filename, image);
    }
}
