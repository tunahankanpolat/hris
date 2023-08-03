import { useState, useEffect } from "react";
import JobPostCard from "../components/JobPostCard";
import JobPostDetailForUpdate from "../components/JobPostDetailForUpdate";
import { Navigate } from "react-router-dom";
import { useSelector } from "react-redux";
import Pagination from "../components/Pagination";
import HumanResourceService from "../services/humanResouceService";

export default function HumanResouceJobPosts() {
  const humanResource = useSelector(
    (state) => state.humanResource.humanResource
  );
  const [jobPosts, setJobPosts] = useState([]);
  const [page, setPage] = useState(0);
  const [currentJobPost, setCurrentJobPost] = useState(false);

  useEffect(() => {
    if (!humanResource) {
      return <Navigate to="/login" />;
    } else {
      let humanResouceService = new HumanResourceService();
      humanResouceService
        .getJobPosts(humanResource.token, page, process.env.REACT_APP_PAGE_SIZE)
        .then((result) => {
          debugger;
          setJobPosts(result.data);
          setCurrentJobPost(result.data[0]);
        })
        .catch((err) => {
          console.log(err);
        });
    }
  }, []);
  const handlePageChange = (page) => {
    page = page - 1;
    let humanResouceService = new HumanResourceService();
    humanResouceService
      .getJobPosts(humanResource.token, page, process.env.REACT_APP_PAGE_SIZE)
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
  const updateJobPost = (updatedJobPost) => {
    setJobPosts((prevJobPosts) =>
      prevJobPosts.map((jobPost) =>
        jobPost.jobPostId === updatedJobPost.jobPostId
          ? updatedJobPost
          : jobPost
      )
    );
    setCurrentJobPost(updatedJobPost);
  };
  const removeJobPost = (deletedJobPost) => {
    setJobPosts((prevJobPosts) =>
      prevJobPosts.filter(
        (jobPost) => jobPost.jobPostId !== deletedJobPost.jobPostId
      )
    );
    setCurrentJobPost(null); // Clear the currentJobPost when the corresponding job post is deleted
  };
  return (
    <main className="wrapper flex bg-job-posts-background pl-48 pr-48">
      <div className="w-1/2 h-full flex flex-col justify-between bg-white ">
        <div className="w-full h-full border shadaow pt-8 overflow-auto">
          {jobPosts.map((jobPost, index) => (
            <JobPostCard
              setJobPost={setCurrentJobPost}
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
          <JobPostDetailForUpdate
            {...currentJobPost}
            onUpdate={updateJobPost}
            onDelete={removeJobPost}
          />
        )}
      </div>
    </main>
  );
}
