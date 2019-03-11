### 如何新建 Module

1. 使用 Android Studiou 的 模块功能创建一个 Android Libray (new -> Module -> Android Library)

2.  在 Module 的 build.gradle 引用 `common_component_build.gradle` 以及增加资源名前缀限制和组件化构建配置，具体如下：

   ```groovy
   apply from:"../common_component_build.gradle"
   android {
       resourcePrefix "wan_" //给 Module 内的资源名增加前缀, 避免资源名冲突
   }
   //组件化的 module 必要的声明
   combuild {
       applicationName = 'com.rank.basiclib.application.BaseApplication'
       //是否通过字节码自动注册
       isRegisterCompoAuto = true
   }
   ```

3. 在 Module 项目更目录下增加 gradle 配置文件 `gradle.properties` ，内容如下 :

   ```groovy
   # isRunAlone 来标示是否单独调试，如果开启需要配置 gradle 脚本的 combuild 属性
   isRunAlone=false
   #声明 debug 模式下依赖的组件
   #debugComponent=sharecomponent
   #声明 release 模式下依赖的组件
   #compileComponent=sharecomponent
   ```

4. 本项目中只使用一个 Application 即使组件需要单独调试运行或需要使用 Application 类的功能也仍不需要自定义 Application，而是创建一个 AppLifecycle 的实现类通过 AutoService 注册进 BaseApplication 中。AppLifecycle 还要负责 Dagger2 插件信息的收集、组件下网络通用拦截器、以及通用异常处理的实现。在 basiclib 中提供了一个默认的实现 BaseAppLifecycle ，平常创建 Lifecycle 可以直接继承。

   ```java
   @AutoService(AppLifecycle.class)
   public class AppLifecycleImpl extends BaseAppLifecycle {

       @Override
       public void onCreate(@NonNull @NotNull BaseApplication application) {

       }

   }

   ```

###

### 如何新建页面(Activity、Fragment)

页面通过 Android Studio 的 `Template  `功能来快速创建一个新的页面结构。

1. 首先将模板文件(**local_repo/MVVMBuilder**)放到 Android Studio 中的模板存放路径中。

   模版存放路径 :

   - Windows : AS安装目录/plugins/android/lib/templates/activities

   - Mac : /Applications/Android Studio.app/Contents/plugins/android/lib/templates/activities

2. 重启 Android  Studio

3. 在完整的 packageName 处右键选择新建 Activity 的时候选择模板的时候选择 **MVVMBuilder**

4. 根据选择按需创建页面。

5. 新建页面后编译选择编译，会生成 DataBinding 和 Dagger2 的依赖类，这时我们需要创建一个 Componet 来声明当前组件的 dagger2依赖,才能正常的使用项目。**ModuleComponent 只需要声明一次，之后新建页面便可以直接使用了**，在 `di`包中声明一个 ModuleComponent，内容如下：

   ```kotlin
   @ActivityScope
   @Component(
       modules = [
           AndroidInjectionModule::class,
           BindActivityModule::class,
           BindFragmentModule::class,
           BindViewModelModule::class
       ],
       dependencies = [AppComponent::class])
   interface ModuleComponent {

       fun viewModules(): Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>

       fun inject(app: AppLifecycleImpl)
   }
   ```

   **要注意 BindActivityModule 和 BindViewModel 不要引用到别的组件下的同名类了，如果没有创建过 Fragment 是没有 BindFragmentModule 的生成类的，等第一次使用的时候自行加上**



### 组件如何单独运行

将项目 `gradle.properties `   文件中的 `isRunAlone` 属性设置为 true 即可单独运行，但是除了 app 组件外其它的组件单独运行需要创建自己的清单文件，我们需要在组件的 `main` 目录下创建一个 `runlone` 包来放置组件自己的 Manifest 文件以及独立运行所需要的一些页面、资源等，gradle plugin 会自动读取该目录下的信息。**Application 仍然不需要自行创建而是使用 basiclib 中的 BaseApplication**

该功能使用的来自 [JIMU](https://github.com/mqzhangw/JIMU) 的 Gradle plugin 模块



### 组件、页面之间如何通信

使用阿里的[ARouter](https://github.com/alibaba/ARouter/blob/master/README_CN.md) ，具体的使用参考文档。需要一提的是组件之间通讯所需要的 Service，javaBean 需按规范下沉到 service Module 种

