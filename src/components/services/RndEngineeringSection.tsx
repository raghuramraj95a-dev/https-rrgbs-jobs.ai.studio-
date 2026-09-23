import React, { useState } from 'react';
import { RRGBS_RND_ENGINEERING_PARTNERSHIP } from '../../data/servicesData';
import {
  Cog,
  Cpu,
  Layers,
  Laptop,
  Check,
  ArrowRight,
  MessageCircle,
  Phone,
  Factory,
  ChevronRight,
  ShieldCheck,
  CheckCircle2,
  Wrench,
  Bot,
  Zap,
} from 'lucide-react';
import { RecruitmentService } from '../../types';

interface RndEngineeringSectionProps {
  onOpenContact: (service?: RecruitmentService | null) => void;
}

export const RndEngineeringSection: React.FC<RndEngineeringSectionProps> = ({
  onOpenContact,
}) => {
  const [selectedCapIdx, setSelectedCapIdx] = useState(0);
  const rnd = RRGBS_RND_ENGINEERING_PARTNERSHIP;

  const getCapabilityIcon = (idx: number) => {
    switch (idx) {
      case 0:
        return <Wrench className="w-5 h-5 text-[#c40000]" />;
      case 1:
        return <Bot className="w-5 h-5 text-[#c40000]" />;
      case 2:
        return <Layers className="w-5 h-5 text-[#c40000]" />;
      case 3:
        return <Laptop className="w-5 h-5 text-[#c40000]" />;
      default:
        return <Cog className="w-5 h-5 text-[#c40000]" />;
    }
  };

  const handleWhatsAppRndInquiry = (topic?: string) => {
    let msg = `Hello RRGBS R&D & Engineering Desk,\n\nI am inquiring about R&D, Engineering & Industrial Solutions with your Associate Partner *Annapurna Technologies, Bengaluru*.\n\n`;
    if (topic) {
      msg += `Requirement Area: ${topic}\n`;
    }
    msg += `We would like to discuss engineering consultation, prototype development, tooling, or technology deployment for our organization. Please connect with us.`;

    const url = `https://wa.me/916363565865?text=${encodeURIComponent(msg)}`;
    window.open(url, '_blank');
  };

  return (
    <section id="rnd-engineering" className="py-20 bg-[#0f1115] text-white relative overflow-hidden border-b border-gray-800">
      {/* Background glow and subtle industrial grid */}
      <div className="absolute top-1/4 right-0 w-[550px] h-[550px] bg-red-600/10 rounded-full blur-3xl pointer-events-none" />
      <div className="absolute bottom-10 left-10 w-[450px] h-[450px] bg-amber-600/5 rounded-full blur-3xl pointer-events-none" />

      <div className="w-[92%] max-w-[1240px] mx-auto relative z-10">
        {/* ==================== 1. PARTNERSHIP HEADER ==================== */}
        <div className="text-center max-w-[920px] mx-auto mb-14">
          <div className="inline-flex items-center gap-2 px-4 py-1.5 rounded-full bg-white/10 border border-white/20 text-xs sm:text-sm font-bold text-gray-200 mb-5 backdrop-blur-md">
            <span className="w-2.5 h-2.5 rounded-full bg-[#ff3b3b] animate-pulse" />
            <span className="text-[#ff5252] font-black uppercase tracking-wider">Associate Partner</span>
            <span className="text-gray-400">•</span>
            <span>Annapurna Technologies, Bengaluru</span>
          </div>

          <h2 className="text-3xl sm:text-4xl lg:text-5xl font-black tracking-tight leading-tight mb-5">
            RRGBS – R&amp;D, Engineering &amp; Technology
            <span className="block text-xl sm:text-2xl lg:text-3xl font-bold text-[#ff4d4d] mt-2">
              Associate Partner: Annapurna Technologies, Bengaluru
            </span>
          </h2>

          <p className="text-sm sm:text-base lg:text-lg text-gray-300 leading-relaxed font-normal mb-4">
            RRGBS, in association with <strong className="text-white font-semibold">Annapurna Technologies, Bengaluru</strong>, supports end-to-end Research &amp; Development (R&amp;D), engineering, technology and industrial solutions.
          </p>

          <p className="text-xs sm:text-sm text-gray-400 leading-relaxed max-w-3xl mx-auto">
            From concept, design and prototype development to testing, deployment and maintenance, RRGBS and its technology associates provide integrated, business-focused solutions for startups, SMEs, manufacturers and enterprises across India.
          </p>
        </div>

        {/* ==================== 2. END-TO-END LIFECYCLE BAR ==================== */}
        <div className="mb-16">
          <div className="text-center max-w-xl mx-auto mb-6">
            <div className="text-xs font-black uppercase tracking-widest text-[#ff4d4d] mb-1">
              End-to-End Execution
            </div>
            <h3 className="text-xl sm:text-2xl font-extrabold text-white">
              Concept to Deployment &amp; Maintenance
            </h3>
          </div>

          <div className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-6 gap-3">
            {rnd.lifecycle.map((step, idx) => (
              <div
                key={idx}
                className="bg-[#161a22] border border-gray-800 hover:border-[#c40000] rounded-xl p-4 transition-all duration-200 group hover:-translate-y-1 hover:bg-[#1a202c]"
              >
                <div className="flex items-center justify-between mb-2">
                  <span className="text-xs font-black text-[#ff4d4d]">
                    STEP {step.step}
                  </span>
                  <span className="w-1.5 h-1.5 rounded-full bg-gray-600 group-hover:bg-[#ff4d4d] transition-colors" />
                </div>
                <h4 className="text-sm font-bold text-white group-hover:text-[#ff4d4d] transition-colors mb-1.5 leading-snug">
                  {step.title}
                </h4>
                <p className="text-[11px] text-gray-400 leading-relaxed">
                  {step.description}
                </p>
              </div>
            ))}
          </div>
        </div>

        {/* ==================== 3. FOUR CORE CAPABILITIES ==================== */}
        <div className="mb-16">
          <div className="flex flex-col md:flex-row md:items-end justify-between gap-4 mb-8">
            <div>
              <div className="text-xs font-extrabold uppercase tracking-widest text-[#ff4d4d] mb-1">
                Engineering &amp; Manufacturing Depth
              </div>
              <h3 className="text-2xl sm:text-3xl font-extrabold text-white">
                Core Industrial &amp; Technology Capabilities
              </h3>
            </div>
            <p className="text-xs sm:text-sm text-gray-400 max-w-md">
              Combining mechanical tool-room precision with cutting-edge electronics, automation, and industrial software systems.
            </p>
          </div>

          {/* Interactive Capabilities Grid */}
          <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
            {rnd.capabilities.map((cap, idx) => (
              <div
                key={idx}
                className="bg-[#141820] border border-gray-800 hover:border-[#c40000]/80 rounded-2xl p-6 sm:p-7 transition-all duration-200 flex flex-col justify-between group shadow-xl"
              >
                <div>
                  <div className="flex items-center justify-between mb-4">
                    <div className="flex items-center gap-3">
                      <div className="w-11 h-11 rounded-xl bg-[#c40000]/15 text-[#ff4d4d] flex items-center justify-center">
                        {getCapabilityIcon(idx)}
                      </div>
                      <div>
                        <span className="text-[11px] font-bold uppercase tracking-wider text-[#ff5252]">
                          {cap.category}
                        </span>
                        <h4 className="text-lg font-bold text-white group-hover:text-[#ff4d4d] transition-colors">
                          {cap.title}
                        </h4>
                      </div>
                    </div>
                  </div>

                  <p className="text-xs sm:text-sm text-gray-300 leading-relaxed mb-5">
                    {cap.description}
                  </p>

                  <div className="grid grid-cols-1 sm:grid-cols-2 gap-2 pt-4 border-t border-gray-800">
                    {cap.highlights.map((h, hIdx) => (
                      <div key={hIdx} className="flex items-start gap-2 text-xs text-gray-300">
                        <Check className="w-3.5 h-3.5 text-[#ff4d4d] shrink-0 mt-0.5 stroke-[2.5]" />
                        <span>{h}</span>
                      </div>
                    ))}
                  </div>
                </div>

                <div className="mt-6 pt-4 border-t border-gray-800 flex items-center justify-between">
                  <button
                    onClick={() => {
                      onOpenContact({
                        id: `rnd-capability-${idx}`,
                        title: `${cap.title} (Annapurna Technologies Partnership)`,
                        icon: '⚙️',
                        description: cap.description,
                        features: cap.highlights,
                      });
                    }}
                    className="text-xs font-bold text-gray-300 hover:text-white flex items-center gap-1 cursor-pointer group/link"
                  >
                    <span>Request Technical RFQ</span>
                    <ChevronRight className="w-3.5 h-3.5 text-[#ff4d4d] group-hover/link:translate-x-1 transition-transform" />
                  </button>

                  <button
                    onClick={() => handleWhatsAppRndInquiry(cap.title)}
                    className="text-[11px] font-semibold text-emerald-400 hover:text-emerald-300 flex items-center gap-1 cursor-pointer"
                  >
                    <MessageCircle className="w-3 h-3" />
                    <span>WhatsApp</span>
                  </button>
                </div>
              </div>
            ))}
          </div>
        </div>

        {/* ==================== 4. SOFTWARE, AI, IOT & DIGITAL SYSTEMS ==================== */}
        <div className="mb-16 bg-[#141820] border border-gray-800 rounded-2xl p-6 sm:p-8 lg:p-10 shadow-2xl">
          <div className="max-w-3xl mb-6">
            <div className="text-xs font-black uppercase tracking-widest text-[#ff4d4d] mb-1">
              Integrated Digital Ecosystem
            </div>
            <h3 className="text-2xl sm:text-3xl font-extrabold text-white mb-2">
              Software, AI, IoT &amp; Industrial Systems
            </h3>
            <p className="text-xs sm:text-sm text-gray-400">
              Supporting comprehensive enterprise software and hardware integration for industrial automation, operations, and commerce.
            </p>
          </div>

          <div className="grid grid-cols-2 sm:grid-cols-3 lg:grid-cols-3 gap-3">
            {rnd.softwareCapabilities.map((item, idx) => (
              <div
                key={idx}
                className="bg-white/5 border border-white/10 hover:border-[#c40000]/60 rounded-xl p-3.5 sm:p-4 flex items-center gap-3 transition-colors group cursor-pointer"
                onClick={() => handleWhatsAppRndInquiry(item)}
              >
                <div className="w-8 h-8 rounded-lg bg-[#c40000]/20 flex items-center justify-center shrink-0">
                  <CheckCircle2 className="w-4 h-4 text-[#ff4d4d]" />
                </div>
                <div>
                  <h5 className="text-xs sm:text-sm font-bold text-white group-hover:text-[#ff4d4d] transition-colors">
                    {item}
                  </h5>
                  <span className="text-[10px] text-gray-400">Integrated Solution</span>
                </div>
              </div>
            ))}
          </div>
        </div>

        {/* ==================== 5. WHO WE SERVE & RFQ CALLOUT ==================== */}
        <div className="mb-14 bg-gradient-to-r from-gray-900 to-[#181c24] border border-gray-800 rounded-2xl p-6 sm:p-8">
          <div className="flex flex-col lg:flex-row lg:items-center justify-between gap-6">
            <div>
              <div className="text-xs font-black uppercase tracking-widest text-[#ff4d4d] mb-1">
                Client Spectrum
              </div>
              <h4 className="text-xl sm:text-2xl font-black text-white mb-3">
                Built for Startups, SMEs, Manufacturers &amp; Enterprises
              </h4>
              <div className="flex flex-wrap gap-2">
                {rnd.industriesServed.map((ind, i) => (
                  <span
                    key={i}
                    className="px-3 py-1 rounded-full text-[11px] font-semibold bg-white/5 border border-white/10 text-gray-300"
                  >
                    {ind}
                  </span>
                ))}
              </div>
            </div>

            <div className="shrink-0 flex flex-col sm:flex-row gap-3">
              <button
                onClick={() => {
                  onOpenContact({
                    id: 'rnd-general',
                    title: 'R&D & Engineering Consultation (Annapurna Technologies Partnership)',
                    icon: '⚙️',
                    description: 'End-to-End R&D, Engineering, Tool-Room, Automation & Software Solutions',
                    features: [
                      'Tool-Room & CNC/VMC Machining',
                      'Moulds, Dies, Jigs & Fixtures',
                      'Automation & Robotics',
                      'Prototyping & Testing',
                      'Software, AI & IoT Systems',
                    ],
                  });
                }}
                className="bg-[#c40000] hover:bg-[#a90000] text-white px-5 py-3 rounded-lg text-xs font-extrabold uppercase tracking-wider transition-all shadow-md flex items-center justify-center gap-2 cursor-pointer"
              >
                <span>Request R&amp;D Consultation</span>
                <ArrowRight className="w-3.5 h-3.5" />
              </button>

              <button
                onClick={() => handleWhatsAppRndInquiry()}
                className="bg-white/10 hover:bg-white/20 border border-white/20 text-white px-4 py-3 rounded-lg text-xs font-bold transition-all flex items-center justify-center gap-2 cursor-pointer"
              >
                <MessageCircle className="w-4 h-4 text-emerald-400" />
                <span>WhatsApp R&amp;D Desk</span>
              </button>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
};
