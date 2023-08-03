import { useState, useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import CandidateService from "../services/candidateService";
import { setCandidate } from "../store/candidate";
import { toast } from "react-toastify";

const CandidateAuthSuccess = () => {
  const [success, setSuccess] = useState(false);
  const candidate = useSelector((state) => state.candidate.candidate);

  const dispatch = useDispatch();
  useEffect(() => {
    if (!candidate) {
      let candidateService = new CandidateService();
      candidateService
        .login()
        .then((res) => {
          dispatch(setCandidate(res.data));
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
