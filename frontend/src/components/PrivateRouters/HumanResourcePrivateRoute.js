import { Navigate, useLocation } from "react-router-dom";
import { getHumanResource } from "../../store/storage";
export default function HumanResourcePrivateRoute({children}) {
  const humanResource = getHumanResource();
  const location = useLocation();
  
  if (!humanResource) {
    return (
      <Navigate
        to="/login"
        replace={true}
        state={{ return_url: location.pathname }}
      />
    );
  }

  return children;
}
