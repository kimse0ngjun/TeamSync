import { useState } from "react";
import AuthScreen from "./components/AuthScreen";
import Dashboard from "./components/Dashboard";
import TeamManagement from "./components/TeamManagement";
import ChatRoom from "./components/ChatRoom";
import CalendarView from "./components/CalendarView";
import MeetingSummary from "./components/MeetingSummary";
import ScheduleModal from "./components/ScheduleModal";
import SettingsPage from "./components/setting";

export type View =
  | "auth"
  | "dashboard"
  | "teams"
  | "chat"
  | "calendar"
  | "summary"
  | "settings";

export default function App() {
  const [currentView, setCurrentView] = useState<View>("auth");
  const [isScheduleModalOpen, setIsScheduleModalOpen] = useState(false);

  const handleLogin = () => {
    setCurrentView("dashboard");
  };

  const handleNavigate = (view: View) => {
    setCurrentView(view);
  };

  const handleOpenScheduleModal = () => {
    setIsScheduleModalOpen(true);
  };

  const handleCloseScheduleModal = () => {
    setIsScheduleModalOpen(false);
  };

  const handleSaveSchedule = () => {
    // Handle save schedule logic
    console.log("Schedule saved");
  };

  return (
    <>
      {currentView === "auth" && <AuthScreen onLogin={handleLogin} />}
      {currentView === "dashboard" && <Dashboard onNavigate={handleNavigate} />}
      {currentView === "teams" && (
        <TeamManagement onBack={() => handleNavigate("dashboard")} />
      )}
      {currentView === "chat" && (
        <ChatRoom
          onBack={() => handleNavigate("dashboard")}
          onOpenScheduleModal={handleOpenScheduleModal}
        />
      )}
      {currentView === "calendar" && (
        <CalendarView onBack={() => handleNavigate("dashboard")} />
      )}
      {currentView === "summary" && (
        <MeetingSummary onBack={() => handleNavigate("dashboard")} />
      )}
      {currentView === "settings" && <SettingsPage />}

      <ScheduleModal
        isOpen={isScheduleModalOpen}
        onClose={handleCloseScheduleModal}
        onSave={handleSaveSchedule}
      />
    </>
  );
}
