import CandidateJobApplicationCard from "../components/CandidateJobApplicationCard";
import JobPostDetail from "../components/JobPostDetail";
import Pagination from "../components/Pagination";
import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import CandidateService from "../services/candidateService";
import { getCandidate, getHumanResource } from "../store/storage";
import CandidateJobApplicationDetail from "../components/CandidateJobApplicationDetail";

export default function CandidateJobApplication() {
  let { id } = useParams();
  const humanResource = getHumanResource();
  const candidate = getCandidate();

  const [page, setPage] = useState(0);
  const [currentJobPost, setCurrentJobPost] = useState(false);
  const [jobApplications, setJobApplications] = useState(false);

  useEffect(() => {
    let candidateService = new CandidateService();
    let token = humanResource.token;
    if (!id) {
      token = candidate.token;
    }
    candidateService
      .getJobApplications(
        token,
        page,
        process.env.REACT_APP_PAGE_SIZE,
        id
      )
      .then((result) => {
        setJobApplications(result.data);
        setCurrentJobPost(result.data[0]);
      });
  }, []);
  const handlePageChange = (page) => {
    let candidateService = new CandidateService();
    let token = humanResource.token;
    if (!id) {
      token = candidate.token;
    }
    page = page - 1;
    candidateService
      .getJobApplications(
        token,
        page,
        process.env.REACT_APP_PAGE_SIZE,
        id,
      )
      .then((result) => {
        if (result.data.length !== 0) {
          setPage(page);
          setJobApplications(result.data);
          setCurrentJobPost(result.data[0]);
        }
      });
  };

  return (
    <main className="wrapper flex bg-job-posts-background pl-48 pr-48">
      <div className="w-1/2 bg-white h-full border shadaow pt-8 overflow-auto">
        {jobApplications &&
          jobApplications.map((jobApplication) => (
            <CandidateJobApplicationCard
              key={jobApplication.jobApplicationId}
              {...jobApplication}
              onClick={() => setCurrentJobPost(jobApplication)}
            />
          ))}
        <Pagination
          currentPage={page + 1}
          totalPages={page + 2}
          className="self-end"
          onPageChange={handlePageChange}
        />
      </div>
      <div className="w-1/2 bg-white h-full border shadaow pt-8 overflow-auto">
        {currentJobPost && (
          <CandidateJobApplicationDetail {...currentJobPost} />
        )}
      </div>
    </main>
  );
}
