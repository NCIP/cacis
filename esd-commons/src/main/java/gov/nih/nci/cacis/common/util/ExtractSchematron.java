/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.common.util;

import com.google.common.base.Function;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.xerces.impl.xs.util.StringListImpl;
import org.apache.xerces.xs.XSAnnotation;
import org.apache.xerces.xs.XSComplexTypeDefinition;
import org.apache.xerces.xs.XSConstants;
import org.apache.xerces.xs.XSElementDeclaration;
import org.apache.xerces.xs.XSImplementation;
import org.apache.xerces.xs.XSLoader;
import org.apache.xerces.xs.XSModel;
import org.apache.xerces.xs.XSModelGroup;
import org.apache.xerces.xs.XSNamedMap;
import org.apache.xerces.xs.XSNamespaceItem;
import org.apache.xerces.xs.XSNamespaceItemList;
import org.apache.xerces.xs.XSObjectList;
import org.apache.xerces.xs.XSParticle;
import org.apache.xerces.xs.XSTerm;
import org.apache.xerces.xs.XSTypeDefinition;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Utility for extracting schematron rules from an HL7 service schema. Because the rules are abstract, we need to
 * traverse the schema and generate possible paths to all instances of a given type.
 * 
 * This produces two schematron schema files. The abstract schema file contains all the abstract rules extracted from
 * the schema. As part of the extraction, we put all the rules in a single pattern, to make inclusion work. Also, we use
 * the name of each pattern as the rule assertion text, so that error messages will have some human readable content.
 * Finally, we modify the rule tests so that all elements are properly referenced with the hl7 namespace.
 * 
 * A known limitation is that this does not handle derivation by extension in the service schema. So far, the
 * MIF-generated service schemas do not appear to use it.
 * 
 * @author dkokotov
 * @since Aug 19, 2010
 */
// TODO Move this class into a utils or tools project. http://caehrorg.jira.com/browse/ESD-1047
public class ExtractSchematron { // NOPMD

    private static final String V3_NS = "urn:hl7-org:v3";
    private static final String V3_PREFIX = "hl7";
    private static final String XS_NS = "http://www.w3.org/2001/XMLSchema";
    private static final String XS_PREFIX = "xs";
    private static final String XSI_NS = "http://www.w3.org/2001/XMLSchema-instance";
    private static final String XSI_PREFIX = "xsi";
    private static final String SCH_NS = "http://purl.oclc.org/dsdl/schematron";
    // private static final String SCH_NS = "http://www.ascc.net/xml/schematron";
    private static final String SCH_PREFIX = "sch";
    private static final String XPATHFN_NS = "http://www.w3.org/2005/xpath-functions";
    private static final String XPATHFN_PREFIX = "fn";
    private static final Map<String, String> DEFAULT_NS_MAP = Maps.newHashMap();
    static {
        DEFAULT_NS_MAP.put(V3_NS, V3_PREFIX);
        DEFAULT_NS_MAP.put(XS_NS, XS_PREFIX);
        DEFAULT_NS_MAP.put(XSI_NS, XSI_PREFIX);
        DEFAULT_NS_MAP.put(SCH_NS, SCH_PREFIX);
        DEFAULT_NS_MAP.put(XPATHFN_NS, XPATHFN_PREFIX);
    }

    // XML Schema "anyType" is the parent of all complex types
    private static final String XS_ANYTYPE_NAME = "anyType";

    // the list of special flavor names which do not have a dot in their name
    private static final List<String> SPECIAL_FLAVOR_TYPES = Arrays.asList("BN", "CV", "PN", "PNXP", "TN", "TNXP",
            "ON", "ONXP");

