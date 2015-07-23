/*

    MiringValidator  Semantic Validator for MIRING compliant HML
    Copyright (c) 2015 National Marrow Donor Program (NMDP)

    This library is free software; you can redistribute it and/or modify it
    under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation; either version 3 of the License, or (at
    your option) any later version.

    This library is distributed in the hope that it will be useful, but WITHOUT
    ANY WARRANTY; with out even the implied warranty of MERCHANTABILITY or
    FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
    License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this library;  if not, write to the Free Software Foundation,
    Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA.

    > http://www.gnu.org/licenses/lgpl.html

*/
package org.nmdp.miring;

import static org.junit.Assert.*;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;

import org.nmdp.miring.SchematronValidator;
import org.nmdp.miring.Utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Test;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.helpers.AttributesImpl;

public class UtilsTest
{
    Logger logger = LoggerFactory.getLogger(UtilsTest.class);

    @Test
    public void testGetAttributes() 
    {
        logger.debug("starting testGetAttributes");

        //AttributesImpl is the default implementer of Attributes interface
        AttributesImpl attributesImplementationObject = new AttributesImpl(); 
        attributesImplementationObject.addAttribute(
                  "uri is NOT in the results"
                , "localName IS in the results"
                , "qName is NOT in the results"
                , "type is NOT in the results"
                , "value IS in the results");
        
        attributesImplementationObject.addAttribute("","Customer Name","","","Fred Stevens");
        
        String results = Utilities.getAttributes(attributesImplementationObject);
        
        assertTrue(results.contains("localName IS in the results"));
        assertTrue(results.contains("value IS in the results"));
        
        assertFalse(results.contains("uri"));
        assertFalse(results.contains("qname"));
        assertFalse(results.contains("type")); 
        
        assertTrue(results.contains("{Customer Name:Fred Stevens}"));
    }
    
    @Test
    public void testLoadJarElements()
    {
        logger.debug("starting testLoadJarElements");
        try
        {
            URL jarURL = SchematronValidator.class.getResource("/org/nmdp/miring/jar/probatron.jar");
            assertNotNull(jarURL);
            URI jarURI = jarURL.toURI();
            URLClassLoader loadedProbatronClasses = Utilities.loadJarElements(new File(jarURI));
            assertNotNull(loadedProbatronClasses);
            
            //If it finds this class then we opened the jar successfully.
            Class sessionClass= loadedProbatronClasses.loadClass("org.probatron.Session");
            assertNotNull(sessionClass);
        }
        catch(Exception e)
        {
            fail("Exception: " + e);
        }        
    }
    
    @Test
    public void testContainsErrorNode()
    {
        logger.debug("starting testContainsErrorNode");
        String xml = "<MiringReport><hmlid extension=\"abcd\" root=\"1234\"/><QualityScore>3</QualityScore>" +
            "<InvalidMiringResult fatal=\"true\" miringRuleID=\"5.7.a\"><description>" +
            "The node variant is missing a filter attribute.</description></InvalidMiringResult></MiringReport>";
        
        assertTrue(Utilities.containsErrorNode(xml, "The node variant is missing a filter attribute."));
        //Only part of the text is required:
        assertTrue(Utilities.containsErrorNode(xml, "ode variant is missing a filter attribu"));
        assertFalse(Utilities.containsErrorNode(xml, "This text is not in the report.  Wooo."));
    }
    
    @Test
    public void testCallReflectedMethod()
    {
        logger.debug("starting testCallReflectedMethod");
        try
        {
            URL jarURL = SchematronValidator.class.getResource("/org/nmdp/miring/jar/probatron.jar");
            URI jarURI = jarURL.toURI();
            ClassLoader loadedProbatronClasses = Utilities.loadJarElements(new File(jarURI));
            
            Class sessionClass= loadedProbatronClasses.loadClass("org.probatron.Session");
            Object currentSession = sessionClass.newInstance();
            
            assertNotNull(currentSession);
            
            //Call org.probatron.Session.setSchemaDoc(String schemaDoc)
            Utilities.callReflectedMethod(currentSession,"setSchemaDoc", "NewSchemaFileName.sch", String.class);
        }
        catch(Exception e)
        {
            fail("Exception trying to call a reflected method: " + e);
        }
    }
    
    @Test
    public void  testXmlToDomObject()
    {
        logger.debug("starting testXmlToDomObject");

        String demoGoodXML = Utilities.readXmlResource("/org/nmdp/miring/hml/demogood.xml");
        Element xmlElement = Utilities.xmlToRootElement(demoGoodXML);
        assertNotNull(xmlElement);
        
        boolean sampleElementFound = false;
        NodeList childrenNodes = xmlElement.getChildNodes();
        for(int i = 0; i < childrenNodes.getLength(); i++)
        {
            String childsName = childrenNodes.item(i).getNodeName();
            if(childsName != null && childsName.equals("sample"))
            {
                //This xml has a sample node underneath, so we know it was succesfully loaded.
                sampleElementFound = true;
            }
        }
        assertTrue(sampleElementFound);
    }
    
    @Test
    public void testGetHMLID()
    {
        logger.debug("starting testGetHMLID");

        String demoGoodXML = Utilities.readXmlResource("/org/nmdp/miring/hml/demogood.xml");
        
        String root = Utilities.getHMLIDRoot(demoGoodXML);
        String extension = Utilities.getHMLIDExtension(demoGoodXML);
        
        assertTrue(root.equals("1234"));
        assertTrue(extension.equals("abcd"));        
    }
    
    @Test
    public void testReadXML()
    {
        logger.debug("starting testReadXML");

        String demoGoodXML = Utilities.readXmlResource("/org/nmdp/miring/hml/demogood.xml");
        assertNotNull(demoGoodXML);
        assertTrue(demoGoodXML.length() > 50);
    }
}
