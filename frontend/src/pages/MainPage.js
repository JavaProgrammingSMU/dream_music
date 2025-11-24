import React, { useState, useEffect } from 'react';
import Header from '../components/Header';
import Sidebar from '../components/Sidebar';
import DreamResult from '../components/DreamResult';
import YouTubePlayer from '../components/YouTubePlayer';
import { dreamAPI } from '../services/api';
import { FiSend, FiLoader } from 'react-icons/fi';
import '../styles/MainPage.css';

const MainPage = () => {
  const [sidebarOpen, setSidebarOpen] = useState(false);
  const [dreamContent, setDreamContent] = useState('');
  const [currentResult, setCurrentResult] = useState(null);
  const [dreamHistory, setDreamHistory] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  useEffect(() => {
    loadDreamHistory();
  }, []);

  const loadDreamHistory = async () => {
    try {
      const response = await dreamAPI.getHistory();
      setDreamHistory(response.data);
    } catch (err) {
      console.error('Failed to load dream history:', err);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!dreamContent.trim() || loading) return;

    setLoading(true);
    setError('');

    try {
      const response = await dreamAPI.interpret(dreamContent.trim());
      setCurrentResult(response.data);
      setDreamContent('');
      loadDreamHistory();
    } catch (err) {
      setError(err.response?.data?.error || '꿈 해석에 실패했습니다. 다시 시도해주세요.');
    } finally {
      setLoading(false);
    }
  };

  const handleSelectDream = (dream) => {
    setCurrentResult(dream);
    setSidebarOpen(false);
  };

  const handleNewDream = () => {
    setCurrentResult(null);
    setDreamContent('');
    setSidebarOpen(false);
  };

  const handleDeleteDream = async (id) => {
    try {
      await dreamAPI.delete(id);
      loadDreamHistory();
      if (currentResult?.id === id) {
        setCurrentResult(null);
      }
    } catch (err) {
      console.error('Failed to delete dream:', err);
    }
  };

  return (
    <div className="main-page">
      <Header onMenuClick={() => setSidebarOpen(true)} />

      <Sidebar
        isOpen={sidebarOpen}
        onClose={() => setSidebarOpen(false)}
        dreamHistory={dreamHistory}
        onSelectDream={handleSelectDream}
        onNewDream={handleNewDream}
        onDeleteDream={handleDeleteDream}
        selectedDreamId={currentResult?.id}
      />

      <main className="main-content">
        <div className="content-wrapper">
          <div className="dream-section">
            <div className="dream-input-area">
              <h2>꿈을 이야기해주세요</h2>
              <form onSubmit={handleSubmit} className="dream-form">
                <div className="textarea-wrapper">
                  <textarea
                    value={dreamContent}
                    onChange={(e) => setDreamContent(e.target.value)}
                    placeholder="어젯밤 꾼 꿈을 자세히 적어주세요. 등장인물, 장소, 감정, 행동 등을 포함하면 더 정확한 해석을 받을 수 있습니다..."
                    disabled={loading}
                    rows={4}
                  />
                  <button
                    type="submit"
                    className="submit-button"
                    disabled={!dreamContent.trim() || loading}
                  >
                    {loading ? <FiLoader className="spin" /> : <FiSend />}
                  </button>
                </div>
                {error && <div className="error-message">{error}</div>}
              </form>
            </div>

            <div className="result-area">
              <DreamResult result={currentResult} />
            </div>
          </div>

          <div className="music-section">
            <h3>추천 음악</h3>
            <YouTubePlayer
              videoId={currentResult?.youtubeVideoId}
              title={currentResult?.recommendedMusicTitle}
              artist={currentResult?.recommendedMusicArtist}
            />
          </div>
        </div>
      </main>
    </div>
  );
};

export default MainPage;
