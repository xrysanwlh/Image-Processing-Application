package ce326.hw2;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

/******CLASS PPMImage******/
public class PPMImage extends RGBImage{


    //CONSTRUCTORS:
    public PPMImage(java.io.File file) throws FileNotFoundException, UnsupportedFileFormatException{
        Scanner scan = null;

        try {
            scan = new Scanner(file);
            if(!scan.next().equals("P3")){ //if file doesn't start with P3
                throw new UnsupportedFileFormatException("THIS FILE IS NOT PPM!");
            }
            else{
                if(scan.hasNextLine()) {
                    this.width = scan.nextInt();
                    this.height = scan.nextInt();
                }
                if(scan.hasNextLine())
                    this.colordepth = scan.nextInt();

                this.image = new RGBPixel[this.height][this.width]; //new object with dimensions we just read
                for(int i=0; i<this.height; i++){
                    for(int j=0; j<this.width; j++){
                        this.image[i][j] = new RGBPixel(scan.nextShort(), scan.nextShort(), scan.nextShort());
                    }
                }
            }
        } catch (FileNotFoundException e){ System.out.println("ERROR IN OPENING FILE!"); System.exit(-1);}

    }
    public PPMImage(RGBImage img){
        super(img);
    }
    public PPMImage(YUVImage img){
        super(img);
    }
    public PPMImage(int width, int height, int colordepth){
        super(width, height, colordepth);
    }

    //METHODS:
    public String toString(){
        StringBuilder string = new StringBuilder("P3\n");
        string.append(this.width).append(" ").append(this.height).append("\n").append(MAX_COLORDEPTH).append("\n");

        for(int i = 0 ; i < this.height; i++){
            for(int j = 0; j < this.width; j++){
                string.append(this.image[i][j].toString()).append(" ");
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

}
