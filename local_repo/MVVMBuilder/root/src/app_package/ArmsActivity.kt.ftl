
<#import "root://activities/MVPArmsTemplate/globals.xml.ftl" as gb>
<#assign activityName>${pageName}Activity</#assign>
<#assign dataBindingName>${gb.getName(activityLayoutName)}Binding</#assign>

package ${ativityPackageName}

import android.content.Intent
import android.os.Bundle

import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import ${packageName}.databinding.${dataBindingName}

import ${packageName}.R


<@gb.fileHeader />
@Route(path = "${RouterName}")
class ${activityClass} : CompatActivity<dataBindingName>(), Injectable {

    override val layoutId: Int = R.layout.${gb.getName(activityLayoutName)};

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(${viewModelClass}::class.java) }

    override fun initViews() {
      
    }

    override fun initEvents() {

    }
}