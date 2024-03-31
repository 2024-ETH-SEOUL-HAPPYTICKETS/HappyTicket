import React from 'react'
// 
const BackendServer = "http://158.179.169.106:8081";

const RefundComponents = ({imp_uid}) => {

    const handleRefundRequest = () => {
        axios.post(BackendServer+'/api/v1/payment/refund',{ 
            merchant_uid: imp_uid, // 주문번호
            cancel_request_amount: 250000, // 환불금액
            reason: "테스트 결제 환불", // 환불사유
          })
          .then((resp)=>{
            console.log(resp.data)
            alert('Refund Success');
          })
          .catch((err)=>{
            console.log(err)
            alert('Refund Fail');
          });
    }
  return (
    <button onClick={handleRefundRequest}>Refund</button>
  )
}

export default RefundComponents