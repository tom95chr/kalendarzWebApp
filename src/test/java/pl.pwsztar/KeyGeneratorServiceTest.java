package pl.pwsztar;

import org.junit.Assert;
import org.junit.Test;
import pl.pwsztar.services.KeyGeneratorService;

public class KeyGeneratorServiceTest {

    private KeyGeneratorService keyGeneratorService = new KeyGeneratorService();

    @Test
    public void generateWhenParamIsOk(){
        Assert.assertEquals("7ced54bb654ec9a4070c2fbe154fd146",keyGeneratorService.generate(
                "tom-chr@wp.pl-12345678900987654321"));
    }

    @Test
    public void generateWhenParamIsEmpty(){
        Assert.assertEquals("d41d8cd98f00b204e9800998ecf8427e",keyGeneratorService.generate(""));
    }

    @Test
    public void generateWhenParamIsNull(){
        Assert.assertEquals(null, keyGeneratorService.generate(null));
    }
}
