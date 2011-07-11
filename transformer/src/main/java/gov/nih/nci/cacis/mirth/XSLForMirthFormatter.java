package gov.nih.nci.cacis.mirth;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Utility class to format the xsls for mirth connect and write to the output folder.
 * 
 * @author vinodh.rc@semanticbits.com
 * 
 */
public class XSLForMirthFormatter {

    private static final Log LOG = LogFactory.getLog(XSLForMirthFormatter.class);

    /**
     * Formats all XSL files for Mirth
     * 
     * @param outputDir - String instance for the output dir
     * @param xsls - String[] representing the path to the xsl files
     * @throws IOException - error thrown, if any
     */
    public void formatXSL(String outputDir, String[] xsls) throws IOException {
        final File opDir = new File(outputDir);
        if ( !opDir.exists() && !opDir.mkdirs() ) {
            throw new IOException("Unable to create the output dir");
        }
        for (int i = 0; i < xsls.length; i++) {
           formatSingleXSl(opDir, xsls[i]);
        }
    }

    /**
     * Formats one XSL file for Mirth
     * 
     * @param outputDir - String instance for the output dir
     * @param xsl - String representing the path to the xsl file
     * @throws IOException - error thrown, if any
     */
    public void formatSingleXSl(String outputDir, String xsl) throws IOException {
        final File opDir = new File(outputDir);
        if ( !opDir.exists() && !opDir.mkdirs() ) {
            throw new IOException("Unable to create the output dir");
        }
        formatSingleXSl(opDir, xsl);
    }

    /**
     * Formats one XSL file for Mirth
     * 
     * @param outputDir - File instance for the output dir
     * @param xsl - String representing the path to the xsl file
     * @throws IOException - error thrown, if any
     */
    public void formatSingleXSl(File outputDir, String xsl) throws IOException {
        File xslF = null;
        File formattedF = null;
        FileWriter fw = null;
        String xslContent = null;
        String formattedContent = null;

        try {
            xslF = new File(xsl);             
            formattedF = new File(outputDir, xslF.getName());
            fw = new FileWriter(formattedF);

            xslContent = FileUtils.readFileToString(xslF);

            // htmlencode content
            formattedContent = StringEscapeUtils.escapeXml(xslContent);

            // escape all dollar signs
            formattedContent = formattedContent.replaceAll("\\$", "\\\\\\$");

            fw.write(formattedContent);
            fw.flush();
        } catch (IOException e) {
            LOG.debug("Error writing formatted xsl", e);
            throw e;
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    LOG.debug("Error while closing the output writer", e);
                }
            }
        }
    }

    /**
     * @param args - main method args
     * @throws IOException - error thrown, if any
     * @throws URISyntaxException - error thrown, if any
     */
    public static void main(String[] args) throws IOException, URISyntaxException {
        if (args.length < 2) {
            LOG.error("Usage: XSLForMirthFormatter <formatted-file-output-dir> <xsl-file-1> <xsl-file-2> ...");
            System.exit(1);
        }
        final List<String> lst = new ArrayList<String>(Arrays.asList(args));
        lst.remove(0);
        final XSLForMirthFormatter frmtr = new XSLForMirthFormatter();
        frmtr.formatXSL(args[0], (String[]) lst.toArray(new String[lst.size()]));
    }

}
