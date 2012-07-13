package edu.ucdenver.bios.matrixsvc.test;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import static org.junit.Assert.assertArrayEquals;
import edu.ucdenver.bios.matrixsvc.resource.MatrixMultiplicationServerResource;
import edu.ucdenver.bios.webservice.common.domain.NamedMatrix;
import edu.ucdenver.bios.webservice.common.domain.NamedMatrixList;
/**
 * TestCase for Matrix Multiplication operation.
 * @author VIJAY AKULA
 *
 */
public class MatrixMultiplicationTestCases 
{

    NamedMatrixList sameDimensionMatrixList = new NamedMatrixList();
    NamedMatrixList resultMatrixList = new NamedMatrixList();
    int rows;
    int column;
    private boolean verbose = false;
    private static String DATA_FILE = "data" + File.separator + "Multiplication Data Same Dimensions.xml";
    private static String DATA_FILE1 = "data" + File.separator + "Multiplication Data Different Dimensions.xml";
    private static final String OUTPUT_FILE = "Reports"+ File.separator + "Multiplication Results Same Dimensions.html";
    private static final String OUTPUT_FILE1 = "Reports"+ File.separator + "Multiplication Results Different Dimensions.html";
    private static final String TITLE = "Multiplication Results";
    
    
    StringBuffer buffer = new StringBuffer();
    StringBuffer buffer1 = new StringBuffer();
    StringBuffer buffer2 = new StringBuffer();
    
    NodeList nodes;
    NodeList nodes1;
    public void setUp()
    {
       rows = 2; 
       column = 2;
    }
    
    @Test
    public void testValidMultiplication()
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
                Node m3r0c0 = attrs.getNamedItem("m3r0c0");
                Node m3r0c1 = attrs.getNamedItem("m3r0c1");
                Node m3r1c0 = attrs.getNamedItem("m3r1c0");
                Node m3r1c1 = attrs.getNamedItem("m3r1c1");

                
                NamedMatrix matrix1 = new NamedMatrix();
                double[][] data1 = new double[2][2];
                data1[0][0] = Double.parseDouble(m1r0c0.getNodeValue().trim());
                data1[0][1] = Double.parseDouble(m1r0c1.getNodeValue().trim());
                data1[1][0] = Double.parseDouble(m1r1c0.getNodeValue().trim());
                data1[1][1] = Double.parseDouble(m1r1c1.getNodeValue().trim());
                matrix1.setDataFromArray(data1);

                NamedMatrix matrix2 = new NamedMatrix();
                double[][] data2 = new double[2][2];
                data2[0][0] = Double.parseDouble(m2r0c0.getNodeValue().trim());
                data2[0][1] = Double.parseDouble(m2r0c1.getNodeValue().trim());
                data2[1][0] = Double.parseDouble(m2r1c0.getNodeValue().trim());
                data2[1][1] = Double.parseDouble(m2r1c1.getNodeValue().trim());
                matrix2.setDataFromArray(data2);              
                
                double[][] data = new double[2][2];
                data[0][0] = Double.parseDouble(m3r0c0.getNodeValue().trim());
                data[0][1] = Double.parseDouble(m3r0c1.getNodeValue().trim());
                data[1][0] = Double.parseDouble(m3r1c0.getNodeValue().trim());
                data[1][1] = Double.parseDouble(m3r1c1.getNodeValue().trim());
                
                sameDimensionMatrixList.add(matrix1);
                sameDimensionMatrixList.add(matrix2);
                
                NamedMatrix result = new NamedMatrix();
                
                MatrixMultiplicationServerResource resource = new MatrixMultiplicationServerResource();
                result = resource.multiply(sameDimensionMatrixList);
                
