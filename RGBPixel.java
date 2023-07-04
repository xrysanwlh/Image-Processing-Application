package ce326.hw2;

/******CLASS RGBPixel******/
public class RGBPixel {
    private int rgb; //the pixel

    //CONSTRUCTORS:
    public RGBPixel(short red, short green, short blue){

        this.rgb = red;
        this.rgb = (this.rgb << 8) + green;
        this.rgb = (this.rgb << 8) + blue;
    }
    public RGBPixel(RGBPixel pixel){
        this.rgb = pixel.rgb;
    }
    public RGBPixel(YUVPixel pixel){
        //from yuv to rgb:
        int C = pixel.getY() - 16;
        int D = pixel.getU() - 128;
        int E = pixel.getV() - 128;

        setRed((short)clip(( 298 * C + 409 * E + 128) >> 8));
        setGreen((short)clip(( 298 * C - 100 * D - 208 * E + 128) >> 8));
        setBlue((short) clip(( 298 * C + 516 * D + 128) >> 8));

    }


    //METHODS:
    public int clip (int input){

        if(input < 0)
            input = 0;
        else if(input > 255)
            input = 255;

        return(input);
    }
    public short getRed(){
        return (short)(rgb >> 16);
    }
    public short getGreen(){
        return (short)(rgb >> 8 & 0xFF);
    }
    public short getBlue(){
        return (short)(rgb & 0xFF);
    }
    public void setRed(short red){
        rgb = rgb & 0xFFFF; //keep green and blue the same
        int  helpRed = (red << 16);
        rgb = rgb + helpRed;
    }
    public void setGreen(short green){
        int helpGreen;
        rgb  = rgb & 0x00FF00FF; //keep red and blue the same
        helpGreen = (green << 8);
        rgb = rgb + helpGreen;
    }
    public void setBlue(short blue){
        rgb = rgb & 0x00FFFF00;  //keep red and green the same
        rgb = rgb + blue;
    }
    public int getRGB(){
        return(rgb);
    }
    public void setRGB(int value){
        rgb = value;
    }
    public final void setRGB(short red, short green, short blue){
        setRed(red);
        setGreen(green);
        setBlue(blue);
    }
    public String toString(){
        return getRed() + " " + getGreen() + " " + getBlue();
    }

}
