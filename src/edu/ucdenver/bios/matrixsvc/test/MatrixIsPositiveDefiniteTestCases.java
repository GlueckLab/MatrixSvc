package edu.ucdenver.bios.matrixsvc.test;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.TestCase;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import edu.ucdenver.bios.matrixsvc.resource.MatrixIsPositiveDefiniteServerResource;
import edu.ucdenver.bios.webservice.common.domain.NamedMatrix;
import edu.ucdenver.bios.webservice.common.domain.NamedMatrixList;

public class MatrixIsPositiveDefiniteTestCases extends TestCase
{
    NamedMatrixList matrixList = new NamedMatrixList();
    String [] boolaenArray = new String[5];
    int rows;
    int column;
    private boolean verbose = false;
    private static String DATA_FILE = "data" + File.separator + "isPositiveDefinite Data.xml";
    private static final String OUTPUT_FILE = "Reports"+ File.separator + "Positive Definite Matrix Operation Results.html";
    private static final String TITLE = "Matrix Positive Defintie Operation Results";
        
    StringBuffer buffer = new StringBuffer();
    
    NodeList nodes;
    
    public void setUp()
    {
       rows = 2; 
       column = 2;
    }
    
    @Test
    public void testIsPostiveDefiniteOperation()
    {
        FileReader reader = null;
        try
        {
            
            DocumentBuilderFactory factory =  DocumentBuilderFactory.newInstance();
            
            DocumentBuilder builder = factory.newDocumentBuilder();
            reader = new FileReader(DATA_FILE);
            Document doc = builder.parse(new InputSource(reader));
            
            nodes = doc.getElementsByTagName("matrix");
            
            for(int i = 0; i < nodes.getLength(); i++)
            {
                if(i == nodes.getLength()-1) 
                { 
                    verbose = true;
                }
                
                Node matrix = nodes.item(i);
                NamedNodeMap attrs = matrix.getAttributes();
                
               
                
                Node m1r0c0 = attrs.getNamedItem("m1r0c0");
                Node m1r0c1 = attrs.getNamedItem("m1r0c1");
                Node m1r1c0 = attrs.getNamedItem("m1r1c0");
                Node m1r1c1 = attrs.getNamedItem("m1r1c1");
                Node result = attrs.getNamedItem("isPositiveDefinite");
                
               
                NamedMatrix namedMatrix = new NamedMatrix();

                double[][] data = new double[2][2];
                
                data[0][0] = Double.parseDouble(m1r0c0.getNodeValue());
                
                data[0][1] = Double.parseDouble(m1r0c1.getNodeValue());
                
                data[1][0] = Double.parseDouble(m1r1c0.getNodeValue());
               
                data[1][1] = Double.parseDouble(m1r1c1.getNodeValue());
             
                namedMatrix.setDataFromArray(data);
                
                matrixList.add(namedMatrix);
                
                MatrixIsPositiveDefiniteServerResource resource = new MatrixIsPositiveDefiniteServerResource();
                
                boolean isPositiveDefinite = resource
                        .isPositiveDefinite(namedMatrix);
                
                boolean flag = Boolean.parseBoolean(result.getNodeValue());
                
                assertEquals(flag, isPositiveDefinite);
                boolaenArray[i] = result.getNodeValue();
                if(verbose)
                {
                    outputResults(TITLE, OUTPUT_FILE, "");    
                }
            }
            
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException("Parsing of XML failed: " + e.getMessage());
        }
    }
    
    public void outputResults(String title, String filename, String imageFilename)
    {

        
        buffer.append("<html><head></head><body><h1>" + title + "</h1>");
        buffer.append("<table border='2' cellpadding='10'><tr><th>Test Case</th>");
        buffer.append("<th>Matrix</th>");
        buffer.append("<th>Expected</th>");
        buffer.append("<th>Output</th>");
        buffer.append("<th>Result</th>");
        
        for(int i = 0; i < boolaenArray.length; i++)
        {
            Boolean result = Boolean.parseBoolean(boolaenArray[i]);
            
            Node matrix1 = nodes.item(i);
            NamedNodeMap attrs = matrix1.getAttributes();
            
            
            Node m1r0c0 = attrs.getNamedItem("m1r0c0");
            Node m1r0c1 = attrs.getNamedItem("m1r0c1");
            Node m1r1c0 = attrs.getNamedItem("m1r1c0");
            Node m1r1c1 = attrs.getNamedItem("m1r1c1");
            Node result1 = attrs.getNamedItem("isPositiveDefinite");
            
            boolean flag = Boolean.parseBoolean(result1.getNodeValue());
            
            
            
            buffer.append("<tr>");
            buffer.append("<td>Test Case "+(i+1)+"</td>");
            buffer.append("<td><table border='1' cellpadding='5'>" +
                    "<tr>" +
                    "<td>"+Double.parseDouble(m1r0c0.getNodeValue().trim())+"</td>" +
                    "<td>"+Double.parseDouble(m1r0c1.getNodeValue().trim())+"</td></tr>"+
                    "<tr>" +
                    "<td>"+Double.parseDouble(m1r1c0.getNodeValue().trim())+"</td>" +
                    "<td>"+Double.parseDouble(m1r1c1.getNodeValue().trim())+"</td></tr></table>");

            buffer.append("<td>"+flag+"</td>");
            
            buffer.append("<td>"+result+"</td>");
                      
           
            if(flag == result)
            {
                buffer.append("<td>Passed</td>");
            }
            else
            {
                buffer.append("<td>Failed</td>");
            }
            buffer.append("</tr>");         
        }
        
        buffer.append("</table><p>");
        
        if (imageFilename != null)
        {
            buffer.append("<p><img src='" + imageFilename + "' /></p>");
        }
        
        buffer.append("</body></html>");
        FileWriter writer = null;
        try
        {
            writer = new FileWriter(filename);
            writer.write(buffer.toString());
        }
        
        catch (Exception e)
        {
            
        }
        finally
        {
            if (writer != null) 
        try 
            {
                writer.close(); 
            } 
            
        catch (Exception e) 
            {
                
            };
        }
    }
}
