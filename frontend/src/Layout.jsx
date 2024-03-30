import { Outlet } from "react-router-dom";
import { useState } from "react";

const Layout = () => {
  const [userName, setUserName] = useState();
  const [nftIds, setNftIds] = useState();
  const [newNft, setNewNft] = useState();

  return (
    <div>
      <Outlet
        context={{
          userName,
          setUserName,
          nftIds,
          setNftIds,
          newNft,
          setNewNft,
        }}
      />
    </div>
  );
};

export default Layout;
