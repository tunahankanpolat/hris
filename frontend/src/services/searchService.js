import axios from "axios";
export default class SearchService {
  async searchOnCandidate(token, keyword, page, size) {
    return await axios.get(
      process.env.REACT_APP_SEARCH_URL + `/candidates/${page}/${size}?keyword=${keyword}`,
      {
        headers: {
          Authorization: "Bearer " + token,
        },
      }
    );
  }

  async searchOnCandidateForJobPost(token, keyword, page, size, jobPostId) {
    return await axios.get(
        process.env.REACT_APP_SEARCH_URL + `/jobPosts/${jobPostId}/jobApplications/candidates/${page}/${size}?keyword=${keyword}`,
        {
          headers: {
            Authorization: "Bearer " + token,
          },
        }
      );
  }
}