    // regexp for finding element references in the schematron rules, so we can prepend hl7:
    // this needs to match all elements but omit attributes, functions, reserved XPath keywords etc.
    // The basic approach is two-part: restrict what can come before the potential match and what can
    // can be part of the match.
    // the following are what can come before a possible element reference:
    // - nothing (match can be at the very start of XPath expression)
    // - "or " or "and "
    // - "::" or "/" or "("
    // an element must consist of just letters (in theory numbers can be part of an element name, but
    // none of the iso 21090 elements use them). we then further check that this is not one of the known
    // xpath keywords or functions:
    // and, not, or, count, string-length, self, starts-with
    private static final String ELEMENT_REGEXP = "(^|or |and |::|/|\\(|\\|)([^@naocmspxt()&\\.\\[\\\\=*+>!\\-0-9]"
        + "|n(?!ot[ \\(])|a(?!nd[ \\(])|o(?!r[ \\(])|c(?!ount\\()|m(?!atches\\()"
        + "|s(?!tring-length\\(|elf|tarts-with\\()|t(?!ext\\()" + "|p(?!lain')|x(?!si:))";

    private static final String USAGE = "Usage: extract [service-schema] [datatypes-schema] "
        + "[output-abstract-schematron] [output-concrete-schematron]";

    private static final Logger LOG = Logger.getLogger(ExtractSchematron.class);

    private final XSLoader schemaLoader;
    private final Map<QName, TypeUsage> datatypeUsages = new HashMap<QName, TypeUsage>();
    private final Map<QName, TypeUsage> flavorUsages = new HashMap<QName, TypeUsage>();
    private final Map<QName, QName> datatypeExtensions = new HashMap<QName, QName>();
    private final BiMap<String, String> nsMap = HashBiMap.create(DEFAULT_NS_MAP);
    private final DocumentBuilderFactory dfactory;
    private final XPathExpression patternExpr;
    private final Transformer serializer;

    /**
     * Main method, expect at least 4 arguments: location of top-level service XSD, location of ISO datatypes XSD
     * (optionally followed by a comma and the location of flavors XSD), and the names of the concrete and abstract
     * schematron schema files to output. Optionally can also provide a 5th argument: a comma-separated list of
     * additional rule files to bring in.
     * 
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        if (args.length < 4) {
            LOG.warn(USAGE);
            System.exit(-1);
        } else {
            System.setProperty(DOMImplementationRegistry.PROPERTY,
            "org.apache.xerces.dom.DOMXSImplementationSourceImpl");
            try {
                String ruleFiles = null; // NOPMD to remove DD-anomaly warning
                if (args.length > 4) {
                    ruleFiles = args[4];
                }
                new ExtractSchematron().extract(args[0], args[1], args[2], args[3], ruleFiles);
                // CHECKSTYLE:OFF it's a main method, so may as well catch the generic exception
            } catch (final Exception e) {
                // CHECKSTYLE:ON
                LOG.error("Error extracting ", e);
            }
        }
    }

    /**
     * Creates new instance of the schematron extractor.
     * 
     * @throws ClassCastException if there is an error getting a SchemaLoader
     * @throws ClassNotFoundException if there is an error getting a SchemaLoader
     * @throws InstantiationException if there is an error getting a SchemaLoader
     * @throws IllegalAccessException if there is an error getting a SchemaLoader
     * @throws TransformerFactoryConfigurationError if there is an error getting a serializer
     * @throws TransformerConfigurationException if there is an error getting a serializer
     * @throws XPathExpressionException if there is an error compiling expressions
     */
    public ExtractSchematron() throws ClassCastException, ClassNotFoundException, InstantiationException,
    IllegalAccessException, TransformerConfigurationException, TransformerFactoryConfigurationError,
    XPathExpressionException {
        final DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
        final XSImplementation impl = (XSImplementation) registry.getDOMImplementation("XS-Loader");

        this.dfactory = DocumentBuilderFactory.newInstance();
        this.dfactory.setNamespaceAware(true);

        final XPathFactory factory = XPathFactory.newInstance();
        final XPath xpath = factory.newXPath();
        xpath.setNamespaceContext(new SchNamespaceContext());

        this.serializer = TransformerFactory.newInstance().newTransformer();
        this.serializer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

        this.patternExpr = xpath.compile("//sch:pattern");

        this.schemaLoader = impl.createXSLoader(null);
    }