                resultMatrixList.add(result);
                assertArrayEquals(data, result.getDataAsArray());
                if(verbose)
                {
                    outputResultsSameDimensions(TITLE, OUTPUT_FILE, "");    
                }
            }
            
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException("Parsing of SAS XML failed: " + e.getMessage());
        }
    }
    public void outputResultsSameDimensions(String title, String filename, String imageFilename)
    {
        
        
        buffer1.append("<html><head></head><body><h1>" + title + "</h1>");
        
        buffer1.append("<h2>Equal Dimension Matrices</h2>");
        buffer1.append("<table border='2' cellpadding='10'><tr><th>Test Case</th>");
        buffer1.append("<th>Matrix 1</th>");
        buffer1.append("<th>Matrix 2</th>");
        buffer1.append("<th>Expected Matrix</th>");
        buffer1.append("<th>Output Matrix</th>");
        buffer1.append("<th>Result</th>");
        
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
            Node m3r0c0 = attrs.getNamedItem("m3r0c0");
            Node m3r0c1 = attrs.getNamedItem("m3r0c1");
            Node m3r1c0 = attrs.getNamedItem("m3r1c0");
            Node m3r1c1 = attrs.getNamedItem("m3r1c1");

            buffer1.append("<tr>");
            buffer1.append("<td>Test Case "+(i+1)+"</td>");
            buffer1.append("<td><table border='1' cellpadding='5'><tr><td>"+Double.parseDouble(m1r0c0.getNodeValue().trim())+"</td>" +
                    "<td>"+Double.parseDouble(m1r0c1.getNodeValue().trim())+"</td></tr>"+
                    "<tr><td>"+Double.parseDouble(m1r1c0.getNodeValue().trim())+"</td>"+
                    "<td>"+Double.parseDouble(m1r1c1.getNodeValue().trim())+"</td></tr></table>");
            
            buffer1.append("<td><table border='1' cellpadding='5'><tr><td>"+Double.parseDouble(m2r0c0.getNodeValue().trim())+"</td>" +
                    "<td>"+Double.parseDouble(m2r0c1.getNodeValue().trim())+"</td></tr>"+
                    "<tr><td>"+Double.parseDouble(m2r1c0.getNodeValue().trim())+"</td>"+
                    "<td>"+Double.parseDouble(m2r1c1.getNodeValue().trim())+"</td></tr></table>");
            double value1 = Double.parseDouble(m3r0c0.getNodeValue().trim());
            double value2 = Double.parseDouble(m3r0c1.getNodeValue().trim());
            double value3 = Double.parseDouble(m3r1c0.getNodeValue().trim());
            double value4 = Double.parseDouble(m3r1c1.getNodeValue().trim());
            
            buffer1.append("<td><table border='1' cellpadding='5'><tr><td>"+value1+"</td>" +
                    "<td>"+value2+"</td></tr>"+
                    "<tr><td>"+value3+"</td>"+
                    "<td>"+value4+"</td></tr></table>");
            
            buffer1.append("<td><table border='1' cellpadding='5'><tr><td>"+data[0][0]+"</td>" +
                    "<td>"+data[0][1]+"</td></tr>"+
                    "<tr><td>"+data[1][0]+"</td>"+
                    "<td>"+data[1][1]+"</td></tr></table>");
            if(value1 == data[0][0] && value2 == data[0][1] && value3 == data[1][0] && value4 == data[1][1])
            {
                buffer1.append("<td>Passed</td>");
            }
            else
            {
                buffer1.append("<td>Failed</td>");
            }
            buffer1.append("</tr>");         
        }
        
        buffer1.append("</table><p>");
        verbose = false;
        FileWriter writer = null;
        try
        {
            writer = new FileWriter(filename);
            writer.write(buffer1.toString());
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
    
   @Test
    public void testValidDifferentDimensionMultiplication()
    {
        resultMatrixList.clear();
        FileReader reader = null;
        
        try
        {
            
            DocumentBuilderFactory factory =  DocumentBuilderFactory.newInstance();
            
            DocumentBuilder builder = factory.newDocumentBuilder();
            reader = new FileReader(DATA_FILE1);
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
                
                sameDimensionMatrixList.clear();
                
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
                Node m3r1c0 = attrs.getNamedItem("m3r1c0");
                Node m3r1c1 = attrs.getNamedItem("m3r1c1");
                Node m3r2c0 = attrs.getNamedItem("m3r2c0");
                Node m3r2c1 = attrs.getNamedItem("m3r2c1");
                
                
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
                
    
                
                
                double[][] data = new double[3][2];
                
                data[0][0] = Double.parseDouble(m3r0c0.getNodeValue().trim());
                
                data[0][1] = Double.parseDouble(m3r0c1.getNodeValue().trim());
                
                data[1][0] = Double.parseDouble(m3r1c0.getNodeValue().trim());
                
                data[1][1] = Double.parseDouble(m3r1c1.getNodeValue().trim());
                
                data[2][0] = Double.parseDouble(m3r2c0.getNodeValue().trim());
                
                data[2][1] = Double.parseDouble(m3r2c1.getNodeValue().trim());
               
                
 
                
                sameDimensionMatrixList.add(matrix1);
                sameDimensionMatrixList.add(matrix2);
                
                
                System.out.println("Matrices added to matrix list"); 
                
                
                NamedMatrix result = new NamedMatrix();
                
                MatrixMultiplicationServerResource resource = new MatrixMultiplicationServerResource();
                result = resource.multiply(sameDimensionMatrixList);
                
                resultMatrixList.add(result);
                assertArrayEquals(data, result.getDataAsArray());
                
                if(verbose)
                {
                    outputResultsDifferentDimensions(TITLE,OUTPUT_FILE1, "");    
                }
            }
            
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException("Parsing of SAS XML failed: " + e.getMessage());
        }
    }
    
    public void outputResultsDifferentDimensions(String title, String filename, String imageFilename)
    {
        buffer2.append("<html><head></head><body><h1>" + title + "</h1>");
        buffer2.append("<h2>Different Dimension Matrices</h2>");
        buffer2.append("<table border='2' cellpadding='10'><tr><th>Test Case</th>");
        buffer2.append("<th>Matrix 1</th>");
        buffer2.append("<th>Matrix 2</th>");
        buffer2.append("<th>Expected Matrix</th>");
        buffer2.append("<th>Output Matrix</th>");
        buffer2.append("<th>Result</th>");
        
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
            Node m3r1c0 = attrs.getNamedItem("m3r1c0");
            Node m3r1c1 = attrs.getNamedItem("m3r1c1");
            Node m3r2c0 = attrs.getNamedItem("m3r2c0");
            Node m3r2c1 = attrs.getNamedItem("m3r2c1");
            

            buffer2.append("<tr>");
            buffer2.append("<td>Test Case "+(i+1)+"</td>");
            buffer2.append("<td><table border='1' cellpadding='5'><tr><td>"+Double.parseDouble(m1r0c0.getNodeValue().trim())+"</td>" +
                    "<td>"+Double.parseDouble(m1r0c1.getNodeValue().trim())+"</td></tr>"+
                    "<tr><td>"+Double.parseDouble(m1r1c0.getNodeValue().trim())+"</td>"+
                    "<td>"+Double.parseDouble(m1r1c1.getNodeValue().trim())+"</td></tr>" +
                    		"<tr><td>"+Double.parseDouble(m1r2c0.getNodeValue().trim())+"</td>"+
                    "<td>"+Double.parseDouble(m1r2c1.getNodeValue().trim())+"</td></tr></table>");
            
            buffer2.append("<td><table border='1' cellpadding='5'><tr><td>"+Double.parseDouble(m2r0c0.getNodeValue().trim())+"</td>" +
                    "<td>"+Double.parseDouble(m2r0c1.getNodeValue().trim())+"</td></tr>"+
                    "<tr><td>"+Double.parseDouble(m2r1c0.getNodeValue().trim())+"</td>"+
                    "<td>"+Double.parseDouble(m2r1c1.getNodeValue().trim())+"</td></tr></table>");
            
            
            double value1 = Double.parseDouble(m3r0c0.getNodeValue().trim());
            double value2 = Double.parseDouble(m3r0c1.getNodeValue().trim());
            double value3 = Double.parseDouble(m3r1c0.getNodeValue().trim());
            double value4 = Double.parseDouble(m3r1c1.getNodeValue().trim());
            double value5 = Double.parseDouble(m3r2c0.getNodeValue().trim());
            double value6 = Double.parseDouble(m3r2c1.getNodeValue().trim());
            
            buffer2.append("<td><table border='1' cellpadding='5'><tr><td>"+value1+"</td>" +
                    "<td>"+value2+"</td></tr>"+
                    "<tr><td>"+value3+"</td>"+
                    "<td>"+value4+"</td></tr>"+
                    "<tr><td>"+value5+"</td>"+
                    "<td>"+value6+"</td></tr></table>");
            
            buffer2.append("<td><table border='1' cellpadding='5'><tr><td>"+data[0][0]+"</td>" +
                    "<td>"+data[0][1]+"</td></tr>"+
                    "<tr><td>"+data[1][0]+"</td>"+
                    "<td>"+data[1][1]+"</td></tr>"+
                    "<tr><td>"+data[2][0]+"</td>"+
                    "<td>"+data[2][1]+"</td></tr></table>");
            
            
            
            if(value1 == data[0][0] && value2 == data[0][1] && value3 == data[1][0] && value4 == data[1][1] && value5 == data[2][0] && value6 == data[2][1])
            {
                buffer2.append("<td>Passed</td>");
            }
            else
            {
                buffer2.append("<td>Failed</td>");
            }
            buffer2.append("</tr>");         
        }
        
        buffer2.append("</table><p>");
        
        if (imageFilename != null)
        {
            buffer2.append("<p><img src='" + imageFilename + "' /></p>");
        }
        
        buffer.append(buffer1);
        buffer.append(buffer2);
        buffer.append("</body></html>");

        System.out.println(buffer);
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
