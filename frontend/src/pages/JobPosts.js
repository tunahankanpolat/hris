import JobPostCard from "../components/JobPostCard";
import JobPostDetail from "../components/JobPostDetail";
import Pagination from "../components/Pagination";
import { useState } from "react";
import { useEffect } from "react";
import JobPostService from "../services/jobPostService";

export default function JobPosts() {
  const [jobPosts, setJobPosts] = useState([]);
  const [page, setPage] = useState(0);
  const [currentJobPost, setCurrentJobPost] = useState(false);
  useEffect(() => {
    let jobPostService = new JobPostService();
    jobPostService
      .getJobPosts(page, process.env.REACT_APP_PAGE_SIZE)
      .then((result) => {
        setJobPosts(result.data);
        setCurrentJobPost(result.data[0]);
      })
      .catch((err) => {
        console.log(err);
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
      })
      .catch((err) => {
        console.log(err);
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
          <JobPostDetail isApplied={false} {...currentJobPost} />
        )}
      </div>
    </main>
  );
}
