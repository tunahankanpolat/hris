import React from "react";
import { NavLink } from "react-router-dom";
import WorkIcon from "@mui/icons-material/Work";
export default function LoginNavBar() {
  return (
    <nav className="flex justify-between h-20  items-center relative text text-gray-800 shadow-2xl pl-48 pr-48">
      <div className="w-20">
        <NavLink to={"/login"}>
          <img src="/obss-logo.svg" alt="logo" className="w-full h-full" />
        </NavLink>
      </div>
      <div className="flex gap-16 items-center">
        <NavLink to={"/job-posts"}>
          <WorkIcon /> <span>İş İlanları</span>
        </NavLink>
      </div>
    </nav>
  );
}
