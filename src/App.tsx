import React, { useState, useEffect } from 'react';
import { Job, FilterState, ApplicationSubmission, RecruitmentService, HomeServiceItem } from './types';
import { INITIAL_JOBS } from './data/mockData';

// Jobs Portal Components
import { Navbar } from './components/Navbar';
import { HeroSection } from './components/HeroSection';
import { CategoriesSection } from './components/CategoriesSection';
import { JobListingsSection } from './components/JobListingsSection';
import { EmployersSection } from './components/EmployersSection';
import { ServicesSection } from './components/ServicesSection';
import { WhySection } from './components/WhySection';
import { CtaSection } from './components/CtaSection';
import { Footer } from './components/Footer';
import { JobDetailModal } from './components/JobDetailModal';
import { ApplyModal } from './components/ApplyModal';
import { PostJobModal } from './components/PostJobModal';
import { ResumeUploadModal } from './components/ResumeUploadModal';
import { AuthModal } from './components/AuthModal';
import { ContactModal } from './components/ContactModal';
import { ToastContainer, ToastMessage } from './components/Toast';

// Home Services Portal Components
import { HomeTopBar } from './components/home/HomeTopBar';
import { HomeNavbar } from './components/home/HomeNavbar';
import { HomeHero } from './components/home/HomeHero';
import { HomeServicesSection } from './components/home/HomeServicesSection';
import { HomeEstimatorSection } from './components/home/HomeEstimatorSection';
import { HomeWhySection } from './components/home/HomeWhySection';
import { HomeProcessSection } from './components/home/HomeProcessSection';
import { HomeCtaSection } from './components/home/HomeCtaSection';
import { HomeContactSection } from './components/home/HomeContactSection';
import { HomeFooter } from './components/home/HomeFooter';
import { FloatingActionButtons } from './components/home/FloatingActionButtons';

// Corporate Services Portal
import { JobPortalServicesView } from './components/services/JobPortalServicesView';

// Business Store Portal
import { RRGBSStoreView } from './components/store/RRGBSStoreView';

