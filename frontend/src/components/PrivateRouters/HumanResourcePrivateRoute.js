import { Navigate, useLocation } from "react-router-dom";
import { useSelector } from "react-redux";
export default function HumanResourcePrivateRoute({children}) {
  const humanResource = useSelector((state) => state.humanResource.humanResource);
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
