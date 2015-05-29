/*

    MiringValidator  Semantic Validator for MIRING compliant HML
    Copyright (c) 2014-2015 National Marrow Donor Program (NMDP)

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
package test.java.miringvalidatortest;

import static org.junit.Assert.*;
import main.java.miringvalidator.MiringValidator;
import main.java.miringvalidator.SchemaValidator;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Test;

public class MiringValidatorTest
{
    private static final Logger logger = LogManager.getLogger(MiringValidatorTest.class);

    @Test
    public void testMiringValidator()
    {
        logger.debug("testMiringValidator");
        /*MiringValidator myValidator = new MiringValidator("<xml></xml>");
        assert(myValidator!=null);
        //myValidator.validate();
        assert(myValidator!=null);
        
        String report = myValidator.getReport();
        assert(report!=null);
        assert(report!=null);
        assert(report.length() > 0);*/
        
    }


}
