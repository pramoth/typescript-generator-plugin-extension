package th.co.geniustree.typescript;

import cz.habarta.typescript.generator.Settings;
import cz.habarta.typescript.generator.compiler.SymbolTable;
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
        writer.writeIndentedLine("// Generate FomrFileType for Aqua Project");
        model.getOriginalStringEnums().stream().filter(e->e.getName().getSimpleName().endsWith("FileType")).forEach(e->{
            String body = e.getMembers().stream().map(m -> "  "+m.getPropertyName() + ": {fileType: '" + m.getEnumValue() + "' }").collect(Collectors.joining(",\n"));
            String instanceType = e.getName().getSimpleName()+"InstanceType";
            writer.writeIndentedLine("export type "+instanceType+" = {[P in "+e.getName().getSimpleName()+"]: {fileType: "+e.getName().getSimpleName()+"} }");
            writer.writeIndentedLine("export function  create"+e.getName().getSimpleName()+"(): "+instanceType+" { \nreturn {\n"+body);
            writer.writeIndentedLine(" }\n}");
        });
    }
}
