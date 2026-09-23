import React, { useState } from 'react';
import {
  RRGBS_IT_STAFFING_PARTNERSHIP,
} from '../../data/servicesData';
import {
  Code2,
  Cloud,
  GitBranch,
  Database,
  Cpu,
  ShieldCheck,
  CheckCircle2,
  Server,
  ArrowRight,
  Phone,
  MessageCircle,
  Building2,
  Sparkles,
  Users,
  Check,
  ChevronRight,
  Briefcase,
  Layers,
  Laptop,
} from 'lucide-react';
import { RecruitmentService } from '../../types';

interface ITStaffingSectionProps {
  onOpenContact: (service?: RecruitmentService | null) => void;
}

export const ITStaffingSection: React.FC<ITStaffingSectionProps> = ({
  onOpenContact,
}) => {
  const [activeDomainIdx, setActiveDomainIdx] = useState(0);

  const itData = RRGBS_IT_STAFFING_PARTNERSHIP;

  // Icon mapping for technology domains
  const getDomainIcon = (index: number) => {
    switch (index) {
      case 0:
        return <Code2 className="w-5 h-5 text-[#c40000]" />;
      case 1:
        return <Cloud className="w-5 h-5 text-[#c40000]" />;
      case 2:
        return <GitBranch className="w-5 h-5 text-[#c40000]" />;
      case 3:
        return <Database className="w-5 h-5 text-[#c40000]" />;
      case 4:
        return <Cpu className="w-5 h-5 text-[#c40000]" />;
      case 5:
        return <ShieldCheck className="w-5 h-5 text-[#c40000]" />;
      case 6:
        return <CheckCircle2 className="w-5 h-5 text-[#c40000]" />;
      case 7:
        return <Server className="w-5 h-5 text-[#c40000]" />;
      default:
        return <Laptop className="w-5 h-5 text-[#c40000]" />;
    }
  };

  const handleWhatsAppInquiry = (techOrModel?: string) => {
    let msg = `Hello RRGBS IT Staffing Desk,\n\nI am inquiring about IT Staffing Solutions with your Associate Partner *Trinaara Technologies*.\n\n`;
    if (techOrModel) {
      msg += `Focus Area: ${techOrModel}\n`;
    }
    msg += `We are looking for flexible technology talent (permanent/contract/hybrid) for our organization. Please share your candidate profiles and commercial terms.`;

    const url = `https://wa.me/916363565865?text=${encodeURIComponent(msg)}`;
    window.open(url, '_blank');
  };

  return (
    <section id="it-staffing" className="py-20 bg-[#0d0f12] text-white relative overflow-hidden border-b border-gray-800">
      {/* Background ambient lighting */}
      <div className="absolute top-0 right-1/4 w-[500px] h-[500px] bg-[#c40000]/10 rounded-full blur-3xl pointer-events-none" />
      <div className="absolute bottom-0 left-10 w-[400px] h-[400px] bg-red-950/20 rounded-full blur-3xl pointer-events-none" />

      <div className="w-[92%] max-w-[1240px] mx-auto relative z-10">
        {/* ==================== 1. PARTNERSHIP BADGE & HEADER ==================== */}
        <div className="text-center max-w-[900px] mx-auto mb-14">
          <div className="inline-flex items-center gap-2 px-4 py-1.5 rounded-full bg-white/10 border border-white/20 text-xs sm:text-sm font-bold text-gray-200 mb-5 backdrop-blur-md">
            <span className="w-2.5 h-2.5 rounded-full bg-[#ff3b3b] animate-ping" />
            <span className="text-[#ff5252] font-black uppercase tracking-wider">Associate Partnership</span>
            <span className="text-gray-400">•</span>
            <span>Trinaara Technologies</span>
          </div>

          <h2 className="text-3xl sm:text-4xl lg:text-5xl font-black tracking-tight leading-tight mb-5">
            RRGBS IT Staffing Services
            <span className="block text-xl sm:text-2xl lg:text-3xl font-bold text-[#ff4d4d] mt-2">
              (In Association with Trinaara Technologies)
            </span>
          </h2>

          <p className="text-sm sm:text-base lg:text-lg text-gray-300 leading-relaxed font-normal mb-4">
            RRGBS IT is associated with <strong className="text-white font-semibold">Trinaara Technologies</strong> as an Associate Partner for Hybrid IT Staffing Solutions and Services. Together, we support businesses with flexible technology workforce solutions across India.
          </p>

          <p className="text-xs sm:text-sm text-gray-400 leading-relaxed max-w-3xl mx-auto">
            Our services include IT recruitment, permanent hiring, contract staffing, contract-to-hire, remote and hybrid staffing, project-based resources, bulk hiring, RPO, payroll and managed workforce solutions.
          </p>
        </div>

        {/* ==================== 2. THE 4 PILLARS OF EXCELLENCE ==================== */}
        <div className="grid grid-cols-2 md:grid-cols-4 gap-3 sm:gap-4 mb-16">
          {itData.pillars.map((pillar, idx) => (
            <div
              key={idx}
              className="bg-white/5 border border-white/10 hover:border-[#c40000]/60 rounded-xl p-4 sm:p-5 transition-all duration-200 group hover:-translate-y-1 hover:bg-white/[0.08]"
            >
              <div className="text-xs font-black text-[#ff4d4d] uppercase tracking-widest mb-1">
                Pillar 0{idx + 1}
              </div>
              <h3 className="text-lg sm:text-xl font-black text-white group-hover:text-[#ff4d4d] transition-colors mb-1.5">
                {pillar.word}
              </h3>
              <p className="text-xs text-gray-400 leading-relaxed">
                {pillar.description}
              </p>
            </div>
          ))}
        </div>

        {/* ==================== 3. ENGAGEMENT MODELS GRID ==================== */}
        <div className="mb-16">
          <div className="flex flex-col md:flex-row md:items-end justify-between gap-4 mb-8">
            <div>
              <div className="text-xs font-extrabold uppercase tracking-widest text-[#ff4d4d] mb-1">
                Flexible Hiring Models
              </div>
              <h3 className="text-2xl sm:text-3xl font-extrabold text-white">
                How We Deploy Technology Talent
              </h3>
            </div>
            <p className="text-xs sm:text-sm text-gray-400 max-w-md">
              From individual niche hires to dedicated engineering pods and managed workforce deployments across India.
            </p>
          </div>

          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4 sm:gap-6">
            {itData.engagementModels.map((model, idx) => (
              <div
                key={idx}
                className="bg-[#14181f] border border-gray-800 hover:border-[#c40000] rounded-2xl p-6 transition-all duration-200 flex flex-col justify-between group shadow-lg hover:shadow-[#c40000]/10"
              >
                <div>
                  <div className="flex items-center justify-between mb-4">
                    <div className="w-10 h-10 rounded-xl bg-[#c40000]/15 text-[#ff4d4d] flex items-center justify-center font-bold text-sm">
                      0{idx + 1}
                    </div>
                    {model.badge && (
                      <span className="text-[11px] font-bold px-2.5 py-1 rounded-full bg-white/10 text-gray-300 border border-white/10">
                        {model.badge}
                      </span>
                    )}
                  </div>

                  <h4 className="text-base sm:text-lg font-bold text-white mb-2 group-hover:text-[#ff4d4d] transition-colors">
                    {model.title}
                  </h4>
                  <p className="text-xs sm:text-sm text-gray-400 leading-relaxed mb-4">
                    {model.description}
                  </p>
                </div>

                <div className="pt-4 border-t border-gray-800/80 flex items-center justify-between">
                  <button
                    onClick={() => {
                      onOpenContact({
                        id: `it-staffing-${idx}`,
                        title: `IT Staffing - ${model.title} (Trinaara Partnership)`,
                        icon: '💻',
                        description: model.description,
                        features: [model.title, 'Trinaara Technologies Partnership', 'Hybrid / Remote / Onsite', 'End-to-End Compliance'],
                      });
                    }}
                    className="text-xs font-bold text-gray-300 hover:text-white flex items-center gap-1.5 cursor-pointer group/link"
                  >
                    <span>Request Details</span>
                    <ChevronRight className="w-3.5 h-3.5 text-[#ff4d4d] group-hover/link:translate-x-1 transition-transform" />
                  </button>

                  <button
                    onClick={() => handleWhatsAppInquiry(model.title)}
                    className="text-[11px] font-semibold text-emerald-400 hover:text-emerald-300 flex items-center gap-1 cursor-pointer"
                    title="Quick WhatsApp quote"
                  >
                    <MessageCircle className="w-3 h-3" />
                    <span>WhatsApp</span>
                  </button>
                </div>
              </div>
            ))}
          </div>
        </div>

        {/* ==================== 4. TECHNOLOGY TALENT COVERAGE ==================== */}
        <div className="mb-16 bg-[#14181f] border border-gray-800 rounded-2xl p-6 sm:p-8 lg:p-10 shadow-2xl">
          <div className="max-w-3xl mb-8">
            <div className="text-xs font-black uppercase tracking-widest text-[#ff4d4d] mb-1">
              Technology Stack Coverage
            </div>
            <h3 className="text-2xl sm:text-3xl font-extrabold text-white mb-2">
              Comprehensive Domain &amp; Skills Coverage
            </h3>
            <p className="text-xs sm:text-sm text-gray-400">
              Our technology talent coverage includes software development, cloud, DevOps, data, AI/ML, cybersecurity, testing, infrastructure, networking and IT support.
            </p>
          </div>

          {/* Domain tabs */}
          <div className="flex flex-wrap gap-2 mb-6 border-b border-gray-800 pb-4">
            {itData.techCoverage.map((item, idx) => (
              <button
                key={idx}
                onClick={() => setActiveDomainIdx(idx)}
                className={`flex items-center gap-2 px-3.5 py-2 rounded-lg text-xs font-bold transition-all cursor-pointer ${
                  activeDomainIdx === idx
                    ? 'bg-[#c40000] text-white shadow-md'
                    : 'bg-white/5 text-gray-300 hover:bg-white/10 hover:text-white'
                }`}
              >
                {getDomainIcon(idx)}
                <span>{item.domain}</span>
              </button>
            ))}
          </div>

          {/* Active Domain Skills Grid */}
          <div className="bg-[#0b0d11] border border-gray-800/80 rounded-xl p-6">
            <div className="flex items-center gap-3 mb-4">
              <div className="w-9 h-9 rounded-lg bg-[#c40000]/20 flex items-center justify-center">
                {getDomainIcon(activeDomainIdx)}
              </div>
              <div>
                <h4 className="text-lg font-bold text-white">
                  {itData.techCoverage[activeDomainIdx].domain}
                </h4>
                <div className="text-xs text-gray-400">
                  Pre-screened candidates available for contract, permanent &amp; hybrid models
                </div>
              </div>
            </div>

            <div className="grid grid-cols-2 sm:grid-cols-3 lg:grid-cols-4 gap-2.5 pt-2">
              {itData.techCoverage[activeDomainIdx].skills.map((skill, sIdx) => (
                <div
                  key={sIdx}
                  className="bg-white/5 border border-white/10 hover:border-[#c40000]/60 rounded-lg px-3 py-2 text-xs text-gray-200 flex items-center gap-2 transition-colors"
                >
                  <Check className="w-3.5 h-3.5 text-[#ff4d4d] shrink-0" />
                  <span className="font-medium truncate">{skill}</span>
                </div>
              ))}
            </div>

            <div className="mt-6 pt-4 border-t border-gray-800 flex flex-col sm:flex-row items-center justify-between gap-3">
              <span className="text-xs text-gray-400">
                Need talent with a specialized framework or custom stack?
              </span>
              <button
                onClick={() => handleWhatsAppInquiry(itData.techCoverage[activeDomainIdx].domain)}
                className="w-full sm:w-auto bg-[#c40000] hover:bg-[#a90000] text-white px-4 py-2 rounded-lg text-xs font-bold transition-colors flex items-center justify-center gap-1.5 cursor-pointer shadow-sm"
              >
                <span>Hire in {itData.techCoverage[activeDomainIdx].domain}</span>
                <ArrowRight className="w-3.5 h-3.5" />
              </button>
            </div>
          </div>
        </div>

        {/* ==================== 5. SYNERGY: RRGBS + TRINAARA TECHNOLOGIES ==================== */}
        <div className="mb-14">
          <div className="text-center max-w-2xl mx-auto mb-10">
            <div className="text-xs font-black uppercase tracking-widest text-[#ff4d4d] mb-1">
              Complementary Strengths
            </div>
            <h3 className="text-2xl sm:text-3xl font-extrabold text-white mb-2">
              The Power of the RRGBS–Trinaara Partnership
            </h3>
            <p className="text-xs sm:text-sm text-gray-400">
              RRGBS brings recruitment, HR, payroll and workforce management capabilities, while the partnership supports technology-focused talent and project requirements.
            </p>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            {/* RRGBS Card */}
            <div className="bg-[#14181f] border border-gray-800 rounded-2xl p-6 sm:p-8 flex flex-col justify-between">
              <div>
                <div className="flex items-center gap-3 mb-4">
                  <div className="w-12 h-12 rounded-xl bg-[#c40000] text-white flex items-center justify-center font-black text-xl">
                    RR
                  </div>
                  <div>
                    <h4 className="text-lg font-black text-white">RRGBS Capabilities</h4>
                    <p className="text-xs text-[#ff5252] font-semibold">
                      Recruitment, HR, Payroll &amp; Workforce Management
                    </p>
                  </div>
                </div>

                <ul className="space-y-3 pt-3 border-t border-gray-800">
                  {itData.synergy[0].points.map((pt, pIdx) => (
                    <li key={pIdx} className="flex items-start gap-2.5 text-xs sm:text-sm text-gray-300">
                      <CheckCircle2 className="w-4 h-4 text-[#ff4d4d] shrink-0 mt-0.5" />
                      <span>{pt}</span>
                    </li>
                  ))}
                </ul>
              </div>

              <div className="mt-6 pt-4 border-t border-gray-800 text-[11px] text-gray-400">
                Governance, SLA adherence, third-party payroll &amp; statutory compliance guaranteed.
              </div>
            </div>

            {/* Trinaara Technologies Card */}
            <div className="bg-[#14181f] border border-gray-800 rounded-2xl p-6 sm:p-8 flex flex-col justify-between relative overflow-hidden">
              <div className="absolute top-0 right-0 w-32 h-32 bg-[#c40000]/10 rounded-full blur-2xl pointer-events-none" />

              <div className="relative z-10">
                <div className="flex items-center gap-3 mb-4">
                  <div className="w-12 h-12 rounded-xl bg-gradient-to-br from-gray-800 to-gray-900 border border-white/20 text-[#ff4d4d] flex items-center justify-center font-black text-xl">
                    TT
                  </div>
                  <div>
                    <h4 className="text-lg font-black text-white">Trinaara Technologies</h4>
                    <p className="text-xs text-emerald-400 font-semibold">
                      Associate Partner for Hybrid IT Staffing
                    </p>
                  </div>
                </div>

                <ul className="space-y-3 pt-3 border-t border-gray-800">
                  {itData.synergy[1].points.map((pt, pIdx) => (
                    <li key={pIdx} className="flex items-start gap-2.5 text-xs sm:text-sm text-gray-300">
                      <CheckCircle2 className="w-4 h-4 text-emerald-400 shrink-0 mt-0.5" />
                      <span>{pt}</span>
                    </li>
                  ))}
                </ul>
              </div>

              <div className="mt-6 pt-4 border-t border-gray-800 text-[11px] text-gray-400 relative z-10">
                Domain screening, technical depth, agile sprint alignment &amp; technology project delivery.
              </div>
            </div>
          </div>
        </div>

        {/* ==================== 6. CALL TO ACTION BAR ==================== */}
        <div className="bg-gradient-to-r from-[#c40000] to-[#8a0000] rounded-2xl p-6 sm:p-10 text-white shadow-2xl flex flex-col lg:flex-row items-center justify-between gap-6">
          <div className="max-w-2xl">
            <div className="text-xs font-black uppercase tracking-widest text-red-200 mb-1">
              People • Process • Performance • Technology
            </div>
            <h3 className="text-2xl sm:text-3xl font-black mb-2">
              Ready to Scale Your Technology Workforce?
            </h3>
            <p className="text-xs sm:text-sm text-red-100 leading-relaxed">
              Connect with our IT staffing specialists to discuss permanent, contract, or hybrid tech team deployments across India.
            </p>
          </div>

          <div className="flex flex-wrap items-center gap-3 shrink-0">
            <button
              onClick={() => {
                onOpenContact({
                  id: 'it-staffing-general',
                  title: 'RRGBS IT Staffing Services (Trinaara Partnership)',
                  icon: '💻',
                  description: 'Hybrid IT Staffing Solutions and Services across India',
                  features: ['Permanent Hiring', 'Contract Staffing', 'C2H', 'Remote & Hybrid', 'Managed IT Teams'],
                });
              }}
              className="bg-white hover:bg-gray-100 text-[#c40000] px-5 py-3 rounded-lg text-xs sm:text-sm font-extrabold transition-all shadow-md flex items-center gap-2 cursor-pointer"
            >
              <span>Request IT Staffing Quote</span>
              <ArrowRight className="w-4 h-4" />
            </button>

            <button
              onClick={() => handleWhatsAppInquiry()}
              className="bg-black/30 hover:bg-black/50 text-white border border-white/30 px-4 py-3 rounded-lg text-xs sm:text-sm font-bold transition-all flex items-center gap-2 cursor-pointer"
            >
              <MessageCircle className="w-4 h-4 text-emerald-400" />
              <span>WhatsApp Desk</span>
            </button>

            <a
              href="tel:+916363565865"
              className="bg-black/30 hover:bg-black/50 text-white border border-white/30 px-3.5 py-3 rounded-lg text-xs font-bold transition-all flex items-center gap-1.5"
            >
              <Phone className="w-3.5 h-3.5 text-white" />
              <span className="hidden sm:inline">+91 63635 65865</span>
            </a>
          </div>
        </div>
      </div>
    </section>
  );
};
