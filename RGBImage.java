package ce326.hw2;

/******CLASS RGBImage******/
public class RGBImage implements Image{
    static int MAX_COLORDEPTH = 255;
    protected RGBPixel [][] image;
    int colordepth;
    int width;
    int height;


    //CONSTRUCTORS:
    public RGBImage(){}
    public RGBImage(int width, int height, int colordepth){
        short initValue = 0;
        //initialize new object's variables
        this.height = height;
        this.width = width;
        this.colordepth = colordepth;

        image = new RGBPixel[height][width];

        for(int i=0; i<this.height; i++){
            for(int j=0; j<this.width; j++){
                image[i][j] = new RGBPixel(initValue, initValue, initValue);  //(0,0,0)
            }
        }

    }
    public RGBImage(RGBImage copyImg){
        //initialize new object's variables
        this.height = copyImg.height;
        this.width = copyImg.width;
        this.colordepth = copyImg.colordepth;

        image = new RGBPixel[copyImg.height][copyImg.width];

        for(int i=0; i<copyImg.height; i++){
            for(int j=0; j< copyImg.width; j++){
                image[i][j] = copyImg.image[i][j];   //or copyImg.getPixel(i,j);
            }
        }

    }
    public RGBImage(YUVImage YUVImg){
        //initialize new object's variables
        this.height = YUVImg.height;
        this.width = YUVImg.width;

        image = new RGBPixel[this.height][this.width];

        for(int i=0; i<YUVImg.height; i++) {
            for (int j = 0; j < YUVImg.width; j++) {
                image[i][j] = new RGBPixel(YUVImg.imageYUV[i][j]);
            }
        }

    }

    //METHODS:
    public int getWidth(){
        return this.width;
    }
    public int getHeight(){
        return this.height;
    }
    public int getColorDepth(){
        return this.colordepth;
    }
    public RGBPixel getPixel(int row, int col){
        return this.image[row][col];
    }
    public void setPixel(int row, int col,  RGBPixel pixel){
        image[row][col] = pixel;
    }
    public void grayscale(){
        short gray=0;

        for(int i=0; i<this.height; i++){
            for(int j=0; j<this.width; j++){
                //calculate gray value for this pixel
                gray = (short)(0.3*image[i][j].getRed() + 0.59*image[i][j].getGreen() + 0.11*image[i][j].getBlue());
                //set it
                image[i][j].setRed(gray);
                image[i][j].setGreen(gray);
                image[i][j].setBlue(gray);
            }
        }
    }
    public void doublesize(){
        //initialize-new (double) image:
        int heightOld = this.height;
        int widthOld = this.width;
        RGBPixel [][] newImage = new RGBPixel[2*(heightOld)][2*(widthOld)];

        for(int i=0; i<this.height; i++){
            for(int j=0; j<this.width; j++){
                newImage[2*i][2*j] = new RGBPixel(image[i][j]);
                newImage[2*i + 1][2*j] = new RGBPixel(image[i][j]);
                newImage[2*i][2*j + 1] = new RGBPixel(image[i][j]);
                newImage[2*i + 1][2*j + 1] = new RGBPixel(image[i][j]);
            }
        }
        //refresh height, width, image
        this.height = 2*(heightOld);
        this.width = 2*(widthOld);
        image = newImage;

    }
    public void halfsize(){
        //initialize-new (half) image:
        int heightOld = this.height;
        int widthOld = this.width;
        RGBPixel [][] newImage = new RGBPixel[(heightOld)/2][(widthOld)/2];
        int newRed=0, newGreen=0, newBlue=0;


        for(int i=0; i<(this.height)/2; i++){
            for(int j=0; j<(this.width)/2; j++){
                newRed = (image[2*i][2*j].getRed() + image[2*i + 1][2*j].getRed() + image[2*i][2*j + 1].getRed() + image[2*i + 1][2*j + 1].getRed())/4;
                newGreen = (image[2*i][2*j].getGreen() + image[2*i + 1][2*j].getGreen() + image[2*i][2*j + 1].getGreen() + image[2*i + 1][2*j + 1].getGreen())/4;
                newBlue = (image[2*i][2*j].getBlue() + image[2*i + 1][2*j].getBlue() + image[2*i][2*j + 1].getBlue() + image[2*i + 1][2*j + 1].getBlue())/4;
                newImage[i][j] = new RGBPixel ((short)newRed, (short)newGreen, (short)newBlue);
            }
        }
        //refresh height, width, image
        this.width = (widthOld)/2;
        this.height = (heightOld)/2;
        image = newImage;
    }
    public void rotateClockwise(){
        //initialize-new rotated object(image):
        int newHeight = this.width;
        int newWidth = this.height;
        RGBPixel [][] newImage = new RGBPixel[(this.width)][(this.height)];  //reverse dimensions (!)

        for(int i=0; i<this.height; i++){
            for(int j=0; j<this.width; j++){
                newImage[j][this.height - i - 1] = image[i][j];
            }
        }
        //refresh
        this.height = newHeight;
        this.width = newWidth;
        image = newImage;
    }

}
