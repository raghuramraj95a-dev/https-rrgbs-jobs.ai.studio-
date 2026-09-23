package com.example.rrgbs.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.rrgbs.model.AuthUser
import com.example.rrgbs.ui.theme.CrimsonRed
import com.example.rrgbs.ui.theme.DeepNavy

@Composable
fun AuthDialog(
    currentUser: AuthUser?,
    onDismiss: () -> Unit,
    onLoginSuccess: (AuthUser) -> Unit,
    onLogout: () -> Unit,
    onShowToast: (String) -> Unit
) {
    var isRegister by remember { mutableStateOf(false) }
    var selectedRole by remember { mutableStateOf("candidate") } // "candidate" or "employer"
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(22.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = if (currentUser != null) "My Account" else if (isRegister) "Create Account" else "Welcome Back",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = DeepNavy
                        )
                        Text(
                            text = "RRGBS Unified Business & Jobs Portal",
                            fontSize = 11.sp,
                            color = Color(0xFF64748B)
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (currentUser != null) {
                    // Logged in profile view
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "Logged in as:", fontSize = 12.sp, color = Color(0xFF64748B))
                            Text(text = currentUser.name, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = DeepNavy)
                            Text(text = currentUser.email, fontSize = 13.sp, color = Color(0xFF475569))
                            Spacer(modifier = Modifier.height(6.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(CrimsonRed.copy(alpha = 0.1f))
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Text(
                                    text = if (currentUser.role == "employer") "Employer Account" else "Candidate Account",
                                    color = CrimsonRed,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
                            onLogout()
                            onShowToast("Logged out successfully")
                            onDismiss()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDC2626)),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Log Out", fontWeight = FontWeight.Bold)
                    }
                } else {
                    // Role Toggle: Candidate vs Employer
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFF1F5F9))
                            .padding(4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (selectedRole == "candidate") CrimsonRed else Color.Transparent)
                                .clickable { selectedRole = "candidate" }
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Job Seeker",
                                color = if (selectedRole == "candidate") Color.White else DeepNavy,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (selectedRole == "employer") CrimsonRed else Color.Transparent)
                                .clickable { selectedRole = "employer" }
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Employer / HR",
                                color = if (selectedRole == "employer") Color.White else DeepNavy,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    if (isRegister) {
                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            label = { Text(if (selectedRole == "employer") "Company / HR Name" else "Full Name") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                    }

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email Address") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Button(
                        onClick = {
                            val userEmail = if (email.isNotBlank()) email.trim() else "user@example.com"
                            val userName = if (name.isNotBlank()) name.trim() else if (selectedRole == "employer") "Enterprise HR" else "Priya Sharma"
                            val authUser = AuthUser(
                                name = userName,
                                role = selectedRole,
                                email = userEmail
                            )
                            onLoginSuccess(authUser)
                            onShowToast(if (isRegister) "Account created for ${authUser.name}!" else "Welcome back, ${authUser.name}!")
                            onDismiss()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = CrimsonRed),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = if (isRegister) "Register Account" else "Sign In",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isRegister) "Already have an account?" else "Don't have an account?",
                            fontSize = 13.sp,
                            color = Color(0xFF64748B)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (isRegister) "Sign In" else "Register",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = CrimsonRed,
                            modifier = Modifier.clickable { isRegister = !isRegister }
                        )
                    }
                }
            }
        }
    }
}
