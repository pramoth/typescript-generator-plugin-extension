package th.co.geniustree.typescript;

import cz.habarta.typescript.generator.Logger;
import cz.habarta.typescript.generator.Settings;
import cz.habarta.typescript.generator.emitter.EmitterExtension;
import cz.habarta.typescript.generator.emitter.EmitterExtensionFeatures;
import cz.habarta.typescript.generator.emitter.TsModel;

import java.lang.reflect.Method;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnumTypeArrayExtension extends EmitterExtension {
    private static Logger logger = new Logger();

    @Override
    public EmitterExtensionFeatures getFeatures() {
        final EmitterExtensionFeatures features = new EmitterExtensionFeatures();
        return features;
    }

    @Override
    public void emitElements(Writer writer, Settings settings, boolean exportKeyword, TsModel model) {
        String result = model.getTypeAliases().stream()
                .filter(e -> e.getOrigin() != null)
                .map(e -> {
                    logger.verbose("class for generate :" + e.getOrigin());
                    String enumObject = createEnum(e.getOrigin());
                    if (enumObject == null) {
                        return "//skip " + e.getName().getSimpleName() + " because it not have getValue() method";
                    } else {
                        return "export const " + e.getOrigin().getSimpleName() + "Array : " + e.getName().getSimpleName() + "[]  = [" + enumObject + "]";
                    }
                }).collect(Collectors.joining("\n"));
        writer.writeIndentedLine(result);
    }

    String createEnum(Class clazz) {
        try {
            Object[] values = (Object[]) clazz.getMethod("values", null).invoke(null);
            Method getNameMethod = clazz.getMethod("name", null);
            return Stream.of(values).map(value -> getRow(getNameMethod, value)).collect(Collectors.joining(","));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private String getRow(Method getNameMethod, Object value) {
        try {
            return "'" + getNameMethod.invoke(value) + "'";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
