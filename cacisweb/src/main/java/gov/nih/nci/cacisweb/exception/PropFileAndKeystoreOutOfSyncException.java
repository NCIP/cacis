package gov.nih.nci.cacisweb.exception;

/**
 * @author Ajay Nalamala
 */
public class PropFileAndKeystoreOutOfSyncException extends CaCISWebException {

    private static final long serialVersionUID = -5845265416209197670L;

    /**
     * Constructor for DBUnavailableException.
     */
    public PropFileAndKeystoreOutOfSyncException() {
        super();
    }

    /**
     * Constructor for DBUnavailableException.
     * 
     * @param s
     */
    public PropFileAndKeystoreOutOfSyncException(String s) {
        super(s);
    }

}
