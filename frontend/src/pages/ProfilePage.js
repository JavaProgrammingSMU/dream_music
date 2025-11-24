import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { FiArrowLeft, FiUser, FiMail, FiLock, FiTrash2, FiSave } from 'react-icons/fi';
import '../styles/ProfilePage.css';

const ProfilePage = () => {
  const { user, updateUser, deleteAccount } = useAuth();
  const navigate = useNavigate();

  const [nickname, setNickname] = useState(user?.nickname || '');
  const [currentPassword, setCurrentPassword] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [deletePassword, setDeletePassword] = useState('');

  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState({ type: '', text: '' });
  const [showDeleteConfirm, setShowDeleteConfirm] = useState(false);

  const handleUpdateProfile = async (e) => {
    e.preventDefault();
    setMessage({ type: '', text: '' });

    if (newPassword && newPassword !== confirmPassword) {
      setMessage({ type: 'error', text: '새 비밀번호가 일치하지 않습니다.' });
      return;
    }

    if (newPassword && newPassword.length < 6) {
      setMessage({ type: 'error', text: '비밀번호는 6자 이상이어야 합니다.' });
      return;
    }

    if (newPassword && !currentPassword) {
      setMessage({ type: 'error', text: '현재 비밀번호를 입력해주세요.' });
      return;
    }

    setLoading(true);

    try {
      const updateData = {};
      if (nickname !== user.nickname) {
        updateData.nickname = nickname;
      }
      if (newPassword) {
        updateData.password = newPassword;
        updateData.currentPassword = currentPassword;
      }

      if (Object.keys(updateData).length > 0) {
        await updateUser(updateData);
        setMessage({ type: 'success', text: '프로필이 업데이트되었습니다.' });
        setCurrentPassword('');
        setNewPassword('');
        setConfirmPassword('');
      } else {
        setMessage({ type: 'info', text: '변경된 내용이 없습니다.' });
      }
    } catch (err) {
      setMessage({ type: 'error', text: err.response?.data?.error || '업데이트에 실패했습니다.' });
    } finally {
      setLoading(false);
    }
  };

  const handleDeleteAccount = async () => {
    if (!deletePassword) {
      setMessage({ type: 'error', text: '비밀번호를 입력해주세요.' });
      return;
    }

    setLoading(true);

    try {
      await deleteAccount(deletePassword);
      navigate('/login');
    } catch (err) {
      setMessage({ type: 'error', text: err.response?.data?.error || '계정 삭제에 실패했습니다.' });
      setLoading(false);
    }
  };

  return (
    <div className="profile-page">
      <div className="profile-container">
        <div className="profile-header">
          <button className="back-button" onClick={() => navigate('/')}>
            <FiArrowLeft />
          </button>
          <h1>프로필 설정</h1>
        </div>

        {message.text && (
          <div className={`message ${message.type}`}>
            {message.text}
          </div>
        )}

        <form onSubmit={handleUpdateProfile} className="profile-form">
          <div className="form-section">
            <h2>기본 정보</h2>

            <div className="form-group">
              <label>
                <FiMail className="label-icon" />
                이메일
              </label>
              <input type="email" value={user?.email} disabled />
            </div>

            <div className="form-group">
              <label>
                <FiUser className="label-icon" />
                닉네임
              </label>
              <input
                type="text"
                value={nickname}
                onChange={(e) => setNickname(e.target.value)}
                placeholder="닉네임"
                minLength={2}
                maxLength={20}
              />
            </div>
          </div>

          <div className="form-section">
            <h2>비밀번호 변경</h2>

            <div className="form-group">
              <label>
                <FiLock className="label-icon" />
                현재 비밀번호
              </label>
              <input
                type="password"
                value={currentPassword}
                onChange={(e) => setCurrentPassword(e.target.value)}
                placeholder="현재 비밀번호"
              />
            </div>

            <div className="form-group">
              <label>
                <FiLock className="label-icon" />
                새 비밀번호
              </label>
              <input
                type="password"
                value={newPassword}
                onChange={(e) => setNewPassword(e.target.value)}
                placeholder="새 비밀번호 (6자 이상)"
              />
            </div>

            <div className="form-group">
              <label>
                <FiLock className="label-icon" />
                새 비밀번호 확인
              </label>
              <input
                type="password"
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
                placeholder="새 비밀번호 확인"
              />
            </div>
          </div>

          <button type="submit" className="save-button" disabled={loading}>
            <FiSave />
            <span>{loading ? '저장 중...' : '변경사항 저장'}</span>
          </button>
        </form>

        <div className="danger-zone">
          <h2>위험 구역</h2>
          <p>계정을 삭제하면 모든 데이터가 영구적으로 삭제됩니다.</p>

          {!showDeleteConfirm ? (
            <button
              className="delete-button"
              onClick={() => setShowDeleteConfirm(true)}
            >
              <FiTrash2 />
              <span>계정 삭제</span>
            </button>
          ) : (
            <div className="delete-confirm">
              <input
                type="password"
                value={deletePassword}
                onChange={(e) => setDeletePassword(e.target.value)}
                placeholder="비밀번호를 입력하세요"
              />
              <div className="delete-actions">
                <button
                  className="cancel-button"
                  onClick={() => {
                    setShowDeleteConfirm(false);
                    setDeletePassword('');
                  }}
                >
                  취소
                </button>
                <button
                  className="confirm-delete-button"
                  onClick={handleDeleteAccount}
                  disabled={loading}
                >
                  삭제 확인
                </button>
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default ProfilePage;
