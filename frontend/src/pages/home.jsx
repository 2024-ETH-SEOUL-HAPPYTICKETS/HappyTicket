import { Link, useNavigate } from "react-router-dom";
import { useOutletContext } from "react-router-dom";
// import concertImg from "../../public/images/taylor-eras.png";
import { useEffect, useState } from "react";
import KakaoOAuthComponent from "../components/OAuth/KakaoOAuthComponent";
import OAuthComponent from "../components/OAuth/OAuthComponent";
import jwtDecode from "jwt-decode";
import search from "../assets/images/search.png";
import ticketImg from "../assets/images/movies.png";
import BTS from "../assets/images/BTS.png";

import taylorImg from "../assets/images/taylor-eras.png";
import bonjoviImg from "../assets/images/bonjovi.jpeg";
import Footer from "../components/footer/Footer";

const Home = () => {
  const navigate = useNavigate();
  const [isHovered, setIsHovered] = useState(false);
  const { userName, setUserName, nftIds, setNftIds, newNft } =
    useOutletContext();

  const handleMouseEnter = () => {
    setIsHovered(true);
  };

  // 버튼 내용을 원래 내용으로 변경하는 함수
  const handleMouseLeave = () => {
    setIsHovered(false);
  };

  //서버에서 내가 소유한 NFT의 array 가져오기
  const getMyNfts = async () => {
    console.log("get my Nfts from server");
    //setNftIds(); 안에 서버에서 가져온 array 가져오기
  };

  //카카오톡 auth 처음으로 완료되거나, 보유 NFT 목록이 변경될 때 어레이 업데이트하기
  useEffect(() => {
    if (sessionStorage.getItem("accessToken")) {
      setUserName(jwtDecode(sessionStorage.getItem("accessToken")).username);
    }

  }, [userName]);

  return (
    <div className="bg-black w-[480px] h-screen mx-auto flex flex-col">
      <OAuthComponent></OAuthComponent>
      <div className="h-[240px] flex flex-col text-white">
        <div className=" font-bold place-self-center my-4 text-[40px] flex justify-center items-center">
          <p>HAPPY</p>
          <img src={ticketImg} className="w-16 h-16 mx-4"></img>
          <p>Ticket</p>
        </div>
        <div className="w-full h-16 flex items-center justify-between px-6 bg-gray-900 mb-5">
          <button className="text-black border-2 ml-4 p-1 w-20 rounded-full bg-white" onClick={()=>{navigate('/')}}>HOME</button>
          <button className="text-black border-2 p-1 w-20 rounded-full bg-white" onClick={()=>{navigate('/my')}}>MY</button>
        {sessionStorage.getItem("accessToken") ? (
            <button className="text-black border-2 p-1 w-20 rounded-full bg-white" onClick={()=>{
              sessionStorage.removeItem('accessToken');
              setUserName('');
            }}>LogOut</button>
          ) : (
            <KakaoOAuthComponent></KakaoOAuthComponent>
          )}
        </div>
        <div className="flex justify-between">
          <div className="flex w-full flex-row">
            <img src={search} className="w-10 h-10 ml-4"></img>
            <input
              className="w-full rounded-full ml-4 mr-8 cursor-not-allowed"
              type="text"
              placeholder="                  Will be available soon!"
              disabled
            ></input>
          </div>
        </div>
      </div>
      <div className=" bg-gradient-to-br from-sky-700 to-purple-900 grow font-bold rounded-t-[36px]  px-4 py-2">
        <div className="text-white mb-4 mt-4 text-xl underline">
          {" "}
          UPCOMING CONCERTS
        </div>
        <div className="">
          <Link
            to="/purchase"
            className="mt-4 flex flex-col transition group hover:-translate-y-1"
          >
            <div className="bg-transparent h-[240px] rounded-3xl relative">
              <img
                className="absolute top-0 left-0 w-full h-full object-cover rounded-3xl" 
                src={BTS}
                alt="BTS"
              />
              <p className="text-white  text-5xl absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 text-center w-full">
                B.T.S
              </p>
            </div>
          </Link>

          <div className="bg-black h-[240px] rounded-3xl relative mt-5">
            <img
              className="absolute top-0 left-0 w-full h-full object-cover rounded-3xl opacity-20"
              src={taylorImg}
              alt="taylor swift"
            />
            <p className="text-white text-4xl absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 text-center w-full">
              Not Open Yet
            </p>
          </div>
        </div>
        <Footer></Footer>
      </div>
    </div>
  );
};

export default Home;
