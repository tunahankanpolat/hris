import { Outlet } from "react-router-dom";
import HumanResourceNavBar from "../components/NavBars/HumanResourceNavBar";
import CandidateNavBar from "../components/NavBars/CandidateNavBar";
import AnonymousNavBar from "../components/NavBars/AnonymousNavBar";
import { useSelector } from "react-redux";

export default function Layout() {
  const humanResource = useSelector((state) => state.humanResource.humanResource);
  const candidate = useSelector((state) => state.candidate.candidate);
  let NavBarComponent = null;
  if (humanResource) {
    NavBarComponent = <HumanResourceNavBar />;
  } else if (candidate) {
    NavBarComponent = <CandidateNavBar />;
  } else {
    NavBarComponent = <AnonymousNavBar />;
  }
  
  return (
    <div className="h-full">
      {NavBarComponent}
      <Outlet />
    </div>
  );
}
