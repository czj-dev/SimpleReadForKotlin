<#import "root://activities/MVVMBuilder/globals.xml.ftl" as gb>
package ${modelPackageName}

import android.app.Application;
import javax.inject.Inject;
import com.rank.basiclib.Constant;
import com.rank.basiclib.annotations.BindDepend;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

@BindDepend(Constant.ClassType.VIEW_MODEL)
public class ${viewModelClass}  extends AndroidViewModel {

    @Inject
    public DIDIViewModel(@NonNull Application application) {
        super(application);
    }

}