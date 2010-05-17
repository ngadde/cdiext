package pl.softwaremill.cdiext.security.test;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.Archives;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.testng.annotations.Test;
import pl.softwaremill.cdiext.el.ELEvaluator;
import pl.softwaremill.cdiext.security.SecureResult;
import pl.softwaremill.cdiext.security.SecurityConditionException;
import pl.softwaremill.cdiext.test.util.ArquillianUtil;

import javax.inject.Inject;

/**
 * @author Adam Warski (adam at warski dot org)
 */
public class SecureResultTest extends Arquillian {
    @Deployment
    public static JavaArchive createTestArchive() {
        JavaArchive ar = Archives.create("test.jar", JavaArchive.class)
                .addPackage(SecureResultTest.class.getPackage())
                .addPackage(SecureResult.class.getPackage())
                .addPackage(ELEvaluator.class.getPackage());

        ar = ArquillianUtil.addTestBeansXml(ar);
        ar = ArquillianUtil.addExtensionsFromApp(ar);

        return ar;
    }

    @Inject
    private SecureResultBean secureResultBean;

    @Test
    public void testDirectSecurePass() {
        secureResultBean.method1("a");
    }

    @Test(expectedExceptions = SecurityConditionException.class)
    public void testDirectSecureFail() {
        secureResultBean.method1("b");
    }
}