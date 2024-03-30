import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from "./pages/home";
import My from "./pages/my";
import Purchase from "./pages/purchase";

const App = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="my" element={<My />} />
        <Route path="purchase" element={<Purchase />} />
      </Routes>
    </BrowserRouter>
  );
};

export default App;

/// <div className="bg-red-100 w-[480px] h-screen mx-auto">hello world</div>
