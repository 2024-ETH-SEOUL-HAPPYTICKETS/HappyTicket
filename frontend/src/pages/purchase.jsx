import { Link } from "react-router-dom";

import concertImg from "../assets/images/taylor-eras.png";
import { useState } from "react";
import { useOutletContext } from "react-router-dom";

const Purchase = () => {
  const { userName, setUserName, nftIds, setNftIds } = useOutletContext();

  const [ticketCount, setTicketCount] = useState(1);
  const [isClicked, setIsClicked] = useState(0);

  //구매하기 눌렀을 때 NFT 발행 위한 정보 서버에 보냄(구매자 이름, 수량), 서버에서 토큰id 받아서 NftIds array에 추가
  const onClickPurchase = async () => {
    setIsClicked(1);
    //setNftIds( );
    console.log("is owner of NFT");
    console.log("is first buyer of NFT");
  };

  return (
    <div className="relative font-bold bg-gradient-to-br from-black to-indigo-900 w-[480px] h-screen mx-auto flex flex-col text-white px-4">
      <Link className="mt-4" to="/">
        back
      </Link>
      <div className="mt-4 flex flex-col">
        <div className="bg-red-100 h-[240px] rounded-3xl relative">
          <img
            className="absolute top-0 left-0 w-full h-full object-cover rounded-3xl"
            src={concertImg}
            alt="taylor swift"
          />
        </div>
        <div className="mt-4 place-self-center text-3xl">
          Taylor Swift The Eras Tour
        </div>
      </div>
      {isClicked == 1 ? (
        <>
          <div className="mt-36 mb-36">
            <div className="flex justify-between">
              <div>{userName}</div>
              <div>purchased {ticketCount} ticket(s) successful</div>
            </div>
          </div>
          <Link
            to="/my"
            className="mt-4 bg-black border transition hover:-translate-y-1 hover:bg-white hover:text-black border-white px-6 py-3 mr-2 text-white rounded-full text-center "
          >
            VIEW MY TICKETS
          </Link>
        </>
      ) : (
        <>
          <div className="mt-36 mb-36">
            <div className="flex justify-between">
              <div>Initial Buyer</div>
              <div>Alice Buterin</div>
            </div>
            <div className="flex justify-between mt-4">
              <div>Number of Tickets</div>
              <div className="flex">
                <button
                  onClick={() => setTicketCount(ticketCount - 1)}
                  className="bg-slate-100 w-6 h-6 rounded-full text-black text-center mr-4"
                >
                  -
                </button>
                <div>{ticketCount}</div>
                <button
                  onClick={() => setTicketCount(ticketCount + 1)}
                  className="bg-slate-100 w-6 h-6 rounded-full text-black text-center ml-4"
                >
                  +
                </button>
              </div>
            </div>
          </div>
          <button
            onClick={onClickPurchase}
            className="mt-4 bg-black border transition hover:-translate-y-1 hover:bg-white hover:text-black border-white px-6 py-3 mt-4 mr-2 text-white rounded-full"
          >
            PURCHASE CONCERT
          </button>
        </>
      )}
    </div>
  );
};

export default Purchase;
