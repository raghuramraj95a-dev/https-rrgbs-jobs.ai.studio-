package com.example.rrgbs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.rrgbs.data.DataSources
import com.example.rrgbs.model.*
import com.example.rrgbs.ui.components.*
import com.example.rrgbs.ui.screens.home.HomeServicesScreen
import com.example.rrgbs.ui.screens.jobs.JobListingsScreen
import com.example.rrgbs.ui.screens.resume.AIResumeBuilderScreen
import com.example.rrgbs.ui.screens.services.CorporateServicesScreen
import com.example.rrgbs.ui.screens.store.StoreScreen
import com.example.rrgbs.ui.theme.CrimsonRed
import com.example.rrgbs.ui.theme.DeepNavy
import com.example.rrgbs.ui.theme.RRGBSTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RRGBSTheme {
                MainAppScreen()
            }
        }
    }
}

@Composable
fun MainAppScreen() {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // Navigation portal state
    var activePortal by remember { mutableStateOf(AppPortal.RESUME) }

    // Jobs state
    var jobs by remember { mutableStateOf(DataSources.INITIAL_JOBS) }
    var savedJobIds by remember { mutableStateOf(setOf<String>()) }

    // Store cart state
    var cart by remember { mutableStateOf(listOf<CartItem>()) }

    // Resume Builder state
    var resumeData by remember { mutableStateOf(DataSources.DEMO_RESUME) }

    // User authentication state
    var currentUser by remember {
        mutableStateOf<AuthUser?>(
            AuthUser(name = "Rahul Kumar", role = "candidate", email = "rahul@example.com")
        )
    }

    // Common Dialogs
    var isAboutOpen by remember { mutableStateOf(false) }
    var isAuthOpen by remember { mutableStateOf(false) }

    fun showToast(message: String) {
        coroutineScope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()
            snackbarHostState.showSnackbar(message, withDismissAction = true)
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                activePortalTitle = activePortal.title,
                currentUser = currentUser,
                onOpenAuth = { isAuthOpen = true },
                onOpenAboutContact = { isAboutOpen = true }
            )
        },
        bottomBar = {
            AppBottomNav(
                activePortal = activePortal,
                onSelectPortal = { activePortal = it },
                cartCount = cart.sumOf { it.qty }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = DeepNavy,
                    contentColor = Color.White,
                    actionColor = CrimsonRed,
                    dismissActionContentColor = Color.White
                )
            }
        },
        containerColor = Color(0xFFF8FAFC)
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (activePortal) {
                AppPortal.RESUME -> {
                    AIResumeBuilderScreen(
                        resumeData = resumeData,
                        onUpdateResume = { resumeData = it },
                        onShowToast = { showToast(it) }
                    )
                }

                AppPortal.JOBS -> {
                    JobListingsScreen(
                        jobs = jobs,
                        savedJobIds = savedJobIds,
                        currentUser = currentUser,
                        onToggleSaveJob = { id ->
                            savedJobIds = if (savedJobIds.contains(id)) {
                                savedJobIds - id
                            } else {
                                savedJobIds + id
                            }
                        },
                        onSubmitApplication = { submission ->
                            showToast("Application submitted for ${submission.jobTitle}!")
                        },
                        onPostJob = { newJob ->
                            jobs = listOf(newJob) + jobs
                        },
                        onShowToast = { showToast(it) }
                    )
                }

                AppPortal.STORE -> {
                    StoreScreen(
                        cart = cart,
                        onUpdateCartQty = { product, newQty ->
                            cart = if (newQty <= 0) {
                                cart.filterNot { it.product.id == product.id }
                            } else {
                                val existingIndex = cart.indexOfFirst { it.product.id == product.id }
                                if (existingIndex >= 0) {
                                    cart.toMutableList().apply {
                                        this[existingIndex] = this[existingIndex].copy(qty = newQty)
                                    }
                                } else {
                                    cart + CartItem(product, newQty)
                                }
                            }
                        },
                        onClearCart = { cart = emptyList() },
                        onShowToast = { showToast(it) }
                    )
                }

                AppPortal.SERVICES -> {
                    CorporateServicesScreen(
                        onShowToast = { showToast(it) }
                    )
                }

                AppPortal.HOME -> {
                    HomeServicesScreen(
                        onShowToast = { showToast(it) }
                    )
                }
            }
        }
    }

    if (isAboutOpen) {
        AboutContactDialog(
            onDismiss = { isAboutOpen = false },
            onShowToast = { showToast(it) }
        )
    }

    if (isAuthOpen) {
        AuthDialog(
            currentUser = currentUser,
            onDismiss = { isAuthOpen = false },
            onLoginSuccess = { user -> currentUser = user },
            onLogout = { currentUser = null },
            onShowToast = { showToast(it) }
        )
    }
}
