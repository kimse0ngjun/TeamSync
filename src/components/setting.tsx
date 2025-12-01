import { useState } from "react";
import { Button } from "./ui/button";
import { Input } from "./ui/input";
import { Label } from "./ui/label";
import { Switch } from "./ui/switch";
import { Avatar, AvatarFallback, AvatarImage } from "./ui/avatar";
import {
  User,
  Bell,
  Shield,
  Palette,
  Camera,
  LogOut,
  ChevronRight,
} from "lucide-react";
import "./setting.css";

export default function SettingsPage() {
  const [activeTab, setActiveTab] = useState("profile");
  const [notifications, setNotifications] = useState({
    email: true,
    push: true,
    marketing: false,
  });

  const menuItems = [
    { id: "profile", label: "프로필 설정", icon: User },
    { id: "notifications", label: "알림 설정", icon: Bell },
    { id: "security", label: "보안 및 로그인", icon: Shield },
    { id: "appearance", label: "화면 설정", icon: Palette },
  ];

  return (
    <div className="settings-container">
      <div className="settings-layout">
        {/* Sidebar */}
        <div className="settings-sidebar">
          {menuItems.map((item) => (
            <button
              key={item.id}
              onClick={() => setActiveTab(item.id)}
              className={`sidebar-item ${
                activeTab === item.id ? "active" : ""
              }`}
            >
              <item.icon className="w-5 h-5" />
              {item.label}
            </button>
          ))}
          <div className="h-px bg-slate-100 my-2" />
          <button className="sidebar-item text-red-500 hover:bg-red-50 hover:text-red-600">
            <LogOut className="w-5 h-5" />
            로그아웃
          </button>
        </div>

        {/* Content Area */}
        <div className="settings-content">
          {activeTab === "profile" && (
            <div className="animate-in fade-in slide-in-from-bottom-4 duration-500">
              <div className="section-header">
                <h2 className="section-title">프로필 설정</h2>
                <p className="section-description">
                  개인 정보와 프로필 사진을 관리하세요.
                </p>
              </div>

              <div className="profile-header">
                <div className="profile-avatar-wrapper">
                  <Avatar className="w-24 h-24">
                    <AvatarImage src="https://github.com/shadcn.png" />
                    <AvatarFallback>CN</AvatarFallback>
                  </Avatar>
                  <button className="avatar-upload-btn">
                    <Camera className="w-4 h-4" />
                  </button>
                </div>
                <div>
                  <h3 className="text-lg font-medium text-slate-900">홍길동</h3>
                  <p className="text-slate-500">UX Designer</p>
                </div>
              </div>

              <div className="space-y-6">
                <div className="grid grid-cols-2 gap-6">
                  <div className="form-group">
                    <Label className="form-label">이름</Label>
                    <Input defaultValue="홍길동" className="form-input" />
                  </div>
                  <div className="form-group">
                    <Label className="form-label">직책</Label>
                    <Input defaultValue="UX Designer" className="form-input" />
                  </div>
                </div>

                <div className="form-group">
                  <Label className="form-label">이메일</Label>
                  <Input
                    defaultValue="hong@example.com"
                    className="form-input"
                  />
                </div>

                <div className="form-group">
                  <Label className="form-label">소개</Label>
                  <textarea
                    className="form-textarea"
                    placeholder="자기소개를 입력하세요..."
                    defaultValue="안녕하세요, 사용자 경험을 디자인하는 홍길동입니다."
                  />
                </div>

                <div className="form-actions">
                  <Button variant="outline" className="btn-secondary">
                    취소
                  </Button>
                  <Button className="btn-primary">변경사항 저장</Button>
                </div>
              </div>
            </div>
          )}

          {activeTab === "notifications" && (
            <div className="animate-in fade-in slide-in-from-bottom-4 duration-500">
              <div className="section-header">
                <h2 className="section-title">알림 설정</h2>
                <p className="section-description">
                  이메일 및 푸시 알림 수신 여부를 설정하세요.
                </p>
              </div>

              <div className="space-y-2">
                <div className="toggle-row">
                  <div className="toggle-info">
                    <div className="toggle-label">이메일 알림</div>
                    <div className="toggle-description">
                      중요한 업데이트와 활동 내역을 이메일로 받습니다.
                    </div>
                  </div>
                  <Switch
                    checked={notifications.email}
                    onCheckedChange={(checked) =>
                      setNotifications({ ...notifications, email: checked })
                    }
                  />
                </div>

                <div className="toggle-row">
                  <div className="toggle-info">
                    <div className="toggle-label">푸시 알림</div>
                    <div className="toggle-description">
                      새로운 메시지와 일정을 실시간으로 알림 받습니다.
                    </div>
                  </div>
                  <Switch
                    checked={notifications.push}
                    onCheckedChange={(checked) =>
                      setNotifications({ ...notifications, push: checked })
                    }
                  />
                </div>

                <div className="toggle-row">
                  <div className="toggle-info">
                    <div className="toggle-label">마케팅 정보 수신</div>
                    <div className="toggle-description">
                      새로운 기능과 이벤트 소식을 받습니다.
                    </div>
                  </div>
                  <Switch
                    checked={notifications.marketing}
                    onCheckedChange={(checked) =>
                      setNotifications({ ...notifications, marketing: checked })
                    }
                  />
                </div>
              </div>
            </div>
          )}

          {activeTab === "security" && (
            <div className="animate-in fade-in slide-in-from-bottom-4 duration-500">
              <div className="section-header">
                <h2 className="section-title">보안 및 로그인</h2>
                <p className="section-description">
                  비밀번호를 변경하고 계정 보안을 강화하세요.
                </p>
              </div>

              <div className="space-y-6">
                <div className="form-group">
                  <Label className="form-label">현재 비밀번호</Label>
                  <Input type="password" className="form-input" />
                </div>

                <div className="grid grid-cols-2 gap-6">
                  <div className="form-group">
                    <Label className="form-label">새 비밀번호</Label>
                    <Input type="password" className="form-input" />
                  </div>
                  <div className="form-group">
                    <Label className="form-label">새 비밀번호 확인</Label>
                    <Input type="password" className="form-input" />
                  </div>
                </div>

                <div className="bg-slate-50 p-4 rounded-lg border border-slate-200 mt-6">
                  <div className="flex items-center justify-between">
                    <div>
                      <h4 className="font-medium text-slate-900 mb-1">
                        2단계 인증
                      </h4>
                      <p className="text-sm text-slate-500">
                        로그인 시 추가 인증을 통해 계정을 보호합니다.
                      </p>
                    </div>
                    <Button variant="outline">설정하기</Button>
                  </div>
                </div>

                <div className="form-actions">
                  <Button className="btn-primary">비밀번호 변경</Button>
                </div>
              </div>
            </div>
          )}

          {activeTab === "appearance" && (
            <div className="animate-in fade-in slide-in-from-bottom-4 duration-500">
              <div className="section-header">
                <h2 className="section-title">화면 설정</h2>
                <p className="section-description">
                  앱의 테마와 화면 구성을 설정하세요.
                </p>
              </div>

              <div className="form-group">
                <Label className="form-label">테마 선택</Label>
                <div className="theme-grid">
                  <div className="theme-card active">
                    <div className="theme-preview" />
                    <div className="theme-name">라이트 모드</div>
                  </div>
                  <div className="theme-card">
                    <div className="theme-preview dark" />
                    <div className="theme-name">다크 모드</div>
                  </div>
                  <div className="theme-card">
                    <div className="theme-preview system" />
                    <div className="theme-name">시스템 설정</div>
                  </div>
                </div>
              </div>

              <div className="form-group mt-8">
                <Label className="form-label">글자 크기</Label>
                <div className="flex items-center gap-4 p-4 bg-slate-50 rounded-lg border border-slate-200">
                  <span className="text-sm">가</span>
                  <input
                    type="range"
                    className="flex-1 h-2 bg-slate-200 rounded-lg appearance-none cursor-pointer"
                  />
                  <span className="text-xl">가</span>
                </div>
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
