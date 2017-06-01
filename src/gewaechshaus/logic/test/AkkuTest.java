package gewaechshaus.logic.test;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created by sunku on 01.06.2017.
 */
@RunWith(Arquillian.class)
public class AkkuTest {

    @Test
    public void getLadestand() throws Exception {


    }

    @Test
    public void setLadestand() throws Exception {
        assertTrue()

    }

    @Test
    public void istKritisch() throws Exception {
    }

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(gewaechshaus.logic.Akku.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

}
