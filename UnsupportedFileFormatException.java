package ce326.hw2;

/******CLASS UnsupportedFileFormatException******/
public class UnsupportedFileFormatException extends java.lang.Exception {

    static final long serialVersionUID = -4567891456L;

    //CONSTRUCTORS:
    public UnsupportedFileFormatException(){
        super();
    }
    public UnsupportedFileFormatException(String msg){
        super(msg);
    }

}
