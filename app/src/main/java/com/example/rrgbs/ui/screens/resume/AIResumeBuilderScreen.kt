package com.example.rrgbs.ui.screens.resume

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rrgbs.data.DataSources
import com.example.rrgbs.model.ResumeData
import com.example.rrgbs.model.TemplateType
import com.example.rrgbs.ui.theme.CrimsonRed
import com.example.rrgbs.ui.theme.DeepNavy

@Composable
fun AIResumeBuilderScreen(
    resumeData: ResumeData,
    onUpdateResume: (ResumeData) -> Unit,
    onShowToast: (String) -> Unit
) {
    val context = LocalContext.current
    var selectedTemplate by remember { mutableStateOf(TemplateType.CLASSIC) }
    var selectedTab by remember { mutableStateOf(0) } // 0: Form, 1: Preview, 2: ATS Check
    var currentSubSection by remember { mutableStateOf(0) } // 0: Personal, 1: Summary, 2: Experience, 3: Education, 4: Skills

    // Calculate ATS Score
    val atsScore = remember(resumeData) {
        val fields = listOf(
            resumeData.name, resumeData.role, resumeData.email,
            resumeData.phone, resumeData.summary, resumeData.experience,
            resumeData.education, resumeData.institute, resumeData.skills,
            resumeData.target
        )
        val filled = fields.count { it.isNotBlank() }
        val skillsCount = resumeData.skills.split(",").count { it.isNotBlank() }
        var score = 45 + ((filled.toFloat() / fields.size) * 45).toInt()
        if (skillsCount >= 5) score += 5
        if (resumeData.projects.isNotBlank() || resumeData.certifications.isNotBlank()) score += 3
        score.coerceAtMost(98)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
    ) {
        // Hero Header & Quick Actions
        Surface(
            color = Color.White,
            shadowElevation = 2.dp
        ) {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = CrimsonRed,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "AI Resume Builder",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = DeepNavy
                            )
                        }
                        Text(
                            text = "ATS Score: $atsScore/100 • ${selectedTemplate.displayName}",
                            fontSize = 12.sp,
                            color = Color(0xFF64748B)
                        )
                    }

                    // Quick buttons
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        FilledTonalButton(
                            onClick = {
                                onUpdateResume(DataSources.DEMO_RESUME)
                                onShowToast("Demo HR profile loaded!")
                            },
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Demo", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        }

                        Button(
                            onClick = {
                                val shareText = buildResumeShareText(resumeData)
                                val intent = Intent(Intent.ACTION_SEND).apply {
                                    type = "text/plain"
                                    putExtra(Intent.EXTRA_SUBJECT, "${resumeData.name} Resume")
                                    putExtra(Intent.EXTRA_TEXT, shareText)
                                }
                                context.startActivity(Intent.createChooser(intent, "Share Resume"))
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = CrimsonRed),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Export", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Primary Mode Tabs (Edit / Live Preview / ATS Score)
                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = Color(0xFFF1F5F9),
                    contentColor = CrimsonRed,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .height(38.dp)
                ) {
                    Tab(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        text = { Text("Edit Details", fontSize = 12.sp, fontWeight = FontWeight.SemiBold) }
                    )
                    Tab(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        text = { Text("Live Preview", fontSize = 12.sp, fontWeight = FontWeight.SemiBold) }
                    )
                    Tab(
                        selected = selectedTab == 2,
                        onClick = { selectedTab = 2 },
                        text = { Text("ATS Insights", fontSize = 12.sp, fontWeight = FontWeight.SemiBold) }
                    )
                }
            }
        }

        // Body Content based on Tab
        when (selectedTab) {
            0 -> ResumeEditorTab(
                resumeData = resumeData,
                currentSubSection = currentSubSection,
                onSubSectionChange = { currentSubSection = it },
                onUpdateResume = onUpdateResume,
                onShowToast = onShowToast
            )
            1 -> ResumePreviewTab(
                resumeData = resumeData,
                selectedTemplate = selectedTemplate,
                onSelectTemplate = { selectedTemplate = it },
                onExport = {
                    val shareText = buildResumeShareText(resumeData)
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_SUBJECT, "${resumeData.name} Resume")
                        putExtra(Intent.EXTRA_TEXT, shareText)
                    }
                    context.startActivity(Intent.createChooser(intent, "Share Resume"))
                }
            )
            2 -> ResumeAtsTab(
                score = atsScore,
                resumeData = resumeData,
                onJumpToEdit = { subSection ->
                    currentSubSection = subSection
                    selectedTab = 0
                }
            )
        }
    }
}

