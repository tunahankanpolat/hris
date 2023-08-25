import JobPostCard from "../components/JobPostCard";
import JobPostDetail from "../components/JobPostDetail";
import Pagination from "../components/Pagination";
import { useState, useEffect } from "react";
import JobPostService from "../services/jobPostService";
import { toast } from "react-toastify";
import { getCandidate } from "../store/storage";
export default function JobPosts() {
  const [jobPosts, setJobPosts] = useState([]);
  const [page, setPage] = useState(0);
  const [currentJobPost, setCurrentJobPost] = useState(false);

  const candidate = getCandidate();
  const handleApply = () => {
    let jobPostService = new JobPostService();
    jobPostService.applyToJobPost(candidate.token, currentJobPost.jobPostId)
    .then((result) => {
      toast.success(result.data);
    }).catch((err) => {
      toast.error(err.response.data.error);
    })
  }

  useEffect(() => {
    let jobPostService = new JobPostService();
    jobPostService
      .getJobPosts(page, process.env.REACT_APP_PAGE_SIZE)
      .then((result) => {
        setJobPosts(result.data);
        setCurrentJobPost(result.data[0]);
      });
  }, []);
  const handlePageChange = (page) => {
    page = page - 1;
    let jobPostService = new JobPostService();
    jobPostService
      .getJobPosts(page, process.env.REACT_APP_PAGE_SIZE)
      .then((result) => {
        if (result.data.length !== 0) {
          setPage(page);
          setJobPosts(result.data);
          setCurrentJobPost(result.data[0]);
        }
      });
  };
  return (
    <main className="wrapper flex bg-job-posts-background pl-48 pr-48">
      <div className="w-1/2 h-full flex flex-col justify-between bg-white ">
        <div className="w-full h-full border shadaow pt-8 overflow-auto">
          {jobPosts.map((jobPost) => (
            <JobPostCard
              key={jobPost.jobPostId}
              {...jobPost}
              onClick={() => setCurrentJobPost(jobPost)}
            />
          ))}
        </div>
        <Pagination
          currentPage={page + 1}
          totalPages={page + 2}
          className="self-end"
          onPageChange={handlePageChange}
        />
      </div>

      <div className="w-1/2 bg-white h-full border shadaow pt-8 overflow-auto">
        {currentJobPost && (
          <JobPostDetail {...currentJobPost} handleApply={handleApply} />
        )}
      </div>
    </main>
  );
}
