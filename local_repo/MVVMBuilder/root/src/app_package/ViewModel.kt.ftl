<#import "root://activities/MVVMBuilder/globals.xml.ftl" as gb>
package ${modelPackageName}

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import javax.inject.Inject


class ${viewModelClass} @Inject constructor(application: Application) : AndroidViewModel(application) {

}