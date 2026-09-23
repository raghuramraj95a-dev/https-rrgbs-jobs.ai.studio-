package com.example.rrgbs.ui.screens.jobs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.rrgbs.model.Job
import com.example.rrgbs.ui.theme.CrimsonRed
import com.example.rrgbs.ui.theme.DeepNavy

@Composable
fun JobDetailDialog(
    job: Job,
    isSaved: Boolean,
    onToggleSave: () -> Unit,
    onApply: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(DeepNavy),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = job.companyLogoText,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = job.company,
                                fontSize = 13.sp,
                                color = Color(0xFF64748B),
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = job.location,
                                fontSize = 12.sp,
                                color = DeepNavy
                            )
                        }
                    }

                    Row {
                        IconButton(onClick = onToggleSave) {
                            Icon(
                                imageVector = if (isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                contentDescription = "Save Job",
                                tint = if (isSaved) CrimsonRed else Color(0xFF64748B)
                            )
                        }
                        IconButton(onClick = onDismiss) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = job.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = DeepNavy
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Metadata Chips Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AssistChip(
                        onClick = {},
                        label = { Text(job.salary, fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                        leadingIcon = { Icon(Icons.Default.CurrencyRupee, null, modifier = Modifier.size(14.dp), tint = CrimsonRed) }
                    )
                    AssistChip(
                        onClick = {},
                        label = { Text(job.type, fontSize = 11.sp) }
                    )
                    AssistChip(
                        onClick = {},
                        label = { Text(job.experience, fontSize = 11.sp) }
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Description
                Text("Role Overview", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = DeepNavy)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = job.description,
                    fontSize = 13.sp,
                    color = Color(0xFF334155),
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Responsibilities
                if (job.responsibilities.isNotEmpty()) {
                    Text("Key Responsibilities", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = DeepNavy)
                    Spacer(modifier = Modifier.height(6.dp))
                    job.responsibilities.forEach { resp ->
                        Row(modifier = Modifier.padding(bottom = 4.dp)) {
                            Text("• ", color = CrimsonRed, fontWeight = FontWeight.Bold)
                            Text(resp, fontSize = 12.sp, color = Color(0xFF334155), lineHeight = 16.sp)
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }

                // Requirements
                if (job.requirements.isNotEmpty()) {
                    Text("Requirements", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = DeepNavy)
                    Spacer(modifier = Modifier.height(6.dp))
                    job.requirements.forEach { req ->
                        Row(modifier = Modifier.padding(bottom = 4.dp)) {
                            Text("✓ ", color = Color(0xFF10B981), fontWeight = FontWeight.Bold)
                            Text(req, fontSize = 12.sp, color = Color(0xFF334155), lineHeight = 16.sp)
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }

                // Benefits
                if (job.benefits.isNotEmpty()) {
                    Text("Perks & Benefits", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = DeepNavy)
                    Spacer(modifier = Modifier.height(6.dp))
                    job.benefits.forEach { ben ->
                        Row(modifier = Modifier.padding(bottom = 4.dp)) {
                            Text("★ ", color = Color(0xFFF59E0B), fontWeight = FontWeight.Bold)
                            Text(ben, fontSize = 12.sp, color = Color(0xFF334155), lineHeight = 16.sp)
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }

                // Skills
                Text("Skills Required", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = DeepNavy)
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        job.skills.chunked(3).forEach { rowSkills ->
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                rowSkills.forEach { skill ->
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(Color(0xFFF1F5F9))
                                            .padding(horizontal = 8.dp, vertical = 4.dp)
                                    ) {
                                        Text(skill, fontSize = 11.sp, color = DeepNavy)
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Apply Button
                Button(
                    onClick = {
                        onDismiss()
                        onApply()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = CrimsonRed),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(imageVector = Icons.Default.Send, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Apply For This Job", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
            }
        }
    }
}
