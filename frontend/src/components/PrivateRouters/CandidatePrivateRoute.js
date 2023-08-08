import { Navigate, useLocation } from "react-router-dom";
import { getCandidate } from "../../store/storage";
export default function CandidatePrivateRoute({children}) {
  const candidate = getCandidate();
  const location = useLocation();

  if (!candidate) {
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
