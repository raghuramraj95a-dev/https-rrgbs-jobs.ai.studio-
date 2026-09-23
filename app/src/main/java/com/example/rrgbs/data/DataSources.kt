package com.example.rrgbs.data

import com.example.rrgbs.model.*

object DataSources {

    val DEMO_RESUME = ResumeData(
        name = "Rahul Kumar",
        role = "HR & Recruitment Executive",
        phone = "+91 98765 43210",
        email = "rahul@example.com",
        location = "Bengaluru, Karnataka",
        linkedin = "linkedin.com/in/rahulkumar",
        summary = "Recruitment and HR professional with experience supporting end-to-end hiring, candidate coordination, documentation and employee processes. Strong communication and stakeholder management skills with a practical, target-focused approach.",
        company = "RRGBS / Tech Solutions",
        jobtitle = "HR & Recruitment Executive",
        dates = "2023 – Present",
        joblocation = "Bengaluru",
        experience = "Handled candidate sourcing and screening across tech and non-tech domains. Coordinated interviews, maintained candidate records, supported employee onboarding and collaborated closely with hiring managers to meet recruitment targets consistently.",
        education = "MBA / B.Com",
        institute = "Bangalore University",
        eduyear = "2023",
        grade = "75%",
        skills = "Talent Acquisition, Candidate Sourcing, Interview Coordination, HR Operations, MS Excel, Stakeholder Communication, Statutory Compliance",
        projects = "Bulk Hiring Drive — Led sourcing and coordination for 50+ campus recruits within 30 days.\nProcess Streamlining — Reduced interview turnaround time by 20% through standardized candidate tracking sheets.",
        certifications = "Certified Talent Sourcing Professional — HRCI\nHR & Payroll Fundamentals — RRGBS Academy",
        languages = "English, Kannada, Hindi",
        target = "Senior HR Executive / Talent Specialist"
    )

