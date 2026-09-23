package com.example.rrgbs.ui.screens.services

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rrgbs.data.DataSources
import com.example.rrgbs.model.RecruitmentService
import com.example.rrgbs.model.ServiceEnquirySubmission
import com.example.rrgbs.ui.theme.CrimsonRed
import com.example.rrgbs.ui.theme.DeepNavy

@Composable
fun CorporateServicesScreen(
    onShowToast: (String) -> Unit
) {
    var selectedServiceForEnquiry by remember { mutableStateOf<RecruitmentService?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC)),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Hero Banner
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.linearGradient(
                                colors = listOf(DeepNavy, Color(0xFF1E293B))
                            )
                        )
                        .padding(20.dp)
                ) {
                    Column {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(CrimsonRed)
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text("ENTERPRISE TALENT & HR", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 10.sp)
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Staffing, Recruitment & Payroll Solutions",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "End-to-end workforce solutions customized for IT companies, manufacturing units, logistics hubs, and corporate offices across India.",
                            color = Color(0xFFCBD5E1),
                            fontSize = 12.sp,
                            lineHeight = 17.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Stats Highlights Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            StatBox("100%", "Compliance")
                            StatBox("48 Hrs", "Turnaround")
                            StatBox("500+", "Placements")
                            StatBox("PAN India", "Reach")
                        }
                    }
                }
            }
        }

        // Section Title
        item {
            Text(
                text = "Our Corporate Services",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = DeepNavy
            )
        }

        // Services Cards
        items(DataSources.RECRUITMENT_SERVICES) { service ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFFF1F5F9)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(service.icon, fontSize = 22.sp)
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = service.title,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = DeepNavy
                            )
                        }

                        Button(
                            onClick = { selectedServiceForEnquiry = service },
                            colors = ButtonDefaults.buttonColors(containerColor = CrimsonRed),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                            modifier = Modifier.height(34.dp)
                        ) {
                            Text("Enquire", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = service.description,
                        fontSize = 12.sp,
                        color = Color(0xFF64748B),
                        lineHeight = 16.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Divider(color = Color(0xFFF1F5F9))

                    Spacer(modifier = Modifier.height(10.dp))

                    // Features checklist
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        service.features.forEach { feature ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = CrimsonRed,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = feature,
                                    fontSize = 12.sp,
                                    color = Color(0xFF334155)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Trust & Guarantee Card
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF2F2)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Why Partner with RRGBS?",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = CrimsonRed
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "• 100% labor law, PF, and ESI compliance guarantee\n• Replacement warranty for early candidate attrition\n• Dedicated single-point relationship manager for your organization\n• Transparent billing with customized service level agreements (SLAs)",
                        fontSize = 12.sp,
                        color = DeepNavy,
                        lineHeight = 18.sp
                    )
                }
            }
        }
    }

    selectedServiceForEnquiry?.let { service ->
        ServiceEnquiryDialog(
            service = service,
            onDismiss = { selectedServiceForEnquiry = null },
            onSubmitEnquiry = { enq ->
                onShowToast("Mandate for ${service.title} submitted! Our recruitment desk will call you.")
            }
        )
    }
}

@Composable
private fun StatBox(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, color = Color.White, fontWeight = FontWeight.Black, fontSize = 16.sp)
        Text(text = label, color = Color(0xFF94A3B8), fontSize = 10.sp)
    }
}
