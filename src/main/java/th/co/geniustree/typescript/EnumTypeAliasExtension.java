package th.co.geniustree.typescript;

import cz.habarta.typescript.generator.Settings;
import cz.habarta.typescript.generator.emitter.EmitterExtension;
import cz.habarta.typescript.generator.emitter.EmitterExtensionFeatures;
import cz.habarta.typescript.generator.emitter.TsModel;

import java.util.stream.Collectors;

public class EnumTypeAliasExtension extends EmitterExtension {
    @Override
    public EmitterExtensionFeatures getFeatures() {
        final EmitterExtensionFeatures features = new EmitterExtensionFeatures();
        return features;
    }

    @Override
    public void emitElements(Writer writer, Settings settings, boolean exportKeyword, TsModel model) {
        String enums = model.getTypeAliases().stream().map(e -> "\""+e.getName().getSimpleName()+"\"").collect(Collectors.joining(" | "));
        writer.writeIndentedLine("export type AllEnumClass = "+enums);
    }
}
