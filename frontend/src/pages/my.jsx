import { Link, useNavigate } from "react-router-dom";
import taylorImg from "../assets/images/taylor-eras.png";
import { CgProfile } from "react-icons/cg";
import backImg from "../assets/images/left-arrow.png";
import { useEffect, useState } from "react";
import jwtDecode from "jwt-decode";
import axios from "axios";

const BackendServer = "http://158.179.169.106:8081";

const My = () => {
  const navigate = useNavigate();
  const [username, setUsername] = useState('');
  const [ tickets, setTickets ] = useState([]);
  
  useEffect(()=>{
    if(!sessionStorage.getItem('accessToken')){
      navigate('/');
      alert('Need To Login First');
    }else {
      setUsername(jwtDecode(sessionStorage.getItem('accessToken')).username);
      axios.get(BackendServer+'/api/v1/tickets',{
        headers: {
            'Authorization': sessionStorage.getItem('accessToken')
        }
    })
    .then((resp)=>{
      console.log(resp.data);
      setTickets(resp.data);
    })
    .catch((err)=>{
      console.log(err);
    })
    }
  },[])
  //입장하기 눌렀을 때, NFT ID와 이름(카카오톡 오스)를 서버에 보냄.
  //서버에서 NFT가 본인 것인지 검증,
  //본인이 최초 구매자인지 확인. 그 결과를 받아서 보여줌
  const onClickVerifyTicket = async (id) => {
    const axiosConfig = {
      headers: {
        Authorization: sessionStorage.getItem("accessToken"),
      },
    };
    axios.post(BackendServer+'/api/v1/tickets/check', {id} , axiosConfig)
    .then((resp)=>{
      alert('Enjoy your Time!')
    })
    .catch((err)=>{console.log(err)})
  };

  return (
    <div className="bg-gradient-to-br from-black to-indigo-900 w-[480px] h-screen mx-auto flex flex-col text-white px-4">
      <Link className="mt-4 flex items-center mb-4" to="/">
        <img src={backImg} className="w-7 h-7"></img>
        <p className="text-2xl ml-2">BACK</p>
      </Link>
      <div className="mt-4 mb-12 flex ">
        <CgProfile size="60" />
        <div className="text-3xl ml-8 place-self-center">{username}'s Tickets</div>
      </div>
      <div>you can see your Tickets</div>
      <a className="text-cyan-600 underline" href='https://devnet.neonscan.org/address/0x827C8A45E80D3BD257924E266FDABC92cff0D983#tokenTransfers'>@Link Neon Scan</a>
      <div className="mt-4">
        {tickets.map((ticket)=>(
          <div key={ticket.id} className="bg-red-100 h-[240px] rounded-3xl relative">
          <img
            className="absolute top-0 left-0 w-full h-full object-cover rounded-3xl"
            src={taylorImg}
            alt="taylor swift"
          />
          <div className="bg-black/50 relative h-[240px] rounded-3xl"></div>
          <button
            onClick={()=>{onClickVerifyTicket(ticket.id)}}
            className="absolute top-20 left-20 pr-8 pb-8mt-4 bg-black border transition hover:-translate-y-1 hover:bg-white hover:text-black border-white px-6 py-3 mt-4 mr-2 text-white rounded-full"
          >
            Enter Concert
          </button>
          
        </div>
        ))}
      </div>
    </div>
  );
};

export default My;
