import { useState } from "react";
import { Button } from "./ui/button";
import { Avatar, AvatarFallback } from "./ui/avatar";
import { Badge } from "./ui/badge";
import {
  Home,
  Users,
  MessageSquare,
  Calendar,
  Settings,
  Plus,
  UserPlus,
  Clock,
  Sparkles,
  ChevronDown,
  MoreHorizontal,
  TrendingUp,
} from "lucide-react";

import type { View } from "../App"; // App 경로에 맞게 수정

interface DashboardProps {
  onNavigate: (view: View) => void;
}

export default function Dashboard({ onNavigate }: DashboardProps) {
  const [activeTeam, setActiveTeam] = useState("전체");

  const upcomingEvents = [
    {
      id: 1,
      title: "주간 스프린트 회의",
      time: "오늘 오후 2:00",
      team: "개발팀",
      attendees: 5,
      color: "indigo",
    },
    {
      id: 2,
      title: "프로젝트 킥오프",
      time: "내일 오전 10:00",
      team: "디자인팀",
      attendees: 8,
      color: "blue",
    },
    {
      id: 3,
      title: "월간 리뷰",
      time: "금요일 오후 4:00",
      team: "마케팅팀",
      attendees: 12,
      color: "purple",
    },
  ];

  const recentActivities = [
    {
      id: 1,
      user: "김민수",
      action: "회의 요약을 생성했습니다",
      target: "주간 스프린트 회의",
      time: "5분 전",
    },
    {
      id: 2,
      user: "이지은",
      action: "일정을 생성했습니다",
      target: "프로젝트 킥오프",
      time: "15분 전",
    },
    {
      id: 3,
      user: "박서준",
      action: "새 팀원을 초대했습니다",
      target: "개발팀",
      time: "1시간 전",
    },
    {
      id: 4,
      user: "최유진",
      action: "메시지를 남겼습니다",
      target: "디자인 리뷰",
      time: "2시간 전",
    },
  ];

  const activeRooms = [
    { id: 1, name: "디자인 리뷰", members: 4, unread: 3, team: "디자인팀" },
    { id: 2, name: "스프린트 플래닝", members: 6, unread: 0, team: "개발팀" },
    { id: 3, name: "마케팅 캠페인", members: 5, unread: 7, team: "마케팅팀" },
  ];

  const teamMembers = [
    { id: 1, name: "김민수", role: "Owner", status: "online", avatar: "KM" },
    { id: 2, name: "이지은", role: "Admin", status: "online", avatar: "LJ" },
    { id: 3, name: "박서준", role: "Member", status: "away", avatar: "PS" },
    { id: 4, name: "최유진", role: "Member", status: "offline", avatar: "CY" },
    { id: 5, name: "정수아", role: "Member", status: "online", avatar: "JS" },
  ];

  const teams = [
    { name: "개발팀", members: 8, color: "indigo" },
    { name: "디자인팀", members: 5, color: "blue" },
    { name: "마케팅팀", members: 6, color: "purple" },
  ];

  return (
    <div className="min-h-screen bg-gradient-to-br from-indigo-50 via-white to-blue-50">
      {/* Sidebar */}
      <aside className="fixed left-0 top-0 h-full w-64 glass-sidebar p-4 space-y-6 z-10">
        {/* Organization Header */}
        <div className="space-y-4">
          <div className="flex items-center gap-3">
            <div className="w-10 h-10 rounded-xl bg-gradient-to-br from-indigo-600 to-blue-600 flex items-center justify-center">
              <Sparkles className="w-5 h-5 text-white" />
            </div>
            <div className="flex-1 min-w-0">
              <h2 className="text-slate-900 truncate">TeamSync</h2>
              <p className="text-xs text-slate-600">Premium Plan</p>
            </div>
            <button className="p-1.5 hover:bg-white/50 rounded-lg transition-colors">
              <ChevronDown className="w-4 h-4 text-slate-600" />
            </button>
          </div>
        </div>

        {/* Navigation */}
        <nav className="space-y-1">
          <button
            onClick={() => onNavigate("dashboard")}
            className="w-full flex items-center gap-3 px-3 py-2.5 rounded-xl bg-indigo-100 text-indigo-700 transition-colors"
          >
            <Home className="w-5 h-5" />
            <span>대시보드</span>
          </button>
          <button
            onClick={() => onNavigate("teams")}
            className="w-full flex items-center gap-3 px-3 py-2.5 rounded-xl hover:bg-white/50 text-slate-700 transition-colors"
          >
            <Users className="w-5 h-5" />
            <span>팀 관리</span>
          </button>
          <button
            onClick={() => onNavigate("chat")}
            className="w-full flex items-center gap-3 px-3 py-2.5 rounded-xl hover:bg-white/50 text-slate-700 transition-colors"
          >
            <MessageSquare className="w-5 h-5" />
            <span>회의방</span>
            <Badge className="ml-auto bg-indigo-600 text-white">3</Badge>
          </button>
          <button
            onClick={() => onNavigate("calendar")}
            className="w-full flex items-center gap-3 px-3 py-2.5 rounded-xl hover:bg-white/50 text-slate-700 transition-colors"
          >
            <Calendar className="w-5 h-5" />
            <span>캘린더</span>
          </button>
          <button
            onClick={() => onNavigate("summary")}
            className="w-full flex items-center gap-3 px-3 py-2.5 rounded-xl hover:bg-white/50 text-slate-700 transition-colors"
          >
            <Sparkles className="w-5 h-5" />
            <span>회의 요약</span>
          </button>
        </nav>

        {/* Teams Section */}
        <div className="space-y-2">
          <div className="flex items-center justify-between px-3">
            <span className="text-sm text-slate-600">팀</span>
            <button className="p-1 hover:bg-white/50 rounded transition-colors">
              <Plus className="w-4 h-4 text-slate-600" />
            </button>
          </div>
          <div className="space-y-1">
            {teams.map((team) => (
              <button
                key={team.name}
                className="w-full flex items-center gap-3 px-3 py-2 rounded-lg hover:bg-white/50 text-slate-700 transition-colors group"
              >
                <div
                  className={`w-2 h-2 rounded-full bg-${team.color}-500`}
                ></div>
                <span className="flex-1 text-left text-sm">{team.name}</span>
                <span className="text-xs text-slate-500">{team.members}</span>
              </button>
            ))}
          </div>
        </div>

        {/* Settings */}
        <div className="!mt-auto pt-4 border-t border-slate-200/50">
          <button className="w-full flex items-center gap-3 px-3 py-2.5 rounded-xl hover:bg-white/50 text-slate-700 transition-colors">
            <Settings className="w-5 h-5" />
            <span>설정</span>
          </button>
        </div>
      </aside>

      {/* Main Content */}
      <main className="ml-64 p-8">
        {/* Header */}
        <div className="flex items-center justify-between mb-8">
          <div>
            <h1 className="text-3xl text-slate-900 mb-2">대시보드</h1>
            <p className="text-slate-600">
              안녕하세요, 오늘도 좋은 하루 되세요! 👋
            </p>
          </div>
          <div className="flex items-center gap-3">
            <Button
              onClick={() => onNavigate("teams")}
              variant="outline"
              className="gap-2 border-slate-200 hover:border-indigo-300 hover:bg-indigo-50"
            >
              <Plus className="w-4 h-4" />팀 생성
            </Button>
            <Button className="gap-2 bg-gradient-to-r from-indigo-600 to-blue-600 hover:from-indigo-700 hover:to-blue-700 text-white shadow-lg shadow-indigo-500/30">
              <UserPlus className="w-4 h-4" />
              멤버 초대
            </Button>
          </div>
        </div>

        {/* Stats Cards */}
        <div className="grid grid-cols-1 md:grid-cols-4 gap-6 mb-8">
          <div className="glass-card rounded-2xl p-6">
            <div className="flex items-center justify-between mb-3">
              <div className="w-10 h-10 rounded-xl bg-indigo-100 flex items-center justify-center">
                <Calendar className="w-5 h-5 text-indigo-600" />
              </div>
              <TrendingUp className="w-4 h-4 text-green-500" />
            </div>
            <h3 className="text-2xl text-slate-900 mb-1">12</h3>
            <p className="text-sm text-slate-600">예정된 회의</p>
          </div>

          <div className="glass-card rounded-2xl p-6">
            <div className="flex items-center justify-between mb-3">
              <div className="w-10 h-10 rounded-xl bg-blue-100 flex items-center justify-center">
                <Users className="w-5 h-5 text-blue-600" />
              </div>
              <TrendingUp className="w-4 h-4 text-green-500" />
            </div>
            <h3 className="text-2xl text-slate-900 mb-1">24</h3>
            <p className="text-sm text-slate-600">팀 멤버</p>
          </div>

          <div className="glass-card rounded-2xl p-6">
            <div className="flex items-center justify-between mb-3">
              <div className="w-10 h-10 rounded-xl bg-purple-100 flex items-center justify-center">
                <MessageSquare className="w-5 h-5 text-purple-600" />
              </div>
              <Badge className="bg-purple-600 text-white text-xs">+5</Badge>
            </div>
            <h3 className="text-2xl text-slate-900 mb-1">8</h3>
            <p className="text-sm text-slate-600">활성 회의방</p>
          </div>

          <div className="glass-card rounded-2xl p-6">
            <div className="flex items-center justify-between mb-3">
              <div className="w-10 h-10 rounded-xl bg-pink-100 flex items-center justify-center">
                <Sparkles className="w-5 h-5 text-pink-600" />
              </div>
              <span className="text-xs text-slate-600">이번 주</span>
            </div>
            <h3 className="text-2xl text-slate-900 mb-1">18</h3>
            <p className="text-sm text-slate-600">AI 요약 생성</p>
          </div>
        </div>

        {/* Content Grid */}
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
          {/* Upcoming Events */}
          <div className="lg:col-span-2 glass-card rounded-2xl p-6">
            <div className="flex items-center justify-between mb-6">
              <h2 className="text-xl text-slate-900">다가오는 일정</h2>
              <Button
                onClick={() => onNavigate("calendar")}
                variant="ghost"
                size="sm"
                className="text-indigo-600 hover:text-indigo-700 hover:bg-indigo-50"
              >
                전체 보기 →
              </Button>
            </div>
            <div className="space-y-4">
              {upcomingEvents.map((event) => (
                <div
                  key={event.id}
                  className="flex items-center gap-4 p-4 rounded-xl bg-white border border-slate-100 hover:border-indigo-200 hover:shadow-md transition-all cursor-pointer"
                >
                  <div
                    className={`w-12 h-12 rounded-xl bg-${event.color}-100 flex items-center justify-center`}
                  >
                    <Calendar className={`w-6 h-6 text-${event.color}-600`} />
                  </div>
                  <div className="flex-1 min-w-0">
                    <h3 className="text-slate-900 mb-1">{event.title}</h3>
                    <div className="flex items-center gap-3 text-sm text-slate-600">
                      <span className="flex items-center gap-1">
                        <Clock className="w-4 h-4" />
                        {event.time}
                      </span>
                      <span>•</span>
                      <span>{event.team}</span>
                    </div>
                  </div>
                  <div className="flex items-center gap-2">
                    <div className="flex -space-x-2">
                      {[...Array(Math.min(event.attendees, 3))].map((_, i) => (
                        <Avatar
                          key={i}
                          className="w-8 h-8 border-2 border-white"
                        >
                          <AvatarFallback className="bg-indigo-100 text-indigo-700 text-xs">
                            {i + 1}
                          </AvatarFallback>
                        </Avatar>
                      ))}
                    </div>
                    {event.attendees > 3 && (
                      <span className="text-xs text-slate-600">
                        +{event.attendees - 3}
                      </span>
                    )}
                  </div>
                </div>
              ))}
            </div>
          </div>

          {/* Team Members */}
          <div className="glass-card rounded-2xl p-6">
            <div className="flex items-center justify-between mb-6">
              <h2 className="text-xl text-slate-900">팀 멤버</h2>
              <Button
                variant="ghost"
                size="sm"
                className="text-indigo-600 hover:text-indigo-700 hover:bg-indigo-50"
              >
                <UserPlus className="w-4 h-4" />
              </Button>
            </div>
            <div className="space-y-3">
              {teamMembers.map((member) => (
                <div
                  key={member.id}
                  className="flex items-center gap-3 p-3 rounded-xl hover:bg-white/50 transition-colors cursor-pointer"
                >
                  <div className="relative">
                    <Avatar className="w-10 h-10">
                      <AvatarFallback className="bg-gradient-to-br from-indigo-500 to-blue-500 text-white">
                        {member.avatar}
                      </AvatarFallback>
                    </Avatar>
                    <div
                      className={`absolute -bottom-0.5 -right-0.5 w-3 h-3 rounded-full border-2 border-white ${
                        member.status === "online"
                          ? "bg-green-500"
                          : member.status === "away"
                          ? "bg-yellow-500"
                          : "bg-slate-300"
                      }`}
                    ></div>
                  </div>
                  <div className="flex-1 min-w-0">
                    <p className="text-sm text-slate-900 truncate">
                      {member.name}
                    </p>
                    <p className="text-xs text-slate-600">{member.role}</p>
                  </div>
                  <button className="p-1 hover:bg-white rounded transition-colors">
                    <MoreHorizontal className="w-4 h-4 text-slate-400" />
                  </button>
                </div>
              ))}
            </div>
          </div>

          {/* Active Rooms */}
          <div className="lg:col-span-2 glass-card rounded-2xl p-6">
            <div className="flex items-center justify-between mb-6">
              <h2 className="text-xl text-slate-900">활성 회의방</h2>
              <Button
                onClick={() => onNavigate("chat")}
                variant="ghost"
                size="sm"
                className="text-indigo-600 hover:text-indigo-700 hover:bg-indigo-50"
              >
                전체 보기 →
              </Button>
            </div>
            <div className="space-y-3">
              {activeRooms.map((room) => (
                <div
                  key={room.id}
                  onClick={() => onNavigate("chat")}
                  className="flex items-center gap-4 p-4 rounded-xl bg-white border border-slate-100 hover:border-indigo-200 hover:shadow-md transition-all cursor-pointer"
                >
                  <div className="w-12 h-12 rounded-xl bg-gradient-to-br from-indigo-500 to-blue-500 flex items-center justify-center">
                    <MessageSquare className="w-6 h-6 text-white" />
                  </div>
                  <div className="flex-1 min-w-0">
                    <h3 className="text-slate-900 mb-1">{room.name}</h3>
                    <p className="text-sm text-slate-600">
                      {room.members}명 참여 중 • {room.team}
                    </p>
                  </div>
                  {room.unread > 0 && (
                    <Badge className="bg-indigo-600 text-white">
                      {room.unread}
                    </Badge>
                  )}
                </div>
              ))}
            </div>
          </div>

          {/* Recent Activity */}
          <div className="glass-card rounded-2xl p-6">
            <h2 className="text-xl text-slate-900 mb-6">최근 활동</h2>
            <div className="space-y-4">
              {recentActivities.map((activity) => (
                <div key={activity.id} className="flex gap-3">
                  <Avatar className="w-8 h-8 flex-shrink-0">
                    <AvatarFallback className="bg-slate-100 text-slate-700 text-xs">
                      {activity.user.charAt(0)}
                    </AvatarFallback>
                  </Avatar>
                  <div className="flex-1 min-w-0">
                    <p className="text-sm text-slate-900 mb-0.5">
                      <span>{activity.user}</span>{" "}
                      <span className="text-slate-600">{activity.action}</span>
                    </p>
                    <p className="text-sm text-indigo-600 mb-1">
                      {activity.target}
                    </p>
                    <p className="text-xs text-slate-500">{activity.time}</p>
                  </div>
                </div>
              ))}
            </div>
          </div>
        </div>
      </main>
    </div>
  );
}
