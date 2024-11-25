package com.asset.ccat.simulators_service.parser;


import com.asset.ccat.simulators_service.defines.ErrorCodes;
import com.asset.ccat.simulators_service.exceptions.SimulatorsException;
import com.asset.ccat.simulators_service.logger.CCATLogger;
import com.asset.ccat.simulators_service.models.UCIPModel;
import java.io.StringReader;
import java.util.Objects;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * @author Assem.Hassan
 */
@Component
public class RequestParser {

  public static UCIPModel parseRequest(String request) throws SimulatorsException {
    UCIPModel model = new UCIPModel();
    try {
      DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = domFactory.newDocumentBuilder();
      domFactory.setNamespaceAware(true);
      Document doc = builder.parse(new InputSource(new StringReader(request)));
      doc.getXmlVersion();
      doc.getTextContent();
      XPathExpression expr = null;
      XPath xpath = XPathFactory.newInstance().newXPath();

      domFactory = DocumentBuilderFactory.newInstance();
      domFactory.setNamespaceAware(true);

      XPathFactory factory = XPathFactory.newInstance();
      xpath = factory.newXPath();
      String methodName = xpath.evaluate("//methodName", doc).trim();
      model.setMethod(methodName);




      expr = xpath.compile("//member[name='subscriberNumber']/value/string/text()");
      Object result = expr.evaluate(doc, XPathConstants.STRING);

      String msisdn = (String) result;
      model.setMsisdn(msisdn);


      expr = xpath.compile("//member[name='languageIDNew']/value/int/text()");
      Object languageIdResult = expr.evaluate(doc, XPathConstants.STRING);
      if(Objects.nonNull(languageIdResult)&&languageIdResult!=""){
        String strLanguageId = languageIdResult.toString();
        model.setLanguageId(strLanguageId);
      }



      expr = xpath.compile("//member[name='serviceClassNew']/value/i4/text()");
      Object serviceClassIdResult = expr.evaluate(doc, XPathConstants.STRING);
      if(Objects.nonNull(serviceClassIdResult)&&serviceClassIdResult!=""){
        String serviceClassIdStr = serviceClassIdResult.toString();
        model.setServiceClassId(serviceClassIdStr);
      }

      expr = xpath.compile("//member[name='adjustmentAmountRelative']/value/string/text()");
      Object adjustmentAmount = expr.evaluate(doc, XPathConstants.STRING);
      if(Objects.nonNull(adjustmentAmount)&&adjustmentAmount!=""){
        String adjustmentAmountStr = adjustmentAmount.toString();
        model.setAdjustmentAmount(adjustmentAmountStr);
      }





      CCATLogger.DEBUG_LOGGER.info("Method Name :  [" + methodName + "]");
//
//      expr = xpath.compile("//member/name/text()");
//      result = expr.evaluate(doc, XPathConstants.NODESET);
//      NodeList nodes = (NodeList) result;
//      for (int i = 0; i < nodes.getLength(); i++) {
//        XPathExpression checkExpr = xpath.compile(
//            "count(//member[name='" + nodes.item(i).getNodeValue() + "']/value/array)>0");
//        Boolean isArray = (Boolean) checkExpr.evaluate(doc, XPathConstants.BOOLEAN);
//        if (isArray.booleanValue()) {
//          String paramName = nodes.item(i).getNodeValue().trim();
//          System.out.println(paramName + " Array");
//        } else {
//          String paramName = nodes.item(i).getNodeValue().trim();
//          System.out.print(paramName + "  : ");
//          XPathExpression exp = null;
//          XPathFactory factory2 = XPathFactory.newInstance();
//          XPath xpath2 = XPathFactory.newInstance().newXPath();
//          xpath2 = factory.newXPath();
//          exp = xpath2.compile("//member[name='" + nodes.item(i).getNodeValue() + "']/value");
//          String result2 = (String) exp.evaluate(doc, XPathConstants.STRING);
//          result2 = result2.trim();
//          System.out.println(result2);
//        }

//      }
    } catch (Exception ex) {
      CCATLogger.DEBUG_LOGGER.info("Failed to parse request: " + request);
      throw new SimulatorsException(ErrorCodes.ERROR.PARSING_FAILED);
    }
    return model;
  }
}