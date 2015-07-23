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
import org.nmdp.miring.MiringValidator;
import org.nmdp.miring.ReportGenerator;
import org.nmdp.miring.SchematronValidator;
import org.nmdp.miring.Utilities;
import org.nmdp.miring.ValidationResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Test;

public class MiringTier1Test
{
    Logger logger = LoggerFactory.getLogger(MiringTier1Test.class);

    @Test
    public void testMiringElement1Tier1()
    {
        logger.debug("starting testMiringElement1Tier1");
        //1.1.a
        String xml = Utilities.readXmlResource("/org/nmdp/miring/hml/Element1.no.hmlid.xml");
        String results = new MiringValidator(xml).validate();
        assertTrue(Utilities.containsErrorNode(results, "There is a missing hmlid node underneath the hml node."));
        
        //1.2.a
        xml = Utilities.readXmlResource("/org/nmdp/miring/hml/Element1.no.reportingcenter.xml");
        results = new MiringValidator(xml).validate();
        assertTrue(Utilities.containsErrorNode(results, "There is a missing reporting-center node underneath the hml node."));
        
        //1.3.a
        xml = Utilities.readXmlResource("/org/nmdp/miring/hml/Element1.sbtngs.missing.testid.xml");
        results = new MiringValidator(xml).validate();
        assertTrue(Utilities.containsErrorNode(results, "The node sbt-ngs is missing a test-id attribute."));
        assertTrue(Utilities.containsErrorNode(results, "The node sbt-ngs is missing a test-id-source attribute."));
        
        //1.5.a
        xml = Utilities.readXmlResource("/org/nmdp/miring/hml/Element1.missing.rawreads.xml");
        results = new MiringValidator(xml).validate();
        assertTrue(Utilities.containsErrorNode(results, "There is a missing raw-reads node underneath the sbt-ngs node."));
        
        //1.5.b
        xml = Utilities.readXmlResource("/org/nmdp/miring/hml/Element1.rawreads.no.availability.xml");
        String noAvailResults = new MiringValidator(xml).validate();
        assertTrue(Utilities.containsErrorNode(noAvailResults, "The node raw-reads is missing a availability attribute."));
        
        xml = Utilities.readXmlResource("/org/nmdp/miring/hml/Element1.rawreads.availability.xml");
        String availResults = new MiringValidator(xml).validate();
        assertFalse(Utilities.containsErrorNode(availResults, "The node raw-reads is missing a availability attribute."));
    }

    @Test
    public void testMiringElement2Tier1()
    {
        logger.debug("starting testMiringElement2Tier1");
        
        //2.1.a
        String xml = Utilities.readXmlResource("/org/nmdp/miring/hml/Element2.no.alleleassignment.xml");
        String results = new MiringValidator(xml).validate();
        assertTrue(Utilities.containsErrorNode(results, "There is a missing allele-assignment node underneath the typing node."));
        
        //2.1.b and 2.1.c
        xml = Utilities.readXmlResource("/org/nmdp/miring/hml/Element2.no.alleleassignment.xml");
        results = new MiringValidator(xml).validate();
        assertTrue(Utilities.containsErrorNode(results, "The node allele-assignment is missing a allele-db attribute."));
        assertTrue(Utilities.containsErrorNode(results, "The node allele-assignment is missing a allele-version attribute."));
        
        //2.2.b
        xml = Utilities.readXmlResource("/org/nmdp/miring/hml/Element2.referencesequence.missing.attributes.xml");
        results = new MiringValidator(xml).validate();
        assertTrue(Utilities.containsErrorNode(results, "The node reference-sequence is missing a name attribute."));
        assertTrue(Utilities.containsErrorNode(results, "The node reference-sequence is missing a start attribute."));
        assertTrue(Utilities.containsErrorNode(results, "The node reference-sequence is missing a end attribute."));
        assertTrue(Utilities.containsErrorNode(results, "The node reference-sequence is missing a accession attribute."));
        assertTrue(Utilities.containsErrorNode(results, "The node reference-sequence is missing a uri attribute."));
        
        //2.3.b
        xml = Utilities.readXmlResource("/org/nmdp/miring/hml/Element2.referencesequence.missing.attributes.xml");
        results = new MiringValidator(xml).validate();
        assertTrue(Utilities.containsErrorNode(results, "The node reference-database is missing a curated attribute."));
    }

