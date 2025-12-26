package th.co.geniustree.typescript;

import cz.habarta.typescript.generator.Logger;
import cz.habarta.typescript.generator.Settings;
import cz.habarta.typescript.generator.emitter.EmitterExtension;
import cz.habarta.typescript.generator.emitter.EmitterExtensionFeatures;
import cz.habarta.typescript.generator.emitter.TsModel;

import java.lang.reflect.Method;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnumTypeAliasExtension extends EmitterExtension {
    private static Logger logger = new Logger();

    @Override
    public EmitterExtensionFeatures getFeatures() {
        final EmitterExtensionFeatures features = new EmitterExtensionFeatures();
        return features;
    }

    @Override
    public void emitElements(Writer writer, Settings settings, boolean exportKeyword, TsModel model) {
        String enums = model.getTypeAliases().stream().map(e -> "\"" + e.getName().getSimpleName() + "\"").collect(Collectors.joining(" | "));
        if(!enums.isEmpty()){
            writer.writeIndentedLine("export type AllEnumClass = " + enums);
        }
        writer.writeIndentedLine("// Enum description");
        String result = model.getTypeAliases().stream()
                .filter(e -> e.getOrigin() != null)
                .map(e -> {
                    logger.verbose("class for generate :" + e.getOrigin());
                    String enumObject = createEnum(e.getOrigin());
                    if (enumObject == null) {
                        return "//skip " + e.getName().getSimpleName() + " because it not have getValue() method";
                    } else {
                        return "export const " + e.getOrigin().getSimpleName() + " : {[p in " + e.getName().getSimpleName() + "] : string } = {\n" + enumObject + "\n}";
                    }
                }).collect(Collectors.joining("\n"));
        writer.writeIndentedLine(result);
    }

    String createEnum(Class clazz) {
        try {
            Object[] values = (Object[]) clazz.getMethod("values", null).invoke(null);
            Method getNameMethod = clazz.getMethod("name", null);
            try {
                Method getValueMethod = clazz.getMethod("getValue", null);
                return Stream.of(values).map(value -> getRow(getNameMethod, getValueMethod, value)).collect(Collectors.joining(",\n"));
            } catch (NoSuchMethodException e) {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private String getRow(Method getNameMethod, Method getValueMethod, Object value) {
        try {
            return "   " + getNameMethod.invoke(value) + ":'" + getValueMethod.invoke(value) + "'";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