    val INITIAL_JOBS = listOf(
        Job(
            id = "job-1",
            title = "Software Developer",
            company = "IT Company",
            companyLogoText = "IT",
            location = "Bangalore",
            category = "IT & Software",
            type = "Full Time",
            experience = "2–4 Years",
            salary = "₹4 – ₹8 LPA",
            description = "We are seeking an experienced Software Developer proficient in modern web applications, RESTful APIs, and database architecture to build robust enterprise client solutions.",
            skills = listOf("React.js", "Node.js", "TypeScript", "PostgreSQL", "REST APIs"),
            responsibilities = listOf(
                "Design, develop, and maintain responsive front-end and backend services.",
                "Collaborate with product managers and QA to deliver scalable features.",
                "Write clean, well-tested, and documented code adhering to engineering best practices.",
                "Optimize application performance and latency for high-traffic workloads."
            ),
            requirements = listOf(
                "2 to 4 years of hands-on experience in full-stack web software development.",
                "Proficiency in JavaScript/TypeScript, modern UI frameworks, and relational databases.",
                "Bachelor’s degree in Computer Science, Information Technology, or equivalent."
            ),
            benefits = listOf("Health Insurance", "Performance Bonus", "Flexible Working Hours", "Continuous Learning Allowance"),
            postedDate = "Just now",
            isFeatured = true
        ),
        Job(
            id = "job-2",
            title = "Customer Support Executive",
            company = "BPO Company",
            companyLogoText = "BPO",
            location = "Bangalore",
            category = "BPO & Customer Support",
            type = "Full Time",
            experience = "0–2 Years",
            salary = "₹18,000 – ₹28,000",
            description = "Join a leading customer experience operations team providing multi-channel inbound customer support, query resolution, and CRM ticket management.",
            skills = listOf("Customer Service", "Fluent English", "Email & Chat Support", "CRM Tools", "Problem Solving"),
            responsibilities = listOf(
                "Handle customer inquiries via phone calls, live chat, and ticketing systems.",
                "Maintain first-call resolution benchmarks and ensure high customer satisfaction.",
                "Document interactions accurately in customer relationship management systems."
            ),
            requirements = listOf(
                "0 to 2 years experience (freshers with good communication skills are encouraged to apply).",
                "Excellent verbal and written communication in English and Hindi/Kannada.",
                "Basic computer literacy and fast typing speed."
            ),
            benefits = listOf("Cab Facility / Travel Allowance", "Overtime Allowance", "Rotational Shifts Incentives"),
            postedDate = "1 day ago",
            isFeatured = true
        ),
        Job(
            id = "job-3",
            title = "HR Recruiter",
            company = "RRGBS",
            companyLogoText = "HR",
            location = "Shivamogga",
            category = "HR & Administration",
            type = "Full Time",
            experience = "0–3 Years",
            salary = "₹15,000 – ₹25,000",
            description = "RRGBS is looking for an enthusiastic HR Recruiter to join our talent acquisition desk in Shivamogga, sourcing candidates for IT, Non-IT, and bulk staffing mandates.",
            skills = listOf("Talent Sourcing", "Candidate Screening", "Interview Scheduling", "Portal Sourcing", "Cold Calling"),
            responsibilities = listOf(
                "Source candidate profiles through job portals, LinkedIn, and social networks.",
                "Conduct preliminary candidate phone screenings and evaluate candidate fit.",
                "Coordinate interview schedules with hiring managers and follow up throughout the hiring pipeline.",
                "Maintain candidate databases and recruitment pipeline reports."
            ),
            requirements = listOf(
                "0 to 3 years of recruitment experience (graduates with strong communication welcome).",
                "Knowledge of recruitment life cycles, staffing metrics, and candidate follow-ups.",
                "Resident of or willing to work from Shivamogga office."
            ),
            benefits = listOf("Attractive Placement Incentives", "Annual Appraisal", "Supportive Team Culture"),
            postedDate = "2 days ago",
            isFeatured = true
        ),
        Job(
            id = "job-4",
            title = "Warehouse Executive",
            company = "Logistics Company",
            companyLogoText = "OPS",
            location = "Bangalore",
            category = "Logistics",
            type = "Contract",
            experience = "0–2 Years",
            salary = "₹18,000 – ₹25,000",
            description = "Looking for a dedicated Warehouse Executive to manage inventory sorting, inbound/outbound dispatches, and stock auditing at our central logistics fulfillment center.",
            skills = listOf("Inventory Tracking", "Dispatch Coordination", "Barcode Scanning", "ERP / WMS", "Material Handling"),
            responsibilities = listOf(
                "Supervise loading, unloading, barcode scanning, and bin allocation of goods.",
                "Verify physical stock against digital dispatch manifests and purchase orders.",
                "Coordinate with fleet drivers and delivery partners for timely truck departures."
            ),
            requirements = listOf(
                "0 to 2 years experience in warehousing, inventory, or fulfillment logistics.",
                "Physical stamina, punctuality, and attention to detail.",
                "Familiarity with handheld barcode scanners is preferred."
            ),
            benefits = listOf("Performance Incentives", "Subsidized Canteen", "ESI & PF Coverage"),
            postedDate = "2 days ago"
        ),
        Job(
            id = "job-5",
            title = "Digital Marketing Associate",
            company = "Growth Marketing Agency",
            companyLogoText = "DM",
            location = "Remote",
            category = "Sales & Marketing",
            type = "Work From Home",
            experience = "1–3 Years",
            salary = "₹25,000 – ₹38,000",
            description = "Run performance ad campaigns across Meta and Google Ads, craft persuasive ad copy, and optimize conversion funnels for e-commerce and B2B clients.",
            skills = listOf("Meta Ads", "Google Ads", "Content Writing", "Analytics", "SEO"),
            responsibilities = listOf(
                "Set up and manage paid ad campaigns across Google Search and Meta Ads.",
                "Analyze weekly ROAS metrics, conversion rates, and recommend spend allocation.",
                "Collaborate with graphic designers to produce high-converting creative assets."
            ),
            requirements = listOf(
                "1 to 3 years executing paid social and search advertising campaigns.",
                "Demonstrated track record of achieving target cost-per-acquisition (CPA).",
                "Certifications in Google Ads or Meta Blueprint are an added advantage."
            ),
            benefits = listOf("100% Remote / Work from Home", "Internet & Tech Allowance", "Quarterly Learning Stipend"),
            postedDate = "3 days ago"
        ),
        Job(
            id = "job-6",
            title = "Payroll & Compliance Specialist",
            company = "RRGBS Staffing Division",
            companyLogoText = "HR",
            location = "Shivamogga",
            category = "HR & Administration",
            type = "Full Time",
            experience = "2–5 Years",
            salary = "₹22,000 – ₹35,000",
            description = "Manage end-to-end monthly payroll processing, statutory deductions (PF, ESI, PT), and employee tax filings for multi-client outsourced staffing mandates.",
            skills = listOf("Payroll Processing", "PF & ESI Compliance", "TDS Computation", "MS Excel", "Labor Laws"),
            responsibilities = listOf(
                "Process monthly salary disbursement runs for contract and permanent staff.",
                "Prepare statutory return challans for PF, ESI, Professional Tax, and LWF.",
                "Address employee salary queries and ensure 100% statutory compliance audit readiness."
            ),
            requirements = listOf(
                "2+ years handling payroll or statutory compliance in a staffing or corporate agency.",
                "Proficiency in Indian labor laws, PF portals, and advanced spreadsheet formulas."
            ),
            benefits = listOf("Annual Performance Bonus", "Corporate Health Insurance", "5-day Work Week"),
            postedDate = "3 days ago"
        )
    )

