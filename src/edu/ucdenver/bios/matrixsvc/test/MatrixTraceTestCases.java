package edu.ucdenver.bios.matrixsvc.test;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ResultTreeType;

import edu.ucdenver.bios.matrixsvc.resource.MatrixInversionServerResource;
import edu.ucdenver.bios.matrixsvc.resource.MatrixTraceServerResource;
import edu.ucdenver.bios.webservice.common.domain.NamedMatrix;
import edu.ucdenver.bios.webservice.common.domain.NamedMatrixList;
import junit.framework.TestCase;
/**
 * TestCase for Matrix Trace Operation.
 * @author VIJAY AKULA.
 *
 */
public class MatrixTraceTestCases extends TestCase{
    
    NamedMatrixList matrixList = new NamedMatrixList();
    ArrayList<Double> traceList = new ArrayList<Double>();
    private boolean verbose = false;
    private static String DATA_FILE = "data" + File.separator + "Trace Data.xml";
    private static final String OUTPUT_FILE = "Reports"+ File.separator + "Trace Matrix Operation Results.html";
    private static final String TITLE = "Matrix Trace Operation Results";
    
    
    StringBuffer buffer = new StringBuffer();
    
    NodeList nodes;
    
    public void setUp()
    {
    }
    
    @Test
    public void testMatrixTraceOperation()
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
                
                Node trace = attrs.getNamedItem("trace");
                
                
               
                NamedMatrix namedMatrix = new NamedMatrix();

                double[][] data = new double[3][3];
                
                data[0][0] = Double.parseDouble(m1r0c0.getNodeValue());
                
                data[0][1] = Double.parseDouble(m1r0c1.getNodeValue());
                
                data[0][2] = Double.parseDouble(m1r0c2.getNodeValue());
                
                data[1][0] = Double.parseDouble(m1r1c0.getNodeValue());
                
                
                
                data[1][1] = Double.parseDouble(m1r1c1.getNodeValue());
                
                data[1][2] = Double.parseDouble(m1r1c2.getNodeValue());
                
                data[2][0] = Double.parseDouble(m1r2c0.getNodeValue());
                
                data[2][1] = Double.parseDouble(m1r2c1.getNodeValue());
                
                data[2][2] = Double.parseDouble(m1r2c2.getNodeValue());

                namedMatrix.setDataFromArray(data);

                Double expectedTrace = Double.parseDouble(trace.getNodeValue());
                
                matrixList.add(namedMatrix);
                
                double resultantTrace = 0.0;
                
                MatrixTraceServerResource resource = new MatrixTraceServerResource();
                
                resultantTrace = resource.trace(namedMatrix);
                
                traceList.add(resultantTrace);
                
                assertEquals(expectedTrace, resultantTrace, 0.00000000001);
       
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
        buffer.append("<th>Expected Trace</th>");
        buffer.append("<th>Output Trace</th>");
        buffer.append("<th>Result</th>");
        
        for(int i = 0; i < traceList.size(); i++)
        {
            
            double resultantTrace = traceList.get(i); 
            
            
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
            Node trace = attrs.getNamedItem("trace");

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
            
            
            buffer.append("<td>"+Double.parseDouble(trace.getNodeValue())+"</td>");
            
            int decimalPlace = 2;
            
            BigDecimal bd = new BigDecimal(resultantTrace);
            
            bd = bd.setScale(decimalPlace, BigDecimal.ROUND_UP);
            
            
            
            buffer.append("<td>"+bd+"</td>");            
           
            
            if(Math.abs(Double.parseDouble(trace.getNodeValue())-resultantTrace)<0.00000000001)
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
