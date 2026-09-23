import React, { useState, useEffect, useRef } from 'react';
import { RRGBSLogo } from '../common/RRGBSLogo';
import {
  Sparkles,
  Printer,
  Download,
  RotateCcw,
  Briefcase,
  Home,
  ShoppingBag,
  FileText,
  CheckCircle2,
  AlertCircle,
  HelpCircle,
  ArrowRight,
  User,
  GraduationCap,
  Award,
  Layers,
  Phone,
  Mail,
  MapPin,
  Linkedin,
  Globe,
  Plus,
  Trash2,
} from 'lucide-react';

interface AIResumeBuilderViewProps {
  onSwitchPortal: (portal: 'home' | 'jobs' | 'services' | 'store' | 'resume') => void;
  onShowToast: (message: string, type: 'success' | 'info' | 'error') => void;
}

export type TemplateType = 'Classic' | 'Modern' | 'Executive' | 'ATS Clean';

interface ResumeData {
  name: string;
  role: string;
  phone: string;
  email: string;
  location: string;
  linkedin: string;
  summary: string;
  company: string;
  jobtitle: string;
  dates: string;
  joblocation: string;
  experience: string;
  education: string;
  institute: string;
  eduyear: string;
  grade: string;
  skills: string;
  projects: string;
  certifications: string;
  languages: string;
  target: string;
}

const DEMO_RESUME: ResumeData = {
  name: 'Rahul Kumar',
  role: 'HR & Recruitment Executive',
  phone: '+91 98765 43210',
  email: 'rahul@example.com',
  location: 'Bengaluru, Karnataka',
  linkedin: 'linkedin.com/in/rahulkumar',
  summary:
    'Recruitment and HR professional with experience supporting end-to-end hiring, candidate coordination, documentation and employee processes. Strong communication and stakeholder management skills with a practical, target-focused approach.',
  company: 'RRGBS / Tech Solutions',
  jobtitle: 'HR & Recruitment Executive',
  dates: '2023 – Present',
  joblocation: 'Bengaluru',
  experience:
    'Handled candidate sourcing and screening across tech and non-tech domains. Coordinated interviews, maintained candidate records, supported employee onboarding and collaborated closely with hiring managers to meet recruitment targets consistently.',
  education: 'MBA / B.Com',
  institute: 'Bangalore University',
  eduyear: '2023',
  grade: '75%',
  skills:
    'Talent Acquisition, Candidate Sourcing, Interview Coordination, HR Operations, MS Excel, Stakeholder Communication, Statutory Compliance',
  projects:
    'Bulk Hiring Drive — Led sourcing and coordination for 50+ campus recruits within 30 days.\nProcess Streamlining — Reduced interview turnaround time by 20% through standardized candidate tracking sheets.',
  certifications:
    'Certified Talent Sourcing Professional — HRCI\nHR & Payroll Fundamentals — RRGBS Academy',
  languages: 'English, Kannada, Hindi',
  target: 'Senior HR Executive / Talent Specialist',
};

const INITIAL_RESUME: ResumeData = {
  name: '',
  role: '',
  phone: '',
  email: '',
  location: '',
  linkedin: '',
  summary: '',
  company: '',
  jobtitle: '',
  dates: '',
  joblocation: '',
  experience: '',
  education: '',
  institute: '',
  eduyear: '',
  grade: '',
  skills: '',
  projects: '',
  certifications: '',
  languages: '',
  target: '',
};

