import React from "react";
import { NavLink } from "react-router-dom";
import WorkIcon from "@mui/icons-material/Work";
import AddIcon from '@mui/icons-material/Add';
import BookmarkIcon from '@mui/icons-material/Bookmark';
import LogoutIcon from '@mui/icons-material/Logout';
import Search from "../Search";

export default function HumanResourceNavBar() {
  const handleLogout = async () => {
    localStorage.removeItem("humanResource");
    window.location.href = "/";
  }
  return (
    <nav className="flex justify-between h-20  items-center relative text text-gray-800 shadow-2xl pl-48 pr-48">
      <div className="w-20">
        <NavLink to={"/"}>
          <img src="/obss-logo.svg" alt="logo" className="w-full h-full" />
        </NavLink>
      </div>
      <div className="flex gap-16 items-center">
        <Search />
        <NavLink className="aria-[current=page]:text-obss-blue" to={"/human-resource/job-posts/create"}>
          <AddIcon /> <span>İş İlanı Oluştur</span>
        </NavLink>
        <NavLink className="aria-[current=page]:text-obss-blue" to={"/human-resource/job-posts"} end>
          <BookmarkIcon /> <span>Oluşturduğum İlanlar</span>
        </NavLink>
        <NavLink className="aria-[current=page]:text-obss-blue" to={"/job-posts"}>
          <WorkIcon /> <span>İş İlanları</span>
        </NavLink>
        <button onClick={handleLogout}>
          <LogoutIcon /> <span>Çıkış Yap</span>
        </button>
      </div>
    </nav>
  );
}
