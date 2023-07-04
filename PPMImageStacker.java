package ce326.hw2;

import java.io.*;
import java.util.*;
import java.io.FileNotFoundException;

/******CLASS PPMImageStacker******/
public class PPMImageStacker {
    List <PPMImage> PPMImagesList; //linked list of PPMImages
    PPMImage stackedImage;  //image after stacking

    //CONSTRUCTOR:
    public PPMImageStacker(java.io.File dir) throws UnsupportedFileFormatException, FileNotFoundException {
        PPMImage tempImage;

        if(!dir.exists()){
            throw new java.io.FileNotFoundException("[ERROR] Directory <dirname> does not exist!");
        }
        if(!dir.isDirectory()){
            throw new java.io.FileNotFoundException("[ERROR] <dirname> is not a directory!");
        }

        File[] fileArray = dir.listFiles();  //list of files (PPMImages but written in files)
        PPMImagesList = new LinkedList<PPMImage>();

        for(int i=0; i<fileArray.length; i++){
            tempImage = new PPMImage(fileArray[i]); //call the constructor which make a PPMImage from a file
            PPMImagesList.add(tempImage);  //add the image in the list
        }

    }

    //METHODS:
    public void stack(){
        int height = PPMImagesList.get(0).height;
        int width = PPMImagesList.get(0).width;
        int totalSum = 0;
        int red = 0;
        int blue = 0;
        int green = 0;
        int m=0;

        stackedImage = new PPMImage(width, height, 0);

        for(int i=0; i<height; i++){
            for(int j=0; j<width; j++){

                //red:
                totalSum = 0;
                for(m=0; m<PPMImagesList.size(); m++){
                    totalSum = totalSum + PPMImagesList.get(m).image[i][j].getRed();
                }
                red = totalSum/PPMImagesList.size();

                //green:
                totalSum = 0;
                for( m=0; m<PPMImagesList.size(); m++){
                    totalSum = totalSum + PPMImagesList.get(m).image[i][j].getGreen();
                }
                green = totalSum/PPMImagesList.size();

                //blue:
                totalSum = 0;
                for( m=0; m<PPMImagesList.size(); m++){
                    totalSum = totalSum + PPMImagesList.get(m).image[i][j].getBlue();
                }
                blue = totalSum/PPMImagesList.size();

                //SET:
                stackedImage.image[i][j].setRGB((short)red,(short)green,(short)blue);

            }
        }
    }
    public PPMImage getStackedImage(){
        return stackedImage;
    }

}
