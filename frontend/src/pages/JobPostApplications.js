import JobPostApplicationCard from "../components/JobPostApplicationCard";
import Pagination from "../components/Pagination";
import SyncIcon from "@mui/icons-material/Sync";
import ExpandCircleDownIcon from "@mui/icons-material/ExpandCircleDown";
import BlockIcon from "@mui/icons-material/Block";
import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import SearchBar from "../components/SearchBar";
import JobApplicationService from "../services/jobApplicationService";
import { toast } from "react-toastify";
import HumanResourceService from "../services/humanResouceService";
import { getHumanResource } from "../store/storage";

export default function JobPostApplications() {
  const { id } = useParams();
  const [jobPostApplications, setJobPostApplications] = useState([]);
  const [searchKeyword, setSearchKeyword] = useState(false);
  const [page, setPage] = useState(0);
  const [filter, setFilter] = useState(false);
  const humanResource = getHumanResource();
  const getJobPostApplications = () => {
    let humanResouceService = new HumanResourceService();
    humanResouceService
      .getJobPostApplications(
        humanResource.token,
        id,
        page,
        process.env.REACT_APP_PAGE_SIZE,
        filter,
        searchKeyword
      )
      .then((result) => {
        setJobPostApplications(result.data);
      });
  };

  const handleFilter = (status) => {
    if (filter === status) {
      setFilter(false);
      setPage(0);
    } else {
      setPage(0);
      setFilter(status);
    }
  };
  useEffect(() => {
    getJobPostApplications();
  }, [filter, searchKeyword]);

  const handlePageChange = (page) => {
    page = page - 1;
    let humanResouceService = new HumanResourceService();
    humanResouceService
      .getJobPostApplications(
        humanResource.token,
        id,
        page,
        process.env.REACT_APP_PAGE_SIZE,
        filter,
        searchKeyword
      )
      .then((result) => {
        if (result.data.length !== 0) {
          setPage(page);
          setJobPostApplications(result.data);
        }
      });
  };
  const updateJobApplicationStatus = (id, status) => {
    let jobApplicationService = new JobApplicationService();
    jobApplicationService
      .updateStatus(humanResource.token, id, status)
      .then((result) => {
        setJobPostApplications((prevJobApplications) =>
          prevJobApplications.map((jobApplication) =>
            jobApplication.jobApplicationId === id
              ? (jobApplication = { ...jobApplication, status: status })
              : jobApplication
          )
        );
        toast.success(result.data);
      })
      .catch((err) => {
        toast.error(err.response.data.error);
      });
  };
  const handleSearch = (keyword) => {
    if (keyword === "") {
      setSearchKeyword(false);
    } else {
      setSearchKeyword(keyword);
    }
  };
  return (
    <main className="wrapper flex bg-job-posts-background pl-48 pr-48 pb-8">
      <div className="w-full h-full shadaow pt-8">
        <div className="flex bg-white rounded-3xl w-full h-16 mb-4 justify-between items-center pl-5 pr-10">
          <div>
            <div className="flex item-center justify-start gap-10 ml-10">
              <h2 className="text-xl font-bold text-obss-blue">Filtrele</h2>
              <ul className="flex gap-10">
                <li onClick={() => handleFilter("PROCESSING")}>
                  <SyncIcon className="focus:ring-4 focus:outline-none focus:ring-blue-300text-orange-200 cursor-pointer opacity-70 hover:opacity-100 active:rounded-full active:border transition-all" />
                </li>
                <li onClick={() => handleFilter("REJECTED")}>
                  <BlockIcon className="focus:ring-4 focus:outline-none focus:ring-blue-300 text-red-500 cursor-pointer opacity-70 hover:opacity-100 active:rounded-full active:border transition-all" />
                </li>
                <li onClick={() => handleFilter("ACCEPTED")}>
                  <ExpandCircleDownIcon className="focus:ring-4 focus:outline-none focus:ring-blue-300 text-obss-blue cursor-pointer opacity-70 hover:opacity-100 active:rounded-full active:border transition-all" />
                </li>
              </ul>
            </div>
          </div>
          <SearchBar handleSearchSubmit={handleSearch} />
        </div>
        <div className="bg-white rounded-3xl w-full wrapper overflow-auto">
          <div className="w-full h-full">
            {jobPostApplications.map((jobPostApplication) => (
              <JobPostApplicationCard
                onUpdateStatus={updateJobApplicationStatus}
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
  );
}
