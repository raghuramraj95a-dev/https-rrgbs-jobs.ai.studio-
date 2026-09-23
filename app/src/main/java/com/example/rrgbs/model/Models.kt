package com.example.rrgbs.model

data class Job(
    val id: String,
    val title: String,
    val company: String,
    val companyLogoText: String,
    val location: String,
    val category: String,
    val type: String, // "Full Time", "Part Time", "Contract", "Internship", "Work From Home"
    val experience: String,
    val salary: String,
    val description: String,
    val skills: List<String>,
    val responsibilities: List<String> = emptyList(),
    val requirements: List<String> = emptyList(),
    val benefits: List<String> = emptyList(),
    val postedDate: String,
    val isFeatured: Boolean = false
)

data class JobCategory(
    val id: String,
    val title: String,
    val icon: String,
    val count: Int,
    val description: String
)

data class RecruitmentService(
    val id: String,
    val title: String,
    val icon: String,
    val description: String,
    val features: List<String>
)

data class ApplicationSubmission(
    val id: String,
    val jobId: String,
    val jobTitle: String,
    val company: String,
    val applicantName: String,
    val email: String,
    val phone: String,
    val experience: String,
    val currentLocation: String,
    val resumeFileName: String,
    val notes: String = "",
    val appliedAt: String
)

data class FilterState(
    val keyword: String = "",
    val location: String = "",
    val jobType: String = "",
    val category: String = "",
    val experience: String = ""
)

data class HomeServiceItem(
    val id: String,
    val title: String,
    val icon: String,
    val tagline: String,
    val description: String,
    val checklist: List<String>,
    val startingPrice: String = "",
    val badge: String = ""
)

data class ServiceEnquirySubmission(
    val id: String,
    val name: String,
    val phone: String,
    val email: String = "",
    val service: String,
    val location: String,
    val message: String = "",
    val createdAt: String
)

data class StoreProduct(
    val id: Int,
    val name: String,
    val category: String,
    val price: Int,
    val icon: String,
    val tag: String,
    val description: String
)

data class CartItem(
    val product: StoreProduct,
    val qty: Int
)

data class StoreCategory(
    val id: String,
    val name: String,
    val icon: String,
    val description: String
)

data class ResumeData(
    val name: String = "",
    val role: String = "",
    val phone: String = "",
    val email: String = "",
    val location: String = "",
    val linkedin: String = "",
    val summary: String = "",
    val company: String = "",
    val jobtitle: String = "",
    val dates: String = "",
    val joblocation: String = "",
    val experience: String = "",
    val education: String = "",
    val institute: String = "",
    val eduyear: String = "",
    val grade: String = "",
    val skills: String = "",
    val projects: String = "",
    val certifications: String = "",
    val languages: String = "",
    val target: String = ""
)

enum class TemplateType(val displayName: String) {
    CLASSIC("Classic"),
    MODERN("Modern"),
    EXECUTIVE("Executive"),
    ATS_CLEAN("ATS Clean")
}

data class AuthUser(
    val name: String,
    val role: String, // "candidate" or "employer"
    val email: String
)
