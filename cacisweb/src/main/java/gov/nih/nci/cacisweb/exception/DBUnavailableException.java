package gov.nih.nci.cacisweb.exception;

/**
 * @author Ajay Nalamala
 */
public class DBUnavailableException extends CaCISWebException {

    private static final long serialVersionUID = -5845265416209197670L;

    /**
     * Constructor for DBUnavailableException.
     */
    public DBUnavailableException() {
        super();
    }

    /**
     * Constructor for DBUnavailableException.
     * 
     * @param s
     */
    public DBUnavailableException(String s) {
        super(s);
    }

}
