import { Link } from "react-router-dom";
import { useOutletContext } from "react-router-dom";
// import concertImg from "../../public/images/taylor-eras.png";
import { useEffect, useState } from "react";
import KakaoOAuthComponent from "../components/OAuth/KakaoOAuthComponent";
import OAuthComponent from "../components/OAuth/OAuthComponent";

const Home = () => {
  const { userName, setUserName, nftIds, setNftIds, newNft } =
    useOutletContext();

  //로그인 버튼 누르면 카카오톡 오스로 로그인하고 이름 가져와서 userName 상태변수에 저장
  const onClickAuth = async () => {
    console.log("kakao auth");
    //setUserName();에 저장
  };

  //서버에서 내가 소유한 NFT의 array 가져오기
  const getMyNfts = async () => {
    console.log("get my Nfts from server");
    //setNftIds(); 안에 서버에서 가져온 array 가져오기
  };

  //카카오톡 auth 처음으로 완료되거나, 보유 NFT 목록이 변경될 때 어레이 업데이트하기
  useEffect(() => {
    if (!userName) return;

    getMyNfts();
  }, [userName, newNft]);

  return (
    <div className="bg-gray-950 w-[480px] h-screen mx-auto flex flex-col">
      <div className="h-[240px] flex flex-col text-white">
        <div className=" font-bold place-self-center my-4 text-4xl ">
          HAPPY TICKET
        </div>
        {userName ? (
          <Link className="place-self-end mx-4" to="/my">
            {userName}
          </Link>
        ) : (
          <button onClick={onClickAuth} className="place-self-end mx-4">
            Login
          </button>
        )}
        <KakaoOAuthComponent></KakaoOAuthComponent>
        <OAuthComponent></OAuthComponent>
        {/* <Link className="place-self-end mx-4" to="/my">
          Login
        </Link> */}
      </div>
      <div className=" bg-gradient-to-br from-indigo-900 to-purple-900 grow font-bold rounded-t-[36px]  px-4 py-4">
        <div className="text-white mb-4"> UPCOMING CONCERTS</div>
        <div className="">
          <Link to="/purchase" className="mt-4 flex flex-col">
            <div className="bg-red-100 h-[240px] rounded-xl relative">
              {/* <img
                className="absolute top-0 left-0 w-full h-full object-cover"
                src={concertImg}
                alt="taylor swift"
              /> */}
            </div>
          </Link>
          <Link
            to="/purchase"
            className="bg-white h-36 mt-4 mb-4 px-4 text-7xl flex justify-center items-center rounded-3xl transition hover:-translate-y-1"
          >
            CONCERT2
          </Link>
        </div>
      </div>
    </div>
  );
};

export default Home;
