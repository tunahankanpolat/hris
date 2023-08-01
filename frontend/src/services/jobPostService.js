import axios from "axios";
export default class JobPostService {
  getJobPosts(page, size) {
    return axios.get(process.env.REACT_APP_JOB_POSTS_URL + `/${page}/${size}`);
  }

  updateJobPost(token, jobPost) {
    return axios.put(process.env.REACT_APP_JOB_POSTS_URL, jobPost, {
      headers: {
        Authorization: "Bearer " + token,
      },
    });
  }
  deleteJobPost(token, jobPostId) {
    return axios.delete(process.env.REACT_APP_JOB_POSTS_URL + `/${jobPostId}`, {
      headers: {
        Authorization: "Bearer " + token,
      },
    });
  }
  getJobPostApplications(token, jobPostId, page, size) {
    return axios.get(
      process.env.REACT_APP_JOB_POSTS_URL +
        `/jobApplications/${page}/${size}?jobPostId=${jobPostId}`,
      {
        headers: {
          Authorization: "Bearer " + token,
        },
      }
    );
  }
}
