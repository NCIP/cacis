package gov.nih.nci.cacisweb.exception;


/**
 * 
 * @author Ajay Nalamala
 * 
 */
public class CaCISWebException extends Exception {

    /**
	 * 
	 */
    private static final long serialVersionUID = 3447728820041499533L;

    public CaCISWebException() {
    }

    public CaCISWebException(String arg0) {
        super(arg0);
    }

    public CaCISWebException(Throwable arg0) {
        super(arg0);
    }

    public CaCISWebException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

}
