import axios from "axios";
export default class JobPostService {
  async getJobPosts(page, size) {
    return await axios.get(process.env.REACT_APP_JOB_POSTS_URL + `/${page}/${size}`);
  }

  async updateJobPost(token, jobPost) {
    return await axios.put(process.env.REACT_APP_JOB_POSTS_URL, jobPost, {
      headers: {
        Authorization: "Bearer " + token,
      },
    });
  }

  async applyToJobPost(token, jobPostId) {
    return await axios.post(
      process.env.REACT_APP_JOB_POSTS_URL + `/${jobPostId}`,
      {},
      {
        headers: {
          Authorization: "Bearer " + token,
        },
      }
    );
  }

  async deleteJobPost(token, jobPostId) {
    return await axios.delete(process.env.REACT_APP_JOB_POSTS_URL + `/${jobPostId}`, {
      headers: {
        Authorization: "Bearer " + token,
      },
    });
  }
  async createJobPost(token, jobPost) {
    return await axios.post(process.env.REACT_APP_JOB_POSTS_URL, jobPost, {
      headers: {
        Authorization: "Bearer " + token,
      },
    });
  }
}
