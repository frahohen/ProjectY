import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class UnitTestTemplate {
    @Test
    public void thisAlwaysPasses()
    {
        assertTrue(true);
    }

    @Test
    @Ignore
    public void thisIsIgnored()
    {
    }
}
