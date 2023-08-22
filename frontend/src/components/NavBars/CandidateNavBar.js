import React from "react";
import { NavLink } from "react-router-dom";
import WorkIcon from "@mui/icons-material/Work";
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import BookmarkIcon from '@mui/icons-material/Bookmark';
import LogoutIcon from '@mui/icons-material/Logout';
import { getCandidate, setCandidate } from "../../store/storage";
import { toast } from "react-toastify";
import CandidateService from "../../services/candidateService";
export default function CandidateNavBar() {
  const handleLogout = async () => {
    let candidateService = new CandidateService();

    candidateService.logout(getCandidate().token).then((response) => {
      toast.success(response.data);
      setCandidate();
      setTimeout(() => {
      window.location.href = "/";
      }, 1000);
    }).catch((error) => {
      setCandidate();
      window.location.href = "/";
    });
  }
  return (
    <nav className="flex justify-between h-20  items-center relative text text-gray-800 shadow-2xl pl-48 pr-48">
      <div className="w-20">
        <NavLink to={"/"}>
          <img src="/obss-logo.svg" alt="logo" className="w-full h-full" />
        </NavLink>
      </div>
      <div className="flex gap-16 items-center">
        <NavLink className="aria-[current=page]:text-obss-blue" to={"/candidate/me/job-applications"}>
          <BookmarkIcon /> <span>Başvurduğum İlanlar</span>
        </NavLink>
        <NavLink className="aria-[current=page]:text-obss-blue" to={"/job-posts"}>
          <WorkIcon /> <span>İş İlanları</span>
        </NavLink>
        <NavLink className="aria-[current=page]:text-obss-blue" to={"/candidate/me"} end>
          <AccountCircleIcon /> <span>Profilimi Gör</span>
        </NavLink>
        <button onClick={handleLogout}>
          <LogoutIcon /> <span>Çıkış Yap</span>
        </button>
      </div>
    </nav>
  );
}
