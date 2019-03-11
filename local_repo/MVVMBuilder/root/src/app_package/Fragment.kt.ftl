
<#import "root://activities/MVVMBuilder/globals.xml.ftl" as gb>
<#assign fragmentName>${pageName}Fragment</#assign>
<#assign dataBindingName>${gb.getName(fragmentLayoutName)}Binding</#assign>
package ${fragmentPackageName}

<#if (needModel)>
import javax.inject.Inject
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import ${modelPackageName}.${viewModelClass}
</#if>
import com.rank.basiclib.di.Injectable
import com.rank.basiclib.ext.CompatFragment
import com.rank.binddepend_annotation.BindDepend;
import com.rank.basiclib.Constant.ClassType.FRAGMENT;
import ${packageName}.databinding.${dataBindingName}
<#if needRouter>
import com.alibaba.android.arouter.facade.annotation.Route
</#if>

<@gb.fileHeader />
<#if needRouter>
@Route(path = "${RouterName}")
</#if>
@BindDepend(FRAGMENT)
class ${fragmentName} : CompatFragment<${dataBindingName}>(), Injectable {

<#if (needModel)>
     @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(${viewModelClass}::class.java) }
</#if>

    override val layoutId = R.layout.${fragmentLayoutName}

    override fun initViews() {

    }

    override fun initEvents() {

    }
    
}