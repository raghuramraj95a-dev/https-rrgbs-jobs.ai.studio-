import React, { useState } from 'react';
import { Menu, X, Bookmark, PlusCircle, User, Phone, CheckCircle2, ShoppingBag } from 'lucide-react';
import { RRGBSLogo } from './common/RRGBSLogo';

interface NavbarProps {
  savedJobsCount: number;
  onOpenSavedJobs: () => void;
  onOpenPostJob: () => void;
  onOpenLogin: () => void;
  onOpenRegister: () => void;
  user: { name: string; role: 'candidate' | 'employer' } | null;
  onLogout: () => void;
  onOpenCorporateServices?: () => void;
  onOpenStore?: () => void;
}

export const Navbar: React.FC<NavbarProps> = ({
  savedJobsCount,
  onOpenSavedJobs,
  onOpenPostJob,
  onOpenLogin,
  onOpenRegister,
  user,
  onLogout,
  onOpenCorporateServices,
  onOpenStore,
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
    <header className="sticky top-0 z-50 bg-white border-b border-gray-200 shadow-xs">
      <div className="w-[92%] max-w-[1200px] mx-auto min-h-[72px] flex items-center justify-between gap-4 py-3">
        {/* Logo */}
        <a
          href="#"
          onClick={(e) => {
            e.preventDefault();
            window.scrollTo({ top: 0, behavior: 'smooth' });
          }}
          className="flex items-center gap-3 group"
          id="brand-logo"
        >
          <RRGBSLogo size={44} />
          <div className="flex flex-col">
            <div className="flex items-center gap-1.5 leading-tight">
              <span className="font-black text-xl text-[#111] tracking-tight">
                RR<span className="text-[#d71920]">GBS</span>
              </span>
              <span className="text-[10px] font-bold text-white bg-[#d71920] px-1.5 py-0.5 rounded">
                JOBS
              </span>
            </div>
            <span className="text-[9.5px] text-gray-500 font-bold uppercase tracking-wider mt-0.5">
              RR Group of Business Solutions
            </span>
          </div>
        </a>

        {/* Desktop Navigation */}
        <nav className="hidden md:flex items-center gap-6 lg:gap-8 text-sm font-semibold text-gray-700">
          <button
            onClick={() => scrollTo('jobs')}
            className="hover:text-[#d71920] transition-colors cursor-pointer"
          >
            Find Jobs
          </button>
          <button
            onClick={() => scrollTo('categories')}
            className="hover:text-[#d71920] transition-colors cursor-pointer"
          >
            Categories
          </button>
          <button
            onClick={() => {
              if (onOpenCorporateServices) {
                onOpenCorporateServices();
              } else {
                scrollTo('services');
              }
            }}
            className="hover:text-[#d71920] transition-colors cursor-pointer"
          >
            Services
          </button>
          {onOpenStore && (
            <button
              onClick={onOpenStore}
              className="text-[#d71920] hover:text-[#a90000] transition-colors cursor-pointer flex items-center gap-1 font-bold"
            >
              <ShoppingBag className="w-3.5 h-3.5" />
              Store
            </button>
          )}
          <button
            onClick={() => scrollTo('employer')}
            className="hover:text-[#d71920] transition-colors cursor-pointer"
          >
            Employers
          </button>
          <button
            onClick={() => scrollTo('about')}
            className="hover:text-[#d71920] transition-colors cursor-pointer"
          >
            About
          </button>
          <button
            onClick={() => scrollTo('contact')}
            className="hover:text-[#d71920] transition-colors cursor-pointer"
          >
            Contact
          </button>
        </nav>

        {/* Action Buttons */}
        <div className="hidden sm:flex items-center gap-3">
          <button
            onClick={onOpenSavedJobs}
            className="relative p-2 text-gray-600 hover:text-[#d71920] rounded-md hover:bg-gray-50 transition-colors"
            title="Saved Jobs"
            id="nav-saved-jobs"
          >
            <Bookmark className="w-5 h-5" />
            {savedJobsCount > 0 && (
              <span className="absolute -top-1 -right-1 bg-[#d71920] text-white text-[10px] font-bold w-4 h-4 rounded-full flex items-center justify-center">
                {savedJobsCount}
              </span>
            )}
          </button>

          <button
            onClick={onOpenPostJob}
            className="hidden lg:flex items-center gap-1.5 px-3 py-2 text-xs font-bold text-gray-700 hover:text-[#d71920] border border-gray-300 rounded-md hover:border-[#d71920] transition-colors"
            id="nav-post-job-btn"
          >
            <PlusCircle className="w-3.5 h-3.5 text-[#d71920]" />
            Post a Job
          </button>

          {user ? (
            <div className="flex items-center gap-2 pl-2 border-l border-gray-200">
              <div className="flex items-center gap-2 px-3 py-1.5 bg-gray-100 rounded-md text-xs font-semibold text-gray-800">
                <User className="w-3.5 h-3.5 text-[#d71920]" />
                <span className="max-w-[100px] truncate">{user.name}</span>
                <span className="text-[10px] bg-red-100 text-[#d71920] px-1 rounded uppercase">
                  {user.role}
                </span>
              </div>
              <button
                onClick={onLogout}
                className="text-xs font-semibold text-gray-500 hover:text-red-600 underline"
              >
                Logout
              </button>
            </div>
          ) : (
            <div className="flex items-center gap-2">
              <button
                onClick={onOpenLogin}
                className="px-4 py-2 text-xs font-bold text-[#d71920] bg-white border border-[#d71920] rounded-md hover:bg-red-50 transition-colors"
                id="nav-login-btn"
              >
                Login
              </button>
              <button
                onClick={onOpenRegister}
                className="px-4 py-2 text-xs font-bold text-white bg-[#d71920] rounded-md hover:bg-[#b8141a] shadow-xs transition-colors"
                id="nav-register-btn"
              >
                Register
              </button>
            </div>
          )}
        </div>

        {/* Mobile Hamburger Button */}
        <div className="flex items-center gap-2 sm:hidden">
          <button
            onClick={onOpenSavedJobs}
            className="relative p-2 text-gray-600 hover:text-[#d71920]"
          >
            <Bookmark className="w-5 h-5" />
            {savedJobsCount > 0 && (
              <span className="absolute 0 top-0 right-0 bg-[#d71920] text-white text-[10px] font-bold w-4 h-4 rounded-full flex items-center justify-center">
                {savedJobsCount}
              </span>
            )}
          </button>

          <button
            onClick={() => setMobileMenuOpen(!mobileMenuOpen)}
            className="p-2 text-gray-700 hover:text-[#d71920] focus:outline-none"
            aria-label="Toggle Menu"
          >
            {mobileMenuOpen ? <X className="w-6 h-6" /> : <Menu className="w-6 h-6" />}
          </button>
        </div>
      </div>

      {/* Mobile Drawer */}
      {mobileMenuOpen && (
        <div className="md:hidden border-t border-gray-200 bg-white px-5 py-4 space-y-3 shadow-lg">
          <div className="flex flex-col space-y-2.5 text-sm font-semibold text-gray-800">
            <button
              onClick={() => scrollTo('jobs')}
              className="text-left py-1 hover:text-[#d71920]"
            >
              Find Jobs
            </button>
            <button
              onClick={() => scrollTo('categories')}
              className="text-left py-1 hover:text-[#d71920]"
            >
              Categories
            </button>
            <button
              onClick={() => {
                setMobileMenuOpen(false);
                if (onOpenCorporateServices) {
                  onOpenCorporateServices();
                } else {
                  scrollTo('services');
                }
              }}
              className="text-left py-1 hover:text-[#d71920]"
            >
              Recruitment &amp; Corporate Services
            </button>
            {onOpenStore && (
              <button
                onClick={() => {
                  setMobileMenuOpen(false);
                  onOpenStore();
                }}
                className="text-left py-1 text-[#d71920] font-bold flex items-center gap-1.5"
              >
                <ShoppingBag className="w-3.5 h-3.5" />
                RRGBS Business Store
              </button>
            )}
            <button
              onClick={() => scrollTo('employer')}
              className="text-left py-1 hover:text-[#d71920]"
            >
              For Employers
            </button>
            <button
              onClick={() => scrollTo('about')}
              className="text-left py-1 hover:text-[#d71920]"
            >
              About RRGBS
            </button>
            <button
              onClick={() => scrollTo('contact')}
              className="text-left py-1 hover:text-[#d71920]"
            >
              Contact & Support
            </button>
          </div>

          <div className="pt-3 border-t border-gray-100 flex flex-col gap-2">
            <button
              onClick={() => {
                setMobileMenuOpen(false);
                onOpenPostJob();
              }}
              className="w-full flex items-center justify-center gap-1.5 py-2.5 text-xs font-bold text-[#d71920] border border-[#d71920] rounded-md"
            >
              <PlusCircle className="w-4 h-4" />
              Post a Job as Employer
            </button>

            {user ? (
              <div className="flex items-center justify-between pt-2">
                <span className="text-xs font-medium text-gray-700">
                  Logged in as <b>{user.name}</b> ({user.role})
                </span>
                <button
                  onClick={onLogout}
                  className="text-xs font-bold text-red-600 underline"
                >
                  Logout
                </button>
              </div>
            ) : (
              <div className="grid grid-cols-2 gap-2 pt-1">
                <button
                  onClick={() => {
                    setMobileMenuOpen(false);
                    onOpenLogin();
                  }}
                  className="py-2.5 text-center text-xs font-bold text-gray-800 border border-gray-300 rounded-md"
                >
                  Login
                </button>
                <button
                  onClick={() => {
                    setMobileMenuOpen(false);
                    onOpenRegister();
                  }}
                  className="py-2.5 text-center text-xs font-bold text-white bg-[#d71920] rounded-md"
                >
                  Register
                </button>
              </div>
            )}
          </div>
        </div>
      )}
    </header>
  );
};
