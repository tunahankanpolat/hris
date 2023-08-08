import { useEffect, useState } from "react";
import { setCandidate, setHumanResource } from "../store/storage";
export default function OAuth2RedirectHandler() {
  const [candidateState, setCandidateState] = useState(false);

  useEffect(() => {
    const urlSearchParams = new URLSearchParams(window.location.search);
    const token = urlSearchParams.get("token");
    setCandidate({ token: token });
    setCandidateState(true);
    setHumanResource();
    const closePageAfterDelay = () => {
      window.close();
    };

    const delayInMilliseconds = 1000; 
    const timeoutId = setTimeout(closePageAfterDelay, delayInMilliseconds);
    return () => clearTimeout(timeoutId);
  }, []);
  return (
    <div className="flex items-center justify-center h-screen">
      {candidateState && (
        <div className="text-center">
          <h2 className="text-2xl font-bold text-green-500">Giriş Başarılı</h2>
        </div>
      )}
    </div>
  );
}
