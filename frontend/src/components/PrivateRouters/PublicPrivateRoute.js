import { Navigate } from "react-router-dom";
import { getCandidate, getHumanResource } from "../../store/storage";
export default function PublicPrivateRoute({children}) {
  const humanResource = getHumanResource();
  const candidate = getCandidate();
  
  if (humanResource || candidate) {
    return (
      <Navigate
        to="/job-posts"
        replace={true}
      />
    );
  }

  return children;
}
