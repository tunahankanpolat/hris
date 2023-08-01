import { useRoutes } from "react-router-dom";
import routes from "./routes";
import "react-toastify/dist/ReactToastify.css"; // Make sure to import the CSS file for styling

function App() {

  return useRoutes(routes);
}

export default App;
