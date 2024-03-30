import { Link } from "react-router-dom";

const Purchase = () => {
  return (
    <div className="font-bold bg-gradient-to-br from-black to-indigo-900 w-[480px] h-screen mx-auto flex flex-col text-white px-4">
      <Link className="mt-4" to="/">
        back
      </Link>
      <div className="mt-4 flex flex-col">
        <div className="bg-red-100 h-[240px] rounded-xl relative">
          <img src={`../../public/images/taylor-eras.png`} alt="taylor swift" />
        </div>
        <div className="mt-4 place-self-center text-3xl">
          Taylor Swift The Eras Tour
        </div>
      </div>
      <div className="mt-36">
        <div className="flex justify-between">
          <div>Initial Buyer</div>
          <div>Vitalik Buterin</div>
        </div>
        <div className="flex justify-between mt-4">
          <div>Number of Tickets</div>
          <div className="flex">
            <div className="bg-slate-100 w-6 h-6 rounded-full text-black text-center mr-4">
              -
            </div>
            <div>1</div>
            <div className="bg-slate-100 w-6 h-6 rounded-full text-black text-center ml-4">
              +
            </div>
          </div>
        </div>
      </div>
      <button className="mt-24 bg-black border transition hover:-translate-y-1 hover:bg-white hover:text-black border-white px-6 py-3 mt-4 mr-2 text-white rounded-full">
        PURCHASE CONCERT
      </button>
    </div>
  );
};

export default Purchase;
