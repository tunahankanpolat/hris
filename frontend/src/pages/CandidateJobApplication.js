import React from "react";
import NavBar from "../components/LoginNavBar";
import CandidateJobApplicationCard from "../components/CandidateJobApplicationCard";
import JobPostDetail from "../components/JobPostDetail";
import Pagination from "../components/Pagination";

export default function CandidateJobApplication() {
  return (
    <div className="h-full">
      <NavBar />
      <main className="wrapper flex bg-job-posts-background pl-48 pr-48">
        <div className="w-1/2 bg-white h-full border shadaow pt-8 overflow-auto">
          <CandidateJobApplicationCard state="ACCEPTED" />
          <CandidateJobApplicationCard state="REJECTED" />
          <CandidateJobApplicationCard state="ACCEPTED" />
          <CandidateJobApplicationCard state="PROCESSING" />
          <CandidateJobApplicationCard state="REJECTED" />
          <CandidateJobApplicationCard state="ACCEPTED" />
          <CandidateJobApplicationCard state="PROCESSING" />
          <CandidateJobApplicationCard state="PROCESSING" />
          <CandidateJobApplicationCard state="PROCESSING" />
          <CandidateJobApplicationCard state="REJECTED" />
          <CandidateJobApplicationCard state="ACCEPTED" />
          <CandidateJobApplicationCard state="REJECTED" />
          <Pagination />
        </div>
        <div className="w-1/2 bg-white h-full border shadaow pt-8 overflow-auto">
          <JobPostDetail isApplied={true} />
        </div>
      </main>
    </div>
  );
}
