import { Navigate, useLocation } from "react-router-dom";
import { useSelector } from "react-redux";
export default function CandidatePrivateRoute({children}) {
  const candidate = useSelector((state) => state.candidate.candidate);
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
