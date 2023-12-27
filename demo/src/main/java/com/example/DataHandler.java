package com.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.xml.parsers.DocumentBuilder;
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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DataHandler {
    public static String getData(String value) {
        String result = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document dom = builder.parse(DataHandler.class.getResourceAsStream("/data/user.xml"));

            Element doc = dom.getDocumentElement();
            NodeList nl = doc.getElementsByTagName(value);
            if (nl.getLength() > 0 && nl.item(0).hasChildNodes()) {
                result = nl.item(0).getFirstChild().getNodeValue();
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        
        return result;
    }



    public static void setData(String theme, String language, String quality, String port) {
        Document dom;
        Element e = null;

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            dom = db.newDocument();

            Element rootEle = dom.createElement("User");

            e = dom.createElement("theme");
            e.appendChild(dom.createTextNode(theme));
            rootEle.appendChild(e);

            e = dom.createElement("language");
            e.appendChild(dom.createTextNode(language));
            rootEle.appendChild(e);

            e = dom.createElement("quality");
            e.appendChild(dom.createTextNode(quality));
            rootEle.appendChild(e);

            e = dom.createElement("port");
            e.appendChild(dom.createTextNode(port));
            rootEle.appendChild(e);

            dom.appendChild(rootEle);

            Transformer tr = TransformerFactory.newInstance().newTransformer();
            tr.setOutputProperty(OutputKeys.INDENT, "yes");
            tr.setOutputProperty(OutputKeys.METHOD, "xml");
            tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            File dataFile = new File(DataHandler.class.getResource("/data/user.xml").toURI());
            tr.transform(new DOMSource(dom), new StreamResult(new FileOutputStream(dataFile)));

        } catch (ParserConfigurationException e1) {
            e1.printStackTrace();
        } catch (TransformerConfigurationException e1) {
            e1.printStackTrace();
        } catch (TransformerFactoryConfigurationError e1) {
            e1.printStackTrace();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (TransformerException e1) {
            e1.printStackTrace();
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
        }
    }
}
