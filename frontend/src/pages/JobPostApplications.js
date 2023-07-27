import React from "react";
import NavBar from "../layouts/NavBar";
import JobPostApplicationCard from "../components/JobPostApplicationCard";
import Pagination from "../components/Pagination";

export default function JobPostApplications() {
  return (
    <div className="h-full">
      <NavBar />
      <main className="wrapper flex bg-job-posts-background pl-48 pr-48 pb-8">
        <div className="flex w-full h-full shadaow pt-8">
          <div className="bg-white rounded-3xl w-full h-full overflow-auto">
            <div className="w-full h-full">
              <JobPostApplicationCard state="ACCEPTED" />
              <JobPostApplicationCard state="REJECTED" />
              <JobPostApplicationCard state="ACCEPTED" />
              <JobPostApplicationCard state="WAITING" />
              <JobPostApplicationCard state="REJECTED" />
              <JobPostApplicationCard state="ACCEPTED" />
              <JobPostApplicationCard state="WAITING" />
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
