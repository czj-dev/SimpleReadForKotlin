<#import "root://activities/MVVMBuilder/globals.xml.ftl" as gb>
package ${repositoryPackageName}

import com.rank.basiclib.http.NetworkManager
import javax.inject.Inject

class ${repositoryClass} @Inject constructor(private val networkManager: NetworkManager) {

}