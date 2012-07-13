package edu.ucdenver.bios.matrixsvc.test;

import static org.junit.Assert.assertArrayEquals;

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

import edu.ucdenver.bios.matrixsvc.resource.MatrixScalarMultiplyServerResource;
import edu.ucdenver.bios.webservice.common.domain.NamedMatrix;
import edu.ucdenver.bios.webservice.common.domain.NamedMatrixList;
/**
 * TestCase for Matrix Scalar Multiply operations.
 * @author VIJAY AKULA
 *
 */
public class MatrixScalarMultiplyTestCases extends TestCase
{
    NamedMatrixList sameDimensionMatrixList = new NamedMatrixList();
    NamedMatrixList resultMatrixList = new NamedMatrixList();
    double scalarList[] = new double[5];
    
    int rows;
    int column;
    private boolean verbose = false;
    private static final String DATA_FILE = "data" + File.separator + "Scalar Multiplication Data.xml";
    private static final String OUTPUT_FILE = "Reports"+ File.separator + "Scalar Multiplication Results.html";
    private static final String TITLE = "Scalar Multiplication Results";
    
    NodeList nodes;
    
    public void setUp()
    {
       rows = 2; 
       column = 2;
    }
    
    @Test
    public void testValidScalarMultiply()
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
                
                sameDimensionMatrixList.clear();
                
                Node m1r0c0 = attrs.getNamedItem("m1r0c0");
                Node m1r0c1 = attrs.getNamedItem("m1r0c1");
                Node m1r1c0 = attrs.getNamedItem("m1r1c0");
                Node m1r1c1 = attrs.getNamedItem("m1r1c1");
                Node m2r0c0 = attrs.getNamedItem("m2r0c0");
                Node m2r0c1 = attrs.getNamedItem("m2r0c1");
                Node m2r1c0 = attrs.getNamedItem("m2r1c0");
                Node m2r1c1 = attrs.getNamedItem("m2r1c1");
                Node scalar = attrs.getNamedItem("scalar");
                
                
                
                NamedMatrix namedMatrix = new NamedMatrix();
                double[][] data1 = new double[2][2];
                data1[0][0] = Double.parseDouble(m1r0c0.getNodeValue().trim());
                data1[0][1] = Double.parseDouble(m1r0c1.getNodeValue().trim());
                data1[1][0] = Double.parseDouble(m1r1c0.getNodeValue().trim());
                data1[1][1] = Double.parseDouble(m1r1c1.getNodeValue().trim());
                namedMatrix.setDataFromArray(data1);
                
                double[][] data = new double[2][2];
                data[0][0] = Double.parseDouble(m2r0c0.getNodeValue().trim());
                data[0][1] = Double.parseDouble(m2r0c1.getNodeValue().trim());
                data[1][0] = Double.parseDouble(m2r1c0.getNodeValue().trim());
                data[1][1] = Double.parseDouble(m2r1c1.getNodeValue().trim());
                
                
                double value = Double.parseDouble(scalar.getNodeValue().trim());
                
                
                
                scalarList[i] = value;
                
                NamedMatrix result = new NamedMatrix();
                
                MatrixScalarMultiplyServerResource resource = new MatrixScalarMultiplyServerResource();
                result = resource.scalarMultiply(value, namedMatrix);
                resultMatrixList.add(result);
                
                
                assertArrayEquals(data, result.getDataAsArray());
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
        StringBuffer buffer = new StringBuffer();
        
        buffer.append("<html><head></head><body><h1>" + title + "</h1>");
        
        buffer.append("<h2></h2>");
        buffer.append("<table border='2' cellpadding='10'><tr><th>Test Case</th>");
        buffer.append("<th>Matrix 1</th>");
        buffer.append("<th>Scalar Value</th>");
        buffer.append("<th>Expected Matrix</th>");
        buffer.append("<th>Output Matrix</th>");
        buffer.append("<th>Result</th>");
        
        for(int i = 0; i < resultMatrixList.size(); i++)
        {
            
            NamedMatrix result = resultMatrixList.get(i);
            
            double data[][] = result.getDataAsArray(); 
            
            
            Node matrix = nodes.item(i);
            NamedNodeMap attrs = matrix.getAttributes();
            
            Node m1r0c0 = attrs.getNamedItem("m1r0c0");
            Node m1r0c1 = attrs.getNamedItem("m1r0c1");
            Node m1r1c0 = attrs.getNamedItem("m1r1c0");
            Node m1r1c1 = attrs.getNamedItem("m1r1c1");
            Node m2r0c0 = attrs.getNamedItem("m2r0c0");
            Node m2r0c1 = attrs.getNamedItem("m2r0c1");
            Node m2r1c0 = attrs.getNamedItem("m2r1c0"); 
            Node m2r1c1 = attrs.getNamedItem("m2r1c1");

            buffer.append("<tr>");
            buffer.append("<td>Test Case "+(i+1)+"</td>");
            buffer.append("<td><table border='1' cellpadding='5'><tr><td>"+Double.parseDouble(m1r0c0.getNodeValue().trim())+"</td>" +
                    "<td>"+Double.parseDouble(m1r0c1.getNodeValue().trim())+"</td></tr>"+
                    "<tr><td>"+Double.parseDouble(m1r1c0.getNodeValue().trim())+"</td>"+
                    "<td>"+Double.parseDouble(m1r1c1.getNodeValue().trim())+"</td></tr></table>");
            
            double scalarValue = scalarList[i];
            
            buffer.append("<td>"+scalarValue+"</td>");
            
            double value1 = Double.parseDouble(m2r0c0.getNodeValue().trim());
            double value2 = Double.parseDouble(m2r0c1.getNodeValue().trim());
            double value3 = Double.parseDouble(m2r1c0.getNodeValue().trim());
            double value4 = Double.parseDouble(m2r1c1.getNodeValue().trim());
            
            buffer.append("<td><table border='1' cellpadding='5'><tr><td>"+value1+"</td>" +
                    "<td>"+value2+"</td></tr>"+
                    "<tr><td>"+value3+"</td>"+
                    "<td>"+value4+"</td></tr></table>");
            
            buffer.append("<td><table border='1' cellpadding='5'><tr><td>"+data[0][0]+"</td>" +
                    "<td>"+data[0][1]+"</td></tr>"+
                    "<tr><td>"+data[1][0]+"</td>"+
                    "<td>"+data[1][1]+"</td></tr></table>");
            if(value1 == data[0][0] && value2 == data[0][1] && value3 == data[1][0] && value4 == data[1][1])
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
                try {writer.close(); 
        } 
            
        catch (Exception e) 
        {
                
        };
        }
        
    }
}
