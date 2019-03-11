<#import "root://activities/MVVMBuilder/globals.xml.ftl" as gb>
package ${modelPackageName}

import javax.inject.Inject;
import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import com.rank.binddepend_annotation.BindDepend;
import com.rank.basiclib.Constant.ClassType.VIEWMODEl;
<#if (needRepository)>
import ${repositoryPackageName}.${repositoryClass};
</#if>

@BindDepend(VIEWMODEl)
public class ${viewModelClass}  extends AndroidViewModel {
    
    private ${repositoryClass}  repository;

    @Inject
    public ${viewModelClass}(@NonNull Application application <#if (needRepository)> , ${repositoryClass}  repository</#if>) {
        super(application);
        this.repository=repository;
    }

}