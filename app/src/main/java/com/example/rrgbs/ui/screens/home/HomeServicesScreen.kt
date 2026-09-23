package com.example.rrgbs.ui.screens.home

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
import com.example.rrgbs.model.HomeServiceItem
import com.example.rrgbs.ui.theme.CrimsonRed
import com.example.rrgbs.ui.theme.DeepNavy

@Composable
fun HomeServicesScreen(
    onShowToast: (String) -> Unit
) {
    var selectedServiceForBooking by remember { mutableStateOf<String?>(null) }
    var bookingNote by remember { mutableStateOf("") }

    // Estimator State
    var estimatorServiceType by remember { mutableStateOf("Cleaning") } // "Cleaning", "Elderly Care", "Maintenance"
    var propertySize by remember { mutableStateOf("2 BHK") } // "1 BHK", "2 BHK", "3 BHK", "Villa / 4 BHK"

    val estimatedPrice = remember(estimatorServiceType, propertySize) {
        when (estimatorServiceType) {
            "Cleaning" -> when (propertySize) {
                "1 BHK" -> "₹1,499"
                "2 BHK" -> "₹2,199"
                "3 BHK" -> "₹2,899"
                else -> "₹3,999"
            }
            "Elderly Care" -> when (propertySize) {
                "1 BHK" -> "₹14,000 / mo"
                "2 BHK" -> "₹18,000 / mo"
                "3 BHK" -> "₹22,000 / mo"
                else -> "₹28,000 / mo"
            }
            else -> when (propertySize) {
                "1 BHK" -> "₹299 inspection"
                "2 BHK" -> "₹449 inspection"
                "3 BHK" -> "₹599 inspection"
                else -> "₹799 inspection"
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC)),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Hero Card
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
                            Text("VERIFIED DOORSTEP PROFESSIONALS", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 10.sp)
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Trusted Doorstep Home Services",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Housekeeping, elderly care attendants, home nursing, electricians, plumbers & caretakers. Background-verified staff dispatched to your doorstep.",
                            color = Color(0xFFCBD5E1),
                            fontSize = 12.sp,
                            lineHeight = 17.sp
                        )
                    }
                }
            }
        }

        // Quick Cost Estimator Card
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
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
                            Icon(imageVector = Icons.Default.Calculate, contentDescription = null, tint = CrimsonRed)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Instant Price Estimator", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = DeepNavy)
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xFFFEF2F2))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(estimatedPrice, color = CrimsonRed, fontWeight = FontWeight.Black, fontSize = 13.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text("1. Select Service Type:", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF64748B))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        listOf("Cleaning", "Elderly Care", "Maintenance").forEach { type ->
                            FilterChip(
                                selected = estimatorServiceType == type,
                                onClick = { estimatorServiceType = type },
                                label = { Text(type, fontSize = 11.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = CrimsonRed,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text("2. Select Property Size / Shift:", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF64748B))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        listOf("1 BHK", "2 BHK", "3 BHK", "Villa / 4 BHK").forEach { size ->
                            FilterChip(
                                selected = propertySize == size,
                                onClick = { propertySize = size },
                                label = { Text(size, fontSize = 11.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = DeepNavy,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = {
                            selectedServiceForBooking = "$estimatorServiceType ($propertySize)"
                            bookingNote = "Booked via Instant Cost Estimator. Estimated rate: $estimatedPrice"
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = CrimsonRed),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Book This Plan ($estimatedPrice)", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }
            }
        }

        // Section Title
        item {
            Text(
                text = "All Doorstep Home Services",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = DeepNavy
            )
        }

        // Service Items
        items(DataSources.HOME_SERVICES) { service ->
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
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = service.title,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DeepNavy
                                )
                                Text(
                                    text = service.tagline,
                                    fontSize = 11.sp,
                                    color = Color(0xFF64748B)
                                )
                            }
                        }

                        if (service.badge.isNotBlank()) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(Color(0xFFFEF2F2))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(service.badge, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = CrimsonRed)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = service.description,
                        fontSize = 12.sp,
                        color = Color(0xFF334155),
                        lineHeight = 16.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Checklist
                    Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                        service.checklist.forEach { itemText ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = Color(0xFF10B981),
                                    modifier = Modifier.size(13.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(itemText, fontSize = 11.sp, color = Color(0xFF475569))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = service.startingPrice,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 13.sp,
                            color = CrimsonRed
                        )

                        Button(
                            onClick = {
                                selectedServiceForBooking = service.title
                                bookingNote = "Inquiry for ${service.title} (${service.startingPrice})"
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = CrimsonRed),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                            modifier = Modifier.height(34.dp)
                        ) {
                            Text("Book Staff", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }

    selectedServiceForBooking?.let { serviceTitle ->
        HomeBookingDialog(
            initialService = serviceTitle,
            initialNote = bookingNote,
            onDismiss = { selectedServiceForBooking = null },
            onConfirmBooking = { name, phone, srv, addr, note ->
                onShowToast("Booking confirmed for $srv! Coordinator will reach you at $phone.")
            }
        )
    }
}
