import { Link } from "react-router-dom";
import taylorImg from "../assets/images/taylor-eras.png";
import { CgProfile } from "react-icons/cg";

const My = () => {
  //입장하기 눌렀을 때, NFT ID와 이름(카카오톡 오스)를 서버에 보냄.
  //서버에서 NFT가 본인 것인지 검증,
  //본인이 최초 구매자인지 확인. 그 결과를 받아서 보여줌
  const onClickVerifyTicket = async () => {
    console.log("is owner of NFT");
    console.log("is first buyer of NFT");
  };

  return (
    <div className="bg-gradient-to-br from-black to-indigo-900 w-[480px] h-screen mx-auto flex flex-col text-white px-4">
      <Link className="mt-4" to="/">
        <img></img>
      </Link>
      <div className="mt-4 mb-12 flex ">
        <CgProfile size="60" />
        <div className="text-3xl ml-8 place-self-center">Alice's Tickets</div>
      </div>
      <div className="mt-4">
        <div className="bg-red-100 h-[240px] rounded-3xl relative">
          <img
            className="absolute top-0 left-0 w-full h-full object-cover rounded-3xl"
            src={taylorImg}
            alt="taylor swift"
          />
          <div className="bg-black/50 relative h-[240px] rounded-3xl"></div>
          <button
            onClick={onClickVerifyTicket}
            className="absolute top-20 left-20 pr-8 pb-8mt-4 bg-black border transition hover:-translate-y-1 hover:bg-white hover:text-black border-white px-6 py-3 mt-4 mr-2 text-white rounded-full"
          >
            Enter Concert
          </button>
          <div className="absolute right-0 bottom-16 pr-8 pb-8 font-bold ">
            <div>is Owner</div>
            <div>is Initial Buyer</div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default My;
