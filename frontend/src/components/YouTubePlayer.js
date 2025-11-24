import React from 'react';
import { FiMusic, FiExternalLink } from 'react-icons/fi';
import '../styles/YouTubePlayer.css';

const YouTubePlayer = ({ videoId, title, artist, reason }) => {
  if (!videoId) {
    return (
      <div className="youtube-player-container">
        <div className="youtube-placeholder">
          <FiMusic size={50} />
          <p>꿈을 입력하면 어울리는 음악을 추천해드립니다</p>
        </div>
      </div>
    );
  }

  return (
    <div className="youtube-player-container">
      <div className="music-info">
        <div className="music-icon">
          <FiMusic />
        </div>
        <div className="music-details">
          <h3>{title}</h3>
          <p>{artist}</p>
        </div>
        <a
          href={`https://www.youtube.com/watch?v=${videoId}`}
          target="_blank"
          rel="noopener noreferrer"
          className="external-link"
        >
          <FiExternalLink />
        </a>
      </div>

      {reason && (
        <div className="music-reason">
          <p>{reason}</p>
        </div>
      )}

      <div className="youtube-embed">
        <iframe
          src={`https://www.youtube.com/embed/${videoId}?autoplay=0&rel=0`}
          title={`${title} - ${artist}`}
          frameBorder="0"
          allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
          allowFullScreen
        ></iframe>
      </div>
    </div>
  );
};

export default YouTubePlayer;
