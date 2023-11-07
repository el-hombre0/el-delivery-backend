import { BrowserRouter, Routes, Route } from "react-router-dom";
import { Registration, Authorization, Main } from "./components";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Main />}></Route>
        <Route path="reg" element={<Registration />}></Route>
        <Route path="auth" element={<Authorization />}></Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