    /**
     * Does the extraction.
     * 
     * @param serviceSchema location of the top-level service schema file
     * @param datatypesAndFlavorsSchemas location of the iso datatypes schema file, optionally followed by a comma and
     *            the location of the flavors schema file
     * @param abstractSchematron name of the schematron file with abstract rules to write.
     * @param concreteSchematron name of the schematron file with concrete rules to write.
     * @param commaSeparatedRuleFileList a comma-separated list of rule file names (each rule should have at a MINIMUM
     *            two files such as foo-abstract.sch & foo-concrete.sch) Multiple concrete rule files can also be
     *            passed-in
     * @throws IOException if there is an error writing files
     */
    public void extract(String serviceSchema, String datatypesAndFlavorsSchemas, String abstractSchematron,
            String concreteSchematron, String commaSeparatedRuleFileList) throws IOException {
        processDatatypes(datatypesAndFlavorsSchemas, abstractSchematron);
        processServiceSchema(serviceSchema);
        postprocessFlavorRules();
        postprocessDatatypeExtensions();
        postprocessFlavorContexts();
        writeConcreteSchematron(abstractSchematron, concreteSchematron, commaSeparatedRuleFileList);
    }

    /**
     * Writes out the concrete schematron file, which includes the abstract file and references the abstract rules for
     * each possible path to an ISO datatype element.
     * 
     * @param abstractSchematronLocation
     * @param concreteSchematronLocation
     * @param commaSeparatedRuleFileList
     * @throws IOException
     */
    private void writeConcreteSchematron(String abstractSchematronLocation, String concreteSchematronLocation, // NOPMD
            String commaSeparatedRuleFileList) throws IOException {
        // i should probably use DOM or velocity template or some such to do this
        // but for now this is simple and works
        final Writer out = new FileWriter(concreteSchematronLocation);
        try {
            out.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
            out.write("<sch:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" " + "xmlns:sch=\"" + SCH_NS
                    + "\" queryBinding=\"xslt\">\n");

            for (final Map.Entry<String, String> nsMapping : this.nsMap.entrySet()) {
                if (!XS_NS.equals(nsMapping.getKey()) && !SCH_NS.equals(nsMapping.getKey())) {
                    out.write("  <sch:ns prefix=\"" + nsMapping.getValue() + "\" uri=\"" + nsMapping.getKey()
                            + "\"/>\n"); // NOPMD
                }
            }

            out.write("  <sch:include href=\"" + abstractSchematronLocation + "\"/>\n"); // NOPMD

            /**
             * Pure file name in <include> statement fails with Xalan xslt processor, but works with Saxon and 1.6 JDK
             * default. Although the last two fail in other places of the ant build. With the <include> filename being
             * URI and Xalan xslt engine ant pre-commit build completes successfully.
             * 
             * Also include any custom rule file names passed-in
             */

            if (commaSeparatedRuleFileList != null) {
                for (final String ruleFileName : StringUtils.split(commaSeparatedRuleFileList, ',')) {
                    out.write("  <sch:include href=\"" + new File(ruleFileName).toURI().toASCIIString() + "\"/>\n"); // NOPMD
                }
            }

            for (final TypeUsage typeUsage : this.datatypeUsages.values()) {
                writeTypeUsageToConcreteSchematron(typeUsage, out);
            }

            out.write("<!-- flavor rules -->\n");

            for (final TypeUsage flavorUsage : this.flavorUsages.values()) {
                writeTypeUsageToConcreteSchematron(flavorUsage, out);
            }

            out.write("</sch:schema>\n");
            out.flush();
        } finally {
            try {
                out.close();
                // CHECKSTYLE:OFF We want the original exception to be thrown.
            } catch (final Exception ex) {
                // CHECKSTYLE:ON
                LOG.error("Error closing output stream: " + ex.getMessage(), ex);
            }
        }
    }

    private void writeTypeUsageToConcreteSchematron(TypeUsage typeUsage, Writer out) throws IOException {
        final String context = StringUtils.join(typeUsage.getContexts(), " | ");
        if (!StringUtils.isEmpty(context)) {
            for (final String id : typeUsage.getRuleIds()) {
                out.write("  <sch:pattern name=\"concrete rules\">\n");
                out.write("    <sch:rule context=\"" + context + "\">\n");
                out.write("      <sch:extends rule=\"" + id + "\"/>\n"); // NOPMD
                out.write("    </sch:rule>\n");
                out.write("  </sch:pattern>\n");
            }
        }
    }

