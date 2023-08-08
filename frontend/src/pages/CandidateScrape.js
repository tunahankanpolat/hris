// components/ScrapeForm.js
import { Circles } from "react-loader-spinner";
import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import CandidateService from "../services/candidateService";
import { setCandidate } from "../store/storage";
import { toast } from "react-toastify";
axios.defaults.withCredentials = true;

const CandidateScrape = () => {
  const [loading, setLoading] = useState(false);
  const [url, setUrl] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    let candidateService = new CandidateService();
    candidateService
      .scrapeSkills(url)
      .then((res) => {
        setLoading(false);
        setCandidate(res.data);
        navigate("/candidate/auth/success");
      })
      .catch((err) => {
        setLoading(false);
        toast.error(err.response.data.error_message);
      });
  };

  return (
    <div className="flex flex-col gap-10 justify-center items-center h-screen">
      {loading && <Circles color="#3B82F6" />}
      <form onSubmit={handleSubmit} className="max-w-md w-full">
        <input
          type="text"
          placeholder="Linkedin Profil Linkinizi Giriniz"
          value={url}
          onChange={(e) => setUrl(e.target.value)}
          className="w-full border border-gray-300 p-2 rounded"
        />
        <button
          type="submit"
          className="mt-2 w-full bg-blue-500 text-white py-2 rounded"
        >
          Gönder
        </button>
      </form>
    </div>
  );
};

export default CandidateScrape;
