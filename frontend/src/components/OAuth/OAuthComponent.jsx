import axios from "axios";
import jwtDecode from "jwt-decode";
import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";

const BackendServer = "http://158.179.169.106:8081";

const OAuthComponent = () => {
  const navigate = useNavigate();

  useEffect(() => {
    const searchParams = new URLSearchParams(window.location.search);
    let credentailcode;
    if (sessionStorage.getItem("provider") == "kakao") {
      credentailcode = searchParams.get("code");
      sessionStorage.removeItem("provider");
      axios
        .post(BackendServer + `/api/v1/oauth`, {
          kakaoCode: credentailcode,
        })
        .then((resp) => {
          console.log(jwtDecode(resp.data).username);
          sessionStorage.setItem("accessToken", "Bearer "+resp.data);
          navigate("/");
        })
        .catch((err) => {
          console.log(err);
          navigate("/");
          alert("로그인 실패");
        });
    }
  }, []);

  return <></>;
};

export default OAuthComponent;