    val CATEGORIES = listOf(
        JobCategory("cat-it", "IT & Software", "💻", 24, "Technology & software jobs"),
        JobCategory("cat-bpo", "BPO & Customer Support", "📞", 18, "Customer service opportunities"),
        JobCategory("cat-sales", "Sales & Marketing", "📊", 15, "Sales and business development"),
        JobCategory("cat-mfg", "Manufacturing", "🏭", 12, "Industrial opportunities"),
        JobCategory("cat-logistics", "Logistics", "🚚", 14, "Warehouse & delivery jobs"),
        JobCategory("cat-hr", "HR & Administration", "👨‍💼", 11, "HR and office positions"),
        JobCategory("cat-freshers", "Freshers", "🎓", 32, "Entry-level opportunities"),
        JobCategory("cat-wfh", "Work From Home", "🏠", 16, "Remote opportunities")
    )

    val RECRUITMENT_SERVICES = listOf(
        RecruitmentService(
            id = "serv-1",
            title = "Permanent Hiring",
            icon = "👥",
            description = "Professional recruitment solutions for permanent positions across all organizational tiers.",
            features = listOf("Direct hire talent search", "Rigorous pre-screening", "Role-specific assessments", "Guaranteed candidate replacement warranty")
        ),
        RecruitmentService(
            id = "serv-2",
            title = "Contract Staffing",
            icon = "🏢",
            description = "Flexible workforce solutions for short and long-term business requirements.",
            features = listOf("Rapid project deployments", "Zero long-term headcount liability", "Third-party payroll compliance", "Scalable ramp-up and ramp-down")
        ),
        RecruitmentService(
            id = "serv-3",
            title = "RPO Services",
            icon = "📋",
            description = "Recruitment process outsourcing for growing organizations needing scalable capacity.",
            features = listOf("Dedicated talent acquisition desk", "ATS & pipeline management", "Reduced cost-per-hire", "Employer branding optimization")
        ),
        RecruitmentService(
            id = "serv-4",
            title = "Payroll Outsourcing",
            icon = "💰",
            description = "Payroll administration, statutory deductions and workforce labor compliance support.",
            features = listOf("Statutory PF, ESI, PT filings", "Direct bank disbursement runs", "Digital payslips & tax reports", "100% labor compliance guarantee")
        ),
        RecruitmentService(
            id = "serv-5",
            title = "Bulk Hiring",
            icon = "🚀",
            description = "Large-scale hiring support for business expansion, call centers, and plant rollouts.",
            features = listOf("Campus & walk-in drive execution", "High-volume screening speed", "Turnkey onboarding support", "Multi-location deployment")
        ),
        RecruitmentService(
            id = "serv-6",
            title = "Facility & Manpower Support",
            icon = "🛡️",
            description = "Trained security, housekeeping, and front-desk personnel for enterprise offices.",
            features = listOf("Background-verified staff", "Uniformed & disciplined teams", "24/7 supervisor support", "Rapid replacement assurance")
        )
    )

