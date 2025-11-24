import React from 'react';
import { FiPlus, FiClock, FiTrash2, FiX } from 'react-icons/fi';
import '../styles/Sidebar.css';

const Sidebar = ({
  isOpen,
  onClose,
  dreamHistory,
  onSelectDream,
  onNewDream,
  onDeleteDream,
  selectedDreamId
}) => {
  const formatDate = (dateString) => {
    const date = new Date(dateString);
    const now = new Date();
    const diffDays = Math.floor((now - date) / (1000 * 60 * 60 * 24));

    if (diffDays === 0) {
      return '오늘';
    } else if (diffDays === 1) {
      return '어제';
    } else if (diffDays < 7) {
      return `${diffDays}일 전`;
    } else {
      return date.toLocaleDateString('ko-KR', {
        month: 'short',
        day: 'numeric'
      });
    }
  };

  const truncateText = (text, maxLength = 40) => {
    if (text.length <= maxLength) return text;
    return text.substring(0, maxLength) + '...';
  };

  return (
    <>
      <div className={`sidebar-overlay ${isOpen ? 'active' : ''}`} onClick={onClose}></div>
      <aside className={`sidebar ${isOpen ? 'open' : ''}`}>
        <div className="sidebar-header">
          <h2>꿈 기록</h2>
          <button className="close-button" onClick={onClose}>
            <FiX />
          </button>
        </div>

        <button className="new-dream-button" onClick={onNewDream}>
          <FiPlus />
          <span>새로운 꿈 해석</span>
        </button>

        <div className="dream-history">
          {dreamHistory.length === 0 ? (
            <div className="empty-history">
              <FiClock size={40} />
              <p>아직 기록된 꿈이 없습니다</p>
              <span>새로운 꿈을 입력해보세요</span>
            </div>
          ) : (
            <ul className="dream-list">
              {dreamHistory.map((dream) => (
                <li
                  key={dream.id}
                  className={`dream-item ${selectedDreamId === dream.id ? 'selected' : ''}`}
                  onClick={() => onSelectDream(dream)}
                >
                  <div className="dream-item-content">
                    <p className="dream-preview">{truncateText(dream.dreamContent)}</p>
                    <span className="dream-date">{formatDate(dream.createdAt)}</span>
                  </div>
                  <button
                    className="delete-button"
                    onClick={(e) => {
                      e.stopPropagation();
                      onDeleteDream(dream.id);
                    }}
                  >
                    <FiTrash2 />
                  </button>
                </li>
              ))}
            </ul>
          )}
        </div>
      </aside>
    </>
  );
};

export default Sidebar;
