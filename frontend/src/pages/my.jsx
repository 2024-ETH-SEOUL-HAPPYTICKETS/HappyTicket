import { Link } from "react-router-dom";

const My = () => {
  return (
    <div className="bg-gradient-to-br from-black to-indigo-900 w-[480px] h-screen mx-auto flex flex-col text-white px-4">
      <Link className="mt-4" to="/">
        back
      </Link>
      <div className="mt-4 mb-12 flex ">
        <div className="rounded-full bg-red-300 w-16 h-16"></div>
        <div className="text-3xl ml-8 place-self-center">Vitalik's Tickets</div>
      </div>
      <div className="mt-4">
        <div className="bg-white h-36 mb-4 px-4 text-7xl flex justify-center items-center rounded-3xl text-black">
          CONCERT1
        </div>
        <div className="bg-white h-36 mb-4 px-4 text-7xl flex justify-center items-center rounded-3xl  text-black">
          CONCERT2
        </div>
      </div>
    </div>
  );
};

export default My;
