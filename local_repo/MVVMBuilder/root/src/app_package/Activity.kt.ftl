
<#import "root://activities/MVVMBuilder/globals.xml.ftl" as gb>
<#assign activityName>${pageName}Activity</#assign>
<#assign dataBindingName>${gb.getName(activityLayoutName)}Binding</#assign>

package ${ativityPackageName}

import javax.inject.Inject
import com.rank.basiclib.di.Injectable
import com.rank.basiclib.ext.CompatActivity
import com.rank.binddepend_annotation.BindDepend
import com.rank.basiclib.Constant.ClassType.ACTIVITY;
<#if (needModel)>
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import ${modelPackageName}.${viewModelClass}
</#if>
<#if needRouter>
import com.alibaba.android.arouter.facade.annotation.Route
</#if>
import ${packageName}.databinding.${dataBindingName}

import ${packageName}.R


<@gb.fileHeader />
<#if needRouter>
@Route(path = "${RouterName}")
</#if>
@BindDepend(ACTIVITY)
class ${activityClass} : CompatActivity<${dataBindingName}>(), Injectable {

    override val layoutId = R.layout.${activityLayoutName}
<#if (needModel)>
     @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(${viewModelClass}::class.java) }
</#if>
   
    override fun initViews() {
      
    }

    override fun initEvents() {

    }
}