export const AIResumeBuilderView: React.FC<AIResumeBuilderViewProps> = ({
  onSwitchPortal,
  onShowToast,
}) => {
  const [resumeData, setResumeData] = useState<ResumeData>(() => {
    try {
      const saved = localStorage.getItem('rrgbs_ai_resume_data');
      return saved ? JSON.parse(saved) : DEMO_RESUME;
    } catch {
      return DEMO_RESUME;
    }
  });

  const [activeTemplate, setActiveTemplate] = useState<TemplateType>('Classic');
  const [activeStep, setActiveStep] = useState<number>(1);
  const [resumeId, setResumeId] = useState<string>('RRGBS-RES-2026-000001');
  const [atsScore, setAtsScore] = useState<number>(88);
  const [atsFeedback, setAtsFeedback] = useState<string>(
    'Strong ATS compatibility. Review keywords against target job descriptions.'
  );

  const resumeRef = useRef<HTMLDivElement>(null);

  // Sync to localStorage
  useEffect(() => {
    try {
      localStorage.setItem('rrgbs_ai_resume_data', JSON.stringify(resumeData));
    } catch {
      // ignore
    }
  }, [resumeData]);

  // Initial resume ID generator
  useEffect(() => {
    const year = new Date().getFullYear();
    const rand = String(Math.floor(Math.random() * 900000) + 100000);
    setResumeId(`RRGBS-RES-${year}-${rand}`);
    calculateAts(resumeData);
  }, []);

  const handleChange = (field: keyof ResumeData, value: string) => {
    setResumeData((prev) => ({ ...prev, [field]: value }));
  };

  const calculateAts = (data: ResumeData) => {
    const fields: (keyof ResumeData)[] = [
      'name',
      'role',
      'email',
      'phone',
      'summary',
      'experience',
      'education',
      'institute',
      'skills',
      'target',
    ];

    const filledCount = fields.filter((f) => Boolean(data[f]?.trim())).length;
    const skillsCount = (data.skills || '').split(',').filter((s) => s.trim()).length;

    let score = Math.round(45 + (filledCount / fields.length) * 45);
    if (skillsCount >= 5) score += 5;
    if (data.projects || data.certifications) score += 3;
    score = Math.min(98, score);

    setAtsScore(score);
    if (score >= 85) {
      setAtsFeedback(
        'Excellent ATS compatibility! Your resume contains clean headings, strong role definitions, and structured sections.'
      );
    } else if (score >= 70) {
      setAtsFeedback(
        'Good baseline! Add more industry-specific technical skills and quantifiable work metrics to cross 85+.'
      );
    } else {
      setAtsFeedback(
        'Incomplete fields detected. Fill in your experience, educational qualifications, and target job keywords.'
      );
    }
  };

  const handleAtsCheck = () => {
    calculateAts(resumeData);
    onShowToast(`ATS Check completed: Score ${atsScore}/100`, 'success');
  };

  const handleLoadDemo = () => {
    setResumeData(DEMO_RESUME);
    calculateAts(DEMO_RESUME);
    onShowToast('Demo HR & Recruitment resume loaded!', 'info');
  };

  const handleReset = () => {
    if (window.confirm('Are you sure you want to clear all resume fields?')) {
      setResumeData(INITIAL_RESUME);
      calculateAts(INITIAL_RESUME);
      onShowToast('Resume fields reset.', 'info');
    }
  };

  const handleGenerateSummary = () => {
    const role = resumeData.role.trim() || 'professional';
    const target = resumeData.target.trim() || role;
    const generated = `Results-oriented ${role} with a strong focus on operational efficiency, team collaboration and continuous process improvement. Proven track record in delivering reliable business outcomes, communicating effectively with internal and external stakeholders, and driving measurable value. Seeking a progressive ${target} position where I can apply my skills and grow with the organization.`;

    handleChange('summary', generated);
    onShowToast('Professional summary generated!', 'success');
  };

  const handleAiImproveSummary = () => {
    if (!resumeData.summary.trim()) {
      handleGenerateSummary();
      return;
    }
    const improved = `${resumeData.summary.trim()} Demonstrated expertise in managing high-priority deliverables, collaborating with cross-functional leadership, and maintaining stringent standards of excellence with a proactive, customer-focused approach.`;
    handleChange('summary', improved);
    onShowToast('AI summary enhancement applied!', 'success');
  };

  const handleAiImproveExperience = () => {
    if (!resumeData.experience.trim()) {
      const defExp =
        'Managed day-to-day operations and responsibilities, coordinated with key stakeholders, maintained comprehensive documentation, exceeded quarterly performance benchmarks, and championed continuous workflow improvements.';
      handleChange('experience', defExp);
    } else {
      const impExp = `${resumeData.experience.trim()} Spearheaded process optimization initiatives, maintained accurate compliance records, collaborated across multi-tiered teams, and directly contributed to organizational growth metrics.`;
      handleChange('experience', impExp);
    }
    onShowToast('AI experience enhancement applied!', 'success');
  };

  const handlePrint = () => {
    onShowToast('Preparing printable A4 format...', 'info');
    setTimeout(() => {
      window.print();
    }, 200);
  };

  const handleDownloadHTML = () => {
    if (!resumeRef.current) return;
    const resumeHTML = resumeRef.current.outerHTML;
    const fullDocument = `<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>RRGBS Resume - ${resumeData.name || 'Candidate'}</title>
  <style>
    @page { size: A4; margin: 12mm; }
    body { font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Arial, sans-serif; color: #17191d; background: #fff; margin: 0; padding: 20px; line-height: 1.5; }
    .resume { max-width: 800px; margin: auto; padding: 30px; background: #fff; }
    h1 { font-size: 28px; margin: 0 0 6px; }
    .role { color: #c91f2b; font-weight: bold; font-size: 15px; }
    .contact { font-size: 11px; color: #555; margin: 6px 0 14px; }
    h3 { font-size: 13px; text-transform: uppercase; letter-spacing: 1px; color: #c91f2b; border-bottom: 1.5px solid #ddd; padding-bottom: 4px; margin: 18px 0 8px; }
    p, li { font-size: 11px; line-height: 1.6; }
    ul { margin: 4px 0; padding-left: 18px; }
    .meta { float: right; color: #777; font-size: 10px; }
    .footer { margin-top: 30px; border-top: 1px solid #ddd; padding-top: 8px; font-size: 9px; color: #777; display: flex; justify-content: space-between; }
  </style>
</head>
<body>
  ${resumeHTML}
</body>
</html>`;

    const blob = new Blob([fullDocument], { type: 'text/html;charset=utf-8' });
    const link = document.createElement('a');
    link.href = URL.createObjectURL(blob);
    link.download = `RRGBS-Resume-${(resumeData.name || 'Candidate').replace(/\s+/g, '-')}.html`;
    link.click();
    onShowToast('Resume downloaded as standalone HTML file!', 'success');
  };

  const parsedSkills = (resumeData.skills || '')
    .split(',')
    .map((s) => s.trim())
    .filter(Boolean);

  const contactItems = [
    resumeData.phone,
    resumeData.email,
    resumeData.location,
    resumeData.linkedin,
  ].filter(Boolean);

  return (
    <div className="min-h-screen bg-[#f5f6f8] text-[#17191d] flex flex-col font-sans selection:bg-[#c91f2b] selection:text-white print:bg-white print:p-0">
      {/* -------------------- 1. TOP BAR -------------------- */}
      <header className="bg-[#111111] text-white py-3 px-4 sm:px-8 border-b border-gray-800 print:hidden">
        <div className="max-w-[1450px] mx-auto flex flex-col sm:flex-row items-center justify-between gap-3">
          <div className="flex items-center gap-3">
            <RRGBSLogo size={36} />
            <div>
              <div className="font-black text-lg tracking-tight flex items-center gap-2">
                RR<span className="text-[#c91f2b]">GBS</span>
                <span className="text-[10px] bg-[#c91f2b] text-white px-2 py-0.5 rounded font-extrabold uppercase tracking-wider">
                  AI RESUME BUILDER
                </span>
              </div>
              <small className="text-[9px] text-gray-400 block tracking-wider uppercase">
                RR Group of Business Solutions
              </small>
            </div>
          </div>

          {/* Portal Switcher */}
          <div className="flex items-center gap-2 flex-wrap">
            <span className="text-xs text-gray-400 hidden lg:inline mr-2">
              Pan-India Career Ecosystem:
            </span>
            <div className="flex items-center bg-gray-800/80 p-0.5 rounded-lg border border-gray-700 text-xs font-bold">
              <button
                onClick={() => onSwitchPortal('jobs')}
                className="px-2.5 py-1 rounded text-gray-300 hover:text-white transition-all cursor-pointer flex items-center gap-1.5"
                title="Browse Job Openings"
              >
                <Briefcase className="w-3.5 h-3.5" />
                <span>Jobs</span>
              </button>
              <button
                className="px-2.5 py-1 rounded bg-[#c91f2b] text-white transition-all cursor-default flex items-center gap-1.5 shadow-xs"
                title="AI Resume Builder"
              >
                <FileText className="w-3.5 h-3.5" />
                <span>Resume Builder</span>
              </button>
              <button
                onClick={() => onSwitchPortal('services')}
                className="px-2.5 py-1 rounded text-gray-300 hover:text-white transition-all cursor-pointer flex items-center gap-1.5"
                title="Corporate & Staffing Services"
              >
                <Sparkles className="w-3.5 h-3.5" />
                <span>Services</span>
              </button>
              <button
                onClick={() => onSwitchPortal('store')}
                className="px-2.5 py-1 rounded text-gray-300 hover:text-white transition-all cursor-pointer flex items-center gap-1.5"
                title="Business Store"
              >
                <ShoppingBag className="w-3.5 h-3.5" />
                <span>Store</span>
              </button>
              <button
                onClick={() => onSwitchPortal('home')}
                className="px-2.5 py-1 rounded text-gray-300 hover:text-white transition-all cursor-pointer flex items-center gap-1.5"
                title="Doorstep Home Services"
              >
                <Home className="w-3.5 h-3.5" />
                <span>Home</span>
              </button>
            </div>
          </div>
        </div>
      </header>

      {/* -------------------- 2. HERO BANNER -------------------- */}
      <section className="bg-gradient-to-r from-[#111111] via-[#251417] to-[#8d111b] text-white py-12 px-4 sm:px-8 border-b border-gray-900 print:hidden relative overflow-hidden">
        <div className="absolute right-0 top-0 w-96 h-96 bg-[#c91f2b]/15 rounded-full blur-3xl pointer-events-none" />
        <div className="max-w-[1450px] mx-auto relative z-10">
          <div className="text-[#ff9da4] text-xs font-bold tracking-[2px] uppercase mb-2">
            AI Powered Career Tool • RRGBS Workforce Solutions
          </div>
          <h1 className="text-3xl sm:text-4xl lg:text-5xl font-black leading-tight tracking-tight mb-3">
            Build Your Professional Resume with RRGBS
          </h1>
          <p className="text-gray-200 text-sm sm:text-base max-w-[800px] leading-relaxed mb-6">
            Create a professional, ATS-friendly resume with AI-style content assistance. Live
            preview your resume, run automated ATS compatibility checks, and print or export your
            verified resume directly as PDF.
          </p>

          <div className="flex flex-wrap gap-2.5 text-xs">
            {[
              '✨ AI Resume Assistant',
              '🎯 ATS Ready',
              '📄 A4 Standard Print/PDF',
              '🏆 Official RRGBS Verification',
              '🎨 4 Professional Templates',
            ].map((badge, idx) => (
              <span
                key={idx}
                className="border border-white/20 bg-white/10 backdrop-blur-xs px-3.5 py-1.5 rounded-full font-semibold text-gray-100"
              >
                {badge}
              </span>
            ))}
          </div>
        </div>
      </section>

      {/* -------------------- 3. MAIN APP (BUILDER & PREVIEW) -------------------- */}
      <main className="max-w-[1450px] w-full mx-auto my-7 px-4 sm:px-6 flex-1 print:m-0 print:p-0 print:max-w-none">
        <div className="grid grid-cols-1 lg:grid-cols-[minmax(0,1fr)_minmax(450px,640px)] gap-6 items-start print:block">
          {/* ==================== LEFT COLUMN: BUILDER PANEL ==================== */}
          <section className="bg-white border border-[#e1e4e8] rounded-2xl overflow-hidden shadow-sm print:hidden">
            {/* Header */}
            <div className="px-5 py-4 border-b border-[#e1e4e8] flex items-center justify-between gap-3 bg-gray-50/50">
              <div className="flex items-center gap-2">
                <FileText className="w-5 h-5 text-[#c91f2b]" />
                <h2 className="font-black text-lg text-gray-900 tracking-tight">
                  RRGBS Resume Builder
                </h2>
              </div>
              <div className="flex items-center gap-2">
                <button
                  onClick={handleLoadDemo}
                  className="bg-gray-100 hover:bg-gray-200 text-gray-800 text-xs font-bold px-3 py-1.5 rounded-lg transition-colors cursor-pointer"
                >
                  Load Demo Profile
                </button>
                <button
                  onClick={handleReset}
                  className="bg-red-50 hover:bg-red-100 text-[#c91f2b] text-xs font-bold px-2.5 py-1.5 rounded-lg transition-colors cursor-pointer flex items-center gap-1"
                  title="Clear form"
                >
                  <RotateCcw className="w-3.5 h-3.5" />
                  <span>Reset</span>
                </button>
              </div>
            </div>

            {/* Quick Step Indicators */}
            <div className="flex gap-2 overflow-x-auto p-4 border-b border-gray-100 bg-white">
              {[
                { step: 1, label: 'Profile' },
                { step: 2, label: 'Experience' },
                { step: 3, label: 'Education' },
                { step: 4, label: 'Skills' },
                { step: 5, label: 'Template' },
              ].map((s) => (
                <button
                  key={s.step}
                  onClick={() => setActiveStep(s.step)}
                  className={`whitespace-nowrap px-3.5 py-1.5 rounded-full text-xs font-bold transition-all cursor-pointer ${
                    activeStep === s.step
                      ? 'bg-[#c91f2b] text-white shadow-xs'
                      : 'bg-gray-100 text-gray-600 hover:bg-gray-200'
                  }`}
                >
                  {s.step} {s.label}
                </button>
              ))}
            </div>

            {/* Form Fields Body */}
            <div className="p-5 sm:p-6 space-y-6">
              {/* STEP 1: PERSONAL INFO */}
              <div>
                <div className="text-xs font-extrabold uppercase tracking-wider text-gray-900 border-b border-gray-200 pb-2 mb-4 flex items-center gap-2">
                  <User className="w-4 h-4 text-[#c91f2b]" />
                  <span>1. Personal Information</span>
                </div>

                <div className="grid grid-cols-1 sm:grid-cols-2 gap-3.5">
                  <div>
                    <label className="block text-xs font-bold text-gray-700 mb-1">
                      Full Name *
                    </label>
                    <input
                      type="text"
                      value={resumeData.name}
                      onChange={(e) => handleChange('name', e.target.value)}
                      placeholder="e.g. Rahul Kumar"
                      className="w-full p-2.5 text-sm border border-gray-300 focus:border-[#c91f2b] focus:ring-2 focus:ring-[#c91f2b]/15 rounded-lg outline-none"
                    />
                  </div>

                  <div>
                    <label className="block text-xs font-bold text-gray-700 mb-1">
                      Professional Title *
                    </label>
                    <input
                      type="text"
                      value={resumeData.role}
                      onChange={(e) => handleChange('role', e.target.value)}
                      placeholder="e.g. HR Executive / Full Stack Developer"
                      className="w-full p-2.5 text-sm border border-gray-300 focus:border-[#c91f2b] focus:ring-2 focus:ring-[#c91f2b]/15 rounded-lg outline-none"
                    />
                  </div>

                  <div>
                    <label className="block text-xs font-bold text-gray-700 mb-1">
                      Phone Number *
                    </label>
                    <input
                      type="tel"
                      value={resumeData.phone}
                      onChange={(e) => handleChange('phone', e.target.value)}
                      placeholder="+91 98765 43210"
                      className="w-full p-2.5 text-sm border border-gray-300 focus:border-[#c91f2b] focus:ring-2 focus:ring-[#c91f2b]/15 rounded-lg outline-none"
                    />
                  </div>

                  <div>
                    <label className="block text-xs font-bold text-gray-700 mb-1">
                      Email Address *
                    </label>
                    <input
                      type="email"
                      value={resumeData.email}
                      onChange={(e) => handleChange('email', e.target.value)}
                      placeholder="rahul@example.com"
                      className="w-full p-2.5 text-sm border border-gray-300 focus:border-[#c91f2b] focus:ring-2 focus:ring-[#c91f2b]/15 rounded-lg outline-none"
                    />
                  </div>

                  <div>
                    <label className="block text-xs font-bold text-gray-700 mb-1">Location</label>
                    <input
                      type="text"
                      value={resumeData.location}
                      onChange={(e) => handleChange('location', e.target.value)}
                      placeholder="Bengaluru, Karnataka"
                      className="w-full p-2.5 text-sm border border-gray-300 focus:border-[#c91f2b] focus:ring-2 focus:ring-[#c91f2b]/15 rounded-lg outline-none"
                    />
                  </div>

                  <div>
                    <label className="block text-xs font-bold text-gray-700 mb-1">
                      LinkedIn / Portfolio
                    </label>
                    <input
                      type="text"
                      value={resumeData.linkedin}
                      onChange={(e) => handleChange('linkedin', e.target.value)}
                      placeholder="linkedin.com/in/yourname"
                      className="w-full p-2.5 text-sm border border-gray-300 focus:border-[#c91f2b] focus:ring-2 focus:ring-[#c91f2b]/15 rounded-lg outline-none"
                    />
                  </div>
                </div>
              </div>

              {/* PROFESSIONAL SUMMARY & AI TOOLS */}
              <div>
                <div className="flex items-center justify-between border-b border-gray-200 pb-2 mb-3">
                  <span className="text-xs font-extrabold uppercase tracking-wider text-gray-900">
                    Professional Summary
                  </span>
                  <div className="flex items-center gap-2">
                    <button
                      onClick={handleGenerateSummary}
                      className="text-xs font-bold text-gray-700 hover:text-[#c91f2b] bg-gray-100 hover:bg-gray-200 px-2.5 py-1 rounded transition-colors cursor-pointer"
                    >
                      Generate Draft
                    </button>
                    <button
                      onClick={handleAiImproveSummary}
                      className="text-xs font-bold text-white bg-[#c91f2b] hover:bg-[#a51520] px-3 py-1 rounded transition-colors cursor-pointer flex items-center gap-1 shadow-xs"
                    >
                      <Sparkles className="w-3.5 h-3.5" />
                      <span>AI Improve</span>
                    </button>
                  </div>
                </div>

                <textarea
                  rows={3}
                  value={resumeData.summary}
                  onChange={(e) => handleChange('summary', e.target.value)}
                  placeholder="Write your professional summary or click 'Generate Draft' for an AI-suggested profile introduction..."
                  className="w-full p-2.5 text-sm border border-gray-300 focus:border-[#c91f2b] focus:ring-2 focus:ring-[#c91f2b]/15 rounded-lg outline-none resize-vertical"
                />
              </div>

              {/* STEP 2: EXPERIENCE */}
              <div>
                <div className="flex items-center justify-between border-b border-gray-200 pb-2 mb-3">
                  <div className="text-xs font-extrabold uppercase tracking-wider text-gray-900 flex items-center gap-2">
                    <Briefcase className="w-4 h-4 text-[#c91f2b]" />
                    <span>2. Work Experience</span>
                  </div>
                  <button
                    onClick={handleAiImproveExperience}
                    className="text-xs font-bold text-white bg-[#c91f2b] hover:bg-[#a51520] px-3 py-1 rounded transition-colors cursor-pointer flex items-center gap-1 shadow-xs"
                  >
                    <Sparkles className="w-3.5 h-3.5" />
                    <span>AI Enhance Experience</span>
                  </button>
                </div>

                <div className="grid grid-cols-1 sm:grid-cols-2 gap-3.5 mb-3">
                  <div>
                    <label className="block text-xs font-bold text-gray-700 mb-1">Company</label>
                    <input
                      type="text"
                      value={resumeData.company}
                      onChange={(e) => handleChange('company', e.target.value)}
                      placeholder="e.g. RRGBS / Infosys"
                      className="w-full p-2.5 text-sm border border-gray-300 focus:border-[#c91f2b] focus:ring-2 focus:ring-[#c91f2b]/15 rounded-lg outline-none"
                    />
                  </div>

                  <div>
                    <label className="block text-xs font-bold text-gray-700 mb-1">Job Title</label>
                    <input
                      type="text"
                      value={resumeData.jobtitle}
                      onChange={(e) => handleChange('jobtitle', e.target.value)}
                      placeholder="e.g. Senior Recruiter"
                      className="w-full p-2.5 text-sm border border-gray-300 focus:border-[#c91f2b] focus:ring-2 focus:ring-[#c91f2b]/15 rounded-lg outline-none"
                    />
                  </div>

                  <div>
                    <label className="block text-xs font-bold text-gray-700 mb-1">
                      Employment Dates
                    </label>
                    <input
                      type="text"
                      value={resumeData.dates}
                      onChange={(e) => handleChange('dates', e.target.value)}
                      placeholder="e.g. 2023 – Present"
                      className="w-full p-2.5 text-sm border border-gray-300 focus:border-[#c91f2b] focus:ring-2 focus:ring-[#c91f2b]/15 rounded-lg outline-none"
                    />
                  </div>

                  <div>
                    <label className="block text-xs font-bold text-gray-700 mb-1">Location</label>
                    <input
                      type="text"
                      value={resumeData.joblocation}
                      onChange={(e) => handleChange('joblocation', e.target.value)}
                      placeholder="e.g. Bengaluru, India"
                      className="w-full p-2.5 text-sm border border-gray-300 focus:border-[#c91f2b] focus:ring-2 focus:ring-[#c91f2b]/15 rounded-lg outline-none"
                    />
                  </div>
                </div>

                <div>
                  <label className="block text-xs font-bold text-gray-700 mb-1">
                    Responsibilities &amp; Achievements
                  </label>
                  <textarea
                    rows={3}
                    value={resumeData.experience}
                    onChange={(e) => handleChange('experience', e.target.value)}
                    placeholder="Describe your key deliverables, metrics, team responsibilities, and major achievements..."
                    className="w-full p-2.5 text-sm border border-gray-300 focus:border-[#c91f2b] focus:ring-2 focus:ring-[#c91f2b]/15 rounded-lg outline-none resize-vertical"
                  />
                </div>
              </div>

              {/* STEP 3: EDUCATION */}
              <div>
                <div className="text-xs font-extrabold uppercase tracking-wider text-gray-900 border-b border-gray-200 pb-2 mb-3 flex items-center gap-2">
                  <GraduationCap className="w-4 h-4 text-[#c91f2b]" />
                  <span>3. Education</span>
                </div>

                <div className="grid grid-cols-1 sm:grid-cols-2 gap-3.5">
                  <div>
                    <label className="block text-xs font-bold text-gray-700 mb-1">
                      Degree / Qualification
                    </label>
                    <input
                      type="text"
                      value={resumeData.education}
                      onChange={(e) => handleChange('education', e.target.value)}
                      placeholder="e.g. MBA / B.Com / B.E."
                      className="w-full p-2.5 text-sm border border-gray-300 focus:border-[#c91f2b] focus:ring-2 focus:ring-[#c91f2b]/15 rounded-lg outline-none"
                    />
                  </div>

                  <div>
                    <label className="block text-xs font-bold text-gray-700 mb-1">
                      Institute / University
                    </label>
                    <input
                      type="text"
                      value={resumeData.institute}
                      onChange={(e) => handleChange('institute', e.target.value)}
                      placeholder="e.g. Bangalore University"
                      className="w-full p-2.5 text-sm border border-gray-300 focus:border-[#c91f2b] focus:ring-2 focus:ring-[#c91f2b]/15 rounded-lg outline-none"
                    />
                  </div>

                  <div>
                    <label className="block text-xs font-bold text-gray-700 mb-1">
                      Graduation Year
                    </label>
                    <input
                      type="text"
                      value={resumeData.eduyear}
                      onChange={(e) => handleChange('eduyear', e.target.value)}
                      placeholder="e.g. 2024"
                      className="w-full p-2.5 text-sm border border-gray-300 focus:border-[#c91f2b] focus:ring-2 focus:ring-[#c91f2b]/15 rounded-lg outline-none"
                    />
                  </div>

                  <div>
                    <label className="block text-xs font-bold text-gray-700 mb-1">
                      Percentage / CGPA
                    </label>
                    <input
                      type="text"
                      value={resumeData.grade}
                      onChange={(e) => handleChange('grade', e.target.value)}
                      placeholder="e.g. 78% or 8.2 CGPA"
                      className="w-full p-2.5 text-sm border border-gray-300 focus:border-[#c91f2b] focus:ring-2 focus:ring-[#c91f2b]/15 rounded-lg outline-none"
                    />
                  </div>
                </div>
              </div>

              {/* STEP 4: SKILLS & TARGET */}
              <div>
                <div className="text-xs font-extrabold uppercase tracking-wider text-gray-900 border-b border-gray-200 pb-2 mb-3 flex items-center gap-2">
                  <Award className="w-4 h-4 text-[#c91f2b]" />
                  <span>4. Core Competencies &amp; Skills</span>
                </div>

                <div className="mb-3">
                  <label className="block text-xs font-bold text-gray-700 mb-1">
                    Skills (separated by commas)
                  </label>
                  <input
                    type="text"
                    value={resumeData.skills}
                    onChange={(e) => handleChange('skills', e.target.value)}
                    placeholder="Recruitment, Talent Sourcing, Interviewing, Payroll, MS Excel, Stakeholder Coordination"
                    className="w-full p-2.5 text-sm border border-gray-300 focus:border-[#c91f2b] focus:ring-2 focus:ring-[#c91f2b]/15 rounded-lg outline-none"
                  />
                  <p className="text-[11px] text-gray-500 mt-1">
                    Tip: Separate skills with commas. These are automatically extracted and matched by ATS parsers.
                  </p>
                </div>

                <div className="grid grid-cols-1 sm:grid-cols-2 gap-3.5">
                  <div>
                    <label className="block text-xs font-bold text-gray-700 mb-1">
                      Languages Known
                    </label>
                    <input
                      type="text"
                      value={resumeData.languages}
                      onChange={(e) => handleChange('languages', e.target.value)}
                      placeholder="e.g. English, Kannada, Hindi"
                      className="w-full p-2.5 text-sm border border-gray-300 focus:border-[#c91f2b] focus:ring-2 focus:ring-[#c91f2b]/15 rounded-lg outline-none"
                    />
                  </div>

                  <div>
                    <label className="block text-xs font-bold text-gray-700 mb-1">Target Job Title</label>
                    <input
                      type="text"
                      value={resumeData.target}
                      onChange={(e) => handleChange('target', e.target.value)}
                      placeholder="e.g. Senior Recruiter / HR Partner"
                      className="w-full p-2.5 text-sm border border-gray-300 focus:border-[#c91f2b] focus:ring-2 focus:ring-[#c91f2b]/15 rounded-lg outline-none"
                    />
                  </div>
                </div>
              </div>

              {/* PROJECTS & CERTIFICATIONS */}
              <div>
                <div className="text-xs font-extrabold uppercase tracking-wider text-gray-900 border-b border-gray-200 pb-2 mb-3">
                  Projects &amp; Certifications
                </div>

                <div className="grid grid-cols-1 sm:grid-cols-2 gap-3.5">
                  <div>
                    <label className="block text-xs font-bold text-gray-700 mb-1">Projects</label>
                    <textarea
                      rows={2}
                      value={resumeData.projects}
                      onChange={(e) => handleChange('projects', e.target.value)}
                      placeholder="Project name — Brief scope and result"
                      className="w-full p-2.5 text-sm border border-gray-300 focus:border-[#c91f2b] focus:ring-2 focus:ring-[#c91f2b]/15 rounded-lg outline-none resize-vertical"
                    />
                  </div>

                  <div>
                    <label className="block text-xs font-bold text-gray-700 mb-1">
                      Certifications
                    </label>
                    <textarea
                      rows={2}
                      value={resumeData.certifications}
                      onChange={(e) => handleChange('certifications', e.target.value)}
                      placeholder="Certification name — Issuing body"
                      className="w-full p-2.5 text-sm border border-gray-300 focus:border-[#c91f2b] focus:ring-2 focus:ring-[#c91f2b]/15 rounded-lg outline-none resize-vertical"
                    />
                  </div>
                </div>
              </div>

              {/* STEP 5: TEMPLATES */}
              <div>
                <div className="text-xs font-extrabold uppercase tracking-wider text-gray-900 border-b border-gray-200 pb-2 mb-3 flex items-center gap-2">
                  <Layers className="w-4 h-4 text-[#c91f2b]" />
                  <span>5. Choose Template Format</span>
                </div>

                <div className="grid grid-cols-2 sm:grid-cols-4 gap-3">
                  {[
                    {
                      name: 'Classic',
                      miniStyle:
                        'bg-gradient-to-b from-[#111] via-[#eee] via-25% to-white',
                    },
                    {
                      name: 'Modern',
                      miniStyle:
                        'bg-gradient-to-r from-[#c91f2b] from-25% to-white to-25%',
                    },
                    {
                      name: 'Executive',
                      miniStyle:
                        'bg-gradient-to-b from-[#c91f2b] from-30% to-white to-30%',
                    },
                    {
                      name: 'ATS Clean',
                      miniStyle:
                        'bg-gradient-to-r from-[#111] from-30% via-[#eee] to-white',
                    },
                  ].map((tpl) => (
                    <button
                      key={tpl.name}
                      onClick={() => {
                        setActiveTemplate(tpl.name as TemplateType);
                        onShowToast(`${tpl.name} template selected!`, 'info');
                      }}
                      className={`p-3 rounded-xl border-2 text-left transition-all cursor-pointer ${
                        activeTemplate === tpl.name
                          ? 'border-[#c91f2b] bg-red-50/40 shadow-xs'
                          : 'border-gray-200 bg-white hover:border-gray-300'
                      }`}
                    >
                      <div className={`h-10 rounded mb-2 border border-gray-200 ${tpl.miniStyle}`} />
                      <div className="text-xs font-black text-gray-900">{tpl.name}</div>
                    </button>
                  ))}
                </div>
              </div>

              {/* FINAL ACTIONS TOOLBAR */}
              <div className="pt-4 border-t border-gray-200">
                <div className="text-xs font-bold text-gray-500 mb-2">Final Actions</div>
                <div className="flex flex-wrap items-center gap-2.5">
                  <button
                    onClick={() => {
                      calculateAts(resumeData);
                      onShowToast('Resume updated and formatted!', 'success');
                    }}
                    className="bg-[#c91f2b] hover:bg-[#a51520] text-white px-4 py-2.5 rounded-lg font-bold text-xs flex items-center gap-1.5 transition-all shadow-sm cursor-pointer"
                  >
                    <Sparkles className="w-4 h-4" />
                    <span>Update Live Preview</span>
                  </button>

                  <button
                    onClick={handlePrint}
                    className="bg-[#111111] hover:bg-black text-white px-4 py-2.5 rounded-lg font-bold text-xs flex items-center gap-1.5 transition-all shadow-sm cursor-pointer"
                  >
                    <Printer className="w-4 h-4" />
                    <span>Print / Save as PDF</span>
                  </button>

                  <button
                    onClick={handleDownloadHTML}
                    className="bg-[#198754] hover:bg-[#146c43] text-white px-4 py-2.5 rounded-lg font-bold text-xs flex items-center gap-1.5 transition-all shadow-sm cursor-pointer"
                  >
                    <Download className="w-4 h-4" />
                    <span>Download HTML</span>
                  </button>
                </div>
                <p className="text-[11px] text-gray-500 mt-2">
                  To save as PDF: Click <b>Print / Save as PDF</b> and choose <b>Save as PDF</b> as
                  the destination in your print dialog.
                </p>
              </div>
            </div>
          </section>

          {/* ==================== RIGHT COLUMN: LIVE RESUME PREVIEW & ATS SCORE ==================== */}
          <section className="bg-white border border-[#e1e4e8] rounded-2xl overflow-hidden shadow-sm flex flex-col sticky top-4 print:static print:border-none print:shadow-none">
            {/* Live Preview Header */}
            <div className="px-5 py-4 border-b border-[#e1e4e8] flex items-center justify-between gap-3 bg-gray-50/50 print:hidden">
              <div className="flex items-center gap-2">
                <div className="w-2.5 h-2.5 rounded-full bg-emerald-500 animate-pulse" />
                <h2 className="font-black text-base text-gray-900 tracking-tight">
                  Live Resume Preview
                </h2>
                <span className="text-[10px] bg-gray-200 text-gray-700 px-2 py-0.5 rounded-full font-bold uppercase">
                  {activeTemplate}
                </span>
              </div>

              <button
                onClick={handleAtsCheck}
                className="border border-gray-300 hover:border-[#c91f2b] text-gray-800 hover:text-[#c91f2b] text-xs font-bold px-3 py-1.5 rounded-lg transition-colors cursor-pointer"
              >
                Run ATS Check
              </button>
            </div>

            {/* ATS Score Card */}
            <div className="p-4 border-b border-gray-200 bg-gray-50/40 print:hidden">
              <div className="flex items-center gap-3.5 bg-white border border-gray-200 rounded-xl p-3 shadow-xs">
                {/* Conic Ring Gauge */}
                <div
                  className="w-13 h-13 rounded-full flex items-center justify-center relative shrink-0"
                  style={{
                    background: `conic-gradient(#198754 0% ${atsScore}%, #e2e8f0 ${atsScore}% 100%)`,
                  }}
                >
                  <div className="absolute inset-1.5 bg-white rounded-full flex items-center justify-center">
                    <span className="text-xs font-black text-gray-900">{atsScore}%</span>
                  </div>
                </div>

                <div className="flex-1 min-w-0">
                  <div className="flex items-center gap-2">
                    <strong className="text-xs font-bold text-gray-900">
                      ATS Match Compatibility
                    </strong>
                    <span
                      className={`text-[10px] px-1.5 py-0.5 rounded font-extrabold ${
                        atsScore >= 80
                          ? 'bg-emerald-50 text-emerald-700'
                          : 'bg-amber-50 text-amber-700'
                      }`}
                    >
                      {atsScore >= 80 ? 'Optimal' : 'Needs Review'}
                    </span>
                  </div>
                  <p className="text-[11px] text-gray-600 line-clamp-2 mt-0.5 leading-tight">
                    {atsFeedback}
                  </p>
                </div>
              </div>
            </div>

            {/* Live Sheet Area (Scaled A4) */}
            <div className="p-4 sm:p-6 bg-[#dfe2e6] overflow-x-auto min-h-[750px] flex justify-center print:p-0 print:bg-white print:min-h-0">
              {/* Actual A4 Sheet Box */}
              <article
                ref={resumeRef}
                className={`w-[794px] min-h-[1050px] bg-white text-[#17191d] p-10 sm:p-12 relative shadow-md text-left print:shadow-none print:p-8 print:w-full print:m-0 font-sans ${
                  activeTemplate === 'Modern'
                    ? 'border-l-8 border-[#c91f2b]'
                    : activeTemplate === 'Executive'
                    ? 'border-t-8 border-[#111111]'
                    : ''
                }`}
              >
                {/* TEMPLATE: EXECUTIVE HEADER BANNER */}
                {activeTemplate === 'Executive' && (
                  <div className="bg-[#111111] text-white -mx-10 -mt-10 sm:-mx-12 sm:-mt-12 p-8 mb-6">
                    <h1 className="text-2xl sm:text-3xl font-black tracking-tight text-white mb-1">
                      {resumeData.name || 'Your Full Name'}
                    </h1>
                    <div className="text-sm font-bold text-[#ff6670] uppercase tracking-wide">
                      {resumeData.role || 'Professional Title'}
                    </div>
                    <div className="text-[10px] text-gray-300 mt-2 flex flex-wrap gap-x-3 gap-y-1">
                      {contactItems.map((item, idx) => (
                        <span key={idx}>
                          {idx > 0 && <span className="text-gray-500 mr-2">•</span>}
                          {item}
                        </span>
                      ))}
                    </div>
                  </div>
                )}

                {/* TEMPLATE: CLASSIC, MODERN, ATS CLEAN HEADER */}
                {activeTemplate !== 'Executive' && (
                  <header
                    className={`pb-4 mb-4 ${
                      activeTemplate === 'Classic'
                        ? 'border-b-3 border-[#c91f2b]'
                        : activeTemplate === 'Modern'
                        ? 'border-b-2 border-gray-800'
                        : 'border-b border-gray-300'
                    }`}
                  >
                    <h1 className="text-2xl sm:text-3xl font-black text-gray-900 tracking-tight mb-1">
                      {resumeData.name || 'Your Full Name'}
                    </h1>
                    <div
                      className={`text-sm font-bold tracking-wide ${
                        activeTemplate === 'ATS Clean' ? 'text-gray-800' : 'text-[#c91f2b]'
                      }`}
                    >
                      {resumeData.role || 'Professional Title'}
                    </div>

                    <div className="text-[10px] text-gray-600 mt-2 flex flex-wrap gap-x-3 gap-y-1">
                      {contactItems.length > 0
                        ? contactItems.map((c, i) => (
                            <span key={i}>
                              {i > 0 && <span className="text-gray-400 mr-2">•</span>}
                              {c}
                            </span>
                          ))
                        : 'Phone • Email • Location • LinkedIn'}
                    </div>
                  </header>
                )}

                {/* 1. PROFESSIONAL SUMMARY */}
                <section className="mb-5">
                  <h3
                    className={`text-[11px] font-black tracking-wider uppercase pb-1 mb-2 ${
                      activeTemplate === 'ATS Clean'
                        ? 'text-gray-900 border-b border-gray-400'
                        : 'text-[#c91f2b] border-b border-gray-200'
                    }`}
                  >
                    Professional Summary
                  </h3>
                  <p className="text-[11px] text-gray-700 leading-relaxed">
                    {resumeData.summary ||
                      'Your professional summary will appear here. Add details about your core focus, strengths, and background.'}
                  </p>
                </section>

                {/* 2. WORK EXPERIENCE */}
                <section className="mb-5">
                  <h3
                    className={`text-[11px] font-black tracking-wider uppercase pb-1 mb-2.5 ${
                      activeTemplate === 'ATS Clean'
                        ? 'text-gray-900 border-b border-gray-400'
                        : 'text-[#c91f2b] border-b border-gray-200'
                    }`}
                  >
                    Work Experience
                  </h3>

                  <div className="mb-3">
                    <div className="flex items-center justify-between">
                      <b className="text-[11.5px] font-bold text-gray-900">
                        {resumeData.company || 'Company Name'}
                      </b>
                      <span className="text-[10px] text-gray-500 font-semibold">
                        {resumeData.dates || '2023 – Present'}
                      </span>
                    </div>

                    <div className="flex items-center justify-between text-[10.5px] text-gray-700 mt-0.5 font-medium">
                      <span>{resumeData.jobtitle || 'Job Title'}</span>
                      {resumeData.joblocation && (
                        <span className="text-gray-500 text-[9.5px]">
                          {resumeData.joblocation}
                        </span>
                      )}
                    </div>

                    <p className="text-[10.5px] text-gray-600 leading-relaxed mt-1.5 whitespace-pre-line">
                      {resumeData.experience ||
                        'Your responsibilities and measurable achievements will appear here.'}
                    </p>
                  </div>
                </section>

                {/* 3. EDUCATION */}
                <section className="mb-5">
                  <h3
                    className={`text-[11px] font-black tracking-wider uppercase pb-1 mb-2.5 ${
                      activeTemplate === 'ATS Clean'
                        ? 'text-gray-900 border-b border-gray-400'
                        : 'text-[#c91f2b] border-b border-gray-200'
                    }`}
                  >
                    Education
                  </h3>

                  <div>
                    <div className="flex items-center justify-between">
                      <b className="text-[11.5px] font-bold text-gray-900">
                        {resumeData.education || 'Qualification / Degree'}
                      </b>
                      <span className="text-[10px] text-gray-500 font-semibold">
                        {resumeData.eduyear || 'Year'}
                      </span>
                    </div>

                    <div className="text-[10.5px] text-gray-700 mt-0.5">
                      <span>{resumeData.institute || 'College / University'}</span>
                      {resumeData.grade && (
                        <span className="font-semibold text-gray-900">
                          {' '}
                          • {resumeData.grade}
                        </span>
                      )}
                    </div>
                  </div>
                </section>

                {/* 4. SKILLS & COMPETENCIES */}
                <section className="mb-5">
                  <h3
                    className={`text-[11px] font-black tracking-wider uppercase pb-1 mb-2 ${
                      activeTemplate === 'ATS Clean'
                        ? 'text-gray-900 border-b border-gray-400'
                        : 'text-[#c91f2b] border-b border-gray-200'
                    }`}
                  >
                    Skills &amp; Competencies
                  </h3>

                  {parsedSkills.length > 0 ? (
                    <div className="flex flex-wrap gap-1.5">
                      {parsedSkills.map((skill, idx) => (
                        <span
                          key={idx}
                          className="bg-gray-100 text-gray-800 text-[10px] font-semibold px-2.5 py-0.5 rounded border border-gray-200/80"
                        >
                          {skill}
                        </span>
                      ))}
                    </div>
                  ) : (
                    <p className="text-[10.5px] text-gray-500 italic">
                      Key competencies and skills will appear here.
                    </p>
                  )}
                </section>

                {/* 5. PROJECTS (OPTIONAL) */}
                {resumeData.projects && (
                  <section className="mb-5">
                    <h3
                      className={`text-[11px] font-black tracking-wider uppercase pb-1 mb-2 ${
                        activeTemplate === 'ATS Clean'
                          ? 'text-gray-900 border-b border-gray-400'
                          : 'text-[#c91f2b] border-b border-gray-200'
                      }`}
                    >
                      Key Projects
                    </h3>
                    <p className="text-[10.5px] text-gray-700 leading-relaxed whitespace-pre-line">
                      {resumeData.projects}
                    </p>
                  </section>
                )}

                {/* 6. CERTIFICATIONS (OPTIONAL) */}
                {resumeData.certifications && (
                  <section className="mb-5">
                    <h3
                      className={`text-[11px] font-black tracking-wider uppercase pb-1 mb-2 ${
                        activeTemplate === 'ATS Clean'
                          ? 'text-gray-900 border-b border-gray-400'
                          : 'text-[#c91f2b] border-b border-gray-200'
                      }`}
                    >
                      Certifications
                    </h3>
                    <p className="text-[10.5px] text-gray-700 leading-relaxed whitespace-pre-line">
                      {resumeData.certifications}
                    </p>
                  </section>
                )}

                {/* 7. LANGUAGES & TARGET (OPTIONAL) */}
                {resumeData.languages && (
                  <section className="mb-5">
                    <h3
                      className={`text-[11px] font-black tracking-wider uppercase pb-1 mb-2 ${
                        activeTemplate === 'ATS Clean'
                          ? 'text-gray-900 border-b border-gray-400'
                          : 'text-[#c91f2b] border-b border-gray-200'
                      }`}
                    >
                      Languages
                    </h3>
                    <p className="text-[10.5px] text-gray-700">{resumeData.languages}</p>
                  </section>
                )}

                {/* RESUME FOOTER WATERMARK */}
                <footer className="mt-8 pt-2.5 border-t border-gray-200 flex items-center justify-between text-[8px] text-gray-500 font-medium">
                  <span>
                    RRGBS AI Resume Builder • People. Process. Performance. • www.rrgroupofbusinesssolutions.in
                  </span>
                  <span className="font-mono">{resumeId}</span>
                </footer>
              </article>
            </div>
          </section>
        </div>
      </main>

      {/* -------------------- 4. FOOTER -------------------- */}
      <footer className="bg-[#111111] text-gray-400 py-8 px-4 sm:px-8 text-center text-xs border-t border-gray-900 mt-auto print:hidden">
        <div className="max-w-[1200px] mx-auto space-y-2">
          <p className="font-black text-white text-sm">
            RRGBS — RR Group of Business Solutions
          </p>
          <p className="text-gray-400">People. Process. Performance.</p>
          <p className="text-gray-500">
            Staffing • Recruitment • HR • Payroll • Business Solutions • Online Business Store • Home Services
          </p>
          <p className="text-[#c91f2b] font-medium pt-2">
            www.rrgroupofbusinesssolutions.in • +91 63635 65865
          </p>
        </div>
      </footer>
    </div>
  );
};