export default function App() {
  // Current active portal: 'store' (Online Business Store), 'services', 'jobs', or 'home'
  const [portal, setPortal] = useState<'home' | 'jobs' | 'services' | 'store'>('store');

  // Home service prefill state for contact section
  const [prefilledService, setPrefilledService] = useState<string>('Home Cleaning & Housekeeping');
  const [prefilledNote, setPrefilledNote] = useState<string>('');

  // -------------------- JOBS PORTAL STATE --------------------
  const [jobs, setJobs] = useState<Job[]>(() => {
    try {
      const stored = localStorage.getItem('rrgbs_custom_jobs_v1');
      if (stored) {
        const parsed = JSON.parse(stored);
        return [...parsed, ...INITIAL_JOBS];
      }
    } catch {
      // fallback
    }
    return INITIAL_JOBS;
  });

  const [savedJobIds, setSavedJobIds] = useState<string[]>(() => {
    try {
      const stored = localStorage.getItem('rrgbs_saved_job_ids_v1');
      if (stored) return JSON.parse(stored);
    } catch {
      // ignore
    }
    return [];
  });

  const [user, setUser] = useState<{
    name: string;
    role: 'candidate' | 'employer';
    email: string;
  } | null>(() => {
    try {
      const stored = localStorage.getItem('rrgbs_auth_user_v1');
      if (stored) return JSON.parse(stored);
    } catch {
      // ignore
    }
    return null;
  });

  const [filters, setFilters] = useState<FilterState>({
    keyword: '',
    location: '',
    jobType: '',
    category: '',
    experience: '',
  });

  const [showingSavedOnly, setShowingSavedOnly] = useState(false);

  // Modals for Jobs Portal
  const [selectedJobForDetail, setSelectedJobForDetail] = useState<Job | null>(null);
  const [selectedJobForApply, setSelectedJobForApply] = useState<Job | null>(null);
  const [postJobModalOpen, setPostJobModalOpen] = useState(false);
  const [resumeUploadModalOpen, setResumeUploadModalOpen] = useState(false);
  const [contactModalOpen, setContactModalOpen] = useState(false);
  const [selectedServiceForContact, setSelectedServiceForContact] = useState<RecruitmentService | null>(null);
  const [authModalOpen, setAuthModalOpen] = useState(false);
  const [authMode, setAuthMode] = useState<'login' | 'register'>('login');

  // Toasts
  const [toasts, setToasts] = useState<ToastMessage[]>([]);

  const addToast = (text: string, type: 'success' | 'info' | 'error' = 'success') => {
    const id = `toast-${Date.now()}-${Math.random()}`;
    setToasts((prev) => [...prev, { id, text, type }]);
    setTimeout(() => {
      setToasts((prev) => prev.filter((t) => t.id !== id));
    }, 4000);
  };

  const removeToast = (id: string) => {
    setToasts((prev) => prev.filter((t) => t.id !== id));
  };

  // Sync saved jobs with localStorage
  useEffect(() => {
    try {
      localStorage.setItem('rrgbs_saved_job_ids_v1', JSON.stringify(savedJobIds));
    } catch {
      // ignore
    }
  }, [savedJobIds]);

  // Sync user with localStorage
  useEffect(() => {
    try {
      if (user) {
        localStorage.setItem('rrgbs_auth_user_v1', JSON.stringify(user));
      } else {
        localStorage.removeItem('rrgbs_auth_user_v1');
      }
    } catch {
      // ignore
    }
  }, [user]);

  // Handle Home Service selection
  const handleSelectHomeService = (service: HomeServiceItem) => {
    setPrefilledService(service.title);
    setPrefilledNote(`I am looking for ${service.title} services. Please coordinate available staff and pricing.`);
    const contactEl = document.getElementById('contact');
    if (contactEl) {
      contactEl.scrollIntoView({ behavior: 'smooth' });
    }
    addToast(`Selected "${service.title}". Complete your contact details below.`, 'info');
  };

  // Handle Home Estimator Plan selection
  const handleSelectEstimatorPlan = (serviceName: string, optionLabel: string) => {
    setPrefilledService(serviceName);
    setPrefilledNote(`Interested in booking plan: ${optionLabel}. Please confirm slot and personnel.`);
    const contactEl = document.getElementById('contact');
    if (contactEl) {
      contactEl.scrollIntoView({ behavior: 'smooth' });
    }
    addToast(`Selected "${optionLabel}". Form updated below.`, 'info');
  };

  const scrollToContact = () => {
    const contactEl = document.getElementById('contact');
    if (contactEl) {
      contactEl.scrollIntoView({ behavior: 'smooth' });
    }
  };

  // -------------------- JOBS HANDLERS --------------------
  const handleFilterChange = (newFilters: Partial<FilterState>) => {
    setFilters((prev) => ({ ...prev, ...newFilters }));
  };

  const handleResetFilters = () => {
    setFilters({
      keyword: '',
      location: '',
      jobType: '',
      category: '',
      experience: '',
    });
    setShowingSavedOnly(false);
  };

  const handleToggleSaveJob = (jobId: string) => {
    setSavedJobIds((prev) => {
      const exists = prev.includes(jobId);
      if (exists) {
        addToast('Removed job from your saved list.', 'info');
        return prev.filter((id) => id !== jobId);
      } else {
        addToast('Job saved to your bookmarks!', 'success');
        return [...prev, jobId];
      }
    });
  };

  const handleJobCreated = (newJob: Job) => {
    setJobs((prev) => [newJob, ...prev]);
    try {
      const stored = localStorage.getItem('rrgbs_custom_jobs_v1');
      const existing = stored ? JSON.parse(stored) : [];
      localStorage.setItem('rrgbs_custom_jobs_v1', JSON.stringify([newJob, ...existing]));
    } catch {
      // ignore
    }
    addToast(`"${newJob.title}" has been published!`, 'success');
    handleResetFilters();
    setTimeout(() => {
      const el = document.getElementById('jobs');
      if (el) el.scrollIntoView({ behavior: 'smooth' });
    }, 150);
  };

  const handleApplicationSubmitted = (submission: ApplicationSubmission) => {
    try {
      const stored = localStorage.getItem('rrgbs_applications_v1');
      const existing = stored ? JSON.parse(stored) : [];
      localStorage.setItem('rrgbs_applications_v1', JSON.stringify([submission, ...existing]));
    } catch {
      // ignore
    }
    addToast(`Application for ${submission.jobTitle} submitted!`, 'success');
  };

  return (
    <div className="min-h-screen flex flex-col bg-white text-[#111111]">
      {/* Top Bar with Brand & Portal Switcher (for home & jobs portals) */}
      {(portal === 'home' || portal === 'jobs') && (
        <HomeTopBar
          currentPortal={portal}
          onSwitchPortal={(p) => {
            setPortal(p);
            window.scrollTo({ top: 0, behavior: 'smooth' });
          }}
        />
      )}

      {portal === 'store' ? (
        /* ==================== RRGBS ONLINE BUSINESS STORE ==================== */
        <div className="flex-1 flex flex-col">
          <RRGBSStoreView
            onSwitchPortal={(p) => {
              setPortal(p);
              window.scrollTo({ top: 0, behavior: 'smooth' });
            }}
            onShowToast={(msg, type) => addToast(msg, type)}
          />
        </div>
      ) : portal === 'services' ? (
        /* ==================== RRGBS CORPORATE & STAFFING SERVICES PORTAL ==================== */
        <div className="flex-1 flex flex-col">
          <JobPortalServicesView
            onSwitchPortal={(p) => {
              setPortal(p);
              window.scrollTo({ top: 0, behavior: 'smooth' });
            }}
            onOpenContact={(serv) => {
              setSelectedServiceForContact(serv || null);
              setContactModalOpen(true);
            }}
          />
        </div>
      ) : portal === 'home' ? (
        /* ==================== RRGBS HOME SERVICES PORTAL ==================== */
        <div className="flex-1 flex flex-col">
          {/* Main Navigation */}
          <HomeNavbar onRequestService={scrollToContact} />

          <main className="flex-1">
            {/* Hero Section */}
            <HomeHero onRequestService={scrollToContact} />

            {/* Complete Home Service Solutions Section */}
            <HomeServicesSection onSelectService={handleSelectHomeService} />

            {/* Interactive Pricing & Plan Estimator */}
            <HomeEstimatorSection onSelectOption={handleSelectEstimatorPlan} />

            {/* Why RRGBS Section */}
            <HomeWhySection />

            {/* How RRGBS Works 4-Step Process Section */}
            <HomeProcessSection />

            {/* Need a Home Service CTA Section */}
            <HomeCtaSection onRequestService={scrollToContact} />

            {/* Request a Home Service / Contact Section */}
            <HomeContactSection
              prefilledService={prefilledService}
              prefilledNote={prefilledNote}
            />
          </main>

          {/* Footer */}
          <HomeFooter
            onSwitchToJobs={() => {
              setPortal('jobs');
              window.scrollTo({ top: 0, behavior: 'smooth' });
            }}
          />

          {/* Floating Action Buttons (Phone & WhatsApp) */}
          <FloatingActionButtons />
        </div>
      ) : (
        /* ==================== RRGBS JOBS & STAFFING PORTAL ==================== */
        <div className="flex-1 flex flex-col bg-[#f6f7f9]">
          <Navbar
            savedJobsCount={savedJobIds.length}
            onOpenSavedJobs={() => {
              setShowingSavedOnly(true);
              const el = document.getElementById('jobs');
              if (el) el.scrollIntoView({ behavior: 'smooth' });
            }}
            onOpenPostJob={() => setPostJobModalOpen(true)}
            onOpenLogin={() => {
              setAuthMode('login');
              setAuthModalOpen(true);
            }}
            onOpenRegister={() => {
              setAuthMode('register');
              setAuthModalOpen(true);
            }}
            user={user}
            onLogout={() => {
              setUser(null);
              addToast('You have been logged out.', 'info');
            }}
            onOpenCorporateServices={() => {
              setPortal('services');
              window.scrollTo({ top: 0, behavior: 'smooth' });
            }}
            onOpenStore={() => {
              setPortal('store');
              window.scrollTo({ top: 0, behavior: 'smooth' });
            }}
          />

          <main className="flex-1">
            <HeroSection
              filters={filters}
              onFilterChange={handleFilterChange}
              onSearchSubmit={() => {
                const el = document.getElementById('jobs');
                if (el) el.scrollIntoView({ behavior: 'smooth' });
              }}
              totalJobsCount={jobs.length}
              jobs={jobs}
            />

            <CategoriesSection
              selectedCategory={filters.category}
              onSelectCategory={(cat) => {
                setFilters((prev) => ({
                  ...prev,
                  category: prev.category === cat ? '' : cat,
                }));
                setTimeout(() => {
                  const el = document.getElementById('jobs');
                  if (el) el.scrollIntoView({ behavior: 'smooth' });
                }, 100);
              }}
            />

            <JobListingsSection
              jobs={jobs}
              filters={filters}
              onFilterChange={handleFilterChange}
              onResetFilters={handleResetFilters}
              savedJobIds={savedJobIds}
              onToggleSaveJob={handleToggleSaveJob}
              onSelectJobForDetail={(job) => setSelectedJobForDetail(job)}
              onApplyForJob={(job) => setSelectedJobForApply(job)}
              showingSavedOnly={showingSavedOnly}
              onToggleShowingSavedOnly={() => setShowingSavedOnly(!showingSavedOnly)}
            />

            <EmployersSection
              onOpenPostJob={() => setPostJobModalOpen(true)}
              onOpenContact={() => {
                setSelectedServiceForContact(null);
                setContactModalOpen(true);
              }}
            />

            <ServicesSection
              onSelectService={(serv) => {
                setSelectedServiceForContact(serv);
                setContactModalOpen(true);
              }}
              onViewAllCorporateServices={() => {
                setPortal('services');
                window.scrollTo({ top: 0, behavior: 'smooth' });
              }}
            />

            <WhySection />

            <CtaSection
              onOpenRegister={() => {
                setAuthMode('register');
                setAuthModalOpen(true);
              }}
              onOpenResumeUpload={() => setResumeUploadModalOpen(true)}
            />
          </main>

          <Footer
            onOpenPostJob={() => setPostJobModalOpen(true)}
            onOpenLogin={() => {
              setAuthMode('login');
              setAuthModalOpen(true);
            }}
            onOpenRegister={() => {
              setAuthMode('register');
              setAuthModalOpen(true);
            }}
            onOpenResumeUpload={() => setResumeUploadModalOpen(true)}
            onOpenContact={() => {
              setSelectedServiceForContact(null);
              setContactModalOpen(true);
            }}
            onSelectCategory={(cat) => {
              setFilters((prev) => ({ ...prev, category: cat }));
              const el = document.getElementById('jobs');
              if (el) el.scrollIntoView({ behavior: 'smooth' });
            }}
          />

          {/* Job Portal Modals */}
          <JobDetailModal
            job={selectedJobForDetail}
            onClose={() => setSelectedJobForDetail(null)}
            onApply={(job) => setSelectedJobForApply(job)}
            isSaved={selectedJobForDetail ? savedJobIds.includes(selectedJobForDetail.id) : false}
            onToggleSave={handleToggleSaveJob}
          />

          <ApplyModal
            job={selectedJobForApply}
            onClose={() => setSelectedJobForApply(null)}
            onSubmitApplication={handleApplicationSubmitted}
          />

          <PostJobModal
            isOpen={postJobModalOpen}
            onClose={() => setPostJobModalOpen(false)}
            onJobCreated={handleJobCreated}
          />

          <ResumeUploadModal
            isOpen={resumeUploadModalOpen}
            onClose={() => setResumeUploadModalOpen(false)}
            onSuccess={(candidateName) => {
              addToast(`Resume registered for ${candidateName}!`, 'success');
            }}
          />

          <AuthModal
            isOpen={authModalOpen}
            initialMode={authMode}
            onClose={() => setAuthModalOpen(false)}
            onSuccess={(newUser) => {
              setUser(newUser);
              addToast(`Welcome, ${newUser.name}! Signed in as ${newUser.role}.`, 'success');
            }}
          />
        </div>
      )}

      {/* Shared Contact Modal (accessible across all portals) */}
      <ContactModal
        isOpen={contactModalOpen}
        onClose={() => setContactModalOpen(false)}
        service={selectedServiceForContact}
      />

      {/* Shared Toast Container */}
      <ToastContainer toasts={toasts} onDismiss={removeToast} />
    </div>
  );
}