    /**
     * Read the ISO datatypes schema and build up a mapping of datatype to schematron abstract rule ids. At the same
     * time, extract the abstract rules and write them to the abstract schematron file.
     * 
     * @param datatypeSchemaLocation
     * @param abstractSchematronLocation
     * @throws IOException
     */
    private void processDatatypes(String datatypesAndFlavorsSchemaLocations, String abstractSchematronLocation)
    throws IOException {
        final String[] schemaLocations = StringUtils.split(datatypesAndFlavorsSchemaLocations, ",");
        final XSModel schema = this.schemaLoader
        .loadURIList(new StringListImpl(schemaLocations, schemaLocations.length));
        if (schema == null) {
            throw new IOException("Schema not loaded for URI(s): " + datatypesAndFlavorsSchemaLocations);
        }
        final Writer out = new FileWriter(abstractSchematronLocation);
        try {
            out.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
            out.write("<sch:pattern name=\"abstract rules\" xmlns:sch=\"" + SCH_NS + "\">\n");

            final XSNamedMap typeDecls = schema.getComponents(XSTypeDefinition.COMPLEX_TYPE);
            for (int i = 0; i < typeDecls.getLength(); i++) {
                final XSComplexTypeDefinition typeDecl = (XSComplexTypeDefinition) typeDecls.item(i);
                processDatatype(typeDecl, out);
            }
            out.write("</sch:pattern>\n");
            out.flush();
        } finally {
            try {
                out.close();
                // CHECKSTYLE:OFF We want the original exception to be thrown.
            } catch (final Exception ex) {
                // CHECKSTYLE:ON
                LOG.error("Error closing output stream: " + ex.getMessage(), ex);
            }
        }

    }

    private void processDatatype(XSComplexTypeDefinition typeDecl, Writer out) throws IOException {
        final TypeUsage typeUsage = new TypeUsage();
        final QName typeName = new QName(V3_NS, typeDecl.getName());
        this.datatypeUsages.put(typeName, typeUsage);
        if (isFlavor(typeName)) {
            this.flavorUsages.put(typeName, new TypeUsage());
        }
        final XSTypeDefinition parentType = typeDecl.getBaseType();
        if (parentType != null && !XS_ANYTYPE_NAME.equals(parentType.getName())) {
            this.datatypeExtensions.put(typeName, new QName(V3_NS, parentType.getName()));
        }

        final XSObjectList annotations = typeDecl.getAnnotations();
        if (annotations != null) {
            for (int i = 0; i < annotations.getLength(); i++) {
                processDatatypeAnnotation((XSAnnotation) annotations.item(i), typeUsage, out);
            }
        }
    }

    private boolean isFlavor(QName typeName) {
        return typeName.getLocalPart().contains(".") && !typeName.getLocalPart().startsWith("StrucDoc")
        || SPECIAL_FLAVOR_TYPES.contains(typeName.getLocalPart());
    }