    val HOME_SERVICES = listOf(
        HomeServiceItem(
            id = "cleaning",
            title = "Home Cleaning & Housekeeping",
            icon = "🏠",
            tagline = "Professional cleaning for comfortable homes",
            description = "Professional support for maintaining a clean, sanitized, and comfortable home.",
            checklist = listOf(
                "Deep Cleaning",
                "Regular Housekeeping",
                "Kitchen Cleaning",
                "Bathroom Cleaning",
                "Move-in / Move-out Cleaning"
            ),
            startingPrice = "From ₹1,499",
            badge = "Most Popular"
        ),
        HomeServiceItem(
            id = "elderly-care",
            title = "Elderly Care & Support",
            icon = "👴",
            tagline = "Compassionate assistance for seniors",
            description = "Personal assistance, gentle companionship, and daily support for elderly family members.",
            checklist = listOf(
                "Elderly Assistance",
                "Attendant Services",
                "Companion Support",
                "Daily Living Assistance",
                "Post-Hospital Support"
            ),
            startingPrice = "Flexible Shifts",
            badge = "Trained Attendants"
        ),
        HomeServiceItem(
            id = "nursing",
            title = "Home Nursing Support",
            icon = "🩺",
            tagline = "Clinical assistance at your doorstep",
            description = "Home-care assistance coordinated according to customer medical and recovery requirements.",
            checklist = listOf(
                "Patient Attendants",
                "Basic Nursing Support",
                "Post-Operative Support",
                "Long-Term Care Support",
                "Home Care Assistance"
            ),
            startingPrice = "Day / Night Shifts",
            badge = "Verified Staff"
        ),
        HomeServiceItem(
            id = "maintenance",
            title = "Home Maintenance",
            icon = "🔧",
            tagline = "Quick repairs by skilled technicians",
            description = "Dependable support for essential household repairs, installations, and ongoing maintenance.",
            checklist = listOf(
                "Electrical Services",
                "Plumbing",
                "Carpentry",
                "Painting",
                "General Maintenance"
            ),
            startingPrice = "From ₹299 visit",
            badge = "Quick Response"
        ),
        HomeServiceItem(
            id = "security",
            title = "Security & Caretaker Services",
            icon = "🛡️",
            tagline = "Trusted safety for homes & properties",
            description = "Residential manpower and round-the-clock protective support services for homes and properties.",
            checklist = listOf(
                "Residential Security",
                "Security Guards",
                "Caretakers",
                "Housekeeping Staff",
                "Property Support"
            ),
            startingPrice = "12h / 24h Guard",
            badge = "Background Checked"
        ),
        HomeServiceItem(
            id = "other",
            title = "Other Home Services",
            icon = "🌿",
            tagline = "Comprehensive home support services",
            description = "Additional specialized support services coordinated according to availability and need.",
            checklist = listOf(
                "Gardening",
                "Driver Services",
                "Packers & Movers Support",
                "Pest-Control Coordination",
                "Home Support Staff"
            ),
            startingPrice = "Customized Booking",
            badge = "On Demand"
        )
    )

    val STORE_CATEGORIES = listOf(
        StoreCategory("Housekeeping", "Housekeeping", "🧹", "Cleaning & facility products"),
        StoreCategory("Uniforms", "Uniforms & Workwear", "👕", "Staff uniforms & safety wear"),
        StoreCategory("IT", "IT & Laptops", "💻", "Computers & accessories"),
        StoreCategory("Biometric", "Biometric & Security", "🔐", "Attendance & access control"),
        StoreCategory("Corporate Gifts", "Corporate Gifts", "🎁", "Branded business gifts"),
        StoreCategory("Joining Kits", "Employee Joining Kits", "🎒", "Welcome & onboarding kits"),
        StoreCategory("Safety", "Safety Products", "⛑️", "PPE & workplace safety"),
        StoreCategory("Office", "Office Supplies", "📋", "Stationery & workplace products")
    )

