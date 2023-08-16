package org.example;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DependencyInjector {
    public static void writeInXMLFile(Document doc, File inputFile){
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes"); // Indentar a saída
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(inputFile);
            transformer.transform(source, result);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void insertBuildconfig(File inputFile){
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);

            Element pluginElement = doc.createElement("plugin");
            Element groupIdElement = doc.createElement("groupId");
            groupIdElement.appendChild(doc.createTextNode("org.apache.maven.plugins"));
            Element artifactIdElement = doc.createElement("artifactId");
            artifactIdElement.appendChild(doc.createTextNode("maven-surefire-plugin"));
            Element versionElement = doc.createElement("version");
            versionElement.appendChild(doc.createTextNode("2.19.1"));
            Element configurationElement = doc.createElement("configuration");
            Element includesElement = doc.createElement("includes");
            Element includeElement = doc.createElement("include");
            includeElement.appendChild(doc.createTextNode("**/FASTPrioritizedSuite.java"));
            includesElement.appendChild(includeElement);
            configurationElement.appendChild(includesElement);
            pluginElement.appendChild(groupIdElement);
            pluginElement.appendChild(artifactIdElement);
            pluginElement.appendChild(versionElement);
            pluginElement.appendChild(configurationElement);

            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xPath = xPathFactory.newXPath();

            String xpathExpression = "//build//plugins";
            NodeList nodeList = (NodeList) xPath.evaluate(xpathExpression, doc, XPathConstants.NODESET);

            Element element = (Element) nodeList.item(0);
            element.appendChild(pluginElement);

            writeInXMLFile(doc,inputFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Element createDependencyElement(Document doc, String groupId, String artifactId, String version, String scope) {
        Element dependencyElement = doc.createElement("dependency");

        Element groupIdElement = doc.createElement("groupId");
        groupIdElement.appendChild(doc.createTextNode(groupId));
        dependencyElement.appendChild(groupIdElement);

        Element artifactIdElement = doc.createElement("artifactId");
        artifactIdElement.appendChild(doc.createTextNode(artifactId));
        dependencyElement.appendChild(artifactIdElement);

        Element versionElement = doc.createElement("version");
        versionElement.appendChild(doc.createTextNode(version));
        dependencyElement.appendChild(versionElement);

        Element scopeElement = doc.createElement("scope");
        scopeElement.appendChild(doc.createTextNode(scope));
        dependencyElement.appendChild(scopeElement);

        return dependencyElement;
    }
    public static void insertDependenciesConfig(File inputFile){
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);

            // Crie o elemento <dependencies> como raiz
            //Element dependenciesElement = doc.createElement("dependencies");
            //doc.appendChild(dependenciesElement);

            Element dependencyElement1 = createDependencyElement(doc, "org.junit.jupiter", "junit-jupiter", "5.8.1", "test");
            Element dependencyElement2 = createDependencyElement(doc, "org.junit.platform", "junit-platform-runner", "1.5.2", "test");

            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xPath = xPathFactory.newXPath();

            String xpathExpression = "//dependencies"; // Expressão XPath
            NodeList nodeList = (NodeList) xPath.evaluate(xpathExpression, doc, XPathConstants.NODESET);

            Element element = (Element) nodeList.item(0);
            element.appendChild(dependencyElement1);
            element.appendChild(dependencyElement2);

            writeInXMLFile(doc,inputFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void removeBlankLines(Node node) {
        NodeList childNodes = node.getChildNodes();
        for (int i = childNodes.getLength() - 1; i >= 0; i--) {
            Node child = childNodes.item(i);
            if (child.getNodeType() == Node.TEXT_NODE && child.getNodeValue().trim().isEmpty()) {
                node.removeChild(child);
            } else if (child.getNodeType() == Node.ELEMENT_NODE) {
                removeBlankLines(child);
            }
        }
    }
    public static void insertRatConfig(File inputFile){
        try{
            FileInputStream inputStream = new FileInputStream(inputFile);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputStream);

            NodeList pluginNodes = document.getElementsByTagName("plugin");
            for (int i = 0; i < pluginNodes.getLength(); i++) {
                Element pluginElement = (Element) pluginNodes.item(i);
                Element artifactIdElement = (Element) pluginElement.getElementsByTagName("artifactId").item(0);
                String artifactId = artifactIdElement.getTextContent();

                if ("apache-rat-plugin".equals(artifactId)) {
                    NodeList existingExcludes = pluginElement.getElementsByTagName("excludes");
                    Element element = (Element) existingExcludes.item(0);

                    String newExclude = ".fast/**";
                    Element excludeElement = document.createElement("exclude");
                    excludeElement.appendChild(document.createTextNode(newExclude));

                    String newExclude2 = "src/test/java/fast/**";
                    Element excludeElement2 = document.createElement("exclude");
                    excludeElement2.appendChild(document.createTextNode(newExclude2));

                    element.appendChild(excludeElement);
                    element.appendChild(excludeElement2);
                }
            }
            writeInXMLFile(document,inputFile);

        }catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

    }
    public static void main(String[] args) {
        try {
            File inputFile = new File("C:\\Injecao-dependencias-poc\\src\\main\\resources\\pom.xml");

            insertBuildconfig(inputFile);
            insertDependenciesConfig(inputFile);
            insertRatConfig(inputFile);

            FileInputStream inputStream = new FileInputStream(inputFile);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputStream);

            removeBlankLines(document.getDocumentElement());
            writeInXMLFile(document,inputFile);
//          for (int i = 0; i < nodeList.getLength(); i++) {
//                Element element = (Element) nodeList.item(i);
//                System.out.println("Elemento correspondente encontrado: " + element.getTagName());
//          }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
