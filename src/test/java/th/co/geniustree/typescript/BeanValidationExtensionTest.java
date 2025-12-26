package th.co.geniustree.typescript;

import cz.habarta.typescript.generator.Input;
import cz.habarta.typescript.generator.Settings;
import cz.habarta.typescript.generator.TypeScriptGenerator;
import org.junit.Test;

public class BeanValidationExtensionTest {
    @Test
    public void test(){
        final Settings settings = TestUtils.settings();
        settings.extensions.add(new BeanValidatorExtension());
        final String output = new TypeScriptGenerator(settings).generateTypeScript(Input.from(MyBean.class));
        System.out.println(output);
    }
    @Test
    public void test2(){
        final Settings settings = TestUtils.settings();
        settings.extensions.add(new BeanValidatorExtension());
        final String output = new TypeScriptGenerator(settings).generateTypeScript(Input.from(MyBean.class));
        System.out.println(output);
    }

    @Test
    public void empty(){
        final Settings settings = TestUtils.settings();
        settings.extensions.add(new BeanValidatorExtension());
        final String output = new TypeScriptGenerator(settings).generateTypeScript(Input.from(EmptyBean.class));
        System.out.println(output);
    }
}
