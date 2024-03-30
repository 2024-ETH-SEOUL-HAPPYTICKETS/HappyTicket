import { Link } from "react-router-dom";
import { useOutletContext } from "react-router-dom";
// import concertImg from "../../public/images/taylor-eras.png";
import { useEffect, useState } from "react";
import KakaoOAuthComponent from "../components/OAuth/KakaoOAuthComponent";
import OAuthComponent from "../components/OAuth/OAuthComponent";

import taylorImg from "../assets/images/taylor-eras.png";
import bonjoviImg from "../assets/images/bonjovi.jpeg";

const Home = () => {
  const { userName, setUserName, nftIds, setNftIds, newNft } =
    useOutletContext();

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
          <KakaoOAuthComponent></KakaoOAuthComponent>
        )}
        <OAuthComponent></OAuthComponent>
      </div>
      <div className=" bg-gradient-to-br from-indigo-900 to-purple-900 grow font-bold rounded-t-[36px]  px-4 py-4">
        <div className="text-white mb-4"> UPCOMING CONCERTS</div>
        <div className="">
          <Link
            to="/purchase"
            className="mt-4 flex flex-col transition hover:-translate-y-1"
          >
            <div className="bg-red-100 h-[240px] rounded-3xl relative">
              <img
                className="absolute top-0 left-0 w-full h-full object-cover rounded-3xl"
                src={taylorImg}
                alt="taylor swift"
              />
            </div>
          </Link>
        </div>
      </div>
    </div>
  );
};

export default Home;
