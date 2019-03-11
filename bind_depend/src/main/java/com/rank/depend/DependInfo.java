package com.rank.depend;

import java.util.List;

import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/3/5
 *     desc  :
 * </pre>
 */
class DependInfo {

    public static final int ACTIVITY = 0;
    public static final int FRAGMENT = 1;
    public static final int VIEW_MODEL = 2;
    private final TypeElement typeElement;
    private final Elements elements;

    private String packageName;
    private String className;

    private int buildType;

    //BindFragmentModule
    //BindViewModule
    //BindActivityModule

    DependInfo(TypeElement typeElement, Elements elements) {
        this.typeElement = typeElement;
        this.elements = elements;
        final PackageElement packageElement = elements.getPackageOf(typeElement);
        packageName = packageElement.getQualifiedName().toString();
        className = ClassValidator.getClassName(typeElement, packageName);
    }

    public static String generateJavaCodeHeader(String packageName, int buildType, List<String> importNames) {
        StringBuilder builder = new StringBuilder();
        builder.append("// Generated code. Do not modify!\n");
        builder.append("package ").append(adjustPackageName(packageName)).append(";").append('\n');
        generateImportByType(builder, buildType);
        generateImports(builder, importNames);
        builder.append('\n');
        builder.append("@Module");
        builder.append('\n');
        builder.append("public abstract class ").append(getProxyClassName(buildType));
        builder.append("{");
        return builder.toString();
    }

    private static void generateImports(StringBuilder builder, List<String> importNames) {
        for (String name : importNames) {
            builder.append(String.format("import %s;", name)).append("\n");
        }
    }


    private static void generateImportByType(StringBuilder builder, int buildType) {
        switch (buildType) {
            case ACTIVITY:
            case FRAGMENT:
                builder.append("import dagger.Module;").append("\n");
                builder.append("import dagger.android.ContributesAndroidInjector;").append("\n");
                break;
            case VIEW_MODEL:
                builder.append("import com.rank.basiclib.di.ViewModelKey;").append("\n");
                builder.append("import androidx.lifecycle.ViewModel;").append("\n");
                builder.append("import dagger.Module;").append("\n");
                builder.append("import dagger.Binds;").append("\n");
                builder.append("import dagger.multibindings.IntoMap;").append("\n");
                break;
        }
    }

    public void generateMethods(StringBuilder builder) {
        switch (buildType) {
            case ACTIVITY:
            case FRAGMENT:
                builder.append("\n@ContributesAndroidInjector()\n");
                builder.append(String.format("abstract %s contributes", className))
                        .append(className)
                        .append("();\n");
                break;
            case VIEW_MODEL:
                builder.append("\n@Binds");
                builder.append("\n@IntoMap");
                builder.append(String.format("\n@ViewModelKey(%s.class)", className));
                builder.append("\nabstract ViewModel bind")
                        .append(className)
                        .append(String.format("(%s viewModel);\n", className));
                break;
        }
    }

    public void setBuildType(int buildType) {
        this.buildType = buildType;
    }

    public String getProxyClassFullName() {
        return adjustPackageName(packageName) + "." + getProxyClassName(buildType);
    }

    private static String adjustPackageName(String packageName) {
        final String[] packages = packageName.split("\\.");
        String header;
        if (packages.length >= 3) {
            header = packages[0] + "." + packages[1] + "." + packages[2];
        } else {
            header = packageName;
        }
        return header + ".bind";
    }

    private static String getProxyClassName(int buildType) {
        switch (buildType) {
            case ACTIVITY:
                return "BindActivityModule";
            case FRAGMENT:
                return "BindFragmentModule";
            case VIEW_MODEL:
                return "BindViewModelModule";
            default:
                return "";
        }
    }


    public TypeElement getTypeElement() {
        return typeElement;
    }

    public String getPackageName() {
        return packageName;
    }
}
