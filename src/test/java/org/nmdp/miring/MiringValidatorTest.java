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
import org.nmdp.miring.Utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Test;

public class MiringValidatorTest
{
    Logger logger = LoggerFactory.getLogger(MiringValidatorTest.class);

    @Test
    public void testMiringValidator()
    {
        logger.debug("starting testMiringValidator");
        
        String demoGoodXML = Utilities.readXmlResource("/org/nmdp/miring/hml/demogood.xml");
        String demoBadXML = Utilities.readXmlResource("/org/nmdp/miring/hml/demobad.xml");
        
        MiringValidator goodValidator = new MiringValidator(demoGoodXML);
        MiringValidator badValidator = new MiringValidator(demoBadXML);
        
        String goodValidatorResults = goodValidator.validate();
        String badValidatorResults = badValidator.validate();

        assertFalse(Utilities.containsErrorNode(goodValidatorResults, "There is a missing hmlid node underneath the hml node."));
        assertTrue(Utilities.containsErrorNode(badValidatorResults, "There is a missing hmlid node underneath the hml node."));
        
        assertFalse(Utilities.containsErrorNode(goodValidatorResults, "The node variant is missing a quality-score attribute."));
        assertTrue(Utilities.containsErrorNode(badValidatorResults, "The node variant is missing a quality-score attribute."));
    }
}