@Composable
private fun ResumeEditorTab(
    resumeData: ResumeData,
    currentSubSection: Int,
    onSubSectionChange: (Int) -> Unit,
    onUpdateResume: (ResumeData) -> Unit,
    onShowToast: (String) -> Unit
) {
    val sections = listOf("Contact", "Summary", "Experience", "Education", "Skills")

    Column(modifier = Modifier.fillMaxSize()) {
        // Sub-section pill row
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(sections.indices.toList()) { index ->
                FilterChip(
                    selected = currentSubSection == index,
                    onClick = { onSubSectionChange(index) },
                    label = { Text(sections[index], fontSize = 12.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = CrimsonRed,
                        selectedLabelColor = Color.White
                    )
                )
            }
        }

        // Form fields scrollable area
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            when (currentSubSection) {
                0 -> {
                    // Contact & Personal
                    Text("Personal & Contact Details", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = DeepNavy)
                    OutlinedTextField(
                        value = resumeData.name,
                        onValueChange = { onUpdateResume(resumeData.copy(name = it)) },
                        label = { Text("Full Name *") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = resumeData.role,
                        onValueChange = { onUpdateResume(resumeData.copy(role = it)) },
                        label = { Text("Current / Desired Job Title *") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = resumeData.phone,
                            onValueChange = { onUpdateResume(resumeData.copy(phone = it)) },
                            label = { Text("Phone Number") },
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = resumeData.location,
                            onValueChange = { onUpdateResume(resumeData.copy(location = it)) },
                            label = { Text("Location (City, State)") },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    OutlinedTextField(
                        value = resumeData.email,
                        onValueChange = { onUpdateResume(resumeData.copy(email = it)) },
                        label = { Text("Email Address") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = resumeData.linkedin,
                        onValueChange = { onUpdateResume(resumeData.copy(linkedin = it)) },
                        label = { Text("LinkedIn / Portfolio URL") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                1 -> {
                    // Summary & Target
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Target Role & Summary", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = DeepNavy)
                        Button(
                            onClick = {
                                val role = resumeData.role.ifBlank { "Professional" }
                                val target = resumeData.target.ifBlank { role }
                                val generated = "Results-driven $role with extensive experience in operational excellence, cross-functional collaboration, and strategic execution. Proven background in delivering high-impact business outcomes, stakeholder management, and compliance adherence. Seeking a high-growth $target role to deliver measurable value."
                                onUpdateResume(resumeData.copy(summary = generated))
                                onShowToast("AI Summary generated!")
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = CrimsonRed),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("AI Generate", fontSize = 11.sp)
                        }
                    }

                    OutlinedTextField(
                        value = resumeData.target,
                        onValueChange = { onUpdateResume(resumeData.copy(target = it)) },
                        label = { Text("Target Position / Role Keyword") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = resumeData.summary,
                        onValueChange = { onUpdateResume(resumeData.copy(summary = it)) },
                        label = { Text("Professional Summary") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp),
                        maxLines = 6
                    )

                    FilledTonalButton(
                        onClick = {
                            if (resumeData.summary.isNotBlank()) {
                                val enhanced = resumeData.summary + " Demonstrated proficiency in optimizing workflow velocity, championing best practices, and driving consistent quarterly performance benchmarks."
                                onUpdateResume(resumeData.copy(summary = enhanced))
                                onShowToast("AI enhancements added!")
                            } else {
                                onShowToast("Please enter or generate a summary first.")
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(imageVector = Icons.Default.TrendingUp, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("AI Polish & Boost Impact Keywords")
                    }
                }

                2 -> {
                    // Experience
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Work Experience", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = DeepNavy)
                        Button(
                            onClick = {
                                val defExp = "Spearheaded key operational initiatives, coordinated with cross-functional managers, streamlined turnaround times by 20%, maintained meticulous tracking reports, and exceeded quarterly deliverables consistently."
                                onUpdateResume(resumeData.copy(experience = defExp))
                                onShowToast("Experience bullet points generated!")
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = CrimsonRed),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("AI Bullet Points", fontSize = 11.sp)
                        }
                    }

                    OutlinedTextField(
                        value = resumeData.company,
                        onValueChange = { onUpdateResume(resumeData.copy(company = it)) },
                        label = { Text("Company / Organization") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = resumeData.jobtitle,
                            onValueChange = { onUpdateResume(resumeData.copy(jobtitle = it)) },
                            label = { Text("Job Title") },
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = resumeData.dates,
                            onValueChange = { onUpdateResume(resumeData.copy(dates = it)) },
                            label = { Text("Dates (e.g. 2023 - Present)") },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    OutlinedTextField(
                        value = resumeData.joblocation,
                        onValueChange = { onUpdateResume(resumeData.copy(joblocation = it)) },
                        label = { Text("Job Location") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = resumeData.experience,
                        onValueChange = { onUpdateResume(resumeData.copy(experience = it)) },
                        label = { Text("Responsibilities & Achievements") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp),
                        maxLines = 6
                    )
                }

                3 -> {
                    // Education
                    Text("Education & Academics", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = DeepNavy)

                    OutlinedTextField(
                        value = resumeData.education,
                        onValueChange = { onUpdateResume(resumeData.copy(education = it)) },
                        label = { Text("Degree / Qualification (e.g. MBA, B.Tech, B.Com)") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = resumeData.institute,
                        onValueChange = { onUpdateResume(resumeData.copy(institute = it)) },
                        label = { Text("University / College / Institute") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = resumeData.eduyear,
                            onValueChange = { onUpdateResume(resumeData.copy(eduyear = it)) },
                            label = { Text("Year of Passing") },
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = resumeData.grade,
                            onValueChange = { onUpdateResume(resumeData.copy(grade = it)) },
                            label = { Text("Grade / CGPA / %") },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                4 -> {
                    // Skills & Highlights
                    Text("Skills, Projects & Certifications", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = DeepNavy)

                    OutlinedTextField(
                        value = resumeData.skills,
                        onValueChange = { onUpdateResume(resumeData.copy(skills = it)) },
                        label = { Text("Key Skills (comma separated) *") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = resumeData.projects,
                        onValueChange = { onUpdateResume(resumeData.copy(projects = it)) },
                        label = { Text("Key Projects / Initiatives") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        maxLines = 4
                    )

                    OutlinedTextField(
                        value = resumeData.certifications,
                        onValueChange = { onUpdateResume(resumeData.copy(certifications = it)) },
                        label = { Text("Certifications & Courses") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = resumeData.languages,
                        onValueChange = { onUpdateResume(resumeData.copy(languages = it)) },
                        label = { Text("Languages Known") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun ResumePreviewTab(
    resumeData: ResumeData,
    selectedTemplate: TemplateType,
    onSelectTemplate: (TemplateType) -> Unit,
    onExport: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Template Selector Chips
        Text("Choose ATS Template Style:", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = DeepNavy)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TemplateType.values().forEach { tmpl ->
                val isSelected = tmpl == selectedTemplate
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isSelected) CrimsonRed else Color.White)
                        .border(
                            1.dp,
                            if (isSelected) CrimsonRed else Color(0xFFCBD5E1),
                            RoundedCornerShape(8.dp)
                        )
                        .clickable { onSelectTemplate(tmpl) }
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = tmpl.displayName,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) Color.White else DeepNavy
                    )
                }
            }
        }

        // Formatted Document Canvas
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                // Header based on Template
                when (selectedTemplate) {
                    TemplateType.CLASSIC -> {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = resumeData.name.ifBlank { "YOUR NAME" },
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = DeepNavy,
                                letterSpacing = 1.sp
                            )
                            Text(
                                text = resumeData.role.ifBlank { "Target Professional Title" }.uppercase(),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = CrimsonRed
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "${resumeData.phone} • ${resumeData.email} • ${resumeData.location}",
                                fontSize = 11.sp,
                                color = Color(0xFF64748B)
                            )
                            if (resumeData.linkedin.isNotBlank()) {
                                Text(
                                    text = resumeData.linkedin,
                                    fontSize = 11.sp,
                                    color = Color(0xFF2563EB)
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Divider(color = CrimsonRed, thickness = 2.dp)
                        }
                    }

                    TemplateType.MODERN -> {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFF0F172A))
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .clip(CircleShape)
                                    .background(CrimsonRed),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = resumeData.name.take(2).uppercase().ifBlank { "RR" },
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = resumeData.name.ifBlank { "YOUR NAME" },
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = resumeData.role.ifBlank { "Professional Role" },
                                    fontSize = 12.sp,
                                    color = CrimsonRed
                                )
                                Text(
                                    text = "${resumeData.phone} | ${resumeData.email}",
                                    fontSize = 10.sp,
                                    color = Color(0xFF94A3B8)
                                )
                            }
                        }
                    }

                    TemplateType.EXECUTIVE -> {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = resumeData.name.ifBlank { "YOUR NAME" },
                                fontSize = 24.sp,
                                fontWeight = FontWeight.ExtraBold,
                                fontFamily = FontFamily.Serif,
                                color = DeepNavy
                            )
                            Text(
                                text = resumeData.role.ifBlank { "Executive Leadership" },
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF64748B)
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = resumeData.location, fontSize = 11.sp, color = Color(0xFF475569))
                                Text(text = resumeData.email, fontSize = 11.sp, color = Color(0xFF475569))
                                Text(text = resumeData.phone, fontSize = 11.sp, color = Color(0xFF475569))
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Divider(color = DeepNavy, thickness = 1.dp)
                        }
                    }

                    TemplateType.ATS_CLEAN -> {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = resumeData.name.ifBlank { "YOUR NAME" }.uppercase(),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.Black
                            )
                            Text(
                                text = "${resumeData.phone} | ${resumeData.email} | ${resumeData.location}",
                                fontSize = 11.sp,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Divider(color = Color.Black, thickness = 1.dp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Summary Section
                if (resumeData.summary.isNotBlank()) {
                    SectionHeading("PROFESSIONAL SUMMARY", selectedTemplate)
                    Text(
                        text = resumeData.summary,
                        fontSize = 12.sp,
                        color = Color(0xFF334155),
                        lineHeight = 17.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                // Experience Section
                if (resumeData.company.isNotBlank() || resumeData.experience.isNotBlank()) {
                    SectionHeading("WORK EXPERIENCE", selectedTemplate)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${resumeData.jobtitle.ifBlank { "Position" }} — ${resumeData.company.ifBlank { "Company" }}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = DeepNavy
                        )
                        Text(
                            text = resumeData.dates.ifBlank { "Present" },
                            fontSize = 11.sp,
                            color = Color(0xFF64748B)
                        )
                    }
                    if (resumeData.joblocation.isNotBlank()) {
                        Text(text = resumeData.joblocation, fontSize = 11.sp, color = Color(0xFF64748B))
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = resumeData.experience,
                        fontSize = 12.sp,
                        color = Color(0xFF334155),
                        lineHeight = 17.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                // Skills Section
                if (resumeData.skills.isNotBlank()) {
                    SectionHeading("CORE COMPETENCIES & SKILLS", selectedTemplate)
                    val skillList = resumeData.skills.split(",").map { it.trim() }.filter { it.isNotBlank() }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            skillList.chunked(3).forEach { rowSkills ->
                                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    rowSkills.forEach { skill ->
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(4.dp))
                                                .background(Color(0xFFF1F5F9))
                                                .border(0.5.dp, Color(0xFFCBD5E1), RoundedCornerShape(4.dp))
                                                .padding(horizontal = 6.dp, vertical = 2.dp)
                                        ) {
                                            Text(text = skill, fontSize = 10.sp, color = DeepNavy)
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }

                // Education Section
                if (resumeData.education.isNotBlank() || resumeData.institute.isNotBlank()) {
                    SectionHeading("EDUCATION", selectedTemplate)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${resumeData.education} — ${resumeData.institute}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = DeepNavy
                        )
                        Text(
                            text = resumeData.eduyear,
                            fontSize = 11.sp,
                            color = Color(0xFF64748B)
                        )
                    }
                    if (resumeData.grade.isNotBlank()) {
                        Text(text = "Grade: ${resumeData.grade}", fontSize = 11.sp, color = Color(0xFF64748B))
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }

                // Projects / Certifications
                if (resumeData.projects.isNotBlank()) {
                    SectionHeading("KEY PROJECTS", selectedTemplate)
                    Text(
                        text = resumeData.projects,
                        fontSize = 11.sp,
                        color = Color(0xFF334155),
                        lineHeight = 16.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                if (resumeData.certifications.isNotBlank()) {
                    SectionHeading("CERTIFICATIONS", selectedTemplate)
                    Text(
                        text = resumeData.certifications,
                        fontSize = 11.sp,
                        color = Color(0xFF334155),
                        lineHeight = 16.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                if (resumeData.languages.isNotBlank()) {
                    SectionHeading("LANGUAGES", selectedTemplate)
                    Text(
                        text = resumeData.languages,
                        fontSize = 11.sp,
                        color = Color(0xFF334155)
                    )
                }
            }
        }

        Button(
            onClick = onExport,
            colors = ButtonDefaults.buttonColors(containerColor = CrimsonRed),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp)
        ) {
            Icon(imageVector = Icons.Default.Share, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Export & Share Resume", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun SectionHeading(title: String, template: TemplateType) {
    Column(modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp)) {
        Text(
            text = title,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = if (template == TemplateType.ATS_CLEAN) Color.Black else CrimsonRed,
            letterSpacing = 0.5.sp
        )
        Divider(
            color = if (template == TemplateType.ATS_CLEAN) Color.Black else Color(0xFFE2E8F0),
            thickness = 1.dp
        )
    }
}

@Composable
private fun ResumeAtsTab(
    score: Int,
    resumeData: ResumeData,
    onJumpToEdit: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Score Card
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = DeepNavy),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("ATS Readability & Match Score", color = Color(0xFF94A3B8), fontSize = 13.sp)
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(if (score >= 85) Color(0xFF10B981) else if (score >= 70) Color(0xFFF59E0B) else CrimsonRed),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "$score",
                            color = Color.White,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Black
                        )
                        Text(
                            text = "/ 100",
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 11.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = if (score >= 85) "Excellent ATS Optimization!" else if (score >= 70) "Good Foundation — Room for Keywords" else "Needs Improvement",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = if (score >= 85)
                        "Your resume contains structured headings, explicit job target keywords, and clean typography suitable for enterprise ATS parsers."
                    else
                        "Recruiters filter by specific keywords. Add measurable achievements and at least 5 core technical competencies.",
                    color = Color(0xFFCBD5E1),
                    fontSize = 12.sp,
                    lineHeight = 17.sp,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }

        // ATS Checklist
        Text("Applicant Tracking System Checklist", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = DeepNavy)

        val checks = listOf(
            Triple("Contact Information & Role", resumeData.name.isNotBlank() && resumeData.phone.isNotBlank() && resumeData.email.isNotBlank(), 0),
            Triple("Target Role & Summary", resumeData.summary.isNotBlank(), 1),
            Triple("Work Experience & Metrics", resumeData.experience.isNotBlank() && resumeData.company.isNotBlank(), 2),
            Triple("Educational Qualifications", resumeData.education.isNotBlank() && resumeData.institute.isNotBlank(), 3),
            Triple("Industry Keywords & Skills (min 5)", resumeData.skills.split(",").count { it.isNotBlank() } >= 5, 4)
        )

        checks.forEach { (title, passed, subSection) ->
            Card(
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onJumpToEdit(subSection) }
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (passed) Icons.Default.CheckCircle else Icons.Default.Warning,
                        contentDescription = null,
                        tint = if (passed) Color(0xFF10B981) else CrimsonRed,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = title, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = DeepNavy)
                        Text(
                            text = if (passed) "Ready & Parsable" else "Tap to complete section",
                            fontSize = 11.sp,
                            color = if (passed) Color(0xFF10B981) else CrimsonRed
                        )
                    }
                    Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = Color(0xFF94A3B8))
                }
            }
        }
    }
}

private fun buildResumeShareText(resume: ResumeData): String {
    return """
========================================
${resume.name.uppercase()}
${resume.role}
Phone: ${resume.phone} | Email: ${resume.email}
Location: ${resume.location}
LinkedIn: ${resume.linkedin}
========================================

PROFESSIONAL SUMMARY:
${resume.summary}

WORK EXPERIENCE:
${resume.jobtitle} | ${resume.company} (${resume.dates})
Location: ${resume.joblocation}
${resume.experience}

CORE SKILLS:
${resume.skills}

EDUCATION:
${resume.education} - ${resume.institute} (${resume.eduyear}) Grade: ${resume.grade}

PROJECTS:
${resume.projects}

CERTIFICATIONS:
${resume.certifications}

LANGUAGES:
${resume.languages}

Generated via RRGBS AI Resume Builder
========================================
    """.trimIndent()
}
