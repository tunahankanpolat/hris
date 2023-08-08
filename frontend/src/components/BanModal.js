import React, { useState } from 'react';
import BlacklistService from '../services/blacklistService';
import { toast } from 'react-toastify';
import { getHumanResource } from '../store/storage';
const BanModal = ({ onClose, candidateId}) => {
  const [reason, setReason] = useState('');
  const humanResource = getHumanResource();
  const handleBan = () => {
    let banRequest = {
      reason: reason,
      id: candidateId
    };
    let blacklistService = new BlacklistService();
    blacklistService.addBlacklist(humanResource.token,banRequest).then((res) => {
      toast.success(res.data);
    }).catch((err) => {
      toast.error(err.response.data.error_message);
    });
    onClose();
  };

  return (
    <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50">
      <div className="bg-job-posts-background p-4 rounded-md">
        <h2 className="text-lg font-bold mb-4">Kullanıcıyı Banla</h2>
        <input
          type="text"
          className="w-full border border-gray-300 rounded-md px-2 py-1 mb-4"
          placeholder="Banlama sebebi..."
          value={reason}
          onChange={(e) => setReason(e.target.value)}
        />
        <div className="flex justify-end">
          <button
            className="bg-red-500 text-white px-4 py-2 rounded-md"
            onClick={handleBan}
          >
            Banla
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

export default BanModal;
