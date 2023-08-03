import axios from "axios";
export default class HumanResourceService {
  async login(userName, password) {
    return await axios.post(process.env.REACT_APP_HUMAN_RESOURCE_LOGIN_URL, {
      userName: userName,
      password: password,
    });
  }
  async getJobPosts(token, page, size) {
    return await axios.get(
      process.env.REACT_APP_HUMAN_RESOURCE_JOB_POSTS_URL + `/${page}/${size}`,
      {
        headers: {
          Authorization: "Bearer " + token,
        },
      }
    );
  }
  async getJobPostApplications(token, jobPostId, page, size, status) {
    return await axios.get(
      process.env.REACT_APP_HUMAN_RESOURCE_JOB_POSTS_URL + `/${jobPostId}/jobApplications/${page}/${size}` + (status ? `?jobApplicationStatus=${status}` : ""),
      {
        headers: {
          Authorization: "Bearer " + token,
        },
      }
    );
  }

}
