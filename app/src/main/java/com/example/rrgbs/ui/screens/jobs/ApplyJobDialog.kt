package com.example.rrgbs.ui.screens.jobs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Description
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.rrgbs.model.ApplicationSubmission
import com.example.rrgbs.model.AuthUser
import com.example.rrgbs.model.Job
import com.example.rrgbs.ui.theme.CrimsonRed
import com.example.rrgbs.ui.theme.DeepNavy

@Composable
fun ApplyJobDialog(
    job: Job,
    currentUser: AuthUser?,
    onDismiss: () -> Unit,
    onSubmitApplication: (ApplicationSubmission) -> Unit
) {
    var name by remember { mutableStateOf(currentUser?.name ?: "") }
    var email by remember { mutableStateOf(currentUser?.email ?: "") }
    var phone by remember { mutableStateOf("") }
    var experience by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var attachResumeBuilderProfile by remember { mutableStateOf(true) }

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
                            text = "Apply for Position",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = DeepNavy
                        )
                        Text(
                            text = "${job.title} • ${job.company}",
                            fontSize = 12.sp,
                            color = CrimsonRed,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Your Full Name *") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email Address *") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Mobile Phone Number *") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = experience,
                        onValueChange = { experience = it },
                        label = { Text("Experience") },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = location,
                        onValueChange = { location = it },
                        label = { Text("Current City") },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Resume Attachment checkbox
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = attachResumeBuilderProfile,
                        onCheckedChange = { attachResumeBuilderProfile = it },
                        colors = CheckboxDefaults.colors(checkedColor = CrimsonRed)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Attach my RRGBS AI Resume Profile",
                        fontSize = 12.sp,
                        color = DeepNavy,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Cover Note / Why you're a good fit") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp),
                    maxLines = 3
                )

                Spacer(modifier = Modifier.height(18.dp))

                Button(
                    onClick = {
                        val submission = ApplicationSubmission(
                            id = "app-${System.currentTimeMillis()}",
                            jobId = job.id,
                            jobTitle = job.title,
                            company = job.company,
                            applicantName = name.ifBlank { "Candidate" },
                            email = email.ifBlank { "candidate@example.com" },
                            phone = phone.ifBlank { "+91 90000 00000" },
                            experience = experience.ifBlank { job.experience },
                            currentLocation = location.ifBlank { job.location },
                            resumeFileName = if (attachResumeBuilderProfile) "RRGBS_AI_Resume.pdf" else "Uploaded_Resume.pdf",
                            notes = notes,
                            appliedAt = "Just now"
                        )
                        onSubmitApplication(submission)
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = CrimsonRed),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(imageVector = Icons.Default.Description, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Submit Application", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
            }
        }
    }
}
