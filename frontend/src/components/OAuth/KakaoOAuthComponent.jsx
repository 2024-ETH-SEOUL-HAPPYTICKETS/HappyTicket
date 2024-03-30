import React, { useEffect } from "react";
import kakao from "../../assets/images/kakaoMedium.png";
import { useNavigate } from "react-router-dom";
import jwtDecode from "jwt-decode";
import axios from "axios";

const KakaoOAuthComponent = () => {
  const handleLogin = () => {
    const client_id = `277a841600aba14020ce1835979cd54a`;
    const redirect_id = "http://localhost:3000";
    const response_type = "code";
    const KakaoURL = `https://kauth.kakao.com/oauth/authorize?response_type=${response_type}&client_id=${client_id}&redirect_uri=${redirect_id}`;
    sessionStorage.setItem("provider", "kakao");
    window.location.href = KakaoURL;
    //setUserName();에 사용자 이름 저장
  };

  return (
    <img
      src={kakao}
      alt="kakao_icon"
      className="cursor-pointer  w-16 place-self-end mx-4 "
      onClick={handleLogin}
    ></img>
  );
};

export default KakaoOAuthComponent;
