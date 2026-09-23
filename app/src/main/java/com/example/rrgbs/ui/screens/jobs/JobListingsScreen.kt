package com.example.rrgbs.ui.screens.jobs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rrgbs.data.DataSources
import com.example.rrgbs.model.ApplicationSubmission
import com.example.rrgbs.model.AuthUser
import com.example.rrgbs.model.Job
import com.example.rrgbs.ui.theme.CrimsonRed
import com.example.rrgbs.ui.theme.DeepNavy

@Composable
fun JobListingsScreen(
    jobs: List<Job>,
    savedJobIds: Set<String>,
    currentUser: AuthUser?,
    onToggleSaveJob: (String) -> Unit,
    onSubmitApplication: (ApplicationSubmission) -> Unit,
    onPostJob: (Job) -> Unit,
    onShowToast: (String) -> Unit
) {
    var searchKeyword by remember { mutableStateOf("") }
    var searchLocation by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var showSavedOnly by remember { mutableStateOf(false) }

    // Modals
    var selectedJobForDetail by remember { mutableStateOf<Job?>(null) }
    var selectedJobForApply by remember { mutableStateOf<Job?>(null) }
    var isPostJobOpen by remember { mutableStateOf(false) }

    // Filter logic
    val filteredJobs = remember(jobs, searchKeyword, searchLocation, selectedCategory, showSavedOnly, savedJobIds) {
        jobs.filter { job ->
            val matchesKeyword = searchKeyword.isBlank() ||
                    job.title.contains(searchKeyword, ignoreCase = true) ||
                    job.skills.any { it.contains(searchKeyword, ignoreCase = true) } ||
                    job.company.contains(searchKeyword, ignoreCase = true)

            val matchesLocation = searchLocation.isBlank() ||
                    job.location.contains(searchLocation, ignoreCase = true)

            val matchesCategory = selectedCategory == null ||
                    job.category.equals(selectedCategory, ignoreCase = true)

            val matchesSaved = !showSavedOnly || savedJobIds.contains(job.id)

            matchesKeyword && matchesLocation && matchesCategory && matchesSaved
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8FAFC))
        ) {
            // Search & Filter Header
            Surface(
                color = Color.White,
                shadowElevation = 2.dp
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    // Search Bars
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = searchKeyword,
                            onValueChange = { searchKeyword = it },
                            placeholder = { Text("Title, skill, or company", fontSize = 12.sp) },
                            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, modifier = Modifier.size(18.dp)) },
                            modifier = Modifier.weight(1.2f),
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp)
                        )

                        OutlinedTextField(
                            value = searchLocation,
                            onValueChange = { searchLocation = it },
                            placeholder = { Text("City / Remote", fontSize = 12.sp) },
                            leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.size(18.dp), tint = CrimsonRed) },
                            modifier = Modifier.weight(0.8f),
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Categories & Saved filter
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Bookmark Saved Only Toggle
                        item {
                            FilterChip(
                                selected = showSavedOnly,
                                onClick = { showSavedOnly = !showSavedOnly },
                                label = { Text("Bookmarks (${savedJobIds.size})", fontSize = 11.sp) },
                                leadingIcon = {
                                    Icon(
                                        imageVector = if (showSavedOnly) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                        contentDescription = null,
                                        modifier = Modifier.size(14.dp)
                                    )
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = CrimsonRed,
                                    selectedLabelColor = Color.White,
                                    selectedLeadingIconColor = Color.White
                                )
                            )
                        }

                        // All categories chip
                        item {
                            FilterChip(
                                selected = selectedCategory == null,
                                onClick = { selectedCategory = null },
                                label = { Text("All Roles", fontSize = 11.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = DeepNavy,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }

                        // Specific Categories
                        items(DataSources.CATEGORIES) { cat ->
                            FilterChip(
                                selected = selectedCategory == cat.title,
                                onClick = {
                                    selectedCategory = if (selectedCategory == cat.title) null else cat.title
                                },
                                label = { Text("${cat.icon} ${cat.title}", fontSize = 11.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = CrimsonRed,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }
                }
            }

            // Results Counter & Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${filteredJobs.size} Available Openings",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = DeepNavy
                )

                if (searchKeyword.isNotBlank() || searchLocation.isNotBlank() || selectedCategory != null || showSavedOnly) {
                    Text(
                        text = "Reset Filters",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = CrimsonRed,
                        modifier = Modifier.clickable {
                            searchKeyword = ""
                            searchLocation = ""
                            selectedCategory = null
                            showSavedOnly = false
                        }
                    )
                }
            }

            // Job Cards List
            if (filteredJobs.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.SearchOff,
                            contentDescription = null,
                            tint = Color(0xFF94A3B8),
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "No job vacancies found",
                            fontWeight = FontWeight.Bold,
                            color = DeepNavy,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Try adjusting your search keywords or clear filters",
                            color = Color(0xFF64748B),
                            fontSize = 12.sp
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 80.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredJobs, key = { it.id }) { job ->
                        val isSaved = savedJobIds.contains(job.id)

                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedJobForDetail = job }
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                // Top row: Logo, Company, Title & Bookmark
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.Top,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(40.dp)
                                                .clip(RoundedCornerShape(8.dp))
                                                .background(DeepNavy),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = job.companyLogoText,
                                                color = Color.White,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 14.sp
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Column {
                                            Text(
                                                text = job.company,
                                                fontSize = 12.sp,
                                                color = Color(0xFF64748B),
                                                fontWeight = FontWeight.Medium
                                            )
                                            Text(
                                                text = job.title,
                                                fontSize = 15.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = DeepNavy
                                            )
                                        }
                                    }

                                    IconButton(
                                        onClick = {
                                            onToggleSaveJob(job.id)
                                            onShowToast(if (isSaved) "Job removed from bookmarks" else "Job saved to bookmarks!")
                                        },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = if (isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                            contentDescription = "Save",
                                            tint = if (isSaved) CrimsonRed else Color(0xFF94A3B8)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                // Tag badges: Salary, Location, Type
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(CrimsonRed.copy(alpha = 0.1f))
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = job.salary,
                                            color = CrimsonRed,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }

                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(Color(0xFFF1F5F9))
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = job.location,
                                            color = DeepNavy,
                                            fontSize = 11.sp
                                        )
                                    }

                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(Color(0xFFF1F5F9))
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = job.type,
                                            color = Color(0xFF475569),
                                            fontSize = 11.sp
                                        )
                                    }

                                    Spacer(modifier = Modifier.weight(1f))

                                    Text(
                                        text = job.postedDate,
                                        fontSize = 10.sp,
                                        color = Color(0xFF94A3B8)
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                // Skills preview
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    job.skills.take(3).forEach { skill ->
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(4.dp))
                                                .background(Color(0xFFF8FAFC))
                                                .padding(horizontal = 6.dp, vertical = 2.dp)
                                        ) {
                                            Text(text = skill, fontSize = 10.sp, color = Color(0xFF64748B))
                                        }
                                    }
                                    if (job.skills.size > 3) {
                                        Text(text = "+${job.skills.size - 3} more", fontSize = 10.sp, color = Color(0xFF94A3B8))
                                    }
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                // Actions: Details & Apply
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    OutlinedButton(
                                        onClick = { selectedJobForDetail = job },
                                        modifier = Modifier.weight(1f),
                                        shape = RoundedCornerShape(8.dp),
                                        contentPadding = PaddingValues(vertical = 4.dp)
                                    ) {
                                        Text("View Details", fontSize = 12.sp)
                                    }

                                    Button(
                                        onClick = { selectedJobForApply = job },
                                        colors = ButtonDefaults.buttonColors(containerColor = CrimsonRed),
                                        modifier = Modifier.weight(1f),
                                        shape = RoundedCornerShape(8.dp),
                                        contentPadding = PaddingValues(vertical = 4.dp)
                                    ) {
                                        Text("Apply Now", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Post a Job Floating Action Button (for employers)
        ExtendedFloatingActionButton(
            onClick = { isPostJobOpen = true },
            containerColor = CrimsonRed,
            contentColor = Color.White,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 16.dp, end = 16.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
            Spacer(modifier = Modifier.width(6.dp))
            Text("Post Vacancy", fontWeight = FontWeight.Bold, fontSize = 13.sp)
        }
    }

    // Dialogs
    selectedJobForDetail?.let { job ->
        JobDetailDialog(
            job = job,
            isSaved = savedJobIds.contains(job.id),
            onToggleSave = { onToggleSaveJob(job.id) },
            onApply = { selectedJobForApply = job },
            onDismiss = { selectedJobForDetail = null }
        )
    }

    selectedJobForApply?.let { job ->
        ApplyJobDialog(
            job = job,
            currentUser = currentUser,
            onDismiss = { selectedJobForApply = null },
            onSubmitApplication = { sub ->
                onSubmitApplication(sub)
                onShowToast("Application submitted successfully for ${job.title}!")
            }
        )
    }

    if (isPostJobOpen) {
        PostJobDialog(
            onDismiss = { isPostJobOpen = false },
            onPostJob = { newJob ->
                onPostJob(newJob)
                onShowToast("Job '${newJob.title}' posted successfully!")
            }
        )
    }
}
