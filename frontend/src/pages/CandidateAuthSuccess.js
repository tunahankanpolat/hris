import { useState, useEffect } from "react";
import CandidateService from "../services/candidateService";
import { toast } from "react-toastify";
import { getCandidate, setCandidate } from "../store/storage";

const CandidateAuthSuccess = () => {
  const [success, setSuccess] = useState(false);
  const candidate = getCandidate();

  useEffect(() => {
    if (!candidate) {
      let candidateService = new CandidateService();
      candidateService
        .login()
        .then((res) => {
          setCandidate(res.data);
          setSuccess(true);
        })
        .catch((err) => {
          toast.error(err.response.data.error_message);
        });
    }else{
      setSuccess(true);
    }
  }, [candidate]);

  return (
    <div className="flex items-center justify-center h-screen">
      {success && (
        <div className="text-center">
          <h2 className="text-2xl font-bold text-green-500">Giriş Başarılı</h2>
        </div>
      )}
    </div>
  );
};

export default CandidateAuthSuccess;
