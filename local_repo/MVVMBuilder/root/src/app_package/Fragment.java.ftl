
<#import "root://activities/MVVMBuilder/globals.xml.ftl" as gb>
<#assign fragmentName>${pageName}Fragment</#assign>
<#assign dataBindingName>${gb.getName(fragmentLayoutName)}Binding</#assign>
package ${fragmentPackageName};
import ${packageName}.databinding.${dataBindingName};
<#if needRouter>
import com.alibaba.android.arouter.facade.annotation.Route;
</#if>
import javax.inject.Inject;
import com.rank.basiclib.Constant
import com.rank.basiclib.di.Injectable;
import com.rank.basiclib.ext.CompatFragment;
import com.rank.basiclib.annotations.BindDepend;
<#if (needModel)>
import ${modelPackageName}.${viewModelClass};
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
</#if>
<@gb.fileHeader />
<#if needRouter>
@Route(path = "${RouterName}")
</#if>
@BindDepend(Constant.ClassType.FRAGMENT)
class ${fragmentName} extends CompatFragment<${dataBindingName}>() implements Injectable {
    
    <#if (needModel)>
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    </#if>


    @Override
    public int getLayoutId() {
        return  R.layout.${fragmentLayoutName};
    }

 
    @Override
    public void initViews() {
        <#if (needModel)>
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(${viewModelClass}.class);
        </#if>
    }

    @Override
    public void initEvents() {

    }
}