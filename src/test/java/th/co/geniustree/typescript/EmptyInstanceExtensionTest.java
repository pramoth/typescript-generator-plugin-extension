package th.co.geniustree.typescript;

import cz.habarta.typescript.generator.*;
import org.junit.Test;

public class EmptyInstanceExtensionTest {
    @Test
    public void test(){
        final Settings settings = TestUtils.settings();
        settings.outputFileType= TypeScriptFileType.implementationFile;
        settings.extensions.add(new EmptyInstanceExtension());
        final String output = new TypeScriptGenerator(settings).generateTypeScript(Input.from(EmptyBean.class));
        System.out.println(output);
    }
}
