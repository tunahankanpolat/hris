import { Navigate } from "react-router-dom";
import { useSelector } from "react-redux";
export default function PublicPrivateRoute({children}) {
  const humanResource = useSelector((state) => state.humanResource.humanResource);
  const candidate = useSelector((state) => state.candidate.candidate);
  
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
