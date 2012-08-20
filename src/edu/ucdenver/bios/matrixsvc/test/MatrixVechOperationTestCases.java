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

import edu.ucdenver.bios.matrixsvc.resource.MatrixVechServerResource;
import edu.ucdenver.bios.webservice.common.domain.NamedMatrix;
import edu.ucdenver.bios.webservice.common.domain.NamedMatrixList;
/**
 * TestCase for Matrix Vech Operation
 * @author VIJAY AKULA
 *
 */
public class MatrixVechOperationTestCases extends TestCase{
    NamedMatrixList matrixList = new NamedMatrixList();
    NamedMatrixList resultMatrixList = new NamedMatrixList();
    int rows;
    int column;
    private boolean verbose = false;
    private static String DATA_FILE = "data" + File.separator + "Vech Operation Data.xml";
    private static final String OUTPUT_FILE = "Reports"+ File.separator + "Vech Matrix Operation Results.html";
    private static final String TITLE = "Matrix Vech Operation Results";
    
    
    StringBuffer buffer = new StringBuffer();
    
    NodeList nodes;
    
    public void setUp()
    {

    }
    
    public void testVechOperation()
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
                Node m2r1c0 = attrs.getNamedItem("m2r1c0");
                Node m2r2c0 = attrs.getNamedItem("m2r2c0");
                Node m2r3c0 = attrs.getNamedItem("m2r3c0");
                Node m2r4c0 = attrs.getNamedItem("m2r4c0");
                Node m2r5c0 = attrs.getNamedItem("m2r5c0");
                
                
               
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
                
                double[][] data = new double[6][1];
                
                data[0][0] = Double.parseDouble(m2r0c0.getNodeValue());
                        
                data[1][0] = Double.parseDouble(m2r1c0.getNodeValue());
    
                data[2][0] = Double.parseDouble(m2r2c0.getNodeValue());

                data[3][0] = Double.parseDouble(m2r3c0.getNodeValue());
  
                data[4][0] = Double.parseDouble(m2r4c0.getNodeValue());
  
                data[5][0] = Double.parseDouble(m2r5c0.getNodeValue());
                
                
                expectedMatrix.setDataFromArray(data);
                
                
                matrixList.add(namedMatrix);
                matrixList.add(expectedMatrix);
                
                NamedMatrix result = new NamedMatrix();
                
                MatrixVechServerResource resource = new MatrixVechServerResource();
                
                result = resource.vech(namedMatrix);
                        
                resultMatrixList.add(result);
                double[][] resultData = result.getData().getData();
                
                assertEquals(data[0][0], resultData[0][0], 0.00000000001);
                assertEquals(data[1][0], resultData[1][0], 0.00000000001);
                assertEquals(data[2][0], resultData[2][0], 0.00000000001);
                assertEquals(data[3][0], resultData[3][0], 0.00000000001);
                assertEquals(data[4][0], resultData[4][0], 0.00000000001);
                assertEquals(data[5][0], resultData[5][0], 0.00000000001);
                
                
                if(verbose)
                {
                    outputResults(TITLE, OUTPUT_FILE, "");    
                }
            }
            
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException("Parsing of XML file failed: " + e.getMessage());
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
            
            double data[][] = result.getData().getData(); 
            
            
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
            Node m2r1c0 = attrs.getNamedItem("m2r1c0");
            Node m2r2c0 = attrs.getNamedItem("m2r2c0");
            Node m2r3c0 = attrs.getNamedItem("m2r3c0");
            Node m2r4c0 = attrs.getNamedItem("m2r4c0");
            Node m2r5c0 = attrs.getNamedItem("m2r5c0");
            

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
            double value2 = Double.parseDouble(m2r1c0.getNodeValue().trim());
            double value3 = Double.parseDouble(m2r2c0.getNodeValue().trim());
            double value4 = Double.parseDouble(m2r3c0.getNodeValue().trim());
            double value5 = Double.parseDouble(m2r4c0.getNodeValue().trim());
            double value6 = Double.parseDouble(m2r5c0.getNodeValue().trim());
            
            buffer.append("<td><table border='1' cellpadding='5'>" +
                    "<tr><td>"+value1+"</td></tr>"+
                    "<tr><td>"+value2+"</td></tr>"+
                    "<tr><td>"+value3+"</td></tr>"+
                    "<tr><td>"+value4+"</td></tr>"+
                    "<tr><td>"+value5+"</td></tr>"+
                    "<tr><td>"+value6+"</td></tr>"+
                    "</table>");
            
            buffer.append("<td><table border='1' cellpadding='5'>" +
                    "<tr><td>"+data[0][0]+"</tr>"+
                    "<tr><td>"+data[1][0]+"</tr>"+
                    "<tr><td>"+data[2][0]+"</tr>"+
                    "<tr><td>"+data[3][0]+"</tr>"+
                    "<tr><td>"+data[4][0]+"</tr>"+
                    "<tr><td>"+data[5][0]+"</tr>"+
                    "</table>");
            
            
            
            if(Math.abs(value1-data[0][0])<0.00000000001 && Math.abs(value2-data[1][0])<0.00000000001 && Math.abs(value3-data[2][0])<0.00000000001
                    && Math.abs(value4-data[3][0])<0.00000000001 && Math.abs(value5-data[4][0])<0.00000000001 && Math.abs(value6-data[5][0])<0.00000000001)
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
