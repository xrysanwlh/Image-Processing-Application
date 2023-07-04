package ce326.hw2;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


/******CLASS YUVImage******/
public class YUVImage {
    YUVPixel [][] imageYUV;
    int width;
    int height;

    //CONSTRUCTORS:
    public YUVImage(int width, int height){
        //initialize new object's variables
        this.height = height;
        this.width = width;
        imageYUV = new YUVPixel[width][height];

        for(int i=0; i<this.height; i++){
            for(int j=0; j<this.width; j++){
                imageYUV[i][j] = new YUVPixel((short)16, (short)128, (short)128);
            }
        }
    }
    public YUVImage(YUVImage copyImg){
        //initialize new object's variables
        height = copyImg.height;
        width = copyImg.width;
        imageYUV = new YUVPixel[copyImg.width][copyImg.height];

        for(int i=0; i<copyImg.height; i++){
            for(int j=0; j< copyImg.width; j++){
                imageYUV[i][j] = copyImg.imageYUV[i][j];
            }
        }

    }
    public YUVImage(RGBImage RGBImg){
        //initialize new object's variables
        this.height = RGBImg.height;
        this.width = RGBImg.width;
        imageYUV = new YUVPixel[this.height][this.width];

        for(int i=0; i<this.height; i++){
            for(int j=0; j<this.width; j++){
                imageYUV[i][j] = new YUVPixel(RGBImg.image[i][j]);
            }
        }
    }
    public YUVImage(java.io.File file) throws FileNotFoundException, UnsupportedFileFormatException{
        Scanner scan = null;

        try {
            scan = new Scanner(file);
            if(!scan.next().equals("YUV3")){ //if file doesn't starts with YUV3 format
                throw new UnsupportedFileFormatException("THIS FILE IS NOT YUV!");
            }
            else{
                this.width = scan.nextInt();
                this.height = scan.nextInt();
                this.imageYUV = new YUVPixel[this.height][this.width];  //new object with the dimensions we just read

                for(int i=0; i<this.height; i++){
                    for(int j=0; j<this.width; j++){
                        this.imageYUV[i][j] = new YUVPixel(scan.nextShort(), scan.nextShort(), scan.nextShort());
                    }
                }
            }
        } catch (FileNotFoundException e){ System.out.println("ERROR IN OPENING FILE!"); System.exit(-1);}
    }

    //METHODS:
    public String toString(){
        StringBuilder string = new StringBuilder("YUV3\n");
        string.append(this.width).append(" ").append(this.height).append("\n");

        for(int i = 0 ; i < this.height; i++){
            for(int j = 0; j < this.width; j++){
                string.append(this.imageYUV[i][j].toString()).append(" ");
            }
            string.append("\n");
        }
        return string.toString();
    }
    public void toFile(java.io.File file){
        try (FileWriter writer = new FileWriter(file, false)) {
            writer.write(toString());
        }catch(IOException ignored){}
    }
    public void equalize(){
        short newLuminocity=0;
        YUVPixel [][] newImageYUV = new YUVPixel[this.height][this.width]; //the "equalized" image
        Histogram histogram = new Histogram(this);

        histogram.equalize();  //equalize image's histogram

        for(int i=0; i<this.height; i++){
            for(int j=0; j<this.width; j++){
                //change the Y values with the refreshed ones
                newLuminocity = histogram.getEqualizedLuminocity((int)imageYUV[i][j].getY());
                newImageYUV[i][j] = new YUVPixel(newLuminocity, imageYUV[i][j].getU(), imageYUV[i][j].getV());
            }
        }
        imageYUV = newImageYUV;
    }

}
