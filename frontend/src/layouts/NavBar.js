import React from "react";
import { Link } from "react-router-dom";
import WorkIcon from "@mui/icons-material/Work";

export default function Navbar() {
  return (
    <nav className="flex justify-between h-20  items-center relative text text-gray-800 shadow-2xl pl-48 pr-48">
      <div className="w-20">
        <img src="/obss-logo.svg" alt="logo" className="w-full h-full" />
      </div>
      <div className="flex gap-16">
        <Link>
          <li className="flex flex-col items-center">
            <WorkIcon /> <span>İş İlanları</span>
          </li>
        </Link>
        <button className="ml-8 px-6 py-3 rounded-full ring-[1px] text-blue-600  ring-blue-600">
            Linkedin ile Oturum Aç
          </button>
      </div>
    </nav>
  );
}
