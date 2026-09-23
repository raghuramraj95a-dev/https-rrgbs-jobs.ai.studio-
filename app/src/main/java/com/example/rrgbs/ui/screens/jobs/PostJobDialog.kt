package com.example.rrgbs.ui.screens.jobs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.rrgbs.model.Job
import com.example.rrgbs.ui.theme.CrimsonRed
import com.example.rrgbs.ui.theme.DeepNavy

@Composable
fun PostJobDialog(
    onDismiss: () -> Unit,
    onPostJob: (Job) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var company by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("IT & Software") }
    var jobType by remember { mutableStateOf("Full Time") }
    var salary by remember { mutableStateOf("") }
    var experience by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var skills by remember { mutableStateOf("") }

    val categories = listOf("IT & Software", "BPO & Customer Support", "Sales & Marketing", "HR & Administration", "Logistics", "Manufacturing")

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier.fillMaxWidth()
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
                    Column {
                        Text(
                            text = "Post a New Vacancy",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = DeepNavy
                        )
                        Text(
                            text = "Reach verified candidates across India",
                            fontSize = 12.sp,
                            color = Color(0xFF64748B)
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Job Title (e.g. Senior Java Developer) *") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = company,
                        onValueChange = { company = it },
                        label = { Text("Company Name *") },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = location,
                        onValueChange = { location = it },
                        label = { Text("Location *") },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = salary,
                        onValueChange = { salary = it },
                        label = { Text("Salary / CTC") },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = experience,
                        onValueChange = { experience = it },
                        label = { Text("Experience") },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = skills,
                    onValueChange = { skills = it },
                    label = { Text("Skills Required (comma separated)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Job Description & Requirements *") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp),
                    maxLines = 4
                )

                Spacer(modifier = Modifier.height(18.dp))

                Button(
                    onClick = {
                        val newJob = Job(
                            id = "job-${System.currentTimeMillis()}",
                            title = title.ifBlank { "Professional Role" },
                            company = company.ifBlank { "Hiring Organization" },
                            companyLogoText = (company.take(2).uppercase().ifBlank { "RR" }),
                            location = location.ifBlank { "Bangalore" },
                            category = category,
                            type = jobType,
                            experience = experience.ifBlank { "1–3 Years" },
                            salary = salary.ifBlank { "₹25,000 – ₹40,000" },
                            description = description.ifBlank { "Exciting opportunity with growth potential and competitive compensation." },
                            skills = skills.split(",").map { it.trim() }.filter { it.isNotBlank() }.ifEmpty { listOf("Communication", "Teamwork") },
                            postedDate = "Just now",
                            isFeatured = true
                        )
                        onPostJob(newJob)
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = CrimsonRed),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Publish Job Opening", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
            }
        }
    }
}
