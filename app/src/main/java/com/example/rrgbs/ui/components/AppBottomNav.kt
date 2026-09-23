package com.example.rrgbs.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rrgbs.ui.theme.CrimsonRed
import com.example.rrgbs.ui.theme.DeepNavy

enum class AppPortal(val id: String, val title: String) {
    RESUME("resume", "AI Resume"),
    JOBS("jobs", "Job Portal"),
    STORE("store", "Store"),
    SERVICES("services", "Staffing"),
    HOME("home", "Home Services")
}

@Composable
fun AppBottomNav(
    activePortal: AppPortal,
    onSelectPortal: (AppPortal) -> Unit,
    cartCount: Int = 0
) {
    NavigationBar(
        containerColor = DeepNavy,
        tonalElevation = 8.dp
    ) {
        // 1. AI Resume Builder
        NavigationBarItem(
            selected = activePortal == AppPortal.RESUME,
            onClick = { onSelectPortal(AppPortal.RESUME) },
            icon = {
                Icon(
                    imageVector = Icons.Default.Description,
                    contentDescription = "AI Resume Builder",
                    modifier = Modifier.size(22.dp)
                )
            },
            label = {
                Text(
                    text = "Resume",
                    fontSize = 11.sp,
                    fontWeight = if (activePortal == AppPortal.RESUME) FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = Color.White,
                indicatorColor = CrimsonRed,
                unselectedIconColor = Color(0xFF94A3B8),
                unselectedTextColor = Color(0xFF94A3B8)
            )
        )

        // 2. Jobs Portal
        NavigationBarItem(
            selected = activePortal == AppPortal.JOBS,
            onClick = { onSelectPortal(AppPortal.JOBS) },
            icon = {
                Icon(
                    imageVector = Icons.Default.Work,
                    contentDescription = "Jobs Portal",
                    modifier = Modifier.size(22.dp)
                )
            },
            label = {
                Text(
                    text = "Jobs",
                    fontSize = 11.sp,
                    fontWeight = if (activePortal == AppPortal.JOBS) FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = Color.White,
                indicatorColor = CrimsonRed,
                unselectedIconColor = Color(0xFF94A3B8),
                unselectedTextColor = Color(0xFF94A3B8)
            )
        )

        // 3. Store Portal
        NavigationBarItem(
            selected = activePortal == AppPortal.STORE,
            onClick = { onSelectPortal(AppPortal.STORE) },
            icon = {
                BadgedBox(
                    badge = {
                        if (cartCount > 0) {
                            Badge(containerColor = CrimsonRed) {
                                Text(
                                    text = "$cartCount",
                                    color = Color.White,
                                    fontSize = 10.sp
                                )
                            }
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingBag,
                        contentDescription = "Business Store",
                        modifier = Modifier.size(22.dp)
                    )
                }
            },
            label = {
                Text(
                    text = "Store",
                    fontSize = 11.sp,
                    fontWeight = if (activePortal == AppPortal.STORE) FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = Color.White,
                indicatorColor = CrimsonRed,
                unselectedIconColor = Color(0xFF94A3B8),
                unselectedTextColor = Color(0xFF94A3B8)
            )
        )

        // 4. Staffing & Corporate Services
        NavigationBarItem(
            selected = activePortal == AppPortal.SERVICES,
            onClick = { onSelectPortal(AppPortal.SERVICES) },
            icon = {
                Icon(
                    imageVector = Icons.Default.Business,
                    contentDescription = "Corporate Services",
                    modifier = Modifier.size(22.dp)
                )
            },
            label = {
                Text(
                    text = "Staffing",
                    fontSize = 11.sp,
                    fontWeight = if (activePortal == AppPortal.SERVICES) FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = Color.White,
                indicatorColor = CrimsonRed,
                unselectedIconColor = Color(0xFF94A3B8),
                unselectedTextColor = Color(0xFF94A3B8)
            )
        )

        // 5. Doorstep Home Services
        NavigationBarItem(
            selected = activePortal == AppPortal.HOME,
            onClick = { onSelectPortal(AppPortal.HOME) },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home Services",
                    modifier = Modifier.size(22.dp)
                )
            },
            label = {
                Text(
                    text = "Services",
                    fontSize = 11.sp,
                    fontWeight = if (activePortal == AppPortal.HOME) FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = Color.White,
                indicatorColor = CrimsonRed,
                unselectedIconColor = Color(0xFF94A3B8),
                unselectedTextColor = Color(0xFF94A3B8)
            )
        )
    }
}
