package ce326.hw2;

import java.io.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/******CLASS Histogram******/
public class Histogram {

    int [] histogram;  //the (unequalized) histogram
    double [] equalizedHistogram;  //helping buffer to calculate the finalEqualized histogram
    int [] finalEqualizedHistogram;  // the equalized histogram
    int totalPixels;

    //CONSTRUCTOR:
    public Histogram(YUVImage img){
        int freq = 0;
        //calculate total pixels , need later
        totalPixels = img.height * img.width;

        //initialization
        histogram = new int[236];
        Arrays.fill(histogram, 0);

        for(int i=0; i<img.height; i++){
            for(int j=0; j< img.width; j++){
                //get luminocity, +1 this thesis in histogram
                freq = img.imageYUV[i][j].getY();
                histogram[freq]++;
            }
        }

    }

    //METHODS:
    public String toString(){
        StringBuilder string = new StringBuilder();
        int ones = 0;
        int tens = 0;
        int hundrents = 0;
        int thousands = 0;
        int temp = 0;

        for(int i=0; i<histogram.length; i++) {
            ones = 0;
            tens = 0;
            hundrents = 0;
            thousands = 0;

            string.append("\n").append(String.format("%3d", i)).append(".(").append(String.format("%4d", histogram[i])).append(")\t");

            thousands = histogram[i]/1000;
            temp = histogram[i] - thousands*1000;

            if(temp > 99) { //if number XXX: has hundrents, tens, ones
                ones = Character.digit(String.valueOf(temp).charAt(2), 10);
                tens = Character.digit(String.valueOf(temp).charAt(1), 10);
                hundrents = Character.digit(String.valueOf(temp).charAt(0), 10);
            }
            else if(temp > 9) { //if number XX: has only tens, ones
                tens = Character.digit(String.valueOf(temp).charAt(0), 10);
                ones = Character.digit(String.valueOf(temp).charAt(1), 10);
            }
            else { //if number X: has only ones
                ones = Character.digit(String.valueOf(temp).charAt(0), 10);
            }

            for(int j=0; j<thousands; j++){
                string.append("#");
            }
            for(int j=0; j<hundrents; j++){
                string.append("$");
            }
            for(int j=0; j<tens; j++){
                string.append("@");
            }
            for(int j=0; j<ones; j++){
                string.append("*");
            }

        }

        string.append("\n");

        return string.toString();
    }
    public void toFile(File file){
        try (FileWriter writer = new FileWriter(file, false)) {
            writer.write(toString());
        }catch(IOException ex){}
    }
    public void equalize(){
        equalizedHistogram = new double [236];
        finalEqualizedHistogram = new int [236];

        //initializations:
        for(int i=0; i<equalizedHistogram.length; i++){
            equalizedHistogram[i] = 0;
            finalEqualizedHistogram[i] = 0;
        }
        //PMF buffer:
        for(int i=0; i<equalizedHistogram.length; i++){
            equalizedHistogram[i] = ((double)histogram[i])/(double)totalPixels;
        }
        //CDF buffer:
        for(int i=1; i<equalizedHistogram.length; i++){
            equalizedHistogram[i] = equalizedHistogram[i] + equalizedHistogram[i-1];
        }
        //final equalized buffer:
        for(int i=0; i<finalEqualizedHistogram.length; i++){
            finalEqualizedHistogram[i] = (int)(equalizedHistogram[i]*235);
        }
    }
    public short getEqualizedLuminocity(int luminocity){
        return((short)finalEqualizedHistogram[luminocity]);
    }

}
