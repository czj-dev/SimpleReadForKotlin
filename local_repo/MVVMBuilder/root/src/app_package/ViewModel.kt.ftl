<#import "root://activities/MVVMBuilder/globals.xml.ftl" as gb>
package ${modelPackageName}

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import javax.inject.Inject
import com.rank.basiclib.Constant
import com.rank.basiclib.annotations.BindDepend

@BindDepend(Constant.ClassType.VIEW_MODEL)
class ${viewModelClass} @Inject constructor(application: Application) : AndroidViewModel(application) {

}