package com.korea200

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.korea200.data.NetworkCallState
import com.korea200.ui.screens.MainScreen
import com.korea200.ui.screens.RecordScreen
import com.korea200.ui.screens.SplashScreen
import com.korea200.ui.theme.Korea200Theme
import com.korea200.utils.RetrofitInstance
import com.korea200.viewModels.ServerViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable

/*navGraph routes*/
@Serializable object SplashScreen
@Serializable object MainScreen
@Serializable data class RecordScreen(val name: String)


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        /*Configure the splash screen's exit animation*/
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            val fadeOut = ObjectAnimator.ofFloat(
                splashScreenView.view, // The entire splash screen view
                View.ALPHA, // Animate its alpha property
                1f, // From opaque
                0f // To fully transparent
            )
            fadeOut.duration = 1000L
            // Call SplashScreenView.remove at the end of your custom animation.
            fadeOut.doOnEnd { splashScreenView.remove() }
            // Run the animation.
            fadeOut.start()
        }

        super.onCreate(savedInstanceState)

        setContent {
            Korea200Theme {
                val navController = rememberNavController()
                val vm by viewModels<ServerViewModel>()

                NavHost(navController = navController, startDestination = SplashScreen) {
                    composable<SplashScreen> {
                        SplashScreen(
                            navToHome = {
                                navController.navigate(MainScreen){
                                    popUpTo(SplashScreen) {
                                        inclusive = true
                                    } //prevent going back to SplashScreen
                                }
                            },
                            work = suspend {
                                try {
                                    RetrofitInstance.server.isWarm() //Don't really care about the output or if its successful, we're only warming up the server.
                                    NetworkCallState.Success(result = true)
                                } catch (e: Exception) {
                                    Log.e("warmingUp", "${e.message}") //In case, there is a fatal exception when trying to reach the server
                                    NetworkCallState.Error(mssg = e.message ?: "Something wrong")
                                }
                            }
                        )
                    }
                    composable<MainScreen> {
                        MainScreen(
                            vm = vm,
                            navToRecord = { name -> navController.navigate(RecordScreen(name)) }
                        )
                    }
                    composable<RecordScreen> { backStackEntry ->
                        val args = backStackEntry.toRoute<RecordScreen>()
                        RecordScreen(
                            userName = args.name,
                            vm = vm,
                            navToMain = { navController.navigate(MainScreen) },
                            navToPrev ={navController.popBackStack()}
                        )
                    }
                }
            }
        }
    }

}


