<?xml version="1.0"?>
<globals>
    <global id="hasNoActionBar" type="boolean" value="false" />
    <global id="parentActivityClass" value="" />
    <global id="excludeMenu" type="boolean" value="true" />
    <global id="isLauncher" type="boolean" value="false" />
    <global id="generateActivityTitle" type="boolean" value="false" />
    <global id="relativePackage" value="${ativityPackageName}" />
    <global id="activityClass" value="${pageName}Activity" />
    <global id="diPath" value="${packageName}.di" />
    <global id="viewModelClass" value="${pageName}ViewModel" />
    <#include "../common/common_globals.xml.ftl" />
</globals>

<#macro fileHeader>
/**
 * ================================================
 * Description:
 * <p>
 * Created by ${.now?string["MM/dd/yyyy HH:mm"]}
 * ================================================
 */
</#macro>

<#function getName name>
    <#assign replaceStr>${name?replace("_"," ")?capitalize}</#assign>
    <#assign connectStr>${replaceStr?replace(" ","")}</#assign>
    <#return connectStr>
</#function>