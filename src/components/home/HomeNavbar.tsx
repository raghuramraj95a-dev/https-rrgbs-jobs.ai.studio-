import React, { useState } from 'react';
import { Menu, X, ArrowRight, Phone } from 'lucide-react';
import { RRGBSLogo } from '../common/RRGBSLogo';

interface HomeNavbarProps {
  onRequestService: () => void;
  onOpenEstimator?: () => void;
}

export const HomeNavbar: React.FC<HomeNavbarProps> = ({
  onRequestService,
  onOpenEstimator,
}) => {
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);

  const scrollTo = (id: string) => {
    setMobileMenuOpen(false);
    const element = document.getElementById(id);
    if (element) {
      element.scrollIntoView({ behavior: 'smooth' });
    }
  };

  return (
    <header className="bg-white sticky top-0 z-40 shadow-xs border-b border-gray-100">
      <div className="w-[92%] max-w-[1200px] mx-auto min-h-[78px] flex items-center justify-between gap-4">
        {/* Logo */}
        <a href="#home" className="flex items-center gap-3 group" id="brand-logo-home">
          <RRGBSLogo size={46} />
          <div className="flex flex-col">
            <div className="flex items-center gap-1.5 leading-none">
              <span className="font-black text-2xl tracking-tight text-[#111111]">
                RR<span className="text-[#d71920]">GBS</span>
              </span>
              <span className="text-[10px] font-bold text-[#d71920] bg-red-50 border border-red-200/80 px-2 py-0.5 rounded-full">
                HOME SERVICES
              </span>
            </div>
            <span className="text-[8.5px] tracking-[1.5px] text-gray-500 font-bold uppercase mt-1">
              RR GROUP OF BUSINESS SOLUTIONS
            </span>
          </div>
        </a>

        {/* Desktop Navigation Links */}
        <nav className="hidden lg:flex items-center gap-7">
          <button
            onClick={() => scrollTo('home')}
            className="text-sm font-bold text-gray-800 hover:text-[#d71920] transition-colors cursor-pointer"
          >
            Home
          </button>
          <button
            onClick={() => scrollTo('services')}
            className="text-sm font-bold text-gray-800 hover:text-[#d71920] transition-colors cursor-pointer"
          >
            Services
          </button>
          <button
            onClick={() => scrollTo('estimator')}
            className="text-sm font-bold text-gray-800 hover:text-[#d71920] transition-colors cursor-pointer"
          >
            Pricing &amp; Plans
          </button>
          <button
            onClick={() => scrollTo('why-us')}
            className="text-sm font-bold text-gray-800 hover:text-[#d71920] transition-colors cursor-pointer"
          >
            Why RRGBS
          </button>
          <button
            onClick={() => scrollTo('process')}
            className="text-sm font-bold text-gray-800 hover:text-[#d71920] transition-colors cursor-pointer"
          >
            How It Works
          </button>
          <button
            onClick={() => scrollTo('contact')}
            className="text-sm font-bold text-gray-800 hover:text-[#d71920] transition-colors cursor-pointer"
          >
            Contact
          </button>

          {/* Primary Action Button */}
          <button
            onClick={onRequestService}
            className="bg-[#d71920] hover:bg-[#a90000] text-white px-5 py-2.5 rounded text-sm font-bold transition-all shadow-sm active:scale-98 cursor-pointer flex items-center gap-1.5"
          >
            <span>Request Service</span>
            <ArrowRight className="w-4 h-4" />
          </button>
        </nav>

        {/* Mobile Menu Button */}
        <div className="flex items-center gap-2 lg:hidden">
          <a
            href="tel:+916363565865"
            className="p-2 rounded-lg bg-red-50 text-[#d71920] hover:bg-red-100 transition-colors"
            title="Call RRGBS"
          >
            <Phone className="w-5 h-5" />
          </a>
          <button
            onClick={() => setMobileMenuOpen(!mobileMenuOpen)}
            className="p-2 text-gray-800 hover:text-[#d71920] text-2xl cursor-pointer"
            aria-label="Toggle menu"
          >
            {mobileMenuOpen ? <X className="w-6 h-6" /> : <Menu className="w-6 h-6" />}
          </button>
        </div>
      </div>

      {/* Mobile Drawer */}
      {mobileMenuOpen && (
        <div className="lg:hidden bg-white border-t border-gray-100 px-6 py-5 shadow-lg animate-in slide-in-from-top-2 duration-200">
          <div className="flex flex-col gap-3.5">
            <button
              onClick={() => scrollTo('home')}
              className="text-left font-bold text-sm text-gray-800 py-1 hover:text-[#d71920]"
            >
              Home
            </button>
            <button
              onClick={() => scrollTo('services')}
              className="text-left font-bold text-sm text-gray-800 py-1 hover:text-[#d71920]"
            >
              Services
            </button>
            <button
              onClick={() => scrollTo('estimator')}
              className="text-left font-bold text-sm text-gray-800 py-1 hover:text-[#d71920]"
            >
              Pricing &amp; Plans
            </button>
            <button
              onClick={() => scrollTo('why-us')}
              className="text-left font-bold text-sm text-gray-800 py-1 hover:text-[#d71920]"
            >
              Why RRGBS
            </button>
            <button
              onClick={() => scrollTo('process')}
              className="text-left font-bold text-sm text-gray-800 py-1 hover:text-[#d71920]"
            >
              How It Works
            </button>
            <button
              onClick={() => scrollTo('contact')}
              className="text-left font-bold text-sm text-gray-800 py-1 hover:text-[#d71920]"
            >
              Contact
            </button>

            <div className="pt-3 border-t border-gray-100 flex flex-col gap-2">
              <button
                onClick={() => {
                  setMobileMenuOpen(false);
                  onRequestService();
                }}
                className="w-full bg-[#d71920] text-white py-2.5 rounded font-bold text-sm text-center shadow-sm"
              >
                Request Service
              </button>
              <a
                href="https://wa.me/916363565865?text=Hello%20RRGBS,%20I%20need%20a%20Home%20Service."
                target="_blank"
                rel="noreferrer"
                className="w-full bg-[#25d366] text-white py-2.5 rounded font-bold text-sm text-center shadow-sm flex items-center justify-center gap-2"
              >
                <span>WhatsApp Us</span>
              </a>
            </div>
          </div>
        </div>
      )}
    </header>
  );
};
