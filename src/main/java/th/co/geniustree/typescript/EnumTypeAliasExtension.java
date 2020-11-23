package th.co.geniustree.typescript;

import cz.habarta.typescript.generator.Settings;
import cz.habarta.typescript.generator.emitter.EmitterExtension;
import cz.habarta.typescript.generator.emitter.EmitterExtensionFeatures;
import cz.habarta.typescript.generator.emitter.TsModel;

import java.lang.reflect.Method;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnumTypeAliasExtension extends EmitterExtension {
    @Override
    public EmitterExtensionFeatures getFeatures() {
        final EmitterExtensionFeatures features = new EmitterExtensionFeatures();
        return features;
    }

    @Override
    public void emitElements(Writer writer, Settings settings, boolean exportKeyword, TsModel model) {
        String enums = model.getTypeAliases().stream().map(e -> "\"" + e.getName().getSimpleName() + "\"").collect(Collectors.joining(" | "));
        writer.writeIndentedLine("export type AllEnumClass = " + enums);
        writer.writeIndentedLine("// Generate FomrFileType for Aqua Project");
        model.getOriginalStringEnums().stream().filter(e -> e.getName().getSimpleName().endsWith("FileType")).forEach(e -> {
            String body = e.getMembers().stream().map(m -> "  " + m.getPropertyName() + ": {fileType: '" + m.getEnumValue() + "' }").collect(Collectors.joining(",\n"));
            String instanceType = e.getName().getSimpleName() + "InstanceType";
            writer.writeIndentedLine("export type " + instanceType + " = {[P in " + e.getName().getSimpleName() + "]: {fileType: " + e.getName().getSimpleName() + "} }");
            writer.writeIndentedLine("export function  create" + e.getName().getSimpleName() + "(): " + instanceType + " { \nreturn {\n" + body);
            writer.writeIndentedLine(" }\n}");
        });
        writer.writeIndentedLine("// Enum description for Aqua Project");
        String result = model.getTypeAliases().stream()
                .map(e -> {
                    String enumObject = createEnum(e.getOrigin());
                    if (enumObject == null) {
                        return "//skip " + e.getName().getSimpleName()+ " because it not have getValue() method";
                    } else {
                        return "export const " + e.getOrigin().getSimpleName()+ " = {\n" + enumObject + "\n}";
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
