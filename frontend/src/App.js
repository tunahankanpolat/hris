import { useRoutes } from "react-router-dom";
import routes from "./routes";
import "react-toastify/dist/ReactToastify.css";

function App() {
  return useRoutes(routes);
}

export default App;