    @Test
    public void testMiringElement3Tier1()
    {
        logger.debug("starting testMiringElement3Tier1");
        //3.1.a
        String xml = Utilities.readXmlResource("/org/nmdp/miring/hml/Element3.glstring.missing.xml");
        String results = new MiringValidator(xml).validate();
        assertTrue(Utilities.containsErrorNode(results, "There is a missing glstring node underneath the allele-assignment node."));
        assertFalse(Utilities.containsErrorNode(results, "A glstring node should have one of either A) A uri attribute specifying the location of a valid glstring, or B) Text containing a valid glstring."));
        
        xml = Utilities.readXmlResource("/org/nmdp/miring/hml/Element3.glstring.text.xml");
        results = new MiringValidator(xml).validate();
        assertFalse(Utilities.containsErrorNode(results, "There is a missing glstring node underneath the allele-assignment node."));
        assertFalse(Utilities.containsErrorNode(results, "A glstring node should have one of either A) A uri attribute specifying the location of a valid glstring, or B) Text containing a valid glstring."));
    }

    @Test
    public void testMiringElement4Tier1()
    {
        logger.debug("starting testMiringElement4Tier1");
        
        //4.2.a and 4.2.3.a and 4.2.4.a 
        //and 4.2.5.a and 4.2.7.a
        String xml = Utilities.readXmlResource("/org/nmdp/miring/hml/Element4.CSB.bad.attributes.xml");
        String badResults = new MiringValidator(xml).validate();
        assertTrue(Utilities.containsErrorNode(badResults, "The node consensus-sequence-block is missing a description attribute."));
        assertTrue(Utilities.containsErrorNode(badResults, "The node consensus-sequence-block is missing a start attribute."));
        assertTrue(Utilities.containsErrorNode(badResults, "The node consensus-sequence-block is missing a end attribute."));
        assertTrue(Utilities.containsErrorNode(badResults, "The node consensus-sequence-block is missing a phase-set attribute."));
        assertTrue(Utilities.containsErrorNode(badResults, "The node consensus-sequence-block is missing a expected-copy-number attribute."));
        assertTrue(Utilities.containsErrorNode(badResults, "The node consensus-sequence-block is missing a continuity attribute."));
        
        xml = Utilities.readXmlResource("/org/nmdp/miring/hml/Element4.CSB.good.attributes.xml");
        String goodResults = new MiringValidator(xml).validate();
        assertFalse(Utilities.containsErrorNode(goodResults, "The node consensus-sequence-block is missing a description attribute."));
        assertFalse(Utilities.containsErrorNode(goodResults, "The node consensus-sequence-block is missing a start attribute."));
        assertFalse(Utilities.containsErrorNode(goodResults, "The node consensus-sequence-block is missing a end attribute."));
        assertFalse(Utilities.containsErrorNode(goodResults, "The node consensus-sequence-block is missing a phase-set attribute."));
        assertFalse(Utilities.containsErrorNode(goodResults, "The node consensus-sequence-block is missing a expected-copy-number attribute."));
        assertFalse(Utilities.containsErrorNode(goodResults, "The node consensus-sequence-block is missing a continuity attribute."));
    }

    public void testMiringElement5Tier1()
    {
        logger.debug("starting testMiringElement5Tier1");
        
        //5.3.a and 5.6.a and 5.7.a
        String xml = Utilities.readXmlResource("/org/nmdp/miring/hml/Element5.variant.bad.attributes.xml");
        String badResults = new MiringValidator(xml).validate();
        assertTrue(Utilities.containsErrorNode(badResults, "The node variant is missing a id attribute."));
        assertTrue(Utilities.containsErrorNode(badResults, "The node variant is missing a quality-score attribute."));
        assertTrue(Utilities.containsErrorNode(badResults, "The node variant is missing a filter attribute."));

        xml = Utilities.readXmlResource("/org/nmdp/miring/hml/Element5.variant.good.attributes.xml");
        String goodResults = new MiringValidator(xml).validate();
        assertFalse(Utilities.containsErrorNode(goodResults, "The node variant is missing a id attribute."));
        assertFalse(Utilities.containsErrorNode(goodResults, "The node variant is missing a quality-score attribute."));
        assertFalse(Utilities.containsErrorNode(goodResults, "The node variant is missing a filter attribute."));
    } 
}