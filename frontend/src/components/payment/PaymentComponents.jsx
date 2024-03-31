import React, { useEffect } from "react";
import axios from "axios";
import jwtDecode from "jwt-decode";

const BackendServer = "http://158.179.169.106:8081";

const PaymentComponents = ({ amount }) => {
  useEffect(() => {
    const jquery = document.createElement("script");
    jquery.src = "http://code.jquery.com/jquery-1.12.4.min.js";
    const iamport = document.createElement("script");
    iamport.src = "http://cdn.iamport.kr/js/iamport.payment-1.1.7.js";
    document.head.appendChild(jquery);
    document.head.appendChild(iamport);
    return () => {
      document.head.removeChild(jquery);
      document.head.removeChild(iamport);
    };
  }, []);

  const requestPay = () => {
    if (!sessionStorage.getItem("accessToken")) {
      alert("Need To Login");
      return;
    }
    const { IMP } = window;
    IMP.init("imp85184426");

    IMP.request_pay(
      {
        pg: "kakaopay",
        pay_method: "card",
        merchant_uid: new Date().getTime(),
        name: "BTS-Permission To Dance",
        amount: 250000*amount,
        buyer_email: jwtDecode(sessionStorage.getItem("accessToken")).email,
        buyer_name: jwtDecode(sessionStorage.getItem("accessToken")).username,
        buyer_tel: "010-0000-0000",
        buyer_addr: "None",
        buyer_postcode: "123-456",
      },
      async (resp) => {
        if(resp.success){
            axios.get(BackendServer+'/api/v1/payment/'+resp.imp_uid,{
                headers: {
                    'Authorization': sessionStorage.getItem('accessToken')
                }
            })
            .then((resp)=>{
                console.log(resp);
            }).catch((error)=>{
                console.log(error);
            })
            alert('Payment Success!');
        } else {
            alert('Payment Failed.')
        }
      }
    );
  };

  return (
    <button
      onClick={requestPay}
      className="mt-4 bg-black border transition hover:-translate-y-1 hover:bg-white hover:text-black border-white px-6 py-3 mt-4 mr-2 text-white rounded-full"
    >
      PURCHASE CONCERT
    </button>
  );
};

export default PaymentComponents;
