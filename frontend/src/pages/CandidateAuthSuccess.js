import React, { useState, useEffect } from "react";
import { Circles } from "react-loader-spinner";
import axios from "axios";

const CandidateAuthSuccess = () => {
  const [loading, setLoading] = useState(true);
  const [success, setSuccess] = useState(false);

  useEffect(() => {
    const simulateAPICall = async () => {
      try {
        const response = await axios.get(
          "http://localhost:8080/api/candidate/v1/token"
        );
        // Handle the response data if needed
        setLoading(false);
        setSuccess(true);
      } catch (error) {
        console.error("API call failed:", error);
      }
    };

    simulateAPICall();
  }, []);

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
