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

import edu.ucdenver.bios.matrixsvc.resource.MatrixKroneckerMultiplyServerResource;
import edu.ucdenver.bios.webservice.common.domain.NamedMatrix;
import edu.ucdenver.bios.webservice.common.domain.NamedMatrixList;
/**
 * TestCase for Matrix Kronecker Product.
 * @author VIJAY AKULA
 *
 */
public class MatrixKroneckerProductTestCases extends TestCase
{
    NamedMatrixList matrixList = new NamedMatrixList();
    NamedMatrixList resultMatrixList = new NamedMatrixList();
    int rows;
    int column;
    private boolean verbose = false;
    private static String DATA_FILE = "data" + File.separator + "Kronecker Product Data.xml";
    private static final String OUTPUT_FILE = "Reports"+ File.separator + "Kronecker Product Results.html";
    private static final String TITLE = "Kronecker Product Results";
    
    
    StringBuffer buffer = new StringBuffer();
    
    NodeList nodes;
    NodeList nodes1;
    public void setUp()
    {
       rows = 2; 
       column = 2;
    }
    
    @Test
    public void testValidKroneckerProduct()
    {
        resultMatrixList.clear();
        FileReader reader = null;
        
        try
        {
            
            DocumentBuilderFactory factory =  DocumentBuilderFactory.newInstance();
            
            DocumentBuilder builder = factory.newDocumentBuilder();
            reader = new FileReader(DATA_FILE);
            Document doc = builder.parse(new InputSource(reader));
            
            nodes1 = doc.getElementsByTagName("matrix");
            
            for(int i = 0; i < nodes1.getLength(); i++)
            {
               
                if(i == nodes1.getLength()-1) 
                { 
                    verbose = true;
                }
                Node matrix = nodes1.item(i);
                NamedNodeMap attrs = matrix.getAttributes();
                
                matrixList.clear();
                
                Node m1r0c0 = attrs.getNamedItem("m1r0c0");
                Node m1r0c1 = attrs.getNamedItem("m1r0c1");
                Node m1r1c0 = attrs.getNamedItem("m1r1c0");
                Node m1r1c1 = attrs.getNamedItem("m1r1c1");
                Node m1r2c0 = attrs.getNamedItem("m1r2c0");
                Node m1r2c1 = attrs.getNamedItem("m1r2c1");
                Node m2r0c0 = attrs.getNamedItem("m2r0c0");
                Node m2r0c1 = attrs.getNamedItem("m2r0c1");
                Node m2r1c0 = attrs.getNamedItem("m2r1c0"); 
                Node m2r1c1 = attrs.getNamedItem("m2r1c1");
                Node m3r0c0 = attrs.getNamedItem("m3r0c0");
                Node m3r0c1 = attrs.getNamedItem("m3r0c1");
                Node m3r0c2 = attrs.getNamedItem("m3r0c2");
                Node m3r0c3 = attrs.getNamedItem("m3r0c3");
                Node m3r1c0 = attrs.getNamedItem("m3r1c0");
                Node m3r1c1 = attrs.getNamedItem("m3r1c1");
                Node m3r1c2 = attrs.getNamedItem("m3r1c2");
                Node m3r1c3 = attrs.getNamedItem("m3r1c3");
                Node m3r2c0 = attrs.getNamedItem("m3r2c0");
                Node m3r2c1 = attrs.getNamedItem("m3r2c1");
                Node m3r2c2 = attrs.getNamedItem("m3r2c2");
                Node m3r2c3 = attrs.getNamedItem("m3r2c3");
                Node m3r3c0 = attrs.getNamedItem("m3r3c0");
                Node m3r3c1 = attrs.getNamedItem("m3r3c1");
                Node m3r3c2 = attrs.getNamedItem("m3r3c2");
                Node m3r3c3 = attrs.getNamedItem("m3r3c3");
                Node m3r4c0 = attrs.getNamedItem("m3r4c0");
                Node m3r4c1 = attrs.getNamedItem("m3r4c1");
                Node m3r4c2 = attrs.getNamedItem("m3r4c2");
                Node m3r4c3 = attrs.getNamedItem("m3r4c3");
                Node m3r5c0 = attrs.getNamedItem("m3r5c0");
                Node m3r5c1 = attrs.getNamedItem("m3r5c1");
                Node m3r5c2 = attrs.getNamedItem("m3r5c2");
                Node m3r5c3 = attrs.getNamedItem("m3r5c3");
                
                
                
                NamedMatrix matrix1 = new NamedMatrix();
                double[][] data1 = new double[3][2];
                data1[0][0] = Double.parseDouble(m1r0c0.getNodeValue().trim());
                data1[0][1] = Double.parseDouble(m1r0c1.getNodeValue().trim());
                data1[1][0] = Double.parseDouble(m1r1c0.getNodeValue().trim());
                data1[1][1] = Double.parseDouble(m1r1c1.getNodeValue().trim());
                data1[2][0] = Double.parseDouble(m1r2c0.getNodeValue().trim());
                data1[2][1] = Double.parseDouble(m1r2c1.getNodeValue().trim());
                matrix1.setDataFromArray(data1);

                
                
                NamedMatrix matrix2 = new NamedMatrix();
                double[][] data2 = new double[2][2];
                data2[0][0] = Double.parseDouble(m2r0c0.getNodeValue().trim());
                data2[0][1] = Double.parseDouble(m2r0c1.getNodeValue().trim());
                data2[1][0] = Double.parseDouble(m2r1c0.getNodeValue().trim());
                data2[1][1] = Double.parseDouble(m2r1c1.getNodeValue().trim());
                matrix2.setDataFromArray(data2);              
                
                
                
                
                double[][] data = new double[6][4];
                
                data[0][0] = Double.parseDouble(m3r0c0.getNodeValue().trim());
                
                data[0][1] = Double.parseDouble(m3r0c1.getNodeValue().trim());
                
                data[0][2] = Double.parseDouble(m3r0c2.getNodeValue().trim());
                
                data[0][3] = Double.parseDouble(m3r0c3.getNodeValue().trim());
                
                
                data[1][0] = Double.parseDouble(m3r1c0.getNodeValue().trim());
                
                data[1][1] = Double.parseDouble(m3r1c1.getNodeValue().trim());
                
                data[1][2] = Double.parseDouble(m3r1c2.getNodeValue().trim());
                
                data[1][3] = Double.parseDouble(m3r1c3.getNodeValue().trim());
                
                
                
                data[2][0] = Double.parseDouble(m3r2c0.getNodeValue().trim());
                
                data[2][1] = Double.parseDouble(m3r2c1.getNodeValue().trim());
                
                data[2][2] = Double.parseDouble(m3r2c2.getNodeValue().trim());
                
                data[2][3] = Double.parseDouble(m3r2c3.getNodeValue().trim());
                
               
                
                data[3][0] = Double.parseDouble(m3r3c0.getNodeValue().trim());
                
                data[3][1] = Double.parseDouble(m3r3c1.getNodeValue().trim());
                
                data[3][2] = Double.parseDouble(m3r3c2.getNodeValue().trim());
                
                data[3][3] = Double.parseDouble(m3r3c3.getNodeValue().trim());
                
                
                data[4][0] = Double.parseDouble(m3r4c0.getNodeValue().trim());
                
                data[4][1] = Double.parseDouble(m3r4c1.getNodeValue().trim());
                
                data[4][2] = Double.parseDouble(m3r4c2.getNodeValue().trim());
                
                data[4][3] = Double.parseDouble(m3r4c3.getNodeValue().trim());
                
               
                
                data[5][0] = Double.parseDouble(m3r5c0.getNodeValue().trim());
                
                data[5][1] = Double.parseDouble(m3r5c1.getNodeValue().trim());
                
                data[5][2] = Double.parseDouble(m3r5c2.getNodeValue().trim());
                
                data[5][3] = Double.parseDouble(m3r5c3.getNodeValue().trim());
                
                
                matrixList.add(matrix1);
                matrixList.add(matrix2);
                
                NamedMatrix result = new NamedMatrix();
                
                MatrixKroneckerMultiplyServerResource resource = new MatrixKroneckerMultiplyServerResource();
                result = resource.kroneckerMultiply(matrixList);
                
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

        buffer.append("<html><head></head><body><h1>" + title + "</h1>");
        buffer.append("<h2>Different Dimension Matrices</h2>");
        buffer.append("<table border='2' cellpadding='10'><tr><th>Test Case</th>");
        buffer.append("<th>Matrix 1</th>");
        buffer.append("<th>Matrix 2</th>");
        buffer.append("<th>Expected Matrix</th>");
        buffer.append("<th>Output Matrix</th>");
        buffer.append("<th>Result</th>");
        
        for(int i = 0; i < resultMatrixList.size(); i++)
        {
            
            NamedMatrix result = resultMatrixList.get(i);
            
            double data[][] = result.getDataAsArray(); 
            
            
            Node matrix = nodes1.item(i);
            NamedNodeMap attrs = matrix.getAttributes();
            
            Node m1r0c0 = attrs.getNamedItem("m1r0c0");
            Node m1r0c1 = attrs.getNamedItem("m1r0c1");
            Node m1r1c0 = attrs.getNamedItem("m1r1c0");
            Node m1r1c1 = attrs.getNamedItem("m1r1c1");
            Node m1r2c0 = attrs.getNamedItem("m1r2c0");
            Node m1r2c1 = attrs.getNamedItem("m1r2c1");
            Node m2r0c0 = attrs.getNamedItem("m2r0c0");
            Node m2r0c1 = attrs.getNamedItem("m2r0c1");
            Node m2r1c0 = attrs.getNamedItem("m2r1c0"); 
            Node m2r1c1 = attrs.getNamedItem("m2r1c1");
            Node m3r0c0 = attrs.getNamedItem("m3r0c0");
            Node m3r0c1 = attrs.getNamedItem("m3r0c1");
            Node m3r0c2 = attrs.getNamedItem("m3r0c2");
            Node m3r0c3 = attrs.getNamedItem("m3r0c3");
            Node m3r1c0 = attrs.getNamedItem("m3r1c0");
            Node m3r1c1 = attrs.getNamedItem("m3r1c1");
            Node m3r1c2 = attrs.getNamedItem("m3r1c2");
            Node m3r1c3 = attrs.getNamedItem("m3r1c3");
            Node m3r2c0 = attrs.getNamedItem("m3r2c0");
            Node m3r2c1 = attrs.getNamedItem("m3r2c1");
            Node m3r2c2 = attrs.getNamedItem("m3r2c2");
            Node m3r2c3 = attrs.getNamedItem("m3r2c3");
            Node m3r3c0 = attrs.getNamedItem("m3r3c0");
            Node m3r3c1 = attrs.getNamedItem("m3r3c1");
            Node m3r3c2 = attrs.getNamedItem("m3r3c2");
            Node m3r3c3 = attrs.getNamedItem("m3r3c3");
            Node m3r4c0 = attrs.getNamedItem("m3r4c0");
            Node m3r4c1 = attrs.getNamedItem("m3r4c1");
            Node m3r4c2 = attrs.getNamedItem("m3r4c2");
            Node m3r4c3 = attrs.getNamedItem("m3r4c3");
            Node m3r5c0 = attrs.getNamedItem("m3r5c0");
            Node m3r5c1 = attrs.getNamedItem("m3r5c1");
            Node m3r5c2 = attrs.getNamedItem("m3r5c2");
            Node m3r5c3 = attrs.getNamedItem("m3r5c3");
            

            buffer.append("<tr>");
            buffer.append("<td>Test Case "+(i+1)+"</td>");
            buffer.append("<td><table border='1' cellpadding='5'><tr><td>"+Double.parseDouble(m1r0c0.getNodeValue().trim())+"</td>" +
                    "<td>"+Double.parseDouble(m1r0c1.getNodeValue().trim())+"</td></tr>"+
                    "<tr><td>"+Double.parseDouble(m1r1c0.getNodeValue().trim())+"</td>"+
                    "<td>"+Double.parseDouble(m1r1c1.getNodeValue().trim())+"</td></tr>" +
                            "<tr><td>"+Double.parseDouble(m1r2c0.getNodeValue().trim())+"</td>"+
                    "<td>"+Double.parseDouble(m1r2c1.getNodeValue().trim())+"</td></tr></table>");
            
            buffer.append("<td><table border='1' cellpadding='5'><tr><td>"+Double.parseDouble(m2r0c0.getNodeValue().trim())+"</td>" +
                    "<td>"+Double.parseDouble(m2r0c1.getNodeValue().trim())+"</td></tr>"+
                    "<tr><td>"+Double.parseDouble(m2r1c0.getNodeValue().trim())+"</td>"+
                    "<td>"+Double.parseDouble(m2r1c1.getNodeValue().trim())+"</td></tr></table>");
            
            
            double value1 = Double.parseDouble(m3r0c0.getNodeValue().trim());
            double value2 = Double.parseDouble(m3r0c1.getNodeValue().trim());
            double value3 = Double.parseDouble(m3r0c2.getNodeValue().trim());
            double value4 = Double.parseDouble(m3r0c3.getNodeValue().trim());
            double value5 = Double.parseDouble(m3r1c0.getNodeValue().trim());
            double value6 = Double.parseDouble(m3r1c1.getNodeValue().trim());
            double value7 = Double.parseDouble(m3r1c2.getNodeValue().trim());
            double value8 = Double.parseDouble(m3r1c3.getNodeValue().trim());
            double value9 = Double.parseDouble(m3r2c0.getNodeValue().trim());
            double value10 = Double.parseDouble(m3r2c1.getNodeValue().trim());
            double value11 = Double.parseDouble(m3r2c2.getNodeValue().trim());
            double value12 = Double.parseDouble(m3r2c3.getNodeValue().trim());
            double value13 = Double.parseDouble(m3r3c0.getNodeValue().trim());
            double value14 = Double.parseDouble(m3r3c1.getNodeValue().trim());
            double value15 = Double.parseDouble(m3r3c2.getNodeValue().trim());
            double value16 = Double.parseDouble(m3r3c3.getNodeValue().trim());
            double value17 = Double.parseDouble(m3r4c0.getNodeValue().trim());
            double value18 = Double.parseDouble(m3r4c1.getNodeValue().trim());
            double value19 = Double.parseDouble(m3r4c2.getNodeValue().trim());
            double value20 = Double.parseDouble(m3r4c3.getNodeValue().trim());
            double value21 = Double.parseDouble(m3r5c0.getNodeValue().trim());
            double value22 = Double.parseDouble(m3r5c1.getNodeValue().trim());
            double value23 = Double.parseDouble(m3r5c2.getNodeValue().trim());
            double value24 = Double.parseDouble(m3r5c3.getNodeValue().trim());
            
            buffer.append("<td><table border='1' cellpadding='5'>" +
            		"<tr>" +
            		"<td>"+value1+"</td>" +
                    "<td>"+value2+"</td>" +
                    "<td>"+value3+"</td>"+
                    "<td>"+value4+"</td>" +
                    		"</tr><tr>" +
                            "<td>"+value5+"</td>" +
                            "<td>"+value6+"</td>" +
                            "<td>"+value7+"</td>"+
                            "<td>"+value8+"</td>" +
                                    "</tr><tr>" +
                                    "<td>"+value9+"</td>" +
                                    "<td>"+value10+"</td>" +
                                    "<td>"+value11+"</td>"+
                                    "<td>"+value12+"</td>" +
                                            "</tr><tr>" +
                                            "<td>"+value13+"</td>" +
                                            "<td>"+value14+"</td>" +
                                            "<td>"+value15+"</td>"+
                                            "<td>"+value16+"</td>" +
                                                    "</tr><tr>" +
                                                    "<td>"+value17+"</td>" +
                                                    "<td>"+value18+"</td>" +
                                                    "<td>"+value19+"</td>"+
                                                    "<td>"+value20+"</td>" +
                                                            "</tr><tr>" +
                                                            "<td>"+value21+"</td>" +
                                                            "<td>"+value22+"</td>" +
                                                            "<td>"+value23+"</td>"+
                                                            "<td>"+value24+"</td>" +
                                                            "</tr></table>");
            
            buffer.append("<td><table border='1' cellpadding='5'>" +
            		"<tr>" +
            		"<td>"+data[0][0]+"</td>" +
                    "<td>"+data[0][1]+"</td>"+
                    "<td>"+data[0][2]+"</td>"+
                    "<td>"+data[0][3]+"</td>"+
                    "</tr><tr>" +
                    "<td>"+data[1][0]+"</td>" +
                    "<td>"+data[1][1]+"</td>"+
                    "<td>"+data[1][2]+"</td>"+
                    "<td>"+data[1][3]+"</td>"+
                    "</tr><tr>" +
                    "<td>"+data[2][0]+"</td>" +
                    "<td>"+data[2][1]+"</td>"+
                    "<td>"+data[2][2]+"</td>"+
                    "<td>"+data[2][3]+"</td>"+
                    "</tr><tr>" +
                    "<td>"+data[3][0]+"</td>" +
                    "<td>"+data[3][1]+"</td>"+
                    "<td>"+data[3][2]+"</td>"+
                    "<td>"+data[3][3]+"</td>"+
                    "</tr><tr>" +
                    "<td>"+data[4][0]+"</td>" +
                    "<td>"+data[4][1]+"</td>"+
                    "<td>"+data[4][2]+"</td>"+
                    "<td>"+data[4][3]+"</td>"+
                    "</tr><tr>" +
                    "<td>"+data[5][0]+"</td>" +
                    "<td>"+data[5][1]+"</td>"+
                    "<td>"+data[5][2]+"</td>"+
                    "<td>"+data[5][3]+"</td>"+
                    "</tr></table>");
            
            
            
            if(value1 == data[0][0] && value2 == data[0][1] && value3 == data[0][2] && value4 == data[0][3] &&
                    value5 == data[1][0] && value6 == data[1][1] && value7 == data[1][2] && value8 == data[1][3] &&
                    value9 == data[2][0] && value10 == data[2][1] && value11 == data[2][2] && value12 == data[2][3] &&
                    value13 == data[3][0] && value14 == data[3][1] && value15 == data[3][2] && value16 == data[3][3] &&
                    value17 == data[4][0] && value18 == data[4][1] && value19 == data[4][2] && value20 == data[4][3] &&
                    value21 == data[5][0] && value22 == data[5][1] && value23 == data[5][2] && value24 == data[5][3])
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
