<?xml version="1.0"?>
<#import "root://activities/common/kotlin_macros.ftl" as kt>
<recipe>
<@kt.addAllKotlinDependencies />
<#if needActivity>
    <merge from="root/AndroidManifest.xml.ftl"
           to="${escapeXmlAttribute(manifestOut)}/AndroidManifest.xml" />
</#if>

<#if needActivity >
    <instantiate from="root/res/layout/simple.xml.ftl"
                 to="${escapeXmlAttribute(resOut)}/layout/${activityLayoutName}.xml" />
</#if>

<#if needFragment>
    <instantiate from="root/res/layout/simple.xml.ftl"
                 to="${escapeXmlAttribute(resOut)}/layout/${fragmentLayoutName}.xml" />
</#if>

<#if needActivity>
    <instantiate from="root/src/app_package/Activity.${ktOrJavaExt}.ftl"
                   to="${projectOut}/src/main/java/${slashedPackageName(ativityPackageName)}/${pageName}Activity.${ktOrJavaExt}" />
    <open file="${projectOut}/src/main/java/${slashedPackageName(ativityPackageName)}/${pageName}Activity.${ktOrJavaExt}" />
</#if>

<#if needFragment>
    <instantiate from="root/src/app_package/Fragment.${ktOrJavaExt}.ftl"
                   to="${projectOut}/src/main/java/${slashedPackageName(fragmentPackageName)}/${pageName}Fragment.${ktOrJavaExt}" />
    <open file="${projectOut}/src/main/java/${slashedPackageName(fragmentPackageName)}/${pageName}Fragment.${ktOrJavaExt}" />
</#if>

<#if needModel>
    <instantiate from="root/src/app_package/ViewModel.${ktOrJavaExt}.ftl"
                   to="${projectOut}/src/main/java/${slashedPackageName(modelPackageName)}/${pageName}ViewModel.${ktOrJavaExt}" />
</#if>
</recipe>
