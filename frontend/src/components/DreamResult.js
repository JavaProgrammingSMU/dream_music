import React, { useState } from 'react';
import { FiSun, FiMoon, FiHeart } from 'react-icons/fi';
import '../styles/DreamResult.css';

const DreamResult = ({ result }) => {
  const [activeTab, setActiveTab] = useState('eastern');

  if (!result) {
    return (
      <div className="dream-result-placeholder">
        <div className="placeholder-icon">
          <FiMoon size={60} />
        </div>
        <h3>당신의 꿈을 입력해주세요</h3>
        <p>동양과 서양의 관점에서 꿈을 해석하고,<br />현재 심리 상태를 분석해드립니다.</p>
      </div>
    );
  }

  const tabs = [
    { id: 'eastern', label: '동양 해몽', icon: <FiSun /> },
    { id: 'western', label: '서양 해몽', icon: <FiMoon /> },
    { id: 'psychological', label: '심리 분석', icon: <FiHeart /> },
  ];

  const getContent = () => {
    switch (activeTab) {
      case 'eastern':
        return result.easternInterpretation;
      case 'western':
        return result.westernInterpretation;
      case 'psychological':
        return result.psychologicalAnalysis;
      default:
        return '';
    }
  };

  return (
    <div className="dream-result">
      <div className="dream-content-display">
        <h4>입력한 꿈</h4>
        <p>{result.dreamContent}</p>
      </div>

      <div className="result-tabs">
        {tabs.map((tab) => (
          <button
            key={tab.id}
            className={`tab-button ${activeTab === tab.id ? 'active' : ''}`}
            onClick={() => setActiveTab(tab.id)}
          >
            {tab.icon}
            <span>{tab.label}</span>
          </button>
        ))}
      </div>

      <div className="result-content">
        <div className={`content-panel ${activeTab}`}>
          {getContent().split('\n').map((paragraph, index) => (
            <p key={index}>{paragraph}</p>
          ))}
        </div>
      </div>
    </div>
  );
};

export default DreamResult;
