#### MultiModule

- Use to prevent merge conflict when you have many collaborators in the project.

- Use to group features and easy to maintain

- app (router, application, splashscreen)
- design (ui, base)
- common (extensions, utils, helper, handler)
- feature_name
- uitest
- buildSrc

#### buildSrc module

- Use to create a common  gradle setup

- create a folder named "buildSrc"
- create a build.gradle.kts

```kotlin
  plugins {
    `kotlin-dsl`
  }
  repositories {
   mavenCentral()
  }
```

#### SplashScreen core KTX

- use to modify white screen upon opening of the app.
- dependency

```kotlin
  "androidx.core:core-splashscreen:1.0.0-beta02"
```

- create custom theme and assign it to your activity in the manifest.

```xml
 <style name="Theme.App.Starting" parent="Theme.SplashScreen">
     <item name="windowSplashScreenBackground">@color/white</item>
     <item name="windowSplashScreenAnimatedIcon">@drawable/baseline_android_24</item>
     <item name="windowSplashScreenAnimationDuration">200</item>
     <item name="postSplashScreenTheme">@style/Theme.App</item>
 </style>
```

- add this snippet in onCreate

```kotlin
 Thread.sleep(1000)
 installSplashScreen()
```

#### Dagger Hilt

- Use to simplify the declaration of singleton class.

- use **@HiltAndroidApp** to Application class to be able to inject dependencies

```kotlin
  @HiltAndroidApp
  class MainApplication : Application() {
  }
```

- use **@AndroidEntryPoint** to Activity class to be able to inject dependencies

```kotlin
  @AndroidEntryPoint
  class LoginActivity : BaseActivity() 
```

- use **@HiltViewModel** to ViewModel class to be able to inject dependencies

```kotlin
  @HiltViewModel
  class LoginViewModel  @Inject constructor(
    private val splashUseCase: LoginUseCase): BaseViewModel() 
```

- use **@Inject** to add dependencies via field member or constructor

```kotlin
  @HiltViewModel
  class LoginViewModel @Inject constructor( private val splashUseCase: LoginUseCase)
```

```kotlin
  @HiltAndroidApp
  class MainApplication : Application() {
    @Inject lateinit var bar: Bar
  }
```

- use **@Module** to create dependency class/objec

- use **@InstallIn**(SingletonComponent::class / ActivityComponent::class) to group the hierarchy of dependencies

```kotlin
  @Module
  @InstallIn(SingletonComponent::class)
  object SplashAPIModule
```

```kotlin
  @Module
  @InstallIn(ActivityComponent::class)
  object SplashModule
```

- use qualifier annotation based on **@InstallIn** components. (**@Singleton, @ActivityScoped**)

- use **@Provides** to instantiate singleton classes

```kotlin
  @Provides
  @Singleton
  fun provideService(networkManager: NetworkManager) : SplashService =
    networkManager.create(SplashService::class.java) as SplashService
```

```kotlin
  @Provides
  @ActivityScoped
  fun provideRepository(service: SplashService) : SplashRepository =
    SplashRepository(service)
```

#### Retrofit

- Use to fetch data source using REST api 

- Multiple serialization support from api response (json or xml)

```kotlin
 /// for JSON then use @JSONFormat annotation in your Service interface
 GsonConverterFactory.create(GsonBuilder().setLenient().create())

 /// for XML then use @XMLFormat annotation in your Service class
 TikXmlConverterFactory.create(TikXml.Builder().exceptionOnUnreadXml(false).build())
```

- Support RX architecture 

```kotlin
/// to use Observable<?>, Single<?> as return type in your Services interface
RxJava3CallAdapterFactory.create()
```

- Add interceptor to handler server error code

```kotlin
// use to handle and throw specific exception
addInterceptor(NetworkErrorInterceptor())
```

#### MVVM Architecture

- model (data processing)

- view (activity, fragment)

- view model (bridge between the model classes and view class)

#### UIState Architecture

- every group of view should have a state.

```kotlin
sealed class UiState<out T> {

    object Loading:UiState<Nothing>()

    data class Success<T>(val data: T):UiState<T>()

    data class Error(val error: Throwable):UiState<Nothing>()

}
```

- live data should observe using state.

```kotlin
 // in view model class
 private val _apiResponse = MutableLiveData<UiState<ReponseDataModel>>()
    val apiResponse: LiveData<UiState<ReponseDataModel>>
        get() = _apiResponse
```

```kotlin
// in activity class
  viewModel.apiResponse.observe(this) { state->
            when(state) {
                is UiState.Loading -> {
                  // show shimmer
                }
                is UiState.Success -> {
                  // load content
                }
                is UiState.Error -> {
                  // show error spiels, try again functionality, hide content
                }
            }
        }
```

#### Interactor & Repository Achitecture

- View model class will call interactor class (use case) 
  
  then interactor class will call repository class (data source).

- Interactor class is where we do the mapping, combining of callables or any process.

- Repository class is the implementation of method to access the data source (remote or local). You can also put the caching of response in this class.

```kotlin
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val accountInfoUseCase: AccountInfoUseCase): BaseViewModel() {

    private val _accountInfoState = MutableLiveData<UiState<AccountInfoResponse>>()
    val accountInfoState: LiveData<UiState<AccountInfoResponse>>
        get() = _accountInfoState

    fun getAccountInfo() {
       accountInfoUseCase.getAccountInfo()
           .observeOn(AndroidSchedulers.mainThread())
           .doOnSubscribe {
                _appState.value = UiState.Loading
            }
            .subscribe({
                 _appState.value = UiState.Success(data)
            },{
                _appState.value = UiState.Error(it)
            }).addTo(disposable)
    }

}
```

```kotlin
class AccountInfoUseCase @Inject constructor(private val repository: AccountRepository) {
    fun getAccountInfo(): Observable<AccountInfoResponse> {
        return repository.getPromoList()
    }
}
```

```kotlin
class AccountRepository @Inject constructor(
    private val service: AccountService) {

   private val cacheAccountInfo: AccountInfoResponse? = null

   fun getPromoList(): Observable<AccountInfoResponse> {
        if(cacheAccountInfo !=null) {
           return Observable.just(cacheAccountInfo)
        }
        return service.getAccountInfo()
               .doOnSuccess {
                    cacheAccountInfo = it
               }
   }
}
```

```kotlin
interface AccountService {
    @GET("PATH_URL")
    fun getAccountInfo():Observable<AccountInfoResponse>
}
```

#### AppRouter

- Use to connect activities from different modules.

```kotlin
sealed class AppRouter {
    object LoginScreen: AppRouter()  {
        fun navigate(activity: Activity) {
            activity.startActivity(LoginActivity.createIntent(activity))
        }
    }

    object MainScreen: AppRouter()  {
        fun navigate(activity: Activity) {
            activity.startActivity(MainActivity.createIntent(activity))
        }
    }
}
```

```kotlin
/// how to use
AppRouter.LoginScreen.navigate(this)
```

#### Base Classes

- Use to prevent redundancy of snippets .

```kotlin
///check the code of this classes
BaseActivity()
BaseViewModel()
```