    val STORE_PRODUCTS = listOf(
        StoreProduct(1, "Professional Floor Cleaner 5 Litre", "Housekeeping", 650, "🧴", "POPULAR", "Commercial floor cleaning solution."),
        StoreProduct(2, "Microfiber Cleaning Mop", "Housekeeping", 399, "🧹", "BEST SELLER", "Reusable microfiber commercial mop."),
        StoreProduct(3, "Heavy Duty Garbage Bin 60L", "Housekeeping", 1299, "🗑️", "BUSINESS", "Durable workplace waste bin."),
        StoreProduct(4, "Housekeeping Staff Uniform", "Uniforms", 799, "👕", "CUSTOM", "Customizable housekeeping uniform."),
        StoreProduct(5, "Security Guard Uniform", "Uniforms", 999, "🥋", "CUSTOM", "Professional security workwear."),
        StoreProduct(6, "Safety Reflective Jacket", "Safety", 299, "🦺", "SAFETY", "High visibility reflective workwear."),
        StoreProduct(7, "Business Laptop 15.6 inch", "IT", 45999, "💻", "IT", "Business productivity laptop."),
        StoreProduct(8, "Wireless Keyboard & Mouse", "IT", 1199, "⌨️", "OFFICE", "Wireless desktop keyboard and mouse."),
        StoreProduct(9, "24 inch Business Monitor", "IT", 8999, "🖥️", "IT", "Full HD business monitor."),
        StoreProduct(10, "Fingerprint Biometric Attendance", "Biometric", 4999, "🔐", "POPULAR", "Employee fingerprint attendance device."),
        StoreProduct(11, "Face Recognition Attendance System", "Biometric", 8999, "📷", "BUSINESS", "Face recognition attendance solution."),
        StoreProduct(12, "RFID Employee ID Card", "Biometric", 99, "🪪", "BULK", "RFID-enabled employee access card."),
        StoreProduct(13, "Corporate Notebook", "Corporate Gifts", 149, "📓", "BRANDING", "Custom company logo notebook."),
        StoreProduct(14, "Corporate Gift Mug", "Corporate Gifts", 199, "☕", "CUSTOM", "Branded corporate coffee mug."),
        StoreProduct(15, "Corporate Backpack", "Corporate Gifts", 699, "🎒", "POPULAR", "Custom branded office backpack."),
        StoreProduct(16, "Employee Joining Kit", "Joining Kits", 799, "🎁", "CORPORATE", "Customized employee welcome kit with ID, notebook, pen, mug."),
        StoreProduct(17, "Employee ID Card & Lanyard", "Joining Kits", 129, "🪪", "BULK", "Professional employee identification kit."),
        StoreProduct(18, "Safety Helmet", "Safety", 299, "⛑️", "SAFETY", "Industrial safety helmet."),
        StoreProduct(19, "Safety Shoes", "Safety", 899, "🥾", "WORKWEAR", "Industrial protective safety footwear."),
        StoreProduct(20, "Office Stationery Kit", "Office", 499, "📋", "OFFICE", "Essential office stationery set."),
        StoreProduct(21, "A4 Copier Paper Box", "Office", 2999, "📄", "OFFICE", "Business-use copier paper."),
        StoreProduct(22, "Branded Executive Pen", "Corporate Gifts", 99, "🖊️", "BULK", "Corporate promotional pen.")
    )

    val SAMPLE_JOINING_KIT_ITEMS = listOf(
        "📁 Joining Folder",
        "📓 Company Notebook",
        "🖊️ Branded Pen",
        "🪪 Employee ID Card",
        "🎒 Laptop / Backpack",
        "👕 Company T-Shirt",
        "☕ Branded Mug",
        "📄 Employee Handbook",
        "🔑 ID Card Lanyard",
        "🎁 Welcome Gift"
    )
}
