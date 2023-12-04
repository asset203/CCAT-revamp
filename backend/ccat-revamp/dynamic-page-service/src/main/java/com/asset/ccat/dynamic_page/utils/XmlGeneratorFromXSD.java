package com.asset.ccat.dynamic_page.utils;

import com.asset.ccat.dynamic_page.defines.Defines;
import com.asset.ccat.dynamic_page.defines.ErrorCodes;
import com.asset.ccat.dynamic_page.exceptions.DynamicPageException;
import com.asset.ccat.dynamic_page.logger.CCATLogger;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.SchemaTypeSystem;
import org.apache.xmlbeans.XmlBeans;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.impl.xsd2inst.SampleXmlUtil;
import org.apache.xmlbeans.impl.xsd2inst.SchemaInstanceGenerator;

import java.util.ArrayList;
import java.util.List;

public class XmlGeneratorFromXSD {

    /**
     * Specifies if network downloads are enabled for imports and includes.
     * Default value is {@code false}
     */
    private static final boolean ENABLE_NETWORK_DOWNLOADS = false;

    /**
     * disable particle valid (restriction) rule
     * Default value is {@code false}
     */
    private static final boolean NO_PVR = false;

    /**
     * disable unique particle attribution rule.
     * Default value is {@code false}
     */
    private static final boolean NO_UPA = false;


    public String generateXmlInstance(String xsdAsString, String elementToGenerate) throws DynamicPageException {
        return generateXmlInstance(xsdAsString, elementToGenerate, ENABLE_NETWORK_DOWNLOADS, NO_PVR, NO_UPA);
    }


    public String generateXmlInstance(String xsAsString, String elementToGenerate, boolean enableDownloads,
                                      boolean noPvr, boolean noUpa) throws DynamicPageException {
        List<XmlObject> schemaXmlObjects = new ArrayList<>();
        try {
            schemaXmlObjects.add(XmlObject.Factory.parse(xsAsString));
        } catch (XmlException ex) {
            CCATLogger.DEBUG_LOGGER.error("Failed to parse from XSD to XML", ex);
            throw new DynamicPageException(ErrorCodes.ERROR.PARSE_PARAMETERS_QUERY_FAILED, Defines.SEVERITY.ERROR);
        }
        XmlObject[] xmlObjects = schemaXmlObjects.toArray(new XmlObject[1]);
        SchemaInstanceGenerator.Xsd2InstOptions options = new SchemaInstanceGenerator.Xsd2InstOptions();
        options.setNetworkDownloads(enableDownloads);
        options.setNopvr(noPvr);
        options.setNoupa(noUpa);
        return xsd2inst(xmlObjects, elementToGenerate, options);
    }

    private String xsd2inst(XmlObject[] schemas, String rootName, SchemaInstanceGenerator.Xsd2InstOptions options) throws DynamicPageException {
        SchemaTypeSystem schemaTypeSystem = null;
        if (schemas.length > 0) {
            XmlOptions compileOptions = new XmlOptions();
            if (options.isNetworkDownloads())
                compileOptions.setCompileDownloadUrls();
            if (options.isNopvr())
                compileOptions.setCompileNoPvrRule();
            if (options.isNoupa())
                compileOptions.setCompileNoUpaRule();
            try {
                schemaTypeSystem = XmlBeans.compileXsd(schemas, XmlBeans.getBuiltinTypeSystem(), compileOptions);
            } catch (XmlException ex) {
                CCATLogger.DEBUG_LOGGER.error("Failed to parse from XSD to XML", ex);
                throw new DynamicPageException(ErrorCodes.ERROR.PARSE_PARAMETERS_QUERY_FAILED, Defines.SEVERITY.ERROR);
            }
        }
        if (schemaTypeSystem == null) {
            CCATLogger.DEBUG_LOGGER.error("Failed to parse from XSD to XML No SchemaType");
            throw new DynamicPageException(ErrorCodes.ERROR.PARSE_PARAMETERS_QUERY_FAILED, Defines.SEVERITY.ERROR);
        }

        SchemaType[] globalElements = schemaTypeSystem.documentTypes();
        SchemaType elem = null;
        for (SchemaType globalElement : globalElements) {
            if (rootName.equals(globalElement.getDocumentElementName().getLocalPart())) {
                elem = globalElement;
                break;
            }
        }
        if (elem == null) {
            CCATLogger.DEBUG_LOGGER.error("Could not find a global element with name \"" + rootName + "\"");
            throw new DynamicPageException(ErrorCodes.ERROR.PARSE_PARAMETERS_QUERY_FAILED, Defines.SEVERITY.ERROR);
        }
        // Now generate it and return the result
        return SampleXmlUtil.createSampleForType(elem);
    }
}