    private void processDatatypeAnnotation(XSAnnotation annotation, TypeUsage typeUsage, Writer out) throws IOException {
        try {
            final Document doc = this.dfactory.newDocumentBuilder().newDocument();
            annotation.writeAnnotation(doc, XSAnnotation.W3C_DOM_DOCUMENT);

            final NodeList result = (NodeList) this.patternExpr.evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < result.getLength(); i++) {
                final Element patternElt = (Element) result.item(i);
                // the included schematron rules do not have text content in the assert,
                // which should be the human-readable version of the test. we will use
                // the pattern name instead, as it's the most descriptive thing we have
                final String name = patternElt.getAttribute("name");
                // we know each pattern has a single rule and a single assert
                final Element assertElt = (Element) patternElt.getElementsByTagNameNS(SCH_NS, "assert").item(0);
                assertElt.setTextContent(name);
                final String test = assertElt.getAttribute("test");
                final String test2 = test.replaceAll(ELEMENT_REGEXP, "$1hl7:$2");
                assertElt.setAttribute("test", test2);
                final Element ruleElt = (Element) patternElt.getElementsByTagNameNS(SCH_NS, "rule").item(0);
                typeUsage.getRuleIds().add(ruleElt.getAttribute("id"));
                writeNode(ruleElt, out);
                out.write("\n");
            }
        } catch (final XPathExpressionException e) {
            LOG.error("Could not extract rules", e);
        } catch (final ParserConfigurationException e) {
            LOG.error("Could not extract rules", e);
        } catch (final TransformerException e) {
            LOG.error("Could not extract rules", e);
        }
    }

    // fill out the type usage rule lists by adding in rules from supertypes
    // conversely, we need to account for abstract types using xsi:type
    private void postprocessDatatypeExtensions() {
        // first we have to do a pass to deal with a weird bug in xerces: if a type is used
        // before its parent type, its annotations are also placed on its parent type. so
        // do a pass to remove children's rules from their parents. we rely on uniqueness of
        // rule ids here
        for (final QName type : this.datatypeUsages.keySet()) {
            final TypeUsage typeUsage = this.datatypeUsages.get(type); // NOPMD
            for (QName currentType = this.datatypeExtensions.get(type); currentType != null;
            currentType = this.datatypeExtensions.get(currentType)) {
                this.datatypeUsages.get(currentType).getRuleIds().removeAll(typeUsage.getRuleIds());
            }
        }

        // now go ahead and add in the rules of parents, and the contexts of parents (with xsi:type="child type"), to
        // children
        // because we are using sets, we don't care about the order here which would
        // otherwise cause rules from subtypes to get added to supertypes multiple times
        for (final QName type : this.datatypeUsages.keySet()) {
            final TypeUsage typeUsage = this.datatypeUsages.get(type); // NOPMD
            for (QName parentType = this.datatypeExtensions.get(type); parentType != null;
            parentType = this.datatypeExtensions.get(parentType)) {
                final TypeUsage parentUsage = this.datatypeUsages.get(parentType);
                typeUsage.getRuleIds().addAll(parentUsage.getRuleIds());
                for (final String context : parentUsage.getContexts()) {
                    typeUsage.getContexts().add(addXsiType(context, type.getLocalPart()));
                }
            }
        }
    }

    // go back and fill out TypeUsage rulesets for flavors which are being referenced with flavorId
    // this should be done before postprocessDatatypes, since we don't want to include all parent rules
    // just parent flavor rules
    private void postprocessFlavorRules() {
        for (final QName flavorType : this.flavorUsages.keySet()) {
            final TypeUsage flavorUsage = this.flavorUsages.get(flavorType);
            final TypeUsage typeUsage = this.datatypeUsages.get(flavorType);

            // the ruleset for flavor usage is the rules of this flavor type and any parent flavors types
            // (but not non-flavor types - those will be captured as  part of a non-flavor context)
            flavorUsage.getRuleIds().addAll(typeUsage.getRuleIds());
            for (QName parentFlavorType = this.datatypeExtensions.get(flavorType); parentFlavorType != null
            && isFlavor(parentFlavorType); parentFlavorType = this.datatypeExtensions.get(parentFlavorType)) {
                final TypeUsage parentUsage = this.datatypeUsages.get(parentFlavorType);
                flavorUsage.getRuleIds().addAll(parentUsage.getRuleIds());
            }
        }
    }

    // go back and fill out TypeUsage contexts for flavors which are being referenced with flavorId
    // this should be done after postprocessDatatypes, since we want to include the xsi:type contexts
    private void postprocessFlavorContexts() {
        for (final QName flavorType : this.flavorUsages.keySet()) {
            // the contexts for flavor usage are the contexts to the flavor type's immediate non-flavor supertype, which
            // by now will include the xsi:type contexts to that type's parents, with the addition of
            // [@flavorId=flavorType]

            final TypeUsage flavorUsage = this.flavorUsages.get(flavorType);
            final TypeUsage parentNonFlavorTypeUsage = this.datatypeUsages.get(getDatatypeParent(flavorType));

            if (parentNonFlavorTypeUsage == null) {
                throw new IllegalStateException("Flavor type " + flavorType
                        + " has a missing or undefined non-flavor parent type");
            }

            for (final String context : parentNonFlavorTypeUsage.getContexts()) {
                flavorUsage.getContexts().add(addFlavorId(context, flavorType.getLocalPart()));
            }
        }
    }

    private QName getDatatypeParent(QName flavorType) {
        if (flavorType == null || !isFlavor(flavorType)) {
            return flavorType;
        }
        return getDatatypeParent(this.datatypeExtensions.get(flavorType));
    }

    /**
     * Create a new context by appending xsi:type=<type> to the given context
     * 
     * @param context
     * @param type
     * @return
     */
    private String addXsiType(String context, String type) {
        return String.format("%s[@xsi:type and fn:resolve-QName(@xsi:type, self::node())=fn:QName('" + V3_NS
                + "', '%s')]", context, type);
    }

    /**
     * Create a new context by appending flavorId=<type> to the given context
     * 
     * @param context
     * @param type
     * @return
     */
    private String addFlavorId(String context, String type) {
        return String.format("%s[@flavorId='%s']", context, type);
    }

    /**
     * Read the service schema(s) (and included schemas), and build up the set of XPath paths to each element whose type
     * is an ISO datatype.
     * 
     * @param schemasList list of service schemas to process
     * @throws IOException
     */
    private void processServiceSchema(String schemasList) throws IOException {
        if (schemasList == null) {
            return;
        }
        int count = 0;
        for (final String schemaLocation : StringUtils.split(schemasList, ',')) {

            final XSModel schema = this.schemaLoader.loadURI(schemaLocation.trim());
            if (schema == null) {
                throw new IOException("Schema not loaded for URI: " + schemaLocation);
            }
            // populate namespace-prefix map
            final XSNamespaceItemList nsList = schema.getNamespaceItems();
            for (int i = 0; i < nsList.getLength(); i++) {
                final XSNamespaceItem nsItem = nsList.item(i);
                if (!this.nsMap.containsKey(nsItem.getSchemaNamespace())) {
                    this.nsMap.put(nsItem.getSchemaNamespace(), "ns" + count++);
                }
            }

            // traverse the element tree starting from top
            final XSNamedMap elementDecls = schema.getComponents(XSConstants.ELEMENT_DECLARATION);
            final Deque<XSElementDeclaration> eltStack = new LinkedList<XSElementDeclaration>(); // NOPMD
            for (int i = 0; i < elementDecls.getLength(); i++) {
                processElement(eltStack, (XSElementDeclaration) elementDecls.item(i));
            }
        }
    }

    private void processElement(Deque<XSElementDeclaration> eltStack) {
        final XSElementDeclaration elementDecl = eltStack.getLast();
        if (elementDecl.getTypeDefinition().getTypeCategory() == XSTypeDefinition.SIMPLE_TYPE) {
            // not an ISO complex type and no subelements, no further work necessary
            return;
        }

        final XSComplexTypeDefinition elementType = (XSComplexTypeDefinition) elementDecl.getTypeDefinition();
        final QName type = typeQName(elementType);
        if (this.datatypeUsages.containsKey(type)) {
            this.datatypeUsages.get(type).contexts.add(toXPath(eltStack));
            // we support paths to datatype children of datatype elements, but no further
            if (isPreviousElementDatatype(eltStack)) {
                return;
            }
        }
        // If there is recursive nature upto 2 levels
        // like, Type1/Type2/Type1/Type2
        // it is stopped from adding to the stack
        if (isSameElementDatatypePresentInPreviousLevels(eltStack, 2)) {
            // LOG.warn("************* " + toXPath(eltStack)+ " *****************");
            return;
        }
        // recurse into subelements, if any
        if (elementType.getParticle() == null) {
            return;
        } else {
            processParticle(eltStack, elementType.getParticle());
        }
    }

    private QName typeQName(XSTypeDefinition type) {
        return new QName(type.getNamespace(), StringUtils.defaultString(type.getName(), "##ANONYMOUS##"));
    }

    private boolean isPreviousElementDatatype(Deque<XSElementDeclaration> eltStack) {
        final Iterator<XSElementDeclaration> it = eltStack.descendingIterator();
        // skip the tail element
        it.next();
        if (!it.hasNext()) {
            return false;
        }
        final XSTypeDefinition lastType = it.next().getTypeDefinition();
        return this.datatypeUsages.containsKey(typeQName(lastType));
    }

    private boolean isSameElementDatatypePresentInPreviousLevels(Deque<XSElementDeclaration> eltStack,
            int uptoNoOfLevels) {
        final Iterator<XSElementDeclaration> it = eltStack.descendingIterator();
        // skip the tail element
        final XSTypeDefinition checkType = it.next().getTypeDefinition();
        XSTypeDefinition lastType = null;
        for (int i = 0; i < uptoNoOfLevels; i++) {
            if (!it.hasNext()) {
                return false;
            }
            lastType = it.next().getTypeDefinition();
            if (typeQName(checkType).equals(typeQName(lastType))) {
                return true;
            }
        }
        return false;
    }

    private void processElement(Deque<XSElementDeclaration> eltStack, XSElementDeclaration element) {
        eltStack.addLast(element);
        processElement(eltStack);
        eltStack.removeLast();
    }

    private void processParticle(Deque<XSElementDeclaration> eltStack, XSParticle particle) {
        final XSTerm term = particle.getTerm();
        if (term instanceof XSElementDeclaration) {
            processElement(eltStack, (XSElementDeclaration) term);
        } else if (term instanceof XSModelGroup) {
            processModelGroup(eltStack, (XSModelGroup) term);
        }
        // wildcards can be skipped
    }

    private void processModelGroup(Deque<XSElementDeclaration> eltStack, XSModelGroup modelGroup) {
        final XSObjectList particles = modelGroup.getParticles();
        for (int i = 0; i < particles.getLength(); i++) {
            processParticle(eltStack, (XSParticle) particles.item(i));
        }
    }

    private void writeNode(Node node, Writer w) throws TransformerException {
        // Set up an identity transformer to use as serializer.
        this.serializer.transform(new DOMSource(node), new StreamResult(w));
    }

    private String toXPath(Deque<XSElementDeclaration> eltStack) {
        return StringUtils.join(Iterators.transform(eltStack.iterator(), new Function<XSElementDeclaration, String>() {

            @Override
            public String apply(XSElementDeclaration elt) {
                final StringBuilder sb = new StringBuilder();
                if (elt.getNamespace() != null) {
                    sb.append(getNSPrefix(elt.getNamespace()) + ":");
                }
                return sb.append(elt.getName()).toString();
            }
        }), "/");
    }

    private String getNSPrefix(String ns) {
        return this.nsMap.get(ns);
    }

    /**
     * Returns populated NamespaceContext.
     * 
     * @return NamespaceContext
     */
    public NamespaceContext getNamespaceContext() {
        return new SchNamespaceContext();
    }

    // Implementation of namespace resolver for XPath expressions
    private class SchNamespaceContext implements NamespaceContext {

        @Override
        public String getNamespaceURI(String prefix) {
            if (prefix == null) {
                throw new IllegalArgumentException("The prefix cannot be null.");
            }
            return ExtractSchematron.this.nsMap.inverse().get(prefix);
        }

        @Override
        public String getPrefix(String namespace) {
            if (namespace == null) {
                throw new IllegalArgumentException("The namespace uri cannot be null.");
            }
            return ExtractSchematron.this.nsMap.get(namespace);
        }

        @Override
        public Iterator<?> getPrefixes(String namespace) {
            return null;
        }
    }

    // Simple data structure to hold, for an ISO datatype or flavor, its schematron rule ids and XPath paths to elements
    // of that
    // type
    private static class TypeUsage {

        private final Set<String> ruleIds = new LinkedHashSet<String>(40);
        private final Set<String> contexts = new LinkedHashSet<String>(40);

        /**
         * @return list of rule ids for this type
         */
        public Set<String> getRuleIds() {
            return this.ruleIds;
        }

        /**
         * @return list of contexts where type is used
         */
        public Set<String> getContexts() {
            return this.contexts;
        }
    }
}
