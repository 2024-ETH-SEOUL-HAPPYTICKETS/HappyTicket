import React, { useEffect } from 'react'
import kakao from '../../assets/images/kakao.png'
import { useNavigate } from 'react-router-dom';
import jwtDecode from 'jwt-decode';
import axios from 'axios';


const KakaoOAuthComponent = () => {

  const handleLogin = () => {
    const client_id = `277a841600aba14020ce1835979cd54a`;
    const redirect_id = "https://localhost:3000/login";
    const response_type = "code";
    const KakaoURL = `https://kauth.kakao.com/oauth/authorize?response_type=${response_type}&client_id=${client_id}&redirect_uri=${redirect_id}`
    sessionStorage.setItem("provider","kakao");
    window.location.href = KakaoURL;
  }

  return (
    <img src={kakao} alt='kakao_icon' className="login-icon cursor" onClick={handleLogin}></img>
  )
}

export default KakaoOAuthComponent