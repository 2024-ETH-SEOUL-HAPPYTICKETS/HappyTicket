import { Outlet } from "react-router-dom";
import { useState } from "react";

const Layout = () => {
  const [userName, setUserName] = useState();
  const [nftIds, setNftIds] = useState();

  return (
    <div>
      <Outlet
        context={{
          userName,
          setUserName,
          nftIds,
          setNftIds,
        }}
      />
    </div>
  );
};

export default Layout;
