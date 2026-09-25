import React from 'react';
import { Phone, Mail, Sparkles, Briefcase, Home, ShoppingBag } from 'lucide-react';
import { RRGBSLogo } from '../common/RRGBSLogo';

interface HomeTopBarProps {
  currentPortal: 'home' | 'jobs' | 'services' | 'store';
  onSwitchPortal: (portal: 'home' | 'jobs' | 'services' | 'store') => void;
}

export const HomeTopBar: React.FC<HomeTopBarProps> = ({
  currentPortal,
  onSwitchPortal,
}) => {
  return (
    <div className="bg-[#111111] text-white py-2 text-xs border-b border-gray-800">
      <div className="w-[92%] max-w-[1200px] mx-auto flex flex-col sm:flex-row items-center justify-between gap-2.5">
        <div className="flex items-center gap-2.5">
          <RRGBSLogo size={22} className="shadow-none" />
          <span className="font-semibold text-gray-200">
            RRGBS — Reliable People. Professional Services.
          </span>
          <span className="hidden md:inline-block w-1.5 h-1.5 rounded-full bg-[#d71920]"></span>
          <span className="hidden md:inline-block text-gray-400">
            Shivamogga • Bangalore • Karnataka
          </span>
        </div>

        <div className="flex flex-wrap items-center justify-center gap-3 text-gray-300">
          <a
            href="tel:+916363565865"
            className="flex items-center gap-1.5 hover:text-white transition-colors"
          >
            <Phone className="w-3.5 h-3.5 text-[#ff3b42]" />
            <span className="font-semibold">+91 63635 65865</span>
          </a>

          <span className="text-gray-600 hidden sm:inline">|</span>

          <a
            href="mailto:info@rrgroupofbusinesssolutions.in"
            className="flex items-center gap-1.5 hover:text-white transition-colors truncate max-w-[210px] sm:max-w-none"
          >
            <Mail className="w-3.5 h-3.5 text-[#ff3b42]" />
            <span>info@rrgroupofbusinesssolutions.in</span>
          </a>

          {/* Portal Switcher Button */}
          <div className="flex items-center bg-gray-800 p-0.5 rounded-md text-[11px] font-bold ml-1">
            <button
              onClick={() => onSwitchPortal('home')}
              className={`px-2 py-0.5 rounded transition-all cursor-pointer flex items-center gap-1 ${
                currentPortal === 'home'
                  ? 'bg-[#d71920] text-white'
                  : 'text-gray-400 hover:text-white'
              }`}
              title="RRGBS Doorstep Home Services"
            >
              <Home className="w-3 h-3" />
              <span>Home</span>
            </button>
            <button
              onClick={() => onSwitchPortal('jobs')}
              className={`px-2 py-0.5 rounded transition-all cursor-pointer flex items-center gap-1 ${
                currentPortal === 'jobs'
                  ? 'bg-[#d71920] text-white'
                  : 'text-gray-400 hover:text-white'
              }`}
              title="Browse Jobs & Apply"
            >
              <Briefcase className="w-3 h-3" />
              <span>Jobs</span>
            </button>
            <button
              onClick={() => onSwitchPortal('services')}
              className={`px-2 py-0.5 rounded transition-all cursor-pointer flex items-center gap-1 ${
                currentPortal === 'services'
                  ? 'bg-[#d71920] text-white'
                  : 'text-gray-400 hover:text-white'
              }`}
              title="Staffing, HR & Business Solutions"
            >
              <Sparkles className="w-3 h-3" />
              <span>Services</span>
            </button>
            <button
              onClick={() => onSwitchPortal('store')}
              className={`px-2 py-0.5 rounded transition-all cursor-pointer flex items-center gap-1 ${
                currentPortal === 'store'
                  ? 'bg-[#d71920] text-white'
                  : 'text-gray-400 hover:text-white'
              }`}
              title="RRGBS Business Store"
            >
              <ShoppingBag className="w-3 h-3" />
              <span>Store</span>
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};
