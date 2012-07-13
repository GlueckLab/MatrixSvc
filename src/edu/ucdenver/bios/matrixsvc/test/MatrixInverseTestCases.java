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

import edu.ucdenver.bios.matrixsvc.resource.MatrixInversionServerResource;
import edu.ucdenver.bios.webservice.common.domain.NamedMatrix;
import edu.ucdenver.bios.webservice.common.domain.NamedMatrixList;

public class MatrixInverseTestCases extends TestCase
{
    NamedMatrixList matrixList = new NamedMatrixList();
    NamedMatrixList resultMatrixList = new NamedMatrixList();
    int rows;
    int column;
    private boolean verbose = false;
    private static String DATA_FILE = "data" + File.separator + "Inverse Matrix Data.xml";
    private static final String OUTPUT_FILE = "Reports"+ File.separator + "Inverse Matrix Operation Results.html";
    private static final String TITLE = "Matrix Inverse Operation Results";
    
    
    StringBuffer buffer = new StringBuffer();
    
    NodeList nodes;
    
    public void setUp()
    {
       rows = 2; 
       column = 2;
    }
    
    @Test
    public void testMatrixInverseOperation()
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
                Node m1r0c2 = attrs.getNamedItem("m1r0c2");
                Node m1r1c0 = attrs.getNamedItem("m1r1c0");
                Node m1r1c1 = attrs.getNamedItem("m1r1c1");
                Node m1r1c2 = attrs.getNamedItem("m1r1c2");
                Node m1r2c0 = attrs.getNamedItem("m1r2c0");
                Node m1r2c1 = attrs.getNamedItem("m1r2c1");
                Node m1r2c2 = attrs.getNamedItem("m1r2c2");

                
                Node m2r0c0 = attrs.getNamedItem("m2r0c0");
                Node m2r0c1 = attrs.getNamedItem("m2r0c1");
                Node m2r0c2 = attrs.getNamedItem("m2r0c2");
                Node m2r1c0 = attrs.getNamedItem("m2r1c0");
                Node m2r1c1 = attrs.getNamedItem("m2r1c1");
                Node m2r1c2 = attrs.getNamedItem("m2r1c2");
                Node m2r2c0 = attrs.getNamedItem("m2r2c0");
                Node m2r2c1 = attrs.getNamedItem("m2r2c1");
                Node m2r2c2 = attrs.getNamedItem("m2r2c2");
                
                
               
                NamedMatrix namedMatrix = new NamedMatrix();

                double[][] data1 = new double[3][3];
                
                data1[0][0] = Double.parseDouble(m1r0c0.getNodeValue());
                
                data1[0][1] = Double.parseDouble(m1r0c1.getNodeValue());
                
                data1[0][2] = Double.parseDouble(m1r0c2.getNodeValue());
                
                data1[1][0] = Double.parseDouble(m1r1c0.getNodeValue());
                
                
                
                data1[1][1] = Double.parseDouble(m1r1c1.getNodeValue());
                
                data1[1][2] = Double.parseDouble(m1r1c2.getNodeValue());
                
                data1[2][0] = Double.parseDouble(m1r2c0.getNodeValue());
                
                data1[2][1] = Double.parseDouble(m1r2c1.getNodeValue());
                
                data1[2][2] = Double.parseDouble(m1r2c2.getNodeValue());

                namedMatrix.setDataFromArray(data1);
                
                
                
                
                
                NamedMatrix expectedMatrix = new NamedMatrix();
                
                double[][] data = new double[3][3];
                
                data[0][0] = Double.parseDouble(m2r0c0.getNodeValue());
                        
                data[0][1] = Double.parseDouble(m2r0c1.getNodeValue());
    
                data[0][2] = Double.parseDouble(m2r0c2.getNodeValue());

                data[1][0] = Double.parseDouble(m2r1c0.getNodeValue());
  
                data[1][1] = Double.parseDouble(m2r1c1.getNodeValue());
  
                data[1][2] = Double.parseDouble(m2r1c2.getNodeValue());
                
                data[2][0] = Double.parseDouble(m2r2c0.getNodeValue());
                
                data[2][1] = Double.parseDouble(m2r2c1.getNodeValue());
                
                data[2][2] = Double.parseDouble(m2r2c2.getNodeValue());
                
                expectedMatrix.setDataFromArray(data);
                
                
                matrixList.add(namedMatrix);
                matrixList.add(expectedMatrix);
                
                NamedMatrix result = new NamedMatrix();
                
                MatrixInversionServerResource resource = new MatrixInversionServerResource();
                
                result = resource.invert(namedMatrix);
               
                
                resultMatrixList.add(result);
                double[][] resultData = result.getDataAsArray();
                
