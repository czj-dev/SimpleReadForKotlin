<#import "root://activities/MVVMBuilder/globals.xml.ftl" as gb>
package ${modelPackageName}

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.rank.basiclib.Constant.ClassType.VIEWMODEl
import com.rank.binddepend_annotation.BindDepend
<#if (needRepository)>
import ${repositoryPackageName}.${repositoryClass}
</#if>
import javax.inject.Inject

@BindDepend(VIEWMODEl)
class ${viewModelClass} @Inject constructor(application: Application <#if (needRepository)>, private val repository: ${repositoryClass}</#if>) : AndroidViewModel(application) {

}