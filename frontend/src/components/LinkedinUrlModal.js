import React, { useState } from 'react';
import CandidateService from '../services/candidateService';
import { toast } from 'react-toastify';
import { getCandidate } from '../store/storage';
const LinkedinUrlModal = ({ onClose}) => {
  const [linkedinUrl, setLinkedinUrl] = useState('');
  const candidate = getCandidate();
  const handleScrape = () => {
    let candidateService = new CandidateService();
    candidateService.scrapeLinkedinProfile(candidate.token,linkedinUrl).then((result) => {
        toast.success(result.data);
    }).catch((err) => {
        toast.error(err.response.data.error);
    })
    onClose();
  };

  return (
    <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50">
      <div className="bg-job-posts-background p-4 rounded-md">
        <h2 className="text-lg font-bold mb-4">Linkedin Url</h2>
        <input
          type="text"
          className="w-full border border-gray-300 rounded-md px-2 py-1 mb-4"
          placeholder="Linkedin Profil Adresiniz..."
          value={linkedinUrl}
          onChange={(e) => setLinkedinUrl(e.target.value)}
        />
        <div className="flex justify-end">
          <button
            className="bg-red-500 text-white px-4 py-2 rounded-md"
            onClick={handleScrape}
          >
            Bilgileri Çek
          </button>
          <button
            className="ml-2 bg-gray-300 text-black px-4 py-2 rounded-md"
            onClick={onClose}
          >
            İptal
          </button>
        </div>
      </div>
    </div>
  );
};

export default LinkedinUrlModal;