                assertEquals(data[0][0], resultData[0][0], 0.00000000001);
                assertEquals(data[0][1], resultData[0][1], 0.00000000001);
                assertEquals(data[0][2], resultData[0][2], 0.00000000001);
                assertEquals(data[1][0], resultData[1][0], 0.00000000001);
                assertEquals(data[1][1], resultData[1][1], 0.00000000001);
                assertEquals(data[1][2], resultData[1][2], 0.00000000001);
                assertEquals(data[2][0], resultData[2][0], 0.00000000001);
                assertEquals(data[2][1], resultData[2][1], 0.00000000001);
                assertEquals(data[2][2], resultData[2][2], 0.00000000001);
                
                
                if(verbose)
                {
                    outputResults(TITLE, OUTPUT_FILE, "");    
                }
            }
            
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException("Parsing of SAS XML failed: " + e.getMessage());
        }
    }
    
    public void outputResults(String title, String filename, String imageFilename)
    {

        
        buffer.append("<html><head></head><body><h1>" + title + "</h1>");
        buffer.append("<table border='2' cellpadding='10'><tr><th>Test Case</th>");
        buffer.append("<th>Matrix 1</th>");
        buffer.append("<th>Expected Matrix</th>");
        buffer.append("<th>Output Matrix</th>");
        buffer.append("<th>Result</th>");
        
        for(int i = 0; i < resultMatrixList.size(); i++)
        {
            
            NamedMatrix result = resultMatrixList.get(i);
            
            double data[][] = result.getDataAsArray(); 
            
            
            Node matrix1 = nodes.item(i);
            NamedNodeMap attrs = matrix1.getAttributes();
            
            Node m1r0c0 = attrs.getNamedItem("m1r0c0");
            Node m1r0c1 = attrs.getNamedItem("m1r0c1");
            Node m1r0c2 = attrs.getNamedItem("m1r0c2");
            Node m1r1c0 = attrs.getNamedItem("m1r1c0");
            Node m1r1c1 = attrs.getNamedItem("m1r1c1");
            Node m1r1c2 = attrs.getNamedItem("m1r1c2");
            Node m1r2c0 = attrs.getNamedItem("m1r2c0");
            Node m1r2c1 = attrs.getNamedItem("m1r2c1");
            Node m1r2c2 = attrs.getNamedItem("m1r2c2");

            
            Node m2r0c0 = attrs.getNamedItem("m2r0c0");
            Node m2r0c1 = attrs.getNamedItem("m2r0c1");
            Node m2r0c2 = attrs.getNamedItem("m2r0c2");
            Node m2r1c0 = attrs.getNamedItem("m2r1c0");
            Node m2r1c1 = attrs.getNamedItem("m2r1c1");
            Node m2r1c2 = attrs.getNamedItem("m2r1c2");
            Node m2r2c0 = attrs.getNamedItem("m2r2c0");
            Node m2r2c1 = attrs.getNamedItem("m2r2c1");
            Node m2r2c2 = attrs.getNamedItem("m2r2c2");
            

            buffer.append("<tr>");
            buffer.append("<td>Test Case "+(i+1)+"</td>");
            buffer.append("<td><table border='1' cellpadding='5'>" +
            		"<tr>" +
            		"<td>"+Double.parseDouble(m1r0c0.getNodeValue().trim())+"</td>" +
                    "<td>"+Double.parseDouble(m1r0c1.getNodeValue().trim())+"</td>" +
                    "<td>"+Double.parseDouble(m1r0c2.getNodeValue().trim())+"</td></tr>"+
                    "<tr>" +
                    "<td>"+Double.parseDouble(m1r1c0.getNodeValue().trim())+"</td>" +
                    "<td>"+Double.parseDouble(m1r1c1.getNodeValue().trim())+"</td>" +
                    "<td>"+Double.parseDouble(m1r1c2.getNodeValue().trim())+"</td></tr>"+
                    "<tr>" +
                    "<td>"+Double.parseDouble(m1r2c0.getNodeValue().trim())+"</td>" +
                    "<td>"+Double.parseDouble(m1r2c1.getNodeValue().trim())+"</td>" +
                    "<td>"+Double.parseDouble(m1r2c2.getNodeValue().trim())+"</td></tr></table>");
            
     
            
            double value1 = Double.parseDouble(m2r0c0.getNodeValue().trim());
            double value2 = Double.parseDouble(m2r0c1.getNodeValue().trim());
            double value3 = Double.parseDouble(m2r0c2.getNodeValue().trim());
            double value4 = Double.parseDouble(m2r1c0.getNodeValue().trim());
            double value5 = Double.parseDouble(m2r1c1.getNodeValue().trim());
            double value6 = Double.parseDouble(m2r1c2.getNodeValue().trim());
            double value7 = Double.parseDouble(m2r2c0.getNodeValue().trim());
            double value8 = Double.parseDouble(m2r2c1.getNodeValue().trim());
            double value9 = Double.parseDouble(m2r2c2.getNodeValue().trim());
            
            
            buffer.append("<td><table border='1' cellpadding='5'>" +
                    "<tr>" +
                    "<td>"+value1+"</td>" +
                    "<td>"+value2+"</td>" +
                    "<td>"+value3+"</td></tr>"+
                    "<tr>" +
                    "<td>"+value4+"</td>" +
                    "<td>"+value5+"</td>" +
                    "<td>"+value6+"</td></tr>"+
                    "<tr>" +
                    "<td>"+value7+"</td>" +
                    "<td>"+value8+"</td>" +
                    "<td>"+value9+"</td></tr></table>");
            
            buffer.append("<td><table border='1' cellpadding='5'>" +
                    "<tr>" +
                    "<td>"+data[0][0]+"</td>" +
                    "<td>"+data[0][1]+"</td>"+
                    "<td>"+data[0][2]+"</td>"+
                    "</tr><tr>" +
                    "<td>"+data[1][0]+"</td>" +
                    "<td>"+data[1][1]+"</td>"+
                    "<td>"+data[1][2]+"</td>"+
                    "</tr><tr>" +
                    "<td>"+data[2][0]+"</td>" +
                    "<td>"+data[2][1]+"</td>"+
                    "<td>"+data[2][2]+"</td>"+
                    "</tr></table>");
            
            
            
            if(Math.abs(value1-data[0][0])<0.00000000001 && Math.abs(value2-data[0][1])<0.00000000001 && Math.abs(value3-data[0][2])<0.00000000001
                    && Math.abs(value4-data[1][0])<0.00000000001 && Math.abs(value5-data[1][1])<0.00000000001 && Math.abs(value6-data[1][2])<0.00000000001 
                    && Math.abs(value7-data[2][0])<0.00000000001 && Math.abs(value8-data[2][1])<0.00000000001 && Math.abs(value9-data[2][2])<0.00000000001)
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
