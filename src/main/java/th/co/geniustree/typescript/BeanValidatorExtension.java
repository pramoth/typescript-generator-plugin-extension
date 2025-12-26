package th.co.geniustree.typescript;

import cz.habarta.typescript.generator.Logger;
import cz.habarta.typescript.generator.Settings;
import cz.habarta.typescript.generator.emitter.EmitterExtension;
import cz.habarta.typescript.generator.emitter.EmitterExtensionFeatures;
import cz.habarta.typescript.generator.emitter.TsModel;
import jakarta.validation.constraints.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

public class BeanValidatorExtension extends EmitterExtension {
    private static Logger logger = new Logger();

    @Override
    public EmitterExtensionFeatures getFeatures() {
        final EmitterExtensionFeatures features = new EmitterExtensionFeatures();
        return features;
    }

    @Override
    public void emitElements(Writer writer, Settings settings, boolean exportKeyword, TsModel model) {
        String result = model.getBeans().stream()
                .filter(e -> e.getOrigin() != null)
                .map(e -> {
                    logger.verbose("class for generate :" + e.getOrigin());
                    String enumObject = createBeanValidatorSchema(e.getOrigin());
                    if (enumObject == null) {
                        return "//skip " + e.getName().getSimpleName() + " because it not have getValue() method";
                    } else {
                        if(!enumObject.isBlank()){
                            String tsVariableName = e.getOrigin().getSimpleName()+"s";
                            String comment = "// const "+tsVariableName+" is validation constraint for java class "+e.getOrigin().getSimpleName()+" \n";
                            return comment+"export const " + tsVariableName + " = { " + enumObject + "}";
                        }else{
                            return "//skip " + e.getName().getSimpleName() + " java class. no validator annotation found";
                        }
                    }
                }).collect(Collectors.joining("\n"));
        writer.writeIndentedLine(result);
    }

    String createBeanValidatorSchema(Class clazz) {
        StringBuilder builder = new StringBuilder();
        try {
            Arrays.stream(clazz.getDeclaredFields())
                    .filter(e -> e.getAnnotation(Size.class) != null ||
                            e.getAnnotation(Min.class) != null ||
                            e.getAnnotation(Max.class) != null ||
                            e.getAnnotation(NotBlank.class) != null ||
                            e.getAnnotation(NotNull.class) != null ||
                            e.getAnnotation(Digits.class) != null
                            )
                    .forEach(e -> {
                        builder.append(e.getName());
                        builder.append(":{");
                        builder.append(doSizeAnnotation(e));
                        builder.append(doMinAnnotation(e));
                        builder.append(doMaxAnnotation(e));
                        builder.append(doNotBlankAnnotation(e));
                        builder.append(doNotNullAnnotation(e));
                        builder.append(doDigitsAnnotation(e));
                        builder.append("},");
                    });
            return builder.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private String doDigitsAnnotation(Field declaredField) {
        Digits annotation = declaredField.getAnnotation(Digits.class);
        if (annotation != null) {
            return "digitsInteger:\"" + annotation.integer() + "\",digitsFraction:\""+ annotation.fraction() + "\"";
        }
        return "";
    }

    private String doNotNullAnnotation(Field declaredField) {
        NotNull annotation = declaredField.getAnnotation(NotNull.class);
        if (annotation != null) {
            return "notNull:\"" + annotation.message() + "\",";
        }
        return "";
    }

    private String doNotBlankAnnotation(Field declaredField) {
        NotBlank annotation = declaredField.getAnnotation(NotBlank.class);
        if (annotation != null) {
            return "notBlank:\"" + annotation.message() + "\",";
        }
        return "";
    }

    private String doMaxAnnotation(Field declaredField) {
        Max annotation = declaredField.getAnnotation(Max.class);
        if (annotation != null) {
            return "max:" + annotation.value() + ",";
        }
        return "";
    }

    private String doMinAnnotation(Field declaredField) {
        Min annotation = declaredField.getAnnotation(Min.class);
        if (annotation != null) {
            return "min:" + annotation.value() + ",";
        }
        return "";
    }

    private String doSizeAnnotation(Field declaredField) {
        Size annotation = declaredField.getAnnotation(Size.class);
        if (annotation != null) {
            return "sizeMin:" + annotation.min() + ",sizeMax:" + annotation.max() + ",";
        }
        return "";
    }
}
