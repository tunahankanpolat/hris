import React from "react";
import NavBar from "../layouts/NavBar";
import JobPostCard from "../components/JobPostCard";
import JobPostDetail from "../components/JobPostDetail";

export default function Home() {
  return (
    <div className="h-full">
      <NavBar />
      <main className="wrapper flex bg-job-posts-background pl-48 pr-48">
        <div className="w-1/2 bg-white h-full border shadaow pt-8 overflow-auto">
            <JobPostCard />
            <JobPostCard />
            <JobPostCard />
            <JobPostCard />
            <JobPostCard />
            <JobPostCard />
            <JobPostCard />
            <JobPostCard />
            <JobPostCard />
            <JobPostCard />
            <JobPostCard />
            <JobPostCard />

        </div>
        <div className="w-1/2 bg-white h-full border shadaow pt-8 overflow-auto">
            <JobPostDetail isApplied={false}/>
        </div>
      </main>
    </div>
  );
}
