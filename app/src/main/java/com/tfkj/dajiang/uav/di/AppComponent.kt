package debug.di


import com.mvp.tfkj.lib.di.ApplicationModule
import com.mvp.tfkj.lib.di.OkhttpModule
import com.mvp.tfkj.lib.di.RetrofitModule
import com.mvp.tfkj.login.module.ActivityModule
import com.tfkj.dajiang.uav.MainApplication
import com.tfkj.dajiang.uav.module.MainModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules =
[
    (ActivityModule::class),
    (AndroidSupportInjectionModule::class),
    (OkhttpModule::class),
    (RetrofitModule::class)
])
interface AppComponent : AndroidInjector<MainApplication> {

    // Gives us syntactic sugar. we can then do DaggerAppComponent.builder().application(this).build().inject(this);
    // never having to instantiate any modules or say which module we are passing the application to.
    // Application will just be provided into our app graph now.
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: MainApplication): Builder

        fun build(): AppComponent
    }
}