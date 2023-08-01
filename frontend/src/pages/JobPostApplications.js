import React from "react";
import JobPostApplicationCard from "../components/JobPostApplicationCard";
import Pagination from "../components/Pagination";
import SyncIcon from "@mui/icons-material/Sync";
import ExpandCircleDownIcon from "@mui/icons-material/ExpandCircleDown";
import BlockIcon from "@mui/icons-material/Block";
import HumanResourceNavBar from "../components/HumanResourceNavBar";
import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { useSelector } from "react-redux";
import JobPostService from "../services/jobPostService";

export default function JobPostApplications() {
  const { id } = useParams();
  const [jobPostApplications, setJobPostApplications] = useState([]);
  const [page, setPage] = useState(0);
  const humanResource = useSelector(
    (state) => state.humanResource.humanResource
  );
  useEffect(() => {
    let jobPostService = new JobPostService();
    jobPostService
      .getJobPostApplications(
        humanResource.token,
        id,
        page,
        process.env.REACT_APP_PAGE_SIZE
      )
      .then((result) => {
        debugger;
        setJobPostApplications(result.data);
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);
  const handlePageChange = (page) => {
    page = page - 1;
    let jobPostService = new JobPostService();
    jobPostService.getJobPostApplications(
        humanResource.token,
        id,
        page,
        process.env.REACT_APP_PAGE_SIZE
      )
      .then((result) => {
        if (result.data.length !== 0) {
          setPage(page);
          setJobPostApplications(result.data);
        }
      })
      .catch((err) => {
        console.log(err);
      });
  };
  return (
    <div className="h-full">
      <HumanResourceNavBar />
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
              {jobPostApplications.map((jobPostApplication) => (
                <JobPostApplicationCard
                  key={jobPostApplication.jobApplicationId}
                  {...jobPostApplication}
                  defaultAvatar="/public/avatar.jpg"
                />
              ))}
              <Pagination
                currentPage={page + 1}
                totalPages={page + 2}
                className="self-end"
                onPageChange={handlePageChange}
              />
            </div>
          </div>
        </div>
      </main>
    </div>
  );
}
