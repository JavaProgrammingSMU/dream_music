import React from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { FiMenu, FiUser, FiLogOut, FiMoon } from 'react-icons/fi';
import '../styles/Header.css';

const Header = ({ onMenuClick }) => {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <header className="header">
      <div className="header-left">
        <button className="menu-button" onClick={onMenuClick}>
          <FiMenu />
        </button>
        <div className="logo">
          <FiMoon className="logo-icon" />
          <span>꿈 해몽</span>
        </div>
      </div>

      <div className="header-right">
        <div className="user-info">
          <span className="user-nickname">{user?.nickname}</span>
        </div>
        <button className="header-button" onClick={() => navigate('/profile')}>
          <FiUser />
        </button>
        <button className="header-button logout" onClick={handleLogout}>
          <FiLogOut />
        </button>
      </div>
    </header>
  );
};

export default Header;
