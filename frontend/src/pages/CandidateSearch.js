import SyncIcon from "@mui/icons-material/Sync";
import ExpandCircleDownIcon from "@mui/icons-material/ExpandCircleDown";
import BlockIcon from "@mui/icons-material/Block";
import Pagination from "../components/Pagination";
import CandidateCard from "../components/CandidateCard";
import SearchService from "../services/searchService";
import { useState } from "react";
import { useEffect } from "react";
import { useParams } from "react-router-dom";
import { getHumanResource } from "../store/storage";
export default function CandidateSearch(props) {
  const [page, setPage] = useState(0);
  const [candidates, setCandidates] = useState(false);
  const humanResource = getHumanResource();
  const { keyword } = useParams();
  const handlePageChange = (page) => {
    page = page - 1;
    let searchService = new SearchService();
    searchService
      .searchOnCandidate(
        humanResource.token,
        keyword,
        page,
        process.env.REACT_APP_PAGE_SIZE
      )
      .then((result) => {
        if (result.data.length !== 0) {
          setPage(page);
          setCandidates(result.data);
        }
      })
      .catch((err) => {
        console.log(err);
      });
  };
  useEffect(() => {
    debugger;

    let searchService = new SearchService();
    searchService
      .searchOnCandidate(
        humanResource.token,
        keyword,
        page,
        process.env.REACT_APP_PAGE_SIZE
      )
      .then((result) => {
        setCandidates(result.data);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [keyword ]);
  return (
    <main className="wrapper flex bg-job-posts-background pl-48 pr-48 pb-8">
      <div className="w-full h-full shadaow pt-8">
        <div className="bg-white rounded-3xl w-full h-full overflow-auto">
          <div className="w-full h-full">
            {candidates &&
              candidates.map((candidate) => (
                <CandidateCard
                  key={candidate.candidateId}
                  {...candidate}
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
