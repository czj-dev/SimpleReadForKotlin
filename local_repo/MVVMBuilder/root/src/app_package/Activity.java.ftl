<#import "root://activities/MVVMBuilder/globals.xml.ftl" as gb>
<#assign activityName>${pageName}Activity</#assign>
<#assign dataBindingName>${gb.getName(activityLayoutName)}Binding</#assign>
package ${ativityPackageName};
<#if needRouter>
import com.alibaba.android.arouter.facade.annotation.Route;
</#if>
import com.rank.basiclib.di.Injectable;
import com.rank.basiclib.ext.CompatActivity;
import ${packageName}.databinding.${dataBindingName};
import javax.inject.Inject;
<#if (needModel)>
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import ${modelPackageName}.${viewModelClass}
</#if>
import ${packageName}.R;

<@gb.fileHeader />
<#if needRouter>
@Route(path = "${RouterName}")
</#if>
public class  ${activityClass}  extends CompatActivity<${dataBindingName}> implements Injectable {

<#if (needModel)>
    @Inject
    ViewModelProvider.Factory viewModelFactory;
</#if>
    ${viewModelClass} viewModel;

    @Override
    public int getLayoutId() {
        return  R.layout.${activityLayoutName};
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