import { Link } from "react-router-dom";

const Home = () => {
  return (
    <div className="bg-gray-950 w-[480px] h-screen mx-auto flex flex-col">
      <div className="h-[240px] flex flex-col text-white">
        <div className=" font-bold place-self-center my-4 text-4xl ">
          HAPPY TICKET
        </div>
        <Link className="place-self-end mx-4" to="/my">
          Login
        </Link>
      </div>
      <div className=" bg-gradient-to-br from-indigo-900 to-purple-900 grow font-bold rounded-t-[36px]  px-4 py-4">
        <div className="text-white mb-4"> UPCOMING CONCERTS</div>
        <div className="">
          <Link
            to="/purchase"
            className="bg-white h-36 mb-4 px-4 text-7xl flex justify-center items-center rounded-3xl "
          >
            CONCERT1
          </Link>
          <Link
            to="/purchase"
            className="bg-white h-36 mb-4 px-4 text-7xl flex justify-center items-center rounded-3xl "
          >
            CONCERT2
          </Link>
        </div>
      </div>
    </div>
  );
};

export default Home;
