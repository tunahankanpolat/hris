import React from "react";
import NavBar from "../layouts/NavBar";
import JobPostApplicationCard from "../components/JobPostApplicationCard";
import Pagination from "../components/Pagination";
import SyncIcon from "@mui/icons-material/Sync";
import ExpandCircleDownIcon from "@mui/icons-material/ExpandCircleDown";
import BlockIcon from "@mui/icons-material/Block";


export default function JobPostApplications() {
  return (
    <div className="h-full">
      <NavBar />
      <main className="wrapper flex bg-job-posts-background pl-48 pr-48 pb-8">
        <div className="w-full h-full shadaow pt-8">
          <div className="flex bg-white rounded-3xl w-full h-16 mb-4 justify-start items-center pl-5">
          <div>
      <div className="flex item-center justify-start gap-5">
        <h2 className="text-xl font-bold text-obss-blue">Filtrele</h2>
        <SyncIcon className="focus:ring-4 focus:outline-none focus:ring-blue-300text-orange-200 cursor-pointer opacity-70 hover:opacity-100 active:rounded-full active:border transition-all" />
        <BlockIcon className="focus:ring-4 focus:outline-none focus:ring-blue-300 text-red-500 cursor-pointer opacity-70 hover:opacity-100 active:rounded-full active:border transition-all" />
        <ExpandCircleDownIcon className="focus:ring-4 focus:outline-none focus:ring-blue-300 text-obss-blue cursor-pointer opacity-70 hover:opacity-100 active:rounded-full active:border transition-all" />
      </div>
    </div>
          </div>
          <div className="bg-white rounded-3xl w-full wrapper overflow-auto">
            <div className="w-full h-full">
              <JobPostApplicationCard state="ACCEPTED" />
              <JobPostApplicationCard state="REJECTED" />
              <JobPostApplicationCard state="ACCEPTED" />
              <JobPostApplicationCard state="PROCESSING" />
              <JobPostApplicationCard state="REJECTED" />
              <JobPostApplicationCard state="ACCEPTED" />
              <JobPostApplicationCard state="PROCESSING" />
              <JobPostApplicationCard state="PROCESSING" />
              <JobPostApplicationCard state="PROCESSING" />
              <JobPostApplicationCard state="REJECTED" />
              <JobPostApplicationCard state="ACCEPTED" />
              <JobPostApplicationCard state="REJECTED" />
              <Pagination />
            </div>
          </div>
        </div>
      </main>
    </div>
  );
}
