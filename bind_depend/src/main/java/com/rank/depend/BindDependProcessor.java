package com.rank.depend;

import com.google.auto.service.AutoService;
import com.rank.binddepend_annotation.BindDepend;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

@AutoService(Processor.class)
public class BindDependProcessor extends AbstractProcessor {

    private Elements elementUtils;
    private Messager messager;

    private Map<Integer, Map<String, DependInfo>> mProxyMap = new HashMap<>();

    @Override

    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elementUtils = processingEnv.getElementUtils();
        messager = processingEnv.getMessager();
    }


    @Override
    public Set<String> getSupportedAnnotationTypes() {
        HashSet<String> supportTypes = new LinkedHashSet<>();
        supportTypes.add(BindDepend.class.getCanonicalName());
        return supportTypes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }


    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        mProxyMap.clear();
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "action");
        final Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(BindDepend.class);
        for (Element element : elements) {

            TypeElement typeElement = (TypeElement) element;
            String qulifiedName = typeElement.getQualifiedName().toString();
            BindDepend bindDepend = typeElement.getAnnotation(BindDepend.class);
            final int type = bindDepend.value();
            Map<String, DependInfo> typeMap = mProxyMap.get(type);
            if (typeMap == null) {
                typeMap = new HashMap<>();
                mProxyMap.put(type, typeMap);
            }
            DependInfo dependInfo = typeMap.get(qulifiedName);
            if (dependInfo == null) {
                dependInfo = new DependInfo(typeElement, elementUtils);
                typeMap.put(qulifiedName, dependInfo);
            }
            dependInfo.setBuildType(type);
        }

        for (int type : mProxyMap.keySet()) {
            final Map<String, DependInfo> dependInfos = mProxyMap.get(type);
            try {
                JavaFileObject javaFileObject = null;
                Writer writer = null;
                StringBuilder methodStringBuilder = new StringBuilder();
                List<String> classNames = new ArrayList<>();
                String packageName = null;
                for (String classname : dependInfos.keySet()) {
                    final DependInfo dependInfo = dependInfos.get(classname);
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, dependInfo.getProxyClassFullName());
                    if (javaFileObject == null) {
                        javaFileObject = processingEnv.getFiler().createSourceFile(dependInfo.getProxyClassFullName(), dependInfo.getTypeElement());
                        writer = javaFileObject.openWriter();
                        classNames = new ArrayList<>();
                        packageName = dependInfo.getPackageName();
                    }
                    classNames.add(classname);
                    dependInfo.generateMethods(methodStringBuilder);
                }
                writer.write(DependInfo.generateJavaCodeHeader(packageName, type, classNames));
                writer.write(methodStringBuilder.toString() + "}");
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "output error");
            }
        }
        return true;
    }

    private boolean checkAnnotationValid(Element annotatedElement, Class clazz) {
        if (annotatedElement.getKind() != ElementKind.CLASS) {
            error(annotatedElement, "%s must be declared on class.", clazz.getSimpleName());
            return false;
        }
        if (ClassValidator.isPrivate(annotatedElement)) {
            error(annotatedElement, "%s() must can not be private.", annotatedElement.getSimpleName());
            return false;
        }

        return true;
    }

    private void error(Element element, String message, Object... args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message, element);
    }
}
