<#import "root://activities/MVVMBuilder/globals.xml.ftl" as gb>
package ${repositoryPackageName};

import com.rank.basiclib.http.NetworkManager;
import javax.inject.Inject;

public class ${repositoryClass} {
    
    private NetworkManager networkManager;

    @Inject
    public ${repositoryClass}(@NonNull NetworkManager networkManager) {
        this.networkManager=networkManager;
    }

}