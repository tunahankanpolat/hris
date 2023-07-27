import React from "react";
import NavBar from "../layouts/NavBar";
import JobPostCard from "../components/JobPostCard";
import JobPostDetailForUpdate from "../components/JobPostDetailForUpdate";

export default function CandidateJobApplication() {
  return (
    <div className="h-full">
      <NavBar />
      <main className="wrapper flex bg-job-posts-background pl-48 pr-48">
        <div className="w-1/2 bg-white h-full border shadaow pt-8 overflow-auto">
            <JobPostCard state = "ACCEPTED"/>
            <JobPostCard state = "REJECTED"/>
            <JobPostCard state = "ACCEPTED"/>
            <JobPostCard state = "WAITING"/>
            <JobPostCard state = "REJECTED"/>
            <JobPostCard state = "ACCEPTED"/>
            <JobPostCard state = "WAITING"/>
            <JobPostCard state = "PROCESSING"/>
            <JobPostCard state = "PROCESSING"/>
            <JobPostCard state = "REJECTED"/>
            <JobPostCard state = "ACCEPTED"/>
            <JobPostCard state = "REJECTED"/>

        </div>
        <div className="w-1/2 bg-white h-full border shadaow pt-8 overflow-auto">
            <JobPostDetailForUpdate/>
        </div>
      </main>
    </div>
  );
}
